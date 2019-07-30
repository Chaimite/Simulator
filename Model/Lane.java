package model;

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

   public Lane(double radius)
   {
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

}
