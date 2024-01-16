import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class DuckHunt extends Application {
    public  MediaPlayer mediaPlayerForTitle = new MediaPlayer(new Media(new File("effects/Title.mp3").toURI().toString()));
    public boolean flag = true; //it is a flag for prevent to press enter more than once
    private final Duration DURATION = Duration.seconds(0.5);
    static int currentScreen = 0;
    static int scale = 3;
    static double scaleForMusic=0.025;
    static final double WINDOW_WIDTH = 600*scale/3.0; // Here game window width and height
    static final double WINDOW_HEIGHT = 563 *scale/3.0;
    private ImageView welcomeImageView;
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        welcomeImageView = createWelcomeImageView();
        root.getChildren().add(welcomeImageView);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("HUBBM Duck Hunt"); // title
        setApplicationIcon(primaryStage); // icon

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); // Disable full screen mode
        primaryStage.setResizable(false); // prevent window resize

        primaryStage.show();
        mediaPlayerForTitle.setVolume(scaleForMusic);
        mediaPlayerForTitle.setCycleCount(AudioClip.INDEFINITE); // music is playing in infinity loop
        mediaPlayerForTitle.play();

        Label flashingText = createFlashingTextLabel();
        root.getChildren().add(flashingText);
        StackPane.setAlignment(flashingText, Pos.CENTER);
        SelectScreen select = new SelectScreen();
        scene.setOnKeyPressed(eventStart -> {
            if (currentScreen == 0) {
                if (eventStart.getCode() == KeyCode.ENTER && flag) {
                    flag = false;
                    root.getChildren().remove(welcomeImageView);
                    root.getChildren().remove(flashingText);
                    currentScreen = 1;
                    select.showBackgroundAndCrosshairSelection(root, primaryStage,mediaPlayerForTitle); // go to select screen
                } else if (eventStart.getCode() == KeyCode.ESCAPE) {
                    primaryStage.close(); // close the game screen
                }
            }
        });
    }
    // this is where label message is made
    private Label createFlashingTextLabel() {
        Label flashingTextLabel = new Label("PRESS ENTER TO PLAY\nPRESS ESC TO EXIT");
        flashingTextLabel.setFont(Font.font("Arial", FontWeight.BOLD, WINDOW_WIDTH/17.1));
        flashingTextLabel.setTextFill(Color.ORANGE);
        flashingTextLabel.setTranslateY(WINDOW_HEIGHT/6.0);

        FadeTransition fadeIn = new FadeTransition(DURATION, flashingTextLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(Animation.INDEFINITE);
        fadeIn.setAutoReverse(true); // this is oto-playing animation
        fadeIn.play();

        return flashingTextLabel;
    }
    // this is where start screen view is made
    private ImageView createWelcomeImageView() {
        Image welcome = new Image("welcome/1.png");
        ImageView welcomeImageView = new ImageView(welcome);
        welcomeImageView.setFitWidth(WINDOW_WIDTH);
        welcomeImageView.setFitHeight(WINDOW_HEIGHT);
        welcomeImageView.setPreserveRatio(true);
        return welcomeImageView;
    }
    // this is where icon view is made
    private void setApplicationIcon(Stage primaryStage) {
        Image icon = new Image("favicon/1.png");
        primaryStage.getIcons().add(icon);
    }
}
