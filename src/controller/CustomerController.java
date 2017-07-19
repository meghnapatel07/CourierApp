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
import model.Customer;


public class CustomerController {
	
	private Stage custStage; //= new Stage();
	static BorderPane parent;
	Customer cust;
	@FXML
	private Label welcome_msg;
	
	public void setDialogStage (Stage dialogStage,Customer cust)
	{
		custStage = dialogStage;
		this.cust = cust;
		parent = (BorderPane) custStage.getScene().getRoot();
		welcome_msg.setText("Welcome: " + "\n"+this.cust.getFname().toUpperCase()+"\n"+this.cust.getLname().toUpperCase());

		custStage.setTitle("Customer Screen");
		parent.setLeft(welcome_msg);
		custStage.show();
		
		//CustomerDAO a = new CustomerDAO();
		//ArrayList<Courier> n = a.getCouriers(cust);
		//cust.setCourierlist(n);
	}
	public void showBookACourier() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bookACourier.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			bookACourierController baccon = loader.<bookACourierController> getController();
			baccon.initialize(cust);
			parent.setCenter(pane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showViewCourierByTrackingNo()
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewCourierByTrackingNo.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			viewCourierByTrackingNoController con = loader.<viewCourierByTrackingNoController> getController();
			con.initialize();
			parent.setCenter(pane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showViewUpdateCustProfile()
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewUpdateCustProfile.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			viewUpdateCustProfileController con = loader.<viewUpdateCustProfileController> getController();
			con.initialize(this.cust);
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
		s.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void close() {
		
		custStage.fireEvent(new WindowEvent(custStage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
}
