package ru.nsu.g.klyus.d.game.model;

import ru.nsu.g.klyus.d.game.controller.GameController;

import java.util.*;

public class GameModel {

    private double sceneWidth;
    private double sceneHeight;
    private Long lastTimeValue;
    private Player player;
    private ArrayList<Sprite> entities;
    private ArrayList<Projectile> bullets;
    private ArrayList<Actor> actors;
    private ArrayDeque<Level> levels;
    private Integer enemiesLeft;
    private GameController controller;
    private Integer flag;
    private HashMap<String, Rectangle> sizes;

    public Integer getFlag() {
        return flag;
    }

    public GameModel() {
    }

    public void setLastTimeValue(Long lastTimeValue) {
        this.lastTimeValue = lastTimeValue;
    }

    public GameModel(HashMap <String, Rectangle> sizes, Double sceneWidth, Double sceneHeight, GameController controller) {
        flag = 0;
        this.controller = controller;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.sizes = sizes;
        player = new Player(sizes.get("Projectile"));
        player.getImage().setHeight(sizes.get("Player").getHeight());
        player.getImage().setWidth(sizes.get("Player").getWidth());
        player.correctBoundaries();
        entities = new ArrayList<>();
        bullets = new ArrayList<>();
        actors = new ArrayList<>();
        levels = new ArrayDeque<>();
        entities.add(player);
        actors.add(player);
        player.getImage().setPosition(sceneWidth / 2 - player.getWidth() / 2,
                sceneHeight - player.getHeight());
        for (int i = 1; i <= 5; i++) {
            levels.add(new Level("/resources/levels/level" + i + ".txt"));
        }
        enemiesLeft = 0;
    }

    public void loadNextLevel() throws NoSuchElementException {
        Level level = levels.removeFirst();
        switch (level.getLevelType()) {
            case "static":
                addStaticMonsters(level.getHealth(), level.getColumns(), level.getRows());
                break;
            case "moving":
                addMovingMonsters(level.getVelocity(), level.getHealth(), level.getColumns(), level.getRows());
                break;
            case "boss":
                addBoss(level.getHealth());
                break;
        }
    }

    public Enemy createEnemyByPosition(int column, int row, int columns, int rows, Enemy lastEnemy) {
        Enemy enemy = new Enemy(sizes.get("Projectile"));
        enemy.getImage().setHeight(sizes.get("Enemy").getHeight());
        enemy.getImage().setWidth(sizes.get("Enemy").getWidth());
        enemy.getImage().setPosition((sceneWidth / columns) * column + sceneWidth / (columns * 2) - enemy.getWidth() / 2,
                sceneHeight / (2 * rows) * row + sceneHeight / (4 * rows) - enemy.getHeight() / 2);
        if (row != 0)
            enemy.setUpperNeighbour(lastEnemy);
        else
            enemy.setUpperNeighbour(null);
        if (row == rows - 1)
            enemy.setLowest(true);
        else
            enemy.setLowest(false);
        return enemy;
    }

