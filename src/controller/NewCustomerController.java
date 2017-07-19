package controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import dao.CustomerDAO;

public class NewCustomerController {
	Stage dialogStage;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField fname;
	@FXML
	private TextField lname;
	@FXML
	private TextField email;
	@FXML
	private TextField contact;
	@FXML
	private Label msg;
	
	String regex_cntct = "^([0-9]{10})$";
	Pattern cntct_pattern = Pattern.compile(regex_cntct);
	
	String regex_email = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	Pattern email_pattern = Pattern.compile(regex_email);

	public void setDialogStage(Stage dialogStage)

	{
		this.dialogStage = dialogStage;
		this.dialogStage.setTitle("New Customer Registration Screen");
		username.setText(null);
		password.setText(null);
		fname.setText(null);
		lname.setText(null);
		email.setText(null);
		contact.setText(null);
	}

	public void register() {
		msg.setText("");
		System.out.println("inside register");

		// validate the data
		if (username.getText() == null || username.getText().trim().equals("")) {
			msg.setText("Username cannot be empty");
		} else if (password.getText() == null || password.getText().trim().equals("")) {
			msg.setText("Password cannot be empty");
		} else if (fname.getText() == null || fname.getText().trim().equals("")) {
			msg.setText("First Name cannot be empty");
		} else if (lname.getText() == null || lname.getText().trim().equals("")) {
			msg.setText("Last Name cannot be empty");
		} else if (email.getText() == null || email.getText().trim().equals("")) {
			msg.setText("Email address cannot be empty");
		} else if (contact.getText() == null || contact.getText().trim().equals("")) {
			msg.setText("Contact no cannot be empty");
		}else if (!email_pattern.matcher(email.getText().trim()).matches()) {
			msg.setText("Invalid Email Id");
		} else if (!cntct_pattern.matcher(contact.getText().trim()).matches()) {
			msg.setText("Contact number should be of 10 digits");
		}

		else {
			// Extract the data from the view elements
			String username = this.username.getText();
			String password = this.password.getText();
			String fname = this.fname.getText();
			String lname = this.lname.getText();
			String email = this.email.getText();
			try {
				Long contact = Long.parseLong(this.contact.getText());
				// create model object
				Customer c = new Customer();
				c.setFname(fname);
				c.setLname(lname);
				c.setUsername(username);
				c.setPassword(password);
				c.setEmail(email);
				c.setContactno(contact);

				CustomerDAO udao = new CustomerDAO();
				c = udao.addCustomer(c);
				if (c != null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setContentText("Registeration successful.");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Error creating customer");
					alert.showAndWait();
				}
				clear();

			} catch (NumberFormatException e) {
				msg.setText("Number format error in Contact no");
			}
		}// end of if-else validation
	}

	public void backToLogin() {
		// back to login screen
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));

			AnchorPane pane = (AnchorPane) loader.load();
			this.dialogStage.setScene(new Scene(pane));

			LoginController controller = loader.<LoginController> getController();
			controller.setDialogStage(this.dialogStage);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void clear() {
		username.setText("");
		password.setText("");
		fname.setText("");
		lname.setText("");
		email.setText("");
		contact.setText("");
	}

}// end class
