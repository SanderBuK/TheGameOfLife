package the.game.of.life;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

public class ControlPanel extends JPanel{
    //Everything in this class should be static, so it can be accessed from the GameBoard class
    
    GameBoard board;
    
    String[] presets = {"None", "Glider", "Glider Generator", "Small Explosion", "Medium Explosion", "Large Explosion"};
    JComboBox presetChooser = new JComboBox(presets);
    JButton fillButton = new JButton("Fill");
    JButton clearButton = new JButton("Clear");
    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");
    JButton stepButton = new JButton("Step");
    JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 2, 8, 5);
    JLabel generation = new JLabel();
    
    ControlPanel(GameBoard gameBoard){
        board = gameBoard;
        
        presetChooser.addActionListener((ActionEvent e) -> {
            board.preset(presetChooser.getSelectedIndex());
        });
        fillButton.addActionListener((ActionEvent e) -> {
            board.fill();
        });
        clearButton.addActionListener((ActionEvent e) -> {
            board.clear();
        });
        startButton.addActionListener((ActionEvent e) -> {
            board.start();
        });
        stopButton.addActionListener((ActionEvent e) -> {
            board.stop();
        });
        stepButton.addActionListener((ActionEvent e) -> {
            board.step();
        });
        speedSlider.setInverted(true);
        speedSlider.addChangeListener((ChangeEvent e) -> {
            board.frameRate = (int)Math.pow(2, speedSlider.getValue());
            System.out.println(board.frameRate);
        });
        generation.setText("Generation: " + board.generation);
        
        super.add(presetChooser);
        super.add(fillButton);
        super.add(clearButton);
        super.add(startButton);
        super.add(stopButton);
        super.add(stepButton);
        super.add(speedSlider);
//        super.add(generation);
    }
}
