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
         if (distance < 20.0)
         {
            stopVehicle();
         }

         try
         {
            Thread.sleep(100);
         }
         catch (InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         // look on the other lane to see if it can move.
      }
   }

   private double calculateDistanceBetweenAccidentAndVehicle()
   {
      
      Bounds vB = vehicle.getBoundsInParent();
      double vCenterX = (vB.getMinX() + vB.getMaxX()) / 2.0;
      System.out.println("vehicle Minx " + vB.getMinX() + " vehicle Miny " + vB.getMinY());
      System.out.println("vehicle MaxX " + vB.getMaxX() + " vehicle MaxY " + vB.getMaxY());
      
      Bounds bB = blockingObject.getBoundsInParent();
      double bCenterX = (bB.getMinX() + bB.getMaxX()) / 2.0;
      double bCenterY = (bB.getMinY() + bB.getMaxY()) / 2.0;
      
      System.out.println("Block Minx " + bB.getMinX() + " block Miny " + bB.getMinY());
      System.out.println("Block MaxX " + bB.getMaxX() + " Block MaxY " + bB.getMaxY());
      System.out.println("Block CenterX " + bCenterX + " Block CenterY " + bCenterY);
      
      
      Point2D v = new Point2D(vCenterX, vB.getMaxY());
      Point2D b = new Point2D(bCenterX, bCenterY);
      
      double distance =  v.distance(b);
      
      
      System.out.println("distance : " + distance);
      System.out.println();
      System.out.println("---------------------------------------------------------");
      System.out.println();
      
      return distance;

   }
   
   
//   
//   private double getGlobalX(Node node) {
//      if (node == null) {
//          return 0.0;
//      }
//      double parentGlobalX = getGlobalX(node.getParent());
//      return node.getLayoutX() - parentGlobalX;
//  }
//
//  private double getGlobalY(Node node) {
//      if (node == null) {
//          return 0.0;
//      }
//      double parentGlobalY = getGlobalY(node.getParent());
//      return parentGlobalY - node.getLayoutY();
//  }

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
