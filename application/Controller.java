package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import com.sun.corba.se.spi.ior.MakeImmutable;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
   private StackPane vehicleStackPane;
   
   @FXML
   private Circle blockObject;
  
   
   private PathTransition transition;
   private double centerXCoordinate;
   private double centerYCoordinate;
   
   private Circle outerCircle;
   private double lastElementRadius;
   private Circle lastElementInTrack;
   private double laneRadius = 22;
   private double newRadius;
   private Stack<Circle> trackStack = new Stack<>();
   private int elementsInTrack;
   
   
   
   public void addLaneOnClick(ActionEvent event) throws IOException
   {  
      // This method will make the button add a track
      
      // Data from the last inserted lane
      lastElementInTrack = trackStack.peek();
      centerXCoordinate = lastElementInTrack.getLayoutX();
      centerYCoordinate = lastElementInTrack.getLayoutY();     
      lastElementRadius = lastElementInTrack.getRadius();
      
      // A new lane
      newRadius = lastElementRadius + laneRadius;
      outerCircle = new Circle(centerXCoordinate, centerYCoordinate,newRadius, Color.TRANSPARENT);
      outerCircle.setStroke(Color.rgb(211, 211, 211));
      outerCircle.setStrokeWidth(25);

      // The inner road marks
//      Circle innerCircle = new Circle(centerXCoordinate, centerYCoordinate,innerRadius, Color.TRANSPARENT);
//      innerCircle.setStroke(Color.BLACK);
//      innerCircle.getStrokeDashArray().add(10d);

      // Placing the circle in the stack
      elementsInTrack = track.getChildren().size();
      if (elementsInTrack <= 5)
      {
         track.getChildren().addAll(trackStack.push(outerCircle));
      }
      
   }

   
   public void removeLaneOnClick(ActionEvent event)
   {
      // This method will make the button remove a track, but will not allow to remove the base track
      elementsInTrack = track.getChildren().size();
      if (elementsInTrack >= 3)
      {
         track.getChildren().remove(trackStack.pop());
      }
   }
 
   void path(double xCoordinate, double yCoordinate, double radius)
   {
      // Transition type for vehicle
      transition = new PathTransition();
      transition.setNode(vehicle);
      transition.setDuration(Duration.seconds(5));
      transition.setPath(this.baseTrack);
      transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      transition.setCycleCount(PathTransition.INDEFINITE);
      transition.play();

   }

   @Override
   public void initialize(URL location, ResourceBundle resources)
   {
      
      // Inserts the first part of the track
      trackStack.push(baseTrack);
      // Establish path format and radius
      double radius = 110;
      path(vehicleStackPane.getLayoutX(), vehicleStackPane.getLayoutY(), radius);
      
      
      // Makes the vehicle stack pane transparent
      vehicleStackPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);");
      // Make the blocking object draggable
      

      
      Delta dragDelta = new Delta();

      blockObject.setOnMousePressed(new EventHandler<MouseEvent>()
      {
         @Override
         public void handle(MouseEvent event)
         {
            dragDelta.x = blockObject.getCenterX() - event.getX();
            dragDelta.y = blockObject.getCenterY() - event.getY();
            blockObject.getScene().setCursor(Cursor.MOVE);
         }
      });

      blockObject.setOnMouseReleased(new EventHandler<MouseEvent>()
      {
         @Override
         public void handle(MouseEvent event)
         {
            blockObject.getScene().setCursor(Cursor.HAND);
         }
      });

      blockObject.setOnMouseDragged(new EventHandler<MouseEvent>()
      {
         @Override
         public void handle(MouseEvent mouseEvent)
         {
            blockObject.setCenterX(mouseEvent.getX() + dragDelta.x);
            blockObject.setCenterY(mouseEvent.getY() + dragDelta.y);
         }
      });
      }
   
}
