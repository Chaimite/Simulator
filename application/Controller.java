package application;

import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Track;

public class Controller implements Initializable
{
    @FXML
   private Rectangle vehicle;

   @FXML
   private Circle baseLane;

   @FXML
   private Button addLane;

   @FXML
   private Button removeLane;

   @FXML
   private StackPane trackPane;

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
   private final double laneRadius = 22;
   private double newRadius; 
   private int elementsInTrack;
   private Timeline timeline;
   private  Delta dragDelta =  new Delta(); 
   private DoubleProperty angle = new SimpleDoubleProperty();
   private Rotate rotate;
   private Track track;
        
   @Override
   public void initialize(URL location, ResourceBundle resources)
   {
      
      track = new Track(trackPane, baseLane);
      // Inserts the first part of the track

      // Makes the vehicle stack pane transparent
      vehicleStackPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
      
      // Makes the vehicle go around in a circle     
      timeline = new Timeline(new KeyFrame(Duration.seconds(5), new KeyValue(angle, 360)));
      timeline.setCycleCount(Animation.INDEFINITE);
     
      timeline.play();

      for (int i = 0; i < 1; i++)
      {        
         double xCenter = baseLane.getCenterX();
         double yCenter = baseLane.getCenterY() ;
//         double xCenter = 213;
//         double yCenter = 200;
         double centerRadius = baseLane.getRadius();
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
      System.out.println("object x " + blockObject.getCenterX());
      System.out.println("object y " + blockObject.getCenterY());
//      System.out.println("vehcile x " + vehicle.ge);
      System.out.println("vehcile y " + vehicle.getTranslateY());
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
      elementsInTrack = trackPane.getChildren().size();
      if (!(elementsInTrack <= 4))
      {
         return;
      }
      
      lastElementInTrack = track.getLanes().peek();
      lastElementRadius = lastElementInTrack.getRadius();

      // A new lane
      newRadius = lastElementRadius + laneRadius;
      outerCircle = new Circle(centerXCoordinate, centerYCoordinate, newRadius,
            Color.TRANSPARENT);
      outerCircle.setStroke(Color.rgb(211, 211, 211));
      outerCircle.setStrokeWidth(25);

      // Placing the circle in the stack
      trackPane.getChildren().add(track.getLanes().push(outerCircle));
      
   }
   
   @FXML
   void removeLaneOnClick(MouseEvent event) {
      elementsInTrack = track.getTrackPane().getChildren().size();
      if (elementsInTrack >= 3)
      {
         trackPane.getChildren().remove(track.getLanes().pop());
      }
   }
}