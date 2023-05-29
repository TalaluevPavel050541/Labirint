package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
/*
абстрактный класс, от которого наследуются классы создания дочерних фреймов статистики,
фрейм сохраненного меню. Он используется для переключения окон в игре (родительское и дочернее окно).
 */
public abstract class BaseChildFrame extends JFrame {

    public BaseChildFrame() {
        setCloseOperation();
    }

    private JFrame parentFrame;

    public JFrame getParentFrame() {
        return parentFrame;
    }

    protected void showFrame(JFrame parent) { // показ формы
        this.parentFrame = parent;
        parent.setVisible(false); // дочернее окно невидимо
        super.setVisible(true); //родительское окно видимо
    }

    protected void closeFrame() { // закрытие формы
        if (parentFrame==null) {
            throw new IllegalArgumentException("parent frame must not be null!");
        }
        super.setVisible(false);//родительское окно невидимо
        parentFrame.setVisible(true);// дочернее окно видимо

    }

    protected void setCloseOperation() {
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeFrame();
            }
        });

    }

}

