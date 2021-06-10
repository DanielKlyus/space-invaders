package ru.nsu.g.klyus.d.game.model;

public class Projectile extends Sprite {
    private Integer damage;
    private Boolean destroyed;
    Projectile(double velocity){
        super();
        destroyed = false;
        addVelocity(0, velocity);
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    @Override
    public void checkBoundaries(double time) {
        if (this.getPositionX() > this.getRightXBoundary()
                || this.getPositionX() < this.getLeftXBoundary()
                || this.getPositionY() < this.getUpperYBoundary()
                || this.getPositionY() > this.getDownYBoundary())
        destroyed = true;
    }

    public Integer getDamage() {
        return damage;
    }

    public Boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public String className() {
        return "Projectile";
    }
}
