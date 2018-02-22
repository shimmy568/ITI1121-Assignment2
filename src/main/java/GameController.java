import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

    private GameModel model;
    private GameView view;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {
        this.model = new GameModel(width, height, numberOfMines);

        this.view = new GameView(this.model, this);
        this.view.setVisible(true);
    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        DotButton but = (DotButton)e.getSource();
        this.model.step();
        this.play(but.getColumn(), but.getRow());
        this.view.update();
    }

    /**
     * resets the game
     */
    private void reset(){
        this.model.reset();
    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param x
     *            the selected column
     * @param y
     *            the selected line
     */
    private void play(int x, int y){
        DotInfo spot = this.model.get(x, y);
        
        // Uncover the spot
        spot.uncover();

        // If it's a mine you lose
        if(spot.isMined()){
            spot.click();
            this.view.update();// Update before showing dialog so the mine shows while blocking
            boolean again = this.view.askPlayAgain();
            if(!again){
                System.exit(0);
            }else{
                this.reset();
            }
            return;
        }

        // If it's blank start clearing 
        if(spot.getNeighboringMines() == 0){
            this.clearZone(spot);
        }
    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {
        GenericArrayStack<DotInfo> stack = new GenericArrayStack<DotInfo>(this.model.getHeigth() * this.model.getWidth());
        stack.push(initialDot);
        initialDot.uncover();
        while(!stack.isEmpty()){
            DotInfo cur = stack.pop();
            if(cur.getNeighboringMines() == 0){
                DotInfo[] adj = this.model.getAdjacent(cur.getX(), cur.getY());
                for(int i = 0; i < adj.length; i++){
                    if(adj[i].isCovered() && adj[i].getNeighboringMines() == 0){
                        stack.push(adj[i]);
                    }
                    adj[i].uncover();
                }
            }
        }
    }



}
