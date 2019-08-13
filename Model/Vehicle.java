package model;

import javafx.animation.Animation.Status;
import javafx.animation.AnimationTimer;

import java.util.Optional;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Vehicle implements Observer, Runnable
{
   private final double width = 30;
   private final double height = 15;
   private Color fill = Color.RED;
   private Rectangle vehicle;

   private PathTransition transition;

   private Bounds blockingBounds;
   private boolean accidentOnTheRoad;
   private Circle blockingObject;
   private Bounds vehicleBounds;
   private StackPane trackPane;
   private final double probeSize = 20;
   private Lane baseLane;
   private boolean flag;
   Optional<Node> result = null;
   private Pane vehiclePane;

   public Vehicle(StackPane trackPane, Lane baseLane,
         Circle blockingObject, Pane vehiclePane)
   {
      this.baseLane = baseLane;
      accidentOnTheRoad = false;
      this.blockingObject = blockingObject;
      vehicle = new Rectangle(width, height, fill);
      StackPane.setMargin(vehicle, new Insets(15, 0, 0, 30));
//      trackPane.getChildren().add(vehicle);
      this.trackPane = trackPane;
      setupVehicle(vehicle, baseLane.getAsphalt());
      flag = false;
      this.vehiclePane = vehiclePane;
//      Bounds trackPaneBounds = trackPane.g;//getBoundsInLocal, getBoundsInParent
//      double trackPaneCenterX = (trackPaneBounds.getMinX() + trackPaneBounds.getMaxX()) / 2.0;
//      double trackPaneCenterY = (trackPaneBounds.getMinY() + trackPaneBounds.getMaxY()) / 2.0;
      vehiclePane.setManaged(true);
     
      vehicle.toFront();
   }

   @Override
   public void run()
   {
      // This will keep the vehicle always moving or wanting to move
      startVehicle();
      while (true)
      {
         
         while (!accidentOnTheRoad)
         {
            // always check for collision against the front vehicle
            if (isVehicleStopped())
            {
               startVehicle();
            }

         }

         // when there is an accident on the road, calculate the distance and if
         // the distance is less than the specified then it should pause.
         double distance = calculateDistanceBetweenAccidentAndVehicle();
         
         if (distance < 40.0)
         {
            // pauseVehicle();
//            double xOrigin = vehicle.getX();
//            double yOrigin = vehicle.getY();
            stopVehicle();
            try
            {
               Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }
            baseLane.removeObserver(this);
            baseLane.getRightLane().addObserver(this);

            Platform.runLater(new Runnable()
            {
               public void run()
               {
                  Lane right = baseLane.getRightLane();
                  changeLane(right.getAsphalt());
               }

            });

            // pauseVehicle();

            // setupVehicle(vehicle, testCircle);
            break;

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

   private boolean isFrontBlocked()
   {     
      double probeFrontLocationX = vehicle.getX() + probeSize;
      double probeFrontLocationY = vehicle.getY() + probeSize;
      result = vehiclePane.getChildren().stream()
            .filter(n -> n.contains(probeFrontLocationX, probeFrontLocationY))
            .findAny();
      return flag = (result != null) ? true : false;
   }

   // private boolean isRightSideBlocked(Pane root) {
   // double probeSize = 10;
   // boolean flag = false;
   // Optional<Node> result;
   //
   // double probeFrontLocationX = vehicle.getX() - probeSize;
   //
   // root.getChildren().stream().filter(n -> n.getX() ==
   // probeFrontLocationX).findAny());
   // return flag = (result != null) ? true : false;
   // }
   //
   // private boolean isLeftSideBlocked(Pane root) {
   // double probeSize = 10;
   // boolean flag = false;
   // Optional<Node> result;
   //
   // double probeFrontLocationX = vehicle.getX() + probeSize;
   //
   // result = root.getChildren().stream().filter(n -> n.getX() ==
   // probeFrontLocationX).findAny();
   //
   // return flag = (result != null) ? true : false;
   // }

   private void changeLane(Circle lane)
   {
      // get actual position
      Duration actualPosition = transition.getDuration();
      
      // change path of the transition
      transition.setPath(lane);
      
      // update the new position for new lane
      transition.setDuration(actualPosition);
      
      transition.play();
      vehicle.toFront();
      
      
//      
//      Bounds vB = vehicle.getBoundsInParent();
//      double vCenterX = (vB.getMinX() + vB.getMaxX()) / 2.0;
//      double vCenterY = (vB.getMinY() + vB.getMaxY()) / 2.0;
//    
//      // localToParent,localToScene, parentToLocal,localToScreen, sceneToLocal
//      Line laneChange = new Line(0, 0, 300, 0);
////      Line laneChange = new Line(vCenterX, vCenterY, vCenterX + 300, vCenterY);
//      laneChange.getStartX();
//      StackPane.setMargin(laneChange, new Insets(0, 0, 0, 150));
//      trackPane.getChildren().add(laneChange);
//      transition.setNode(vehicle);
//      transition.setDuration(Duration.seconds(10));
//      transition.setPath(laneChange);
//      transition.setCycleCount(1);
//      transition.play();
//      
//      transition.jumpTo(Duration.seconds(6));
   }

   private double calculateDistanceBetweenAccidentAndVehicle()
   {

      Bounds vB = vehicle.getBoundsInParent();
      double vCenterX = (vB.getMinX() + vB.getMaxX()) / 2.0;
      double vCenterY = (vB.getMinY() + vB.getMaxY()) / 2.0;
      System.out.println(
            "vehicle Minx " + vB.getMinX() + " vehicle Miny " + vB.getMinY());
      System.out.println(
            "vehicle MaxX " + vB.getMaxX() + " vehicle MaxY " + vB.getMaxY());

      Bounds bB = blockingObject.getBoundsInParent();
      double bCenterX = (bB.getMinX() + bB.getMaxX()) / 2.0;
      double bCenterY = (bB.getMinY() + bB.getMaxY()) / 2.0;

      System.out.println(
            "Block Minx " + bB.getMinX() + " block Miny " + bB.getMinY());
      System.out.println(
            "Block MaxX " + bB.getMaxX() + " Block MaxY " + bB.getMaxY());
      System.out.println(
            "Block CenterX " + bCenterX + " Block CenterY " + bCenterY);

      Point2D v = new Point2D(vCenterX, vCenterY);
      Point2D b = new Point2D(bCenterX, bCenterY);

      double distance = v.distance(b);

      System.out.println("distance : " + distance);
      System.out.println();
      System.out.println(
            "---------------------------------------------------------");
      System.out.println();

      return distance;

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

   private void startVehicle()
   {
      transition.play();
   }

   private void pauseVehicle()
   {
      transition.pause();
   }

   private void stopVehicle()
   {
      transition.stop();
   }

   private boolean isVehicleStopped()
   {
      return transition.getStatus() == Status.PAUSED;
   }
}
