package model;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class Carriageway
{
   private final double laneSize = 25;
   private final int maxElementsOnTrack = 5;
   private final int minElementsOnTrack = 1;

   private StackPane trackPane;
   private Pane vehiclePane;
   private double radius;
   private int elementsInTrack;
   private Lane baseLane;
   private Circle blockingObject;
   

   public Carriageway(StackPane trackPane, Circle asphalt, Circle blockingObject, Pane vehiclePane)
   {
      this.blockingObject = blockingObject;
      baseLane = LaneFactory.generateLanes(asphalt);
      this.radius = asphalt.getRadius();
      this.trackPane = trackPane;
      elementsInTrack = 1;
      this.vehiclePane = vehiclePane;
      
   }

   public ArrayList<Lane> getAllAvailableLanes()
   {
      return LaneFactory.getLanes(elementsInTrack);
   }

   public void addLane()
   {
      if (!(elementsInTrack <= maxElementsOnTrack))
      {
         return;
      }
      // calculates radius for next lane
      radius = radius + laneSize;
      // get the lane from LaneFactory
      Lane lane = LaneFactory.getLane(radius);
      // add the lane to the stack
      addLaneToStack(lane);
      elementsInTrack++;
   }

   public void removeLane()
   {
      if (elementsInTrack > minElementsOnTrack)
      {
         Lane lane = LaneFactory.getLane(radius);
         removeLaneFromStack(lane);
         radius = radius - laneSize;
         elementsInTrack--;
      }
   }

   // Removes a lane
   private void removeLaneFromStack(Lane lane)
   {
      trackPane.getChildren().remove(lane.getAsphalt());
      trackPane.getChildren().remove(lane.getOuterRoadMarks());
      trackPane.getChildren().remove(lane.getInnerRoadMarks());
   }

   // Adds a lane
   private void addLaneToStack(Lane lane)
   {
      trackPane.getChildren().add(lane.getAsphalt());
      trackPane.getChildren().add(lane.getOuterRoadMarks());
      trackPane.getChildren().add(lane.getInnerRoadMarks());
   }

   public void generateVehicle()
   {
//      Vehicle v = new Vehicle(trackPane, baseLane, blockingObject, vehiclePane);
//      Thread t = new Thread(v);
//      t.start();
//      ArrayList<Lane> lanes = getAllAvailableLanes();
//      lanes.get(0).addObserver(v);
      
      Vehicle2 v = new Vehicle2(1.0,baseLane,this,vehiclePane);
//      Thread t = new Thread(v);
//      t.start();
      v.start();
      
      
   }
}
