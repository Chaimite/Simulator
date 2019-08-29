package model;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class Carriageway
{
   private final int maxElementsOnTrack = 5;
   private final int minElementsOnTrack = 1;

   private StackPane trackPane;
   private int elementsInTrack;
   private Lane baseLane;
   private Lane ghostLane;
   private Lane currentLane;

   public Carriageway(StackPane trackPane, Circle asphalt, Pane vehiclePane)
   {
      baseLane = LaneFactory.generateLanes(asphalt, vehiclePane);
      baseLane.setIsActive(true);
      currentLane = baseLane;
      ghostLane = baseLane.getOuterLane();
      // Sets inner and outer ghost lanes
      ghostLane.setInnerLane(null);
      currentLane.setOuterLane(null);
      
      this.trackPane = trackPane;
      elementsInTrack = 1;
      
   }
   // Adds lane in carriage way
   public void addLane()
   {
      if (!(elementsInTrack < maxElementsOnTrack))
      {
         return;
      }
      double speed = baseLane.getVehicles().get(0).getSpeed();
      ghostLane.changeSpeed(speed);
      addLaneToStack(ghostLane);
      ghostLane.setIsActive(true);
      
      currentLane.setOuterLane(ghostLane);
      if(ghostLane != null) {        
         ghostLane.setInnerLane(currentLane);
      }
      // Needs comments, linked list part
      currentLane = currentLane.getOuterLane();
      ghostLane = ghostLane.getOuterLane();
      
      currentLane.setOuterLane(null);
      if(ghostLane != null)
      {
         ghostLane.setInnerLane(null);         
      }
      
      elementsInTrack++;
   }
   // Removes a lane from carriage way
   public void removeLane()
   {
      if (elementsInTrack > minElementsOnTrack)
      {
         removeLaneFromStack(currentLane);
         currentLane.setIsActive(false);
         elementsInTrack--;
         
         currentLane.setOuterLane(ghostLane);
         if(ghostLane != null)
         {
            ghostLane.setInnerLane(currentLane);            
         }
         
         if(ghostLane == null)
         {
            ghostLane = currentLane;
         }
         else
         {
            ghostLane = ghostLane.getInnerLane();                        
         }
         currentLane = currentLane.getInnerLane();
         
         currentLane.setOuterLane(null);
         ghostLane.setInnerLane(null);
         
      }
   }

   // Removes a complete lane to the stack
   private void removeLaneFromStack(Lane lane)
   {
      trackPane.getChildren().remove(lane.getAsphalt());
      trackPane.getChildren().remove(lane.getOuterRoadMarks());
      trackPane.getChildren().remove(lane.getInnerRoadMarks());
   }

   // Adds a complete lane to the stack
   private void addLaneToStack(Lane lane)
   {
      trackPane.getChildren().add(lane.getAsphalt());
      trackPane.getChildren().add(lane.getOuterRoadMarks());
      trackPane.getChildren().add(lane.getInnerRoadMarks());
   }
   
   // Changes speed of in the current lane
   // establishes the current lane has the outer lane if there is a lane
   public void changeSpeed(double newSpeed)
   {
      Lane currentLane = baseLane;
      do{
         currentLane.changeSpeed(newSpeed);;
         currentLane = currentLane.getOuterLane();
      }
      while(currentLane != null);
   }

   // if the current lane does not exist, makes the current lane as the outer lane and establishes if the vehicles are moving
   public void isMoving(boolean value)
   {
      Lane currentLane = baseLane;
      do{
         currentLane.isMoving(value);
         currentLane = currentLane.getOuterLane();
      }
      while(currentLane != null);
   }
   
   // if the current lane does not exist sets the current lane as the outer lane and establishes the vehicle density 
   public void setVehicleDensity(int value)
   {
      Lane currentLane = baseLane;
      do{
         currentLane.setVehicleDensity(value);
         currentLane = currentLane.getOuterLane();
      }
      while(currentLane != null);
   }
   
   // checks if the base lane is not stopped
   public boolean isMoving()
   {
      return baseLane.isMoving();
   }

}