    public void addStaticMonsters(int hp, int columns, int rows) {
        Enemy lastEnemy = null;
        enemiesLeft = columns * rows;
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                Enemy enemy = createEnemyByPosition(j, i, columns, rows, lastEnemy);
                enemy.setHealth(hp);
                entities.add(enemy);
                actors.add(enemy);
                enemy.correctBoundaries();
                lastEnemy = enemy;
            }
        }
    }

    public void addBoss(int hp) {
        enemiesLeft = 1;
        Boss enemy = new Boss(sizes.get("Projectile"));
        enemy.setHealth(hp);
        enemy.setLowest(true);
        enemy.getImage().setHeight(sizes.get("Boss").getHeight());
        enemy.getImage().setWidth(sizes.get("Boss").getWidth());
        enemy.getImage().setPosition(sceneWidth / 2 - enemy.getWidth() / 2, sceneHeight / 2 - enemy.getHeight());
        entities.add(enemy);
        actors.add(enemy);
        enemy.correctBoundaries();
    }

    public void addMovingMonsters(int velocity, int hp, int columns, int rows) {
        Enemy lastEnemy = null;
        enemiesLeft = columns * rows;
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                Enemy enemy = createEnemyByPosition(j, i, columns, rows, lastEnemy);
                enemy.setPrefVelocityX(velocity);
                enemy.setPrefVelocityY(velocity);
                enemy.setHealth(hp);
                enemy.setYBoundaries(sceneHeight / (2 * rows) * i, sceneHeight / (2 * rows) * (i + 1));
                enemy.setXBoundaries(sceneWidth / columns * j, sceneWidth / columns * (j + 1));
                enemy.setVelocity(-velocity, 0);
                enemy.correctBoundaries();
                entities.add(enemy);
                actors.add(enemy);
                lastEnemy = enemy;
            }
        }
    }


    public void handle(long currentNanoTime) {
        //frame time
        double elapsedTime = (currentNanoTime - lastTimeValue) / 1e9;
        lastTimeValue = currentNanoTime;
        if (flag != -1 && flag != -2) {
            if (enemiesLeft <= 0) {
                Thread th = new Thread(() -> {
                    try {
                        loadNextLevel();
                        flag = 1;
                    } catch (NoSuchElementException e) {
                        flag = -2;
                    }
                    Thread.currentThread().interrupt();
                });
                th.start();
                try {
                    th.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (flag == -2) {
                    return;
                    /*controller.getInputs().clear();
                    controller.unbindControls(view.getScene());
                    view.victoryScreen();
                    */
                }
            }
            //player movement
            controller.getInputs().forEach(button -> {
                //move
                if (button.equals("LEFT") || button.equals("RIGHT"))
                    player.movePlayer(button, elapsedTime);
                //attack
                if (button.equals("SPACE")) {
                    Projectile bullet = player.attack(currentNanoTime);
                    if (bullet != null) {
                        entities.add(bullet);
                        bullets.add(bullet);
                    }
                }
                if (button.equals("C")) {
                    Projectile bullet = player.superAttack(currentNanoTime);
                    if (bullet != null) {
                        entities.add(bullet);
                        bullets.add(bullet);
                    }
                }
            });
            //monster shoot
            actors.forEach(actor -> {
                if (actor.getClass() == Boss.class) {
                    if ((int) (Math.random() * 10) == 0) {
                        Projectile bullet = actor.attack();
                        entities.add(bullet);
                        bullets.add(bullet);
                    }
                }
                if (actor.getClass() == Enemy.class) {
                    if (((Enemy) actor).isLowest()) {
                        if ((int) (Math.random() * 150) == 1) { //once in 150 frames ~ 2.5 sec; 1 can be any number
                            Projectile bullet = ((Enemy) actor).attack();
                            entities.add(bullet);
                            bullets.add(bullet);
                        }
                    }
                }
            });
            Iterator<Sprite> iter = entities.iterator();
            while (iter.hasNext()) {
                Sprite entity = iter.next();
                if (entity != player) {
                    entity.update(elapsedTime);
                }
                if (entity.getClass() == Projectile.class) {
                    if (((Projectile) entity).isDestroyed()) {
                        bullets.remove(entity);
                        iter.remove();
                    }
                }
            }
            //getting hit by bullets
            for (int i = bullets.size() - 1; i >= 0; i--) {
                Projectile bullet = bullets.get(i);
                for (int j = actors.size() - 1; j >= 0; j--) {
                    Actor actor = actors.get(j);
                    if (bullet.intersects(actor)) {
                        actor.getHit(bullet);
                        bullets.remove(bullet);
                        entities.remove(bullet);
                        if (actor.getHealth() <= 0) {
                            if (actor.getClass() == Enemy.class
                                    && ((Enemy) actor).getUpperNeighbour() != null
                                    && ((Enemy) actor).isLowest())
                                ((Enemy) actor).getUpperNeighbour().setLowest(true);
                            actors.remove(actor);
                            entities.remove(actor);
                            if (actor.getClass() != Player.class)
                                enemiesLeft--;
                            else {
                                flag = -1;
                                return;
                                /*controller.getInputs().clear();
                                controller.unbindControls(view.getScene());
                                view.deathScreen();
                                */
                            }
                        }
                    }
                }
            }
        }
        //view.drawView();
    }


    public ArrayList<Sprite> getEntities() {
        return entities;
    }


}
