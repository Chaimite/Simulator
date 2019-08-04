package model;

public interface Observable
{
   void notifyObservers(boolean collisionDetected);

   void addObserver(Vehicle vehicle);

   void removeObserver(Vehicle vehicle);
}
