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
      ghostLane = baseLane.getRightLane();
      
      ghostLane.setLeftLane(null);
      currentLane.setRightLane(null);
      
      this.trackPane = trackPane;
      elementsInTrack = 1;
      
   }

   public void addLane()
   {
      if (!(elementsInTrack < maxElementsOnTrack))
      {
         return;
      }
      addLaneToStack(ghostLane);
      ghostLane.setIsActive(true);
      
      currentLane.setRightLane(ghostLane);
      if(ghostLane != null) {        
         ghostLane.setLeftLane(currentLane);
      }
      
      currentLane = currentLane.getRightLane();
      ghostLane = ghostLane.getRightLane();
      
      currentLane.setRightLane(null);
      if(ghostLane != null)
      {
         ghostLane.setLeftLane(null);         
      }
      
      elementsInTrack++;
   }

   public void removeLane()
   {
      if (elementsInTrack > minElementsOnTrack)
      {
         removeLaneFromStack(currentLane);
         currentLane.setIsActive(false);
         elementsInTrack--;
         
         currentLane.setRightLane(ghostLane);
         if(ghostLane != null)
         {
            ghostLane.setLeftLane(currentLane);            
         }
         
         if(ghostLane == null)
         {
            ghostLane = currentLane;
         }
         else
         {
            ghostLane = ghostLane.getLeftLane();                        
         }
         currentLane = currentLane.getLeftLane();
         
         currentLane.setRightLane(null);
         ghostLane.setLeftLane(null);
         
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
   
   public void changeSpeed(double newSpeed)
   {
      Lane currentLane = baseLane;
      do{
         currentLane.changeSpeed(newSpeed);;
         currentLane = currentLane.getRightLane();
      }
      while(currentLane != null);
   }

   public void isMoving(boolean value)
   {
      Lane currentLane = baseLane;
      do{
         currentLane.isMoving(value);
         currentLane = currentLane.getRightLane();
      }
      while(currentLane != null);
   }
   
   public void setVehicleDensity(int value)
   {
      Lane currentLane = baseLane;
      do{
         currentLane.setVehicleDensity(value);
         currentLane = currentLane.getRightLane();
      }
      while(currentLane != null);
   }
   
   public boolean isMoving()
   {
      return baseLane.isMoving();
   }
}
