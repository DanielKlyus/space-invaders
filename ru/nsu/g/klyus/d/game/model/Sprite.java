package ru.nsu.g.klyus.d.game.model;

public class Sprite {

    private SpriteImage image;
    private double velocityX;
    private double velocityY;
    private double leftXBoundary;
    private double upperYBoundary;
    private double rightXBoundary;
    private double downYBoundary;

    public double getUpperYBoundary() {
        return upperYBoundary;
    }

    public double getRightXBoundary() {
        return rightXBoundary;
    }

    public double getDownYBoundary() {
        return downYBoundary;
    }

    public SpriteImage getImage() {
        return image;
    }

    public double getLeftXBoundary() {
        return leftXBoundary;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public Sprite() {
        image = new SpriteImage();
        velocityX = 0;
        velocityY = 0;
        upperYBoundary = 0.0;
        downYBoundary = 600.0;
        rightXBoundary = 800.0;
        leftXBoundary = 0.0;
    }

    public void setXBoundaries(double left, double right) {
        leftXBoundary = left;
        rightXBoundary = right;
    }

    public void setYBoundaries(double upper, double down) {
        upperYBoundary = upper;
        downYBoundary = down;
    }



    public double getPositionX(){
        return image.getPositionX();
    }

    public double getPositionY(){
        return image.getPositionY();
    }

    public double getWidth(){
        return image.getWidth();
    }

    public double getHeight(){
        return image.getHeight();
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time) {
        image.setPosition(image.getPositionX() + velocityX * time,
                image.getPositionY() + velocityY * time);
        checkBoundaries(time);
    }

    public void checkBoundaries(double time){
        if (image.getPositionX() > rightXBoundary || image.getPositionX() < leftXBoundary)
            image.setPositionX(image.getPositionX() - velocityX * time);
        if (image.getPositionY() < upperYBoundary || image.getPositionY() > downYBoundary)
            image.setPositionY(image.getPositionY() - velocityY * time);
    }

    public String className(){
        return "Sprite";
    }

    public boolean intersects(Sprite s) {
        return this.getImage().getBoundary().intersects(s.getImage().getBoundary());
    }

    public String toString() {
        return " Position: [" + image.getPositionX() + "," + image.getPositionY() + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

    public void correctBoundaries(){
        setXBoundaries(getLeftXBoundary(), getRightXBoundary() - image.getWidth());
        setYBoundaries(getUpperYBoundary(), getDownYBoundary() - image.getHeight());
    }
}
