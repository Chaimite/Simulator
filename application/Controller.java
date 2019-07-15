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

      // Data about coordinates to center objects according with the track
      // center
      double centerXCoordinate = trackCenter.getLayoutX();
      double centerYCoordinate = trackCenter.getLayoutY();

      // Data from initial track
      // Need to fix this plus 12 and plus 22
      double innerRadius = trackCenter.getRadius()+ 12;
      double outerRadius = trackCenter.getRadius() + 22;
      
     
      // The  new lane
      Circle outerCircle = new Circle(centerXCoordinate,centerYCoordinate,outerRadius,Color.TRANSPARENT);    
      outerCircle.setStroke(Color.rgb(211, 211, 211));
      outerCircle.setStrokeWidth(25);
      
      // The inner road marks
      Circle innerCircle = new Circle(centerXCoordinate, centerYCoordinate,innerRadius, Color.TRANSPARENT);
      innerCircle.setStroke(Color.BLACK);
      innerCircle.getStrokeDashArray().add(10d);
      
      track.getChildren().addAll(outerCircle,innerCircle);
       
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
      transition.setPath(this.trackCenter);
      transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      transition.setCycleCount(PathTransition.INDEFINITE);
      transition.play();

   }
   @Override
   public void initialize(URL location, ResourceBundle resources)
   {
     
      
      
      
      // Establish path format and radius
      double radius = 110;
      vehicle(trackCenter.getCenterX(), trackCenter.getCenterY(), radius);

   }
}
