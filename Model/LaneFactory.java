package model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class LaneFactory
{
   private static final double laneSize = 25;//defined value of 25 since it allowed to fit the vehicle and blocking element well
   private static Lane baseLane;
   
   public static Lane generateLanes(Circle asphalt, Pane vehiclePane)
   {
      baseLane = new Lane(asphalt, vehiclePane);
      double radius = asphalt.getRadius();
      // initial setup since there is always a baselane, which will be the previous lane
      Lane prevLane = baseLane;
      
      for (int i = 0; i < 4; i++)
      {
         radius = radius + laneSize;
         // creates a new lane, naming it as current lane in the vehicle pane
         Lane currentLane = new Lane(radius, vehiclePane);
         // because the new lane was created, the new lane is now the right lane
         prevLane.setOuterLane(currentLane);
         // 
         currentLane.setInnerLane(prevLane);
         
         prevLane = currentLane;
      }
      
      return baseLane;
   }
   
   public static Lane getBaseLane()
   {
      return baseLane;
   }
}
