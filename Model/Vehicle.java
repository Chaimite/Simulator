package model;

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
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
   

   private PathTransition transition;
   
   
//
//   @FXML
//   Circle baseCarriageway;
   
   private Bounds blockingBounds;
   private Bounds vehicleBounds;
   private boolean accidentOnTheRoad;
   private Circle blockingObject;
   
   public Vehicle(StackPane trackPane, Circle baseCarriageway, Circle blockingObject)
   {
      accidentOnTheRoad = false;
      this.blockingObject = blockingObject;
      vehicle = new Rectangle(width, height, fill);
      StackPane.setMargin(vehicle,new Insets(15, 0, 0, 30));
      trackPane.getChildren().add(vehicle);
      vehicle.toFront();
      setupVehicle(vehicle, baseCarriageway);
      
   }
   
   @Override
   public void run()
   {
      // This will keep the vehicle always moving or wanting to move
      startVehicle();
      while(true) {
         
         while(!accidentOnTheRoad)
         {
            // always check for collision against the front vehicle
            if(isVehicleStopped())
            {
               startVehicle();
            }
            
         }
         // when there is an accident on the road, calculate the distance and if the distance is less than the specified then it should pause.
         double distance = calculateDistanceBetweenAccidentAndVehicle();
         System.out.println(distance);
         if(distance < 2.0)
         {
            stopVehicle();
         }
         
         // look on the other lane to see if it can move.
         
         
      }
//      while(true)                      
//      {
//         
//         while(!checkCollisionBetweenBlockingElementAndVehicle() && !checkCollisionBetweenVehicles())
//         {
//            // do nothing
//         }
//         // collision detected coming out of the while loop
//         // check side collision with rectangle/vehicle, in the y axis for side vehicle
//         checkCollisionBetweenVehicles(); // for the sides, also need to verify if it has lanes on the side
//         stopVehicle();
//         
//         
//         while(checkCollisionBetweenBlockingElementAndVehicle())
//         {
//            // check shape collision with rectangle/vehicle type
//            // do nothing
//            // maybe later here see if the car can change lane.
//         }
//      }
      
//      while(true)
//      {
//         startVehicle();
//         if(!isFrontBlockDetected()) {
//            // Need to have this being detect
//            return;
//         }
//         else if(isLaneOnRight) {
//            if(!isRightSideBlockDetected()) {
//               
//            }
//         }
//      }
   }

   private double calculateDistanceBetweenAccidentAndVehicle()
   {
//      Point2D v = new Point2D(vehicle.getX(), vehicle.getY());
//      Point2D be = new Point2D(blockingObject.getCenterX(), blockingObject.getCenterY());
//      v.distan
      Bounds boundsInV = vehicle.localToScene(vehicle.getBoundsInLocal());
      Point2D ff = vehicle.localToScene(vehicle.getX(), vehicle.getY());
      double vX = getGlobalX(vehicle);
      double vY = getGlobalY(vehicle);
      double bX = getGlobalX(blockingObject);
      double bY = getGlobalY(blockingObject);
      
      Point2D v = new Point2D(ff.getX(), ff.getX());
      System.out.println("vehicle x " + ff.getX() + " vehicle y " + ff.getY());
      Point2D b = new Point2D(blockingObject.getCenterX(), blockingObject.getCenterY());
      
      return v.distance(b);
      
   }
   
   private double getGlobalX(Node node) {
      if (node == null) {
          return 0.0;
      }
      double parentGlobalX = getGlobalX(node.getParent());
      return node.getLayoutX() - parentGlobalX;
  }

  private double getGlobalY(Node node) {
      if (node == null) {
          return 0.0;
      }
      double parentGlobalY = getGlobalY(node.getParent());
      return parentGlobalY - node.getLayoutY();
  }

   public void setupVehicle(Rectangle vehicle, Circle baseCarriageway)
   {
      transition = new PathTransition();
      transition.setNode(vehicle);
      transition.setDuration(Duration.seconds(5));
      transition.setPath(baseCarriageway);
      transition.setInterpolator(Interpolator.LINEAR);
      transition.setOrientation(
            PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      transition.setCycleCount(PathTransition.INDEFINITE);
      
   }
   
   @Override
   public void update(boolean collisionDetected)
   {
      accidentOnTheRoad = collisionDetected;
      
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
      if (this.vehicle.getBoundsInParent().intersects(vehicleBounds))
      {
         this.vehicle.setFill(Color.YELLOW);
         return true;
      }
      else
      {
         this.vehicle.setFill(Color.RED);
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
   
   private boolean isVehicleStopped()
   {
      return transition.getStatus() == Status.PAUSED;
   }
}
