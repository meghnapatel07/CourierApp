package controller;

import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Customer;
import dao.CustomerDAO;

public class viewUpdateCustProfileController {
	@FXML
	private Label username;
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

	Customer cust;
	String regex_cntct = "^([0-9]{10})$";
	Pattern cntct_pattern = Pattern.compile(regex_cntct);
	
	String regex_email = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	Pattern email_pattern = Pattern.compile(regex_email);

	public void initialize(Customer c) {
		cust = c;
		// System.out.println("Cust id is "+ cust.getCustid());
		fname.setEditable(false);
		lname.setEditable(false);
		email.setEditable(false);
		contact.setEditable(false);

		username.setText(c.getUsername());
		fname.setText(c.getFname());
		lname.setText(c.getLname());
		email.setText(c.getEmail());
		contact.setText(c.getContactno().toString());

	}

	public void edit() {
		fname.setEditable(true);
		lname.setEditable(true);
		email.setEditable(true);
		contact.setEditable(true);
	}

	public void update() {
		msg.setText("");
		System.out.println("inside update profile");
		System.out.println("Cust id is " + cust.getCustid());
		// validation
		if (fname.getText() == null || fname.getText().trim().equals("")) {
			msg.setText("First Name cannot be empty");
		} else if (lname.getText() == null || lname.getText().trim().equals("")) {
			msg.setText("Last Name cannot be empty");
		} else if (email.getText() == null || email.getText().trim().equals("")) {
			msg.setText("Email ID cannot be empty");
		} else if (contact.getText() == null || contact.getText().trim().equals("")) {
			msg.setText("Contact number cannot be empty");
		} else if (!email_pattern.matcher(email.getText().trim()).matches()) {
			msg.setText("Invalid Email Id");
		} else if (!cntct_pattern.matcher(contact.getText().trim()).matches()) {
			msg.setText("Contact number should be of 10 digits");
		}else {
			// Extract the data from the view elements

			String fname = this.fname.getText();
			String lname = this.lname.getText();
			String email = this.email.getText();
			try {
				Long contact = Long.parseLong(this.contact.getText());

				cust.setFname(fname);
				cust.setLname(lname);
				cust.setEmail(email);
				cust.setContactno(contact);

				// Create a DAO instance of the model
				CustomerDAO cdao = new CustomerDAO();
				// Use the DAO to persist the model to database
				cust = cdao.updateCustomer(cust);
				if (cust != null) {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setTitle("Success");
					alert1.setContentText("Details updated successfully");
					alert1.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Error updating Profile");
					alert.showAndWait();
				}

			} catch (NumberFormatException e) {
				msg.setText("Number format error in Contactno");
			}
		}
	}// end method
}// end class
