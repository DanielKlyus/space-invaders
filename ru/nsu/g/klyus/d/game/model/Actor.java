package ru.nsu.g.klyus.d.game.model;

public class Actor extends Sprite implements Hittable {
    private Integer health;
    @Override
    public void getHit(Projectile projectile) {
        setHealth(getHealth() - projectile.getDamage());
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Projectile attack() {
        return null;
    }

    public String getClassName(){
        return "Actor";
    }

    protected Projectile createProjectile (Integer velocity, Rectangle sprite, Integer damage){
        Projectile bullet = new Projectile(velocity);
        //bullet.getImage().setImage(sprite);
        bullet.getImage().setHeight(sprite.getHeight());
        bullet.getImage().setWidth(sprite.getWidth());
        bullet.setDamage(damage);
        bullet.getImage().setPosition(this.getPositionX() + this.getWidth()/2 - bullet.getWidth()/2,
                this.getPositionY() - 2 - bullet.getHeight());
        return bullet;
    }

}
