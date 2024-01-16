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

public class Level1 {
    public MediaPlayer mediaPlayerForGunShot = new MediaPlayer(new Media(new File("effects/GunShot.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForDuckFall = new MediaPlayer(new Media(new File("effects/DuckFalls.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForLevelCompleted = new MediaPlayer(new Media(new File("effects/LevelCompleted.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForGameOver =  new MediaPlayer(new Media(new File("effects/GameOver.mp3").toURI().toString()));
    public boolean flag = true; //it is a flag for prevent to press enter more than once while level completed
    public boolean flag2 = true; //it is a flag for prevent to press enter more than once while game over

    private static final int MAX_BULLETS = 3;

    private int bulletCount = MAX_BULLETS;
    // check if there is a duck where you hit
    private void checkCollision(StackPane root,Scene scene,Stage primaryStage,Text instructionText,Duck duckForLevel1,Messages messages) {
        if (duckForLevel1.getDuckImageView() != null && duckForLevel1.getDuckImageView().getBoundsInParent().
                intersects(SelectScreen.crosshairImageView.getBoundsInParent())) { // This checks are there any bound intersects between cross_hair and duck
            duckForLevel1.setGameOver(true);
            mediaPlayerForDuckFall.stop();
            mediaPlayerForDuckFall.play();
            mediaPlayerForLevelCompleted.play();

            //Stop the duck animation because duck is hit
            duckForLevel1.stopAnimationTimer();

            //Get the position of the where duck is hitted
            double duckX = duckForLevel1.getDuckImageView().getTranslateX();
            double duckY = duckForLevel1.getDuckImageView().getTranslateY();

            //Remove the duck  image from the root pane
            root.getChildren().remove(duckForLevel1.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_black/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel1.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel1.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds with hit image
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);

                ImageView fallingDuckImageView = new ImageView("duck_black/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel1.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel1.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel1.getDuckImageView().getFitHeight()); // fall duck
                fallingTransition.setOnFinished(fallingEvent -> {
                    //Remove the falling duck image from the root pane
                    root.getChildren().remove(fallingDuckImageView);
                });
                root.getChildren().removeAll(SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                root.getChildren().addAll(fallingDuckImageView,SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                fallingTransition.play(); // falling animation stars at here
                Text instructionsText = messages.WinMessage();
                Label flashingText = messages.createFlashingTextLabelwinmessage("Press ENTER to play next level");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                root.getChildren().addAll(instructionsText,flashingText);
                scene.setOnKeyPressed(level1Collision -> {
                    if (level1Collision.getCode() == KeyCode.ENTER && flag) { // go to next level
                        flag = false;
                        root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel1.getDuckImageView(),flashingText,
                                instructionText,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                        mediaPlayerForDuckFall.stop();
                        mediaPlayerForLevelCompleted.stop();
                        Duck duckForLevel2 = new Duck();
                        duckForLevel2.CreateDuck(false, DuckHunt.WINDOW_WIDTH/2.0, // create new level's duck
                                DuckHunt.WINDOW_HEIGHT/1.5, DuckHunt.WINDOW_WIDTH/100.0,
                                DuckHunt.WINDOW_HEIGHT/100.0,true,true,1,-1);
                        Level2 level2 = new Level2();
                        level2.startGameLoop(root,primaryStage,duckForLevel2,messages); // start new level
                        duckForLevel2.startDuckAnimation(); // start animation for new level
                    }
                });
            });
            pauseTransition.play();

        }
    }
    // starts new game loop
    public void startGameLoop(StackPane root, Stage primaryStage,Duck duckForLevel1,Messages messages) {
        mediaPlayerForGunShot.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForDuckFall.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForGameOver.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForLevelCompleted.setVolume(DuckHunt.scaleForMusic);
        duckForLevel1.loadDuckImages("duck_black",4,7); //collecting duck images for animation
        Image duckImage = new Image("duck_black/4.png");
        duckForLevel1.setDuckImageView(new ImageView(duckImage));
        duckForLevel1.getDuckImageView().setFitWidth(duckImage.getWidth() * DuckHunt.WINDOW_WIDTH/171.4); // set duck size X axis
        duckForLevel1.getDuckImageView().setFitHeight(duckImage.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);// set duck size Y axis
        duckForLevel1.getDuckImageView().setPreserveRatio(true);
        Text instructionsText = messages.BulletAndLevelMessage("LEVEL1",bulletCount);
        root.getChildren().addAll(duckForLevel1.getDuckImageView(),SelectScreen.foregroundImageView,SelectScreen.crosshairImageView,instructionsText);

        Scene scene = root.getScene();
        // make same cross-hair picture position and mouse position
        scene.setOnMouseMoved(eventlevel1Move -> {
            double crosshairX = eventlevel1Move.getX() - SelectScreen.crosshairImageView.getFitWidth() - DuckHunt.WINDOW_WIDTH/2.30;
            double crosshairY = eventlevel1Move.getY() - SelectScreen.crosshairImageView.getFitHeight() - DuckHunt.WINDOW_HEIGHT/2.28;
            SelectScreen.crosshairImageView.setTranslateX(crosshairX);
            SelectScreen.crosshairImageView.setTranslateY(crosshairY);
            primaryStage.getScene().setCursor(javafx.scene.Cursor.NONE);
        });
        scene.setOnMouseClicked(eventlevel1MouseClick -> {
            if (bulletCount > 0 && !duckForLevel1.isGameOver()){ //check whether game is over
                mediaPlayerForGunShot.stop();
                mediaPlayerForGunShot.play();
                bulletCount--; // decrease bullet
                instructionsText.setText("LEVEL1\t\t\t\t\t" + "Ammo Left: "+ bulletCount); // update instructions texts
                checkCollision(root,scene,primaryStage,instructionsText,duckForLevel1,messages); // go to check collision
            }
            if (bulletCount == 0 && !duckForLevel1.isGameOver()){ //game lose
                Text instructionsTextLost = messages.GameOverMessageLabel("GAME OVER!");
                Label flashingText = messages.createFlashingTextLabelwinmessage("Press ENTER to play again\nPress ESC to exit");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                root.getChildren().addAll(instructionsTextLost,flashingText);
                mediaPlayerForGameOver.play();
                flag2 = true;
                scene.setOnKeyPressed(eventlevel1 -> {
                    if (eventlevel1.getCode() == KeyCode.ENTER && flag2) { //starts new game
                        flag2 = false;
                        mediaPlayerForGunShot.stop();
                        mediaPlayerForGameOver.stop();
                        root.getChildren().removeAll(instructionsTextLost,duckForLevel1.getDuckImageView(),flashingText
                                ,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView,instructionsText);
                        bulletCount = MAX_BULLETS;
                        Duck newDuck = new Duck();
                        // create new duck for new game
                        newDuck.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.0,
                                DuckHunt.WINDOW_WIDTH/150.0,0,true,
                                false,1,-1);
                        startGameLoop(root, primaryStage,newDuck,messages); // start game
                        newDuck.startDuckAnimation(); // start animation
                    }
                    else if (eventlevel1.getCode() == KeyCode.ESCAPE){ // go home screen
                        DuckHunt.currentScreen = 0;
                        root.getChildren().removeAll(instructionsText,instructionsTextLost,flashingText,duckForLevel1.getDuckImageView(),
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
                duckForLevel1.handle(now);
            }
        };

        gameLoop.start();
    }



}
