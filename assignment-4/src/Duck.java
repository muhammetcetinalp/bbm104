import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Duck {

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    private boolean isGameOver;

    public ImageView getDuckImageView() {
        return duckImageView;
    }

    public void setDuckImageView(ImageView duckImageView) {
        this.duckImageView = duckImageView;
    }

    private ImageView duckImageView;
    private Image[] duckImages; // this is the list I stored duck images
    private int currentDuckImageIndex = 0; //this is a index for duck animation
    private int ScaleX;
    private int ScaleY;

    public  void stopAnimationTimer() {
        duckAnimationTimer.stop();
    }

    private AnimationTimer duckAnimationTimer;


    private double duckX;

    private double duckY;


    double duckSpeedX;


    double duckSpeedY;


    private boolean isDuckMovingRight;

    private boolean isDuckMovingDown;
    public void handle(long now) {
        if (isDuckMovingRight) {
            duckX += duckSpeedX;
            duckImageView.setScaleX(ScaleX); //this is where the duck's position on the x-axis changes
        } else {
            duckX -= duckSpeedX;
            duckImageView.setScaleX(-ScaleX);
        }

        if (isDuckMovingDown) {
            duckY += duckSpeedY;
            duckImageView.setScaleY(ScaleY); //this is where the duck's position on the y-axis changes
        } else {
            duckY -= duckSpeedY;
            duckImageView.setScaleY(-ScaleY);
        }

        double duckWidth = duckImageView.getFitWidth();
        double duckHeight = duckImageView.getFitHeight();

        // Limit strike control
        if (duckX <= 0) {
            isDuckMovingRight = true;
        } else if (duckX >= DuckHunt.WINDOW_WIDTH - duckWidth) {
            isDuckMovingRight = false;
        }                                           //this is where the duck makes itself turn when it hits the border
        if (duckY <= 0) {
            isDuckMovingDown = true;
        } else if (duckY >= DuckHunt.WINDOW_HEIGHT - duckHeight) {
            isDuckMovingDown = false;
        }

        // Making sure the ducks stay within the bounds
        duckX = Math.max(0, Math.min(duckX, DuckHunt.WINDOW_WIDTH + duckWidth));
        duckY = Math.max(0, Math.min(duckY, DuckHunt.WINDOW_HEIGHT + duckHeight));

        duckImageView.setTranslateX(duckX- DuckHunt.WINDOW_WIDTH/2.4);
        duckImageView.setTranslateY(duckY- DuckHunt.WINDOW_HEIGHT/2.4);
    }
    public void startDuckAnimation() {
        duckAnimationTimer = new AnimationTimer() {
            private long previousTime = 0;

            @Override
            public void handle(long now) {
                if (previousTime == 0) {
                    previousTime = now;         //this is where the duck flapping animation is done
                    return;
                }
                if (now - previousTime >= 300_000_000) {
                    currentDuckImageIndex = (currentDuckImageIndex + 1) % duckImages.length;
                    duckImageView.setImage(duckImages[currentDuckImageIndex]);
                    previousTime = now;
                }
            }
        };
        duckAnimationTimer.start();
    }
    public void loadDuckImages(String klasoradi,int start,int end) { // loading duck images
        duckImages = new Image[3];
        for (int i = start; i < end; i++) {
            duckImages[i-start] = new Image(klasoradi +"/" + (i) + ".png");
        }
    }
    //creating brand-new duck
    public void CreateDuck (boolean isGameOver,double duckX,double duckY,double duckSpeedX,double duckSpeedY,
                             boolean isDuckMovingRight,boolean isDuckMovingDown,int scaleX,int scaleY){
        this.isGameOver = isGameOver;
        this.duckX = duckX;
        this.duckY = duckY;
        this.duckSpeedX = duckSpeedX;
        this.duckSpeedY = duckSpeedY;
        this.isDuckMovingRight = isDuckMovingRight;
        this.isDuckMovingDown = isDuckMovingDown;
        this.ScaleX = scaleX;
        this.ScaleY = scaleY;



    }

}
