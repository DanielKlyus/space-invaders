package ru.nsu.g.klyus.d.game.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import ru.nsu.g.klyus.d.game.controller.GameController;
import ru.nsu.g.klyus.d.game.model.*;

import java.util.HashMap;


public class View extends Application {

    private Scene scene;
    private GameModel model;
    private GraphicsContext gc;
    private Group root;
    private HashMap<String, Image> sprites;
    private GameController controller;

    public View() {
    }

    public void drawView() {
        gc.clearRect(0, 0, 800, 600);
        gc.drawImage(new Image(getClass().getResourceAsStream("/resources/space.png")), 0, 0);
        for (Sprite ent : model.getEntities()) {
            //ent.getImage().render(gc);
            gc.drawImage(sprites.get(ent.className()), ent.getPositionX(), ent.getPositionY());
        }
    }

    public Button addExitButton(double x, double y) {
        Button exit = new Button("");
        ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("/resources/exit.png")));
        exit.setGraphic(iv1);
        exit.setStyle(" -fx-focus-color: transparent; -fx-background-color: transparent; -fx-base: #000000");
        exit.setOnAction(e ->
                System.exit(1)
        );
        exit.setLayoutX(x);
        exit.setLayoutY(y);
        root.getChildren().add(exit);
        return exit;
    }

    public void victoryScreen() {
        gc.clearRect(0, 0, 800, 600);
        gc.drawImage(new Image(getClass().getResourceAsStream("/resources/space.png")), 0, 0);
        Label title = new Label("You win!");
        title.setLayoutX(315);
        title.setLayoutY(100);
        title.setTextFill(Color.rgb(250, 12, 91));
        title.setFont(Font.font("Comic Sans MS", 32));
        root.getChildren().add(title);
        addExitButton(300, 225);
    }

    public void deathScreen() {
        gc.clearRect(0, 0, 800, 600);
        gc.drawImage(new Image(getClass().getResourceAsStream("/resources/space.png")), 0, 0);
        Label title = new Label("You are dead!");
        title.setLayoutX(315);
        title.setLayoutY(300);
        title.setTextFill(Color.rgb(229, 12, 91));
        title.setFont(Font.font("Comic Sans MS", 32));
        root.getChildren().add(title);
        addExitButton(300, 425);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void run(View view) {
        model.setLastTimeValue(System.nanoTime());
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (model.getFlag() != -1 && model.getFlag() != -2) {
                    model.handle(now);
                    if (model.getFlag() == -1) {
                        controller.getInputs().clear();
                        controller.unbindControls(view.getScene());
                        view.deathScreen();
                    } else if (model.getFlag() == -2) {
                        controller.getInputs().clear();
                        controller.unbindControls(view.getScene());
                        view.victoryScreen();
                    }
                }
                view.drawView();

            }
        }.start();
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("space invaders v0.01");

        sprites = new HashMap<>();
        sprites.put("Player", new Image(getClass().getResourceAsStream("/resources/player.png")));
        sprites.put("Enemy", new Image(getClass().getResourceAsStream("/resources/enemy.png")));
        sprites.put("Boss", new Image(getClass().getResourceAsStream("/resources/boss.png")));
        sprites.put("Projectile", new Image(getClass().getResourceAsStream("/resources/bullet.png")));

        root = new Group();
        scene = new Scene(root);
        theStage.setScene(scene);
        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        controller = new GameController(this.getScene());

        HashMap<String, Rectangle> sizes = new HashMap<>();
        sizes.put("Player", new Rectangle(sprites.get("Player").getWidth(), sprites.get("Player").getHeight()));
        sizes.put("Enemy", new Rectangle(sprites.get("Enemy").getWidth(), sprites.get("Enemy").getHeight()));
        sizes.put("Boss", new Rectangle(sprites.get("Boss").getWidth(), sprites.get("Boss").getHeight()));
        sizes.put("Projectile", new Rectangle(sprites.get("Projectile").getWidth(), sprites.get("Projectile").getHeight()));
        model = new GameModel(sizes, canvas.getWidth(), canvas.getHeight(), controller);


        gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image(getClass().getResourceAsStream("/resources/space.png")), 0, 0);


        Label title = new Label("space invaders");
        title.setLayoutX(300);
        title.setLayoutY(100);
        title.setTextFill(Color.rgb(229, 12, 91));
        title.setFont(Font.font("Comic Sans MS", 32));
        root.getChildren().add(title);

        Label controls = new Label("Controls:\n" +
                "left and right arrows to move;\n" +
                "space to shoot;\n" +
                "c for super attack\n");
        controls.setLayoutX(100);
        controls.setLayoutY(225);
        controls.setTextFill(Color.rgb(229, 12, 91));
        root.getChildren().add(controls);

        Button exit = addExitButton(300, 325);

        Button newGame = new Button("");
        ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/resources/ng.png")));
        newGame.setGraphic(iv);
        newGame.setBorder(null);
        iv.setFitHeight(50);
        iv.setFitWidth(200);
        newGame.setStyle(" -fx-focus-color: transparent; -fx-background-color: transparent; -fx-base: #000000");
        newGame.setOnAction(e -> {
            run(this);
            root.getChildren().remove(newGame);
            root.getChildren().remove(exit);
            root.getChildren().remove(title);
            root.getChildren().remove(controls);
        });
        newGame.setLayoutX(300);
        newGame.setLayoutY(225);
        root.getChildren().add(newGame);

        theStage.setResizable(false);
        theStage.show();
    }

    public Scene getScene() {
        return scene;
    }
}

