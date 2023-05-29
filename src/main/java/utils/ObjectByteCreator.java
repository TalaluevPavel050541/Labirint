package utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//Класс для работы с файлами, считывание байтов в файле - используются потоки - стандартная работа со считыванием информации в файле
public class ObjectByteCreator {

    public static byte[] getBytes(Object obj) { //получение байтов
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj); // запись
            return baos.toByteArray(); // массив байтов
        } catch (IOException ex) {
            Logger.getLogger(ObjectByteCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(ObjectByteCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public static Object getObject(byte[] bytes) { // получение объекта
        Object obj = null;
        InputStream is = null;
        ObjectInputStream ois = null;

        try {

            is = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(is);

            obj = ois.readObject(); // чтение

            return obj;
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(ObjectByteCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(ObjectByteCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }
}
