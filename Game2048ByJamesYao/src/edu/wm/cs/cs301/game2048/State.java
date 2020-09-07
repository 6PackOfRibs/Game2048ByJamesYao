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
		// counts number of positions with value of 0
		int zero_count = 0;
		for (int i = 0; i < boardPositions.length; i++) {
			if (boardPositions[i] == 0) {
				zero_count++;
			}
		}
		if (zero_count == 0) {
			return false;
		}
		
		// create array with size equal to the number of zero positions
		int zero_positions[] = new int[zero_count];
		// stores the positions with value of 0
		int array_pos = 0;
		for (int i = 0; i < boardPositions.length; i++) {
			if (boardPositions[i] == 0) {
				zero_positions[array_pos] = i;
				array_pos++;
			}
		}
		
		// sets random position with value of 0 to a random value of 2 or 4
		int random_pos = (int) ((Math.random() * zero_count));
		double random_val = Math.random();
		if (random_val <= 0.5) {
			boardPositions[zero_positions[random_pos]] = 2;
		}
		else {
		boardPositions[zero_positions[random_pos]] = 4;
		}
		return true;
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
