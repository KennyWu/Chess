package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chess.Chessboard;

public abstract class Piece {

    public static final float PIECE_BLOCK_RATIO = 0.85f;
    private Texture piece;
    private String side;
    private Rectangle box;
    private float posX;
    private float posY;
    private String currPos;
    private String pieceDescription;
    private int width;
    private int height;
    private String fullName;
    private boolean isDead;

    /**
     *
     * @param pieceDescription
     * @param currPos
     * @param posX - center positions
     * @param posY - center positions
     * @param width
     * @param height
     */
    public Piece(String pieceDescription, String currPos, float posX, float posY,
                 int width, int height) {
        this.isDead = false;
        this.side = pieceDescription.substring(2,3);
        this.currPos = currPos;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.box = new Rectangle(posX-width/2, posY-height/2, width, height);
        this.pieceDescription = pieceDescription;
        fullName = createPieceName(pieceDescription);
        createTexture();

    }

    public void dead() {
        isDead = true;
    }

    public boolean getIsDead() {
        return isDead;
    }

    /**
     * Create the piece name by the description of the piece
     * format piecetype_side
     * @param pieceDescription
     * @return name
     */
    private String createPieceName(String pieceDescription) {
        String name  = "";
        switch (pieceDescription.substring(0,1)) {
            case "b":
                name += "bishop";
                break;
            case "k":
                name += "king";
                break;
            case "n":
                name += "knight";
                break;
            case "p":
                name += "pawn";
                break;
            case "q":
                name += "queen";
                break;
            case "r":
                name += "rook";
                break;

        }
        if(pieceDescription.substring(2,3).equalsIgnoreCase("W")) {
            name += "_white";
        }else {
            name += "_black";
        }
        return name;
    }

    private void createTexture() {
        Pixmap pixmapOrig = new Pixmap(Gdx.files.internal(fullName + ".png"));
        Pixmap pixmapNew = new Pixmap(width, height, pixmapOrig.getFormat());
        pixmapNew.drawPixmap(pixmapOrig, 0, 0, pixmapOrig.getWidth(), pixmapOrig.getHeight(),
                0, 0, pixmapNew.getWidth(), pixmapNew.getHeight());
        piece = new Texture(pixmapNew);
        pixmapOrig.dispose();
        pixmapNew.dispose();
    }


    public abstract boolean validateMove(String potentialPos);

    public String getPieceDescription() {
        return pieceDescription;
    }
    //render textures, update the rectangle with the texture pos(not sure yet will see)
    public void render(Chessboard chessboard) {
        chessboard.getSpriteBatch().begin();
        chessboard.getSpriteBatch().draw(piece, posX-width/2, posY-height/2);
        chessboard.getSpriteBatch().end();
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public String getCurrPos() {
        return currPos;
    }

    //use static constants from chessboard
    public void checkBounds() {

    }

    public Rectangle getBox() {
        return box;
    }

    /**
     * Takes in left corner positions which is transformed to the middle and set to the piece's x and y
     * @param posX left corner position
     * @param posY left corner position
     */
    public void setNewPos(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        box.x = posX;
        box.y = posY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height= height;
    }

    public void setCurrPos(String pos) {
        this.currPos = pos;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSide() {
        return side;
    }

    public void dispose() {
        piece.dispose();
    }

}
