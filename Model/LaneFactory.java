package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javafx.scene.shape.Circle;

public class LaneFactory
{
   private static final double laneSize = 25;
   private static HashMap<Double, Lane> lanes = new HashMap<>();
   private static Lane baseLane;
   public static Lane getLane(double radius)
   {
      Lane lane = lanes.get(radius);
     
      if (lane == null)
      {
         lane = new Lane(radius);
         lanes.put(radius, lane);
      }
      return lane;
   }

   public static ArrayList<Lane> getLanes(int availableNumber)
   {
      ArrayList<Lane> availableLanes = new ArrayList<>();
      availableLanes.add(baseLane);
      
      for (int i = 1; i < availableNumber; i++)
      {
            availableLanes.add(baseLane.getRightLane());
      }
      return availableLanes;
   }
   
   public static void addBaseLane(Lane lane)
   {
      lanes.put(lane.getAsphalt().getRadius(), lane);
   }
   
   public static Lane generateLanes(Circle asphalt)
   {
      baseLane = new Lane(asphalt);
      addBaseLane(baseLane);
      double radius = asphalt.getRadius();
      Lane prevLane = baseLane;
      
      for (int i = 0; i < 4; i++)
      {
         radius = radius + laneSize;
         Lane currentLane = getLane(radius);
         
         prevLane.setRightLane(currentLane);
         currentLane.setLeftLane(prevLane);
         prevLane = currentLane;
      }
      
      return baseLane;
   }
   
   
}
