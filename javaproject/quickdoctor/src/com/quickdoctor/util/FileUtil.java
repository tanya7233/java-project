package com.quickdoctor.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {
    
    
    public static <T> void saveToFile(T object, String filePath) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(object);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    public static <T> T loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (T) ois.readObject();
        }
    }
    
  
    public static <T> void saveListToFile(List<T> list, String filePath) throws IOException {
        saveToFile(new ArrayList<>(list), filePath);
    }
    
  
    public static <T> List<T> loadListFromFile(String filePath) throws IOException, ClassNotFoundException {
        List<T> list = loadFromFile(filePath);
        return list != null ? list : new ArrayList<>();
    }
    
   
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
    
    
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }
}
