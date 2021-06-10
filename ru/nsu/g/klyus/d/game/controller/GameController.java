package ru.nsu.g.klyus.d.game.controller;

import javafx.scene.Scene;

import java.util.ArrayList;

public class GameController {
    private ArrayList<String> input;
    GameController(){}

    public GameController(Scene scene) {
        input = new ArrayList<>();
        scene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (!input.contains(code)){
                input.add(code);
            }
        });
        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });
    }

    public void unbindControls(Scene scene) {
        scene.setOnKeyPressed(e -> {});
        scene.setOnKeyReleased(e -> {});
    }

    public ArrayList<String> getInputs(){
        return input;
    }






}
