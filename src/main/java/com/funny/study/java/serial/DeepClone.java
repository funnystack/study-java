package com.funny.study.java.serial;

import java.io.*;

public class DeepClone {
    public static <T extends Serializable> T copy(T input) throws IOException {

        ByteArrayOutputStream baos = null;

        ObjectOutputStream oos = null;

        ByteArrayInputStream bis = null;

        ObjectInputStream ois = null;

        try {

            baos = new ByteArrayOutputStream();

            oos = new ObjectOutputStream(baos);

            oos.writeObject(input);

            oos.flush();

            byte[] bytes = baos.toByteArray();

            bis = new ByteArrayInputStream(bytes);

            ois = new ObjectInputStream(bis);

            Object result = ois.readObject();

            return (T) result;

        } catch (IOException e) {

            throw new IllegalArgumentException("Object can't be copied", e);

        } catch (ClassNotFoundException e) {

            throw new IllegalArgumentException("Unable to reconstruct serialized object due to invalid class definition", e);

        } finally {
            baos.close();
            bis.close();
            ois.close();
        }
    }
}
