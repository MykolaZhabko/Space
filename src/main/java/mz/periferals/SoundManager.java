package mz.periferals;

import javafx.scene.media.AudioClip;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * The SOUND MANAGER class. The name of the class is already describing the functionality.
 * So the class is preloads the URL to the audio clip into the MAP, where the key is associated with desired sound effect
 */

public class SoundManager implements Serializable {
    ExecutorService soundPool;
    Map<String, AudioClip> soundEffectsMap = new HashMap<>();

    /**
     * The constructor will create the pool of the threads. In my case there is 2 threads.
     * @param numberOfThreads
     */
    public SoundManager(int numberOfThreads){
        soundPool = Executors.newFixedThreadPool(numberOfThreads);
        loadSoundEffects("laser1", "accets/Sounds/weaponfire6.wav");
        loadSoundEffects("laser2","accets/Sounds/laser6.wav");
        loadSoundEffects("explosion","accets/Sounds/explosion4.wav");
        loadSoundEffects("explosionFighter","accets/Sounds/explosion2.wav");
    }

    /**
     * Loads the sound effect to the MAP<ID:String, SOUND:AudioClip>
     * @param id
     * @param url
     */
    public void loadSoundEffects(String id, String url){
        AudioClip sound = new AudioClip(Paths.get(url).toUri().toString());
        soundEffectsMap.put(id,sound);
    }

    /**
     * Run the thread in which the sound effect is played
     * @param id
     */
    public void playSound(final String id){
        Runnable soundPlay = new Runnable() {
            @Override
            public void run() {
                soundEffectsMap.get(id).play();
            }
        };
        soundPool.execute(soundPlay);
    }

    /**
     * Correct shutdown
     */
    public void shutdown(){
        soundPool.shutdown();
    }
}
