package navigator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Navigator extends Application {

    @Override
    public void start(Stage window) {
        window.setTitle("Navigator");

        // Canvas
        int width = 1100;
        int height = 700;
        Canvas drawing = new Canvas(width, height);
        GraphicsContext gc = drawing.getGraphicsContext2D();

        Image kuvatiedosto = new Image("file:Helsinki1100x700.png");
        gc.drawImage(kuvatiedosto, 0, 0);

        // Radio Buttons
        VBox radioButtonGroupArea = new VBox();
        ToggleGroup radioButtonGroup = new ToggleGroup();

        RadioButton radioButtonDraw = new RadioButton("Draw");
        radioButtonDraw.setToggleGroup(radioButtonGroup);
        radioButtonDraw.setSelected(true);

        RadioButton radioButtonNavigator = new RadioButton("Navigator");
        radioButtonNavigator.setToggleGroup(radioButtonGroup);
        radioButtonNavigator.setSelected(false);

        radioButtonGroupArea.getChildren().add(radioButtonDraw);
        radioButtonGroupArea.getChildren().add(radioButtonNavigator);

        // Button
        Button writeFileButton = new Button("Write file");

        VBox radioButtonAndButtonArea = new VBox();
        radioButtonAndButtonArea.getChildren().add(radioButtonGroupArea);
        radioButtonAndButtonArea.getChildren().add(writeFileButton);

        // Draw roads
        NavigatorInterface navigatorInterface = new NavigatorInterface(gc, width, height);
        navigatorInterface.start();
        navigatorInterface.drawRoads();
        navigatorInterface.drawLocations();

        // Layout
        BorderPane layout = new BorderPane();
        layout.setCenter(drawing);
        layout.setRight(radioButtonAndButtonArea);

        writeFileButton.setOnAction((event) -> {
            navigatorInterface.writeFile();
        });

        drawing.setOnMouseClicked((event) -> {
            if (radioButtonDraw.isSelected() == true) {
                int clickedScreenX = (int) event.getX();
                int clickedScreenY = (int) event.getY();

                navigatorInterface.defineNewStageStartOrEnd(clickedScreenX, clickedScreenY);

                gc.clearRect(0, 0, width, height);
                gc.drawImage(kuvatiedosto, 0, 0);
                navigatorInterface.drawRoads();
                navigatorInterface.drawLocations();

            }
            if (radioButtonNavigator.isSelected() == true) {
                int clickedScreenX = (int) event.getX();
                int clickedScreenY = (int) event.getY();

                navigatorInterface.defineStartOrEndLocation(clickedScreenX, clickedScreenY);

                gc.clearRect(0, 0, width, height);
                gc.drawImage(kuvatiedosto, 0, 0);
                navigatorInterface.drawRoads();
                navigatorInterface.drawLocations();
                navigatorInterface.defineNeighbors();
                navigatorInterface.drawFastestPath();
            }
        });

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(Navigator.class);
    }

}
