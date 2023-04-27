package abstracts;

import abstracts.AbstractGameObject;
import objects.interfaces.MovingObject;

import javax.swing.*;

/**
 * класс, который отвечает за любой движущийся объект. наследуется от класса
 * AbstractGameObject с добавлением функций движения
 */
public abstract class AbstractMovingObject extends AbstractGameObject implements MovingObject{

    
    private ImageIcon iconLeft;
    private ImageIcon iconUp;
    private ImageIcon iconDown;
    private ImageIcon iconRight;


    public void setIconLeft(ImageIcon iconLeft) {
        this.iconLeft = iconLeft;
    }

    public void setIconUp(ImageIcon iconUp) {
        this.iconUp = iconUp;
    }

    public void setIconDown(ImageIcon iconDown) {
        this.iconDown = iconDown;
    }

    public void setIconRight(ImageIcon iconRight) {
        this.iconRight = iconRight;
    }

    @Override
    public ImageIcon getIconLeft() {
        return iconLeft;
    }

    @Override
    public ImageIcon getIconUp() {
        return iconUp;
    }

    @Override
    public ImageIcon getIconDown() {
        return iconDown;
    }

    @Override
    public ImageIcon getIconRight() {
        return iconRight;
    }
}
