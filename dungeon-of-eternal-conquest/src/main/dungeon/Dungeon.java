package main.dungeon;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.dungeon.tile.Floor;
import main.dungeon.tile.Stairway;
import main.dungeon.tile.Wall;
import main.entities.Entity;
import main.entities.Player;
import main.entities.Slime;
import main.util.Spritesheet;

public class Dungeon {

	public final int WIDTH;
	public final int HEIGHT;
	
	public final int UP;
	public final int DOWN;
	public final int RIGHT;
	public final int LEFT;

	public Player player;
	
	private final List<Wall> walls;
	private final List<Floor> floors;
	private final List<Stairway> stairways;
	
	private final List<Slime> slimes;

	public Dungeon(String path, Spritesheet spritesheet, Player player) throws IOException {
		this.UP = 1;
		this.DOWN = 2;
		this.RIGHT = 3;
		this.LEFT = 4;
		
		this.player = player;

		this.walls = new ArrayList<>();
		this.floors = new ArrayList<>();
		this.stairways = new ArrayList<>();
		
		this.slimes = new ArrayList<>();

		BufferedImage map = ImageIO.read(this.getClass().getResource(path));

		int[] pixels = new int[map.getWidth() * map.getHeight()];

		this.WIDTH = map.getWidth();
		this.HEIGHT = map.getHeight();

		map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int currentPixel = pixels[x + (y * map.getWidth())];

				switch (currentPixel) {
					case 0xFFFFFFFF:
						this.floors.add(new Floor(x * 16, y * 16, 16, 16, spritesheet));
						break;
					case 0xFF000000:
						this.walls.add(new Wall(x * 16, y * 16, 16, 16, spritesheet));
						break;
					case 0xFF00FF00:
						Slime slime = new Slime();
						slime.setPosition(x * 16, y * 16);
						this.slimes.add(slime);
						this.floors.add(new Floor(x * 16, y * 16, 16, 16, spritesheet));
						break;
					case 0xFFFF0000:
						this.stairways.add(new Stairway(x * 16, y * 16, 16, 16, spritesheet));
						break;
					case 0xFF0000FF:
						this.player.setPosition(x * 16, y * 16);
						this.floors.add(new Floor(x * 16, y * 16, 16, 16, spritesheet));
						break;
				}
			}
		}
	}

	public void tick() {
		List<Slime> slimeRemoved = new ArrayList<>();
		
		for (Slime slime : slimes) {
			slime.tick(this);
			
			if (slime.isDead()) {
				slimeRemoved.add(slime);
			}
		}
		
		slimes.removeAll(slimeRemoved);
		
		player.tick(this);
	}

	public void render(Graphics graphics) {
		for (Floor floor : floors) {
			floor.render(graphics);
		}
		
		for (Wall wall: walls) {
			wall.render(graphics);
		}
		
		for (Stairway stairway : stairways) {
			stairway.render(graphics);
		}
		
		for (Slime slime : slimes) {
			slime.render(graphics);
		}

		player.render(graphics);
	}
	
	public boolean isFree(Entity entity, int dir) {
		Rectangle rectangle = entity.getRectangle();
		
		if (dir == UP) {
			rectangle.y -= entity.getSpeed();
		} else if (dir == DOWN) {
			rectangle.y += entity.getSpeed();
		} else if (dir == RIGHT) {
			rectangle.x += entity.getSpeed();
		} else if (dir == LEFT) {
			rectangle.x -= entity.getSpeed();
		}
		
		for (Wall wall: walls) {
			if (rectangle.intersects(wall.getRectangle())) {
				return false;
			}
		}
		
		return true;
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player.moveUp();
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			player.moveDown();
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.moveRight();
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			player.moveLeft();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player.stopUp();
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			player.stopDown();
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.stopRight();
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			player.stopLeft();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (!player.isAttacking()) {
				player.initAttack();
			}
		}
	}
	
}
