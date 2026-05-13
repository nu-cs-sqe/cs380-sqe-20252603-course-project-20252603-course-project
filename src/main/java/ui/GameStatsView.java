package ui;

import javax.swing.*;

public class GameStatsView extends JPanel {
    public GameStatsView(String player1Name, String player2Name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel a = new JLabel();
        JLabel b = new JLabel();
        JLabel c = new JLabel();
        JLabel d = new JLabel();

        add(a);
        add(b);
        add(c);
        add(d);
    }
}
