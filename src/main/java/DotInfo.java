
/**
 * The class <b>DotInfo</b> is a simple helper class to store 
 * the state (e.g. clicked, mined, number of neighbooring mines...) 
 * at the dot position (x,y)
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class DotInfo {

    private int x, y, neighboringMines;
    private boolean mined, covered, wasClicked;
    

    /**
     * Constructor, used to initialize the instance variables
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public DotInfo(int x, int y){
        this.x = x;
        this.y = y;
        this.covered = true;
    }

    /**
     * Getter method for the attribute x.
     * 
     * @return the value of the attribute x
     */
    public int getX(){
        return this.x;
    }
    
    /**
     * Getter method for the attribute y.
     * 
     * @return the value of the attribute y
     */
    public int getY(){
        return this.y;
    }
    
 
    /**
     * Setter for mined
     */
    public void setMined() {
        this.mined = true;
    }

    /**
     * Getter for mined
     *
     * @return mined
     */
    public boolean isMined() {
        return this.mined;
    }


    /**
     * Setter for covered
     */
    public void uncover() {
        this.covered = false;
    }

    /**
     * Getter for covered
     *
     * @return covered
     */
    public boolean isCovered(){
        return this.covered;
    }



    /**
     * Setter for wasClicked
     */
    public void click() {
        this.wasClicked = true;
    }


    /**
     * Getter for wasClicked
     *
     * @return wasClicked
     */
    public boolean hasBeenClicked() {
        return this.wasClicked;
    }


    /**
     * Setter for neighboringMines
     *
     * @param neighboringMines
     *          number of neighbooring mines
     */
    public void setNeighboringMines(int neighboringMines) {
        this.neighboringMines = neighboringMines;
    }

    /**
     * Get for neighboringMines
     *
     * @return neighboringMines
     */
    public int getNeighboringMines() {
        return this.neighboringMines;
    }

 }
