package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Battleship extends JFrame implements ActionListener {

    // Change this value to change the maximum allowed attempts
    public static final int MAX_ATTEMPTS = 40;

    private Board mGameBoard;
    private JPanel mControlPanel;
    private JButton mShowShipsButton, mStartNewGameButton, mShowStatisticsButton, mShowGridArrayButton;

    public Battleship(String title, Board gameBoard) throws HeadlessException {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        this.mGameBoard = gameBoard;
        this.add(gameBoard, BorderLayout.CENTER);

        mStartNewGameButton = new JButton("Start New Game");
        mStartNewGameButton.setFocusPainted(false);
        mStartNewGameButton.addActionListener(this);

        mShowStatisticsButton = new JButton("Statistics");
        mShowStatisticsButton.setFocusPainted(false);
        mShowStatisticsButton.addActionListener(this);

        mShowShipsButton = new JButton("Reveal");
        mShowShipsButton.setFocusPainted(false);
        mShowShipsButton.addActionListener(this);

        mShowGridArrayButton = new JButton("Show Grid Values");
        mShowGridArrayButton.setFocusPainted(false);
        mShowGridArrayButton.addActionListener(this);

        mControlPanel = new JPanel();
        mControlPanel.setBackground(Color.WHITE);
        mControlPanel.add(mShowGridArrayButton);
        mControlPanel.add(mShowStatisticsButton);
        mControlPanel.add(mShowShipsButton);
        mControlPanel.add(mStartNewGameButton);
        this.add(mControlPanel, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(mShowShipsButton)) {
            mGameBoard.showGrid();
        } else if (e.getSource().equals(mStartNewGameButton)) {
            mGameBoard.reset();
            mGameBoard.placeAllShips();
        } else if (e.getSource().equals(mShowStatisticsButton)) {
            mGameBoard.printStatistics();
            JOptionPane.showMessageDialog(this,
                    mGameBoard.getStatistics(), "Game Statistics", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource().equals(mShowGridArrayButton)) {
            mGameBoard.printBoard();
            JOptionPane.showMessageDialog(this,
                    mGameBoard.getBoardArray(), "Board Values", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Board framePanel = new Board();
        Battleship applicationFrame = new Battleship("Battleship", framePanel);
        applicationFrame.show();
    }
}
