package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller {
    public GridPane gp;
    private Model model;

    public Controller() {
        model = new Model();
    }

    public void initialize() {
        gp.setHgap(0);
        gp.setVgap(0);
        gp.setPadding(new Insets(0, 0, 0, 0));

        new Thread(() -> {
            update();
            Platform.runLater(() -> {
                gp.getScene().setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case UP:
                            model.move(-1, 0);
                            update();
                            break;
                        case DOWN:
                            model.move(1, 0);
                            update();
                            break;
                        case LEFT:
                            model.move(0, -1);
                            update();
                            break;
                        case RIGHT:
                            model.move(0, 1);
                            update();
                            break;
                    }
                });
            });
        }).start();
    }

    private void update() {
        gp.getChildren().clear();

        if (model.completed()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText("Congratulations, you've completed the level.");
            alert.setContentText("All the lamps have succesfully been activated.");

            alert.showAndWait();
            ((Stage) gp.getScene().getWindow()).close();
        }

        for (int i = 0; i < model.getSpelbord().size(); i++) {
            for (int j = 0; j < model.getSpelbord().get(i).size(); j++) {
                gp.add(model.getSpelbord().get(i).get(j), j, i);
            }
        }
    }
}
