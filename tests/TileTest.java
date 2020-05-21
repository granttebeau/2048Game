import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    @Test
    public void testChangeValue() { // tests the changeValue method in the Tile class
        Tile tile = new Tile(1, 2); // initializes a tile
        assertEquals(0, tile.getValue()); // checks that the tile is initialized to 0

        tile.changeValue(2); // changes the value
        assertEquals(2, tile.getValue()); // checks if the change worked

        tile.changeValue(2048); // changes the value again
        assertEquals(2048, tile.getValue()); // checks if the change worked again

        tile.changeValue(-1); // changes the value to an invalid number
        assertEquals(2048, tile.getValue()); // checks if the value hasn't changed
    }


    @Test
    public void testGetValue() { // tests the getValue method in the Tile class
        Tile tile1 = new Tile(2, 3, 2);// initializes a tile
        assertEquals(2, tile1.getValue()); // checks if the initialized number was correct
        tile1.changeValue(4); // changes the value of the tile
        assertEquals(4, tile1.getValue()); // checks to see if getValue accounted for the change

        Tile tile2 = new Tile(-1, 3, 0); // initializes a tile
        assertEquals(0, tile2.getValue()); // checks to see if the value was set to 0 with the invalid number
        tile2.changeValue(1024); // changes the value of the tile
        assertEquals(1024, tile2.getValue());  // checks to see if getValue accounted for the change

        Tile tile3 = new Tile(1, 2); // initializes a tile without a value
        assertEquals(0, tile3.getValue()); // checks if the initialized number was correct
    }

}