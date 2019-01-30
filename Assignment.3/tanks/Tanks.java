package tanks;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tanks extends JFrame {

    public static final int MAX_MISSILE = 10;
    public static final int MIN_MISSILE = 0;
    public static final int INIT_MISSILE = 3;
    public static final int MAX_ACCURACY = 100;
    public static final int MIN_ACCURACY = 0;
    public static final int INIT_ACCURACY = 40;
    public static final int MAX_MINES = 10;
    public static final int MIN_MINES = 0;
    public static final int INIT_MINES = 4;

    private JPanel mControlPanel;
    private JButton mStartNewGameButton;
    private JSlider mMissileFrequencySlider, mMissileAccuracySlider, mMineNumberSlider;
    private Board mGameBoard;

    public Tanks(String title) throws HeadlessException {
        super(title);

        mControlPanel = new JPanel();
        mControlPanel.setLayout(new GridBagLayout());
        mControlPanel.setBackground(Color.WHITE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 0.5;
        constraints.weightx = 0.5;
        constraints.insets = new Insets(0, 5, 5, 0);

        JLabel missileFrequencySliderLabel = new JLabel("Missile Frequency", JLabel.CENTER);
        missileFrequencySliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        constraints.gridx = 0;
        constraints.gridy = 0;
        mControlPanel.add(missileFrequencySliderLabel, constraints);

        JLabel missileAccuracySliderLabel= new JLabel("Missile Accuracy", JLabel.CENTER);
        missileAccuracySliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        constraints.gridx = 1;
        constraints.gridy = 0;
        mControlPanel.add(missileAccuracySliderLabel, constraints);

        JLabel mineNumberSliderLabel = new JLabel("Mine Number", JLabel.CENTER);
        mineNumberSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        constraints.gridx = 2;       //aligned with button 2
        constraints.gridy = 0;       //third row
        mControlPanel.add(mineNumberSliderLabel, constraints);

        mMissileFrequencySlider = new JSlider(JSlider.HORIZONTAL, MIN_MISSILE, MAX_MISSILE, INIT_MISSILE);
        mMissileFrequencySlider.setMajorTickSpacing(1);
        mMissileFrequencySlider.setPaintTicks(true);
        mMissileFrequencySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    mGameBoard.getmEnemyHouse().setFrequency(source.getValue());
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        mControlPanel.add(mMissileFrequencySlider, constraints);

        mMissileAccuracySlider = new JSlider(JSlider.HORIZONTAL, MIN_ACCURACY, MAX_ACCURACY, INIT_ACCURACY);
        mMissileAccuracySlider.setMajorTickSpacing(10);
        mMissileAccuracySlider.setPaintTicks(true);
        mMissileAccuracySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    mGameBoard.getmEnemyHouse().setAccuracy(source.getValue());
                }
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 1;
        mControlPanel.add(mMissileAccuracySlider, constraints);

        mMineNumberSlider = new JSlider(JSlider.HORIZONTAL, MIN_MINES, MAX_MINES, INIT_MINES);
        mMineNumberSlider.setMajorTickSpacing(1);
        mMineNumberSlider.setPaintTicks(true);
        mMineNumberSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    mGameBoard.updateMines(source.getValue());
                }
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 1;
        mControlPanel.add(mMineNumberSlider, constraints);

        mStartNewGameButton = new JButton("Start New Game");
        mStartNewGameButton.setFocusPainted(false);
        mStartNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(0, 5, 5, 5);
        mControlPanel.add(mStartNewGameButton, constraints);

        this.add(mControlPanel, BorderLayout.SOUTH);
        this.mGameBoard = new Board(mMineNumberSlider.getValue());
        this.add(this.mGameBoard, BorderLayout.CENTER);
    }

    public void reset() {
        this.mGameBoard.reset(mMineNumberSlider.getValue());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Tanks applicationFrame = new Tanks("Tanks");
            applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            applicationFrame.setBackground(Color.WHITE);
            applicationFrame.setResizable(true);
            applicationFrame.setLocationRelativeTo(null);
            applicationFrame.pack();
            applicationFrame.setVisible(true);
        });
    }
}
