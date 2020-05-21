import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

// represents a Tile that is used in the game

public class Tile extends JFrame {

    private int value; // the integer value of the tile
    private String number; // the String value of the tile
    private int row; // the row it's located in
    private int column; // the column it's located in
    private Color background; // the background color

    static int SIZE = 125;

    public Tile(int x, int row, int column) {
        // sets the initial value to the given number, or 0 if the number is less than 0
        if (x < 0) {
            this.value = 0;
        } else {
            this.value = x;
        }

        // sets row and column equal to specific numbers to fit the screen
        this.row = (SIZE * row) + 20;
        this.column = (SIZE * column) + 35;

        // sets the specific background color
        background = new Color(0x999999);

        // sets the initial string to empty
        number = "";
    }

    public Tile(int row, int column) {
        this.value = 0; // sets the initial value to 0

        // sets row and column equal to specific numbers to fit the screen
        this.row = (SIZE * row) + 20;
        this.column = (SIZE * column) + 35;

        // sets the specific background color
        background = new Color(0x999999);

        // sets the initial string to empty
        number = "";
    }

    @Override
    // animates the game
    public void paint(Graphics g) {
        if (value > 0) { // if the value is anything but 0

            g.setColor(background);// sets the color to the appropriate background color

            // draws the tile
            g.fillRoundRect(row, column,
                    100, 100, 10, 10);

            // sets the text color to black
            g.setColor(Color.BLACK);

            // then, draws the number string. the measurements are based on the size of the string
            if (value > 999) {
                g.drawString(number, row + 20, column + 55);
            }
            else if (value > 99) {
                g.drawString(number, row + 25, column + 55);
            }
            else if (value > 9) {
                g.drawString(number, row + 30, column + 55);
            }
            else {
                g.drawString(number, row + 40, column + 55);
            }
        }
    }

    // changes the color of the tile based on an ArrayList of colors
    private void changeColor() {
        // list of the appropriate colors
        ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(new Color(0x999999), new Color(0xbebebe),
                new Color(0xFFF4E8), new Color(0xFFFFaa), new Color(0xffa544),
                new Color(0xffad7a), Color.RED, new Color(0xFFFF66), new Color(0xFFFF66),
                new Color(0xFFFF66), new Color(0xDDDD00), new Color(0xDDDD00)));

        // gets the List value of a specific value
        int color = logB2(value);

        // sets the background to the appropriate color
        background = colors.get(color);
    }

    // calculates the log base 2 of the given int
    private int logB2(int x) {
        if (x > 0) { // if x is greater than 0, calculate the log
            return (int) (Math.log(x) / Math.log(2));
        }
        return 0; // else, return 0
    }

    // changes the value of the Tile
    public void changeValue(int x) {
        if (x >= 0) {
            value = x; // sets the value equal to the given int
            number = String.valueOf(value); // changes the string to the appropriate value
            changeColor(); // changes the color based on the new value
        }
    }

    // returns the value of the tile
    public int getValue() {
        return value;
    }

}