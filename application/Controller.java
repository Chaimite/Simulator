package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
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
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import model.Carriageway;
import model.Vehicle;

public class Controller implements Initializable
{
//   @FXML
//   private Rectangle vehicle;

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

   private Delta dragDelta =  new Delta(); 
   private Carriageway track;
   private boolean isMoving = true;
   private ArrayList<Circle> availableLanes;
   private ArrayList<Shape> nodes;
   private PathTransition transition = new PathTransition();
   
   private Vehicle vehicle = new Vehicle();     
   @Override
   public void initialize(URL location, ResourceBundle resources)
   {    
      track = new Carriageway(trackPane, baseCarriageway.getRadius());

      // Makes the vehicle stack pane transparent
      vehicleStackPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
      
      // Makes the vehicle go around in a circle   
      makeVehicleMove();
      
      // Get coordinates of block object
      setMouseOnPressed();
      
      // Making the object move
      setMouseOnDragged();
      
      // Action taken when object is released
      nodes = new ArrayList<>();
      nodes.add(vehicle);
      nodes.add(blockObject);
      
      blockObject.boundsInParentProperty()
            .addListener((observable, oldValue,
                  newValue) -> checkCollisionBetweenBlockingElementAndVehicle(
                        blockObject));
   }
   
   public void setMouseOnPressed()
   {
      blockObject.setOnMousePressed((t) -> {

         // Make cursor change when it's on top of blocking element
         blockObject.setCursor(Cursor.HAND);
         Circle r = (Circle) (t.getSource());
         r.toFront();
         availableLanes = track.getAllAvailableLanes();
         availableLanes.add(baseCarriageway);       
      });
   }

   public void setMouseOnDragged()
   {
      blockObject.setOnMouseDragged((t) -> {
         blockObject.setCenterX(t.getX() + dragDelta.x);
         blockObject.setCenterY(t.getY() + dragDelta.y);
         checkBounds(blockObject);
      });
   }
   
   // Makes the vehicle go around the lane
   private void makeVehicleMove()
   {
    // Establish what object to follow path
    transition.setNode(vehicle);
    transition.setDuration(Duration.seconds(5));
    transition.setPath(baseCarriageway);
    transition.setInterpolator(Interpolator.LINEAR);
    transition.setOrientation(
          PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
    transition.setCycleCount(PathTransition.INDEFINITE);
    transition.play();   
   }

   
   
   private void checkCollisionBetweenBlockingElementAndVehicle(Shape block) {
      boolean collisionDetected = false;
      for(Shape static_bloc : nodes) {
         if(static_bloc != block) {

            Shape intersect = Shape.intersect(vehicle, blockObject);
            if (intersect.getBoundsInParent().getWidth() != -1) {
               collisionDetected = true;
            }
         }
      }
      if(collisionDetected) {
        vehicle.setFill(Color.YELLOW);
      }
      else {
         vehicle.setFill(Color.RED);
      }
   }
   // Checks if the blocking object and the lane are colliding
   private void checkBounds(Shape block)
   {
      boolean collisionDetected = false;
      Circle collisionCircle = null;

      for (Circle c : availableLanes)
      {

         if (block.getBoundsInParent().intersects(c.getBoundsInParent()))
         {
            collisionDetected = true;
            collisionCircle = c;
         }
      }

      if (collisionDetected)
      {
         block.setFill(Color.GREEN);
         collisionCircle.setStroke(Color.GREEN);
      }
      else
      {
         block.setFill(Color.BLUE);
      }
   }

   @FXML
   void playPauseAction(MouseEvent event)
   {
      if (isMoving)
      {
         transition.pause();
         playPause.setText("Play");
         isMoving = false;
      }
      else
      {
         transition.play();
         playPause.setText("Pause");
         isMoving = true;
      }
   }

   // Adding and removing lanes
   @FXML
   void addLaneOnClick(MouseEvent event)
   {
      track.addLane();
      vehicle.toFront();
   }

   @FXML
   void removeLaneOnClick(MouseEvent event)
   {
      track.removeLane();
   }
}