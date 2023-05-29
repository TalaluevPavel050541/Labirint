package sound.impl;

import gameobjects.abstracts.AbstractGameObject;
import enums.ActionResult;
import listeners.interfaces.MoveResultListener;
import sound.interfaces.SoundObject;
import sound.interfaces.SoundPlayer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//класс для работы со звуком, является подписчиком для слушателя по паттерну Наблюдатель
public class WavPlayer implements MoveResultListener, SoundPlayer {

    public static final String SOUND_BACKGROUND = "background.wav";
    public static final String SOUND_DIE = "die.wav";
    public static final String SOUND_TREASURE = "treasure.wav";
    public static final String SOUND_WIN = "win.wav";
    public static final String SOUND_MOVE = "waka_waka.wav";
    public static final String SOUND_TREE = "tree.wav";

    public static final String SOUND_PATH = "/sounds/";
    private Clip backGroundClip;

    public WavPlayer() {

        AudioInputStream ais = null; // библиотека для работы со звуковыми файлами
        try {
            backGroundClip = AudioSystem.getClip(); // получение клипа для проигрывания фоновой музыки
            ais = AudioSystem.getAudioInputStream(this.getClass().getResource(WavPlayer.SOUND_PATH + SOUND_BACKGROUND)); // получение музыкального клипа
            backGroundClip.open(ais);//открываем поток

        } catch (UnsupportedAudioFileException ex) { //в случае ошибки выводятся логи с сообщениями
            Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(WavPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void startBackgroundMusic(String soundName) {
        playSound(backGroundClip, true);
    } //воспроизведение фоновой музыки

    @Override
    public void stopBackgoundMusic() { //остановка фоновой музыки
        if (backGroundClip != null && backGroundClip.isRunning()) {
            backGroundClip.stop();
        }

    }

    @Override //уведомление о событии
    public void notifyActionResult(ActionResult actionResult, final AbstractGameObject abstractMovingObject, final AbstractGameObject targetObject) {

        if (!(abstractMovingObject instanceof SoundObject)) { // проверка объекта
            return;
        }

        SoundObject soundObject = (SoundObject) abstractMovingObject;

        Clip clip = soundObject.getSoundClip(actionResult); //получение музыкального файла в зависимости от типа события, происходящего на карте
//музыка на карте меняется в зависимости от события
        playSound(clip, false); //проигрывание файла музыкального

    }

    @Override
    public void playSound(final Clip clip, final boolean loop) { //проигрывание файла музыкального

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() { //работа с потоками в Java

                if (clip == null) {
                    return;
                }

                clip.setFramePosition(0);

                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    if (clip.isRunning()) {
                        clip.stop();
                    }

                    clip.start();
                }


            }
        });

        thread.setDaemon(true); // работа с потоками в Java
        thread.start();
    }
}
