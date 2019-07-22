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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Carriageway;

public class Controller implements Initializable
{
   @FXML
   private Rectangle vehicle;

   @FXML
   private Circle baseCarriageway;

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
   private Button playPause;

   @FXML
   private Slider velocityHandler;

   private Timeline timeline;
   private Delta dragDelta =  new Delta(); 
   private DoubleProperty angle = new SimpleDoubleProperty();
   private Rotate rotate;
   private Carriageway track;
   private boolean isMoving;
   
   
   @Override
   public void initialize(URL location, ResourceBundle resources)
   {
      
      track = new Carriageway(trackPane, baseCarriageway);
//      System.out.println("lanes " + track.getLanes().size());
      // Inserts the first part of the track

      // Makes the vehicle stack pane transparent
      vehicleStackPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
      
      // Makes the vehicle go around in a circle     
      timeline = new Timeline(new KeyFrame(Duration.seconds(5), new KeyValue(angle, 360)));
      timeline.setCycleCount(Animation.INDEFINITE);
      timeline.play();
      

      for (int i = 0; i < 1; i++)
      {        
         double xCenter = baseCarriageway.getCenterX();
         double yCenter = baseCarriageway.getCenterY() ;
         double centerRadius = baseCarriageway.getRadius();
         rotate = new Rotate(xCenter, yCenter, centerRadius);
         
         vehicle.getTransforms().add(rotate);
         
         rotate.angleProperty().bind(angle.add(360.0 * i / 5));
         
      }     
   }
   
   // Make the blocking object draggable
   @FXML
   void blockingElementReleased(MouseEvent event) throws InterruptedException {
      blockObject.getScene().setCursor(Cursor.HAND);
      System.out.println("vehcile x " + vehicle.getLocalToParentTransform().getTx());
      System.out.println("x coordinate blocking element: " + blockObject.getCenterX());
      System.out.println("y coordinate blocking element: " + blockObject.getCenterY());
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
   

   @FXML
   void playPauseAction(MouseEvent event) {
   
      if (isMoving)
      {
         timeline.pause();
         playPause.setText("Play");
         isMoving = false;
         System.out.println("x coordinate vehicle: " + vehicle.getX());
      }
      else
      {
         timeline.play();
         playPause.setText("Pause");
         isMoving = true;
      }
   }
   
   // Adding and removing lanes
   @FXML
   void addLaneOnClick(MouseEvent event) {     
      track.addLane();     
   }
   
   @FXML
   void removeLaneOnClick(MouseEvent event) {
      track.removeLane();
   }
}