package game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.main.Game;

public class Pause {

	private final String[] options;
	private int currentOption;
	private int selectedOption;
	private int maxOption;

	private boolean up;
	private boolean down;
	private boolean enter;

	public Pause() {
		options = new String[3];
		currentOption = 0;
		selectedOption = Game.GAME_PAUSED;
		maxOption = options.length - 1;

		up = false;
		down = false;
		enter = false;

		options[0] = "Resume";
		options[1] = "Menu";
		options[2] = "Exit";
	}
	
	public void menuUp() {
		up = true;
	}
	
	public void menuDown() {
		down = true;
	}
	
	public void menuEnter() {
		enter = true;
	}
	
	public int getOption() {
		return selectedOption;
	}
	
	public void tick() {
		selectedOption = Game.GAME_PAUSED;
		
		if (up) {
			up = false;
			currentOption--;

			if (currentOption < 0) {
				currentOption = 0;
			}
		}

		if (down) {
			down = false;
			currentOption++;

			if (currentOption > maxOption) {
				currentOption = maxOption;
			}
		}

		if (enter) {
			enter = false;
			
			if (currentOption == 0) {
				selectedOption = Game.GAME_RUN;
			} else if (currentOption == 1) {
				selectedOption = Game.GAME_MENU;
			} else if (currentOption == 2) {
				selectedOption = Game.GAME_EXIT;
			}
		}
	}

	public void render(Graphics graphics) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Dungeon of Eternal Conquest<", Game.WIDTH * Game.SCALE / 2 - 330, Game.HEIGHT * Game.SCALE / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		
		graphics.drawString(options[0], Game.WIDTH * Game.SCALE / 2 - 50, 160);
		graphics.drawString(options[1], Game.WIDTH * Game.SCALE / 2 - 50, 204);
		graphics.drawString(options[2], Game.WIDTH * Game.SCALE / 2 - 50, 248);

		if (currentOption == 0) {
			graphics.drawString("-> ", Game.WIDTH * Game.SCALE / 2 - 90, 160);
		} else if (currentOption == 1) {
			graphics.drawString("-> ", Game.WIDTH * Game.SCALE / 2 - 90, 204);
		} else if (currentOption == 2) {
			graphics.drawString("-> ", Game.WIDTH * Game.SCALE / 2 - 90, 248);
		}
		
		graphics.setColor(Color.GREEN);
		
		graphics.drawString("Use W and S keys to move and ENTER to select", Game.WIDTH * Game.SCALE / 2 - 305, 314);
	}
	
	public void renderFullscreen(Graphics graphics, int width, int height) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, width, height);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Dungeon of Eternal Conquest<", width / 2 - 330, height / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		
		graphics.drawString(options[0], width / 2 - 50, height / 2 - 100);
		graphics.drawString(options[1], width / 2 - 50, height / 2 - 50);
		graphics.drawString(options[2], width / 2 - 50, height / 2 - 0);

		if (currentOption == 0) {
			graphics.drawString("-> ", width / 2 - 90, height / 2 - 100);
		} else if (currentOption == 1) {
			graphics.drawString("-> ", width / 2 - 90, height / 2 - 50);
		} else if (currentOption == 2) {
			graphics.drawString("-> ", width / 2 - 90, height / 2 - 0);
		}
		
		graphics.setColor(Color.GREEN);
		
		graphics.drawString("Use W and S keys to move and ENTER to select", width / 2 - 300, height / 2 + 50);
	}

}
