package gamemap.impl;

import gamemap.abstracts.AbstractGameMap;
import gamemap.interfaces.TimeMap;
import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;
import gameobjects.impl.Coordinate;
import gameobjects.impl.Nothing;
import gameobjects.impl.Wall;
import collections.interfaces.GameCollection;
import movestrategies.impl.SlowMoving;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JTableGameMap extends AbstractGameMap implements TimeMap {

    private transient JTable jTableMap = new JTable();
    private transient String[] columnNames;
    // объекты для отображения на карте будут храниться в двумерном массиве типа AbstractGameObject
    // каждый элемент массива будет обозначаться согласно текстовому представлению объекта как описано в GameObjectType
    private transient AbstractGameObject[][] mapObjects;
    private transient TimeMover timeMover = new TimeMover();

    public JTableGameMap(GameCollection gameCollection) {
        super(gameCollection);


        try {
            jTableMap.setEnabled(false);
            jTableMap.setSize(new java.awt.Dimension(300, 300));
            jTableMap.setRowHeight(26);
            jTableMap.setRowSelectionAllowed(false);
            jTableMap.setShowHorizontalLines(false);
            jTableMap.setShowVerticalLines(false);
            jTableMap.setTableHeader(null);
            jTableMap.setUpdateSelectionOnSort(false);
            jTableMap.setVerifyInputWhenFocusTarget(false);
        } catch (Exception ex) {
            Logger.getLogger(JTableGameMap.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void fillEmptyMap(int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapObjects[y][x] = new Nothing(new Coordinate(x, y));
            }
        }
    }

    private void updateObjectsArray() {

        mapObjects = new AbstractGameObject[mapInfo.getHeight()][mapInfo.getWidth()];

        fillEmptyMap(mapInfo.getWidth(), mapInfo.getHeight());

        // потом заполнить массив объектами
        for (AbstractGameObject gameObj : getGameCollection().getAllGameObjects()) {
            if (!gameObj.getType().equals(GameObjectType.NOTHING)
            ) {// пустоты не добавляем, т.к. они уже добавились когда мы вызвали метод fillEmptyMap()
                int y = gameObj.getCoordinate().getY();
                int x = gameObj.getCoordinate().getX();
                if (!(mapObjects[y][x] instanceof Nothing) & // если в этих координатах уже есть какой то объект, отличный от пустоты и стены
                        !(mapObjects[y][x] instanceof Wall)) {
                    AbstractGameObject tmpObj = mapObjects[y][x];
                    mapObjects[y][x] = getPriorityObject(tmpObj, gameObj);
                } else {
                    mapObjects[y][x] = gameObj;// проставить объект на карте согласно его координатам
                }
            }
        }
    }

    @Override
    public boolean drawMap() {

        updateObjectsArray();

        try {
            // присваиваем пустоту всем заголовкам столбцов, чтобы у таблицы не было заголовоков, а то некрасиво смотрится
            columnNames = new String[mapInfo.getWidth()];

            for (int i = 0; i < columnNames.length; i++) {
                columnNames[i] = "";
            }


            // игровое поле будет отображаться в super без заголовков столбцов
            jTableMap.setModel(new DefaultTableModel(mapObjects, columnNames));


            // вместо текста в ячейках таблицы устанавливаем отображение картинки
            for (int i = 0; i < jTableMap.getColumnCount(); i++) {
                jTableMap.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());
                TableColumn a = jTableMap.getColumnModel().getColumn(i);
                a.setPreferredWidth(26);
            }
        } catch (Exception ex) {
            Logger.getLogger(JTableGameMap.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }



        return true;
    }

    @Override
    public Component getMapComponent() {
        return jTableMap;
    }

    @Override
    public void start() {
        timeMover.start();
    }

    @Override
    public void stop() {
        timeMover.stop();
    }

    private class TimeMover implements ActionListener {

        private Timer timer;
        private final static int MOVING_PAUSE = 500;
        private final static int INIT_PAUSE = 1000;

        private TimeMover() {
            timer = new Timer(MOVING_PAUSE, this);
            timer.setInitialDelay(INIT_PAUSE);
        }

        public void start() {
            timer.start();
        }

        public void stop() {
            timer.stop();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            getGameCollection().moveObject(new SlowMoving(), GameObjectType.MONSTER);
        }
    }
}
