package sound.interfaces;


import javax.sound.sampled.Clip;

public interface SoundPlayer {


    void startBackgroundMusic(String soundName); // запуск музыкального клипа

    void stopBackgoundMusic(); // остановка музыкального клипа

    void playSound(Clip clip, boolean loop); // проигрывание музыкального клипа
}
