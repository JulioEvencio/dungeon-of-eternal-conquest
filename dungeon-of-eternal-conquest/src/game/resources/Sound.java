package game.resources;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private final String path;
	private Clip clip;

	public Sound(String path) {
		this.path = path;
	}

	public void play() {
		if (clip != null && !clip.isRunning()) {
			clip.setFramePosition(0);
			clip.start();
		}
	}

	public void stop() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
			clip.setFramePosition(0);
		}
	}

	public void loop() {
		if (clip != null && !clip.isRunning()) {
			clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	private void loadClip() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(path));
			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}

	private class SoundThread extends Thread {
		@Override
		public void run() {
			loadClip();
		}
	}

	public void start() {
		SoundThread soundThread = new SoundThread();
		soundThread.start();
	}

}
