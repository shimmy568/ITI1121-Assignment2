import java.util.ArrayList;
import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Owen Anderson
 * Student number: 300011168
 * Course: ITI 1121-A
 * Assignment: 2
 *
 */

public class GameModel {

    private int width, height, numberOfMines, steps, minesLeft;
    private Random generator;
    private DotInfo[][] board;
    private boolean lost;

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        this.width = width;
        this.height = heigth;
        this.numberOfMines = numberOfMines;
        this.generator = new Random();
        this.lost = false;
        this.minesLeft = numberOfMines;
        this.initializeBoard();
    }

    private int[] pickPosition() {
        int[] cords = new int[2];
        cords[0] = this.generator.nextInt(this.width);
        cords[1] = this.generator.nextInt(this.height);
        return cords;
    }

    private void placeMine(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        this.board[x][y].setMined();

        //Gets all adjacent spots and adds one to their adjacent mine counter
        DotInfo[] adj = this.getAdjacent(x, y);
        for (int i = 0; i < adj.length; i++) {
            adj[i].incrementNeighboringMines();
        }
    }

    /**
     * Gets all the adjacent DotInfo objects to a given x and y cord
     * takes into account the border of the board
     * 
     * @param x The x cord of the spot
     * @param y The y cord of the spot
     * 
     * @return The adjacent spots
     */
    public DotInfo[] getAdjacent(int x, int y) {
        ArrayList<DotInfo> temp = new ArrayList<DotInfo>();

        boolean leftGood = false, rightGood = false, upGood = false, downGood = false;

        if (y == 0) {
            temp.add(this.board[x][y + 1]);
            downGood = true;
        } else if (y == this.height - 1) {
            temp.add(this.board[x][y - 1]);
            upGood = true;
        } else {
            temp.add(this.board[x][y - 1]);
            temp.add(this.board[x][y + 1]);
            upGood = true;
            downGood = true;
        }

        if (x == 0) {
            temp.add(this.board[x + 1][y]);
            rightGood = true;
        } else if (x == this.width - 1) {
            temp.add(this.board[x - 1][y]);
            leftGood = true;
        } else {
            temp.add(this.board[x - 1][y]);
            temp.add(this.board[x + 1][y]);
            leftGood = true;
            rightGood = true;
        }

        if (rightGood && downGood) {
            temp.add(this.board[x + 1][y + 1]);
        }

        if (rightGood && upGood) {
            temp.add(this.board[x + 1][y - 1]);
        }

        if (leftGood && downGood) {
            temp.add(this.board[x - 1][y + 1]);
        }

        if (leftGood && upGood) {
            temp.add(this.board[x - 1][y - 1]);
        }

        DotInfo[] ret = new DotInfo[temp.size()];
        ret = temp.toArray(ret);
        return ret;
    }

    private void initializeBoard() {
        this.board = new DotInfo[width][height];

        // Init DotInfo objects
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.board[i][j] = new DotInfo(i, j);
            }
        }

        int numTracker = this.numberOfMines;
        while (numTracker > 0) {
            int[] pos = this.pickPosition();
            if (!this.board[pos[0]][pos[1]].isMined()) {
                this.placeMine(pos);
                numTracker--;
            }
        }
    }

    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset() {

        // Clear old game data that needs to be
        this.steps = 0;
        this.lost = false;
        this.minesLeft = this.numberOfMines;

        // Call init function
        this.initializeBoard();
    }

    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */
    public int getHeigth() {
        return this.height;
    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isMined(int i, int j) {
        return this.board[i][j].isMined();
    }

    /**
     * Toggles a flagged mine at a given position and
     * updates the minesLeft tracker
     * 
     * @param x - The x position of the spot to flag
     * @param y - The y position of the spot to flag
     */
    public void toggleMineFlag(int x, int y){
        if(this.board[x][y].isFlagged()){
            this.minesLeft++;
        }else{
            this.minesLeft--;
        }

        this.board[x][y].toggleFlagged();
    }

    /**
     * Returns the number of unflagged mines
     * 
     * @return - The mines left counter
     */
    public int getMinesLeft(){
        return this.minesLeft;
    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean hasBeenClicked(int i, int j) {
        return this.board[i][j].hasBeenClicked();
    }

    /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isBlank(int i, int j) {
        return this.board[i][j].getNeighboringMines() == 0;
    }

    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isCovered(int i, int j) {
        return this.board[i][j].isCovered();
    }

    /**
     * returns the number of neighboring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighboring mines at location (i,j)
     */
    public int getNeighboringMines(int i, int j) {
        return this.board[i][j].getNeighboringMines();
    }

    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void uncover(int i, int j) {
        this.board[i][j].uncover();
    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void click(int i, int j) {
        this.board[i][j].click();
    }

    /**
    * Uncover all remaining covered dot
    */
    public void uncoverAll() {

        // Iterate through each board position and uncover
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; i < this.height; j++) {
                this.board[i][j].uncover();
            }
        }
    }

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */
    public int getNumberOfSteps() {
        return this.steps;
    }

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */
    public DotInfo get(int i, int j) {
        return this.board[i][j];
    }

    /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the player selected a new square.
     */
    public void step() {
        this.steps++;
    }

    /**
     * The metod <b>isFinished</b> returns true if the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished() {

        // Iterate through each spot and check if it's uncovered and not mined
        // If so return false
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (!this.board[i][j].isMined() && !this.board[i][j].isCovered()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString() {
        String output = "";
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                if (this.board[i][j].isMined()) {
                    output += "B ";
                } else {
                    output += this.board[i][j].getNeighboringMines() + " ";
                }
            }
            output += "\n";
        }
        return output;
    }

    /**
     * Sets the lost state of the game to true
     */
    public void setLose(){
        this.lost = true;
    }

    /**
     * Gets weather the game is in the lost state or not
     * 
     * @return - The lost state of the game
     */
    public boolean getLose(){
        return this.lost;
    }
}
