package model;

import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Vehicle extends AnimationTimer
{

   private double speed = 25;
   private Lane currentLane;
   private double radius;
   private double angle = 1.57;

   private Circle vehicle;
   private Pane vehiclePane;
   private double vehicleLane;
   private long lastTimerCall = System.nanoTime();
   private long One_Sec = 10000000;
   private boolean collision;
   Circle frontSensor;
   Circle rightSensor;
   Circle leftSensor;

  
   public Vehicle(Lane lane,
         Pane vehiclePane)
   {
      super();
      vehicle = new Circle(10, Color.RED);
      frontSensor = new Circle(1, Color.BLUE);
      rightSensor = new Circle(2, Color.YELLOW);
      leftSensor = new Circle(2, Color.TRANSPARENT);
      this.currentLane = lane;
      this.vehiclePane = vehiclePane;
      addVehicle();
      vehicleLane = lane.getAsphalt().getRadius();
      double x = ((vehiclePane.getBoundsInParent().getMaxX()
            + vehiclePane.getBoundsInParent().getMinX()) / 4.0) + 22;
      double y = ((vehiclePane.getBoundsInParent().getMaxY()
            + vehiclePane.getBoundsInParent().getMinY()) / 4.0) - 2.5;

      vehicle.setLayoutX(x);
      vehicle.setLayoutY(y);

      frontSensor.setLayoutX(x);
      frontSensor.setLayoutY(y);
      
      rightSensor.setLayoutX(x);
      rightSensor.setLayoutY(y);
      
      leftSensor.setLayoutX(x);
      leftSensor.setLayoutY(y);

   }

   public void addVehicle() {
      vehiclePane.getChildren().addAll(vehicle, frontSensor, rightSensor, leftSensor);
   }
   
   public void removeVehicle() {
      this.stop();
      boolean result = vehiclePane.getChildren().removeAll(vehicle, frontSensor, rightSensor, leftSensor);
      System.out.println(result);
   }
   
   
   public double getX()
   {
      return vehicle.getCenterX();
   }

   public void setX(double x)
   {
      vehicle.setCenterX(x);
   }

   public double getY()
   {
      return vehicle.getCenterY();
   }

   public void setY(double y)
   {
      vehicle.setCenterY(y);
   }

   public double getSpeed()
   {
      return speed;
   }

   public void setSpeed(double speed)
   {
      this.speed = speed;
   }

   public void moveInCircle(double radius)
   {
      double newX = currentLane.getAsphalt().getCenterX()
            + (radius * Math.cos(angle));
      double newY = currentLane.getAsphalt().getCenterY()
            + (radius * Math.sin(angle));

      vehicle.setTranslateX(newX);
      vehicle.setTranslateY(newY);

      frontSensor(radius);
   }

   public void frontSensor(double radius)
   {
      double frontSensorLocationX = currentLane.getAsphalt().getCenterX()
            + (radius * Math.cos(angle + 0.15));
      double frontSensorLocationY = currentLane.getAsphalt().getCenterY()
            + (radius * Math.sin(angle + 0.15));

      frontSensor.setTranslateX(frontSensorLocationX);
      frontSensor.setTranslateY(frontSensorLocationY);
   }

   // needs to look to the next right currentLane
   public void rightSensor(double radius)
   {
      double rightSensorLocationX = currentLane.getRightLane().getAsphalt().getCenterX()
            + (radius * Math.cos(angle));
      double rightSensorLocationY = currentLane.getRightLane().getAsphalt().getCenterY()
            + (radius * Math.sin(angle));

      rightSensor.setTranslateX(rightSensorLocationX);
      rightSensor.setTranslateY(rightSensorLocationY);
   }

   // needs to look to the next left currentLane and
   public void leftSensor(double radius)
   {
      double leftSensorLocationX = currentLane.getLeftLane().getAsphalt().getCenterX()
            + (radius * Math.cos(angle));
      double leftSensorLocationY = currentLane.getLeftLane().getAsphalt().getCenterY()
            + (radius * Math.sin(angle));

      leftSensor.setTranslateX(leftSensorLocationX);
      leftSensor.setTranslateY(leftSensorLocationY);
   }

   private boolean isFrontBlocked()
   {
      collision = false;
      collision = collisionSensed(frontSensor);
      Circle block = currentLane.getBlockingObject();
      if (block != null)
      {
         Bounds blockBounds = block.localToScreen(block.getBoundsInLocal());

         if (frontSensor.localToScreen(frontSensor.getBoundsInLocal())
               .intersects(blockBounds))
         {
            collision = true;
         }
      }
      return collision;
   }

   private boolean collisionSensed(Circle sensor)
   {
      Optional<Node> result =
       (vehiclePane.getChildren().stream()
            .filter(n -> n instanceof Circle 
                  && ((Circle)n).getRadius() > 2
                  && n != vehicle 
                  && n.getLayoutX() == sensor.getLayoutX()
                  && n.getLayoutY() == sensor.getLayoutY())
            .findAny());
      return result.isPresent();
      
   }

   private boolean isRightSideBlocked()
   {
      boolean collision = false;
      if(currentLane.getRightLane() != null)
      {
         rightSensor(currentLane.getRightLane().getAsphalt().getRadius());
         collision = collisionSensed(rightSensor);
      }
      else
      {
         collision = true;
      }
      return collision;
   }

   private boolean isLeftSideBlocked()
   {
      boolean collision = false;
      if(currentLane.getLeftLane() != null)
      {
         leftSensor(currentLane.getLeftLane().getAsphalt().getRadius());
         collision = collisionSensed(leftSensor);
      }
      else
      {
         collision = true;
      }
      return collision;
   }

   public double getRadius()
   {
      return radius;
   }

   public void setRadius(Lane lane)
   {
      this.radius = lane.getAsphalt().getRadius();
   }

   public Lane getLane()
   {
      return currentLane;
   }

   public void setLane(Lane lane)
   {
      this.currentLane = lane;
   }
      
   @Override
   public void handle(long now)
   {
      if (now > lastTimerCall + One_Sec)
      {

         if (!isFrontBlocked())
         {
            moveInCircle(vehicleLane);
            angle += getSpeed() * 0.0011;
            lastTimerCall = now;
         }
         else
         {
            // go left
            if (!isLeftSideBlocked())
            {
               currentLane = currentLane.getLeftLane();
               vehicleLane = currentLane.getAsphalt().getRadius();
               moveInCircle(vehicleLane);
            }
            else if (!isRightSideBlocked())
            {
               currentLane = currentLane.getRightLane();
               vehicleLane = currentLane.getAsphalt().getRadius();
               moveInCircle(vehicleLane);
            }
         }
      }
   }
}
