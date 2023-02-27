import java.awt.Graphics;
import java.awt.Rectangle;

public class Balloon extends Sprite{
    public int lives;

    /**
     * creates a new balloon 
     * @param x the x position of the balloon
     * @param y the y position of the balloon
     * @param width the width of the balloon
     * @param height the height of the balloon
     * @param speed the normal speed of the balloon
     * @param lives the number of lives which affects its colour
     */
    public Balloon(int x, int y, int width, int height, int speed, int lives){
        super(x, y, width, height, speed, "pics//blueBalloon.png");
        this.lives = lives;
    }

    /**
     * determines the number of lives
     * @return the balloon's lives
     */
    public int getLives(){
        return this.lives;
    }

    /**
     * if the balloon collides decrease its life count and if no more, kill it
     */
    public void updateLives(){
        if(this.lives == 0){
            this.kill();
        }

        this.lives --;
    }

    /**
     * create a rectangle in the position of the balloon
     */
    public Rectangle getRectangle(){
        return super.getRectangle();
    }

    /**
     * drawing function of the balloon dependent on the number of lives
     * @param g takes in the graphics in order to draw the balloon
     */
    public void draw(Graphics g){
        if(this.lives == 2){
            g.drawImage(super. load("pics//blueBalloon.png"), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
        } else if(this.lives == 1){
            g.drawImage(super. load("pics//redBalloon.png"), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
        }

        
    }

}
