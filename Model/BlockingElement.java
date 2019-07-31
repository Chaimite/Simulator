package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BlockingElement implements Observable
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
//      vehicles.update();
   }
   

   @Override
   public void notifyObservers()
   {
      
      for (Vehicle vehicle : vehicles)
      {
         Iterator<Vehicle> it = vehicles.iterator();
         while(it.hasNext()) {
            
         }
//         vehicle.update();

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
