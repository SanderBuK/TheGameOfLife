package the.game.of.life;

import java.awt.BorderLayout;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

//TODO: The state of the squares around could represent numbers; 0 = off, 1 = on, and it could represent letters?

public class TheGameOfLife extends JApplet{

    //Initialize all of the variables.
    @Override
    public void init(){
        JPanel gameBoard = new GameBoard();
        JPanel controlPanel = new ControlPanel((GameBoard)gameBoard);//Passing the gameboard object, so the start and stop methods affect the board in the actual JFrame
        
        super.add(gameBoard, BorderLayout.CENTER);
        super.add(controlPanel, BorderLayout.SOUTH);
    }
    
    //Creates the frame and adds the application inside of it, whereafter the app is started.
    public static void main(String[] args) {
        JFrame main = new JFrame("The Game of Life");
        main.setSize(817,677);
        TheGameOfLife app = new TheGameOfLife();
        app.init();
        app.start();
        main.add(app);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }
}
