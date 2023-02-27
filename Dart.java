import java.awt.Graphics;

import java.awt.Rectangle;

public class Dart extends Sprite{
    private int xSpeed;
    private int ySpeed;

    /**
     * creates a new dart/bullet 
     * @param x the x position of the dart
     * @param y the y position of the dart
     * @param width the width of the dart
     * @param height the height of the dart
     * @param speed the standard speed of the dart
     * @param xSpeed the speed affecting the x position
     * @param ySpeed the speed affecting the y position
     */
    public Dart(int x, int y, int width, int height, int speed, int xSpeed, int ySpeed){
        super(x, y, width, height, speed, "pics//dart.png");
        super.setSpeed(25);
       
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    /**
     * if the dart falls out of bounds without hitting anything, remove it
     */
    public void updatePosition(){
        int x = this.getX();
        int y = this.getY();
        if(y < 0 || y > 600 || x < 0 || x > 1100){
            this.kill();
        }
    }

    /**
     * create a rectangle in the position of the bullet
     */
    public Rectangle getRectangle(){
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getWidth());
    }

    /**
     * determining whether two rectangle collide
     * @param object another object that collides with the dart
     * @return whether they collide or not
     */
    public boolean collision(Sprite object){
        if(object.getRectangle().intersects(this.getRectangle())){
            return true;
        } else{
            return false;
        }
    }

    /**
     * drawing for the dart
     */
    public void draw(Graphics g){
        super.draw(g);
        super.updatePosition(this.xSpeed, this.ySpeed);
    }
}
