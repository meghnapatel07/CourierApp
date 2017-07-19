package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Admin;
import model.Customer;
import model.User;
import dao.AdminDAO;
import dao.CustomerDAO;
import dao.UserDAO;

public class LoginController {

	private Stage dialogStage;
	@FXML
	private TextField user;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;
	@FXML
	private Label message;

	// Method to set the parent stage of the current view

	public void setDialogStage(Stage stage) {
		dialogStage = stage;
		dialogStage.setTitle("Login Screen");
		// System.out.println("scene is :"+ dialogStage.getScene());
	}

	public Scene getDialogScene() {
		System.out.println("calling getdialog scene:" + dialogStage.getScene());
		return dialogStage.getScene();
	}

	public void login() throws IOException {

		String user = this.user.getText();
		String passwd = this.password.getText();
		String role = null;
		User loginuser = new User(user, passwd);

		// use DAO to check data from database
		UserDAO udao = new UserDAO();

		role = getRole(loginuser);

		if (role.equals("ADMIN")) {
			close();

			Admin loginadmin = udao.getAdmin(loginuser);
			Stage newStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin_new.fxml"));
			// Inflate the view using the loader
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			newStage.setScene(scene);

			AdminController controller = loader.<AdminController> getController();
			controller.setDialogStage(newStage);
			controller.initialize(loginadmin);

		} else if (role.equals("CUSTOMER")) {
			close();

			Customer logincust = udao.getCustomer(loginuser);
			Stage newStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customer_main.fxml"));
			// Inflate the view using the loader
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			newStage.setScene(scene);

			CustomerController controller = loader.<CustomerController> getController();
			controller.setDialogStage(newStage, logincust);

		} else if (role.equals("INVALID")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Invalid Username/password !" + "\n" + "Please try again.");
			alert.showAndWait();
			this.user.setText("");
			this.password.setText("");
		}

	}// end login() method

	public String getRole(User loginuser) {
		// use DAO to check data from database
		UserDAO ud = new UserDAO();

		if (ud.checkRole(loginuser, "ADMIN"))
			return "ADMIN";
		else if (ud.checkRole(loginuser, "CUSTOMER"))
			return "CUSTOMER";
		else if (ud.checkRole(loginuser, "EMPLOYEE"))
			return "EMPLOYEE";
		else
			return "INVALID";

	}
	public void addNewCustomer() {
		// System.out.println("new customer sign up");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewCustomer.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			dialogStage.setScene(new Scene(pane));
			NewCustomerController custcon = loader.<NewCustomerController> getController();
			custcon.setDialogStage(dialogStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void close() {
		dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	
}// end LoginController
