package models;

import objects.SavedMapInfo;
import objects.MapInfo;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
// класс отвечающий за таблицу, которая появляется при нажатии кнопки загрузить игру для определенного пользователя
public class SaveGameTableModel extends AbstractTableModel {

    private final ArrayList<SavedMapInfo> list; // список объектов SavedMapInfo - сохранение игры

    public SaveGameTableModel(ArrayList<SavedMapInfo> list) {
        this.list = list;
    }

    public MapInfo getMapInfo(int index){
        return list.get(index);
    }

    @Override
    public int getRowCount() {
        return list.size();
    } // количество строк получаем из размера коллекции, сколько будет объектов - столько и строк

    @Override
    public int getColumnCount() {
        return 5;
    } // количестов столбцов - 5
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // формат даты

    //получение данных для каждого поля таблицы с сохраненными результатами
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = list.get(rowIndex).getUser().getUsername();
                break;
            case 2:
                Date date = new Date(list.get(rowIndex).getSaveDate()); // получение даты
                value = dateFormat.format(date); // изменение формата даты
                break;
            case 3:
                value = list.get(rowIndex).getTotalScore(); // получение количества очков
                break;
            case 4:
                value = list.get(rowIndex).getTurnsCount(); // получение количества ходов
                break;


            default:
                throw new IndexOutOfBoundsException("Column index out of bounds: " + // NOI18N
                        columnIndex);
        }
        return value;
    }

    //определяем тип значений для каждого поля
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class<?> clazz;
        switch (columnIndex) {// задаем типы для полей в таблице
            case 0://1,4,5 - это число
            case 3:
            case 4:
                clazz = Integer.class;
                break;
            case 1:// 2,3 - строка
            case 2:
                clazz = String.class;
                break;


            default:
                throw new IndexOutOfBoundsException("Column index out of bounds: " + columnIndex);
        }
        return clazz;
    }

    // названия столбцов в таблице
    @Override
    public String getColumnName(int column) {
        String columnName;
        switch (column) {
            case 0: // первый столбец
                columnName = "№";
                break;
            case 1: // второй столбец
                columnName = "Имя игрока";
                break;
            case 2: // третий столбец
                columnName = "Дата игры";
                break;
            case 3: // 4 стоблец
                columnName = "Кол-во очков";
                break;
            case 4: // 5 столбец
                columnName = "Кол-во ходов";
                break;
            default:
                throw new IndexOutOfBoundsException("Column index out of bounds: " + column);
        }
        return columnName;
    }

    public void deleteMapInfo(int index){
        list.remove(index);
    } // удаление сохраненной игры

    public void refresh() {
        fireTableDataChanged();
    } // обвновление таблицы с данными
}