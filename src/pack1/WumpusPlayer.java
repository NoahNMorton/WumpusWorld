package pack1;

/**
 * @author Noah Morton
 *         Created on: 10/20/2014 , Time is :  1:27 PM
 *         Part of Project: WumpusWorldTrogdor
 */
public class WumpusPlayer {
    public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    private int direction;
    private boolean arrow, gold;
    private int colPosition, rowPosition;

    public WumpusPlayer() {
        direction = NORTH;
        gold = false;
        arrow = true;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isArrow() {
        return arrow;
    }

    public void setArrow(boolean arrow) {
        this.arrow = arrow;
    }

    public boolean isGold() {
        return gold;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }

    public int getColPosition() {
        return colPosition;
    }

    public void setColPosition(int colPosition) {
        this.colPosition = colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }


}
