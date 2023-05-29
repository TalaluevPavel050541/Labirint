package sound.interfaces;

import enums.ActionResult;

import javax.sound.sampled.Clip;

public interface SoundObject {

    Clip getSoundClip(ActionResult actionResult); // получение музыкального клипа в зависитости от события
    
}
