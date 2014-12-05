package pack1;

import javax.swing.*;
import java.awt.*;


/**
 * @author Noah Morton
 *         Created on: 10/21/2014 , Time is :  17:53
 *         Part of Project: WumpusWorldTrogdor
 */
public class WumpusFrame extends JFrame {

    public WumpusFrame() {
        super("WumpusWorld");

        WumpusPanel wp = new WumpusPanel();

        pack();
        Insets i = getInsets();
        int frameWidth = wp.getWidth() + i.left + i.right;
        int frameHeight = wp.getHeight() + i.top + i.bottom;
        add(wp);

        Panel p = new Panel();
        add(p);

        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(true);
        pack();
        setVisible(true);


    }
}
