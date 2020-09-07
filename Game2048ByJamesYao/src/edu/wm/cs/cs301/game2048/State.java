package edu.wm.cs.cs301.game2048;

public class State implements GameState {

	int boardPositions[] = new int [16];
	public State(State currentState) {
		// new State inherits currentState's board positions
		boardPositions = currentState.boardPositions;
	}
	
	public State() {
		// no code necessary, boardPositions is instantiated outside of constructor
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
		// check every row and column to see if there are identical values
		// check every row
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				// check if value x is equal to any values before it
				for (int i = 0; i < x; i++) {
					if (getValue(x, y) != 0 && getValue(i, y) == getValue(x, y)) {
						// if adjacent, it can be merged
						if (i == x-1) {
							return true;
						}
						// if not adjacent, check if there are interfering values
						if (i == x-2) {
							if (getValue(i+1, y) == 0 || getValue(i+1, y) == getValue(x, y)) {
								return true;
							}
						}
						if (i == x-3) {
							if (getValue(i+1, y) == 0 || getValue(i+1, y) == getValue(x, y)) {
								if (getValue(i+2, y) == 0 || getValue(i+2, y) == getValue(x, y)) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		
		//check every column
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				// check if value y is equal to any values before it
				for (int i = 0; i < y; i++) {
					if (getValue(x, y) != 0 && getValue(x, i) == getValue(x, y)) {
						// if adjacent, it can be merged
						if (i == y-1) {
							return true;
						}
						// if not adjacent, check if there are interfering values
						if (i == y-2) {
							if (getValue(x, i+1) == 0 || getValue(x, i+1) == getValue(x, y)) {
								return true;
							}
						}
						if (i == y-3) {
							if (getValue(x, i+1) == 0 || getValue(x, i+1) == getValue(x, y)) {
								if (getValue(x, i+2) == 0 || getValue(x, i+2) == getValue(x, y)) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean reachedThreshold() {
		// iterates through positions to see if game is won
		for (int i = 0; i < boardPositions.length; i++) {
			if (boardPositions[i] >= 2048) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int left() {
		//track sum to return at end
		int sum = 0;
		// moves every value to the left, and merges values if they can be merged
		for (int y = 0; y < 4; y++) {
			for (int x = 1; x < 4; x++) {
				// when value is found, check values to its left
				if (getValue(x, y) != 0) {
					//saves the current value
					int current_val = getValue(x, y);
					for (int i = x-1; i >= 0; i--) {
						// if left value is zero, switch values
						if (getValue(i, y) == 0) {
							setValue(i, y, current_val);
							setValue(i+1, y, 0);
						}
						// if values can merge: merge and add value to sum
						else if (getValue(i, y) == current_val) {
							setValue(i, y, 2*current_val);
							setValue(i+1, y, 0);
							sum += 2*current_val;
						}
					}
				}
			}
		}
		return sum;
	}

	@Override
	public int right() {
		// track sum to return at end
		int sum = 0;
		// moves every value to the right, and merges values if they can be merged
		for (int y = 0; y < 4; y++) {
			for (int x = 2; x >= 0; x--) {
				// when value is found, check values to its right
				if (getValue(x, y) != 0) {
					//saves the current value
					int current_val = getValue(x, y);
					for (int i = x+1; i < 4; i++) {
						// if left value is zero, switch values
						if (getValue(i, y) == 0) {
							setValue(i, y, current_val);
							setValue(i-1, y, 0);
						}
						// if values can merge: merge and add value to sum
						else if (getValue(i, y) == current_val) {
							setValue(i, y, 2*current_val);
							setValue(i-1, y, 0);
							sum += 2*current_val;
						}
					}
				}
			}
		}
		return sum;
	}

	@Override
	public int down() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int up() {
		// track sum to return at end
		int sum = 0;
		// moves every value up, and merges values if they can be merged
		for (int x = 0; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				// when value is found, check values above
				if (getValue(x, y) != 0) {
					//saves the current value
					int current_val = getValue(x, y);
					for (int i = y-1; i >= 0; i--) {
						// if upper value is zero, switch values
						if (getValue(x, i) == 0) {
							setValue(x, i, current_val);
							setValue(x, i+1, 0);
						}
						// if values can merge: merge and add value to sum
						else if (getValue(x, i) == current_val) {
							setValue(x, i, 2*current_val);
							setValue(x, i+1, 0);
							sum += 2*current_val;
						}
					}
				}
			}
		}
		return sum;
	}

}
