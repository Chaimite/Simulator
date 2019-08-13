package model;

import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Vehicle2 extends AnimationTimer
{

   private double xMotion;
   private double yMotion;
   private double speed = Math.PI/100;
   private Lane lane;
   private double radius;
   private double angle = 1.57;

   private final Rectangle vehicle;
   private Carriageway carriageway;
   private Pane vehiclePane;
   private double vehicleLane;
   private long lastTimerCall = System.nanoTime();
   private final long One_Sec = 10000000;
   private final double probeSize = 1;
   private boolean flag;
   private double x,y;
   Optional<Node> result = null;
   Circle probe;
   private double rotationAngle;
   
   
   public Vehicle2(double speed, Lane lane, Carriageway carriageway, Pane vehiclePane)
   {
      super();
      this.speed = speed;
      rotationAngle = 2;
      vehicle = new Rectangle(30, 15, Color.RED);
      probe = new Circle( 10, Color.TRANSPARENT);
//      vehiclePane.getChildren().add(probe);
      this.lane = lane;
      this.carriageway = carriageway;
      this.vehiclePane = vehiclePane;
      vehiclePane.getChildren().addAll(vehicle,probe);
      vehicleLane = lane.getAsphalt().getRadius();
//      Circle baseCircle = lane.getAsphalt();
      
      
      double x = (vehiclePane.getBoundsInParent().getMaxX() + vehiclePane.getBoundsInParent().getMinX()) / 4.0  ;
      double y = (vehiclePane.getBoundsInParent().getMaxY() + vehiclePane.getBoundsInParent().getMinY()) / 4.0 ;
     
      vehicle.setLayoutX(x); 
      vehicle.setLayoutY(y); 
      
      probe.setLayoutX(x - 2);
      probe.setLayoutY(y - 2);
   }

   
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
      
      double newX = lane.getAsphalt().getCenterX() + (radius * Math.cos(angle));
      double newY = lane.getAsphalt().getCenterY() + (radius * Math.sin(angle));
      
      System.out.println("new x value " + newX);
      System.out.println("new y value " + newY);
      System.out.println("####################");
//      Duration duration = Duration.seconds(5);
//      Rotate rotate = new RotateTransition(duration, node)
//      
//      vehicle.getTransforms().add(rotate);
      vehicle.setTranslateX(newX);
      vehicle.setTranslateY(newY);
   }
   private boolean isFrontBlocked(double radius)
   {     
      
      double probeFrontLocationX = (lane.getAsphalt().getCenterX() + probeSize) + (radius * Math.cos(angle));
      double probeFrontLocationY = (lane.getAsphalt().getCenterY() + probeSize) + (radius * Math.sin(angle));
      
      probe.setTranslateX(probeFrontLocationX);
      probe.setTranslateY(probeFrontLocationY);
      
      System.out.println("probe x " +  probeFrontLocationX);
      System.out.println("probe y " +  probeFrontLocationY);
//  
      result = vehiclePane.getChildren().stream().filter(n -> n.getLayoutX() == probeFrontLocationX && n.getLayoutY() == probeFrontLocationY)
            .findAny();
      return flag = (result != null) ? true : false;
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
         
         isFrontBlocked(vehicleLane);
         moveInCircle(vehicleLane);
         System.out.println(now);
         System.out.println((now / 1000000000.0));
         System.out.println(((now / 1000000000.0) / 10000000.0));
         System.out.println("--------------------------");
         angle += 0.01;
         lastTimerCall = now;
         
      }
   }
}
