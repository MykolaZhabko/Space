package mz.periferals;

import javafx.scene.media.AudioClip;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SoundManager {
    public ExecutorService soundPool;
    public Map<String, AudioClip> soundEffectsMap = new HashMap<>();

    public SoundManager(int numberOfThreads){
        soundPool = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void loadSoundEffects(String id, String url){
        System.out.println("Here!!");
        AudioClip sound = new AudioClip(Paths.get(url).toUri().toString());
        System.out.println("Sound = " );
        soundEffectsMap.put(id,sound);
    }

    public void playSound(final String id){
        Runnable soundPlay = new Runnable() {
            @Override
            public void run() {
                soundEffectsMap.get(id).play();
            }
        };
        soundPool.execute(soundPlay);
    }

    public void shutdown(){
        soundPool.shutdown();
    }
}
