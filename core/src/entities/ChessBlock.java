package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chess.Chessboard;

public class ChessBlock {

    private String color;
    private Chessboard chessboard;
    private Rectangle chessBox;
    /**
     * X,Y are center positions
     */
    private float x;
    private float y;
    private float height;
    private float width;
    private Piece piece;
    private final String pos;

//    public ChessBlock(boolean isWhite, int row, int column,
//                      Chessboard chessboard, String pieceDescription) {
//        color = isWhite? "white" : "black";
//        this.chessboard = chessboard;
//        this.pos = returnPos(row, column);
//        height = chessboard.HEIGHT_CHESSBOARD/chessboard.CHESS_ROWS;
//        width = chessboard.WIDTH_CHESSBOARD/chessboard.CHESS_COLUMNS;
//        y = 800 - (height*(row-1)) - height/2;
//        x = width * (column - 1) + width/2;
//        createPiece(pieceDescription);
//
//    }
    public ChessBlock(boolean isWhite, int row, int column,
                      Chessboard chessboard) {
        color = isWhite? "white" : "black";
        this.chessboard = chessboard;
        this.pos = createPos(row, column);
        height = chessboard.HEIGHT_CHESSBOARD/chessboard.CHESS_ROWS;
        width = chessboard.WIDTH_CHESSBOARD/chessboard.CHESS_COLUMNS;
        y = chessboard.HEIGHT_CHESSBOARD - (height*(row-1)) - height/2;
        x = width * (column - 1) + width/2;
        chessBox = new Rectangle(x-width/2, y-height/2, width, height);
        piece = null;
    }

    /**
     * Create the piece by the description of the piece
     * format piecetype_side
     * @param pieceDescription
     * @return name
     */
    public void createPiece(String pieceDescription) {
        String name  = "";
        switch (pieceDescription.substring(0,1)) {
            case "b":
                piece = new Bishop(pieceDescription,
                        pos, x, y, (int)(width*Piece.PIECE_BLOCK_RATIO), (int)(height*Piece.PIECE_BLOCK_RATIO));
                break;
            case "k":
                piece = new King(pieceDescription,
                        pos, x, y, (int)(width*Piece.PIECE_BLOCK_RATIO), (int)(height*Piece.PIECE_BLOCK_RATIO));
                break;
            case "n":
                piece = new Knight(pieceDescription,
                        pos, x, y, (int)(width*Piece.PIECE_BLOCK_RATIO), (int)(height*Piece.PIECE_BLOCK_RATIO));
                break;
            case "p":
                piece = new Pawn(pieceDescription,
                        pos, x, y, (int)(width*Piece.PIECE_BLOCK_RATIO), (int)(height*Piece.PIECE_BLOCK_RATIO));
                break;
            case "q":
                piece = new Queen(pieceDescription,
                        pos, x, y, (int)(width*Piece.PIECE_BLOCK_RATIO), (int)(height*Piece.PIECE_BLOCK_RATIO));
                break;
            case "r":
                piece = new Rook(pieceDescription,
                        pos, x, y, (int)(width*Piece.PIECE_BLOCK_RATIO), (int)(height*Piece.PIECE_BLOCK_RATIO));
                break;

        }
    }



    /**
     * Return description of the chess position
     * @return description of chess position plus piece description in piece_position letter format
     */
    public String returnDescription() {
        if(piece != null) {
            return piece.getPieceDescription() + "_" + pos;
        }
        return pos;
    }

    public boolean removePiece() {
        if(piece != null) {
            piece = null;
            return true;
        }
        return false;
    }

    /**
     * will remove the current piece in favor of new piece
     * @return true if added, false if not
     */
    public boolean addPiece(Piece piece) {
        if(piece == this.piece) {
            this.piece.setNewPos(x, y);
            return false;
        }
        if(piece != null) {
            if(this.piece != null) {
                this.piece.dead();
            }
            this.piece = piece;
            this.piece.setNewPos(x,y);
            this.piece.setCurrPos(pos);
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        if (piece != null) {
            return false;
        }
        return true;
    }

    public Piece getPiece() {
        if(piece != null) {
            return piece;
        }
        return null;
    }

    public String getPosition() {
        return pos;
    }

    private String createPos(int row, int column) {
        String pos = "";
        switch (column) {
            case 1:
                pos +=  "A";
                break;
            case 2:
                pos +=  "B";
                break;
            case 3:
                pos +=  "C";
                break;
            case 4:
                pos +=  "D";
                break;
            case 5:
                pos +=  "E";
                break;
            case 6:
                pos +=  "F";
                break;
            case 7:
                pos +=  "G";
                break;
            case 8:
                pos +=  "H";
                break;
        }
        switch (row) {
            case 1:
                pos +=  "8";
                break;
            case 2:
                pos +=  "7";
                break;
            case 3:
                pos +=  "6";
                break;
            case 4:
                pos +=  "5";
                break;
            case 5:
                pos +=  "4";
                break;
            case 6:
                pos +=  "3";
                break;
            case 7:
                pos +=  "2";
                break;
            case 8:
                pos +=  "1";
                break;
        }
        return pos;
    }

    public void draw() {
        chessboard.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        if(color.equals("white")) {
            chessboard.getShapeRenderer().setColor(Color.WHITE);
        }else if(color.equals("black")){
            chessboard.getShapeRenderer().setColor(Color.GRAY);
        }
        chessboard.getShapeRenderer().rect(x-width/2, y-height/2, width, height);
        chessboard.getShapeRenderer().end();
        if(piece != null) {
            if (!piece.getCurrPos().equals(pos)) {
                removePiece();
            }
        }

    }

    public boolean contains(float x, float y) {
        return chessBox.contains(x, y);
    }

    public void dispose() {
        if(piece != null) {
            piece.dispose();
        }
    }


}
