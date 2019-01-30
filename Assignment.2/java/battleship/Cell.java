package battleship;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

public class Cell extends JButton {

    enum ShipType {PATROL_BOAT, SUBMARINE, DESTROYER, BATTLESHIP, AIRCRAFT_CARRIER}
    private enum CellStatus {HIT, MISSED}
    private boolean mIdle, mOccupied;
    private CellStatus mStatus;
    private ShipType mShipType;

//    A constructor having two parameters, one of type Board and the other a boolean. The
//    object of the class Board will be the event listener of this specialized JButton. The
//    boolean is true if this Cell is occupied by a ship, and false otherwise.
    Cell(Board gameBoard, boolean occupied) {
        this.mOccupied = occupied;
        this.mIdle = true;
        this.mStatus = null;
        this.setBackground(Color.WHITE);
        try {
            this.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("idle.png"))));
        } catch (IOException exception) {
            // This is extremely bad, but I know the resource is there so doesn't really matter
        }
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        this.setBorder(emptyBorder);
        this.setBorderPainted(false);
        this.addActionListener(gameBoard);
    }

//    Will be called by the action listener. You must reveal the content of the Cell.
//    A white circle will be displayed if the Cell is empty.
//    A red dot will appear if the Cell has a ship.
    void fire() {
        this.disable();
        this.mIdle = false;
        if (this.mOccupied) {
            this.mStatus = CellStatus.HIT;
            String name = "hit";
            try {
                switch (this.mShipType) {
                    case PATROL_BOAT:
                        name = "patrol";
                        break;
                    case SUBMARINE:
                        name = "submarine";
                        break;
                    case DESTROYER:
                        name = "destroyer";
                        break;
                    case BATTLESHIP:
                        name = "battleship";
                        break;
                    case AIRCRAFT_CARRIER:
                        name = "carrier";
                        break;
                }
                this.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(name + ".png"))));
            } catch (IOException exception) {
                // This is so bad I can't believe I'm doing this
            }
        } else {
            this.mStatus = CellStatus.MISSED;
            try {
                this.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("miss.png"))));
            } catch (IOException exception) {
                // This is so bad I can't believe I'm doing this
            }
        }
    }

    // Returns true if this Cell represents a portion of a ship.
    boolean hasShip() {
        return this.mOccupied;
    }

    // Returns true if no shot has been fired at this Cell since the last reset of the game.
    boolean isIdle() {
        return this.mIdle;
    }


    // Sets the state of the cell so that it has no ship and the content is hidden.
    void reset(){
        this.mOccupied = false;
        this.mIdle = true;
        this.mStatus = null;
        this.setBackground(Color.WHITE);
        try {
            this.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("idle.png"))));
        } catch (IOException exception) {
            // This is extremely bad, but I know the resource is there so doesn't really matter
        }
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        this.setBorder(emptyBorder);
        this.setBorderPainted(false);
    }

    // Changes the state of the Cell according to the parameter value so that the Cell has a ship or not.
    void setHasShip(boolean value) {
        this.mOccupied = value;
    }

    ShipType getShipType() {
        return this.mShipType;
    }

    void setShipType(ShipType type) {
        this.mShipType = type;
    }

    // Returns the String “1” is this Cell is occupied by a portion of a Ship and “0” otherwise.
    public String toString() {
        return this.mOccupied ? "1" : "0";
    }
}
