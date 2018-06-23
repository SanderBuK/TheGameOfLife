package the.game.of.life;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

    ArrayList<ArrayList<Field>> board = new ArrayList<>();

    int generation;

    int frameRate = 100;

    boolean mousePressed = false;
    boolean drawing;
    Field lastClickedField;

    boolean running = false;
    Thread start;

    GameBoard() {
        for (int i = 0; i < 80; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 60; j++) {
                board.get(i).add(new Field(i * 10, j * 10, 10, 10));
            }
        }

        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed = true;
                for (ArrayList<Field> list : board) {
                    for (Field field : list) {
                        if (field.contains(e.getPoint())) {
                            drawing = (!(field.isAlive));
                            field.isAlive = drawing;
                            lastClickedField = field;
                            repaint();
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
            }
        });
        super.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                for (ArrayList<Field> list : board) {
                    for (Field field : list) {
                        if (field.contains(e.getPoint()) && field != lastClickedField && mousePressed) {
                            field.isAlive = drawing;
                            repaint();
                            lastClickedField = field;
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //Draw all of 
        for (ArrayList<Field> list : board) {
            for (Field field : list) {
                if (field.isAlive) {
                    g2.fill(field);
                } else {
                    g2.draw(field);
                }
            }
        }
    }

    public void start() {
        if (!running) {
            System.out.println("Start");
            running = true;

            start = new Thread() {
                @Override
                public void run() {
                    while (running) {
                        step();
                        try {
                            sleep(frameRate);
                        } catch (InterruptedException ex) {
                            System.err.println("Error in running thread!");
                        }
                    }
                }
            };
            start.start();
        }
    }

    public void step() {
        //Looping through all of the fields
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 60; j++) {
                int neighbors = 0;
                //Looping through all of the neighbors
                for (int i2 = -1; i2 < 2; i2++) {
                    for (int j2 = -1; j2 < 2; j2++) {
                        if (!(i2 == 0 && j2 == 0)) {
                            neighbors += checkingNeighbors(i + i2, j + j2);
                        }
                    }
                }
                if (board.get(i).get(j).isAlive) {
                    if (neighbors < 2 || neighbors > 3) {
                        board.get(i).get(j).nextAliveState = false;
                    } else {
                        board.get(i).get(j).nextAliveState = true;
                    }
                } else {
                    if (neighbors == 3) {
                        board.get(i).get(j).nextAliveState = true;
                    } else {
                        board.get(i).get(j).nextAliveState = false;
                    }
                }
            }
        }
        for (ArrayList<Field> list : board) {
            for (Field field : list) {
                field.generation();
            }
        }
        generation++;
        repaint();
    }

    private int checkingNeighbors(int i, int j) {
        if (!((i < 0 || j < 0) || (i >= 80 || j >= 60))) {
            return ((board.get(i).get(j).isAlive) ? 1 : 0);
        }
        return 0;
    }

    public void stop() {
        System.out.println("Stop!");

        running = false;
    }

    public void clear() {
        for (ArrayList<Field> list : board) {
            for (Field field : list) {
                field.isAlive = false;
                field.nextAliveState = false;
            }
        }
        generation = 0;
        repaint();
    }

    public void fill() {
        for (ArrayList<Field> list : board) {
            for (Field field : list) {
                field.isAlive = true;
            }
        }
        repaint();
    }

    public void preset(int presetID) {
        switch (presetID) {
            case 0:
                break;
            case 1:
                //Glider
                clear();
                board.get(2).get(1).isAlive = true;
                board.get(3).get(2).isAlive = true;
                board.get(3).get(3).isAlive = true;
                board.get(2).get(3).isAlive = true;
                board.get(1).get(3).isAlive = true;
                break;
            case 2:
                //Glider Generator
                clear();
                board.get(4).get(6).isAlive = true;
                board.get(4).get(7).isAlive = true;
                board.get(5).get(6).isAlive = true;
                board.get(5).get(7).isAlive = true;

                board.get(12).get(7).isAlive = true;
                board.get(12).get(8).isAlive = true;
                board.get(13).get(6).isAlive = true;
                board.get(13).get(8).isAlive = true;
                board.get(14).get(6).isAlive = true;
                board.get(14).get(7).isAlive = true;

                board.get(20).get(8).isAlive = true;
                board.get(20).get(9).isAlive = true;
                board.get(20).get(10).isAlive = true;
                board.get(21).get(8).isAlive = true;
                board.get(22).get(9).isAlive = true;

                board.get(26).get(6).isAlive = true;
                board.get(26).get(5).isAlive = true;
                board.get(27).get(4).isAlive = true;
                board.get(27).get(6).isAlive = true;
                board.get(28).get(4).isAlive = true;
                board.get(28).get(5).isAlive = true;

                board.get(28).get(16).isAlive = true;
                board.get(28).get(17).isAlive = true;
                board.get(29).get(16).isAlive = true;
                board.get(29).get(18).isAlive = true;
                board.get(30).get(16).isAlive = true;

                board.get(38).get(4).isAlive = true;
                board.get(38).get(5).isAlive = true;
                board.get(39).get(4).isAlive = true;
                board.get(39).get(5).isAlive = true;

                board.get(39).get(11).isAlive = true;
                board.get(39).get(12).isAlive = true;
                board.get(39).get(13).isAlive = true;
                board.get(40).get(11).isAlive = true;
                board.get(41).get(12).isAlive = true;
                break;
            case 3:
                //Small explosion
                clear();
                board.get(39).get(29).isAlive = true;
                board.get(39).get(30).isAlive = true;
                board.get(40).get(28).isAlive = true;
                board.get(40).get(29).isAlive = true;
                board.get(40).get(31).isAlive = true;
                board.get(41).get(29).isAlive = true;
                board.get(41).get(30).isAlive = true;
                break;
            case 4:
                //Medium explosion
                clear();
                board.get(35).get(30).isAlive = true;
                board.get(36).get(30).isAlive = true;
                board.get(37).get(30).isAlive = true;
                board.get(38).get(30).isAlive = true;
                board.get(39).get(30).isAlive = true;
                board.get(40).get(30).isAlive = true;
                board.get(41).get(30).isAlive = true;
                board.get(42).get(30).isAlive = true;
                board.get(43).get(30).isAlive = true;
                board.get(44).get(30).isAlive = true;
                break;
            case 5:
                //Huge explosion
                clear();
                board.get(38).get(28).isAlive = true;
                board.get(38).get(29).isAlive = true;
                board.get(38).get(30).isAlive = true;
                board.get(38).get(31).isAlive = true;
                board.get(38).get(32).isAlive = true;

                board.get(40).get(28).isAlive = true;
                board.get(40).get(32).isAlive = true;

                board.get(42).get(28).isAlive = true;
                board.get(42).get(29).isAlive = true;
                board.get(42).get(30).isAlive = true;
                board.get(42).get(31).isAlive = true;
                board.get(42).get(32).isAlive = true;
                break;
            default:
                System.err.println("Error in chossing a preset, as it goes to default");
                break;
        }
    }

    private class Field extends Rectangle/* implements MouseListener, MouseMotionListener*/ {

        boolean isAlive = false;

        boolean nextAliveState;

        public void generation() {
            isAlive = nextAliveState;
        }

        public Field(int x, int y, int width, int height) {
            super(x, y, width, height);
        }
    }
}
