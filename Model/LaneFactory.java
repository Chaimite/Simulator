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

   public static ArrayList<Circle> getLanes(int availableNumber)
   {
      Iterator<Lane> lanes = LaneFactory.lanes.values().iterator();
      ArrayList<Circle> availableLanes = new ArrayList<>();

      for (int i = 1; i < availableNumber; i++)
      {
         if (lanes.hasNext())
         {
            availableLanes.add(lanes.next().getAsphalt());
         }
      }
      return availableLanes;
   }
}
