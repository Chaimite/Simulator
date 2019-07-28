package test;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CircleTransitions extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
//        final Group group = new Group();
        Pane root = new Pane();
        final Scene scene = new Scene(root, 500, 500, Color.WHITE);
        stage.setScene(scene);
        stage.setTitle("Circles");
        stage.show();
        
        
        
         
        
        //Setting title to the Stage 
        stage.setTitle("Path transition example"); 
        Circle circle = new Circle(150, 150, 100, Color.YELLOW);
        Rectangle vehicle = new Rectangle(20, 20);
       
//        Group group = new Group(circle); 
        stage.setScene(scene); 
        PathTransition transition = new PathTransition();
        // Establish what object to follow path
        transition.setNode(vehicle);
        //
        transition.setDuration(Duration.seconds(5));
        transition.setPath(circle);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setOrientation(
              PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();
        
        root.getChildren().add(circle);
        root.getChildren().add(vehicle);
       
    }

    public static void main(final String[] arguments) {
        Application.launch(arguments);
    }
}
