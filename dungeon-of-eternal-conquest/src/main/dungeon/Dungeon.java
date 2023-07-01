package main.dungeon;

import java.awt.Color;
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
import main.util.Particle;
import main.util.Spritesheet;

public class Dungeon {

	public final int WIDTH;
	public final int HEIGHT;
	
	public final int UP;
	public final int DOWN;
	public final int RIGHT;
	public final int LEFT;
	
	private final int level;

	public final Player player;
	
	private final Wall wallSpecial;
	private final Stairway stairway;
	
	private final List<Wall> walls;
	private final List<Floor> floors;
	
	private final List<Slime> slimes;
	
	private final List<Particle> particles;

	public Dungeon(int level, Spritesheet spritesheet, Player player) throws IOException {
		this.UP = 1;
		this.DOWN = 2;
		this.RIGHT = 3;
		this.LEFT = 4;
		
		this.level = level;
		
		this.player = player;
		
		this.wallSpecial = new Wall(0, 0, 16, 16, spritesheet);
		this.stairway = new Stairway(0, 0, 16, 16, spritesheet);

		this.walls = new ArrayList<>();
		this.floors = new ArrayList<>();
		
		this.slimes = new ArrayList<>();
		
		this.particles = new ArrayList<>();

		String path = String.format("/levels/level-%02d.png", this.level);
		
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
					case 0xFFFFFF00:
						this.stairway.setPosition(x * 16, y * 16);
						break;
					case 0xFF00FFFF:
						wallSpecial.setPosition(x * 16, y * 16);
						this.floors.add(new Floor(x * 16, y * 16, 16, 16, spritesheet));
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
		List<Slime> slimesRemoved = new ArrayList<>();
		
		for (Slime slime : slimes) {
			slime.tick(this);
			
			if (slime.isDead()) {
				slimesRemoved.add(slime);
				this.generateParticles(100, (int) slime.getX(), (int) slime.getY(), new Color(51, 112, 59));
			}
		}
		
		slimes.removeAll(slimesRemoved);
		
		List<Particle> particlesRemoved = new ArrayList<>();
		
		for (Particle particle : particles) {
			particle.tick();
			
			if (particle.isDead()) {
				particlesRemoved.add(particle);
			}
		}
		
		particles.removeAll(particlesRemoved);
		
		if (slimes.isEmpty()) {
			wallSpecial.setPosition(0, 0);
		}
		
		player.tick(this);
	}

	public void render(Graphics graphics) {
		for (Floor floor : floors) {
			floor.render(graphics);
		}
		
		for (Wall wall: walls) {
			wall.render(graphics);
		}
		
		wallSpecial.render(graphics);
		
		stairway.render(graphics);
		
		for (Slime slime : slimes) {
			slime.render(graphics);
		}
		
		for (Particle particle : particles) {
			particle.render(graphics);
		}

		player.render(graphics);
	}
	
	public void generateParticles(int amount, int x, int y, Color color) {
		for (int i = 0; i < amount; i++) {
			particles.add(new Particle(x, y, 3, 3, color));
		}
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean nextLevel() {
		return player.getRectangle().intersects(stairway.getRectangle());
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
		
		if (rectangle.intersects(wallSpecial.getRectangle())) {
			return false;
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
