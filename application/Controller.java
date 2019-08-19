package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.Carriageway;
import model.Lane;
import model.LaneFactory;

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

   @FXML
   private Slider vehicleDensitySlider;
   
   
   private Delta dragDelta =  new Delta(); 
   private Carriageway track;
   
   
   
   @Override
   public void initialize(URL location, ResourceBundle resources)
   {  
      
      track = new Carriageway(trackPane, baseCarriageway, vehiclePane);

      // Makes the vehicle stack pane transparent
      vehiclePane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
      
      // Get coordinates of block object
      setMouseOnPressed();
      
      // Making the object move
      setMouseOnDragged();
      
      // Action taken when blocking element is released 
      setOnMouseReleased();
   }
   
   public class Delta
   {
      double x, y;
   }
   
   // Action for vehicle speed slider
   @FXML
   void speedHandler(MouseEvent event)
   {
      double sliderValue = (double) velocityHandler.getValue();
      track.changeSpeed(sliderValue);
   }
   
   // Action for vehicle density slider
   @FXML
   void vehicleHandler(MouseEvent event) {
      int value = (int)vehicleDensitySlider.getValue();
      if(value < 25)
      {
         value = 0;
      }
      else if(value < 75)
      {
         value = 50;
      }
      else
      {
         value = 100;
      }
      vehicleDensitySlider.setValue(value);
      
      track.setVehicleDensity(value);
   }
   // Make cursor change when it's on top of blocking element
   public void setMouseOnPressed()
   {
      blockObject.setOnMousePressed((t) -> {     
         blockObject.setCursor(Cursor.HAND);
         Circle r = (Circle) (t.getSource());
         r.toFront();       
      });
   }
   
   public void setOnMouseReleased() {
      blockObject.setOnMouseReleased((t) ->{
         // checks for collisions
         checkBounds();
      });
   }
   // Sets the position of the block object
   public void setMouseOnDragged()
   {
      blockObject.setOnMouseDragged((t) -> {
         blockObject.setCenterX(t.getX() + dragDelta.x);
         blockObject.setCenterY(t.getY() + dragDelta.y);
      });
   }
     
   // Checks if the blocking object and the lane are colliding
   private void checkBounds()
   {  
      Lane currentLane = LaneFactory.getBaseLane();
      do{
         Shape intersect = Shape.intersect(currentLane.getAsphalt(), blockObject);
         if (intersect.getBoundsInParent().getWidth() != -1) {
            currentLane.setBlockingObject(blockObject);
         }
         else
         {
            currentLane.setBlockingObject(null);
         }
         currentLane = currentLane.getRightLane();
      }
      while(currentLane != null);
   }
   
   //Play pause button
   @FXML
   void playPauseAction(MouseEvent event)
   {
      if (track.isMoving())
      {
         playPause.setText("Play");
         track.isMoving(false);
         
      }
      else
      {
         playPause.setText("Pause");
         track.isMoving(true);
      }
   }

   // Adding lane button
   @FXML
   void addLaneOnClick(MouseEvent event)
   {
      track.addLane();
   }
   
   // Removing lane
   @FXML
   void removeLaneOnClick(MouseEvent event)
   {
      track.removeLane();
   }


  
}