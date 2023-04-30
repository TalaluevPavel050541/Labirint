package objects.sound;

import abstracts.AbstractMovingObject;
import enums.ActionResult;
import objects.listeners.MoveResultListener;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WavPlayer implements MoveResultListener, SoundPlayer {
    
    private Clip backGroundClip; 
    private Clip moveClip; 

    public WavPlayer() {
        try {
            backGroundClip = AudioSystem.getClip();
            moveClip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void startBackgroundMusic(String soundName) {
        playSound(soundName, true, backGroundClip, false);
    }
    
    @Override
    public void stopBackgoundMusic(){
        backGroundClip.stop();
        backGroundClip.close();
    }

    @Override
    public void notifyActionResult(ActionResult actionResult, final AbstractMovingObject abstractMovingObject) {

        if (!(abstractMovingObject instanceof SoundObject)) {
            return;
        }
        
        if (actionResult.equals(ActionResult.DIE)){
            backGroundClip.stop();
            backGroundClip.close();
        }

        SoundObject soundObject = (SoundObject) abstractMovingObject;

        playSound(soundObject.getSoundPath(actionResult), false, moveClip, true);

    }
    
    @Override
    public void playSound(String name, final boolean loop){
        try {
            playSound(name, loop, AudioSystem.getClip(), false);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private void playSound(String name, final boolean loop, final Clip clip, final boolean stopPrev) {

        try {
            
            if (name==null){
                return;
            }

            URL sound = this.getClass().getResource("/sounds/"+name);
            
            final AudioInputStream ais = AudioSystem.getAudioInputStream(sound);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (stopPrev && clip != null) { //избегание накладывания звука
                            clip.stop();
                            clip.close();
                        }

                        clip.open(ais);

                        if (loop) {
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        } else {
                            clip.start();
                        }
                       
                    } catch (IOException ex) {
                        Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (ais != null) {
                            try {
                                ais.close();
                            } catch (IOException ex) {
                                Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                }
            });


        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
