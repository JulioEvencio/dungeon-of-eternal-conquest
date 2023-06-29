package main.weapons;

import java.io.IOException;

public class Punch extends Weapons {

	public Punch(double x, double y, int width, int height, double minDamage, double maxDamage, int maxFrames, int maxIndex, String spritePath) throws IOException {
		super(x, y, width, height, minDamage, maxDamage, maxFrames, maxIndex, spritePath);
	}
	
	@Override
	public void setPosition(double x, double y) {
		if (dir == dirRight) {
			this.x = x - 6;
		} else {
			this.x = x + 6;
		}
		
		this.y = y;

		this.updateMask();
	}

	@Override
	protected void updateMask() {
		if (dir == dirRight) {
			this.maskX = x;
			this.maskW = width - 5;
		} else {
			this.maskX = x + 5;
			this.maskW = width - 5;
		}
		
		this.maskY = y;
		this.maskH = height;
	}

}
