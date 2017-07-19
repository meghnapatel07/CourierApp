package controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Admin;
import model.Location;
import dao.AdminDAO;
import dao.LocationDAO;

public class viewLocationController {

	@FXML
	private TableView<Location> LocationTable;
	@FXML
	private TableColumn<Location, String> LocationName;
	@FXML
	private TableColumn<Location, String> City;

	@FXML
	private TextField locname;
	@FXML
	private TextArea address;
	@FXML
	private ComboBox state;
	@FXML
	private ComboBox city;
	@FXML
	private TextField zipcode;
	@FXML
	private TextField contactno;
	@FXML
	private Label msg;

	ObservableList<String> statelist = FXCollections.observableArrayList("Gujarat", "Punjab", "Rajasthan", "Tamilnadu",
			"West Bengal");
	ObservableList<String> gujlist = FXCollections.observableArrayList("Ahmedabad", "Baroda", "Surat");
	ObservableList<String> pblist = FXCollections.observableArrayList("Amritsar", "Jalandhar", "Ludhiana");
	ObservableList<String> rajlist = FXCollections.observableArrayList("Udaipur", "Jaipur", "Jaisalmer");
	ObservableList<String> tnlist = FXCollections.observableArrayList("Chennai", "Coimbatore", "Erode");
	ObservableList<String> wblist = FXCollections.observableArrayList("Kolkata", "Kharagpur", "Siliguri");
	Admin admin = new Admin();
	ObservableList<Location> locationData;
	
	String regex = "^([0-9]{6})$" ;
	Pattern zip_pattern = Pattern.compile(regex);
	String regex_cntct = "^([0-9]{10})$";
	Pattern cntct_pattern = Pattern.compile(regex_cntct);
	
	void initialize(Admin admin) {
		state.setItems(statelist);
		state.setVisibleRowCount(3);

		this.locname.setText(null);
		this.address.setText(null);
		this.city.setValue(null);
		this.zipcode.setText(null);
		this.contactno.setText(null);
		msg.setText("");
		this.admin = admin;
		AdminDAO a = new AdminDAO();
		ArrayList<Location> loclist = a.getLocations(admin);
		locationData = FXCollections.observableArrayList(loclist);

		LocationName.setCellValueFactory((new PropertyValueFactory<Location, String>("name")));
		City.setCellValueFactory((new PropertyValueFactory<Location, String>("city")));

		LocationTable.setItems(locationData);

		// showLocationDetails(null);

		// Listen for selection changes and show the location details when
		// changed.
		LocationTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showLocationDetails(newValue));

	}// end initialize

	public void showLocationDetails(Location loc) {
		msg.setText("");
		if (loc != null) {
			locname.setText(loc.getName());
			address.setText(loc.getAddress());
			this.city.setValue(loc.getCity());
			this.state.setValue(loc.getState());
			this.zipcode.setText(loc.getPincode().toString());
			this.contactno.setText(loc.getContactno().toString());
		} else {
			clear();
		}
	}// end showLocationDetails

	public void handleUpdateLocation() {
		msg.setText("");
		System.out.println("inside update loc");
		int selectedIndex = LocationTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {

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
				msg.setText("Invalid zipcode");
			}else if (!cntct_pattern.matcher(this.contactno.getText().trim()).matches()) {
				msg.setText("Contact number should be of 10 digits");
			}else {

				// Extract the data from the view elements
				String name = this.locname.getText();
				String address = this.address.getText();
				String city = (String) this.city.getValue();
				String state = (String) this.state.getValue();
				try {
					Long zip = Long.parseLong(this.zipcode.getText());
					Long contactnum = Long.parseLong(this.contactno.getText());
					// get the model object
					Location loc = LocationTable.getSelectionModel().getSelectedItem();

					//System.out.println("aa walu update thase in controller:" + loc.getId());
					loc.setName(name);
					loc.setAddress(address);
					loc.setCity(city);
					loc.setState(state);
					loc.setPincode(zip);
					loc.setContactno(contactnum);

					// Create a DAO instance of the model
					LocationDAO l = new LocationDAO();
					// Use the DAO to persist the model to database
					loc = l.updateLoc(loc);
					if (loc != null) {
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setTitle("Success");
						alert1.setContentText("Location updated successfully");
						alert1.showAndWait();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setContentText("Error updating location");
						alert.showAndWait();
					}
					clear();
				} catch (NumberFormatException e) {
					msg.setText("Number format error in Zipcode/Contactno");
				}
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			// alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Location Selected");
			alert.setContentText("Please select a location in the table.");
			alert.showAndWait();
		}

	}// end of handleUpdateLocation()

	public void handleDeleteLocation() {
		int selectedIndex = LocationTable.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			// alert.setTitle("");
			alert.setContentText("Are you sure you want to delete this location?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				/** handle the case when OK button is clicked */

				// Create the model object
				Location loc = LocationTable.getSelectionModel().getSelectedItem();
				System.out.println("aa walu delete thase in controller:" + loc.getId());
				// Create a DAO instance of the model
				LocationDAO l = new LocationDAO();
				// Use the DAO to persist the model to database
				l.deleteLoc(loc);

				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Success");
				alert1.setContentText("Location deleted successfully");
				alert1.showAndWait();
				clear();
				LocationTable.getItems().remove(selectedIndex);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			// alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Location Selected");
			alert.setContentText("Please select a location in the table.");
			alert.showAndWait();
		}
	}// end of handleDeleteLocation()

	
	public void clear() {
		locname.setText("");
		address.setText("");
		this.city.setValue("");
		this.state.setValue("");
		this.zipcode.setText("");
		this.contactno.setText("");
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

}// end class
