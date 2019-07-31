package model;

public interface Observable
{
  void notifyObservers();
  void addObserver(Vehicle vehicle);
  void removeObserver(Vehicle vehicle);
  
}
