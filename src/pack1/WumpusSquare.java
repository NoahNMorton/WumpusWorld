package pack1;

/**
 * @author Noah Morton
 *         Created on: 10/20/2014 , Time is :  1:35 PM
 *         Part of Project: WumpusWorldTrogdor
 */

public class WumpusSquare {

    private boolean gold, ladder, pit, breeze, wumpus, deadWumpus, stench, visited;

    public WumpusSquare() {
        gold = false;
        ladder = false;
        pit = false;
        breeze = false;
        wumpus = false;
        deadWumpus = false;
        stench = false;
        visited = false;
    }

    public boolean isGold() {
        return gold;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }

    public boolean isLadder() {
        return ladder;
    }

    public void setLadder(boolean ladder) {
        this.ladder = ladder;
    }

    public boolean isPit() {
        return pit;
    }

    public void setPit(boolean pit) {
        this.pit = pit;
    }

    public boolean isBreeze() {
        return breeze;
    }

    public void setBreeze(boolean breeze) {
        this.breeze = breeze;
    }

    public boolean isWumpus() {
        return wumpus;
    }

    public void setWumpus(boolean wumpus) {
        this.wumpus = wumpus;
        this.deadWumpus = false;
    }

    public boolean isDeadWumpus() {
        return deadWumpus;
    }

    public void setDeadWumpus(boolean deadWumpus) {
        this.deadWumpus = deadWumpus;
        this.wumpus = false;
    }

    public boolean isStench() {
        return stench;
    }

    public void setStench(boolean stench) {
        this.stench = stench;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String toString() {
        if (gold) {
            return "G";
        } else if (ladder) {
            return "L";
        } else if (pit) {
            return "P";
        } else if (wumpus) {
            return "W";
        } else if (deadWumpus) {
            return "D";
        } else {
            return "*";
        }

    }

}
