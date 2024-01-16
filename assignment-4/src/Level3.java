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

public class Level3  {
    public MediaPlayer mediaPlayerForGunShot = new MediaPlayer(new Media(new File(
            "effects/GunShot.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForDuckFall = new MediaPlayer(new Media(new File(
            "effects/DuckFalls.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForLevelComplated =  new MediaPlayer(new Media(new File(
            "effects/LevelCompleted.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForGameOver =  new MediaPlayer(new Media(new File(
            "effects/GameOver.mp3").toURI().toString()));
    public boolean flag = true;
    public boolean flag2 = true;
    private static final int MAX_BULLETS = 6;
    private int bulletCount = MAX_BULLETS;
    private void checkCollision(StackPane root,Scene scene,Stage primaryStage,Text instructionText,Duck duckForLevel31,
                                Duck duckForLevel32,Messages messages) {
        if (duckForLevel31.getDuckImageView() != null && duckForLevel31.getDuckImageView().getBoundsInParent().
                intersects(SelectScreen.crosshairImageView.getBoundsInParent())) {
            mediaPlayerForDuckFall.stop();
            mediaPlayerForDuckFall.play();
            duckForLevel31.setGameOver(true);
            // Stop the duck animation
            duckForLevel31.stopAnimationTimer();

            // Get the current position of the duck
            double duckX = duckForLevel31.getDuckImageView().getTranslateX();
            double duckY = duckForLevel31.getDuckImageView().getTranslateY();

            // Remove the duck from the root pane
            root.getChildren().remove(duckForLevel31.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_black/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel31.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel31.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);


                ImageView fallingDuckImageView = new ImageView("duck_black/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel31.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel31.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel31.getDuckImageView().getFitHeight()); //fall duck for 1.5 seconds
                fallingTransition.setOnFinished(fallingEvent -> {
                    // Remove the falling duck image from the root pane
                    root.getChildren().remove(fallingDuckImageView);
                });
                root.getChildren().removeAll(SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                root.getChildren().addAll(fallingDuckImageView,SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                fallingTransition.play();
                root.getChildren().remove(hitImageView);
                Text instructionsText = messages.WinMessage();
                Label flashingText = messages.createFlashingTextLabelwinmessage("Press ENTER to play next level");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                if (duckForLevel31.isGameOver() && duckForLevel32.isGameOver()){
                    root.getChildren().addAll(instructionsText,flashingText);
                    mediaPlayerForLevelComplated.play();
                    scene.setOnKeyPressed(eventlevel3collision -> {
                        if (eventlevel3collision.getCode() == KeyCode.ENTER && flag) {
                            flag = false;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel31.getDuckImageView(),flashingText,
                                    instructionText,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            mediaPlayerForLevelComplated.stop();
                            Duck duckForLevel41 = new Duck();
                            Duck duckForLevel42 = new Duck();
                            duckForLevel41.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.0,
                                    DuckHunt.WINDOW_WIDTH/190.0, DuckHunt.WINDOW_HEIGHT/100.0,
                                    true,true,1,-1); // Create the ducks
                            duckForLevel42.CreateDuck(false, DuckHunt.WINDOW_WIDTH/2.0,
                                    DuckHunt.WINDOW_HEIGHT/2.4, DuckHunt.WINDOW_WIDTH/100.0,
                                    DuckHunt.WINDOW_HEIGHT/120.0,true,true,1,-1);
                            Level4 level4 = new Level4();
                            level4.startGameLoop(root,primaryStage,duckForLevel41,duckForLevel42,messages);
                            duckForLevel41.startDuckAnimation();
                            duckForLevel42.startDuckAnimation();
                        }
                    });
                }

            });
            pauseTransition.play();

        }
    }
    private void checkCollision2(StackPane root,Scene scene,Stage primaryStage,Text instructionText,Duck duckForLevel31,
                                 Duck duckForLevel32,Messages messages) {
        if (duckForLevel32.getDuckImageView() != null && duckForLevel32.getDuckImageView().getBoundsInParent().
                intersects(SelectScreen.crosshairImageView.getBoundsInParent())) {
            mediaPlayerForDuckFall.stop();
            mediaPlayerForDuckFall.play();
            duckForLevel32.setGameOver(true);

            // Stop the duck animation
            duckForLevel32.stopAnimationTimer();

            // Get the current position of the duck
            double duckX = duckForLevel32.getDuckImageView().getTranslateX();
            double duckY = duckForLevel32.getDuckImageView().getTranslateY();

            // Remove the duck from the root pane
            root.getChildren().remove(duckForLevel32.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_blue/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel32.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel32.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);

                ImageView fallingDuckImageView = new ImageView("duck_blue/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel32.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel32.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel32.getDuckImageView().getFitHeight()); // Fall duck
                fallingTransition.setOnFinished(fallingEvent -> {
                    // Remove the falling duck image from the root pane
                    root.getChildren().remove(fallingDuckImageView);
                });
                root.getChildren().removeAll(SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                root.getChildren().addAll(fallingDuckImageView,SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                fallingTransition.play();
                root.getChildren().remove(hitImageView);
                Text instructionsText = messages.WinMessage();
                Label flashingText = messages.createFlashingTextLabelwinmessage("Press ENTER to play next level");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                if (duckForLevel32.isGameOver() && duckForLevel31.isGameOver()){
                    root.getChildren().addAll(instructionsText,flashingText);
                    mediaPlayerForLevelComplated.play();
                    scene.setOnKeyPressed(eventlevel3collision2 -> {
                        if (eventlevel3collision2.getCode() == KeyCode.ENTER && flag) {
                            flag = false;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel32.getDuckImageView()
                                    ,flashingText,
                                    instructionText,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            mediaPlayerForLevelComplated.stop();
                            Duck duckForLevel41 = new Duck();
                            Duck duckForLevel42 = new Duck();
                            duckForLevel41.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.0,
                                    DuckHunt.WINDOW_WIDTH/90.0, DuckHunt.WINDOW_HEIGHT/100.0,
                                    true,true,1,-1);                 // Create the ducks
                            duckForLevel42.CreateDuck(false, DuckHunt.WINDOW_WIDTH/2.0,
                                    DuckHunt.WINDOW_HEIGHT/2.4, DuckHunt.WINDOW_WIDTH/100.0,
                                    DuckHunt.WINDOW_HEIGHT/120.0,true,true,1,-1);
                            Level4 level4 = new Level4();
                            level4.startGameLoop(root,primaryStage,duckForLevel41,duckForLevel42,messages);
                            duckForLevel41.startDuckAnimation();
                            duckForLevel42.startDuckAnimation();
                        }
                    });
                }
            });
            pauseTransition.play();

        }
    }

