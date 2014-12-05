package pack1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * @author Noah Morton
 *         Created on: 10/21/2014 , Time is :  17:53
 *         Part of Project: WumpusWorldTrogdor
 */
public class WumpusPanel extends JPanel implements KeyListener {

    public static final int PLAYING = 0, DEAD = 2, WON = 1;
    private static boolean displayFog = true;
    int status;
    WumpusPlayer player;
    WumpusMap map;
    Image arrow, black, breeze, deadWumpus, floor, gold, ladder, pit, playerDown, playerLeft, playerRight, playerUp, wumpus, stench;


    public WumpusPanel() {


        setSize(500, 700); //set the size of the window


        //load images
        try {
            arrow = ImageIO.read((new File("arrow.gif")));
           // black = ImageIO.read((new File("black.GIF")));
            breeze = ImageIO.read((new File("breeze.gif")));
            deadWumpus = ImageIO.read((new File("deadwumpus.GIF")));
            floor = ImageIO.read((new File("Floor.gif")));
            gold = ImageIO.read((new File("gold.gif")));
            ladder = ImageIO.read((new File("ladder.gif")));
            pit = ImageIO.read((new File("pit.gif")));
            playerDown = ImageIO.read((new File("playerDown.png")));
            playerLeft = ImageIO.read((new File("playerLeft.png")));
            playerRight = ImageIO.read((new File("playerRight.png")));
            playerUp = ImageIO.read((new File("playerUp.png")));
            stench = ImageIO.read((new File("stench.gif")));
            wumpus = ImageIO.read((new File("wumpus.gif")));

        } catch (Exception e) {
            System.err.println("Error Loading Images: " + e.getMessage());
            System.exit(-1); //if loading fails, end the program.
        }
        addKeyListener(this);
        reset();

    }

