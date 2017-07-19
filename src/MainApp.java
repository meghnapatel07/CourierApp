
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import controller.LoginController;

/**
 * Courier Management application
 * @author Meghna Patel
 *
 */

public class MainApp extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

		LoginController controller = loader.<LoginController> getController();
		controller.setDialogStage(primaryStage);
	} catch (Exception e) {
		e.printStackTrace();
	}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
