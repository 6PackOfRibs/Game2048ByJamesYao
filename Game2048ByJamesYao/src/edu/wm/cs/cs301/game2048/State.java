package edu.wm.cs.cs301.game2048;

public class State implements GameState {

	int boardPositions[] = new int [16];
	public State(State currentState) {
		// new State inherits currentState's board positions
		boardPositions = currentState.boardPositions;
	}
	
	public State() {
		// contains the values at the positions
	}

	@Override
	public int getValue(int x, int y) {
		// returns value at (x,y) position
		return boardPositions[x + (4 * y)];
	}

	@Override
	public void setValue(int x, int y, int value) {
		// sets value at (x,y) position
		boardPositions[x + (4 * y)] = value;
		
	}

	@Override
	public void setEmptyBoard() {
		// sets value at all positions to 0
		for (int i = 0; i < boardPositions.length; i++) {
			boardPositions[i] = 0;
		}
	}

	@Override
	public boolean addTile() {
		// TODO
		return false;
	}

	@Override
	public boolean isFull() {
		// checks if there is an empty spot on board
		for (int i = 0; i < boardPositions.length; i++) {
			if (boardPositions[i] == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean canMerge() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reachedThreshold() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int left() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int right() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int down() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int up() {
		// TODO Auto-generated method stub
		return 0;
	}

}
