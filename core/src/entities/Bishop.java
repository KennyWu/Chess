package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bishop extends Piece{

    public Bishop(String pieceDescription, String currPos, float posX,
                  float posY, int width, int height) {
        super(pieceDescription, currPos, posX, posY, width, height);
    }

    @Override
    public boolean validateMove(String potentialPos) {
        return false;
    }

}
