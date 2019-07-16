package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;




public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		   double width = 1000;
		   double height = 800;
			Parent root = FXMLLoader.load(getClass().getResource("Sim2.fxml"));
			Scene scene = new Scene(root,width,height);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Traffic Simulator");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
