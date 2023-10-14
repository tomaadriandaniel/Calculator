package ro.ctrl.javafx.calculator;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class JavaFXCalculator extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Calculator.fxml"));
        Scene scene = new Scene(root, 400,400);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.setX(Screen.getScreens().get(0).getBounds().getMinX() + 200);
        primaryStage.setY(Screen.getScreens().get(0).getBounds().getMinY() + 200);
        primaryStage.show();

    }
    
}
