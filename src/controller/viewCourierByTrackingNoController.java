package controller;

import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Courier;
import dao.CourierDAO;

public class viewCourierByTrackingNoController {
	@FXML
	private TextField trackingno;
	@FXML
	private Label fcity;
	@FXML
	private Label fstate;
	@FXML
	private Label tcity;
	@FXML
	private Label tstate;
	@FXML
	private Label type;
	@FXML
	private Label weight;
	@FXML
	private Label amount;
	@FXML
	private Label status;
	@FXML
	private Label lblfcity;
	@FXML
	private Label lblfstate;
	@FXML
	private Label lbltcity;
	@FXML
	private Label lbltstate;
	@FXML
	private Label lbltype;
	@FXML
	private Label lblweight;
	@FXML
	private Label lblamount;
	@FXML
	private Label lblstatus;

	public void initialize() {
		trackingno.setText(null);
		lblfcity.setVisible(false);
		lblfstate.setVisible(false);
		lbltcity.setVisible(false);
		lbltstate.setVisible(false);
		lbltype.setVisible(false);
		lblweight.setVisible(false);
		lblamount.setVisible(false);
		lblstatus.setVisible(false);
		fcity.setVisible(false);
		fstate.setVisible(false);
		tcity.setVisible(false);
		tstate.setVisible(false);
		type.setVisible(false);
		weight.setVisible(false);
		amount.setVisible(false);
		status.setVisible(false);
	}

	public void ok() {

		// object to incorporate currency format
		DecimalFormat twoDecimal = new DecimalFormat("$0.00");
		if (trackingno.getText() == null || trackingno.getText().trim().equals(""))
		// Courier c = new Courier();
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("TrackingNo is empty!");
			alert.showAndWait();
			initialize();
		} else {
			try {
				Integer no = Integer.parseInt(trackingno.getText());
				
				
				
				Courier courier = new Courier();
				courier.setTrackingNo(no);

				CourierDAO cdao = new CourierDAO();
				courier = cdao.viewCourierByTrackingNo(courier);
				if (courier != null) {
					lblfcity.setVisible(true);
					lblfstate.setVisible(true);
					lbltcity.setVisible(true);
					lbltstate.setVisible(true);
					lbltype.setVisible(true);
					lblweight.setVisible(true);
					lblamount.setVisible(true);
					lblstatus.setVisible(true);
					fcity.setVisible(true);
					fstate.setVisible(true);
					tcity.setVisible(true);
					tstate.setVisible(true);
					type.setVisible(true);
					weight.setVisible(true);
					amount.setVisible(true);
					status.setVisible(true);
					fcity.setText(courier.getSourceLoc().getCity());
					fstate.setText(courier.getSourceLoc().getState());
					tcity.setText(courier.getDestLoc().getCity());
					tstate.setText(courier.getDestLoc().getState());
					type.setText(courier.getCourierType());
					weight.setText(courier.getWeight().toString());
					amount.setText(twoDecimal.format(courier.getRate()));
					status.setText(courier.getStatus());

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Courier does not exist.");
					alert.showAndWait();
					initialize();
				}
			} catch (NumberFormatException e) {
				System.out.println("Number format error");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Number format error. Only numeric values allowed");
				alert.showAndWait();
				initialize();

			}
		}
	}
}// end class
