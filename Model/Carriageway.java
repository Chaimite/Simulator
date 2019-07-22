package model;

import java.util.Stack;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Carriageway 
{
   private double centerXCoordinate;
   private double centerYCoordinate;
   private Stack<Circle> lanes = new Stack<>();
   private StackPane trackPane;
   private double lastElementRadius;
   private Circle lastElementInTrack;
   private double radius;
   private final double laneRadius = 22;
   private Circle lane;
   private int elementsInTrack;
   private final double laneSize = 25;
   private final int maxElementsOnTrack = 5;
   private final int minElementsOnTrack = 3;
   private Circle innerRoadMarks;
   private Circle outerRoadMarks;
   private final Color roadMarksColor = Color.WHITE;
   private final Color circleInsideColor = Color.TRANSPARENT;
   
   public Carriageway(StackPane trackPane, Circle baseCarriageway) {
      centerXCoordinate = baseCarriageway.getLayoutX();
      centerYCoordinate = baseCarriageway.getLayoutY();
      lanes.push(baseCarriageway);
      this.trackPane = trackPane;
           
   }

   public StackPane getTrackPane()
   {
      return trackPane;
   }

   public void setTrackPane(StackPane trackPane)
   {
      this.trackPane = trackPane;
   }

   public Stack<Circle> getLanes()
   {
      return lanes;
   }

   public void setLanes(Stack<Circle> lanes)
   {
      this.lanes = lanes;
   }
   
   public void addLane()
   {
      elementsInTrack = this.getTrackPane().getChildren().size();
      if (!(elementsInTrack <= maxElementsOnTrack))
      {
         return;
      }
      lastElementInTrack = lanes.peek();
      lastElementRadius = lastElementInTrack.getRadius();

      // Lane without road marks
      radius = lastElementRadius + laneRadius;
      lane = new Circle(centerXCoordinate, centerYCoordinate, radius,
            circleInsideColor);
      lane.setStroke(Color.rgb(211, 211, 211));
      lane.setStrokeWidth(laneSize);
      
      // Inner marks on lane
      innerRoadMarks = new Circle(centerXCoordinate,centerXCoordinate, lastElementRadius, circleInsideColor);     
      innerRoadMarks.setStroke(roadMarksColor);
      innerRoadMarks.getStrokeDashArray().add(10d);
      
      // Outer marks on lane
      outerRoadMarks = new Circle(centerXCoordinate,centerXCoordinate, lastElementRadius + 22, circleInsideColor); 
      outerRoadMarks.setStroke(roadMarksColor);
      
      Lane fullLane = new Lane(lane, innerRoadMarks, outerRoadMarks);
      
      // Places the lane in the pane and the stack
      trackPane.getChildren().add(lanes.push(lane));     
   }
   public void removeLane() {
      elementsInTrack = this.getTrackPane().getChildren().size();
      if (elementsInTrack >= minElementsOnTrack)
      {
         trackPane.getChildren().remove(this.getLanes().pop());
      }
   }

//   public Circle returnNewInnerMarks(double lastElementRadius, double centerXCoordinate, double centerYCoordinate, double innerRadius) {
//      
//      innerLaneMarks = new Circle(centerXCoordinate,centerYCoordinate,innerRadius, Color.TRANSPARENT);
//      innerLaneMarks.setStroke(Color.WHITE);
//      innerLaneMarks.getStrokeDashArray().add(10d);
//      return innerLaneMarks;
//      
//   }
//   public Circle returnNewOutsideMarks(double lastElementRadius, double centerXCoordinate, double centerYCoordinate, double outerRadius) {
//      outerLaneMarks = new Circle(centerXCoordinate,centerYCoordinate,outerRadius, Color.TRANSPARENT);
//      outerLaneMarks.setStroke(Color.WHITE);
//      return outerLaneMarks;
//   }

}
