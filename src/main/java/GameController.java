import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameController implements MouseListener {

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
     * Resets the game and updates the view
     */
    public void reset() {
        this.model.reset();
        this.view.update();
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
    private void play(int x, int y) {
        DotInfo spot = this.model.get(x, y);

        // If already uncovered or flagged stop now
        if (!spot.isCovered() || spot.isFlagged()) {
            return;
        }

        this.model.step();

        // Uncover the spot
        spot.uncover();

        // If it's a mine you lose
        if (spot.isMined()) {
            spot.click();
            this.model.setLose();
            this.view.update();// Update before showing dialog so the mine shows while blocking
            boolean again = this.view.askPlayAgain();
            if (!again) {
                System.exit(0);
            } else {
                this.reset();
            }
            return;
        }

        // If it's blank start clearing 
        if (spot.getNeighbooringMines() == 0) {
            this.clearZone(spot);
        }

        // Check if they player has won and if so let them know
        if (this.hasWon()) {
            System.out.println("yay");
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
        GenericArrayStack<DotInfo> stack = new GenericArrayStack<DotInfo>(
                this.model.getHeigth() * this.model.getWidth());
        stack.push(initialDot);
        initialDot.uncover();
        while (!stack.isEmpty()) {
            DotInfo cur = stack.pop();
            if (cur.getNeighbooringMines() == 0) {
                DotInfo[] adj = this.model.getAdjacent(cur.getX(), cur.getY());
                for (int i = 0; i < adj.length; i++) {
                    if (adj[i].isCovered() && adj[i].getNeighbooringMines() == 0) {
                        stack.push(adj[i]);
                    }
                    if(!adj[i].isFlagged()){
                        adj[i].uncover();
                    }
                }
            }
        }
    }

    /**
     * Checks if the player has won the game
     * 
     * @return True if they have won false if not
     */
    private boolean hasWon() {
        for (int i = 0; i < this.model.getWidth(); i++) {
            for (int o = 0; o < this.model.getHeigth(); o++) {
                if (this.model.get(i, o).isCovered() && !this.model.get(i, o).isMined()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    /**
     * Triggered when the button is clicked by the mouse
     * It then triggers the approprite action based on which button
     * was used to click it
     * 
     * @param e - The mouse event object passed from the event
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            DotButton but = (DotButton) e.getSource();
            this.play(but.getColumn(), but.getRow());
            this.view.update();
        } else if (e.getButton() == 3) {
            // If right click and covered toggle the flagged state
            DotButton but = (DotButton) e.getSource();
            if(this.model.get(but.getColumn(), but.getRow()).isFlagged() || this.model.get(but.getColumn(), but.getRow()).isCovered()){
                this.model.toggleMineFlag(but.getColumn(), but.getRow());
                this.view.update();
            }
        }
    }

    @Override
    /**
     * Just an empty listener method
     */
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    /**
     * Just an empty listener method
     */
    public void mouseExited(MouseEvent e) {
    }

    @Override
    /**
     * Just an empty listener method
     */
    public void mousePressed(MouseEvent e) {
    }

    @Override
    /**
     * Just an empty listener method
     */
    public void mouseReleased(MouseEvent e) {
    }

}
