package gameobjects.abstracts;

import sound.impl.WavPlayer;
import sound.interfaces.SoundObject;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//абстрактный класс, отвечающий за работу со звуковыми файлами
public abstract class AbstractSoundObject extends AbstractMovingObject implements SoundObject {

    private transient Clip dieClip;

    public Clip getDieClip() { // получение звукового файла для события Die
        if (dieClip==null){
            setDieClip();
        }
        return dieClip;
    }


    protected Clip openClip(String soundName) { // метод считывания звукового файла
        try {
            Clip clip = AudioSystem.getClip();
            //получаем все звуковые файлы по названию из указанной папки
            AudioInputStream ais = AudioSystem.getAudioInputStream(this.getClass().getResource(WavPlayer.SOUND_PATH + soundName));
            clip.open(ais); //открываем поток
            return clip; //возвращаем клип
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AbstractSoundObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AbstractSoundObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AbstractSoundObject.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void setDieClip() { // запись файла
        dieClip = openClip(WavPlayer.SOUND_DIE); // открытие звукового файла SOUND_DIE
    }
}

