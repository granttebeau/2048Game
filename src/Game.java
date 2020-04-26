import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;

public class Game extends JFrame {

    ArrayList<Tile> buttonList = new ArrayList<Tile>();
    HashMap<Integer, Boolean> tileIndices = new HashMap<Integer, Boolean>();
    private Random rand = new Random();

    Game() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttonList.add(new Tile(0, j, i));
            }
        }
        for (int i = 0; i < 16; i++) {
            tileIndices.put(i, false);
        }

        getRandomSpot();
        getRandomSpot();
        setBackground(Color.decode("#717171"));
        setFont(new Font("SansSerif", Font.BOLD, 24));
        setSize(525, 525);
        setResizable(false);
        setLayout(null);
        setVisible(true);


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    move(1);
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    move(2);
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    move(3);
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    move(4);
                }
            }
        });
    }


    public static void main(String[] args) {
        new Game();
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.decode("#919191"));
        int size = 125;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g.fillRoundRect((size * i) + 20, (size * j) + 35,
                        100, 100, 10, 10);
            }
        }

        for (int i = 0; i < buttonList.size(); i++) {
            Tile t = buttonList.get(i);
            t.paint(g);
        }
    }

    private ArrayList<Integer> getZeroNumTiles() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 16; i++) {
            if (tileIndices.get(i) == false) {
                list.add(i);
            }
        }
        return list;
    }

    private int getNumTiles() {
        int num = 0;
        for (int i = 0; i < 16; i++) {
            if (tileIndices.get(i) == true) {
                num++;
            }
        }
        return num;
    }


    private void getRandomSpot() {
        ArrayList<Integer> emptyTiles = getZeroNumTiles();
        int firstTileNum = rand.nextInt(emptyTiles.size());
        int tileNum = emptyTiles.get(firstTileNum);

        tileIndices.replace(firstTileNum, true);
        Tile t = buttonList.get(tileNum);
        t.changeValue(2);
    }

    private void setFarthestRight(int row, int newVal) {
        Tile t4 = buttonList.get(row + 3);
        Tile t3 = buttonList.get(row + 2);
        Tile t2 = buttonList.get(row + 1);
        int intT4 = t4.getValue();
        int intT3 = t3.getValue();
        int intT2 = t2.getValue();
        if (intT4 == 0) {
            t4.changeValue(newVal);
            tileIndices.replace(row + 3, true);
        }
        else if (intT3 == 0) {
            t3.changeValue(newVal);
            tileIndices.replace(row + 2, true);
        }
        else if (intT2 == 0) {
            t2.changeValue(newVal);
            tileIndices.replace(row + 1, true);
        }
        else {
            return;
        }
    }

    private void setFarthestLeft(int row, int newVal) {
        Tile t1 = buttonList.get(row);
        Tile t2 = buttonList.get(row + 1);
        Tile t3 = buttonList.get(row + 2);
        int intT1 = t1.getValue();
        int intT2 = t2.getValue();
        int intT3 = t3.getValue();
        if (intT1 == 0) {
            t1.changeValue(newVal);
            tileIndices.replace(row, true);
        }
        else if (intT2 == 0) {
            t2.changeValue(newVal);
            tileIndices.replace(row + 1, true);
        }
        else if (intT3 == 0) {
            t3.changeValue(newVal);
            tileIndices.replace(row + 2, true);
        }
        else {
            return;
        }
    }

    private void setFarthestUp(int column, int newVal) {
        Tile t1 = buttonList.get(column);
        Tile t2 = buttonList.get(column + 4);
        Tile t3 = buttonList.get(column + 8);
        int intT1 = t1.getValue();
        int intT2 = t2.getValue();
        int intT3 = t3.getValue();
        if (intT1 == 0) {
            t1.changeValue(newVal);
            tileIndices.replace(column, true);
        }
        else if (intT2 == 0) {
            t2.changeValue(newVal);
            tileIndices.replace(column + 4, true);
        }
        else if (intT3 == 0) {
            t3.changeValue(newVal);
            tileIndices.replace(column + 8, true);
        }
        else {
            return;
        }
    }

    private void setFarthestDown(int column, int newVal) {
        Tile t1 = buttonList.get(column + 12);
        Tile t2 = buttonList.get(column + 8);
        Tile t3 = buttonList.get(column + 4);
        int intT1 = t1.getValue();
        int intT2 = t2.getValue();
        int intT3 = t3.getValue();
        if (intT1 == 0) {
            t1.changeValue(newVal);
            tileIndices.replace(column + 12, true);
        }
        else if (intT2 == 0) {
            t2.changeValue(newVal);
            tileIndices.replace(column + 8, true);
        }
        else if (intT3 == 0) {
            t3.changeValue(newVal);
            tileIndices.replace(column + 4, true);
        }
        else {
            return;
        }
    }

    private void move(int dir) {
        boolean moved = false;

        if (dir == 1) {
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
                        setFarthestUp(i, newVal);
                        carryVal = -1;
                        moved = true;
                    }
                    else if (t1Val == t2Val && t1Val > 0) {
                        newVal = t1Val * 2;
                        t1.changeValue(0);
                        t2.changeValue(0);
                        tileIndices.replace(col + i, false);
                        tileIndices.replace(col + i + 4, false);
                        setFarthestUp(i, newVal);
                        moved = true;
                    }
                    else if (ind == 2 && ((t1Val > 0 ^ t2Val > 0) || carryVal > 0)) {
                        if (t2Val == carryVal) {
                            newVal = carryVal * 2;
                            setFarthestUp(i, newVal);
                            t2.changeValue(0);
                            tileIndices.replace(col + i + 4, false);
                            moved = true;
                        }
                        else {
                            if (carryVal > 0) {
                                setFarthestUp(i, carryVal);
                            }
                            if (t2Val > 0) {
                                setFarthestUp(i, t2Val);
                                t2.changeValue(0);
                                tileIndices.replace(col + i + 4, false);
                                moved = true;
                            }
                        }

                    }
                    else if (t1Val == 0 && t2Val > 0) {
                        if (carryVal > 0) {
                            setFarthestUp(i, carryVal);
                        }
                        carryVal = t2.getValue();
                        t2.changeValue(0);
                        tileIndices.replace(col + i + 4, false);
                        moved = true;
                    }
                    else if (t1Val > 0 && t2Val == 0) {
                        if (carryVal > 0) {
                            setFarthestUp(i, carryVal);
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

        else if (dir == 2) {
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
                        setFarthestRight(i * 4, newVal);
                        carryVal = -1;
                        moved = true;
                    }
                    else if (t1Val == t2Val && t1Val > 0) {
                        newVal = t1Val * 2;
                        t1.changeValue(0);
                        t2.changeValue(0);
                        tileIndices.replace((i * 4) + ind, false);
                        tileIndices.replace((i * 4) + ind - 1, false);
                        setFarthestRight(i * 4, newVal);
                        moved = true;
                    }
                    else if (ind == 1 && ((t1Val > 0 ^ t2Val > 0) || carryVal > 0)) {
                        if (t2Val == carryVal) {
                            newVal = carryVal * 2;
                            setFarthestRight(i * 4, newVal);
                            t2.changeValue(0);
                            tileIndices.replace((i * 4) + ind - 1, false);
                            moved = true;
                        }
                        else {
                            if (carryVal > 0) {
                                setFarthestRight(i * 4, carryVal);
                            }
                            if (t2Val > 0) {
                                setFarthestRight(i * 4, t2Val);
                                t2.changeValue(0);
                                tileIndices.replace((i * 4) + ind - 1, false);
                                moved = true;

                            }
                        }

                    }
                    else if (t1Val == 0 && t2Val > 0) {
                        if (carryVal > 0) {
                            setFarthestRight(i * 4, carryVal);
                        }
                        carryVal = t2.getValue();
                        t2.changeValue(0);
                        tileIndices.replace((i * 4) + ind - 1, false);
                        moved = true;
                    }
                    else if (t1Val > 0 && t2Val == 0) {
                        if (carryVal > 0) {
                            setFarthestRight(i * 4, carryVal);
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
        else if (dir == 3) {
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
                        setFarthestLeft(i * 4, newVal);
                        carryVal = -1;
                        moved = true;
                    }
                    else if (t1Val == t2Val && t1Val > 0) {
                        newVal = t1Val * 2;
                        t1.changeValue(0);
                        t2.changeValue(0);
                        tileIndices.replace((i * 4) + ind, false);
                        tileIndices.replace((i * 4) + ind + 1, false);
                        setFarthestLeft(i * 4, newVal);
                        moved = true;
                    }
                    else if (ind == 2 && ((t1Val > 0 ^ t2Val > 0) || carryVal > 0)) {
                        if (t2Val == carryVal) {
                            newVal = carryVal * 2;
                            setFarthestLeft(i * 4, newVal);
                            t2.changeValue(0);
                            tileIndices.replace((i * 4) + ind + 1, false);
                            moved = true;
                        }
                        else {
                            if (carryVal > 0) {
                                setFarthestLeft(i * 4, carryVal);
                            }
                            if (t2Val > 0) {
                                t2.changeValue(0);
                                setFarthestLeft(i * 4, t2Val);
                                tileIndices.replace((i * 4) + ind + 1, false);
                                moved = true;
                            }
                        }

                    }
                    else if (t1Val == 0 && t2Val > 0) {
                        if (carryVal > 0) {
                            setFarthestLeft(i * 4, carryVal);
                        }
                        carryVal = t2.getValue();
                        t2.changeValue(0);
                        tileIndices.replace((i * 4) + ind + 1, false);
                        moved = true;
                    }
                    else if (t1Val > 0 && t2Val == 0) {
                        if (carryVal > 0) {
                            setFarthestLeft(i * 4, carryVal);
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

        else if (dir == 4) {
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
                        setFarthestDown(i, newVal);
                        carryVal = -1;
                        moved = true;
                    }
                    else if (t1Val == t2Val && t1Val > 0) {
                        newVal = t1Val * 2;
                        t1.changeValue(0);
                        t2.changeValue(0);
                        tileIndices.replace(col + i, false);
                        tileIndices.replace(col + i - 4, false);
                        setFarthestDown(i, newVal);
                        moved = true;
                    }
                    else if (ind == 1 && ((t1Val > 0 ^ t2Val > 0) || carryVal > 0)) {
                        if (t2Val == carryVal) {
                            newVal = carryVal * 2;
                            setFarthestDown(i, newVal);
                            t2.changeValue(0);
                            tileIndices.replace(col + i - 4, false);
                            moved = true;
                        }
                        else {
                            if (carryVal > 0) {
                                setFarthestDown(i, carryVal);
                            }
                            if (t2Val > 0) {
                                setFarthestDown(i, t2Val);
                                t2.changeValue(0);
                                tileIndices.replace(col + i - 4, false);
                                moved = true;
                            }
                        }

                    }
                    else if (t1Val == 0 && t2Val > 0) {
                        if (carryVal > 0) {
                            setFarthestDown(i, carryVal);
                        }
                        carryVal = t2.getValue();
                        t2.changeValue(0);
                        tileIndices.replace(col + i - 4, false);
                        moved = true;
                    }
                    else if (t1Val > 0 && t2Val == 0) {
                        if (carryVal > 0) {
                            setFarthestDown(i, carryVal);
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
    }

}
