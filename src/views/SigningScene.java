package views;

import java.io.PrintWriter;

import game.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class SigningScene {
	private StackPane root = new StackPane();
	private ImageView wallpaper = Main.createImageView("");
	private VBox vbox = new VBox(10);
	private TextArea textArea = new TextArea();
	private Label label = new Label();
	private Button sign = new Button("Sign Up");
	private Label errorMessages = new Label();
	private PrintWriter pw;
	
	public StackPane getRoot() throws Exception {
		pw = new PrintWriter("assests/dataBase.txt");
		createRoot();
		return root;
	}
	
	private void createRoot() {
		createLabel();
		createTextArea();
		createButton();
		errorMessages.setStyle("-fx-background-radius: 5;"
				+ "-fx-font-size: 15px;"
				+ "-fx-text-fill : #000000;");
		vbox.getChildren().addAll(textArea , sign , errorMessages);
		label.setAlignment(Pos.BASELINE_CENTER);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		root.getChildren().addAll(wallpaper , label , vbox);
		root.setStyle("-fx-background-color: #613726;");
	}
	
	private void createLabel() {
		label.setText("Welcome To Our Game!!\n\n\nYou Don't Wanna Miss A Move!");
		label.setTextAlignment(TextAlignment.CENTER);
		label.setStyle("-fx-font-size : 50px ;"
				+ "-fx-text-fill : #E6B39E ;"
				+ "-fx-font-family :\"Orbitron\";");
	}
	
	private void createTextArea() {
		textArea.setStyle("-fx-background-color: #F2B47E;"
				+ "-fx-background-radius: 5;"
				+ "-fx-font-size: 15px ;");
		textArea.setMaxSize(200, 40);
	}
	
	private void createButton() {
		sign.setStyle("-fx-background-color: #AD6244;"
				+ " -fx-padding: 15; "
				+ "-fx-background-radius: 5;"
				+ "-fx--fx-font-size : 30px ;"
				+ "-fx-font-family :\"Orbitron\";");
		sign.setOnMouseEntered(e -> {
			sign.setStyle("-fx-background-color: #E07E58;"
					+ " -fx-padding: 15; "
					+ "-fx-background-radius: 5;"
					+ "-fx--fx-font-size : 30px ;"
					+ "-fx-font-family :\"Orbitron\";");
		});
		sign.setOnMouseExited(e -> {
			sign.setStyle("-fx-background-color: #AD6244;"
					+ " -fx-padding: 15; "
					+ "-fx-background-radius: 5;"
					+ "-fx--fx-font-size : 30px ;"
					+ "-fx-font-family :\"Orbitron\";");
		});
		sign.setOnMouseClicked(e -> {
			String text = textArea.getText();
			if(text.equals("")) {
				errorMessages.setText("You haven't entered anything!");
			}
			else if(Main.allPlayers.contains(new Player(text))) {
				errorMessages.setText("A player with this name already exists!");
			}
			else {
				pw.println(text);
				Main.scene.setRoot((new FirstScene()).getRoot());
			}
		});
	}

}
