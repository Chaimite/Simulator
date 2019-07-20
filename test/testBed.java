package test;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class testBed extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        final Group group = new Group();
        final Scene scene = new Scene(group, 500, 500, Color.WHITE);
        stage.setScene(scene);
        stage.setTitle("Circles");
        stage.show();
        final Circle circle = new Circle(20, 20, 15);
        circle.setFill(Color.DARKRED);

        Circle path = new Circle(250,250,200);
        path.setFill(Color.WHITE);

        group.getChildren().add(path);
        group.getChildren().add(circle);
        final PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.seconds(2.0));
        pathTransition.setDelay(Duration.ZERO);
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition
                .setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        //pathTransition.setAutoReverse(true);
        pathTransition.play();
    }

    public static void main(final String[] arguments) {
        Application.launch(arguments);
    }
}