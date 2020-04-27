import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;

// Represents a Game class that is used to construct the game.

public class Game extends JFrame {

    ArrayList<Tile> buttonList = new ArrayList<Tile>(); // a list of the tile spaces in the game
    HashMap<Integer, Boolean> tileIndices = new HashMap<Integer, Boolean>(); // a hashmap used to keep track of which spaces are taken
                                                              // if the Boolean is true, then it's taken, false otherwise

    private boolean gameLost = false;
    private boolean gameWon = false;
    private Random rand = new Random();

    static int UP = 1;
    static int RIGHT = 2;
    static int LEFT = 3;
    static int DOWN = 4;

    Game() {
        // adding tile spaces to the game
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttonList.add(new Tile(j, i));
            }
        }
        // setting each spot in the hashmap to false
        for (int i = 0; i < 16; i++) {
            tileIndices.put(i, false);
        }

        // generates two random spots at the beginning of the game
        getRandomSpot();
        getRandomSpot();

        // sets the color, font, and size parameters  for the game
        setBackground(Color.decode("#717171"));
        setFont(new Font("SansSerif", Font.BOLD, 24));
        setSize(525, 525);
        setResizable(false);

        setVisible(true); // sets game to visible

        // handler for KeyEvents
        addKeyListener(new KeyAdapter() {
            @Override
            // if an arrow is pressed, invokes the move method with a given direction
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP && !gameWon && !gameLost) {
                    move(UP); // 1 is up
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !gameWon && !gameLost) {
                    move(RIGHT); // 2 is right
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT && !gameWon && !gameLost) {
                    move(LEFT); // 3 is left
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN && !gameWon && !gameLost) {
                    move(DOWN); // 4 is down
                }
                else if (e.getKeyCode() == KeyEvent.VK_ENTER && (gameWon || gameLost)) {
                    // adding tile spaces to the game
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            buttonList.set((i * 4) + j, new Tile(j, i));
                        }
                    }
                    // setting each spot in the hashmap to false
                    for (int i = 0; i < 16; i++) {
                        tileIndices.put(i, false);
                    }
                    gameLost = false;
                    gameWon = false;
                    getRandomSpot();
                    getRandomSpot();

                    getContentPane().invalidate();
                    getContentPane().validate();
                    repaint();

                }
            }
        });
    }


    // runs the game
    public static void main(String[] args) {
        new Game();
    }

    @Override
    // animates the game
    public void paint(Graphics g) {

        int size = 125; // screen size divided by four

        g.setColor(Color.decode("#717171"));
        g.fillRect(30, 45, 500, 500);
        g.setColor(Color.decode("#919191"));

        // sets up each tile space
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g.fillRoundRect((size * i) + 20, (size * j) + 35,
                        100, 100, 10, 10);
            }
        }

        // paints each tile
        for (int i = 0; i < buttonList.size(); i++) {
            Tile t = buttonList.get(i);
            t.paint(g);
        }

        if (gameWon) {
            g.setColor(Color.decode("#aaaaaa"));
            g.fillRect(50, 125, 425, 300);
            g.setColor(Color.black);
            g.drawString("You Won!", 200, 250);
            g.drawString("Click return to start a new game", 60, 300);

        }
        else if (gameLost) {
            g.setColor(Color.decode("#aaaaaa"));
            g.fillRect(85, 125, 350, 300);
            g.setColor(Color.black);
            g.drawString("You Lost", 200, 250);
            g.drawString("Click return to play again", 100, 300);
        }
    }

    // returns an ArrayList of integers that contains the indices of each
    // empty space in the game
    private ArrayList<Integer> getZeroNumTiles() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 16; i++) { // for each spot in the game
            if (tileIndices.get(i) == false) { // if the spot is empty, add it to the list
                list.add(i);
            }
        }
        return list;
    }

    // uses the Random rand to generate a random new tile on the board
    private void getRandomSpot() {
        ArrayList<Integer> emptyTiles = getZeroNumTiles(); // gets a list with the number of empty tiles
        int firstTileNum = rand.nextInt(emptyTiles.size()); // gets a random number with a bound of the size of the empty tiles list
        int tileNum = emptyTiles.get(firstTileNum); // uses firstTileNum as the index to get an empty spot

        tileIndices.replace(tileNum, true); // records the new tile in tileIndices
        Tile t = buttonList.get(tileNum); // gets the tile from the list of game tiles

        var value = Math.random() < 0.9 ? 2 : 4; // if the random number generated is greater than .9, the value is a 4
        t.changeValue(value); // changes its value to 2
    }

    // returns whether or not all of the tiles are full
    private boolean tilesFull() {
        int numEmptyTiles = 0;
        for (int i = 0; i < 16; i++) {
            boolean pass = tileIndices.get(i);
            if (!pass) {
                numEmptyTiles++;
            }
        }
        return numEmptyTiles == 0; // returns whether the number of empty tiles equals 0
    }

    // ends the game when it's appropriate
    private void isEndGame() {
        boolean isEnd = true;

        // checks each row and column if there is a move that can be made
        for (int i = 0; i < 4; i++) {
            isEnd = canMove(i, true) && canMove(i * 4, false) && isEnd;
        }

        // if the game has been won, set gameWon to true
        if (wonGame()) {
            gameWon = true;
        }
        // if there are no empty tiles and isEnd is true, set gameLost to true
        else if (tilesFull() && isEnd) {
            gameLost = true;
        }
    }

    // returns true if the game has been won, ie if there is a 2048 tile on the board
    private boolean wonGame() {
        boolean won = false;
        for (int i = 0; i < 16; i++) {
            int val = buttonList.get(i).getValue();
            if (val == 2048) {
                won = true;
            }
        }
        return won;
    }

    // returns true if the player can make a move at any given time
    private boolean canMove(int ind, boolean upOrDown) {
        // initializes i1, i2, and i3, which are the indices used to figure out how far the value should be placed
        int i1, i2, i3, i4;

        if (upOrDown) { // if the direction is up, the indices to check are the top three in the respective column
            i1 = ind;
            i2 = ind + 4;
            i3 = ind + 8;
            i4 = ind + 12;
        }
        else { // if the direction is left, the indices to check are the first three in the respective row
            i1 = ind;
            i2 = ind + 1;
            i3 = ind + 2;
            i4 = ind + 3;
        }

        // gets the tiles at indices i1, i2, and i3
        Tile t1 = buttonList.get(i1);
        Tile t2 = buttonList.get(i2);
        Tile t3 = buttonList.get(i3);
        Tile t4 = buttonList.get(i4);

        // gets the values at those tiles
        int intT1 = t1.getValue();
        int intT2 = t2.getValue();
        int intT3 = t3.getValue();
        int intT4 = t4.getValue();


        return intT1 != intT2 && intT2 != intT3 && intT3 != intT4; // checks if none of the values equal each other
    }



    // moves the value the farthest it can go, based on the given index ind and the direction dir
    private void setFarthest(int ind, int newVal, int dir) {
        // initializes i1, i2, and i3, which are the indices used to figure out how far the value should be placed
        int i1 = -1;
        int i2 = -1;
        int i3 = -1;

        if (dir == UP) { // if the direction is up, the indices to check are the top three in the respective column
            i1 = ind;
            i2 = ind + 4;
            i3 = ind + 8;
        }
        else if (dir == RIGHT) {// if the direction is right, the indices to check are the last three in the respective row
            i1 = ind + 3;
            i2 = ind + 2;
            i3 = ind + 1;
        }
        else if (dir == LEFT) { // if the direction is left, the indices to check are the first three in the respective row
            i1 = ind;
            i2 = ind + 1;
            i3 = ind + 2;
        }
        else if (dir == DOWN) { // if the direction is down, the indices to check are the last three in the respective column
            i1 = ind + 12;
            i2 = ind + 8;
            i3 = ind + 4;
        }

        // gets the tiles at indices i1, i2, and i3
        Tile t1 = buttonList.get(i1);
        Tile t2 = buttonList.get(i2);
        Tile t3 = buttonList.get(i3);

        // gets the values at those tiles
        int intT1 = t1.getValue();
        int intT2 = t2.getValue();
        int intT3 = t3.getValue();

        // if the last tile is empty, place the value there
        if (intT1 == 0) {
            t1.changeValue(newVal);
            tileIndices.replace(i1, true);
        }
        // else, if the second to last tile is empty, place the value there
        else if (intT2 == 0) {
            t2.changeValue(newVal);
            tileIndices.replace(i2, true);
        }
        // else, if the third to last tile is empty, place the value there
        else if (intT3 == 0) {
            t3.changeValue(newVal);
            tileIndices.replace(i3, true);
        }
        // if all of these fail, the tile is at the right place, so return
        else {
            return;
        }
    }

    // method used to move the tile based on a KeyEvent in a given direction
    private void move(int dir) {
        boolean moved = false; // used to check whether or not a new random tile should be generated

        // for 4 rows/columns
        for (int i = 0; i < 4; i++) {
            int ind; // the current index
            int newVal; // the new value being created
            boolean pastOne = false; // checks to see whether the algorithm is past the first tile
            int carryVal = -1; // a value used if there are any carry over values
            int setFarVal; // value that keeps track of what number should go into setFarthest
            boolean isRising; // represents whether the while loop should be rising or not

            // sets the ind, isRising, and setFarVal parameters for each direction
            if (dir == UP) {
                ind = 0;
                isRising = true;
                setFarVal = i;
            }
            else if (dir == RIGHT){
                ind = 3;
                isRising = false;
                setFarVal = i * 4;
            }
            else if (dir == LEFT) {
                ind = 0;
                isRising = true;
                setFarVal = i * 4;
            }
            else {
                ind = 3;
                isRising = false;
                setFarVal = i;
            }

            // while ind is within its appropriate limit
            while (checkLimit(ind, isRising)) {
                int t1Ind, t2Ind;

                // sets t1Ind and t2Ind to their appropriate values. these represent
                // where in the buttonList that Tiles t1 and t2 will be found
                if (dir == UP) {
                    t1Ind = (ind * 4) + i;
                    t2Ind = (ind * 4) + i + 4;
                }
                else if (dir == RIGHT) {
                    t1Ind = (i * 4) + ind;
                    t2Ind = (i * 4) + ind - 1;
                }
                else if (dir == LEFT) {
                    t1Ind = (i * 4) + ind;
                    t2Ind = (i * 4) + ind + 1;
                }
                else {
                    t1Ind = (ind * 4) + i;
                    t2Ind = (ind * 4) + i - 4;
                }

                // initializes the two tiles that are being looked at
                Tile t1 = buttonList.get(t1Ind);
                Tile t2 = buttonList.get(t2Ind);

                // gets the values of the two tiles
                int t1Val = t1.getValue();
                int t2Val = t2.getValue();

                // if the carry over value is equal to either of the values
                if (carryVal == t1Val ^ carryVal == t2Val) {
                    newVal = carryVal * 2; // sets the new value
                    if (carryVal == t1Val) { // if it's equal to t1Val
                        t1.changeValue(0); // change the value of t1 to 0 and replace it in tileIndices
                        tileIndices.replace(t1Ind, false);
                    } else { // if it's equal to t2Val
                        t2.changeValue(0);// change the value of t1 to 0 and replace it in tileIndices
                        tileIndices.replace(t2Ind, false);
                    }
                    setFarthest(setFarVal, newVal, dir); // place the newVal farthest to the given direction
                    carryVal = -1; // reset the carryVal
                }
                // if the two values are equal to each other
                else if (t1Val == t2Val && t1Val > 0) {
                    newVal = t1Val * 2; // sets the new value

                    // changes each of their values, and then replaces their booleans in tileIndices
                    t1.changeValue(0);
                    t2.changeValue(0);
                    tileIndices.replace(t1Ind, false);
                    tileIndices.replace(t2Ind, false);

                    setFarthest(setFarVal, newVal, dir); // place the newVal farthest to the given direction
                    moved = true;
                }
                // if the while loop is in its last iteration and one of the values or carryVal is greater than 0
                else if (checkAlmost(ind, isRising) && ((t1Val > 0 ^ t2Val > 0) || carryVal > 0)) {
                    if (t2Val == carryVal) { // if t2 and carryVal are equal
                        newVal = carryVal * 2; // sets the new value
                        setFarthest(setFarVal, newVal, dir); // place the newVal farthest to the given direction
                        t2.changeValue(0); // changes the value of t2 and replaces it in tileIndicies
                        tileIndices.replace(t2Ind, false);
                        moved = true;
                    }
                    else {
                        if (carryVal > 0) { // if carryVal is greater than 0, place it farthest to the given direction
                            setFarthest(setFarVal, carryVal, dir);
                        }
                        if (t2Val > 0) { // if t2Val is greater than 0, place it farthest to the given direction
                            setFarthest(setFarVal, t2Val, dir);
                            t2.changeValue(0); // changes value of t2 and replaces it in tileIndices
                            tileIndices.replace(t2Ind, false);
                            moved = true;
                        }
                    }
                }
                // if t2Val is the only one greater than 0
                else if (t1Val == 0 && t2Val > 0) {
                    if (carryVal > 0) { // if carryVal isn't -1, set it the farthest to the given direction
                        setFarthest(setFarVal, carryVal, dir);
                    }
                    carryVal = t2Val; // sets a new value for carryVal
                    t2.changeValue(0); // changes the value of t2 and replaces it in tileIndices
                    tileIndices.replace(t2Ind, false);
                    moved = true;
                }
                // if t1Val is the only one greater than 0
                else if (t1Val > 0 && t2Val == 0) {
                    if (carryVal > 0) { // if carryVal isn't -1, set it the farthest to the given direction
                        setFarthest(setFarVal, carryVal, dir);
                    }
                    if (!pastOne) { // if the loop isn't on its first iteration, set moved equal to true
                        moved = true;
                    }
                    carryVal = t1Val; // sets a new value for carryVal
                    t1.changeValue(0); // changes the value of t1 and replaces it in tileIndices
                    tileIndices.replace(t1Ind, false);
                }

                // moves the loop on based on whether or not it's rising
                if (isRising) {
                    ind++;
                }
                else {
                    ind -= 1;
                }

                // signals that the loop is past the first tile
                pastOne = true;
            }
        }

        if (moved) { // if there has been a move, get a random spot
            getRandomSpot();
        }
        isEndGame(); // checks for a win or a loss
        repaint(); // repaints the game
    }

    // used in the move method to check whether or not the number is at the while loop limit
    private boolean checkLimit(int num, boolean isRising) {
        if (isRising) {
            return num < 3;
        } else {
            return num > 0;
        }
    }

    // used in the move method to check whether or not the while loop is at its last iteration
    private boolean checkAlmost(int num, boolean isRising) {
        if (isRising) {
            return num == 2;
        } else {
            return num == 1;
        }
    }

}
