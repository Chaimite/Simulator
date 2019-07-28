package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class path implements Initializable
{
   @FXML
   private Rectangle vehicle;

   @FXML
   private Circle lane;

   @FXML
   private Circle green_center;


   @FXML
   private Button addLane;

   @FXML
   private Button removeLane;


   public void addLaneOnClick(ActionEvent event)
   {
      // This method will make the button add a track

      // Still needs to have a location(coordinates), and probably the
      // anchor pane
      System.out.println("Well you pressed the button");
      double innerRadius = 350;
      double outerRadius = 360;

      Circle outerCircle = new Circle(outerRadius, Color.rgb(255, 255, 255, 0));
      outerCircle.setStroke(Color.WHITE);

      Circle innerCircle = new Circle(innerRadius, Color.rgb(211, 211, 211, 1));
      innerCircle.setStroke(Color.BLUE);
      innerCircle.getStrokeDashArray().add(45d);


   }

   public void removeLaneOnClick()
   {
   // This method will make the button remove a track
   }

   @Override
   public void initialize(URL location, ResourceBundle resources)
   {

      // Establish path format and radius
      double radius = 110;
//      vehicle(radius);
      Circle circle = new Circle(radius);

      // Establish location of format location
      // circle.setCenterX(110);
      // circle.setCenterY(225);

      // Create transition type
      PathTransition transition = new PathTransition();
      // Establish what object to follow path
      transition.setNode(vehicle);
      //
      transition.setInterpolator(Interpolator.LINEAR);
      transition.setDuration(Duration.seconds(5));
      transition.setPath(circle);
      transition.setOrientation(
            PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      transition.setCycleCount(PathTransition.INDEFINITE);
      transition.play();

   }

   static void vehicle(double radius)
   {
      Circle vehiclePath = new Circle(radius);
      // Transition type for vehicle
      PathTransition transition = new PathTransition();
      transition.setDuration(Duration.seconds(5));
      transition.setPath(vehiclePath);
      transition.setOrientation(
            PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      transition.setCycleCount(PathTransition.INDEFINITE);
      transition.play();

   }

}