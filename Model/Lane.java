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
   private long period = 10000L;
   private long lastTime= System.currentTimeMillis() - period;
   private double x;
   private double y;

   public Lane(double radius, Pane vehiclePane)
   {
      this.vehiclePane = vehiclePane;
      // The asphalt part
      asphalt = new Circle(radius + 0.1, circleInsideColor);
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
      maxVehicles = (int) ((((int) (2 * Math.PI * asphalt.getRadius())) / 10)
            * 0.2);
      vehicleDensity = 0;
      this.isActive = false;
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
      maxVehicles = (int) ((((int) (2 * Math.PI * asphalt.getRadius())) / 10)
            * 0.2);
      vehicleDensity = 0;
      this.isActive = false;
      x = ((vehiclePane.getBoundsInParent().getMaxX()
            + vehiclePane.getBoundsInParent().getMinX()) / 4.0) + 22;
      y = ((vehiclePane.getBoundsInParent().getMaxY()
            + vehiclePane.getBoundsInParent().getMinY()) / 4.0) - 2.5;
   }

   public Circle getAsphalt()
   {
      return asphalt;
   }

   public void setAsphalt(Circle asphalt)
   {
      this.asphalt = asphalt;
   }

   public Circle getInnerRoadMarks()
   {
      return innerRoadMarks;
   }

   public void setInnerRoadMarks(Circle innerRoadMarks)
   {
      this.innerRoadMarks = innerRoadMarks;
   }

   public Circle getOuterRoadMarks()
   {
      return outerRoadMarks;
   }

   public void setOuterRoadMarks(Circle outerRoadMarks)
   {
      this.outerRoadMarks = outerRoadMarks;
   }

   public Lane getRightLane()
   {
      return rightLane;
   }

   public Lane getLeftLane()
   {
      return leftLane;
   }

   public void setRightLane(Lane rightLane)
   {
      this.rightLane = rightLane;
   }

   public void setLeftLane(Lane leftLane)
   {
      this.leftLane = leftLane;
   }

   public void setBlockingObject(Circle blockObject)
   {
      this.blockObject = blockObject;

   }

   public Circle getBlockingObject()
   {
      return blockObject;
   }

   public int getNumberOfVehicles()
   {
      return numberOfVehicles;
   }

   public void setNumberOfVehicles(int numberOfVehicles)
   {
      this.numberOfVehicles = numberOfVehicles;
   }

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

   public void changeSpeed(double newSpeed)
   {
      for (Vehicle v : vehicles)
      {
         v.setSpeed(newSpeed);
      }
   }

   public void setVehicleDensity(int value)
   {
      if (vehicleDensity != value)
      {
         this.vehicleDensity = value;
         generateVehicles();
      }
   }

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

   public void addVehicles()
   {
      for (int i = vehicles.size(); i < getNumberOfVehicles(); i++)
      {
         Vehicle v = new Vehicle("V" + vehicles.size(),this, vehiclePane, x, y);
         v.start();
         addVehicle(v);
      }
   }

   public void removeVehicles()
   {
      int oldNumber = vehicles.size();
      for (int i = getNumberOfVehicles(); i < oldNumber; i++)
      {
         Vehicle v = vehicles.get(0);
         v.removeVehicle();
         removeVehicle(v);
      }
   }
   
   public void addVehicle(Vehicle v)
   {
      vehicles.add(v);      
   }
   
   public void removeVehicle(Vehicle v)
   {
      vehicles.remove(v);      
   }
   
   public void setIsActive(boolean isActive)
   {
      this.isActive = isActive;
      generateVehicles();
   }
}
