package application;

public class Ant {
	// public final static String RULESET =
	// "LRRRRRLLRLRRRRRLLRLRRRRRLLRLRRRRRLLR"; // krasses
	// ding
	// public final static String RULESET =
	// "LRRLLLLRLRLRLRLLLLLLRRRRRRRRRRRRRR";
	// public final static String RULESET =
	// "LRRRRRLLRLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLR"; //
	// ILLAMINATI
	// public final static String RULESET = "RL";
	// public final static String RULESET = "LRRRRRLLR";
	public final static String RULESET = "LLRLLLRRRRRLLLLR"; // ARNE
	// public final static String RULESET =
	// "RLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLLRLRLRLRLRLRLRLRLLLLLLLLLL";
	// public final static String RULESET =
	// "LRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLR";
	private final static char[] rules = RULESET.toCharArray();
	private Direction dir = Direction.left;
	private short[][] grid;

	private int x;
	private int y;

	public Ant(short[][] grid) {
		this.grid = grid;

		x = grid.length / 2;
		y = grid[0].length / 2;
	}

	public void update() {
		if (rules[(grid[x][y])] == 'R') {
			dir = dir.turnRight();
		} else if (rules[(grid[x][y])] == 'L') {
			dir = dir.turnLeft();
		} else {
			throw new IllegalStateException("Nur R oder L erlaubt");
		}

		grid[x][y]++;
		if (grid[x][y] >= rules.length) {
			grid[x][y] = 0;
		}
		move();
	}

	private void move() {
		switch (dir) {
		case left:
			x--;
			break;
		case down:
			y++;
			break;
		case right:
			x++;
			break;
		case up:
			y--;
			break;
		}

		if (x < 0) {
			x = grid.length - 1;
		} else if (x >= grid.length) {
			x = 0;
		}
		if (y >= grid[0].length) {
			y = 0;
		} else if (y < 0) {
			y = grid[0].length - 1;
		}

	}
}
