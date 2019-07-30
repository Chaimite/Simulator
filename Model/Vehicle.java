package model;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Vehicle implements VehicleObserver
{
   private final double width = 30;
   private final double height = 15;
   private Color fill = Color.GREENYELLOW;
   private final Rectangle vehicle = new Rectangle(width, height, fill);

   private PathTransition transition = new PathTransition();
   private Circle path;
   private BlockingElement be = new BlockingElement();
   
   private double centerX, centerY;
   private final double radius = 12;
   private Circle blockObject;
   private ArrayList<Shape> nodes;

   @FXML
   Circle baseCarriageway;

   public Vehicle()
   {
      be.addObserver(this);
      ExecutorService service = Executors.newCachedThreadPool();
      service.submit(new Runnable()
      {
         @Override
         public void run()
         {
         
            blockObject = new Circle(centerX, centerY, radius);
            checkCollisionBetweenBlockingElementAndVehicle(blockObject);
            
            boolean colision = true;
            if (!colision)
            {
               moveVehicle(path);
            }
            else  {
              
               
            }
         }
      });
      }

   public void moveVehicle(Circle path)
   {
      transition.setNode(this.vehicle);
      transition.setDuration(Duration.seconds(5));
      transition.setPath(baseCarriageway);
      transition.setInterpolator(Interpolator.LINEAR);
      transition.setOrientation(
            PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      transition.setCycleCount(PathTransition.INDEFINITE);
      transition.play();
   }

   public Color getFill()
   {
      return fill;
   }

   public void setFill(Color fill)
   {
      this.fill = fill;
   }

   private void checkCollisionBetweenBlockingElementAndVehicle(Shape block)
   {

      boolean collisionDetected = false;
      for (Shape static_bloc : nodes)
      {
         if (static_bloc != block)
         {

            Shape intersect = Shape.intersect(vehicle, blockObject);
            if (intersect.getBoundsInParent().getWidth() != -1)
            {
               collisionDetected = true;
            }
         }
      }
      if (collisionDetected)
      {
         vehicle.setFill(Color.YELLOW);
      }
      else
      {
         vehicle.setFill(Color.RED);
      }
   }

   @Override
   public void update()
   {
      be.getCenterX();
      be.getCenterY();
   
      
   }

  
  


}
