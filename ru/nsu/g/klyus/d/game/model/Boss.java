package ru.nsu.g.klyus.d.game.model;

public class Boss extends Enemy {
    private Rectangle projectile;
    Boss(Rectangle projectile){
        this.projectile = projectile;
    }

    @Override
    public Projectile attack() {
        Projectile bullet = createProjectile(200,  projectile, 1);
        Integer pos = (int)(Math.random() * 15);
        bullet.getImage().setPosition(this.getPositionX() + this.getWidth() / 16 * (pos + 1) - bullet.getWidth()/2,
                this.getPositionY() + this.getHeight() + 1);
        return bullet;
    }

    @Override
    public String className() {
        return "Boss";
    }


    @Override
    public void checkBoundaries(double time) {
    }
}
