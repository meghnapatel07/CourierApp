package controller;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Admin;
import model.Location;
import dao.AdminDAO;

public class AdminController {

	private Stage adminStage; //= new Stage();
	static BorderPane parent;
	Admin admin;
	@FXML
	private Label welcome_msg;
	
	private addLocationController addloccon;
	private viewLocationController viewloccon;

	
	public void initialize (Admin admin)
	{
		this.admin = admin;
		welcome_msg.setText("Welcome: " + admin.getUsername().toUpperCase()+"\n"+"Zone:"+"\n"+admin.getZone().toUpperCase());
		AdminDAO a = new AdminDAO();
		ArrayList<Location> n = a.getLocations(admin);
		admin.setLoclist(n);
	}
	
	 public Stage getPrimaryStage() {
	        return adminStage;
	    }
	public void setDialogStage(Stage dialogStage) {		
		adminStage = dialogStage;
		parent = (BorderPane) adminStage.getScene().getRoot();
		adminStage.setTitle("Admin Screen");
		parent.setLeft(welcome_msg);
		adminStage.show();
	}
	

	public void showAddLocation() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addLocation.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			addloccon = loader.<addLocationController> getController();
			addloccon.initialize(admin);
			parent.setCenter(pane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showViewUpdateDeleteLocation() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewLocation.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			viewloccon = loader.<viewLocationController> getController();
			viewloccon.initialize(admin);
			parent.setCenter(pane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout() {
		System.out.println("calling logout");
		close();
		//showLoginScreen();
		try{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Stage s = new Stage();
		s.setScene(new Scene(root));
		LoginController controller = loader.<LoginController> getController();
		controller.setDialogStage(s);
		s.setTitle("Login Screen");
		s.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void close() {
		//System.out.println("admin wala stage in close"+ adminStage);
		adminStage.fireEvent(new WindowEvent(adminStage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

}
