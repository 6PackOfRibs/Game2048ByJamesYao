package edu.wm.cs.cs301.game2048;

import java.util.Arrays;

public class State implements GameState {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(boardPositions);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (!Arrays.equals(boardPositions, other.boardPositions))
			return false;
		return true;
	}

	int boardPositions[] = new int [16];
	public State(State currentState) {
		// new State inherits currentState's board positions
		int new_arr[] = new int [16];
		for (int i = 0; i < currentState.boardPositions.length; i++) {
			new_arr[i] = currentState.boardPositions[i];
		}
		boardPositions = new_arr;
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
		
		// calculates the random position
		int random_pos = (int) ((Math.random() * zero_count));
		// random double will be converted to random 2 or 4
		double random_val = Math.random();
		
		// sets random position with value of 0 to a random value of 2 or 4
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
		// if there is no empty spot, return true
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
						// if right value is zero, switch values
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
		// track sum to return at end
		int sum = 0;
		// moves every value down, and merges values if they can be merged
		for (int x = 0; x < 4; x++) {
			for (int y = 2; y >= 0; y--) {
				// when value is found, check values below
				if (getValue(x, y) != 0) {
					//saves the current value
					int current_val = getValue(x, y);
					for (int i = y+1; i < 4; i++) {
						// if lower value is zero, switch values
						if (getValue(x, i) == 0) {
							setValue(x, i, current_val);
							setValue(x, i-1, 0);
						}
						// if values can merge: merge and add value to sum
						else if (getValue(x, i) == current_val) {
							setValue(x, i, 2*current_val);
							setValue(x, i-1, 0);
							sum += 2*current_val;
						}
					}
				}
			}
		}
		return sum;
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
	
	// helper methods for analyzing board
	public int decision() {
		// track weighted scores
		int left_score = 0;
		int right_score = 0;
		int up_score = 0;
		int down_score = 0;
		// see scores after a potential move
		State temp = new State(this);
		temp.left();
		if (!temp.equals(this)) {
			left_score = weight_score(temp.boardPositions);
		}
		
		temp = new State(this);
		temp.right();
		if (!temp.equals(this)) {
			right_score = weight_score(temp.boardPositions);
		}
		
		temp = new State(this);
		temp.up();
		if (!temp.equals(this)) {
			up_score = weight_score(temp.boardPositions);
		}
		
		temp = new State(this);
		temp.down();
		if (!temp.equals(this)) {
			down_score = weight_score(temp.boardPositions);
		}
		// return move (as int) with max score
		int max_score = Math.max(Math.max(left_score, right_score), Math.max(up_score, down_score));
		if (max_score == left_score) {
			return 0;
		}
		if (max_score == right_score) {
			return 1;
		}
		if (max_score == up_score) {
			return 2;
		}
		if (max_score == down_score) {
			return 3;
		}
		return 1;
	}
	
	// private method to weight board
	private int weight_score(int[] curboard) {
		int weights[] = new int[] {
				300, 100, 70, 50, 5, 6, 10, 20, 4, 3, 3, 3, 1, 1, 1, 2
		};
		int score = 0;
		int base = 1;
		for (int i = 0; i < curboard.length; i++) {
			if (curboard[i] == 0) {
				base = 0;
			}
			else {
				base = (int) (Math.log(curboard[i]) / Math.log(2));
			}
			score += weights[i] * base;
		}
		return score;
	}

}
