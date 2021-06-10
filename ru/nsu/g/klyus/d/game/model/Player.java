package ru.nsu.g.klyus.d.game.model;


import java.util.HashMap;

public class Player extends Actor{
    private HashMap<String, Attack> attacks;
    private Rectangle projectile;

    Player(Rectangle projectile) {
        attacks = new HashMap<>();
        this.projectile = projectile;
        attacks.put("Attack", new Attack(0.5, 1));
        attacks.put("Super attack", new Attack (3.0, 5));
        setHealth(1);
    }



    public void movePlayer(String button, Double time) {
        this.setVelocity(0,0);
        //setImage(frames.get("idle"));
        if (button.equals("LEFT")) {
            this.addVelocity(-200, 0);
        }
        else if (button.equals("RIGHT"))
            this.addVelocity(200,0);
        this.update(time);
    }



    public Projectile attack(Long time) {
        if (attacks.get("Attack").checkCooldown(time)) {
            Projectile bullet = createProjectile(-200,  projectile, attacks.get("Attack").getDamage());
            attacks.get("Attack").setWhenShot(time);
            return bullet;
        }
        else {
            return null;
        }
    }

    public Projectile superAttack(Long time) {
        if (attacks.get("Super attack").checkCooldown(time)) {
            Projectile bullet = createProjectile(-300, projectile, attacks.get("Super attack").getDamage());
            attacks.get("Super attack").setWhenShot(time);
            return bullet;
        }
        else {
            return null;
        }
    }

    @Override
    public String className() {
        return "Player";
    }
}
