package model;

import javafx.scene.shape.Circle;

public interface LaneObserver
{
   void onBlockingMethodLocationChanged(Circle path);
}
