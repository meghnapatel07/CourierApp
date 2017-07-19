package controller;

import java.time.LocalDate;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.Address;
import model.Courier;
import model.Customer;
import model.ExpressCourier;
import model.NormalCourier;
import dao.AddressDAO;
import dao.CourierDAO;

public class bookACourierController {
	@FXML
	private TextField srcstreet;
	@FXML
	private TextField srccity;
	@FXML
	private TextField srcstate;
	@FXML
	private TextField srczip;
	@FXML
	private TextField deststreet;
	@FXML
	private TextField destcity;
	@FXML
	private TextField deststate;
	@FXML
	private TextField destzip;
	@FXML
	private RadioButton normalBtn;
	@FXML
	private RadioButton expressBtn;
	@FXML
	private TextField weight;
	@FXML
	private TextField recname;
	@FXML
	private TextField reccntctno;
	@FXML
	private DatePicker date;
	@FXML
	private Label msg;

	Customer cust;
	String regex = "^([0-9]{6})$";
	Pattern zip_pattern = Pattern.compile(regex);
	String regex_cntct = "^([0-9]{10})$";
	Pattern cntct_pattern = Pattern.compile(regex_cntct);

	public void initialize(Customer c) {
		cust = c;
		srcstreet.setText(null);
		srccity.setText(null);
		srcstate.setText(null);
		srczip.setText(null);
		deststreet.setText(null);
		destcity.setText(null);
		deststate.setText(null);
		destzip.setText(null);
		recname.setText(null);
		reccntctno.setText(null);
		weight.setText(null);
		date.setValue(null);
	}

	public void submit() {
		msg.setText("");
		System.out.println("inside submit");

		// validate the data
		if (srcstreet.getText() == null || srcstreet.getText().trim().equals("")) {
			msg.setText("Source street cannot be empty");
		} else if (srccity.getText() == null || srccity.getText().trim().equals("")) {
			msg.setText("Source city cannot be empty");
		} else if (srcstate.getText() == null || srcstate.getText().trim().equals("")) {
			msg.setText("Source State cannot be empty");
		} else if (srczip.getText() == null || srczip.getText().trim().equals("")) {
			msg.setText("Source zipcode cannot be empty");
		} else if (deststreet.getText() == null || deststreet.getText().trim().equals("")) {
			msg.setText("Destination street cannot be empty");
		} else if (destcity.getText() == null || destcity.getText().trim().equals("")) {
			msg.setText("Destination city cannot be empty");
		} else if (deststate.getText() == null || deststate.getText().trim().equals("")) {
			msg.setText("Destination State cannot be empty");
		} else if (destzip.getText() == null || destzip.getText().trim().equals("")) {
			msg.setText("Destination zipcode cannot be empty");
		} else if (!zip_pattern.matcher(this.srczip.getText().trim()).matches()) {
			msg.setText("Invalid source zipcode");
		} else if (!zip_pattern.matcher(this.destzip.getText().trim()).matches()) {
			msg.setText("Invalid destination zipcode");
		} else if (weight.getText() == null || weight.getText().trim().equals("")) {
			msg.setText("Weight cannot be empty");
		} else if (reccntctno.getText() == null || reccntctno.getText().trim().equals("")) {
			msg.setText("Receiver contact no. cannot be empty");
		} else if (date.getValue() == null) {
			msg.setText("Pick up date cannot be empty");
		} else if (!cntct_pattern.matcher(this.reccntctno.getText().trim()).matches()) {
			msg.setText("Contact number should be of 10 digits");
		}

		else {

			try {
				String srcstreet = this.srcstreet.getText();
				String srccity = this.srccity.getText();
				String srcstate = this.srcstate.getText();
				Integer srczip = Integer.parseInt(this.srczip.getText());

				String deststreet = this.deststreet.getText();
				String destcity = this.destcity.getText();
				String deststate = this.deststate.getText();
				Integer destzip = Integer.parseInt(this.destzip.getText());

				Double weight = Double.parseDouble(this.weight.getText());
				String recname = this.recname.getText();
				Long reccntctno = Long.parseLong(this.reccntctno.getText());

				LocalDate d = date.getValue();

				// create address model object
				Address src = new Address(srcstreet, srccity, srcstate, srczip);
				Address dest = new Address(deststreet, destcity, deststate, destzip);

				AddressDAO addao = new AddressDAO();
				// persist data into database
				src = addao.createAdd(src);
				dest = addao.createAdd(dest);

				// System.out.println("Source:"+ src);
				// System.out.println("Destination:"+ dest);

				CourierDAO cdao = new CourierDAO();

				String couriertype = "";
				Courier c = null;
				if (normalBtn.isSelected()) {
					couriertype = "NORMAL";
					// create courier model object
					// polymorphism : ability to take different forms
					c = new NormalCourier(cust, src, dest, couriertype, weight, recname, reccntctno, d, "INITIAL");

				} else if (expressBtn.isSelected()) {
					couriertype = "EXPRESS";
					// create courier model object
					// polymorphism : ability to take different forms
					c = new ExpressCourier(cust, src, dest, couriertype, weight, recname, reccntctno, d, "INITIAL");
				}
				// dynamic binding: call to calculateRate() will be resolved at run time
				// depending on type of object that variable references
				double rate = c.calculateRate();
				c.setRate(rate);
				// persist into database
				c = cdao.createCourier(c);
				if (c != null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setContentText("Courier booked successfully" + "\n" + "Tracking number is: "
							+ c.getTrackingNo());
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Error creating courier");
					alert.showAndWait();
				}
				clear();
			} catch (NumberFormatException e) {
				msg.setText("Number format error in numeric fields:Zipcode/Contactno/Weight");
			}
		}// end of if-else validation
	}// end save

	public void clear() {
		srcstreet.setText("");
		srccity.setText("");
		srcstate.setText("");
		srczip.setText("");
		deststreet.setText("");
		destcity.setText("");
		deststate.setText("");
		destzip.setText("");
		weight.setText("");
		recname.setText("");
		reccntctno.setText("");
		date.setValue(null);
		normalBtn.setSelected(false);
		expressBtn.setSelected(false);
	}
}// end class