    public void startGameLoop(StackPane root, Stage primaryStage,Duck duckForLevel31,Duck duckForLevel32,Messages messages) {
        mediaPlayerForGunShot.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForDuckFall.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForGameOver.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForLevelComplated.setVolume(DuckHunt.scaleForMusic);
        duckForLevel31.loadDuckImages("duck_black",4,7); //collecting duck images for animation
        Image duckImage = new Image("duck_black/4.png");
        duckForLevel31.setDuckImageView(new ImageView(duckImage));
        duckForLevel31.getDuckImageView().setFitWidth(duckImage.getWidth() * DuckHunt.WINDOW_WIDTH/171.4);
        duckForLevel31.getDuckImageView().setFitHeight(duckImage.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);
        duckForLevel31.getDuckImageView().setPreserveRatio(true);

        duckForLevel32.loadDuckImages("duck_blue",4,7); //collecting duck images for animation
        Image duckImage2 = new Image("duck_blue/1.png");
        duckForLevel32.setDuckImageView(new ImageView(duckImage2));
        duckForLevel32.getDuckImageView().setFitWidth(duckImage2.getWidth() * DuckHunt.WINDOW_WIDTH/171.4);
        duckForLevel32.getDuckImageView().setFitHeight(duckImage2.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);
        duckForLevel32.getDuckImageView().setPreserveRatio(true);

        Text instructionsText = messages.BulletAndLevelMessage("LEVEL3",bulletCount);
        root.getChildren().addAll(duckForLevel31.getDuckImageView(),duckForLevel32.getDuckImageView(),
                SelectScreen.foregroundImageView,SelectScreen.crosshairImageView,instructionsText);

        Scene scene = root.getScene();
        // make same cross-hair picture and mouse position
        scene.setOnMouseMoved(eventlevel3MouseMove -> {
            double crosshairX = eventlevel3MouseMove.getX() - SelectScreen.crosshairImageView.getFitWidth() -
                    DuckHunt.WINDOW_WIDTH/2.30;
            double crosshairY = eventlevel3MouseMove.getY() - SelectScreen.crosshairImageView.getFitHeight() -
                    DuckHunt.WINDOW_HEIGHT/2.28;
            SelectScreen.crosshairImageView.setTranslateX(crosshairX);
            SelectScreen.crosshairImageView.setTranslateY(crosshairY);
            primaryStage.getScene().setCursor(javafx.scene.Cursor.NONE);
        });
        scene.setOnMouseClicked(eventlevel3MouseClicked -> {
            if (bulletCount > 0 && (!duckForLevel31.isGameOver() || !duckForLevel32.isGameOver())){
                mediaPlayerForGunShot.stop();
                mediaPlayerForGunShot.play();
                bulletCount--;
                instructionsText.setText("LEVEL3\t\t\t\t\t" + "Ammo Left: "+ bulletCount);
                checkCollision(root,scene,primaryStage,instructionsText,duckForLevel31,duckForLevel32,messages);
                checkCollision2(root,scene,primaryStage,instructionsText,duckForLevel31,duckForLevel32,messages);
            }
            if (bulletCount == 0 && (!duckForLevel31.isGameOver() || !duckForLevel32.isGameOver())){
                Text instructionsTextLost = messages.GameOverMessageLabel("GAME OVER!");
                Label flashingText = messages.createFlashingTextLabelwinmessage("Press ENTER to play again\nPress ESC to exit");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                root.getChildren().addAll(instructionsTextLost,flashingText);
                mediaPlayerForGameOver.play();
                flag2 = true;
                scene.setOnKeyPressed(eventlevel3 -> {
                    if (eventlevel3.getCode() == KeyCode.ENTER && flag2) {
                        flag2 = false;
                        mediaPlayerForGameOver.stop();
                        bulletCount = MAX_BULLETS;
                        root.getChildren().removeAll(instructionsTextLost,duckForLevel31.getDuckImageView(),duckForLevel32.getDuckImageView(),flashingText
                                ,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView,instructionsText);
                        Level1 level1= new Level1();
                        Duck duckForLevel1 = new Duck();
                        duckForLevel1.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.8,
                                DuckHunt.WINDOW_WIDTH/150.0,0,true,
                                false,1,-1);
                        level1.startGameLoop(root, primaryStage,duckForLevel1,messages);
                        duckForLevel1.startDuckAnimation();
                    }
                    else if (eventlevel3.getCode() == KeyCode.ESCAPE){
                        DuckHunt.currentScreen = 0;
                        root.getChildren().removeAll(instructionsText,instructionsTextLost,flashingText,duckForLevel31.getDuckImageView(),
                                duckForLevel32.getDuckImageView(),SelectScreen.crosshairImageView,
                                SelectScreen.foregroundImageView,SelectScreen.backgroundImageView);
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
                duckForLevel31.handle(now);
                duckForLevel32.handle(now);
            }
        };

        gameLoop.start();
    }



}
