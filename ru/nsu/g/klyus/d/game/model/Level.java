package ru.nsu.g.klyus.d.game.model;

import java.io.InputStream;
import java.util.Properties;

public class Level {
    private String levelType;
    private Integer rows;
    private Integer columns;
    private Integer health;
    private Integer velocity;

    Level(){}

    Level(String filename){
        try (InputStream in = Level.class.getResourceAsStream(filename)) {
        Properties properties = new Properties();
        properties.load(in);
        levelType = properties.getProperty("EnemyType");
        health = Integer.parseInt(properties.getProperty("hp"));
        if (levelType.equals("static") || levelType.equals("moving")) {
            rows = Integer.parseInt(properties.getProperty("rows"));
            columns = Integer.parseInt(properties.getProperty("columns"));
        }
        if(levelType.equals("moving")){
            velocity = Integer.parseInt(properties.getProperty("velocity"));
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getLevelType() {
        return levelType;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getColumns() {
        return columns;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getVelocity() {
        return velocity;
    }

}
