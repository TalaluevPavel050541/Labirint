package enums;

import java.io.Serializable;

// перечисления, которые задают доступные типы объектов игры

// перечисления используются когда нужно описать ограниченное количество типов какого либо объекта
/**
 * типы объектов, которые участвуют в игре (которые будут рисоваться на карте)
 */
public enum GameObjectType implements Serializable { //перечисление констант

        TREE(6), // дерево
        MONSTER(5), // монстр
        TREASURE(4), // сокровище
        EXIT(3), //выход
        WALL(2), //стена
        GOLDMAN(1), //персонаж
        NOTHING(-1);

        private GameObjectType(int indexPriority) {
            this.indexPriority = indexPriority;
        }

        private int indexPriority;// индекс для приоритета показа на карте, если несколько объектов окажется в одном квадрате

        public int getIndexPriority() {
            return indexPriority;
        }
}

