package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.shape.Shape;
import model.BlockingElement;
import model.Carriageway;
import model.Lane;

public class Controller implements Initializable
{
   @FXML
   private Circle baseCarriageway;

   @FXML
   private Button addLane;

   @FXML
   private Button removeLane;

   @FXML
   private StackPane trackPane;

   @FXML
   private Pane vehiclePane;

   @FXML
   private Circle blockObject;

   @FXML
   private Button playPause;

   @FXML
   private Slider velocityHandler;

   private Delta dragDelta =  new Delta(); 
   private Carriageway track;
   private boolean isMoving = true;
   private ArrayList<Lane> availableLanes;
   
   
   private BlockingElement be = new BlockingElement();
   
   
   
   @Override
   public void initialize(URL location, ResourceBundle resources)
   {  
      
      track = new Carriageway(trackPane, baseCarriageway, blockObject, vehiclePane);

      // Makes the vehicle stack pane transparent
      vehiclePane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
      
      // Get coordinates of block object
      setMouseOnPressed();
      
      // Making the object move
      setMouseOnDragged();
      
      setOnMouseReleased();
      
      track.generateVehicle();
      
      // Action taken when object is released
//      nodes = new ArrayList<>();
//      nodes.add(vehicle);
//      nodes.add(blockObject);
      
   }
  
   
   public void setMouseOnPressed()
   {
      blockObject.setOnMousePressed((t) -> {

         // Make cursor change when it's on top of blocking element
         blockObject.setCursor(Cursor.HAND);
         Circle r = (Circle) (t.getSource());
         r.toFront();
         availableLanes = track.getAllAvailableLanes();
         
      });
   }
   public void setOnMouseReleased() {
      blockObject.setOnMouseReleased((t) ->{
         be.setCenterX(blockObject.getCenterX());
         be.setCenterY(blockObject.getCenterY());
         checkBounds();
      });
   }

   public void setMouseOnDragged()
   {
      blockObject.setOnMouseDragged((t) -> {
         blockObject.setCenterX(t.getX() + dragDelta.x);
         blockObject.setCenterY(t.getY() + dragDelta.y);
      });
   }
     
   
//   private void checkCollisionBetweenBlockingElementAndVehicle(Shape block) {
//      boolean collisionDetected = false;
//      for(Shape static_bloc : nodes) {
//         if(static_bloc != block) {
//
//            Shape intersect = Shape.intersect(vehicle, blockObject);
//            if (intersect.getBoundsInParent().getWidth() != -1) {
//               collisionDetected = true;
//            }
//         }
//      }
//      if(collisionDetected) {
//        vehicle.setFill(Color.YELLOW);
//      }
//      else {
//         vehicle.setFill(Color.RED);
//      }
//   }
   // Checks if the blocking object and the lane are colliding
   private void checkBounds()
   {
      boolean collisionDetected = false;
      Lane collisionCircle = null;
      for (Lane lane : availableLanes)
      {
         
         Shape intersect = Shape.intersect(lane.getAsphalt(), blockObject);
          if (intersect.getBoundsInParent().getWidth() != -1) {
             collisionDetected = true;
             collisionCircle = lane;
             lane.collisionDetected(true);
          }
          else
          {
             lane.collisionDetected(false);
          }
      }

      if (collisionDetected)
      {
         blockObject.setFill(Color.GREEN);
         collisionCircle.getAsphalt().setStroke(Color.GREEN);
      }
      else
      {
         blockObject.setFill(Color.BLUE);
      }
   }

   @FXML
   void playPauseAction(MouseEvent event)
   {
      if (isMoving)
      {
         playPause.setText("Play");
         isMoving = false;
      }
      else
      {
         playPause.setText("Pause");
         isMoving = true;
      }
   }

   // Adding and removing lanes
   @FXML
   void addLaneOnClick(MouseEvent event)
   {
      track.addLane();
   }

   @FXML
   void removeLaneOnClick(MouseEvent event)
   {
      track.removeLane();
   }
}