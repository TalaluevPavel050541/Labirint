package gamemap.impl;

import gameobjects.abstracts.AbstractGameObject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageRenderer extends DefaultTableCellRenderer {

    private JLabel lbl = new JLabel();

    //получаем компонетны для ячеек таблицы в виде иконок - метод переопределяет внешний вид отображения в ячейки
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        lbl.setText(null); // не указываем текс
        lbl.setIcon(((AbstractGameObject) value).getIcon());// указываем в ячейках иконки (изображения)
        return lbl;
    }
}
