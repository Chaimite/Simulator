package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
   
   @FXML
   private Label numberOfVehicles;
     
   private Delta dragDelta =  new Delta(); 
   private Carriageway track;
   private int totalNumberOfVehiclesInPane = 0;
   
   
   
   
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
      // Set the value of the density of vehicles in the vehicle pane
      track.setVehicleDensity(value);
      // Updates the number of vehicles existing in the vehicle pane
      numberOfVehicles.setText(Integer.toString( getNumberOfVehiclesInPane()));
     
   }
   
   // This method gets the number of vehicles in the pane
   public int getNumberOfVehiclesInPane()
   {
      totalNumberOfVehiclesInPane = 0;
      for (Node circle : vehiclePane.getChildren())
      {
         if (circle instanceof Circle && ((Circle) circle).getRadius() > 2//this is to skip the sensors
               && ((Circle) circle).getRadius() < 11)//this is to skip the blocking element
         {
            totalNumberOfVehiclesInPane++;
         }
      }
      return totalNumberOfVehiclesInPane;
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
         currentLane = currentLane.getOuterLane();
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
      numberOfVehicles.setText(Integer.toString( getNumberOfVehiclesInPane()));
      
   }
   
   // Removing lane
   @FXML
   void removeLaneOnClick(MouseEvent event)
   {
      track.removeLane();
      numberOfVehicles.setText(Integer.toString( getNumberOfVehiclesInPane()));
   }

}