package application;

public enum Direction {
	up, right, left, down;

	public Direction turnLeft() {
		switch (this) {
		case up:
			return left;
		case left:
			return down;
		case down:
			return right;
		case right:
			return up;
		default:
			throw new IllegalStateException("The Direction was not supported");
		}
	}

	public Direction turnRight() {
		switch (this) {
		case up:
			return right;
		case right:
			return down;
		case down:
			return left;
		case left:
			return up;
		default:
			throw new IllegalStateException("The Direction was not supported");
		}
	}
}
