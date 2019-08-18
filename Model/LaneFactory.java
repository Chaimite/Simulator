package model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class LaneFactory
{
   private static final double laneSize = 25;
   private static Lane baseLane;
   
   public static Lane generateLanes(Circle asphalt, Pane vehiclePane)
   {
      baseLane = new Lane(asphalt, vehiclePane);
      double radius = asphalt.getRadius();
      Lane prevLane = baseLane;
      
      for (int i = 0; i < 4; i++)
      {
         radius = radius + laneSize;
         Lane currentLane = new Lane(radius, vehiclePane);
         
         prevLane.setRightLane(currentLane);
         currentLane.setLeftLane(prevLane);
         prevLane = currentLane;
      }
      
      return baseLane;
   }
   
   public static Lane getBaseLane()
   {
      return baseLane;
   }
}
