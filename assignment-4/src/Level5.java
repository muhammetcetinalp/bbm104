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

public class Level5{
    public MediaPlayer mediaPlayerForGunShot =  new MediaPlayer(new Media(new File("effects/GunShot.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForDuckFall =  new MediaPlayer(new Media(new File("effects/DuckFalls.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForLevelComplated =  new MediaPlayer(new Media(new File("effects/LevelCompleted.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForGameOver =  new MediaPlayer(new Media(new File("effects/GameOver.mp3").toURI().toString()));
    public boolean flag = true;
    public boolean flag2 = true;
    private static final int MAX_BULLETS = 9;
    private int bulletCount = MAX_BULLETS;
    private void checkCollision(StackPane root,Scene scene,Stage primaryStage,Text instructionText,Duck duckForLevel51,
                                Duck duckForLevel52,Duck duckForLevel53,Messages messages) {
        if (duckForLevel51.getDuckImageView() != null && duckForLevel51.getDuckImageView().getBoundsInParent().
                intersects(SelectScreen.crosshairImageView.getBoundsInParent())) {
            mediaPlayerForDuckFall.stop();
            mediaPlayerForDuckFall.play();
            duckForLevel51.setGameOver(true);
            // Stop the duck animation
            duckForLevel51.stopAnimationTimer();

            // Get the current position of the duck
            double duckX = duckForLevel51.getDuckImageView().getTranslateX();
            double duckY = duckForLevel51.getDuckImageView().getTranslateY();

            // Remove the duck from the root pane
            root.getChildren().remove(duckForLevel51.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_black/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel51.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel51.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);



                ImageView fallingDuckImageView = new ImageView("duck_black/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel51.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel51.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel51.getDuckImageView().getFitHeight()); // fall duck
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
                if (duckForLevel51.isGameOver() && duckForLevel52.isGameOver() && duckForLevel53.isGameOver()){
                    root.getChildren().addAll(instructionsText,flashingText);
                    mediaPlayerForLevelComplated.play();
                    scene.setOnKeyPressed(eventlevel5collision1 -> {
                        if (eventlevel5collision1.getCode() == KeyCode.ENTER && flag) {
                            flag = false;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel51.getDuckImageView(),flashingText,
                                    instructionText,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            mediaPlayerForLevelComplated.stop();
                            Duck duckForLevel61 = new Duck();
                            Duck duckForLevel62 = new Duck();
                            Duck duckForLevel63 = new Duck();
                            duckForLevel61.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/1.7,
                                    DuckHunt.WINDOW_WIDTH/85.7, DuckHunt.WINDOW_HEIGHT/75.0,
                                    true,true,1,-1);
                            duckForLevel62.CreateDuck(false, DuckHunt.WINDOW_WIDTH/2.4
                                    , DuckHunt.WINDOW_HEIGHT/1.7, DuckHunt.WINDOW_WIDTH/75.0,       // Create the ducks
                                    DuckHunt.WINDOW_HEIGHT/85.7,true,true,1,-1);
                            duckForLevel63.CreateDuck(false, DuckHunt.WINDOW_WIDTH/2.4,
                                    DuckHunt.WINDOW_HEIGHT/2.4, DuckHunt.WINDOW_WIDTH/60.0,
                                    DuckHunt.WINDOW_HEIGHT/120.0,true,true,1,-1);
                            Level6 level6 = new Level6();
                            level6.startGameLoop(root,primaryStage,duckForLevel61,duckForLevel62,duckForLevel63,messages);
                            duckForLevel61.startDuckAnimation();
                            duckForLevel62.startDuckAnimation();
                            duckForLevel63.startDuckAnimation();
                        }
                    });
                }

            });
            pauseTransition.play();

        }
    }
    private void checkCollision2(StackPane root,Scene scene,Stage primaryStage,Text instructionText,Duck duckForLevel51
            ,Duck duckForLevel52,Duck duckForLevel53,Messages messages) {
        if (duckForLevel52.getDuckImageView() != null && duckForLevel52.getDuckImageView().getBoundsInParent().
                intersects(SelectScreen.crosshairImageView.getBoundsInParent())) {
            mediaPlayerForDuckFall.stop();
            mediaPlayerForDuckFall.play();
            duckForLevel52.setGameOver(true);
            // Stop the duck animation
            duckForLevel52.stopAnimationTimer();

            // Get the current position of the duck
            double duckX = duckForLevel52.getDuckImageView().getTranslateX();
            double duckY = duckForLevel52.getDuckImageView().getTranslateY();

            // Remove the duck from the root pane
            root.getChildren().remove(duckForLevel52.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_blue/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel52.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel52.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);


                ImageView fallingDuckImageView = new ImageView("duck_blue/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel52.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel52.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel52.getDuckImageView().getFitHeight()); // fall duck
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
                if (duckForLevel52.isGameOver() && duckForLevel51.isGameOver() && duckForLevel53.isGameOver()){
                    root.getChildren().addAll(instructionsText,flashingText);
                    mediaPlayerForLevelComplated.play();
                    scene.setOnKeyPressed(eventlevel5collision2 -> {
                        if (eventlevel5collision2.getCode() == KeyCode.ENTER && flag) {
                            flag = false;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel52.getDuckImageView(),flashingText,
                                    instructionText,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            mediaPlayerForLevelComplated.stop();
                            Duck duckForLevel61 = new Duck();
                            Duck duckForLevel62 = new Duck();
                            Duck duckForLevel63 = new Duck();
                            duckForLevel61.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/1.7,
                                    DuckHunt.WINDOW_WIDTH/85.7, DuckHunt.WINDOW_HEIGHT/75.0,
                                    true,true,1,-1);
                            duckForLevel62.CreateDuck(false, DuckHunt.WINDOW_WIDTH/2.4,
                                    DuckHunt.WINDOW_HEIGHT/1.7, DuckHunt.WINDOW_WIDTH/75.0,        // Create the ducks
                                    DuckHunt.WINDOW_HEIGHT/85.7,true,true,1,-1);
                            duckForLevel63.CreateDuck(false, DuckHunt.WINDOW_WIDTH/2.4,
                                    DuckHunt.WINDOW_HEIGHT/2.4, DuckHunt.WINDOW_WIDTH/60.0,
                                    DuckHunt.WINDOW_HEIGHT/120.0,true,true,1,-1);
                            Level6 level6 = new Level6();
                            level6.startGameLoop(root,primaryStage,duckForLevel61,duckForLevel62,duckForLevel63,messages);
                            duckForLevel61.startDuckAnimation();
                            duckForLevel62.startDuckAnimation();
                            duckForLevel63.startDuckAnimation();
                        }
                    });
                }
            });
            pauseTransition.play();

        }
    }
    private void checkCollision3(StackPane root,Scene scene,Stage primaryStage,Text instructionText,Duck duckForLevel51,
                                 Duck duckForLevel52,Duck duckForLevel53,Messages messages) {
        if (duckForLevel53.getDuckImageView() != null && duckForLevel53.getDuckImageView().getBoundsInParent().
                intersects(SelectScreen.crosshairImageView.getBoundsInParent())) {
            mediaPlayerForDuckFall.stop();
            mediaPlayerForDuckFall.play();
            duckForLevel53.setGameOver(true);
            // Stop the duck animation
            duckForLevel53.stopAnimationTimer();

            // Get the current position of the duck
            double duckX = duckForLevel53.getDuckImageView().getTranslateX();
            double duckY = duckForLevel53.getDuckImageView().getTranslateY();

            // Remove the duck from the root pane
            root.getChildren().remove(duckForLevel53.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_red/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel53.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel53.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);


                ImageView fallingDuckImageView = new ImageView("duck_red/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel53.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel53.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel53.getDuckImageView().getFitHeight()); // fall duck
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
                if (duckForLevel53.isGameOver() && duckForLevel51.isGameOver() && duckForLevel52.isGameOver()){
                    root.getChildren().addAll(instructionsText,flashingText);
                    mediaPlayerForLevelComplated.play();
                    scene.setOnKeyPressed(eventlevel5collision3 -> {
                        if (eventlevel5collision3.getCode() == KeyCode.ENTER && flag) {
                            flag = false;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel53.getDuckImageView(),flashingText,
                                    instructionText,SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            mediaPlayerForLevelComplated.stop();
                            Duck duckForLevel61 = new Duck();
                            Duck duckForLevel62 = new Duck();
                            Duck duckForLevel63 = new Duck();
                            duckForLevel61.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/1.7,
                                    DuckHunt.WINDOW_WIDTH/85.7, DuckHunt.WINDOW_HEIGHT/75.0,
                                    true,true,1,-1);
                            duckForLevel62.CreateDuck(false, DuckHunt.WINDOW_WIDTH/2.4,
                                    DuckHunt.WINDOW_HEIGHT/1.7, DuckHunt.WINDOW_WIDTH/75.0,        // Create the ducks
                                    DuckHunt.WINDOW_HEIGHT/85.7,true,true,1,-1);
                            duckForLevel63.CreateDuck(false, DuckHunt.WINDOW_WIDTH/2.4,
                                    DuckHunt.WINDOW_HEIGHT/2.4, DuckHunt.WINDOW_WIDTH/60.0,
                                    DuckHunt.WINDOW_HEIGHT/120.0,true,true,1,-1);
                            Level6 level6 = new Level6();
                            level6.startGameLoop(root,primaryStage,duckForLevel61,duckForLevel62,duckForLevel63,messages);
                            duckForLevel61.startDuckAnimation();
                            duckForLevel62.startDuckAnimation();
                            duckForLevel63.startDuckAnimation();
                        }
                    });
                }
            });
            pauseTransition.play();

        }
    }

