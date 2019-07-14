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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Controller implements Initializable
{
   @FXML
   private Rectangle vehicle;

   @FXML
   private Circle lane;

   @FXML
   private Circle trackCenter;

   @FXML
   private Button addLane;

   @FXML
   private Button removeLane;

   @FXML
   private AnchorPane track;
   
   

   public void addLaneOnClick(ActionEvent event) throws IOException
   {
      // This method will make the button add a track    
      // Still needs to have a location(coordinates), and probably the
      // anchor pane
      // Not sure if I should give it a return different from void
      double newLaneRadius = 20;

      // Data about coordinates to center objects according with the track
      // center
      double centerXCoordinate = trackCenter.getLayoutX();
      double centerYCoordinate = trackCenter.getLayoutY();

      // Data from initial track
     double innerRadius = lane.getRadius();
      double outerRadius = lane.getRadius() + newLaneRadius;
      
     
      
      Circle outerCircle = new Circle(centerXCoordinate,centerYCoordinate,outerRadius,Color.TRANSPARENT);
    
      outerCircle.setStroke(Color.rgb(211, 211, 211));
      outerCircle.setStrokeWidth(20);
      
      
      Circle innerCircle = new Circle(centerXCoordinate, centerYCoordinate,innerRadius, Color.TRANSPARENT);
      innerCircle.setStroke(Color.BLACK);
      innerCircle.getStrokeDashArray().add(45d);
      
      track.getChildren().addAll(outerCircle,innerCircle);
      Stack<Circle> test = new Stack<>();
      for (Circle circle : test)
      {
         test.add(circle);         
      }     
   }

   public void removeLaneOnClick(ActionEvent event)
   {
   // This method will make the button remove a track
//      track.getChildren().remove();
      Stack <Circle> test = new Stack<>();
      
//     test.pop(circle);
      
   }

    void vehicle(double xCoordinate, double yCoordinate,double radius)
   {
           // Transition type for vehicle
      PathTransition transition = new PathTransition();
      transition.setNode(vehicle);
      transition.setDuration(Duration.seconds(5));
      transition.setPath(this.lane);
      transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      transition.setCycleCount(PathTransition.INDEFINITE);
      transition.play();

   }
   @Override
   public void initialize(URL location, ResourceBundle resources)
   {
      
      
      
      
      // Establish path format and radius
      double radius = 110;
      vehicle(lane.getCenterX(), lane.getCenterY(), radius);

   }
}
