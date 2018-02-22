import java.awt.*;

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
        //setSize(this.model.getWidth() * 28, this.model.getHeigth() * 28);
        setResizable(false);
        
        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(28 * gameModel.getWidth() + 30, 28 * gameModel.getHeigth() + 30));
        add(this.panel);
        
        this.panel.setLayout(null);
        
        this.dotButtons = new DotButton[gameModel.getWidth()][gameModel.getHeigth()];
        
        for(int i = 0; i < gameModel.getWidth(); i++){
            for(int o = 0; o < gameModel.getHeigth(); o++){
                this.dotButtons[i][o] = new DotButton(i, o, getIcon(i, o));
                this.dotButtons[i][o].addActionListener(this.controller);
                this.panel.add(this.dotButtons[i][o]);
            }
        }
        
        pack();
        update();
    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update() {
        for(int i = 0; i < this.model.getWidth(); i++){
            for(int o = 0; o < this.model.getHeigth(); o++){
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
        if(this.model.get(i, j).isCovered()){
            return 11;
        } else if(this.model.get(i, j).isMined() && this.model.get(i, j).hasBeenClicked()){
            return 10;
        } else if(this.model.get(i, j).isMined()){
            return 9;
        } else {
            return this.model.get(i, j).getNeighboringMines();
        }
    }

}
