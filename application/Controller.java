package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Controller implements Initializable
{
   @FXML
   private Rectangle vehicle;

   @FXML
   private Circle trackCenter;

   @FXML
   private Button addLane;

   @FXML
   private Button removeLane;

   @FXML
   private StackPane track;
   
   @FXML
   private StackPane vehicleStackPane;
   
   
   private PathTransition transition;
   private double centerXCoordinate;
   private double centerYCoordinate;
   
   private Circle outerCircle;
   private double lastElementRadius;
   private Circle lastElementInTrack;
//   private double elementRadius;
   private double newRadius;
   private Stack<Circle> trackStack = new Stack<>();
   private int elementsInTrack;

   public void addLaneOnClick(ActionEvent event) throws IOException
   {
      // This method will make the button add a track
      
      // Data from initial track
      lastElementInTrack = trackStack.peek();
      centerXCoordinate = lastElementInTrack.getLayoutX();
      centerYCoordinate = lastElementInTrack.getLayoutY();
      
      lastElementRadius = lastElementInTrack.getRadius();
      
      newRadius = lastElementRadius + 22;
      System.out.println("outer radius: " + lastElementRadius);
      
      System.out.println("new radius " + newRadius);

      // The new lane
      outerCircle = new Circle(centerXCoordinate, centerYCoordinate,newRadius, Color.TRANSPARENT);
      outerCircle.setStroke(Color.rgb(211, 211, 211));
      outerCircle.setStrokeWidth(25);

      // The inner road marks
//      Circle innerCircle = new Circle(centerXCoordinate, centerYCoordinate,innerRadius, Color.TRANSPARENT);
//      innerCircle.setStroke(Color.BLACK);
//      innerCircle.getStrokeDashArray().add(10d);

      // Placing the circle in the stack
      track.getChildren().addAll(trackStack.push(outerCircle));
   }

   public void removeLaneOnClick(ActionEvent event)
   {
      // This method will make the button remove a track 
      elementsInTrack = track.getChildren().size();
      if (elementsInTrack >= 3)
         track.getChildren().remove(trackStack.pop());       
   }

   void path(double xCoordinate, double yCoordinate, double radius)
   {
      // Transition type for vehicle
      transition = new PathTransition();
      transition.setNode(vehicle);
      transition.setDuration(Duration.seconds(5));
      transition.setPath(this.trackCenter);
      transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      transition.setCycleCount(PathTransition.INDEFINITE);
      transition.play();

   }

   @Override
   public void initialize(URL location, ResourceBundle resources)
   {
      trackStack.push(trackCenter);
      // Establish path format and radius
      double radius = 110;
      path(trackCenter.getCenterX(), trackCenter.getCenterY(), radius);
//      vehicleStackPane.setBackground(rgb);
     

   }
}
