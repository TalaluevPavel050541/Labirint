package gamemap.impl;

import gamemap.abstracts.AbstractGameMap;
import gamemap.interfaces.TimeMap;
import gameobjects.abstracts.AbstractGameObject;
import enums.GameObjectType;
import collections.interfaces.GameCollection;
import movestrategies.impl.AgressiveMoving;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
//Отрисовка карты - для карты используется JTable из Swing с помощью композиции
public class JTableGameMap extends AbstractGameMap implements TimeMap {

    private transient JTable jTableMap = new JTable(); // создание компонента JTable
    private transient String[] columnNames;// массив для названий столбцов

    // объекты для отображения на карте будут храниться в двумерном массиве типа AbstractGameObject
    // каждый элемент массива будет обозначаться согласно текстовому представлению объекта как описано в GameObjectType
    private transient AbstractGameObject[][] mapObjects;
    private transient TimeMover timeMover = new TimeMover();

    public JTableGameMap(GameCollection gameCollection) {
        super(gameCollection);


        try {
            prepareTable();
            updateObjectsArray();
        } catch (Exception ex) {
            Logger.getLogger(JTableGameMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateObjectsArray() { // обвновление двумерного массива, JTable использует двумерный массив
//считывание объектов из коллекции и заполнение двумерного массива
        mapObjects = new AbstractGameObject[mapInfo.getHeight()][mapInfo.getWidth()];

        for (AbstractGameObject gameObj : getGameCollection().getAllGameObjects()) { // создаем объекты по координатам
            int y = gameObj.getCoordinate().getY();
            int x = gameObj.getCoordinate().getX();
            if (mapObjects[y][x] != null) { // если в этих координатах уже есть какой-то объект
                mapObjects[y][x] = getGameCollection().getObjectByCoordinate(x, y); // получение объекта из коллекции по координатам
            } else {
                mapObjects[y][x] = gameObj;
            }
        }
    }

    @Override
    public boolean updateMap() { // проверка на обновление на карте

        if (mapObjects == null || mapObjects.length == 0) {
            updateObjectsArray(); // обновление объектов на карте
        }

        if (jTableMap.getModel().getRowCount() == 0) {
            updateModel(); // обновляем ячейки на карте
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

    @Override
    public void updateMapObjects(AbstractGameObject obj1, AbstractGameObject obj2) {

        if (obj1 != null) { // объект 1
            int y1 = obj1.getCoordinate().getY(); // получение координаты y1
            int x1 = obj1.getCoordinate().getX();// получение координаты х1

            ((AbstractTableModel) jTableMap.getModel()).setValueAt(getGameCollection().getObjectByCoordinate(x1, y1), y1, x1);
        }

        if (obj2 != null) { // // объект 2
            int y2 = obj2.getCoordinate().getY(); // получение координаты y2
            int x2 = obj2.getCoordinate().getX();// получение координаты х2

            ((AbstractTableModel) jTableMap.getModel()).setValueAt(getGameCollection().getObjectByCoordinate(x2, y2), y2, x2); // получение объекта по координатам
        }

    }

    private boolean updateModel() {

        try {
            // присваиваем пустоту всем заголовкам столбцов, чтобы у таблицы не было заголовоков
            columnNames = new String[mapInfo.getWidth()]; //отрисовываем колонки
           for (int i = 0; i < columnNames.length; i++) {
                columnNames[i] = "";
            }


            // игровое поле будет отображаться в super без заголовков столбцов
            jTableMap.setModel(new DefaultTableModel(mapObjects, columnNames)); // передаем двумерный массив и названия столбцов в модель


            // вместо текста в ячейках таблицы устанавливаем отображение картинки
            for (int i = 0; i < jTableMap.getColumnCount(); i++) {
                jTableMap.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer()); //для каждой ячейки указываем специальный ImageRenderer()
                TableColumn a = jTableMap.getColumnModel().getColumn(i);
                a.setPreferredWidth(26);
            }


        } catch (Exception ex) {
            Logger.getLogger(JTableGameMap.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
//настройки JTable, чтобы карта выглядела без заголовков, чтобы не было линий, заданы ширина и высота (квадратная карта)
    private void prepareTable() {
        jTableMap.setEnabled(false);
        jTableMap.setSize(new java.awt.Dimension(300, 300));
        jTableMap.setRowHeight(26);
        jTableMap.setRowSelectionAllowed(false);
        jTableMap.setShowHorizontalLines(false);
        jTableMap.setShowVerticalLines(false);
        jTableMap.setTableHeader(null);
        jTableMap.setUpdateSelectionOnSort(false);
        jTableMap.setVerifyInputWhenFocusTarget(false);
    }

    //функционал таймера (внутренний класс)
    private class TimeMover implements ActionListener {

        private Timer timer; // класс таймер из свинга
        private final static int MOVING_PAUSE = 500; // пауза между движением
        /*
        Устанавливает начальную задержку таймера,
        время в миллисекундах ожидания после запуска таймера перед запуском первого события.
         */
        private final static int INIT_PAUSE = 1000; // пауза перед началом движения

        private TimeMover() {
            timer = new Timer(MOVING_PAUSE, this);
            timer.setInitialDelay(INIT_PAUSE); // пауза перед началом движения
        }

        public void start() {
            timer.start();
        } // запуск таймера

        public void stop() {
            timer.stop();
        } // остановка таймера

        //каждый полсекунды будет вызываться метод actionPerformed
        @Override
        public void actionPerformed(ActionEvent e) {
            getGameCollection().moveObject(new AgressiveMoving(), GameObjectType.MONSTER); // задание стратегии для монстра
        }
    }
}