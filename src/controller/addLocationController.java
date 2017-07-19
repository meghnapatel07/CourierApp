package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Admin;
import model.Location;
import dao.LocationDAO;

public class addLocationController {

	@FXML
	private TextField locname;
	// This is the Text Area element in the view for address of location
	@FXML
	private TextArea address;
	@SuppressWarnings("rawtypes")
	@FXML
	private ComboBox city;
	@SuppressWarnings("rawtypes")
	@FXML
	private ComboBox state;
	@FXML
	private TextField zipcode;
	@FXML
	private TextField contactno;
	@FXML
	private Label msg;

	Admin admin;

	ObservableList<String> statelist = FXCollections.observableArrayList("Gujarat", "Punjab", "Rajasthan", "Tamilnadu",
			"West Bengal");
	ObservableList<String> gujlist = FXCollections.observableArrayList("Ahmedabad", "Baroda", "Surat");
	ObservableList<String> pblist = FXCollections.observableArrayList("Amritsar", "Jalandhar", "Ludhiana");
	ObservableList<String> rajlist = FXCollections.observableArrayList("Udaipur", "Jaipur", "Jaisalmer");
	ObservableList<String> tnlist = FXCollections.observableArrayList("Chennai", "Coimbatore", "Erode");
	ObservableList<String> wblist = FXCollections.observableArrayList("Kolkata", "Kharagpur", "Siliguri");

	String regex_zip = "^([0-9]{6})$";
	Pattern zip_pattern = Pattern.compile(regex_zip);
	String regex_cntct = "^([0-9]{10})$";
	Pattern cntct_pattern = Pattern.compile(regex_cntct);
	
	public void initialize(Admin a) {

		admin = a;

		this.locname.setText(null);
		this.address.setText(null);
		this.city.setValue(null);
		this.state.setValue(null);
		this.zipcode.setText(null);
		this.contactno.setText(null);
		this.state.setItems(statelist);
		this.state.setVisibleRowCount(3);

	}

	public void statechoice() {
		if (state.getValue().equals("Gujarat"))
			city.setItems(gujlist);
		else if (state.getValue().equals("Punjab"))
			city.setItems(pblist);
		else if (state.getValue().equals("Rajasthan"))
			city.setItems(rajlist);
		else if (state.getValue().equals("Tamilnadu"))
			city.setItems(tnlist);
		else if (state.getValue().equals("West Bengal"))
			city.setItems(wblist);

	}

	// Method to save the form to database
	public void save() {
		msg.setText("");
		System.out.println("inside save");

		// validate the data
		if (locname.getText() == null || locname.getText().trim().equals("")) {
			msg.setText("Location Name cannot be empty");
		} else if (address.getText() == null || address.getText().trim().equals("")) {
			msg.setText("Address cannot be empty");
		} else if (state.getValue() == null) {
			msg.setText("State cannot be empty");
		} else if (city.getValue() == null) {
			msg.setText("City cannot be empty");
		} else if (zipcode.getText() == null || zipcode.getText().trim().equals("")) {
			msg.setText("Zipcode cannot be empty");
		} else if (contactno.getText() == null || contactno.getText().trim().equals("")) {
			msg.setText("Contact no cannot be empty");
		} else if (!zip_pattern.matcher(this.zipcode.getText().trim()).matches()) {
			msg.setText("Invalid zipcode.");
		}else if (!cntct_pattern.matcher(this.contactno.getText().trim()).matches()) {
			msg.setText("Contact number should be of 10 digits");
		} else {
			// Extract the data from the view elements
			String name = this.locname.getText();
			String address = this.address.getText();
			String city = (String) this.city.getValue();
			String state = (String) this.state.getValue();
			String zipcode = this.zipcode.getText();

			Matcher matcher = zip_pattern.matcher(zipcode);

			try {
				Long zip = Long.parseLong(zipcode);
				Long contactnum = Long.parseLong(this.contactno.getText());
				// Create the model object
				Location loc = new Location();
				// Set the values from the input form
				loc.setName(name);
				loc.setAddress(address);
				loc.setCity(city);
				loc.setState(state);
				// zone of admin will be the zone of location
				// ie. whatever location an admin will add by default it will be
				// added to his/her zone list
				loc.setZone(admin.getZone());
				loc.setPincode(zip);
				loc.setContactno(contactnum);
				// Create a DAO instance of the model
				LocationDAO l = new LocationDAO();
				// Use the DAO to persist the model to database
				loc = l.createLoc(loc);
				if (loc != null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setContentText("Location added successfully");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Error creating location");
					alert.showAndWait();
				}
				clear();
			} catch (NumberFormatException e) {
				msg.setText("Number format error in Zipcode/Contactno");
			}

		}// end of if-else

	}// end addLocation()

	void clear() {
		locname.setText("");
		this.address.setText("");
		this.city.setValue("");
		this.state.setValue("");
		this.zipcode.setText("");
		this.contactno.setText("");
	}
}// end class
