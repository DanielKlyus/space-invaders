package ru.nsu.g.klyus.d.game.model;

public class Attack {
    private Double cooldown;
    private Integer damage;
    private Long whenShot;
    Attack(Double cooldown, Integer damage){
        this.cooldown = cooldown;
        this.damage = damage;
        whenShot = 0L;
    }

    public boolean checkCooldown(Long time) {
        return (time - whenShot) / 1e9 >= cooldown;
    }

    public Integer getDamage() {
        return damage;
    }

    public Double getCooldown() {
        return cooldown;
    }

    public Long getWhenShot() {
        return whenShot;
    }

    public void setWhenShot(Long whenShot) {
        this.whenShot = whenShot;
    }
}
