package game.util;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Particle {

	private int x;
	private int y;
	private final int width;
	private final int height;

	private int currentLife;
	private final int maxLife;

	private final int speed = 2;
	private double dx = 0;
	private double dy = 0;

	private final Color color;

	public Particle(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.currentLife = 0;
		this.maxLife = 15;

		this.dx = new Random().nextGaussian();
		this.dy = new Random().nextGaussian();

		this.color = color;
	}

	public boolean isDead() {
		return currentLife == maxLife;
	}

	public void tick() {
		x += (int) (dx * speed);
		y += (int) (dy * speed);

		currentLife++;
	}

	public void render(Graphics graphics) {
		graphics.setColor(color);
		graphics.fillRect(x - Camera.x, y - Camera.y, width, height);
	}

}
