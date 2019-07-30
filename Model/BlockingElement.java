package model;

import java.util.ArrayList;
import java.util.List;

public class BlockingElement implements ObservableBlockingElement
{
   private double centerX;
   private double centerY;
   private List<Vehicle> vehicles = new ArrayList<>();

   public BlockingElement()
   {
      
   }
   public BlockingElement(double centerX, double centerY)
   {
      super();
      this.centerX = centerX;
      this.centerY = centerY;
   }

   public double getCenterX()
   {
      return centerX;
   }
   
   public void setCenterX(double centerX)
   {
      this.centerX = centerX;
   }

   public double getCenterY()
   {
      return centerY;
   }
   
   public void setCenterY(double centerY)
   {
      this.centerY = centerY;
   }
   

   @Override
   public void notifyObservers()
   {
      
      for (Vehicle vehicle : vehicles)
      {
         vehicle.notify();
         getCenterX();
         getCenterY();
      }

   }  

   @Override
   public void onLocationChangedUpdate(double centerX, double centerY)
   {
      for (Vehicle vehicle : vehicles)
      {
         vehicle.notify();
      }
      
   }

   @Override
   public void addObserver(Vehicle vehicle)
   {
      vehicles.add(vehicle);
      
   }

   @Override
   public void removeObserver(Vehicle vehicle)
   {
      vehicles.remove(vehicle);
      
   }

}
