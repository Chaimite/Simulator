package model;

public interface ObservableBlockingElement
{
  void onLocationChangedUpdate(double centerX, double centerY);
  void notifyObservers();
  void addObserver(Vehicle vehicle);
  void removeObserver(Vehicle vehicle);
  
}
