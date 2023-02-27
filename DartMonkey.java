import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class DartMonkey extends Sprite {
    private int diameter;
    private boolean isSelected;
    private double angle;
    private int bulletDelay;
    final private int DELAY = 30;
    private Balloon target;

    /**
     * creates a new dart monkey
     * 
     * @param x          the x position of the monkey
     * @param y          the y position of the monkey
     * @param width      the width of the monkey
     * @param height     the height of the monkey
     * @param speed      the speed of the monkey (mainly affects the airplane monkey
     *                   if added)
     * @param diameter   the diameter of the monkey's range
     * @param isSelected whether the monkey is selected by the user or not
     */
    public DartMonkey(int x, int y, int width, int height, int speed, int diameter, boolean isSelected) {
        super(x, y, width, height, speed, "pics//dartmonkey.png");
        this.diameter = diameter;
        this.isSelected = isSelected;
        this.bulletDelay = DELAY;
        target = null;
    }

    /**
     * gets the radius range of the monkey
     * @return the monkey's radius
     */
    public int getRadius() {
        return this.diameter / 2;
    }

    /**
     * gets the information on whether the monkey is selected
     * @return whther the monkey is selected or not
     */
    public boolean getSelection() {
        return this.isSelected;
    }

    /**
     * toggles the selection
     */
    public void setSelection() {
        this.isSelected = !this.isSelected;
    }

    /**
     * gets the bullet delay for the monkey
     * @return the monkey's bullet delay
     */
    public int getDelay() {
        return this.bulletDelay;
    }

    /**
     * decrease the delay from the timer and reset once it reaches 0
     */
    public void updateDelay() {
        if (this.bulletDelay == 0) {
            this.bulletDelay = DELAY;
        }

        this.bulletDelay--;
    }

    /**
     * gets the targeted balloon in range
     * @return the target balloon
     */
    public Balloon getBalloon() {
        return this.target;
    }

    /**
     * label the first balloon that was in range as the target
     * @param target the balloon passed in as the target
     */
    public void setBalloon(Balloon target) {
        this.target = target;
    }

    /**
     * check if a target falls within the monkey's range
     * @param b a balloon from the arraylist
     * @return whether the balloon is in the monkey's range
     */
    public boolean inRange(Balloon b) {
        // determine if the distance between the this.target and the monkey is within the diameter
        int distance = (int) ((b.getX() + b.getWidth() / 2) - (this.getX() + this.getWidth() / 2))
                * ((b.getX() + b.getWidth() / 2) - (this.getX() + this.getWidth() / 2))
                + (((b.getY() + b.getHeight() / 2) - (this.getY() + this.getHeight() / 2))
                        * ((b.getY() + b.getHeight() / 2) - (this.getY() + this.getHeight() / 2)));

        if (distance <= this.getRadius() * this.getRadius()) {
            return true;
        }
        return false;
    }

    /**
     * drawing method for the monkey
     */
    public void draw(Graphics g) {
        int distanceX = 1000;
        int distanceY = 1000;

        //determine the distance between the target and the monkey
        if (target != null) {
            distanceX = (target.getX() + target.getWidth()) - (this.getX() + this.getWidth());
            distanceY = (target.getY() + target.getHeight()) - (this.getY() + this.getHeight());
        }

        if (target == null) {
            angle = 0;
        }
        //determine the angle
        angle = Math.atan(distanceY / distanceX) - (Math.PI) / 4;

        //check in which quadrant the target balloon lies in and face it
        if(distanceX <= 0 && distanceY > 0){
            angle -= Math.PI / 2;
        } else if(distanceX < 0 && distanceY < 0){
            angle -= Math.PI * 2;
        } else if(distanceX > 0 && distanceY < 0){
            angle -= Math.PI;
        } else if(distanceX > 0 && distanceY > 0){
            angle -= Math.PI * 1.5;
        }

        //show the monkey's range before placing it down
        if (this.isSelected) {
            g.setColor(new Color(211, 211, 211, 225));
            g.fillOval(this.getX() - 2 * this.getWidth(), this.getY() - 2 * this.getHeight(), diameter, diameter);
        }

        // change the graphics variable into graphics 2D
        Graphics2D g2d = (Graphics2D) g;
        //shift the monkey to its center
        g2d.translate(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
        //rotates everything
        g2d.rotate(angle);
        //draw the monkey at that spot
        g.drawImage(super.getImage(), -this.getWidth() / 2, -this.getHeight() / 2,
                this.getWidth(), this.getHeight(), null);
        //rotate everthing back
        g2d.rotate(-angle);
        //shift the monkey from its center back to its original corner
        g2d.translate(-this.getX() - this.getWidth() / 2, -this.getY() - this.getHeight() / 2);

    }

}
