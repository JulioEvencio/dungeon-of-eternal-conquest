package game.resources;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.main.Game;

public class Sound {

	private final String path;
	private Clip clip;

	public Sound(String path) {
		this.path = path;
	}

	public void soundPlay() {
		if (clip != null && !clip.isRunning()) {
			clip.setFramePosition(0);
			clip.start();
		}
	}

	public void soundStop() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
			clip.setFramePosition(0);
		}
	}

	public void soundLoop() {
		if (clip != null && !clip.isRunning()) {
			clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	private void loadClip() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			AudioFormat format = audioInputStream.getFormat();
			
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			
			if (!AudioSystem.isLineSupported(info)) {
                AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16, format.getChannels(), format.getChannels() * 2, format.getSampleRate(), false);

                audioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
                format = audioInputStream.getFormat();
                info = new DataLine.Info(Clip.class, format);
            }
			
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			Game.exitWithError();
		}
	}

	private class SoundThread extends Thread {
		@Override
		public void run() {
			loadClip();
			soundStop();
		}
	}

	public void start() {
		SoundThread soundThread = new SoundThread();
		soundThread.start();
	}

}
