package pack1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Noah Morton
 *         Created on: 10/21/2014 , Time is :  17:53
 *         Part of Project: WumpusWorldTrogdor
 */

@SuppressWarnings("WeakerAccess")
public class WumpusPanel extends JPanel implements KeyListener {

    public static final int PLAYING = 0, DEAD = 2, WON = 1;
    private static boolean displayFog = true, wumpusKilled = false;
    int status;
    WumpusPlayer player;
    WumpusMap map;
    Image arrow, breeze, deadWumpus, floor, gold, ladder, pit, playerDown, playerLeft, playerRight, playerUp, wumpus, stench;

    BufferedImage buffer = null;

    public WumpusPanel() {

        setSize(500, 700); //set the size of the window
        buffer = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        //load images
        try {
            arrow = ImageIO.read((new File("arrow.gif")));
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
                        wumpusKilled=false;
                        player.setRowPosition(player.getRowPosition() - 1);
                        player.setDirection(WumpusPlayer.NORTH);
                        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                    }
                }
                break;
            case 's':
                if (status == PLAYING) {
                    if (player.getRowPosition() + 1 < 10) {
                        wumpusKilled=false;
                        player.setRowPosition(player.getRowPosition() + 1);
                        player.setDirection(WumpusPlayer.SOUTH);
                        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                    }
                }
                break;
            case 'a':
                if (status == PLAYING) {
                    if (player.getColPosition() - 1 >= 0) {
                        wumpusKilled=false;
                        player.setColPosition(player.getColPosition() - 1);
                        player.setDirection(WumpusPlayer.WEST);
                        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                    }
                }
                break;
            case 'd':
                if (status == PLAYING) {
                    if (player.getColPosition() + 1 < 10) {
                        wumpusKilled=false;
                        player.setColPosition(player.getColPosition() + 1);
                        player.setDirection(WumpusPlayer.EAST);
                        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                    }
                }
                break;
            case 'i': //shoot upward
                if (player.isArrow()) {
                    player.setArrow(false);
                    for (int y = player.getRowPosition(); y >= 0; y--) {
                        if (map.getSquare(y, player.getColPosition()).isWumpus()) {
                            System.out.println("Wumpus killed.");
                            wumpusKilled = true;
                            map.getSquare(y, player.getColPosition()).setDeadWumpus(true);
                        }
                    }
                }
                break;
            case 'k': //shoot downward
                if (player.isArrow()) {
                    player.setArrow(false);
                    for (int y = player.getRowPosition(); y <= 10; y++) {
                        if (map.getSquare(y, player.getColPosition()).isWumpus()) {
                            System.out.println("Wumpus killed.");
                            wumpusKilled = true;
                            map.getSquare(y, player.getColPosition()).setDeadWumpus(true);
                        }
                    }
                }
                break;
            case 'j': //shoot left
                if (player.isArrow()) {
                    player.setArrow(false);
                    for (int x = player.getColPosition(); x >= 0; x--) {
                        if (map.getSquare(player.getRowPosition(), x).isWumpus()) {
                            System.out.println("Wumpus killed.");
                            wumpusKilled = true;
                            map.getSquare(player.getRowPosition(), x).setDeadWumpus(true);
                        }
                    }
                }
                break;
            case 'l': //shoot right
                if (player.isArrow()) {
                    player.setArrow(false);
                    for (int x = player.getColPosition(); x <= 10; x++) {
                        if (map.getSquare(player.getRowPosition(), x).isWumpus()) {
                            System.out.println("Wumpus killed.");
                            wumpusKilled = true;
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
        //System.out.println("Player is at: " + player.getColPosition() + "," + player.getRowPosition());
        //check if stepped on wumpus
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isWumpus() || map.getSquare(player.getRowPosition(), player.getColPosition()).isPit()) {
            status = DEAD;
            System.out.println("Player is dead.");
        }


    }

    public void keyPressed(KeyEvent event) {

    }

    public void paint(Graphics g) {

        Graphics bg = buffer.getGraphics();

        for (int y = 0; y < WumpusMap.NUM_ROWS; y++) {
            for (int x = 0; x < WumpusMap.NUM_COLUMNS; x++) {


                bg.drawImage(floor, x * 50, y * 50, null);


                if (map.getSquare(y, x).isGold())
                    //print gold
                    bg.drawImage(gold, x * 50, y * 50, null);
                if (map.getSquare(y, x).isPit())
                    //print pit
                    bg.drawImage(pit, x * 50, y * 50, null);

                if (map.getSquare(y, x).isBreeze() && !map.getSquare(y, x).isPit())
                    //print breeze
                    bg.drawImage(breeze, x * 50, y * 50, null);

                if (map.getSquare(y, x).isStench() && !map.getSquare(y, x).isPit())
                    //print stench
                    bg.drawImage(stench, x * 50, y * 50, null);

                if (map.getSquare(y, x).isWumpus() && !map.getSquare(y, x).isPit())
                    //print wumpus
                    bg.drawImage(wumpus, x * 50, y * 50, null);

                if (map.getSquare(y, x).isDeadWumpus() && !map.getSquare(y, x).isPit())
                    //print dead wumpus
                    bg.drawImage(deadWumpus, x * 50, y * 50, null);

                if (map.getSquare(y, x).isLadder())
                    //print ladder
                    bg.drawImage(ladder, x * 50, y * 50, null);

                /*
                  while(Noah.isAlive())
                  {
                  Noah.stealGirl(Evan,girl);
                  }
                 */

                if (!map.getSquare(y, x).isVisited() && displayFog) {
                    bg.setColor(Color.black);
                    bg.fillRect(x * 50, y * 50, 50, 50);
                }

                //fill back of gui
                bg.setColor(Color.white);
                bg.fillRect(0, 500, 250, 700);
                bg.setColor(Color.black);
                bg.drawLine(250, 500, 500, 500);
                bg.setColor(Color.white);
                bg.fillRect(255, 500, 500, 700);

                bg.setColor(Color.black);
                bg.drawString("Inventory:", 0, 520);

                //inventory
                if (player.isArrow()) {
                    bg.drawImage(arrow, 0, 550, null);
                }
                if (player.isGold()) {
                    bg.drawImage(gold, 50, 550, null);
                }
                //messages handling

                bg.drawString("Messages:", 300, 520);
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isLadder()) {
                    bg.setColor(Color.black);
                    if (player.isGold()) {
                        status = WON;
                        bg.drawString("You climb the ladder.", 300, 550);
                    } else {
                        bg.drawString("You bump into the ladder.", 300, 550);
                    }
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isBreeze()) {
                    bg.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    bg.drawString("You feel a breeze.", 300, 570);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isStench()) {
                    bg.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    bg.drawString("You smell a smelly smell.", 300, 590);
                    bg.drawString(" It smells... Smelly.", 300, 600);
                    bg.drawString("Press I,J,K,L to shoot.", 300, 620);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isGold()) {
                    bg.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    bg.drawString("You see a glimmer.", 300, 620);
                    bg.drawString("Press p to pick up the glimmer.", 300, 690);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isPit()) {
                    bg.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    bg.drawString("You fell down a pit.", 300, 640);
                    bg.drawString("Press n to reset.", 300, 690);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isWumpus()) {
                    bg.setColor(Color.black);
                    // g.fillRect(350,500,450,660);
                    bg.drawString("You don been eated, fool!", 300, 660);
                    bg.drawString("Press n to reset.", 300, 690);
                }
                if (status == WON) {
                    bg.drawString("You won the game!", 300, 680);
                    bg.drawString("Press n to reset.", 300, 690);
                }
                if (wumpusKilled) { //if the wumpus has been killed, display scream heard
                    bg.drawString("You hear a scream!", 300, 650);
                }
            }
        }

        // player display
        if (player.getDirection() == WumpusPlayer.NORTH)
        {
            bg.drawImage(playerUp, player.getColPosition() * 50, player.getRowPosition() * 50, null); //draw the player
        } else if (player.getDirection() == WumpusPlayer.SOUTH)
        {
            bg.drawImage(playerDown, player.getColPosition() * 50, player.getRowPosition() * 50, null); //draw the player
        } else if (player.getDirection() == WumpusPlayer.EAST)
        {
            bg.drawImage(playerRight, player.getColPosition() * 50, player.getRowPosition() * 50, null); //draw the player
        } else if (player.getDirection() == WumpusPlayer.WEST)
        {
            bg.drawImage(playerLeft, player.getColPosition() * 50, player.getRowPosition() * 50, null); //draw the player
        }

        g.drawImage(buffer,0,0,null);
        //repaint();
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
