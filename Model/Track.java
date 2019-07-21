package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Track
{
   private double newRadius;
   private double laneRadius;
   private double centerXCoordinate;
   private double centerYCoordinate;
   private Circle lane;
   private Circle innerLaneMarks;
   private Circle outerLaneMarks;
   
   public Circle returnNewLane(double lastElementRadius, double centerXCoordinate, double centerYCoordinate, double laneRadius) {
      newRadius = lastElementRadius + laneRadius;
      lane = new Circle(centerXCoordinate, centerYCoordinate, newRadius,
            Color.TRANSPARENT);
      lane.setStroke(Color.rgb(211, 211, 211));
      lane.setStrokeWidth(25);
      return lane;
   }
   public Circle returnNewInnerMarks(double lastElementRadius, double centerXCoordinate, double centerYCoordinate, double innerRadius) {
      
      innerLaneMarks = new Circle(centerXCoordinate,centerYCoordinate,innerRadius, Color.TRANSPARENT);
      innerLaneMarks.setStroke(Color.WHITE);
      innerLaneMarks.getStrokeDashArray().add(10d);
      return innerLaneMarks;
      
   }
   public Circle returnNewOutsideMarks(double lastElementRadius, double centerXCoordinate, double centerYCoordinate, double outerRadius) {
      outerLaneMarks = new Circle(centerXCoordinate,centerYCoordinate,outerRadius, Color.TRANSPARENT);
      outerLaneMarks.setStroke(Color.WHITE);
      return outerLaneMarks;
   }

}
