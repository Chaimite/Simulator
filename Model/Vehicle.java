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

   private double speed = 25;// Pre-defined value speed to start
   private Lane currentLane;
   private double radius;
   private double angle = 1.57;

   private Circle vehicle;
   private Pane vehiclePane;
   private double vehicleLane;
   private long lastTimerCall = System.nanoTime();
   private long One_Sec = 10000000;
   private boolean collision;
   private Circle frontSensor;
   private Circle rightSensor;
   private Circle leftSensor;
   private boolean startedMoving;
   private final double sensorPosition = 0.15;

  
   public Vehicle(Lane lane,
         Pane vehiclePane, double x, double y)
   {
      super();
      vehicle = new Circle(10, Color.CORNFLOWERBLUE);
      frontSensor = new Circle(1, Color.TRANSPARENT);
      rightSensor = new Circle(2, Color.TRANSPARENT);
      leftSensor = new Circle(2, Color.TRANSPARENT);
      this.currentLane = lane;
      this.vehiclePane = vehiclePane;
      addVehicle();
      vehicleLane = lane.getAsphalt().getRadius();
      startedMoving = false;
      
      
      // Sets x and y values for vehicle
      vehicle.setLayoutX(x);
      vehicle.setLayoutY(y);
      // Sets x and y values for front sensor, if it
      // doesn't exist the vehicle goes to the top left corner
      frontSensor.setLayoutX(x);
      frontSensor.setLayoutY(y);// this value of 
      // 5 allows for the next vehicle not be caught by it
      
      // Sets x and y values for right sensor
      rightSensor.setLayoutX(x);
      rightSensor.setLayoutY(y);
      // Sets x and y values for left sensor
      leftSensor.setLayoutX(x);
      leftSensor.setLayoutY(y);
      // Starts the vehicle
      this.start();

   }

   // Adds a vehicle and its sensors to the vehicle pane
   public void addVehicle() {
      vehiclePane.getChildren().addAll(vehicle, frontSensor, rightSensor, leftSensor);
   }
   
   // Removes a vehicle and its sensors from the vehicle pane
   public void removeVehicle() {
      this.stop();
      vehiclePane.getChildren().removeAll(vehicle, frontSensor, rightSensor, leftSensor);
   }
   
   // Gets the center x coordinate of a vehicle
   public double getX()
   {
      return vehicle.getCenterX();
   }

   // Sets the center x coordinate of a vehicle
   public void setX(double x)
   {
      vehicle.setCenterX(x);
   }

   // Gets the center y coordinate of a vehicle
   public double getY()
   {
      return vehicle.getCenterY();
   }

   // Sets the center x coordinate of a vehicle
   public void setY(double y)
   {
      vehicle.setCenterY(y);
   }

   // Getter for vehicle speed
   public double getSpeed()
   {
      return speed;
   }
   
   // Setter for speed
   public void setSpeed(double speed)
   {
      this.speed = speed;
   }
   
   // Makes the vehicle advance
   public void moveInCircle(double radius)
   {
      // Formula to define a point in a circle
      double newX = currentLane.getAsphalt().getCenterX()
            + (radius * Math.cos(angle));
      double newY = currentLane.getAsphalt().getCenterY()
            + (radius * Math.sin(angle));
      
      // Makes the translation of the vehicle
      vehicle.setTranslateX(newX);
      vehicle.setTranslateY(newY);
      
      // Positions the sensor constantly in front of the vehicle
      frontSensor(radius);
   }
   
   // Creates a sensor and positioning it slightly ahead of the vehicle
   public void frontSensor(double radius)
   {
      double frontSensorLocationX = currentLane.getAsphalt().getCenterX()
            + (radius * Math.cos(angle + sensorPosition));
      double frontSensorLocationY = currentLane.getAsphalt().getCenterY()
            + (radius * Math.sin(angle + sensorPosition));

      frontSensor.setTranslateX(frontSensorLocationX);
      frontSensor.setTranslateY(frontSensorLocationY);
   }

   // Creates a sensor on the right side and positions it in the right lane of the current lane
   public void rightSensor(double radius)
   {
      double rightSensorLocationX = currentLane.getRightLane().getAsphalt().getCenterX()
            + (radius * Math.cos(angle));
      double rightSensorLocationY = currentLane.getRightLane().getAsphalt().getCenterY()
            + (radius * Math.sin(angle));

      rightSensor.setTranslateX(rightSensorLocationX);
      rightSensor.setTranslateY(rightSensorLocationY);
   }

   // Creates a sensor on the left side and positions it in the right lane of the current lane
   public void leftSensor(double radius)
   {
      double leftSensorLocationX = currentLane.getLeftLane().getAsphalt().getCenterX()
            + (radius * Math.cos(angle));
      double leftSensorLocationY = currentLane.getLeftLane().getAsphalt().getCenterY()
            + (radius * Math.sin(angle));

      leftSensor.setTranslateX(leftSensorLocationX);
      leftSensor.setTranslateY(leftSensorLocationY);
   }
   
   // Check collisions in front of the vehicle
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
                        && ((Circle)n).getRadius() > 2 // This value 
                        // is for it to not confused with the size of other sensors
                        && n != vehicle 
                        && n.getBoundsInParent().intersects(sensor.getBoundsInParent()))
                  .findAny());
      return result.isPresent();
      
   }
   
   // Check collisions on the right side of the vehicle
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
   
   // Check collisions on the left side of the vehicle
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

   // Getter for radius
   public double getRadius()
   {
      return radius;
   }

   // Setter for radius of the asphalt 
   public void setRadius(Lane lane)
   {
      this.radius = lane.getAsphalt().getRadius();
   }

   // Getter for current lane where the vehicle is positioned
   public Lane getLane()
   {
      return currentLane;
   }

   // Setter for current lane where the vehicle is positioned
   public void setLane(Lane lane)
   {
      this.currentLane = lane;
   }
      
   
   // Handle method that is used for the logic of the movement of the vehicle
   @Override
   public void handle(long now)
   {
      // Starts moving the vehicle

      if(startedMoving)
      {
         if (now > lastTimerCall + One_Sec)
         {  // Checks if the front of the vehicle is blocked
            if (!isFrontBlocked())
            {
               moveInCircle(vehicleLane);
               angle += getSpeed() * 0.0011;// The angle is what 
               // controls the speed, basically the movement,
               // the value of 0.0011 is what was found to make the 
               // transitions smoother, so that the user of the 
               // software can understand the movement of the vehicles
               lastTimerCall = now;
            }
            else
            {
               // Checks the left side of the vehicle if the front is blocked
               if (!isLeftSideBlocked())
               {
                  currentLane.removeVehicle(this);
                  currentLane = currentLane.getLeftLane();
                  currentLane.addVehicle(this);
                  vehicleLane = currentLane.getAsphalt().getRadius();
                  moveInCircle(vehicleLane);
               }
               // Checks the right side of the vehicle if the front and left side are blocked
               else if (!isRightSideBlocked())
               {
                  currentLane.removeVehicle(this);
                  currentLane = currentLane.getRightLane();
                  currentLane.addVehicle(this);
                  vehicleLane = currentLane.getAsphalt().getRadius();
                  moveInCircle(vehicleLane);
               }
               else
               {
                  // System.out.println(name + " All sides are blocked. stopping!!");
                  // There is no need of introducing code here, since it means
                  // that the vehicles will not advance and will stop 

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
