package models;

import objects.UserScore;
import javax.swing.table.AbstractTableModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
// класс для отображения таблицы сохраненных результатов пользователя - СТАТИСТИКА
public class ScoreTableModel extends AbstractTableModel {

    private final ArrayList<UserScore> list;// список сохраненных объектов UserScore

    public ScoreTableModel(ArrayList<UserScore> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    } // количество строк получаем из размера коллекции, сколько будет объектов - столько и строк

    @Override
    public int getColumnCount() {
        return 5;
    } //задаем количество столбцов
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // формат даты

    //получение данных для каждого поля таблицы с сохраненными результатами
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = rowIndex + 1; // поле используется для нумерации
                break;
            case 1:
                value = list.get(rowIndex).getUser().getUsername(); // получение имени пользователя
                break;
            case 2:
                Date date = new Date(list.get(rowIndex).getPlayDate()); // получение даты из таблицы
                value = dateFormat.format(date); // преобразование формата даты
                break;
            case 3:
                value = list.get(rowIndex).getScore(); // получение количества очков
                break;
            case 4:
                value = list.get(rowIndex).getPlayCount(); // получение количества игр
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
        switch (columnIndex) { // задаем типы для полей в таблице
            case 0: //1,4,5 - это число
            case 3:
            case 4:
                clazz = Integer.class;
                break;
            case 1: // 2,3 - строка
            case 2:
                clazz = String.class;
                break;

            default:
                throw new IndexOutOfBoundsException("Column index out of bounds: " + columnIndex);
        }
        return clazz;
    }

    // даем имена полям в таблице
    @Override
    public String getColumnName(int column) {
        String columnName;
        switch (column) {
            case 0:
                columnName = "№";
                break;
            case 1:
                columnName = "Имя игрока";
                break;
            case 2:
                columnName = "Дата игры";
                break;
            case 3:
                columnName = "Кол-во очков";
                break;
            case 4:
                columnName = "Кол-во игр";
                break;
            default:
                throw new IndexOutOfBoundsException("Column index out of bounds: " + column);
        }
        return columnName;
    }
}