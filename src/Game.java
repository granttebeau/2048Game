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
                buttonList.add(new Tile(0, j, i));
            }
        }
        // setting each spot in the hashmap to false
        for (int i = 0; i < 16; i++) {
            tileIndices.put(i, false);
        }

        buttonList.get(0).changeValue(8);
        tileIndices.replace(0, true);
        buttonList.get(1).changeValue(2);
        tileIndices.replace(1, true);
        buttonList.get(2).changeValue(2);
        tileIndices.replace(2, true);
        buttonList.get(3).changeValue(2);
        tileIndices.replace(3, true);
        buttonList.get(4).changeValue(4);
        tileIndices.replace(4, true);
        buttonList.get(5).changeValue(2);
        tileIndices.replace(5, true);
        buttonList.get(6).changeValue(4);
        tileIndices.replace(6, true);
        buttonList.get(7).changeValue(2);
        tileIndices.replace(7, true);
        buttonList.get(8).changeValue(2);
        tileIndices.replace(8, true);
        buttonList.get(9).changeValue(4);
        tileIndices.replace(9, true);
        buttonList.get(10).changeValue(2);
        tileIndices.replace(10, true);
        buttonList.get(11).changeValue(4);
        tileIndices.replace(11, true);
        buttonList.get(12).changeValue(4);
        tileIndices.replace(12, true);
        buttonList.get(13).changeValue(2);
        tileIndices.replace(13, true);
        buttonList.get(14).changeValue(4);
        tileIndices.replace(14, true);
        buttonList.get(15).changeValue(2);
        tileIndices.replace(15, true);


        // generates two random spots at the beginning of the game
        //getRandomSpot();
        //getRandomSpot();

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
                else if (e.getKeyCode() == KeyEvent.VK_SPACE && (gameWon || gameLost)) {
                    // adding tile spaces to the game
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            buttonList.set((i * 4) + j, new Tile(0, j, i));
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
        g.setColor(Color.decode("#919191"));
        int size = 125; // screen size divided by four

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
            g.setColor(Color.decode("#717171"));
            g.fillRect(132, 145, 250, 250);
            g.setColor(Color.black);
            g.drawString("You Won!", 200, 250);
        }
        else if (gameLost) {
            g.setColor(Color.decode("#717171"));
            g.fillRect(132, 145, 250, 250);
            g.setColor(Color.black);
            g.drawString("You Lost", 200, 250);
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

        tileIndices.replace(firstTileNum, true); // records the new tile in tileIndices
        Tile t = buttonList.get(tileNum); // gets the tile from the list of game tiles

        var value = Math.random() < 0.9 ? 2 : 4; // if the random number generated is greater than .9, the value is a 4
        t.changeValue(value); // changes its value to 2
    }

    // returns whether or not all the tiles are full
    private boolean tilesFull() {
        int num = 0;
        for (int i = 0; i < 16; i++) {
            if (!tileIndices.get(i)) {
                num++;
            }
        }
        return num == 0;
    }

    // ends the game when it's appropriate
    private void isEndGame() {
        boolean isEnd = true;

        for (int i = 0; i < 4; i++) {
            isEnd = canMove(i, 1) && canMove(i * 4, 2)
                    && canMove(i * 4, 3) && canMove(i, 4) && isEnd;
        }

        if (wonGame()) {
            gameWon = true;
        }
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
    private boolean canMove(int ind, int dir) {
        // initializes i1, i2, and i3, which are the indices used to figure out how far the value should be placed
        int i1 = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;

        if (dir == UP || dir == DOWN) { // if the direction is up, the indices to check are the top three in the respective column
            i1 = ind;
            i2 = ind + 4;
            i3 = ind + 8;
            i4 = ind + 12;
        }
        else if (dir == LEFT || dir == RIGHT) { // if the direction is left, the indices to check are the first three in the respective row
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

        return intT1 > 0 && intT2 > 0 && intT3 > 0 && intT4 > 0
                && intT1 != intT2 && intT2 != intT3 && intT3 != intT4;
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

        if (dir == UP) {
            for (int i = 0; i < 4; i++) {
                int ind = 0;
                int newVal;
                boolean pastOne = false;
                int carryVal = -1;
                while (ind < 3) {
                    int col = ind * 4;
                    Tile t1 = buttonList.get(col + i);
                    Tile t2 = buttonList.get(col + i + 4);
                    int t1Val = t1.getValue();
                    int t2Val = t2.getValue();

                    if (carryVal == t1Val ^ carryVal == t2Val) {
                        newVal = carryVal * 2;
                        if (carryVal == t1Val) {
                            t1.changeValue(0);
                            tileIndices.replace(col + i, false);
                        } else {
                            t2.changeValue(0);
                            tileIndices.replace(col + i + 4, false);
                        }
                        setFarthest(i, newVal, UP);
                        carryVal = -1;
                        moved = true;
                    }
                    else if (t1Val == t2Val && t1Val > 0) {
                        newVal = t1Val * 2;
                        t1.changeValue(0);
                        t2.changeValue(0);
                        tileIndices.replace(col + i, false);
                        tileIndices.replace(col + i + 4, false);
                        setFarthest(i, newVal, UP);
                        moved = true;
                    }
                    else if (ind == 2 && ((t1Val > 0 ^ t2Val > 0) || carryVal > 0)) {
                        if (t2Val == carryVal) {
                            newVal = carryVal * 2;
                            setFarthest(i, newVal, UP);
                            t2.changeValue(0);
                            tileIndices.replace(col + i + 4, false);
                            moved = true;
                        }
                        else {
                            if (carryVal > 0) {
                                setFarthest(i, carryVal, UP);
                            }
                            if (t2Val > 0) {
                                setFarthest(i, t2Val, UP);
                                t2.changeValue(0);
                                tileIndices.replace(col + i + 4, false);
                                moved = true;
                            }
                        }

                    }
                    else if (t1Val == 0 && t2Val > 0) {
                        if (carryVal > 0) {
                            setFarthest(i, carryVal, UP);
                        }
                        carryVal = t2.getValue();
                        t2.changeValue(0);
                        tileIndices.replace(col + i + 4, false);
                        moved = true;
                    }
                    else if (t1Val > 0 && t2Val == 0) {
                        if (carryVal > 0) {
                            setFarthest(i, carryVal, UP);
                        }
                        if (ind > 0 && !pastOne) {
                            moved = true;
                        }
                        carryVal = t1.getValue();
                        t1.changeValue(0);
                        tileIndices.replace(col + i, false);
                    }
                    ind++;
                    pastOne = true;
                }
            }
        }

        else if (dir == RIGHT) {
            for (int i = 0; i < 4; i++) {
                int ind = 3;
                int newVal;
                boolean pastOne = false;
                int carryVal = -1;
                while (ind > 0) {
                    Tile t1 = buttonList.get((i * 4) + ind);
                    Tile t2 = buttonList.get((i * 4) + ind - 1);
                    int t1Val = t1.getValue();
                    int t2Val = t2.getValue();

                    if (carryVal == t1Val ^ carryVal == t2Val) {
                        newVal = carryVal * 2;
                        if (carryVal == t1Val) {
                            t1.changeValue(0);
                            tileIndices.replace((i * 4) + ind, false);
                        } else {
                            t2.changeValue(0);
                            tileIndices.replace((i * 4) + ind - 1, false);
                        }
                        setFarthest(i * 4, newVal, RIGHT);
                        carryVal = -1;
                        moved = true;
                    }
                    else if (t1Val == t2Val && t1Val > 0) {
                        newVal = t1Val * 2;
                        t1.changeValue(0);
                        t2.changeValue(0);
                        tileIndices.replace((i * 4) + ind, false);
                        tileIndices.replace((i * 4) + ind - 1, false);
                        setFarthest(i * 4, newVal, RIGHT);
                        moved = true;
                    }
                    else if (ind == 1 && ((t1Val > 0 ^ t2Val > 0) || carryVal > 0)) {
                        if (t2Val == carryVal) {
                            newVal = carryVal * 2;
                            setFarthest(i * 4, newVal, RIGHT);
                            t2.changeValue(0);
                            tileIndices.replace((i * 4) + ind - 1, false);
                            moved = true;
                        }
                        else {
                            if (carryVal > 0) {
                                setFarthest(i * 4, carryVal, RIGHT);
                            }
                            if (t2Val > 0) {
                                setFarthest(i * 4, t2Val, RIGHT);
                                t2.changeValue(0);
                                tileIndices.replace((i * 4) + ind - 1, false);
                                moved = true;

                            }
                        }

                    }
                    else if (t1Val == 0 && t2Val > 0) {
                        if (carryVal > 0) {
                            setFarthest(i * 4, carryVal, RIGHT);
                        }
                        carryVal = t2.getValue();
                        t2.changeValue(0);
                        tileIndices.replace((i * 4) + ind - 1, false);
                        moved = true;
                    }
                    else if (t1Val > 0 && t2Val == 0) {
                        if (carryVal > 0) {
                            setFarthest(i * 4, carryVal, RIGHT);
                        }
                        if (ind < 3 && !pastOne) {
                            moved = true;
                        }
                        carryVal = t1.getValue();
                        t1.changeValue(0);
                        tileIndices.replace((i * 4) + ind, false);
                    }
                    ind -= 1;
                    pastOne = true;
                }
            }
        }
        else if (dir == LEFT) {
            for (int i = 0; i < 4; i++) {
                int ind = 0;
                int newVal;
                boolean pastOne = false;
                int carryVal = -1;
                while (ind < 3) {
                    Tile t1 = buttonList.get((i * 4) + ind);
                    Tile t2 = buttonList.get((i * 4) + ind + 1);
                    int t1Val = t1.getValue();
                    int t2Val = t2.getValue();

                    if (carryVal == t1Val ^ carryVal == t2Val) {
                        newVal = carryVal * 2;
                        if (carryVal == t1Val) {
                            t1.changeValue(0);
                            tileIndices.replace((i * 4) + ind, false);
                        } else {
                            t2.changeValue(0);
                            tileIndices.replace((i * 4) + ind + 1, false);
                        }
                        setFarthest(i * 4, newVal, LEFT);
                        carryVal = -1;
                        moved = true;
                    }
                    else if (t1Val == t2Val && t1Val > 0) {
                        newVal = t1Val * 2;
                        t1.changeValue(0);
                        t2.changeValue(0);
                        tileIndices.replace((i * 4) + ind, false);
                        tileIndices.replace((i * 4) + ind + 1, false);
                        setFarthest(i * 4, newVal, LEFT);
                        moved = true;
                    }
                    else if (ind == 2 && ((t1Val > 0 ^ t2Val > 0) || carryVal > 0)) {
                        if (t2Val == carryVal) {
                            newVal = carryVal * 2;
                            setFarthest(i * 4, newVal, LEFT);
                            t2.changeValue(0);
                            tileIndices.replace((i * 4) + ind + 1, false);
                            moved = true;
                        }
                        else {
                            if (carryVal > 0) {
                                setFarthest(i * 4, carryVal, LEFT);
                            }
                            if (t2Val > 0) {
                                t2.changeValue(0);
                                setFarthest(i * 4, t2Val, LEFT);
                                tileIndices.replace((i * 4) + ind + 1, false);
                                moved = true;
                            }
                        }

                    }
                    else if (t1Val == 0 && t2Val > 0) {
                        if (carryVal > 0) {
                            setFarthest(i * 4, carryVal, LEFT);
                        }
                        carryVal = t2.getValue();
                        t2.changeValue(0);
                        tileIndices.replace((i * 4) + ind + 1, false);
                        moved = true;
                    }
                    else if (t1Val > 0 && t2Val == 0) {
                        if (carryVal > 0) {
                            setFarthest(i * 4, carryVal, LEFT);
                        }
                        if (ind > 0 && !pastOne) {
                            moved = true;
                        }
                        carryVal = t1.getValue();
                        t1.changeValue(0);
                        tileIndices.replace((i * 4) + ind, false);
                    }
                    ind++;
                    pastOne = true;
                }
            }
        }

        else if (dir == DOWN) {
            for (int i = 0; i < 4; i++) {
                int ind = 3;
                int newVal;
                boolean pastOne = false;
                int carryVal = -1;
                while (ind > 0) {
                    int col = ind * 4;
                    Tile t1 = buttonList.get(col + i);
                    Tile t2 = buttonList.get((col + i) - 4);
                    int t1Val = t1.getValue();
                    int t2Val = t2.getValue();

                    if (carryVal == t1Val ^ carryVal == t2Val) {
                        newVal = carryVal * 2;
                        if (carryVal == t1Val) {
                            t1.changeValue(0);
                            tileIndices.replace(col + i, false);
                        } else {
                            t2.changeValue(0);
                            tileIndices.replace(col + i - 4, false);
                        }
                        setFarthest(i, newVal, DOWN);
                        carryVal = -1;
                        moved = true;
                    }
                    else if (t1Val == t2Val && t1Val > 0) {
                        newVal = t1Val * 2;
                        t1.changeValue(0);
                        t2.changeValue(0);
                        tileIndices.replace(col + i, false);
                        tileIndices.replace(col + i - 4, false);
                        setFarthest(i, newVal, DOWN);
                        moved = true;
                    }
                    else if (ind == 1 && ((t1Val > 0 ^ t2Val > 0) || carryVal > 0)) {
                        if (t2Val == carryVal) {
                            newVal = carryVal * 2;
                            setFarthest(i, newVal, DOWN);
                            t2.changeValue(0);
                            tileIndices.replace(col + i - 4, false);
                            moved = true;
                        }
                        else {
                            if (carryVal > 0) {
                                setFarthest(i, carryVal, DOWN);
                            }
                            if (t2Val > 0) {
                                setFarthest(i, t2Val, DOWN);
                                t2.changeValue(0);
                                tileIndices.replace(col + i - 4, false);
                                moved = true;
                            }
                        }

                    }
                    else if (t1Val == 0 && t2Val > 0) {
                        if (carryVal > 0) {
                            setFarthest(i, carryVal, DOWN);
                        }
                        carryVal = t2.getValue();
                        t2.changeValue(0);
                        tileIndices.replace(col + i - 4, false);
                        moved = true;
                    }
                    else if (t1Val > 0 && t2Val == 0) {
                        if (carryVal > 0) {
                            setFarthest(i, carryVal, DOWN);
                        }
                        if (ind < 3 && !pastOne) {
                            moved = true;
                        }
                        carryVal = t1.getValue();
                        t1.changeValue(0);
                        tileIndices.replace(col + i, false);
                    }
                    ind -= 1;
                    pastOne = true;
                }
            }
        }

        if (moved) {
            getRandomSpot();
        }
        repaint();
        isEndGame();
    }

}
