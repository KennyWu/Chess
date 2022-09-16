package com.mygdx.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ScreenUtils;
import entities.ChessBlock;
import entities.Piece;


public class Chessboard extends ApplicationAdapter implements InputProcessor {

	public static int HEIGHT_CHESSBOARD;
	public static int WIDTH_CHESSBOARD;
	public static final int CHESS_COLUMNS = 8;
	public static final int CHESS_ROWS = 8;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	ChessBlock[][] chessBlocks;
	ArrayMap<Piece, ChessBlock> piecePosMap;
	boolean hasPiece;
	Piece selectedPiece;
	ChessBlock origBlock;
	String turn;




	@Override
	public void create () {
		turn = "W";
		selectedPiece = null;
		hasPiece = false;
		origBlock = null;
		Gdx.input.setInputProcessor(this);
		HEIGHT_CHESSBOARD = Gdx.graphics.getHeight();
		WIDTH_CHESSBOARD = Gdx.graphics.getWidth();
		chessBlocks = new ChessBlock[CHESS_ROWS][CHESS_COLUMNS];
		batch = new SpriteBatch();
		piecePosMap = new ArrayMap<Piece, ChessBlock>();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		boolean drawwhite = true;
		boolean readSpawn = true;
		FileHandle file = Gdx.files.internal("settings/white_side_spawn.txt");
		String pieces = file.readString();
		String[] piece = pieces.split("\r\n");
		int pieceIndex = 0;
		String pieceDescription = "";
		String piecePos = "";
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				drawwhite = !drawwhite;
				if(drawwhite) {
					chessBlocks[i][j] = new ChessBlock(true,
							i+1, j+1, this);
				}else {
					chessBlocks[i][j] = new ChessBlock(false,
							i+1, j+1, this);
				}
				if(readSpawn) {
					pieceDescription = piece[pieceIndex];
					piecePos = pieceDescription.substring(4);
				}
				//I dont believe the if statement ever evaluates to true - debug
				if(piecePos.equals(chessBlocks[i][j].getPosition())) {
					chessBlocks[i][j].createPiece(pieceDescription);
					piecePosMap.put(chessBlocks[i][j].getPiece(), chessBlocks[i][j]);
					readSpawn = true;
					pieceIndex++;
				}else {
					readSpawn = false;
				}
			}
			drawwhite = !drawwhite;
		}
		linkBlocks();
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public ChessBlock findChessBlock(int x, int y) {
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(chessBlocks[i][j].contains((float)x, (float)y)){
					return chessBlocks[i][j];
				}
			}
		}
		return null;
	}

	public void returnPiece() {

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 0);
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBlocks[i][j].draw();
			}
		}
		for(Piece piece : piecePosMap.keys()) {
			if(piece == selectedPiece || piece.getIsDead()) {
				continue;
			}
			piece.render(this);
		}
		if(selectedPiece != null) {
			selectedPiece.render(this);
		}

	}
	
	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBlocks[i][j].dispose();
			}
		}
	}

	/**
	 * Adds references to all adjacent blocks to the block
	 */
	private void linkBlocks() {
		for(int i = 0; i < chessBlocks.length; i++) {
			for(int j = 0; j < chessBlocks[0].length; j++) {
				ChessBlock block = chessBlocks[i][j];
				//right
				block.fillAdjacent(getBlock(i,j+1), ChessBlock.ADJACENT.RIGHT);
				//left
				block.fillAdjacent(getBlock(i,j-1), ChessBlock.ADJACENT.LEFT);
				//top
				block.fillAdjacent(getBlock(i-1,j), ChessBlock.ADJACENT.TOP);
				//bot
				block.fillAdjacent(getBlock(i+1,j), ChessBlock.ADJACENT.BOT);
				//botleft
				block.fillAdjacent(getBlock(i+1,j-1), ChessBlock.ADJACENT.BOT_LEFT);
				//botright
				block.fillAdjacent(getBlock(i+1,j+1), ChessBlock.ADJACENT.BOT_RIGHT);
				//topleft
				block.fillAdjacent(getBlock(i-1,j-1), ChessBlock.ADJACENT.TOP_LEFT);
				//topright
				block.fillAdjacent(getBlock(i-1,j+1), ChessBlock.ADJACENT.TOP_RIGHT);
			}
		}
	}
	private ChessBlock getBlock(int i, int j) {
		if(i < 0 || i >= chessBlocks.length ||
				j < 0 || j >= chessBlocks[0].length) {
			return null;
		}
		return chessBlocks[i][j];
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(Input.Buttons.LEFT == button) {
		 	origBlock = findChessBlock(screenX, HEIGHT_CHESSBOARD-screenY);
			if(origBlock != null) {
				hasPiece = !(origBlock.isEmpty());
			}
			if(hasPiece) {
				if(origBlock.getPiece().getSide().equals(turn)) {
					selectedPiece = origBlock.getPiece();
				}else {
					hasPiece = false;
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(selectedPiece != null) {
			ChessBlock block = findChessBlock((int) selectedPiece.getPosX(), (int) selectedPiece.getPosY());
			//when piece is let go on same block return to original condition
			if(!block.addPiece(selectedPiece)) {
				if(origBlock != null) {
					origBlock.addPiece(selectedPiece);
				}
			}else {
				if (turn.equals("W")) {
					turn = "B";
				}else {
					turn = "W";
				}
			}
		}
		origBlock = null;
		selectedPiece = null;
		return true;
	}

	/**
	 * Moves chess pieces around
	 * @param screenX - origin at top left
	 * @param screenY
	 * @param pointer the pointer for the event.
	 * @return false
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(hasPiece) {
			selectedPiece.setNewPos(screenX, HEIGHT_CHESSBOARD - screenY);
		}
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
