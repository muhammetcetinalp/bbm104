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


public class Level6{
    public MediaPlayer mediaPlayerForGunShot =  new MediaPlayer(new Media(
            new File("effects/GunShot.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForDuckFall = new MediaPlayer(
            new Media(new File("effects/DuckFalls.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForGameOver =  new MediaPlayer(
            new Media(new File("effects/GameOver.mp3").toURI().toString()));
    public  MediaPlayer mediaPlayerForGameComplated =  new MediaPlayer(
            new Media(new File("effects/GameCompleted.mp3").toURI().toString()));
    public boolean flag = true;
    public boolean flag2 = true;
    private static final int MAX_BULLETS = 9;
    private int bulletCount = MAX_BULLETS;
    private void checkCollision(StackPane root,Scene scene,Stage primaryStage,Text instructionText,
                                Duck duckForLevel61,Duck duckForLevel62,Duck duckForLevel63,Messages messages) {
        if (duckForLevel61.getDuckImageView() != null && duckForLevel61.getDuckImageView().getBoundsInParent().
                intersects(SelectScreen.crosshairImageView.getBoundsInParent())) {
            mediaPlayerForDuckFall.stop();
            mediaPlayerForDuckFall.play();
            duckForLevel61.setGameOver(true);
            // Stop the duck animation
            duckForLevel61.stopAnimationTimer();

            // Get the current position of the duck
            double duckX = duckForLevel61.getDuckImageView().getTranslateX();
            double duckY = duckForLevel61.getDuckImageView().getTranslateY();

            // Remove the duck from the root pane
            root.getChildren().remove(duckForLevel61.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_black/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel61.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel61.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);

                ImageView fallingDuckImageView = new ImageView("duck_black/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel61.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel61.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel61.getDuckImageView().getFitHeight()); // duck fall
                fallingTransition.setOnFinished(fallingEvent -> {
                    // Remove the falling duck image from the root pane
                    root.getChildren().remove(fallingDuckImageView);
                });
                root.getChildren().removeAll(SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                root.getChildren().addAll(fallingDuckImageView,SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                fallingTransition.play();
                root.getChildren().remove(hitImageView);
                Text instructionsText = messages.EndMessage();
                Label flashingText = messages.createFlashingTextLabelwinmessage("PRESS Enter to Play Again\n Press ESC to Exit");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                if (duckForLevel61.isGameOver() && duckForLevel62.isGameOver() && duckForLevel63.isGameOver()){
                    root.getChildren().addAll(instructionsText,flashingText);
                    mediaPlayerForGameComplated.play();
                    scene.setOnKeyPressed(eventlevel6collision -> {
                        if (eventlevel6collision.getCode() == KeyCode.ENTER && flag) {
                            mediaPlayerForGameComplated.stop();
                            flag = false;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel61.
                                            getDuckImageView(),flashingText,instructionText,
                                    SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            Level1 level1 = new Level1();
                            Duck duckForLevel1 = new Duck();
                            duckForLevel1.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.8,
                                    DuckHunt.WINDOW_WIDTH/150.0,0,true, // Create the duck for new game
                                    false,1,-1);
                            level1.startGameLoop(root, primaryStage,duckForLevel1,messages);
                            duckForLevel1.startDuckAnimation();

                        }
                        if (eventlevel6collision.getCode() == KeyCode.ESCAPE){
                            mediaPlayerForGameComplated.stop();
                            DuckHunt.currentScreen = 0;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,
                                    duckForLevel61.getDuckImageView(),flashingText,instructionText,
                                    SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            primaryStage.close();
                            DuckHunt screen = new DuckHunt();
                            screen.start(primaryStage);

                        }
                    });
                }

            });
            pauseTransition.play();

        }
    }
    private void checkCollision2(StackPane root,Scene scene,Stage primaryStage,Text instructionText,Duck duckForLevel61,
                                 Duck duckForLevel62,Duck duckForLevel63,Messages messages) {
        if (duckForLevel62.getDuckImageView() != null && duckForLevel62.getDuckImageView().
                getBoundsInParent().intersects(SelectScreen.crosshairImageView.getBoundsInParent())) {
            mediaPlayerForDuckFall.stop();
            mediaPlayerForDuckFall.play();
            duckForLevel62.setGameOver(true);

            // Stop the duck animation
            duckForLevel62.stopAnimationTimer();

            // Get the current position of the duck
            double duckX = duckForLevel62.getDuckImageView().getTranslateX();
            double duckY = duckForLevel62.getDuckImageView().getTranslateY();

            // Remove the duck from the root pane
            root.getChildren().remove(duckForLevel62.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_blue/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel62.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel62.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);


                ImageView fallingDuckImageView = new ImageView("duck_blue/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel62.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel62.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel62.getDuckImageView().getFitHeight()); //duck fall
                fallingTransition.setOnFinished(fallingEvent -> {
                    // Remove the falling duck image from the root pane
                    root.getChildren().remove(fallingDuckImageView);
                });
                root.getChildren().removeAll(SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                root.getChildren().addAll(fallingDuckImageView,SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                fallingTransition.play();
                root.getChildren().remove(hitImageView);
                Text instructionsText = messages.EndMessage();
                Label flashingText = messages.createFlashingTextLabelwinmessage("PRESS Enter to Play Again\n Press ESC to Exit");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                if (duckForLevel62.isGameOver() && duckForLevel61.isGameOver() && duckForLevel63.isGameOver()){
                    root.getChildren().addAll(instructionsText,flashingText);
                    mediaPlayerForGameComplated.play();
                    scene.setOnKeyPressed(eventlevel6collision2 -> {
                        if (eventlevel6collision2.getCode() == KeyCode.ENTER && flag) {
                            mediaPlayerForGameComplated.stop();
                            flag = false;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel62.getDuckImageView()
                                    ,flashingText,instructionText,
                                    SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            Level1 level1 = new Level1();
                            Duck duckForLevel1 = new Duck();
                            duckForLevel1.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.8,
                                    DuckHunt.WINDOW_WIDTH/150.0,0,true, // Create the duck for new game
                                    false,1,-1);
                            level1.startGameLoop(root, primaryStage,duckForLevel1,messages);
                            duckForLevel1.startDuckAnimation();

                        }
                        if (eventlevel6collision2.getCode() == KeyCode.ESCAPE){
                            mediaPlayerForGameComplated.stop();
                            DuckHunt.currentScreen = 0;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel62.getDuckImageView(),
                                    flashingText,instructionText,
                                    SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            primaryStage.close();
                            DuckHunt screen = new DuckHunt();
                            screen.start(primaryStage);

                        }
                    });
                }
            });
            pauseTransition.play();

        }
    }
    private void checkCollision3(StackPane root,Scene scene,Stage primaryStage,Text instructionText,Duck duckForLevel61,
                                 Duck duckForLevel62,Duck duckForLevel63,Messages messages) {
        if (duckForLevel63.getDuckImageView() != null && duckForLevel63.getDuckImageView().getBoundsInParent().
                intersects(SelectScreen.crosshairImageView.getBoundsInParent())) {
            mediaPlayerForDuckFall.stop();
            mediaPlayerForDuckFall.play();
            duckForLevel63.setGameOver(true);
            // Stop the duck animation
            duckForLevel63.stopAnimationTimer();

            // Get the current position of the duck
            double duckX = duckForLevel63.getDuckImageView().getTranslateX();
            double duckY = duckForLevel63.getDuckImageView().getTranslateY();

            // Remove the duck from the root pane
            root.getChildren().remove(duckForLevel63.getDuckImageView());

            // Create a new ImageView for the hit image
            Image hitImage = new Image("duck_red/7.png");
            ImageView hitImageView = new ImageView(hitImage);
            hitImageView.setFitWidth(duckForLevel63.getDuckImageView().getFitWidth());
            hitImageView.setFitHeight(duckForLevel63.getDuckImageView().getFitHeight());
            hitImageView.setTranslateX(duckX);
            hitImageView.setTranslateY(duckY);
            root.getChildren().add(hitImageView);

            // Pause for 0.5 seconds
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
            pauseTransition.setOnFinished(event -> {
                // Remove the hit image from the root pane
                root.getChildren().remove(hitImageView);

                ImageView fallingDuckImageView = new ImageView("duck_red/8.png");
                fallingDuckImageView.setFitWidth(duckForLevel63.getDuckImageView().getFitWidth());
                fallingDuckImageView.setFitHeight(duckForLevel63.getDuckImageView().getFitHeight());
                fallingDuckImageView.setTranslateX(duckX);
                fallingDuckImageView.setTranslateY(duckY);

                TranslateTransition fallingTransition = new TranslateTransition(Duration.seconds(1.5), fallingDuckImageView);
                fallingTransition.setByY(3 * duckForLevel63.getDuckImageView().getFitHeight()); // fall duck
                fallingTransition.setOnFinished(fallingEvent -> {
                    // Remove the falling duck image from the root pane
                    root.getChildren().remove(fallingDuckImageView);
                });
                root.getChildren().removeAll(SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                root.getChildren().addAll(fallingDuckImageView,SelectScreen.foregroundImageView,SelectScreen.crosshairImageView);
                fallingTransition.play();
                root.getChildren().remove(hitImageView);
                Text instructionsText = messages.EndMessage();
                Label flashingText = messages.createFlashingTextLabelwinmessage("PRESS Enter to Play Again\n Press ESC to Exit");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                if (duckForLevel63.isGameOver() && duckForLevel61.isGameOver() && duckForLevel62.isGameOver()){
                    root.getChildren().addAll(instructionsText,flashingText);
                    mediaPlayerForGameComplated.play();
                    scene.setOnKeyPressed(eventlevel6collision3 -> {
                        mediaPlayerForGameComplated.stop();
                        if (eventlevel6collision3.getCode() == KeyCode.ENTER && flag) {
                            flag = false;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel63.getDuckImageView(),flashingText,instructionText,
                                    SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            Level1 level1= new Level1();
                            Duck duckForLevel1 = new Duck();
                            duckForLevel1.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.8,
                                    DuckHunt.WINDOW_WIDTH/150.0,0, // Create the duck for new game
                                    true,false,1,-1);
                            level1.startGameLoop(root, primaryStage,duckForLevel1,messages);
                            duckForLevel1.startDuckAnimation();

                        }
                        if (eventlevel6collision3.getCode() == KeyCode.ESCAPE){
                            mediaPlayerForGameComplated.stop();
                            DuckHunt.currentScreen = 0;
                            root.getChildren().removeAll(instructionsText,fallingDuckImageView,duckForLevel63.getDuckImageView(),
                                    flashingText,instructionText,
                                    SelectScreen.crosshairImageView,SelectScreen.foregroundImageView);
                            primaryStage.close();
                            DuckHunt screen = new DuckHunt();
                            screen.start(primaryStage);

                        }
                    });
                }
            });
            pauseTransition.play();

        }
    }

    public void startGameLoop(StackPane root, Stage primaryStage,Duck duckForLevel61,Duck duckForLevel62,Duck duckForLevel63,Messages messages) {
        mediaPlayerForGunShot.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForDuckFall.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForGameOver.setVolume(DuckHunt.scaleForMusic);
        mediaPlayerForGameComplated.setVolume(DuckHunt.scaleForMusic);
        duckForLevel61.loadDuckImages("duck_black",1,4);
        Image duckImage = new Image("duck_black/1.png");
        duckForLevel61.setDuckImageView(new ImageView(duckImage));
        duckForLevel61.getDuckImageView().setFitWidth(duckImage.getWidth() * DuckHunt.WINDOW_WIDTH/171.4);
        duckForLevel61.getDuckImageView().setFitHeight(duckImage.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);
        duckForLevel61.getDuckImageView().setPreserveRatio(true);

        duckForLevel62.loadDuckImages("duck_blue",1,4);
        Image duckImage2 = new Image("duck_blue/1.png");
        duckForLevel62.setDuckImageView(new ImageView(duckImage2));
        duckForLevel62.getDuckImageView().setFitWidth(duckImage2.getWidth() * DuckHunt.WINDOW_WIDTH/171.4);
        duckForLevel62.getDuckImageView().setFitHeight(duckImage2.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);
        duckForLevel62.getDuckImageView().setPreserveRatio(true);

        duckForLevel63.loadDuckImages("duck_red",1,4);
        Image duckImage3 = new Image("duck_red/1.png");
        duckForLevel63.setDuckImageView(new ImageView(duckImage3));
        duckForLevel63.getDuckImageView().setFitWidth(duckImage3.getWidth() * DuckHunt.WINDOW_WIDTH/171.4);
        duckForLevel63.getDuckImageView().setFitHeight(duckImage3.getHeight() * DuckHunt.WINDOW_HEIGHT/160.8);
        duckForLevel63.getDuckImageView().setPreserveRatio(true);

        Text instructionsText = messages.BulletAndLevelMessage("LEVEL6",bulletCount);
        root.getChildren().addAll(duckForLevel61.getDuckImageView(),duckForLevel62.getDuckImageView(),duckForLevel63.getDuckImageView(),
                SelectScreen.foregroundImageView,SelectScreen.crosshairImageView,instructionsText);

        Scene scene = root.getScene();
        // make same cross-hair picture and mouse position
        scene.setOnMouseMoved(eventlevel6MouseMove -> {
            double crosshairX = eventlevel6MouseMove.getX() - SelectScreen.crosshairImageView.getFitWidth() -
                    DuckHunt.WINDOW_WIDTH/2.30;
            double crosshairY = eventlevel6MouseMove.getY() - SelectScreen.crosshairImageView.getFitHeight() -
                    DuckHunt.WINDOW_HEIGHT/2.28;
            SelectScreen.crosshairImageView.setTranslateX(crosshairX);
            SelectScreen.crosshairImageView.setTranslateY(crosshairY);
            primaryStage.getScene().setCursor(javafx.scene.Cursor.NONE);
        });
        scene.setOnMouseClicked(eventlevel6MouseClick -> {
            if (bulletCount > 0 && (!duckForLevel61.isGameOver() || !duckForLevel62.isGameOver() || !duckForLevel63.isGameOver())){
                mediaPlayerForGunShot.stop();
                mediaPlayerForGunShot.play();
                bulletCount--;
                instructionsText.setText("LEVEL6\t\t\t\t\t" + "Ammo Left: "+ bulletCount);
                checkCollision(root,scene,primaryStage,instructionsText,duckForLevel61,duckForLevel62,duckForLevel63,messages);
                checkCollision2(root,scene,primaryStage,instructionsText,duckForLevel61,duckForLevel62,duckForLevel63,messages);
                checkCollision3(root,scene,primaryStage,instructionsText,duckForLevel61,duckForLevel62,duckForLevel63,messages);
            }
            if (bulletCount == 0 && (!duckForLevel61.isGameOver() || !duckForLevel62.isGameOver() || !duckForLevel63.isGameOver())){
                Text instructionsTextLost = messages.GameOverMessageLabel("GAME OVER!");
                Label flashingText = messages.createFlashingTextLabelwinmessage("Press ENTER to play again\nPress ESC to exit");
                StackPane.setAlignment(flashingText, Pos.CENTER);
                root.getChildren().addAll(instructionsTextLost,flashingText);
                mediaPlayerForGameOver.play();
                flag2 = true;
                scene.setOnKeyPressed(eventlevel6 -> {
                    if (eventlevel6.getCode() == KeyCode.ENTER && flag2) {
                        flag2 = false;
                        root.getChildren().removeAll(instructionsTextLost,duckForLevel61.getDuckImageView(),
                                duckForLevel62.getDuckImageView(),flashingText,
                                duckForLevel63.getDuckImageView(),SelectScreen.crosshairImageView,
                                SelectScreen.foregroundImageView,instructionsText);
                        bulletCount = MAX_BULLETS;
                        Level1 level1= new Level1();
                        Duck duckForLevel1 = new Duck(); //stars game again
                        duckForLevel1.CreateDuck(false,0, DuckHunt.WINDOW_HEIGHT/4.8,
                                DuckHunt.WINDOW_WIDTH/150.0,0, // create new game
                                true,false,1,-1);
                        level1.startGameLoop(root, primaryStage,duckForLevel1,messages);
                        duckForLevel1.startDuckAnimation();
                    }
                    else if (eventlevel6.getCode() == KeyCode.ESCAPE){
                        DuckHunt.currentScreen = 0; // going first stage
                        root.getChildren().removeAll(instructionsText,instructionsTextLost,flashingText,duckForLevel61.getDuckImageView(),
                                duckForLevel62.getDuckImageView(),duckForLevel63.getDuckImageView(),SelectScreen.crosshairImageView,SelectScreen.foregroundImageView,SelectScreen.backgroundImageView);
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
                duckForLevel61.handle(now);
                duckForLevel62.handle(now);
                duckForLevel63.handle(now);
            }
        };

        gameLoop.start();
    }



}
