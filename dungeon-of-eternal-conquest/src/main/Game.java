package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private boolean isRunning;
	
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	private boolean showFPS = true;
	private int fps;
	
	private final BufferedImage renderer;
	
	public Game() {
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		JFrame frame = new JFrame();
		
		frame.setTitle("Dungeon of eternal conquest");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		renderer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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
	
	public void tick() {
		// Code
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
		
		// Code
		
		render.dispose();

		Graphics g = bs.getDrawGraphics();
		g.drawImage(renderer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		// Code
		
		if (showFPS) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.setColor(Color.WHITE);
			g.drawString("FPS: " + fps, 620, 32);
		}
		
		bs.show();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// Code
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Code
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F3 || e.getKeyCode() == KeyEvent.VK_F3) {
			showFPS = !showFPS;
		}
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
		new Game().start();
	}
	
}
