import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Level2 {
    public MediaPlayer mediaPlayerForGunShot =  new MediaPlayer(new Media(new File("effects/GunShot.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForDuckFall  = new MediaPlayer(new Media(new File("effects/DuckFalls.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForLevelComplated  = new MediaPlayer(new Media(new File("effects/LevelCompleted.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForGameOver  = new MediaPlayer(new Media(new File("effects/GameOver.mp3").toURI().toString()));
    public boolean flag = true;
    public boolean flag2 = true;

    private static final int MAX_BULLETS = 3;

    private int bulletCount = MAX_BULLETS;

    private void checkCollision(StackPane root,Scene scene,Stage primaryStage,Text instructionText,Duck duckForLevel2,Messages messages) {
        if (duckForLevel2.getDuckImageView() != null && duckForLevel2.getDuckImageView().getBoundsInParent().intersects(SelectScreen.crosshairImageView.getBoundsInParent())) {
            duckForLevel2.setGameOver(true);
            mediaPlayerForDuckFall.play();
            mediaPlayerForLevelComplated.play();

            // Stop the duck animation
            duckForLevel2.stopAnimationTimer();

            // Get the current position of the duck
            double duckX = duckForLevel2.getDuckImageView().getTranslateX();
            double duckY = duckForLevel2.getDuckImageView().getTranslateY();

            // Remove the duck from the root pane
            root.getChildren().remove(duckForLevel2.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_blue/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel2.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel2.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds with hit image
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);

                ImageView fallingDuckImageView = new ImageView("duck_blue/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel2.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel2.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel2.getDuckImageView().getFitHeight()); // fall duck for 1.5 seconds
                fallingTransition.setOnFinished(fallingEvent -> {
                    // Remove the falling duck image from the root pane
                    root.getChildren().remove(fallingDuckImageView);
                });
                root.getChildren().removeAll(SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                root.getChildren().addAll(fallingDuckImageView,SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                fallingTransition.play();
                Text instructionsText = messages.WinMessage();
                Label flashingText = messages.createFlashingTextLabelwinmessage("Press ENTER to play next level");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                root.getChildren().addAll(instructionsText,flashingText);
                scene.setOnKeyPressed(eventlevel2Collision-> {
                    if (eventlevel2Collision.getCode() == KeyCode.ENTER && flag) {
                        flag = false;
                        root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel2.getDuckImageView(),flashingText,
                                instructionText,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                        mediaPlayerForDuckFall.stop();
                        mediaPlayerForLevelComplated.stop();
                        Duck duckForLevel31 = new Duck();
                        Duck duckForLevel32 = new Duck();
                        duckForLevel31.CreateDuck(false, DuckHunt.WINDOW_WIDTH/4.0,
                                DuckHunt.WINDOW_HEIGHT/12.0, DuckHunt.WINDOW_WIDTH/100.0,
                                0,false,true,1,1);
                        duckForLevel32.CreateDuck(false, DuckHunt.WINDOW_WIDTH/3.0, // creates new ducks for new level
                                DuckHunt.WINDOW_HEIGHT/2.4, DuckHunt.WINDOW_WIDTH/100.0,
                                0,true,true,1,1);
                        Level3 level3 = new Level3();
                        level3.startGameLoop(root,primaryStage,duckForLevel31,duckForLevel32,messages); // starts game loop
                        duckForLevel31.startDuckAnimation(); // starts animations
                        duckForLevel32.startDuckAnimation();

                    }
                });
            });
            pauseTransition.play();

        }
    }
    public void startGameLoop(StackPane root, Stage primaryStage,Duck duckForLevel2,Messages messages) {
        mediaPlayerForGunShot.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForDuckFall.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForGameOver.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForLevelComplated.setVolume(DuckHunt.scaleForMusic);
        duckForLevel2.loadDuckImages("duck_blue",1,4);
        Image duckImage = new Image("duck_blue/1.png");
        duckForLevel2.setDuckImageView(new ImageView(duckImage));
        duckForLevel2.getDuckImageView().setFitWidth(duckImage.getWidth() * DuckHunt.WINDOW_WIDTH/171.4);
        duckForLevel2.getDuckImageView().setFitHeight(duckImage.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);
        duckForLevel2.getDuckImageView().setPreserveRatio(true);
        Text instructionsText = messages.BulletAndLevelMessage("LEVEL2",bulletCount);
        root.getChildren().addAll(duckForLevel2.getDuckImageView(),SelectScreen.foregroundImageView,SelectScreen.crosshairImageView,instructionsText);

        Scene scene = root.getScene();
        // make same cross-hair picture and mouse position
        scene.setOnMouseMoved(eventlevel2Mousemove -> {
            double crosshairX = eventlevel2Mousemove.getX() - SelectScreen.crosshairImageView.getFitWidth() - DuckHunt.WINDOW_WIDTH/2.30;
            double crosshairY = eventlevel2Mousemove.getY() - SelectScreen.crosshairImageView.getFitHeight() - DuckHunt.WINDOW_HEIGHT/2.28;
            SelectScreen.crosshairImageView.setTranslateX(crosshairX);
            SelectScreen.crosshairImageView.setTranslateY(crosshairY);
            primaryStage.getScene().setCursor(javafx.scene.Cursor.NONE);
        });

        scene.setOnMouseClicked(eventlevel2MouseClick -> {
            if (bulletCount >0 && !duckForLevel2.isGameOver()){
                mediaPlayerForGunShot.stop();
                mediaPlayerForGunShot.play();
                bulletCount--;
                instructionsText.setText("LEVEL2\t\t\t\t\t" + "Ammo Left: "+ bulletCount);
                checkCollision(root,scene,primaryStage,instructionsText,duckForLevel2,messages);
            }
            if (bulletCount == 0 && !duckForLevel2.isGameOver()){
                Text instructionsTextLost = messages.GameOverMessageLabel("GAME OVER!");
                Label flashingText = messages.createFlashingTextLabelwinmessage("Press ENTER to play again\nPress ESC to exit");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                root.getChildren().addAll(instructionsTextLost,flashingText);
                mediaPlayerForGameOver.play();
                flag2 = true;
                scene.setOnKeyPressed(eventlevel2 -> {
                    if (eventlevel2.getCode() == KeyCode.ENTER && flag2) {
                        flag2 = false;
                        mediaPlayerForGameOver.stop();
                        root.getChildren().removeAll(instructionsTextLost,duckForLevel2.getDuckImageView(),flashingText
                                ,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView,instructionsText);
                        bulletCount = MAX_BULLETS;
                        Level1 level1= new Level1();
                        Duck duckForLevel1 = new Duck();
                        duckForLevel1.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.8,
                                DuckHunt.WINDOW_WIDTH/150.0,0,
                                true,false,1,-1);
                        level1.startGameLoop(root, primaryStage,duckForLevel1,messages);
                        duckForLevel1.startDuckAnimation();
                    }
                    else if (eventlevel2.getCode() == KeyCode.ESCAPE){

                        DuckHunt.currentScreen = 0;
                        root.getChildren().removeAll(instructionsText,instructionsTextLost,flashingText,duckForLevel2.getDuckImageView(),
                                SelectScreen.crosshairImageView,SelectScreen.foregroundImageView,SelectScreen.backgroundImageView);
                        primaryStage.close();
                        DuckHunt screen = new DuckHunt();
                        screen.start(primaryStage);

                    }
                });
            }
        });

        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
                duckForLevel2.handle(now);
            }
        };

        gameLoop.start();
    }



}
