import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import java.awt.Rectangle;

//make the class abstract so it can act as a blueprint for the children classes
public abstract class Sprite {
    private int height;
    private int width;
    private int x;
    private int y;
    private int speed;
    private boolean alive;
    BufferedImage image;

    /**
     * creates a new sprite object 
     * @param x the x position of the object
     * @param y the y position of the object
     * @param width the width of the object
     * @param height the height of the object
     * @param speed the speed of the object
     */
    public Sprite(int x, int y, int width, int height, int speed, String filename){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.alive = true;
        this.image = load(filename);
    }

    /**
     * drawing function for a sprite function
     * @param g takes in graphics in order to draw
     * @param image takes in an image address to draw
     */
    public void draw(Graphics g){
        g.drawImage(this.image, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }

    /**
     * gets the image of the object
     * @return the object's image
     */
    public BufferedImage getImage(){
        return this.image;
    }

    /**
     * gets wether the object is still alive or not
     * @return the state of the object, alive or dead
     */
    public boolean isAlive(){
        return this.alive;
    }

    /**
     * kill off the object, sets its state to not alive
     */
    public void kill(){
        this.alive = false;
    }

    /**
     * gets the x position of the object
     * @return the x coordinate
     */
    public int getX(){
        return this.x;
    }

    /**
     * gets the y position of the object
     * @return the y coordinate 
     */
    public int getY(){
        return this.y;
    }

    /**
     * can change the x position of the object
     * @param value the new x coordinate
     */
    public void setX(int value){
        this.x = value;
    }

    /**
     * can change the y position of the object
     * @param value the new y coordinate
     */
    public void setY(int value){
        this.y = value;
    }

    /**
     * gets the width of the object
     * @return the object's width
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * gets the height of the object
     * @return the object's height
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * gets the normal speed variable of the object
     * @return the object's speed
     */
    public int getSpeed(){
        return this.speed;
    }

    /**
     * can change the speed of the object
     * @param value the new speed value
     */
    public void setSpeed(int value){
        this.speed = value;
    }

    /**
     * moves the object to the right
     */
    public void moveRight(){
       this.x += this.speed;
    }

    /**
     * moves the obejct downward
     */
    public void moveDown(){
       this.y += this.speed;
    }

    /**
     * handles movement for projectiles
     * @param xSpeed seperate speed added to the x coordinate
     * @param ySpeed seperate speed added to the y coordinate
     */
    public void updatePosition(int xSpeed, int ySpeed){
        this.x += xSpeed;
        this.y += ySpeed;
    }

    /**
     * create a rectangle in the position of a sprite
     * @return rectangle of the object's position
     */
    public Rectangle getRectangle(){
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getWidth());
    }
    
    /**
     * safe way of loading in an image when given its filename
     * @param filename the image address stored in the files
     * @return buffered image of the object
     */
    public BufferedImage load(String filename) {
        // create empty image
        BufferedImage image = null;
        // loading files may have errors
        try {
            image = ImageIO.read(new File(filename));
        } catch (Exception e) {
            // print ugly error message
            e.printStackTrace();
        }
        // give the image back
        return image;
    }
}
