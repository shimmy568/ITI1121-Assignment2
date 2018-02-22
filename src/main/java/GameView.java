import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out an instance of  <b>BoardView</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

    private static final long serialVersionUID = -4418557344980102586L;
    private DotButton[][] dotButtons;
    private GameModel model;
    private GameController controller;
    private JPanel panel;
    private JLabel stepsLabel, minesLabel;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */
    public GameView(GameModel gameModel, GameController gameController) {
        this.model = gameModel;
        this.controller = gameController;

        setTitle("MineSweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(this.model.getWidth() * 28, this.model.getHeigth() * 28);
        setResizable(false);
        setLayout(new BorderLayout());

        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(28 * gameModel.getWidth() + 30, 28 * gameModel.getHeigth()));
        add(this.panel, BorderLayout.CENTER);

        this.stepsLabel = new JLabel();
        this.updateStepsLabel();
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        add(bottomPanel, BorderLayout.PAGE_END);
        bottomPanel.add(this.stepsLabel);


        this.initBottomPanelButtons(bottomPanel);

        this.minesLabel = new JLabel();
        this.updateMinesLabel();
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        topPanel.add(minesLabel);
        add(topPanel, BorderLayout.PAGE_START);

        this.panel.setLayout(null);

        this.dotButtons = new DotButton[gameModel.getWidth()][gameModel.getHeigth()];

        for (int i = 0; i < gameModel.getWidth(); i++) {
            for (int o = 0; o < gameModel.getHeigth(); o++) {
                this.dotButtons[i][o] = new DotButton(i, o, getIcon(i, o));
                this.dotButtons[i][o].addMouseListener(this.controller);
                this.panel.add(this.dotButtons[i][o]);
            }
        }

        pack();
        update();
    }

    /**
     * Creates and sets up the buttons on the bottom of the board
     * 
     * @param panel - The panel that sits at the bottom, will have the buttons added to it
     */
    private void initBottomPanelButtons(JPanel panel) {
        JButton restart = new JButton("Reset");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Minesweeper.game.reset();
            }
        });
        panel.add(restart);

        // Add quit button
        JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(quit);

        // Add restart button
    }

    /**
     * Updates the label at the bottom based of the step counter in the model
     */
    private void updateStepsLabel() {
        this.stepsLabel.setText("Number of steps: " + this.model.getNumberOfSteps());
    }

    private void updateMinesLabel(){
        this.minesLabel.setText("Number of mines left: " + this.model.getMinesLeft());
    }

    /**
     * Shows a dialog asking the user to play again
     * 
     * @return True if they want to play again false if they dont
     */
    public boolean askPlayAgain() {
        String[] buttonMessages = new String[] { "Quit", "Play Again" };
        int n = JOptionPane.showOptionDialog(this,
                "Ah dang... You lost in " + this.model.getNumberOfSteps() + " steps.\nWould you like to play agian?",
                "Boom!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttonMessages,
                buttonMessages[1]);
        return n == 1; // Return true if play again was clicked
    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */
    public void update() {
        this.updateStepsLabel();
        this.updateMinesLabel();
        for (int i = 0; i < this.model.getWidth(); i++) {
            for (int o = 0; o < this.model.getHeigth(); o++) {
                this.dotButtons[i][o].setIconNumber(this.getIcon(i, o));
            }
        }

        panel.repaint();
    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */
    private int getIcon(int i, int j) {
        DotInfo spot = this.model.get(i, j);

        // Special case for when the game is in the lost state
        if(this.model.getLose()){
            if(spot.isMined() && !spot.hasBeenClicked()){
                return 9; 
            }
        }

        if (spot.isFlagged()) {
            return 12;
        } else if (spot.isCovered()) {
            return 11;
        } else if (spot.isMined() && spot.hasBeenClicked()) {
            return 10;
        } else {
            return spot.getNeighboringMines();
        }
    }
}
