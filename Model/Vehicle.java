package model;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Vehicle implements Observer, Runnable 
{
   private final double width = 30;
   private final double height = 15;
   private Color fill = Color.GREENYELLOW;
   private Rectangle vehicle;

   private PathTransition transition = new PathTransition();
   private Circle path;
   
   @FXML
   StackPane trackPane;

   @FXML
   Circle baseCarriageway;
   
   private Bounds blockingBounds;
   
   public Vehicle()
   {
      vehicle = new Rectangle(width, height, fill);
      StackPane.setMargin(vehicle,new Insets(15, 0, 0, 30));
      setupVehicle();
   }
   
   @Override
   public void run()
   {
      // This will keep the vehicle always moving or wanting to move
      while(true)                      
      {
         startVehicle();
         
         while(!checkCollisionBetweenBlockingElementAndVehicle())
         {
            // do nothing
         }
         // collision detected coming out of the while loop
         
         stopVehicle();
         while(checkCollisionBetweenBlockingElementAndVehicle())
         {
            // do nothing
            // maybe later here see if the car can change lane.
         }
      }
   }
   
   public void setupVehicle()
   {
      transition.setNode(this.vehicle);
      transition.setDuration(Duration.seconds(5));
      transition.setPath(baseCarriageway);
      transition.setInterpolator(Interpolator.LINEAR);
      transition.setOrientation(
            PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      transition.setCycleCount(PathTransition.INDEFINITE);
   }
   
   @Override
   public void update(Bounds b)
   {
      blockingBounds = b;
      
   }
   
   private boolean checkCollisionBetweenBlockingElementAndVehicle()
   {

//      boolean collisionDetected = false;
//      for (Shape static_bloc : nodes)
//      {
//         if (static_bloc != block)
//         {
//
//            Shape intersect = Shape.intersect(vehicle, blockObject);
//            if (intersect.getBoundsInParent().getWidth() != -1)
//            {
//               collisionDetected = true;
//            }
//         }
//      }
      if (vehicle.getBoundsInParent().intersects(blockingBounds))
      {
         vehicle.setFill(Color.YELLOW);
         return true;
      }
      else
      {
         vehicle.setFill(Color.RED);
         return false;
      }
   }
   
   private void startVehicle()
   {
      transition.play();      
   }
   
   private void stopVehicle()
   {
      transition.pause();
   }
}
