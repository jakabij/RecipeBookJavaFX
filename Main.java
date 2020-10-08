package application;
	
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;


public class Main extends  Application{
	@Override
	public void start(Stage stage) {
		
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("Page.fxml"));
				Scene scene = new Scene(root);
				stage.setTitle("GOOD TO GO");
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
