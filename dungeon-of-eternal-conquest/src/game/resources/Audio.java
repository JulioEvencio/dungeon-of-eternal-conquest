package game.resources;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import game.main.Game;

public class Audio {

	private Clip clip;

	public Audio(String fileName) {
		try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Audio.class.getResource(fileName))) {
			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			this.clip = AudioSystem.getClip();

			if (AudioSystem.isLineSupported(info)) {
				this.clip.open(audioInputStream);
			} else {
				AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16, format.getChannels(), format.getChannels() * 2, format.getSampleRate(), false);

				try (AudioInputStream newAudioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream)) {
					this.clip.open(newAudioInputStream);
				}
			}
		} catch (Exception e) {
			Game.exitWithError("Error loading resources");
		}
	}

	public void play() {
		if (!this.clip.isRunning()) {
			this.clip.setFramePosition(0);
			this.clip.start();
		}
	}

	public void stop() {
		if (this.clip.isRunning()) {
			this.clip.stop();
		}
	}

}
