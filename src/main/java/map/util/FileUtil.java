package map.util;



import map.model.AD;
import map.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
    public static File USER_MAP_PATH = new File("C:\\Users\\MIHRAN\\advertisment-java\\src\\main\\resources\\file.txt");
    public static File AD_LIST_PATH = new File("C:\\Users\\MIHRAN\\advertisment-java\\src\\main\\resources\\file2.txt");

    public static void serializUserMap(Map<String, User> userMap) throws IOException {
        ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(USER_MAP_PATH));
        obj.writeObject(userMap);
        obj.close();
    }

    public static Map<String, User> deserializeUserMap() throws IOException, ClassNotFoundException {
        Map<String, User> userMap = new HashMap<>();
        if (USER_MAP_PATH.exists()) {
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(USER_MAP_PATH));
            Object object = obj.readObject();
            return (Map<String, User>) object;

        }
        return userMap;
    }

    public static void serializeAdsList(List<AD> ads) throws IOException {
        ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(AD_LIST_PATH));
        obj.writeObject(ads);
        obj.close();
    }

    public static List<AD> deserializeAdsList() throws IOException, ClassNotFoundException {
        List<AD> ad = new ArrayList<>();
        if (AD_LIST_PATH.exists()) {
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(AD_LIST_PATH));
            Object object = obj.readObject();
            return (List<AD>) object;
        }
        return ad;
    }

    public static List<AD> exportItem(String phoneNumber, String password, List<AD> ad) throws IOException, ClassNotFoundException {
        ObjectInputStream obj=new ObjectInputStream(new FileInputStream(AD_LIST_PATH));
        Object object = obj.readObject();
        for (AD ad1 : ad) {
            if (ad1.getOuther().getPhoneNumber().equals(phoneNumber) && ad1.getOuther().getPassword().equals(password)){
                return (List<AD>) object;
            }
        }
        return null;
    }
}
