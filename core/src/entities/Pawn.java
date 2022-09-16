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
            if(validateBlock(potentialPos, ChessBlock.ADJACENT.BOT)) {
                firstMove = false;
                return true;
            }
            //diagonal check for enemy pieces
            if(validateBlock(potentialPos, ChessBlock.ADJACENT.BOT_LEFT) ||
                    validateBlock(potentialPos, ChessBlock.ADJACENT.BOT_RIGHT)) {
                firstMove = false;
                return true;
            }
        }
        if(direction == DIRECTION.TOP) {
            if(validateBlock(potentialPos, ChessBlock.ADJACENT.TOP)) {
                firstMove = false;
                return true;
            }
            //diagonal check of enemy pieces
            if(validateBlock(potentialPos, ChessBlock.ADJACENT.TOP_LEFT) ||
                    validateBlock(potentialPos, ChessBlock.ADJACENT.TOP_RIGHT)) {
                firstMove = false;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the block ahead contains pieces or doesn't exist
     * @param potentialPos
     * @param
     * @return
     */
    private boolean validateBlock(ChessBlock potentialPos, ChessBlock.ADJACENT direction) {
        ChessBlock block = currBlock.getAdjacentBlock(direction);
        if (block == null) {
            return false;
        }
        if (block.getPiece() == null) {
            if (block.equals(potentialPos)) {
                return true;
            }
            if (firstMove) {
                block = block.getAdjacentBlock(direction);
                if (block.getPiece() == null && block.equals(potentialPos)) {
                    return true;
                }
            }
        }
        if((block.getPiece() != null)) {
            if (block.equals(potentialPos)) {
                return true;
            }
        }
        return false;

    }
}
