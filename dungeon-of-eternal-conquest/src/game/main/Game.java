package game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.entities.Player;
import game.resources.Sound;
import game.resources.Spritesheet;
import game.scenarios.Dungeon;
import game.screens.Credits;
import game.screens.FinalScreen;
import game.screens.GameOver;
import game.screens.Menu;
import game.screens.Pause;
import game.screens.Tutorial;

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
	public static final int GAME_TUTORIAL = 7;
	public static final int GAME_FINAL_SCREEN = 8;
	
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
	private final Tutorial tutorial;
	private final FinalScreen finalScreen;
	
	private final int maxLevel;
	
	private Player player;
	private Dungeon dungeon;
	
	private boolean enableSound;
	
	private Sound musicNow;
	private Sound soundMenu;
	private Sound soundGame;

	public Game() throws IOException {
		this.enableSound = true;
		
		soundMenu = new Sound("/sounds/menu.wav");
		soundMenu.start();
		
		soundGame = new Sound("/sounds/game.wav");
		soundGame.start();
		
		musicNow = soundMenu;
		this.updateGameState(GAME_MENU);
		
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
		
		Image imageIcon = ImageIO.read(getClass().getResource("/sprites/icon.png"));
		this.frame.setIconImage(imageIcon);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image imageCursor = ImageIO.read(getClass().getResource("/sprites/cursor.png"));
		Cursor cursor = toolkit.createCustomCursor(imageCursor, new Point(0, 0), "cursor");
		frame.setCursor(cursor);

		this.renderer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		this.spritesheet = new Spritesheet("/sprites/dungeon/tiles.png");

		this.pause = new Pause();
		this.menu = new Menu();
		this.gameOver = new GameOver();
		this.credits = new Credits();
		this.tutorial = new Tutorial();
		this.finalScreen = new FinalScreen();
		
		this.maxLevel = 11;
		
		this.restart();
		musicNow = soundMenu;
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
			
			this.setMusicNow(soundGame);
			this.setLevel(1);
		} catch (Exception e) {
			this.exitWithError();
		}
	}
	
	public void setLevel(int level) {
		try {
			dungeon = new Dungeon(level, spritesheet, player);
		} catch (Exception e) {
			this.exitWithError();
		}
	}

	public void exitGame() {
		System.exit(0);
	}
	
	public void exitWithError() {
		JOptionPane.showMessageDialog(frame, "An error has occurred. The program will be terminated.", "Error", JOptionPane.ERROR_MESSAGE);
		this.exitGame();
	}
	
	public void updateGameState(int gameState) {
		this.gameState = gameState;
		
		if (gameState == GAME_RUN && musicNow != soundGame) {
			this.setMusicNow(soundGame);
		} else if (musicNow != soundMenu) {
			this.setMusicNow(soundMenu);
		}
	}
	
	public void setMusicNow(Sound sound) {
		soundMenu.stop();
		soundGame.stop();
		
		musicNow.stop();
		musicNow = sound;
		musicNow.play();
	}
	
	public void tick() {
		if (enableSound) {
			musicNow.play();
		} else {
			musicNow.stop();
		}
		
		if (gameState == GAME_RUN) {
			dungeon.tick();
			
			if (player.isDead()) {
				this.updateGameState(GAME_OVER);
			} else if (dungeon.nextLevel() && dungeon.getLevel() < maxLevel) {
				this.setLevel(dungeon.getLevel() + 1);
			} else if (dungeon.getLevel() == maxLevel) {
				this.updateGameState(GAME_FINAL_SCREEN);
			}
		} else if (gameState == GAME_PAUSED) {
			pause.tick();
			
			gameState = pause.getOption();
			
			if (gameState == GAME_RUN) {
				this.updateGameState(GAME_RUN);
			} else if (gameState == GAME_EXIT) {
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
				case GAME_TUTORIAL:
					tutorial.renderFullscreen(g, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
					break;
				case GAME_CREDITS:
					credits.renderFullscreen(g, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
					break;
				case GAME_FINAL_SCREEN:
					finalScreen.renderFullscreen(g, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
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
				case GAME_TUTORIAL:
					tutorial.render(g);
					break;
				case GAME_CREDITS:
					credits.render(g);
					break;
				case GAME_FINAL_SCREEN:
					finalScreen.render(g);
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
				this.updateGameState(GAME_PAUSED);
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
				this.updateGameState(GAME_RUN);
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
		} else if (gameState == GAME_CREDITS || gameState == GAME_TUTORIAL) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.updateGameState(GAME_MENU);
			}
		} else if (gameState == GAME_OVER) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.updateGameState(GAME_MENU);
			}
		} else if (gameState == GAME_FINAL_SCREEN) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.updateGameState(GAME_CREDITS);
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_F2) {
			this.updateFullscreen();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_F3) {
			showFPS = !showFPS;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_F4) {
			enableSound = !enableSound;
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
