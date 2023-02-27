import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Timer;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author
 */
public class Interface extends JComponent implements ActionListener {

    // Height and Width of our game
    static final int WIDTH = 1100;
    static final int HEIGHT = 600;

    // Title of the window
    String title = "BTD";

    // sets the framerate and delay for our game
    // this calculates the number of milliseconds per frame
    // you just need to select an appropriate framerate
    int desiredFPS = 60;
    int desiredTime = Math.round((1000 / desiredFPS));

    // timer used to run the game loop
    // this is what keeps our time running smoothly :)
    Timer gameTimer;

    // YOUR GAME VARIABLES WOULD GO HERE
    final int rows = 3;
    final int columns = 2;
    Rectangle[][] buttonGrid = new Rectangle[columns][rows];
    Rectangle[] balloonPath = new Rectangle[2];
    Rectangle startButton = new Rectangle(901, 500, 100, 50);

    // button images
    BufferedImage dartMonkeyButton;
    BufferedImage tackShooterButton;
    BufferedImage glueMonkeyButton;
    BufferedImage iceMonkeyButton;
    BufferedImage monkeyBuccaneerButton;
    BufferedImage superMonkeyButton;

    // the lists
    ArrayList<Balloon> balloons = new ArrayList<>();
    ArrayList<DartMonkey> monkeys = new ArrayList<>();
    ArrayList<Dart> darts = new ArrayList<>();

    // timer variables for the balloons
    final int START_TIME = 30;
    int nextTime = START_TIME;
    int updates = 0;

    int mouseX = 0;
    int mouseY = 0;
    boolean bSelected = false;

