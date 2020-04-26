import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class Tile extends JFrame {
    private int value;
    private String number;
    private int row;
    private int column;
    private Color background;


    public Tile(int value, int row, int column) {
        this.value = value;
        this.row = (125 * row) + 20;
        this.column = (125 * column) + 35;
        changeColor();
        number = "";
    }

    @Override
    public void paint(Graphics g) {
        if (value > 0) {
            g.setColor(background);
            g.fillRoundRect(row, column,
                    100, 100, 10, 10);
            g.setColor(Color.BLACK);
            if (value > 999) {
                g.drawString(number, row + 20, column + 55);
            }
            else if (value > 99) {
                g.drawString(number, row + 25, column + 55);
            }
            else {
                g.drawString(number, row + 40, column + 55);
            }
        }
    }

    private void changeColor() {
        ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(new Color(0x999999), new Color(0xbebebe),
                new Color(0xFFF4E8), new Color(0xFFFFaa), new Color(0xffa544),
                new Color(0xffad7a), Color.RED, new Color(0xFFFF66), new Color(0xFFFF66),
                new Color(0xFFFF66), new Color(0xDDDD00), new Color(0xDDDD00)));
        int color = logB2(value);
        background = colors.get(color);
    }

    private int logB2(int x) {
        if (x != 0) {
            return (int) (Math.log(x) / Math.log(2));
        }
        return 0;
    }

    public void changeValue(int x) {
        value = x;
        number = String.valueOf(value);
        changeColor();
    }

    public int getValue() {
        return value;
    }

}
