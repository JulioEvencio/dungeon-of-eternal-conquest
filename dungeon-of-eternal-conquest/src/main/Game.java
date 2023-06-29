package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.dungeon.Dungeon;
import main.entities.Player;
import main.menu.Credits;
import main.menu.GameOver;
import main.menu.Menu;
import main.menu.Pause;
import main.util.Spritesheet;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private boolean isRunning;
	
	private final JFrame frame;

	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	public static final int GAME_MENU = 1;
	public static final int GAME_RUN = 2;
	public static final int GAME_PAUSED = 3;
	public static final int GAME_OVER = 4;
	public static final int GAME_EXIT = 5;
	public static final int GAME_CREDITS = 6;
	
	private int gameState;

	private boolean showFPS;
	private int fps;
	
	private boolean isFullscreen;

	private final BufferedImage renderer;

	private final Spritesheet spritesheet;

	private final Pause pause;
	private final Menu menu;
	private final GameOver gameOver;
	private final Credits credits;
	
	private Player player;
	private Dungeon dungeon;

	public Game() throws IOException {
		this.gameState = GAME_MENU;
		
		this.showFPS = false;
		this.isFullscreen = false;
		
		this.addKeyListener(this);
		
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		this.frame = new JFrame();

		this.frame.setTitle("Dungeon of eternal conquest");
		this.frame.add(this);
		this.frame.setResizable(false);
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);

		renderer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		spritesheet = new Spritesheet("/sprites/dungeon/tiles.png");

		pause = new Pause();
		menu = new Menu();
		gameOver = new GameOver();
		credits = new Credits();
		
		player = new Player();
		dungeon = new Dungeon("/levels/level-01.png", spritesheet, player);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void updateFullscreen() {
		isFullscreen = !isFullscreen;
		
		if (isFullscreen) {
			this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		} else {
			this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		}
		
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	public void restart() {
		try {
			player = new Player();
			dungeon = new Dungeon("/levels/level-01.png", spritesheet, player);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An error has occurred. The program will be terminated.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public void exitGame() {
		System.exit(0);
	}
	
	public void tick() {
		if (gameState == GAME_RUN) {
			dungeon.tick();
			
			if (player.isDead()) {
				gameState = GAME_OVER;
			}
		} else if (gameState == GAME_PAUSED) {
			pause.tick();
			
			gameState = pause.getOption();
			
			if (gameState == GAME_EXIT) {
				this.exitGame();
			}
		} else if (gameState == GAME_MENU) {
			menu.tick();
			
			gameState = menu.getOption();
			
			if (gameState == GAME_RUN) {
				this.restart();
			} else if (gameState == GAME_EXIT) {
				this.exitGame();
			}
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics render = renderer.getGraphics();

		render.setColor(Color.BLACK);
		render.fillRect(0, 0, WIDTH, HEIGHT);

		dungeon.render(render);

		render.dispose();

		Graphics g = bs.getDrawGraphics();
		
		if (isFullscreen) {
			g.drawImage(renderer, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
			
			switch (gameState) {
				case GAME_PAUSED:
					pause.renderFullscreen(g, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
					break;
				case GAME_MENU:
					menu.renderFullscreen(g, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
					break;
				case GAME_OVER:
					gameOver.renderFullscreen(g, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
					break;
				case GAME_CREDITS:
					credits.renderFullscreen(g, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
					break;
			}
		} else {
			g.drawImage(renderer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
			
			switch (gameState) {
				case GAME_PAUSED:
					pause.render(g);
					break;
				case GAME_MENU:
					menu.render(g);
					break;
				case GAME_OVER:
					gameOver.render(g);
					break;
				case GAME_CREDITS:
					credits.render(g);
					break;
			}
		}

		if (showFPS) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.setColor(Color.WHITE);
			g.drawString("FPS: " + fps, frame.getWidth() - 100, 32);
		}

		bs.show();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameState == GAME_RUN) {
			dungeon.keyPressed(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (gameState == GAME_RUN) {
			dungeon.keyReleased(e);
			
			if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gameState = GAME_PAUSED;
			}
		} else if (gameState == GAME_PAUSED) { 
			if (e.getKeyCode() == KeyEvent.VK_W) {
				pause.menuUp();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_S) {
				pause.menuDown();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				pause.menuEnter();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gameState = GAME_RUN;
			}
		} else if (gameState == GAME_MENU) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				menu.menuUp();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_S) {
				menu.menuDown();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				menu.menuEnter();
			}
		} else if (gameState == GAME_CREDITS) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				gameState = GAME_MENU;
			}
		} else if (gameState == GAME_OVER) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				gameState = GAME_MENU;
				this.restart();
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_F2) {
			this.updateFullscreen();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_F3) {
			showFPS = !showFPS;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// Code
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0.0;

		int frames = 0; 
		double timer = System.currentTimeMillis();

		this.requestFocus();

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				this.tick();
				this.render();
				delta--;

				frames++;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				fps = frames;
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}

		this.stop();
	}

	public static void main(String[] args) {
		try {
			new Game().start();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error has occurred. The program will be terminated.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

}
