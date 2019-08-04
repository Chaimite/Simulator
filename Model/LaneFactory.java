package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javafx.scene.shape.Circle;

public class LaneFactory
{
   private static HashMap<Double, Lane> lanes = new HashMap<>();

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
      Iterator<Lane> lanes = LaneFactory.lanes.values().iterator();
      ArrayList<Lane> availableLanes = new ArrayList<>();

      for (int i = 0; i < availableNumber; i++)
      {
         if (lanes.hasNext())
         {
            availableLanes.add(lanes.next());
         }
      }
      return availableLanes;
   }
   
   public static void addBaseLane(Circle asphalt)
   {
      lanes.put(asphalt.getRadius(), new Lane(asphalt));
   }
}
