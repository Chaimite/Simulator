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
   private double speed;

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
         return;// does nothing if this condition is not satisfied
      }
      ghostLane.changeSpeed(speed);
      
      //adds new lane to stack and sets it as active
      addLaneToStack(ghostLane);
      ghostLane.setIsActive(true);
      //sets the ghost lane as the outer lane of the current lane
      currentLane.setOuterLane(ghostLane);
      //if there is a lane
      if(ghostLane != null) {
         //sets the current lane as the inner lane of the ghost lane
         ghostLane.setInnerLane(currentLane);
      }
      //makes the outer lane of the current lane to be current lane
      currentLane = currentLane.getOuterLane();
      // makes the ghost lane to be the outer lane of the ghost lane
      ghostLane = ghostLane.getOuterLane();
      // sets the outer lane of the current lane as null
      currentLane.setOuterLane(null);
      //if there is a lane
      if(ghostLane != null)
      {
         // sets inner lane of the ghost lane as null
         ghostLane.setInnerLane(null);         
      }
      // increases the counter
      elementsInTrack++;
   }
   // Removes a lane from carriage way
   public void removeLane()
   {  // checks the number of elements in the track
      if (elementsInTrack > minElementsOnTrack)
      {
         // removes the current lane from the stack
         removeLaneFromStack(currentLane);
         // sets the current lane as not active
         currentLane.setIsActive(false);
         // decreases the elements in track counter
         elementsInTrack--;
         // sets the ghost lane as the outer lane of the ghost lane
         currentLane.setOuterLane(ghostLane);
         // checks if the there is a ghost lane
         if(ghostLane != null)
         {
            // sets the current lane as the inner lane of the ghost lane
            ghostLane.setInnerLane(currentLane);            
         }
         
         // checks if the ghost lane does not exist
         if(ghostLane == null)
         {
            // sets the ghost lane as the current lane
            ghostLane = currentLane;
         }
         else
         {
            // sets the ghost lane as the inner lane of the ghost lane
            ghostLane = ghostLane.getInnerLane();                        
         }
         // sets the current lane as the inner lane of the current lane
         currentLane = currentLane.getInnerLane();
         
         // sets the outer lane of the current lane as null
         currentLane.setOuterLane(null);
         // sets the inner lane of the ghost lane as null
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
      speed = newSpeed;
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