    public void reset() {
        status = PLAYING; //set game status to playing
        //initialise map and player
        map = new WumpusMap();
        player = new WumpusPlayer();
        //set the player to the ladder's location
        player.setColPosition(map.getLadderCol());
        player.setRowPosition(map.getLadderRow());
        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);

    }


    public void keyReleased(KeyEvent event) {

    }

    public void keyTyped(KeyEvent event) {

        switch (event.getKeyChar()) {
            case 'w':
                if (status == PLAYING) {
                    if (player.getRowPosition() - 1 >= 0) {
                        player.setRowPosition(player.getRowPosition() - 1);
                        player.setDirection(WumpusPlayer.NORTH);
                        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                    }
                }
                break;
            case 's':
                if (status == PLAYING) {
                    if (player.getRowPosition() + 1 < 10) {
                        player.setRowPosition(player.getRowPosition() + 1);
                        player.setDirection(WumpusPlayer.SOUTH);
                        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                    }
                }
                break;
            case 'a':
                if (status == PLAYING) {
                    if (player.getColPosition() - 1 >= 0) {
                        player.setColPosition(player.getColPosition() - 1);
                        player.setDirection(WumpusPlayer.WEST);
                        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                    }
                }
                break;
            case 'd':
                if (status == PLAYING) {
                    if (player.getColPosition() + 1 < 10) {
                        player.setColPosition(player.getColPosition() + 1);
                        player.setDirection(WumpusPlayer.EAST);
                        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                    }
                }
                break;
            case 'i': //shoot upward
                if (player.isArrow()) {
                    player.setArrow(false);
                    for (int y = player.getRowPosition(); y > 0; y--) {
                        if (map.getSquare(y, player.getColPosition()).isWumpus()) {
                            System.out.println("Wumpus killed.");
                            map.getSquare(y, player.getColPosition()).setDeadWumpus(true);
                        }
                    }
                }
                break;
            case 'k': //shoot downward
                if (player.isArrow()) {
                    player.setArrow(false);
                    for (int y = player.getRowPosition(); y < 10; y++) {
                        if (map.getSquare(y, player.getColPosition()).isWumpus()) {
                            System.out.println("Wumpus killed.");
                            map.getSquare(y, player.getColPosition()).setDeadWumpus(true);
                        }
                    }
                }
                break;
            case 'j': //shoot left
                if (player.isArrow()) {
                    player.setArrow(false);
                    for (int x = player.getColPosition(); x > 0; x--) {
                        if (map.getSquare(player.getRowPosition(), x).isWumpus()) {
                            System.out.println("Wumpus killed.");
                            map.getSquare(player.getRowPosition(), x).setDeadWumpus(true);
                        }
                    }
                }
                break;
            case 'l': //shoot right
                if (player.isArrow()) {
                    player.setArrow(false);
                    for (int x = player.getColPosition(); x < 10; x++) {
                        if (map.getSquare(player.getRowPosition(), x).isWumpus()) {
                            System.out.println("Wumpus killed.");
                            map.getSquare(player.getRowPosition(), x).setDeadWumpus(true);
                        }
                    }
                }
                break;
            case 'c': //climb ladder
                if (player.getRowPosition() == map.getLadderRow() && player.getColPosition() == map.getLadderCol() && player.isGold()) {
                    status = WON;
                }
                break;
            case 'p':
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isDeadWumpus()) {
                    map.getSquare(player.getRowPosition(), player.getColPosition()).setDeadWumpus(false);
                    System.out.println("Collected Wumpus corpse");
                }

                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isGold()) {
                    map.getSquare(player.getRowPosition(), player.getColPosition()).setGold(false);
                    player.setGold(true);
                }
                break;
            case 'n': //new game
                if (status == WON || status == DEAD) {
                    reset();
                    repaint();
                }
                break;
            case '*': //toggle cheat mode
                displayFog = !displayFog;
                break;
            case '-':
                System.out.println("Map is: " + map.toString());
                break;
        }

        repaint();
        System.out.println("Player is at: " + player.getColPosition() + "," + player.getRowPosition());
        //check if stepped on wumpus
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isWumpus() || map.getSquare(player.getRowPosition(), player.getColPosition()).isPit()) {
            status = DEAD;
            System.out.println("Player is dead.");
        }


    }

    public void keyPressed(KeyEvent event) {

    }

    public void paint(Graphics g) {

        for (int y = 0; y < WumpusMap.NUM_ROWS; y++) {
            for (int x = 0; x < WumpusMap.NUM_COLUMNS; x++) {


                g.drawImage(floor, x * 50, y * 50, null);


                if (map.getSquare(y, x).isGold())
                    //print gold
                    g.drawImage(gold, x * 50, y * 50, null);
                if (map.getSquare(y, x).isPit())
                    //print pit
                    g.drawImage(pit, x * 50, y * 50, null);

                if (map.getSquare(y, x).isBreeze() && !map.getSquare(y, x).isPit())
                    //print breeze
                    g.drawImage(breeze, x * 50, y * 50, null);

                if (map.getSquare(y, x).isStench() && !map.getSquare(y, x).isPit())
                    //print stench
                    g.drawImage(stench, x * 50, y * 50, null);

                if (map.getSquare(y, x).isWumpus() && !map.getSquare(y, x).isPit())
                    //print wumpus
                    g.drawImage(wumpus, x * 50, y * 50, null);

                if (map.getSquare(y, x).isDeadWumpus() && !map.getSquare(y, x).isPit())
                    //print dead wumpus
                    g.drawImage(deadWumpus, x * 50, y * 50, null);

                if (map.getSquare(y, x).isLadder())
                    //print ladder
                    g.drawImage(ladder, x * 50, y * 50, null);

                /**
                 * while(Noah.isAlive())
                 * {
                 * Noah.stealGirl(Evan,girl);
                 * }
                 */

                if (!map.getSquare(y, x).isVisited() && displayFog) {
                    g.setColor(Color.black);
                    g.fillRect(x * 50, y * 50, 50, 50);
                }

                //fill back of gui
                g.setColor(Color.white);
                g.fillRect(0, 500, 250, 700);
                g.setColor(Color.black);
                g.drawLine(250, 500, 500, 500);
                g.setColor(Color.white);
                g.fillRect(255, 500, 500, 700);

                g.setColor(Color.black);
                g.drawString("Inventory:", 0, 520);

                //inventory
                if (player.isArrow()) {
                    g.drawImage(arrow, 0, 550, null);
                }
                if (player.isGold()) {
                    g.drawImage(gold, 50, 550, null);
                }
                //messages

                g.drawString("Messages:", 300, 520);
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isLadder()) {
                    g.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    g.drawString("You bump into the ladder.", 300, 550);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isBreeze()) {
                    g.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    g.drawString("You feel a breeze.", 300, 570);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isStench()) {
                    g.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    g.drawString("You smell a smelly smell. It smells... ", 280, 590);
                    g.drawString("Smelly.", 280, 600);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isGold()) {
                    g.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    g.drawString("You see a glimmer.", 300, 620);
                    g.drawString("Press p to pick up the glimmer.", 300, 690);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isPit()) {
                    g.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    g.drawString("You fell down a pit.", 300, 640);
                    g.drawString("Press n to reset.", 300, 690);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isWumpus()) {
                    g.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    g.drawString("You don been eated, fool!", 300, 660);
                    g.drawString("Press n to reset.", 300, 690);
                }
                if (status == WON) {
                    g.drawString("You won the game!", 300, 680);
                    g.drawString("Press n to reset.", 300, 690);
                }
            }
        }
        //todo Issue with player display
        // player display
        if (player.getDirection() == WumpusPlayer.NORTH)
        {
            g.drawImage(playerUp, player.getColPosition() * 50, player.getRowPosition() * 50, null); //draw the player
        } else if (player.getDirection() == WumpusPlayer.SOUTH)
        {
            g.drawImage(playerDown, player.getColPosition() * 50, player.getRowPosition() * 50, null); //draw the player
        } else if (player.getDirection() == WumpusPlayer.EAST)
        {
            g.drawImage(playerRight, player.getColPosition() * 50, player.getRowPosition() * 50, null); //draw the player
        } else if (player.getDirection() == WumpusPlayer.WEST)
        {
            g.drawImage(playerLeft, player.getColPosition() * 50, player.getRowPosition() * 50, null); //draw the player
        }

        repaint();
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
