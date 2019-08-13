package test;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PathTranExample extends Application {

   PathTransition transition;

   @Override
   public void start(Stage primaryStage) throws Exception {

       Group root = new Group();

       // create movable object
       Rectangle rect = new Rectangle(50, 50);
       rect.setStroke(Color.BLUE);
       rect.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));
       rect.relocate(100, 80);
       root.getChildren().add(rect);

       Label label = new Label("Click on scene to set destination");
       label.relocate(0, 0);
       root.getChildren().add(label);

       // init transition
       transition = new PathTransition();
       transition.setNode(rect);
       transition.setDuration(Duration.seconds(2));

       Scene scene = new Scene(root, 1024, 768);

       scene.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {

           @Override
           public void handle(Event event) {

               transition.stop();

               setPositionFixed( rect);

               double toX = ((MouseEvent) event).getX();
               double toY = ((MouseEvent) event).getY();

               Path path = new Path();
               path.getElements().add(new MoveToAbs(rect));
               path.getElements().add(new LineToAbs(rect, toX, toY));

               transition.setPath(path);
               transition.play();

           }

           // change layout to current position, reset translate
           private void setPositionFixed( Node node) {
               double x = rect.getLayoutX() + rect.getTranslateX();
               double y = rect.getLayoutY() + rect.getTranslateY();
               rect.relocate(x, y);
               rect.setTranslateX(0);
               rect.setTranslateY(0);
           }

       });

       primaryStage.setScene(scene);
       primaryStage.show();

   }

   public static class MoveToAbs extends MoveTo {

       public MoveToAbs(Node node) {
           super(node.getLayoutBounds().getWidth() / 2, node.getLayoutBounds().getHeight() / 2);
       }

       public MoveToAbs(Node node, double x, double y) {
           super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2, y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
       }

   }

   public static class LineToAbs extends LineTo {

       public LineToAbs(Node node, double x, double y) {
           super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2, y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
       }

   }

   public static void main(String[] args) {
       launch(args);
   }

}