    public void startGameLoop(StackPane root, Stage primaryStage,Duck duckForLevel51,Duck duckForLevel52,Duck duckForLevel53,Messages messages) {
        mediaPlayerForGunShot.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForDuckFall.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForGameOver.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForLevelComplated.setVolume(DuckHunt.scaleForMusic);
        duckForLevel51.loadDuckImages("duck_black",1,4);
        Image duckImage = new Image("duck_black/1.png");
        duckForLevel51.setDuckImageView(new ImageView(duckImage));
        duckForLevel51.getDuckImageView().setFitWidth(duckImage.getWidth() * DuckHunt.WINDOW_WIDTH/171.4);
        duckForLevel51.getDuckImageView().setFitHeight(duckImage.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);
        duckForLevel51.getDuckImageView().setPreserveRatio(true);

        duckForLevel52.loadDuckImages("duck_blue",1,4);
        Image duckImage2 = new Image("duck_blue/1.png");
        duckForLevel52.setDuckImageView(new ImageView(duckImage2));
        duckForLevel52.getDuckImageView().setFitWidth(duckImage2.getWidth() * DuckHunt.WINDOW_WIDTH/171.4);
        duckForLevel52.getDuckImageView().setFitHeight(duckImage2.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);
        duckForLevel52.getDuckImageView().setPreserveRatio(true);

        duckForLevel53.loadDuckImages("duck_red",1,4);
        Image duckImage3 = new Image("duck_red/1.png");
        duckForLevel53.setDuckImageView(new ImageView(duckImage3));
        duckForLevel53.getDuckImageView().setFitWidth(duckImage3.getWidth() * DuckHunt.WINDOW_WIDTH/171.4);
        duckForLevel53.getDuckImageView().setFitHeight(duckImage3.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);
        duckForLevel53.getDuckImageView().setPreserveRatio(true);

        Text instructionsText = messages.BulletAndLevelMessage("LEVEL5",bulletCount);
        root.getChildren().addAll(duckForLevel51.getDuckImageView(),duckForLevel52.getDuckImageView(),duckForLevel53.getDuckImageView(),
                SelectScreen.foregroundImageView,SelectScreen.crosshairImageView,instructionsText);

        Scene scene = root.getScene();
        // make same cross-hair picture and mouse position
        scene.setOnMouseMoved(eventlevel5MouseMove -> {
            double crosshairX = eventlevel5MouseMove.getX() - SelectScreen.crosshairImageView.getFitWidth() -
                    DuckHunt.WINDOW_WIDTH/2.30;
            double crosshairY = eventlevel5MouseMove.getY() - SelectScreen.crosshairImageView.getFitHeight() -
                    DuckHunt.WINDOW_HEIGHT/2.28;
            SelectScreen.crosshairImageView.setTranslateX(crosshairX);
            SelectScreen.crosshairImageView.setTranslateY(crosshairY);
            primaryStage.getScene().setCursor(javafx.scene.Cursor.NONE);
        });
        scene.setOnMouseClicked(eventlevel5MouseClick -> {
            if (bulletCount > 0 && (!duckForLevel51.isGameOver() || !duckForLevel52.isGameOver() || !duckForLevel53.isGameOver())){
                mediaPlayerForGunShot.stop();
                mediaPlayerForGunShot.play();
                bulletCount--;
                instructionsText.setText("LEVEL5\t\t\t\t\t" + "Ammo Left: "+ bulletCount);
                checkCollision(root,scene,primaryStage,instructionsText,duckForLevel51,duckForLevel52,duckForLevel53,messages);
                checkCollision2(root,scene,primaryStage,instructionsText,duckForLevel51,duckForLevel52,duckForLevel53,messages);
                checkCollision3(root,scene,primaryStage,instructionsText,duckForLevel51,duckForLevel52,duckForLevel53,messages);
            }

            if (bulletCount == 0 && (!duckForLevel51.isGameOver() || !duckForLevel52.isGameOver() || !duckForLevel53.isGameOver())){
                Text instructionsTextLost = messages.GameOverMessageLabel("GAME OVER!");
                Label flashingText = messages.createFlashingTextLabelwinmessage("Press ENTER to play again\nPress ESC to exit");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                root.getChildren().addAll(instructionsTextLost,flashingText);
                mediaPlayerForGameOver.play();
                flag2 = true;
                scene.setOnKeyPressed(eventlevel5 -> {
                    if (eventlevel5.getCode() == KeyCode.ENTER && flag2) {
                        flag2 = false;
                        root.getChildren().removeAll(instructionsTextLost,duckForLevel51.getDuckImageView(),
                                duckForLevel52.getDuckImageView(),flashingText,
                                duckForLevel53.getDuckImageView(),SelectScreen.crosshairImageView,
                                SelectScreen.foregroundImageView,instructionsText);
                        bulletCount = MAX_BULLETS;
                        Level1 level1= new Level1();
                        Duck duckForLevel1 = new Duck();
                        duckForLevel1.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.8,
                                DuckHunt.WINDOW_WIDTH/150.0,0,true,      // Create the duck start new game
                                false,1,-1);
                        level1.startGameLoop(root, primaryStage,duckForLevel1,messages);
                        duckForLevel1.startDuckAnimation();
                    }
                    else if (eventlevel5.getCode() == KeyCode.ESCAPE){ // go home screen
                        DuckHunt.currentScreen = 0;
                        root.getChildren().removeAll(instructionsText,instructionsTextLost,flashingText,duckForLevel51.getDuckImageView(),
                                duckForLevel52.getDuckImageView(),duckForLevel53.getDuckImageView(),
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
                duckForLevel51.handle(now);
                duckForLevel52.handle(now);
                duckForLevel53.handle(now);
            }
        };

        gameLoop.start();
    }



}
