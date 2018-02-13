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


    private int width, height, numberOfMines, steps;
    private Random generator;
    private DotInfo[][] board;

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
      this.initializeBoard();
    }
    
    private int[] pickPosition(){
      int[] cords = new int[2];
      cords[0] = this.generator.nextInt(this.width);
      cords[1] = this.generator.nextInt(this.height);
      return cords;
    }

    private void placeMine(int[] pos){
      int x = pos[0];
      int y = pos[1];
      this.board[x][y].setMined();

      boolean leftGood = false, rightGood = false, upGood = false, downGood = false;

      if(y == 0){
        this.board[x][y + 1].incrementNeighboringMines();
        downGood = true;
      } else if(y == this.width - 1){
        this.board[x][y - 1].incrementNeighboringMines();
        upGood = true;
      } else {
        this.board[x][y - 1].incrementNeighboringMines();
        this.board[x][y + 1].incrementNeighboringMines();
        upGood = true;
        downGood = true;
      }

      if(x == 0) {
        this.board[x + 1][y].incrementNeighboringMines();
        rightGood = true;
      } else if(x == this.height - 1){
        this.board[x - 1][y].incrementNeighboringMines();
        leftGood = true;
      } else {
        this.board[x - 1][y].incrementNeighboringMines();
        this.board[x + 1][y].incrementNeighboringMines();
        leftGood = true;
        rightGood = true;
      }

      if(rightGood && downGood){
        this.board[x + 1][y + 1].incrementNeighboringMines();
      }

      if(rightGood && upGood){
        this.board[x + 1][y - 1].incrementNeighboringMines();
      }

      if(leftGood && downGood){
        this.board[x - 1][y + 1].incrementNeighboringMines();
      }

      if(leftGood && upGood){
        this.board[x - 1][y - 1].incrementNeighboringMines();
      }
    }

    private void initializeBoard(){
      this.board = new DotInfo[width][height];

      // Init DotInfo objects
      for(int i = 0; i < this.height; i++){
        for(int j = 0; j < this.width; j++){
          this.board[i][j] = new DotInfo(i, j);
        }
      }

      int numTracker = this.numberOfMines;
      while(numTracker > 0){
        int[] pos = this.pickPosition();
        if(!this.board[pos[0]][pos[1]].isMined()){
          this.placeMine(pos);
          numTracker--;
        }
      }
    }
 
    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){

      // Clear old game data that needs to be
      this.steps = 0;

      // Call init function
      this.initializeBoard();
    }


    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        return this.height;
    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
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
    public boolean isMined(int i, int j){
      return this.board[i][j].isMined();        
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
    public boolean hasBeenClicked(int i, int j){
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
    public boolean isBlank(int i, int j){
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
    public boolean isCovered(int i, int j){
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
    public int getNeighboringMines(int i, int j){
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
    public void uncover(int i, int j){
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
    public void click(int i, int j){
      this.board[i][j].click();        
    }

     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){

      // Iterate through each board position and uncover
      for(int i = 0; i < this.width; i++){
        for(int j = 0; i < this.height; j++){
          this.board[i][j].uncover();
        }
      }
    }

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
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
     public void step(){
      this.steps++;
    }
 
   /**
     * The metod <b>isFinished</b> returns true if the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){

      // Iterate through each spot and check if it's uncovered and not mined
      // If so return false
      for(int i = 0; i < this.width; i++){
        for(int j = 0; j < this.height; j++){
          if(!this.board[i][j].isMined() && !this.board[i][j].isCovered()){
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
    public String toString(){
        String output = "";
        for(int i = 0; i < this.width; i++){
          for(int j = 0; j < this.height; j++){
            if(this.board[i][j].isMined()){
              output += "B ";
            } else {
              output += this.board[i][j].getNeighboringMines() + " ";
            }
          }
          output += "\n";
        }
        return output;
    }
}
