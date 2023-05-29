package gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
абстрактный класс, определяющий новое поведение для закрытия окна в классе FrameGame
(переопределение метода с дополнительной функцией).
перед закрытием сделать какую-либо операцию
 */
public abstract class ConfirmCloseFrame extends BaseChildFrame {

    protected abstract boolean acceptCloseAction();

    @Override
    protected void setCloseOperation() {
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                if (acceptCloseAction()) { //если True
                    closeFrame(); // закрываем фрейм
                }
            }
        });

    }
}
