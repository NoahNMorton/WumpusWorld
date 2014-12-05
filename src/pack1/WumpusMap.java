package pack1;

/**
 * @author Noah Morton
 *         Created on: 10/21/2014 , Time is :  17:53
 *         Part of Project: WumpusWorldTrogdor
 */

public class WumpusMap {

    public static final int NUM_ROWS = 10, NUM_COLUMNS = 10, NUM_PITS = 10;
    private WumpusSquare[][] grid;
    private int ladderC, ladderR;

    public WumpusMap() {
        createMap();
    }

    public int getLadderCol() {
        return ladderC;
    }

    public int getLadderRow() {
        return ladderR;
    }

    /**
     * returns the data about the square specified.
     *
     * @param row row of the grid array
     * @param col column of the grid array
     * @return returns the WumpusSquare at the specified coords
     */
    public WumpusSquare getSquare(int row, int col) {
        if (row < 0 || row >= NUM_ROWS || col < 0 || col >= NUM_COLUMNS) {
            return null;
        }

        return grid[row][col];

    }

    /**
     * Creates the map.
     */
    public void createMap() {
        grid = new WumpusSquare[NUM_ROWS][NUM_COLUMNS];

        for (int y = 0; y < NUM_ROWS; y++) {
            for (int x = 0; x < NUM_COLUMNS; x++) {
                grid[y][x] = new WumpusSquare();
            }
        }


        //*************************************** PLACE PITS

        for (int i = 0; i < NUM_PITS; i++) {

            boolean success = false;
            while (!success) {
                int randomXp = (int) (Math.random() * NUM_ROWS), randomYp = (int) (Math.random() * NUM_COLUMNS);
                if (!grid[randomXp][randomYp].isPit()) {
                    grid[randomXp][randomYp].setPit(true);
                    if (randomXp - 1 >= 0) {
                        grid[randomXp - 1][randomYp].setBreeze(true);
                        success = true;
                    }
                    if (randomXp + 1 < 10) {
                        grid[randomXp + 1][randomYp].setBreeze(true);
                        success = true;
                    }
                    if (randomYp - 1 >= 0) {
                        grid[randomXp][randomYp - 1].setBreeze(true);
                        success = true;
                    }
                    if (randomYp + 1 < 10) {
                        grid[randomXp][randomYp + 1].setBreeze(true);
                        success = true;
                    }

                }
            }
        }

        //********************************* PLACE GOLD


        int randomXg, randomYg;
        while (true) {
            randomXg = (int) (Math.random() * NUM_ROWS);
            randomYg = (int) (Math.random() * NUM_COLUMNS);

            if (!grid[randomXg][randomYg].isPit())  //if not pitt,brad
            {
                grid[randomXg][randomYg].setGold(true);
                System.out.println("Gold is at: " + randomXg + "," + randomYg);
                break;
            }
        }
        //******************************** PLACE WUMPUS

        while (true) {
            int randomXw = (int) (Math.random() * NUM_ROWS), randomYw = (int) (Math.random() * NUM_COLUMNS);
            if (!grid[randomXw][randomYw].isPit() && !grid[randomXw][randomYw].isGold()) {
                grid[randomXw][randomYw].setWumpus(true);
                System.out.println("Wumpus is at: " + randomXw + "," + randomYw);
                if (randomXw - 1 >= 0) //left
                    grid[randomXw - 1][randomYw].setStench(true);
                if (randomXw + 1 < 10) //right
                    grid[randomXw + 1][randomYw].setStench(true);
                if (randomYw - 1 >= 0) //up
                    grid[randomXw][randomYw - 1].setStench(true);
                if (randomYw + 1 < 10) //down
                    grid[randomXw][randomYw + 1].setStench(true);
                break;
            }

        }
        //****************************** PLACE LADDER
        while (true) {
            int randomXl = (int) (Math.random() * NUM_ROWS), randomYl = (int) (Math.random() * NUM_COLUMNS);
            if (!grid[randomXl][randomYl].isPit() && !grid[randomXl][randomYl].isWumpus() && !grid[randomXl][randomYl].isGold()) {
                grid[randomXl][randomYl].setLadder(true);
                ladderC = randomYl;
                ladderR = randomXl;
                break;
            }
        }

    }

    public String toString() {
        String finalString = "";
        for (int x = 0; x < NUM_COLUMNS; x++) {
            System.out.print("\n");
            for (int y = 0; y < NUM_ROWS; y++) {
                finalString += grid[y][x].toString() + " "; //todo >help, not working.
            }

        }
        return finalString;
    }


}
