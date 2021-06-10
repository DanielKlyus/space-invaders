package ru.nsu.g.klyus.d.game.model;

public class Enemy extends Actor {
    private Integer prefVelocityX;
    private Integer prefVelocityY;
    private Attack attack;
    private Boolean isLowest;
    private Enemy upperNeighbour;
    private Rectangle projectile;
    Enemy(){
        isLowest = false;
        attack = new Attack(0.0, 1);
    }

    Enemy(Rectangle projectile){
        isLowest = false;
        attack = new Attack(0.0, 1);
        this.projectile = projectile;
    }

    Enemy(int hp) {
        isLowest = false;
        attack = new Attack(0.0, 1);
        this.setHealth(hp);
    }

    Enemy(int hp, int pvx, int pvy) {
        attack = new Attack(0.0, 1);
        this.setHealth(hp);
        this.prefVelocityX = pvx;
        this.prefVelocityY = pvy;
    }

    @Override
    public void checkBoundaries(double time) {
        if (this.getPositionX() > this.getRightXBoundary()) {
            this.getImage().setPosition(this.getPositionX() - this.getVelocityX() * time, this.getPositionY());
            this.setVelocity(0, prefVelocityY);
        }
        if (this.getPositionX() < this.getLeftXBoundary()) {
            this.getImage().setPosition(this.getPositionX() - this.getVelocityX() * time, this.getPositionY());
            this.setVelocity(0, -prefVelocityY);
        }
        if (this.getPositionY() < this.getUpperYBoundary()) {
            this.getImage().setPosition(this.getPositionX(), this.getPositionY() - this.getVelocityY() * time);
            this.setVelocity(prefVelocityX, 0);
        }
        if (this.getPositionY() > this.getDownYBoundary()) {
            this.getImage().setPosition(this.getPositionX(), this.getPositionY() - this.getVelocityY() * time);
            this.setVelocity(-prefVelocityX, 0);
        }
    }



    public Projectile attack() {
        Projectile bullet = createProjectile(100,  projectile, attack.getDamage());
        bullet.getImage().setPosition(this.getPositionX() + this.getWidth()/2 - bullet.getWidth()/2,
                this.getPositionY() + this.getHeight() + 1);
        return bullet;
    }

    @Override
    public String getClassName() {
        return "Enemy";
    }

    public void setPrefVelocityX(Integer prefVelocityX) {
        this.prefVelocityX = prefVelocityX;
    }

    public void setPrefVelocityY(Integer prefVelocityY) {
        this.prefVelocityY = prefVelocityY;
    }

    public Enemy getUpperNeighbour() {
        return upperNeighbour;
    }

    public void setUpperNeighbour(Enemy upperNeighbour) {
        this.upperNeighbour = upperNeighbour;
    }

    @Override
    public String className() {
        return "Enemy";
    }

    public Boolean isLowest() {
        return isLowest;
    }

    public void setLowest(Boolean lowest) {
        isLowest = lowest;
    }
}
