package model;


import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
public class Vehicle2 extends AnimationTimer
{

   private double speed = Math.PI/100;
   private Lane lane;
   private double radius;
   private double angle = 1.57;

   private final Circle vehicle;
   private Carriageway carriageway;
   private double rotationAngle;
   private Pane vehiclePane;
   private double vehicleLane;
   private long lastTimerCall = System.nanoTime();
   private final long One_Sec = 10000000;
   private boolean collision;
   Circle frontSensor;
   Circle rightSensor;
   Circle leftSensor;
   
   
   public Vehicle2(double speed, Lane lane, Carriageway carriageway, Pane vehiclePane)
   {
      super();
      this.speed = speed;
      rotationAngle = 2;
      vehicle = new Circle(10, Color.RED);
      frontSensor = new Circle( 10, Color.BLUE);
      this.lane = lane;
      this.carriageway = carriageway;
      this.vehiclePane = vehiclePane;
      vehiclePane.getChildren().addAll(vehicle,frontSensor);
      vehicleLane = lane.getAsphalt().getRadius();    
      double x = ((vehiclePane.getBoundsInParent().getMaxX() + vehiclePane.getBoundsInParent().getMinX()) / 4.0  ) + 22;
      double y = ((vehiclePane.getBoundsInParent().getMaxY() + vehiclePane.getBoundsInParent().getMinY()) / 4.0 ) - 2.5 ;
     
      vehicle.setLayoutX(x); 
      vehicle.setLayoutY(y); 
      
      frontSensor.setLayoutX(x);
      frontSensor.setLayoutY(y);      
      
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

   public void accelerate(double speed)
   {
      speed = speed - 5;
   }

   public void brake(double speed)
   {
      speed = speed + 5;
   }

   public void moveInCircle(double radius)
   {
      double newX = lane.getAsphalt().getCenterX() + (radius * Math.cos(angle));
      double newY = lane.getAsphalt().getCenterY() + (radius * Math.sin(angle));

      vehicle.setTranslateX(newX);
      vehicle.setTranslateY(newY);
      
      frontSensor(radius);

   }

   public void frontSensor(double radius)
   {
      double frontSensorLocationX = lane.getAsphalt().getCenterX() + (radius * Math.cos(angle + 0.08));
      double frontSensorLocationY = lane.getAsphalt().getCenterY() + (radius * Math.sin(angle + 0.08));

      frontSensor.setTranslateX(frontSensorLocationX);
      frontSensor.setTranslateY(frontSensorLocationY);
   }
   // needs to look to the next right lane
   public void rightSensor(double radius) {
      double rightSensorLocationX = lane.getAsphalt().getCenterX() + (radius * Math.cos(angle));
      double rightSensorLocationY = lane.getAsphalt().getCenterY() + (radius * Math.sin(angle));

      rightSensor.setTranslateX(rightSensorLocationX);
      rightSensor.setTranslateY(rightSensorLocationY);
   }
   // needs to look to the next left lane and 
   public void leftSensor(double radius) {
      double leftSensorLocationX = lane.getAsphalt().getCenterX() + (radius * Math.cos(angle));
      double leftSensorLocationY = lane.getAsphalt().getCenterY() + (radius * Math.sin(angle));

      leftSensor.setTranslateX(leftSensorLocationX);
      leftSensor.setTranslateY(leftSensorLocationY);
   }
   
   private boolean isFrontBlocked()
   {           
      collision = false;
      collision = (vehiclePane.getChildren().stream().filter(n -> n != vehicle && n != frontSensor && n.getLayoutX() == frontSensor.getLayoutX() && n.getLayoutY() == frontSensor.getLayoutY())
            .findAny()) == null ? true: false;
      Circle block = lane.getBlockingObject();
      if(block != null)
      {
         Bounds blockBounds = block.localToScreen(block.getBoundsInLocal());

         if(frontSensor.localToScreen(frontSensor.getBoundsInLocal()).intersects(blockBounds))
         {
            collision = true;                     
         }         
      }      
      return collision;
   }
   
   
   private boolean isRightSideBlocked() {
      return false;
       }

   private boolean isLeftSideBlocked() {
      return false;
       }
   public void moveInFront()
   {
      setY(getY() + 20);
      // not sure if this needed
   }

   public void moveToRight()
   {
      setX(getX() + 20);
      // needs the angle, perhaps the x cos part
   }

   public void moveToLeft()
   {
      setX(getX() - 20);
   }
   // Used for a vehicle to rotate on it's centre
   public void rotateLeft(double angle)
   {
//      vehicle.getTransforms().add(new Rotate(angle, vehicle.getBoundsInParent().getMinX(), pivotY));
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
      return lane;
   }

   public void setLane(Lane lane)
   {
      this.lane = lane;
   }
   
   @Override
   public void handle(long now)
   {  
      if(now > lastTimerCall + One_Sec)
      {
         
         if(!isFrontBlocked()) {
            moveInCircle(vehicleLane);
            angle += 0.01;
            lastTimerCall = now;
         }
         else if(isFrontBlocked()) {
            // go left
            if(!isLeftSideBlocked()) {
               // translate to left
               // break and start loop again
               // I am assuming it will be able to resume
               // if not copy the coordinates and
               // use those to begin the loop again
            }
            else if(!isRightSideBlocked()) {
               
            }
            
            
         }
         else
         {
            // stop vehicle
            this.stop();
            
         }
         
      }
   }
}
