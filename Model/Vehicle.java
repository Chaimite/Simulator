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
   private long One_Sec = 10000000; // change back to One Second
   private boolean collision;
   Circle frontSensor;
   Circle rightSensor;
   Circle leftSensor;
   private String name;
   private boolean startedMoving;

  
   public Vehicle(String name,Lane lane,
         Pane vehiclePane, double x, double y)
   {
      super();
      this.name = name;
      vehicle = new Circle(10, Color.RED);
      frontSensor = new Circle(1, Color.TRANSPARENT);
      rightSensor = new Circle(2, Color.TRANSPARENT);
      leftSensor = new Circle(2, Color.TRANSPARENT);
      this.currentLane = lane;
      this.vehiclePane = vehiclePane;
      addVehicle();
      vehicleLane = lane.getAsphalt().getRadius();
      startedMoving = false;
      
      
      //sets x and y values for vehicle
      vehicle.setLayoutX(x);
      vehicle.setLayoutY(y);
      //sets x and y values for front sensor, if it doens't exist the vehicle goes to the top left corner
      frontSensor.setLayoutX(x);
      frontSensor.setLayoutY(y+5);
      //sets x and y values for right sensor
      rightSensor.setLayoutX(x);
      rightSensor.setLayoutY(y);
      //sets x and y values for left sensor
      leftSensor.setLayoutX(x);
      leftSensor.setLayoutY(y);
      this.start();

   }

   public void addVehicle() {
      vehiclePane.getChildren().addAll(vehicle, frontSensor, rightSensor, leftSensor);
      
      System.out.println("---------------------------------");
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
   // Makes the vehicle advance
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
   // Creating a front sensor and positioning it slightly ahead of the vehicle
   public void frontSensor(double radius)
   {
      double frontSensorLocationX = currentLane.getAsphalt().getCenterX()
            + (radius * Math.cos(angle + 0.15));
      double frontSensorLocationY = currentLane.getAsphalt().getCenterY()
            + (radius * Math.sin(angle + 0.15));

      frontSensor.setTranslateX(frontSensorLocationX);
      frontSensor.setTranslateY(frontSensorLocationY);
   }

   // Creating the right sensor and positioning it in the right lane of the current lane
   public void rightSensor(double radius)
   {
      double rightSensorLocationX = currentLane.getRightLane().getAsphalt().getCenterX()
            + (radius * Math.cos(angle));
      double rightSensorLocationY = currentLane.getRightLane().getAsphalt().getCenterY()
            + (radius * Math.sin(angle));

      rightSensor.setTranslateX(rightSensorLocationX);
      rightSensor.setTranslateY(rightSensorLocationY);
   }

   // Creating the right sensor and positioning it in the right lane of the current lane
   public void leftSensor(double radius)
   {
      double leftSensorLocationX = currentLane.getLeftLane().getAsphalt().getCenterX()
            + (radius * Math.cos(angle));
      double leftSensorLocationY = currentLane.getLeftLane().getAsphalt().getCenterY()
            + (radius * Math.sin(angle));

      leftSensor.setTranslateX(leftSensorLocationX);
      leftSensor.setTranslateY(leftSensorLocationY);
   }
   // Check collisions in front
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
   //Generic collision detection method
   private boolean collisionSensed(Circle sensor)
   {
      Optional<Node> result =
            (vehiclePane.getChildren().stream()
                  .filter(n -> n instanceof Circle 
                        && ((Circle)n).getRadius() > 2
                        && n != vehicle 
                        && n.getBoundsInParent().intersects(sensor.getBoundsInParent()))
                  .findAny());
      return result.isPresent();
      
   }
   // Check collisions in front
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
   // Check collisions in front
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
      if(startedMoving)
      {
         if (now > lastTimerCall + One_Sec)
         {
//         System.out.println(name + " Handeling");
            if (!isFrontBlocked())
            {
//            System.out.println(name + " Front is not blocked moving forward");
               moveInCircle(vehicleLane);
               angle += getSpeed() * 0.0011;
               lastTimerCall = now;
            }
            else
            {
//            System.out.println("in handle");
               // go left
//            System.out.println(name + " Front is blocked checking left");
               if (!isLeftSideBlocked())
               {
                  currentLane.removeVehicle(this);
                  currentLane = currentLane.getLeftLane();
                  currentLane.addVehicle(this);
                  vehicleLane = currentLane.getAsphalt().getRadius();
                  moveInCircle(vehicleLane);
               }
               else if (!isRightSideBlocked())
               {
//               System.out.println(name + " left is blocked checking right");
                  currentLane.removeVehicle(this);
                  currentLane = currentLane.getRightLane();
                  currentLane.addVehicle(this);
                  vehicleLane = currentLane.getAsphalt().getRadius();
                  moveInCircle(vehicleLane);
               }
               else
               {
//               System.out.println(name + " All sides are blocked. stopping!!");
                  
               }
            }
         }
      }
      else
      {
         moveInCircle(vehicleLane);
         angle += getSpeed() * 0.0011;
         lastTimerCall = now;
         startedMoving = true;
      }
   }
}
