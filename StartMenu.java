import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author
 */
public class StartMenu extends JFrame implements Runnable, ActionListener {

  // Class Variables
  // have a frame to draw things on
  JPanel main = new JPanel();

  JButton play = new JButton("play");
  JButton settings = new JButton("settings");
  JButton quit = new JButton("quit");

  JLabel title = new JLabel("BLOONS TOWER DEFENSE");

  // Method to assemble our GUI 
  public void run() {
    // Creats a JFrame that is 800 pixels by 600 pixels, and closes when you click
    // on the X
    JFrame frame = new JFrame("Welcome to BTD");
    // Makes the X button close the program
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // makes the windows 800 pixel wide by 600 pixels tall
    frame.setSize(800, 600);
    // shows the window
    frame.setVisible(true);
    main.setLayout(null);
    frame.add(main);
    // set the background colour
    main.setBackground(new Color(215, 215, 215));

    // setup for the buttons
    play.addActionListener(this);
    play.setActionCommand("play");
    play.setBounds(325, 200, 150, 50);
    main.add(play);

    settings.addActionListener(this);
    settings.setActionCommand("settings");
    settings.setBounds(325, 275, 150, 50);
    main.add(settings);

    quit.addActionListener(this);
    quit.setActionCommand("quit");
    quit.setBounds(325, 350, 150, 50);
    main.add(quit);

    //setup for labels
    title.setFont(new Font("Serif", Font.BOLD, 30));
    title.setBounds(200, 125, 400, 50);
    main.add(title);
  }

  // method called when a button is pressed
  public void actionPerformed(ActionEvent e) {
    // get the command from the action
    String command = e.getActionCommand();

    if (command.equals("play")) {
      new Interface();
      this.setVisible(false);
      dispose();

    } else if(command.equals("quit")){
      System.exit(0);
    }
  }

}
