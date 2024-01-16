import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class SelectScreen  {
    public  MediaPlayer mediaPlayerForIntro = new MediaPlayer(new Media(new File("effects/Intro.mp3").toURI().toString()));
    public boolean flag = true; //it is a flag for prevent to press enter more than once
    private int handleController = 0;
    static ImageView backgroundImageView;
    static ImageView foregroundImageView;
    static ImageView crosshairImageView;
    public static int currentBackgroundIndex = 0;
    public final String[] backgrounds = {
            "background/1.png",
            "background/2.png",
            "background/3.png",
            "background/4.png",
            "background/5.png",
            "background/6.png"
    };
    private  final String[] foregrounds = {
            "foreground/1.png",
            "foreground/2.png",
            "foreground/3.png",
            "foreground/4.png",
            "foreground/5.png",
            "foreground/6.png"
    };
    private int currentCrosshairIndex = 0;
    private final String[] crosshairs = {
            "crosshair/1.png",
            "crosshair/2.png",
            "crosshair/3.png",
            "crosshair/4.png",
            "crosshair/5.png",
            "crosshair/6.png",
            "crosshair/7.png"
    };
    private int currentScreen;
    void showBackgroundAndCrosshairSelection(StackPane root, Stage primaryStage, MediaPlayer media) {
        mediaPlayerForIntro.setVolume(0.5*DuckHunt.scaleForMusic);
        DuckHunt screen = new DuckHunt();
        Level1 level1 = new Level1();
        this.currentScreen = DuckHunt.currentScreen;
        backgroundImageView = createBackgroundImageView(backgrounds[currentBackgroundIndex]);
        crosshairImageView = createCrosshairImageView(crosshairs[currentCrosshairIndex]);
        foregroundImageView = createForegroundImageView(foregrounds[currentBackgroundIndex]);

        Text instructionsText = new Text("Use arrow keys to navigate options\nPress ENTER to start the game\nPress ESC to exit");
        instructionsText.setFont(Font.font("Arial", FontWeight.BOLD,  DuckHunt.WINDOW_WIDTH / 25.0));
        instructionsText.setFill(Color.ORANGE);
        StackPane.setAlignment(instructionsText, Pos.TOP_CENTER);

        root.getChildren().addAll(backgroundImageView, crosshairImageView, instructionsText);

        Scene scene = backgroundImageView.getScene();

        scene.setOnKeyPressed(eventSelect -> {
            if (handleController == 0){ // it is a controller to use background selection once
                handleKeyPress(eventSelect.getCode());
            }
            if (eventSelect.getCode() == KeyCode.ENTER && flag) {
                flag = false;
                handleController = 1;
                if (media != null){
                    media.stop();
                }
                mediaPlayerForIntro.stop();
                mediaPlayerForIntro.play();
                mediaPlayerForIntro.setOnEndOfMedia(() -> {
                    currentScreen = 2;
                    root.getChildren().removeAll(instructionsText,crosshairImageView);
                    Duck duckForLevel1 = new Duck();
                    Messages messages = new Messages();
                    // this is where create a duck from duck class
                    duckForLevel1.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.0,
                            DuckHunt.WINDOW_WIDTH/150.0,
                            0,true,false,1,-1);
                    level1.startGameLoop(root, primaryStage,duckForLevel1,messages); // start game
                    duckForLevel1.startDuckAnimation(); // start duck animation
                });

            } else if (eventSelect.getCode() == KeyCode.ESCAPE && flag) {
                media.stop();
                this.currentScreen = 0;
                DuckHunt.currentScreen = 0; //it resets background and cross-hair selection
                currentCrosshairIndex = 0;
                currentBackgroundIndex = 0;
                root.getChildren().removeAll(backgroundImageView, crosshairImageView, instructionsText);
                screen.start(primaryStage);
                handleController = 0;
            }
        });
    }
    // this is where the background is created
    private ImageView createBackgroundImageView(String backgroundImage) {
        Image background = new Image(backgroundImage);
        ImageView backgroundImageView = new ImageView(background);
        backgroundImageView.setFitWidth(DuckHunt.WINDOW_WIDTH);
        backgroundImageView.setFitHeight(DuckHunt.WINDOW_HEIGHT);
        backgroundImageView.setPreserveRatio(true);

        Rectangle clip = new Rectangle(DuckHunt.WINDOW_WIDTH, DuckHunt.WINDOW_HEIGHT);
        backgroundImageView.setClip(clip);
        return backgroundImageView;
    }
    // this is where the cross-hair is created
    private ImageView createCrosshairImageView(String crosshairImage) {
        Image crosshair = new Image(crosshairImage);
        ImageView crosshairImageView = new ImageView(crosshair);
        crosshairImageView.setFitWidth(DuckHunt.WINDOW_WIDTH/15.0);
        crosshairImageView.setFitHeight(DuckHunt.WINDOW_HEIGHT/15.0);
        crosshairImageView.setPreserveRatio(true);
        StackPane.setAlignment(crosshairImageView, Pos.CENTER);
        return crosshairImageView;
    }
    // this is where the foreground is created
    private ImageView createForegroundImageView(String foregroundImage) {
        Image foreground = new Image(foregroundImage);
        ImageView foregroundImageView = new ImageView(foreground);
        foregroundImageView.setFitWidth(DuckHunt.WINDOW_WIDTH);
        foregroundImageView.setFitHeight(DuckHunt.WINDOW_HEIGHT);
        foregroundImageView.setPreserveRatio(true);

        Rectangle clip = new Rectangle(DuckHunt.WINDOW_WIDTH, DuckHunt.WINDOW_HEIGHT);
        foregroundImageView.setClip(clip);
        return foregroundImageView;
    }
    // this is where the background and cross-hair are selected
    private void handleKeyPress(KeyCode keyCode) {
        switch (keyCode) {
            case LEFT:
                if (currentScreen == 1) { //current screen 1 means select screen
                    currentBackgroundIndex = (currentBackgroundIndex - 1 + backgrounds.length) % backgrounds.length;
                    backgroundImageView.setImage(new Image(backgrounds[currentBackgroundIndex])); //I got the mod of the number so, I can scroll through the list
                    foregroundImageView.setImage(new Image(foregrounds[currentBackgroundIndex]));
                }
                break;
            case RIGHT:
                if (currentScreen == 1) {
                    currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.length;
                    backgroundImageView.setImage(new Image(backgrounds[currentBackgroundIndex]));
                    foregroundImageView.setImage(new Image(foregrounds[currentBackgroundIndex]));
                }
                break;
            case UP:
                if (currentScreen == 1) {
                    currentCrosshairIndex = (currentCrosshairIndex - 1 + crosshairs.length) % crosshairs.length;
                    crosshairImageView.setImage(new Image(crosshairs[currentCrosshairIndex]));
                }
                break;
            case DOWN:
                if (currentScreen == 1) {
                    currentCrosshairIndex = (currentCrosshairIndex + 1) % crosshairs.length;
                    crosshairImageView.setImage(new Image(crosshairs[currentCrosshairIndex]));
                }
                break;

        }
    }

}
