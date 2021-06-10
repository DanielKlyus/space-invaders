package ru.nsu.g.klyus.d.game.model;

public class Rectangle {
    private double width;
    private double height;
    private double positionX;
    private double positionY;
    public Rectangle(double width, double height, double positionX, double positionY){
        this.width = width;
        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Rectangle(double width, double height){
        this.width = width;
        this.height = height;
        this.positionX = 0;
        this.positionY = 0;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public boolean intersects (Rectangle another) {
        if (this.getPositionX() > another.getPositionX() + another.getWidth()
                || another.getPositionX() > this.getPositionX() + this.getWidth()) {
            return false;
        }


        if (this.getPositionY() > another.getPositionY() + another.getHeight()
                || another.getPositionY() > this.getPositionY() + this.getHeight()) {
            return false;
        }
        return true;
    }
}
