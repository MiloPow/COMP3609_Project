import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;				

public class SoundManager {
	HashMap<String, Clip> clips;

	public SoundManager () {
		Clip clip;

		clips = new HashMap<String, Clip>();

		clip = loadClip("Sounds/Shoot.wav");
		clips.put("shoot", clip);

		clip = loadClip("Sounds/Hit.wav");
		clips.put("hit", clip);

		clip = loadClip("Sounds/Explosion.wav");
		clips.put("explosion", clip);
	}


	public Clip loadClip (String fileName) {
	AudioInputStream audioIn;
	Clip clip = null;

	try {
			File file = new File(fileName);
			audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL()); 
			clip = AudioSystem.getClip();
			clip.open(audioIn);
	}
	catch (Exception e) {
		System.out.println ("Error opening sound file: " + fileName + e);
	}
		return clip;
	}


	public Clip getClip (String title) {

		return clips.get(title);
	}


    	public void playClip(String title) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.setFramePosition(0);
			clip.start();
		}
    	}
}