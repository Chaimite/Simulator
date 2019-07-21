package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Controller implements Initializable
{
    @FXML
   private Rectangle vehicle;

   @FXML
   private Circle baseTrack;

   @FXML
   private Button addLane;

   @FXML
   private Button removeLane;

   @FXML
   private StackPane track;

   @FXML
   private Pane vehicleStackPane;

   @FXML
   private Circle blockObject;

   @FXML
   private Button pause;

   @FXML
   private Button proceed;

   @FXML
   private Slider velocityHandler;
   
   private double centerXCoordinate;
   private double centerYCoordinate;   
   private Circle outerCircle;
   private double lastElementRadius;
   private Circle lastElementInTrack;
   private double laneRadius = 22;
   private double newRadius;
   private Stack<Circle> trackStack = new Stack<>();
   private int elementsInTrack;
   private Timeline timeline;
   private  Delta dragDelta =  new Delta(); 
   private DoubleProperty angle = new SimpleDoubleProperty();
   private Rotate rotate;
        
   @Override
   public void initialize(URL location, ResourceBundle resources)
   {
    
      // Inserts the first part of the track
      trackStack.push(baseTrack);

      // Makes the vehicle stack pane transparent
      vehicleStackPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
      
      // Makes the vehicle go around in a circle     
      timeline = new Timeline(new KeyFrame(Duration.seconds(5), new KeyValue(angle, 360)));
      timeline.setCycleCount(Animation.INDEFINITE);
     
      timeline.play();

      for (int i = 0; i < 1; i++)
      {        
         double xCenter = baseTrack.getCenterX();
         double yCenter = baseTrack.getCenterY() ;
//         double xCenter = 213;
//         double yCenter = 200;
         double centerRadius = baseTrack.getRadius();
         System.out.println(xCenter);
         System.out.println(yCenter);
         System.out.println(centerRadius);
         rotate = new Rotate(xCenter, yCenter, centerRadius);
         
         vehicle.getTransforms().add(rotate);
         rotate.angleProperty().bind(angle.add(360.0 * i / 5));
      }     
   }
   
   // Make the blocking object draggable
   @FXML
   void blockingElementReleased(MouseEvent event) {
      blockObject.getScene().setCursor(Cursor.HAND);
   }
   
   @FXML
   void blockingElementClicked(MouseEvent event) {
      dragDelta.x = blockObject.getCenterX() - event.getX();
      dragDelta.y = blockObject.getCenterY() - event.getY();
      blockObject.getScene().setCursor(Cursor.MOVE);
   }
   
   @FXML
   void blockingElementDragged(MouseEvent event) {
      blockObject.setCenterX(event.getX() + dragDelta.x);
      blockObject.setCenterY(event.getY() + dragDelta.y);
   }
   
   // Pause and play vehicles
   @FXML
   void proceedMovement(MouseEvent event) {
      timeline.play();
   }

   @FXML
   void pauseMovement(MouseEvent event) {
      timeline.pause();
   }
   
   // Adding and removing lanes
   @FXML
   void addLaneOnClick(MouseEvent event) {
   // This method will make the button add a track
      // Data from the last inserted lane
      
      lastElementInTrack = trackStack.peek();
      centerXCoordinate = lastElementInTrack.getLayoutX();
      centerYCoordinate = lastElementInTrack.getLayoutY();
      lastElementRadius = lastElementInTrack.getRadius();

      // A new lane
      newRadius = lastElementRadius + laneRadius;
      outerCircle = new Circle(centerXCoordinate, centerYCoordinate, newRadius,
            Color.TRANSPARENT);
      outerCircle.setStroke(Color.rgb(211, 211, 211));
      outerCircle.setStrokeWidth(25);

      // The inner road marks
      // Circle innerCircle = new Circle(centerXCoordinate,
      // centerYCoordinate,innerRadius, Color.TRANSPARENT);
      // innerCircle.setStroke(Color.BLACK);
      // innerCircle.getStrokeDashArray().add(10d);

      // Placing the circle in the stack
      elementsInTrack = track.getChildren().size();
      
      if (elementsInTrack <= 4)
      {
         track.getChildren().addAll(trackStack.push(outerCircle));
      }
   }
   @FXML
   void removeLaneOnClick(MouseEvent event) {
      // This method will make the button remove a track, but will not allow to
      // remove the base track
      elementsInTrack = track.getChildren().size();
      if (elementsInTrack >= 2)
      {
         track.getChildren().remove(trackStack.pop());
      }
   }
}