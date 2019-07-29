package model;

public interface LaneObservable
{
   void addObserver(LaneObserver observer);

   void notifyObservers();

   void removeObserver(LaneObserver observer);
}
