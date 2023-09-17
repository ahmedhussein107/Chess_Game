package views;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

import game.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static Stage window;
	public static Scene scene = new Scene(new Group());
	public final static int width = 1280 , height = 720;
	public static ArrayList<Player> allPlayers;
	
	@Override
	public void init() {
		try {
			Font.loadFont(new File("assets/Orbitron-Bold.ttf").toURI().toURL().toExternalForm(), 10);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		window = arg0;
		window.setFullScreen(true);
		window.setFullScreenExitHint("");
		window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		window.setScene(scene);
		allPlayers = new ArrayList<>();
		Scanner sc = new Scanner(new File("assests/dataBase.txt"));
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			Player player = new Player(line);
			allPlayers.add(player);
		}
		if(allPlayers.size() == 0) {
			scene.setRoot((new SigningScene()).getRoot());
		}
		else {
			scene.setRoot((new FirstScene()).getRoot());
		}
		window.show();
	}
	
	public static Image loadImage(String name) {
		Image i = null;
		try {
			i = new Image(new File("").toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static ImageView createImageView(String name) {
		ImageView imageView = new ImageView(loadImage(name));

		imageView.setFitWidth(width);
		imageView.setFitHeight(height);

		return imageView;
	}
	

}
