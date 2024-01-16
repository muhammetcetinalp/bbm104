import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Messages {
    private final Duration DURATION = Duration.seconds(0.5);

    public Label createFlashingTextLabelwinmessage(String message) {
        Label flashingTextLabel = new Label(message);
        flashingTextLabel.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.WINDOW_WIDTH/17.1));
        flashingTextLabel.setTextFill(Color.ORANGE);
        StackPane.setAlignment(flashingTextLabel, Pos.CENTER);
        flashingTextLabel.setTranslateY(DuckHunt.WINDOW_HEIGHT/3.0); //this is where labeling messages are made

        FadeTransition fadeIn = new FadeTransition(DURATION, flashingTextLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(Animation.INDEFINITE);
        fadeIn.setAutoReverse(true);
        fadeIn.play();

        return flashingTextLabel;
    }
    //this is where game-over message is made
    public Text GameOverMessageLabel(String gameOver){
        Text instructionsTextLost = new Text(gameOver);
        instructionsTextLost.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.WINDOW_WIDTH/15.0));
        instructionsTextLost.setFill(Color.ORANGE);
        instructionsTextLost.setTranslateY(DuckHunt.WINDOW_HEIGHT/4.6);

        return instructionsTextLost;
    }
    //this is where level win messages are made
    public Text WinMessage(){
        Text instructionsText = new Text("YOU WİN!");
        instructionsText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.WINDOW_WIDTH/15.0));
        instructionsText.setFill(Color.ORANGE);
        StackPane.setAlignment(instructionsText, Pos.CENTER);
        instructionsText.setTranslateY(DuckHunt.WINDOW_HEIGHT/5.0);
        return instructionsText;
    }
    // Here I wrote information about how many bullets left and which level I am playing
    public Text BulletAndLevelMessage(String level,int bulletCount){
        Text instructionsText = new Text(level +"\t\t\t\t\t" + "Ammo Left: "+ bulletCount);
        instructionsText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.WINDOW_WIDTH/35.0));
        instructionsText.setFill(Color.ORANGE);
        StackPane.setAlignment(instructionsText, Pos.TOP_CENTER);
        instructionsText.setTranslateX(DuckHunt.WINDOW_HEIGHT/6.0);

        return instructionsText;
    }
    //this is where End message is made
    public Text EndMessage(){
        Text instructionsText = new Text("YOU HAVE COMPLETED THE GAME");
        instructionsText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.WINDOW_WIDTH/20.0));
        instructionsText.setFill(Color.ORANGE);
        StackPane.setAlignment(instructionsText, Pos.CENTER);
        instructionsText.setTranslateY(DuckHunt.WINDOW_WIDTH/5.0);
        return instructionsText;
    }
}
