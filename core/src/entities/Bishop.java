package entities;

public class Bishop extends Piece{


    public Bishop(String pieceDescription, String currPos, float posX,
                  float posY, int width, int height) {
        super(pieceDescription, currPos, posX, posY, width, height);
    }

    @Override
    public boolean validateMove(ChessBlock potentialPos) {
        return super.validateMove(potentialPos);
    }

}
