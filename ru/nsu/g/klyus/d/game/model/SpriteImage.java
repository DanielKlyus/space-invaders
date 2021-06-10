package ru.nsu.g.klyus.d.game.model;


public class SpriteImage {
    //private Image image;
    private double width;
    private double height;
    private double positionX;
    private double positionY;

    public SpriteImage(){
        positionX = 0;
        positionY = 0;
    }

    public SpriteImage(double width, double height){
        this.width = width;
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }


    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public Rectangle getBoundary() {
        return new Rectangle(width, height, getPositionX(), getPositionY());
    }
    public void setPositionX(double x) {
        positionX = x;
    }

    public void setPositionY(double y) {
        positionY = y;
    }
    public double getPositionX(){
        return positionX;
    }

    public double getPositionY(){
        return positionY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
