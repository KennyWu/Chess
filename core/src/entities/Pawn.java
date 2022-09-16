package entities;


public class Pawn extends Piece {

    public enum DIRECTION {
        TOP, BOT
    }
    private DIRECTION direction;
    private boolean firstMove;

    public Pawn(String pieceDescription, String currPos, float posX, float posY,
                  int width, int height) {
        super(pieceDescription, currPos, posX, posY, width, height);
        firstMove = true;
        if(getSide().equals("W")) {
            direction = DIRECTION.TOP;
        }else {
            direction = DIRECTION.BOT;
        }
    }

    @Override
    public boolean validateMove(ChessBlock potentialPos) {
        ChessBlock block = null;
        if(!super.validateMove(potentialPos)) {
            return false;
        }
        if(direction == DIRECTION.BOT) {
            block = currBlock.getAdjacentBlock(ChessBlock.ADJACENT.BOT);
            if(validateBlock(potentialPos, block)) {
                return true;
            }
            block = block.getAdjacentBlock(ChessBlock.ADJACENT.BOT);
            if (firstMove && validateBlock(potentialPos, block)) {
                return true;
            }
            firstMove = false;
        }
        if(direction == DIRECTION.TOP) {
            block = currBlock.getAdjacentBlock(ChessBlock.ADJACENT.TOP);
            if(validateBlock(potentialPos, block)) {
                firstMove = false;
                return true;
            }
            block = block.getAdjacentBlock(ChessBlock.ADJACENT.TOP);
            if (firstMove && validateBlock(potentialPos, block)) {
                firstMove = false;
                return true;
            }
        }
        return false;
    }

    private boolean validateBlock( ChessBlock potentialPos, ChessBlock block) {
        if(block != null ) {
            if (block.getPiece() == null) {
                if (block.equals(potentialPos)) {
                    return true;
                }
            }
        }
        return false;

    }





}