    int lives = 5;
    int money = 500;
    boolean roundStarted = false;
    // GAME VARIABLES END HERE

    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public Interface() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();
        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);

        // Set things up for the game at startup

        setup();

        // Start the game loop
        gameTimer = new Timer(desiredTime, this);
        gameTimer.setRepeats(true);
        gameTimer.start();
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        // drawing for the path
        g.setColor(new Color(141, 203, 41));
        g.fillRect(0, 0, 800, HEIGHT);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 200, 600, 50);
        g.fillRect(550, 250, 50, 550);
        g.fillRect(800, 0, 300, HEIGHT);
        g.setColor(Color.BLACK);
        g.drawLine(0, 200, 600, 200);
        g.drawLine(0, 250, 550, 250);
        g.drawLine(600, 200, 600, 600);
        g.drawLine(550, 250, 550, 600);

        // drawing for the button menu
        g.setColor(Color.BLACK);
        g.drawLine(800, 0, 800, 600);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                g.drawRect(899 + 52 * x, 200 + 52 * y, 50, 50);
            }
        }

        g.drawRect(901, 500, 100, 50);
        g.drawString("Start", 938, 530);
        g.drawString("Lives: " + lives, 975, 50);
        g.drawString("Money: $" + money, 875, 50);

        g.setColor(new Color(80, 216, 252));
        g.fillRect(450, 100, 150, 100);
        g.drawImage(dartMonkeyButton, 899, 200, this);
        g.drawImage(tackShooterButton, 951, 200, this);
        g.drawImage(glueMonkeyButton, 899, 252, this);
        g.drawImage(iceMonkeyButton, 951, 252, this);
        g.drawImage(monkeyBuccaneerButton, 899, 304, this);
        g.drawImage(superMonkeyButton, 951, 304, this);

        // draw all of the lists
        for (Balloon b : balloons) {
            b.draw(g);
        }

        for (DartMonkey m : monkeys) {
            m.draw(g);
            m.updateDelay();
        }

        for (Dart d : darts) {
            d.draw(g);
        }
        // GAME DRAWING ENDS HERE

    }

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

    // check if the mouse is hovering over something
    public boolean mouseOn(Rectangle object) {
        if (object.contains(mouseX, mouseY)) {
            return true;
        } else {
            return false;
        }
    }

    // monkey throws a dart
    public void throwDart(int xPosition, int yPosition, int xSpeed, int ySpeed) {
        if (!bSelected) {
            darts.add(new Dart(xPosition, yPosition, 15, 6, 15, xSpeed, ySpeed));
        }
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void setup() {
        // Any of your pre setup before the loop starts should go here
        dartMonkeyButton = load("pics//dartmonkey.png");
        tackShooterButton = load("pics//tackShooter.png");
        glueMonkeyButton = load("pics//glueMonkey.png");
        iceMonkeyButton = load("pics//iceMonkey.png");
        monkeyBuccaneerButton = load("pics//monkeyBuccaneer.png");
        superMonkeyButton = load("pics//superMonkey.png");

        balloonPath[0] = new Rectangle(0, 200, 600, 50);
        balloonPath[1] = new Rectangle(550, 250, 50, 550);
        // create the rectangle button grid for the monkey options
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                buttonGrid[x][y] = new Rectangle(899 + 52 * x, 200 + 52 * y, 50, 50);
            }
        }
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void loop() {
        // initially check if the start button was pressed
        if (roundStarted) {
            // create the balloons
            if (nextTime == updates && nextTime <= 5 * START_TIME) {
                balloons.add(new Balloon(0, 210, 28, 36, 5, 2));
                nextTime += START_TIME;
            }

            // make the balloons move
            for (Balloon b : balloons) {
                if (b.getX() < 590 - b.getWidth()) {
                    b.moveRight();
                } else if (b.getY() <= 600) {
                    b.moveDown();
                }

                //update its lives, if 0 pop the balloon
                if (b.getY() == 600) {
                    b.kill();
                    lives -= b.lives;
                }
            }

            //determine a target balloon
            for (DartMonkey m : monkeys) {
                for (Balloon b : balloons)
                    if (m.inRange(b)) {
                        m.setBalloon(b);
                        break;
                    } else {
                        m.setBalloon(null);
                    }

                if (m.getBalloon() != null && m.inRange(m.getBalloon()) && m.getDelay() == 0) {

                    // find a balloon to lock on by determining its position in the arraylist
                    double distanceX = Math
                            .abs((m.getX() + m.getWidth() / 2)
                                    - (m.getBalloon().getX() + m.getBalloon().getWidth() / 2));
                    double distanceY = Math
                            .abs((m.getY() + m.getHeight() / 2)
                                    - (m.getBalloon().getY() + m.getBalloon().getHeight() / 2));

                    int xSide = 1;
                    int ySide = 1;
                    // determine in which quadrant the balloons lie in
                    if (m.getBalloon().getX() < m.getX()) {
                        xSide = -1;
                    }
                    if (m.getBalloon().getY() < m.getY()) {
                        ySide = -1;
                    }

                    if (distanceX > distanceY) {
                        throwDart(m.getX() + m.getWidth() / 2, m.getY() + m.getHeight() / 2, 25 * xSide,
                                (int) (25 * distanceY / distanceX * ySide));
                    } else {
                        throwDart(m.getX() + m.getWidth() / 2, m.getY() + m.getHeight() / 2,
                                (int) (25 * distanceX / distanceY * xSide), 25 * ySide);
                    }
                }
            }

            //movement and collision of the dart and balloons
            for (Dart d : darts) {
                d.updatePosition();

                for (Balloon b : balloons) {
                    if (d.collision(b)) {
                        d.kill();
                        money += 100;
                        b.updateLives();
                    }
                }
            }

            //iterators used to safely remove an object from an arraylist by looping through the whole list
            Iterator<Dart> it = this.darts.iterator();
            while (it.hasNext()) {
                Dart d = it.next();
                if (!d.isAlive()) {
                    it.remove();
                }
            }

            Iterator<Balloon> it2 = this.balloons.iterator();
            while (it2.hasNext()) {
                Balloon b = it2.next();
                if (!b.isAlive()) {
                    it2.remove();
                }
            }

            // check if you lost
            if (lives <= 0) {
                LosingWindow lost = new LosingWindow();
                lost.run();
            }

            // increases the number of updates
            updates++;
        }

    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {

        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

            // check if the mouse clicks on one of the buttons
            if (mouseOn(buttonGrid[0][0]) && !bSelected) {
                System.out.println("clicked");
                monkeys.add(new DartMonkey(mouseX - 25, mouseY - 25, 50, 50, 0, 250, true));
                bSelected = true;
            } else if (mouseX > 0 && mouseX < 800 && mouseY > 0 && mouseY < HEIGHT && bSelected) {
                bSelected = false;
                for (DartMonkey m : monkeys) {
                    if (m.getSelection()) {
                        m.setSelection();
                    }
                }
            }

            // start button functionality
            if (mouseOn(startButton)) {
                roundStarted = true;
            }

        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {

        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

            // have the monkey follow the mouse when moving it
            if (bSelected) {
                for (DartMonkey m : monkeys) {
                    if (m.getSelection()) {
                        m.setX(mouseX - m.getWidth() / 2);
                        m.setY(mouseY - m.getHeight() / 2);
                    }
                }
            }
        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {

        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e) {

        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        loop();
        repaint();
    }
}