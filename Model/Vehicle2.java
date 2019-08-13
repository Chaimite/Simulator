package model;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Vehicle2 extends AnimationTimer
{

   private double xMotion;
   private double yMotion;
   private double speed = 20;
   private Lane lane;
   private double radius;
   private double angle = 10;

   private final Rectangle vehicle;
   private Carriageway carriageway;
   private Pane vehiclePane;
   private double vehicleLane;
   private long lastTimerCall = System.nanoTime();
   private final long One_Sec = 10000000;
   
   
   public Vehicle2(double speed, Lane lane, Carriageway carriageway, Pane vehiclePane)
   {
      super();
      this.speed = speed;
      vehicle = new Rectangle(30, 15, Color.RED);
      this.lane = lane;
      this.carriageway = carriageway;
      this.vehiclePane = vehiclePane;
      vehiclePane.getChildren().add(vehicle);
      vehicleLane = lane.getAsphalt().getRadius();
//      Circle baseCircle = lane.getAsphalt();
      
      
      double x = ((vehiclePane.getBoundsInParent().getMaxX() + vehiclePane.getBoundsInParent().getMinX()) / 4.0)  ;
      double y = (vehiclePane.getBoundsInParent().getMaxY() + vehiclePane.getBoundsInParent().getMinY()) / 4.0 ;
     
      vehicle.setLayoutX(x); 
      vehicle.setLayoutY(y); 
   }

//   @Override
//   public void run()
//   {
//      AnimationTimer timer = new AnimationTimer()
//      {         
//         @Override
//         public void handle(long now)
//         {
//            vehicleLane = lane.getAsphalt().getRadius();
//            moveInCircle(vehicleLane);
//            
//
//         }
//      };
//      timer.start();
//      
//   }

   
   public double getX()
   {
      return vehicle.getX();
   }

   public void setX(double x)
   {
      vehicle.setX(x);
   }

   public double getY()
   {
      return vehicle.getY();
   }

   public void setY(double y)
   {
      vehicle.setY(y);
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
      // point A
      System.out.println(getX());
      System.out.println(getY());
      double newX = getX() + (radius * Math.cos(Math.toDegrees(angle)));
      double newY = getY() + (radius * Math.sin(Math.toDegrees(angle)));
      System.out.println(newX);
      System.out.println(newY);
      System.out.println("####################");
      double factor = Math.tan(newX/newY);
      vehicle.setTranslateX(newX*factor);
      vehicle.setTranslateY(newY*factor);
      
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

   public void rotate(double angle)
   {
      // This is being annoying
      // Maybe this works
      vehicle.rotateProperty().add(angle);
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
         moveInCircle(vehicleLane);
         System.out.println(now);
         System.out.println((now / 1000000000.0));
         System.out.println(((now / 1000000000.0) / 10000000.0));
         System.out.println("--------------------------");
         angle += 0.0001;
         lastTimerCall = now;
      }
   }
}
