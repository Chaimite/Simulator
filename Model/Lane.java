package model;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Lane
{
   private final double laneSize = 25;
   private Circle asphalt;
   private Circle innerRoadMarks;
   private Circle outerRoadMarks;
   private final Color roadMarksColor = Color.WHITE;
   private final Color circleInsideColor = Color.TRANSPARENT;
   private Lane rightLane;
   private Lane leftLane;
   private Circle blockObject;
   private ArrayList<Vehicle> vehicles;
   private int numberOfVehicles;
   private int maxVehicles;
   private boolean isMoving;
   private int vehicleDensity;
   private Pane vehiclePane;
   private boolean isActive;
   private double x;
   private double y;
   public Vehicle getVehicle;

   public Lane(double radius, Pane vehiclePane)
   {
      this.vehiclePane = vehiclePane;
      // The asphalt part
      asphalt = new Circle(radius, circleInsideColor);
      asphalt.setStroke(Color.rgb(211, 211, 211));
      asphalt.setStrokeWidth(laneSize);
      asphalt.setPickOnBounds(false);

      // The inner road marks
      innerRoadMarks = new Circle(radius - 10, circleInsideColor);
      innerRoadMarks.setStroke(roadMarksColor);
      innerRoadMarks.getStrokeDashArray().add(10d);

      // The outer road marks
      outerRoadMarks = new Circle(radius + laneSize - 12, circleInsideColor);
      outerRoadMarks.setStroke(roadMarksColor);

      vehicles = new ArrayList<>();
      // This defines the maximum number of vehicles according to it's
      // radius, so this is applied for any lane radius
      maxVehicles = (int) ((((int) (2 * Math.PI * asphalt.getRadius())) / 10)
            * 0.2);
      vehicleDensity = 0;
      this.isActive = false;
      // This will get the x and y locations of the centre of the vehicle pane
      // they are static variables that will be used in other methods,
      // so that the location is consistent
       x = ((vehiclePane.getBoundsInParent().getMaxX()
            + vehiclePane.getBoundsInParent().getMinX()) / 4.0) + 22;
       y = ((vehiclePane.getBoundsInParent().getMaxY()
            + vehiclePane.getBoundsInParent().getMinY()) / 4.0) - 2.5;
   }

   public Lane(Circle asphalt, Pane vehiclePane)
   {
      this.vehiclePane = vehiclePane;
      this.asphalt = asphalt;

      vehicles = new ArrayList<>();
      // This defines the maximum number of vehicles according to it's
      // radius, so this is applied for any lane radius
      maxVehicles = (int) ((((int) (2 * Math.PI * asphalt.getRadius())) / 10)
            * 0.2);
      vehicleDensity = 0;
      this.isActive = false;
      // This will get the x and y locations of the centre of the vehicle pane
      // they are static variables that will be used in other methods,
      // so that the location is consistent
      x = ((vehiclePane.getBoundsInParent().getMaxX()
            + vehiclePane.getBoundsInParent().getMinX()) / 4.0) + 22;
      y = ((vehiclePane.getBoundsInParent().getMaxY()
            + vehiclePane.getBoundsInParent().getMinY()) / 4.0) - 2.5;
   }
   
   
   public ArrayList<Vehicle> getVehicles() {
       return vehicles;
   }
   // Getter for asphalt
   public Circle getAsphalt()
   {
      return asphalt;
   }
   
   // Setter for asphalt
   public void setAsphalt(Circle asphalt)
   {
      this.asphalt = asphalt;
   }
   
   // Getter for inner road marks
   public Circle getInnerRoadMarks()
   {
      return innerRoadMarks;
   }

   // Setter for inner road marks
   public void setInnerRoadMarks(Circle innerRoadMarks)
   {
      this.innerRoadMarks = innerRoadMarks;
   }

   // Getter for outer road marks
   public Circle getOuterRoadMarks()
   {
      return outerRoadMarks;
   }
   
   // Setter for outer road marks
   public void setOuterRoadMarks(Circle outerRoadMarks)
   {
      this.outerRoadMarks = outerRoadMarks;
   }
   
   // Getter for right lane
   public Lane getRightLane()
   {
      return rightLane;
   }

   // Getter for left lane
   public Lane getLeftLane()
   {
      return leftLane;
   }

   // Setter for right lane
   public void setRightLane(Lane rightLane)
   {
      this.rightLane = rightLane;
   }
   
   // Setter for left lane
   public void setLeftLane(Lane leftLane)
   {
      this.leftLane = leftLane;
   }

   // Setter for blocking object
   public void setBlockingObject(Circle blockObject)
   {
      this.blockObject = blockObject;
   }

   // Getter for blocking object
   public Circle getBlockingObject()
   {
      return blockObject;
   }

   // Getter for number of vehicles
   public int getNumberOfVehicles()
   {
      return numberOfVehicles;
   }

   // Setter for number of vehicles
   public void setNumberOfVehicles(int numberOfVehicles)
   {
      this.numberOfVehicles = numberOfVehicles;
   }

   // Method to start and stop existing vehicles
   public void isMoving(boolean value)
   {
      this.isMoving = value;

      if (value)
      {
         for (Vehicle v : vehicles)
         {
            v.start();
         }
      }
      else
      {
         for (Vehicle v : vehicles)
         {
            v.stop();
         }
      }
   }

   public boolean isMoving()
   {
      return isMoving;
   }
   
   // Method to set speed in each vehicle
   public void changeSpeed(double newSpeed)
   {
      for (Vehicle v : vehicles)
      {
         v.setSpeed(newSpeed);
      }
   }

   // Method to set the density of vehicles 
   public void setVehicleDensity(int value)
   {
      if (vehicleDensity != value)
      {
         this.vehicleDensity = value;
         generateVehicles();
      }
   }

   // This method manages the generation of vehicles (adds or removes them)
   public void generateVehicles()
   {
      if (isActive)
      {
         calculateNumberOfVehiclesBasedOnDensity();

         if (numberOfVehicles > vehicles.size())
         {
            addVehicles();
         }
         else
         {
            removeVehicles();
         }
      }
      else
      {
         numberOfVehicles = 0;
         removeVehicles();
      }

   }
   
   // This method is to convert density to a an int, so that 
   // it can then generate a certain number of vehicles
   private void calculateNumberOfVehiclesBasedOnDensity()
   {
      if (vehicleDensity == 0)
      {
         numberOfVehicles = (int) (maxVehicles * 0.1);
      }
      else if (vehicleDensity == 50)
      {
         numberOfVehicles = (int) (maxVehicles * 0.17);
      }
      else
      {
         numberOfVehicles = maxVehicles;
      }

   }
   
   // This method adds vehicles, by looking at the existing number of vehicles
   // and producing the number of vehicles until what was decided by the user
   public void addVehicles()
   {
      for (int i = vehicles.size(); i < getNumberOfVehicles(); i++)
      {
//         double speed = vehicles.get(0).getSpeed();
         Vehicle v = new Vehicle("V" + vehicles.size(),this, vehiclePane, x, y);
         // The vehicle needs to be started
         v.start();
         // The vehicle is added to the ArrayList
         addVehicle(v);
      }
   }

   // This method removes the amount of existing vehicles from
   // the vehicle pane and the ArrayList
   public void removeVehicles()
   {
      int oldNumber = vehicles.size();
      for (int i = getNumberOfVehicles(); i < oldNumber; i++)
      {
         Vehicle v = vehicles.get(0);        
         // Removes vehicle from the vehicle pane
         v.removeVehicle();
         // Removes vehicle from the ArrayList
         removeVehicle(v);
      }
   }
   
   // Adds a vehicle to the ArrayList
   public void addVehicle(Vehicle v)
   {
      vehicles.add(v);      
   }
   
   // Removes a vehicle from the ArrayList
   public void removeVehicle(Vehicle v)
   {
      vehicles.remove(v);      
   }
   
   // Method to generate vehicles if true, 
   // removes them if false
   public void setIsActive(boolean isActive)
   {
      this.isActive = isActive;
      generateVehicles();
   }
   public int getVehiclesSize() {
      return vehicles.size();
   }
}
