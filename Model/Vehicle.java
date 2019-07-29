package model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Vehicle implements LaneObserver
{
   private final double width = 30;
   private final double height = 15;
   private Color fill = Color.GREENYELLOW;
   private final Rectangle vehicle = new Rectangle(width, height, fill);

  


   private PathTransition transition = new PathTransition();
   private Circle path;
   
   @FXML
   Circle baseCarriageway;
   
   
   public Vehicle()
   {
      ExecutorService service = Executors.newCachedThreadPool();
      service.submit(new Runnable()
      {         
         @Override
         public void run()
         {
            moveVehicle(path);           
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
   @Override
   public void onBlockingMethodLocationChanged(Circle path)
   {
      // TODO Auto-generated method stub
      
   }


}
