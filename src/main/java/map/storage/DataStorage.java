package map.storage;


import map.model.AD;
import map.model.Category;
import map.model.User;
import map.util.FileUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class DataStorage {
    private static long idAd = 1;
    Map<String, User> userMap = new HashMap<>();
    List<AD> ads = new ArrayList<>();

    public void exportItem(String phoneNumber, String password) throws IOException, ClassNotFoundException {
        List<AD> ads1 = FileUtil.getItem(phoneNumber, password, ads);
        if (ads1 != null) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Example");
            Row row1 = sheet.createRow(0);
            row1.createCell(0).setCellValue("ID");
            row1.createCell(1).setCellValue("TITLE");
            row1.createCell(2).setCellValue("TEXT");
            row1.createCell(3).setCellValue("PRICE");
            row1.createCell(4).setCellValue("CATEGORY");
            row1.createCell(5).setCellValue("DATE");
            row1.createCell(6).setCellValue("USER");
            Outer: for (int i = 1; i <=ads1.size(); i++) {
                Row row = sheet.createRow(i);
                for (int j = i-1; j <i; j++) {
                    row.createCell(0).setCellValue(ads1.get(j).getId());
                    row.createCell(1).setCellValue(ads1.get(j).getTitle());
                    row.createCell(2).setCellValue(ads1.get(j).getText());
                    row.createCell(3).setCellValue(ads1.get(j).getPrice());
                    row.createCell(4).setCellValue(String.valueOf(ads1.get(j).getCategory()));
                    row.createCell(5).setCellValue(ads1.get(j).getDate());
                    row.createCell(6).setCellValue(ads1.get(j).getOuther().toString());
                }
            }
            FileOutputStream file = new FileOutputStream("C:\\Users\\MIHRAN\\Advertisment\\src\\main\\resources\\54.xlsx");
            workbook.write(file);
            file.close();
        } else {
            System.out.println("This User Item is empty!");
        }

    }

    public void initData() throws IOException, ClassNotFoundException {
        userMap = FileUtil.deserializeUserMap();
        ads = FileUtil.deserializeAdsList();
        if (ads != null && !ads.isEmpty()) {
            AD ad = ads.get(ads.size() - 1);
            idAd = ad.getId() + 1;
        }

    }

    public void add(User user) throws IOException {

        userMap.put(user.getPhoneNumber(), user);
        FileUtil.serializUserMap(userMap);


    }

    public void add(AD ad) throws IOException {
        ad.setId(idAd++);
        ads.add(ad);
        FileUtil.serializeAdsList(ads);
    }

    public User getUser(String phoneNumber) {
        return userMap.get(phoneNumber);
    }

    public AD getAdById(long id) {
        for (AD a : ads) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public void printAdByUser(User user) {
        for (AD a : ads) {
            if (a.getOuther().equals(user)) {
                System.out.println(a);
            }
        }
    }

    public void printAds() {
        for (AD a : ads) {
            System.out.println(a);
        }
    }

    public void printAdsOrderByTitle() {
        List<AD> orderList = new ArrayList<>(ads);
        Collections.sort(orderList);
        for (AD ad : orderList) {
            System.out.println(ad);
        }
    }

    public void printAdsOrderByDate() {
        List<AD> orderList = new ArrayList<>(ads);
        orderList.sort(Comparator.comparing(AD::getDate));
        for (AD ad : orderList) {
            System.out.println(ad);
        }
    }

    public void deletById(long id) throws IOException, ClassNotFoundException {
        ads.remove(getAdById(id));
        FileUtil.serializeAdsList(ads);
    }

    public void deleteByUser(User user) throws IOException, ClassNotFoundException {
        Iterator<AD> iterator = ads.iterator();
        while (iterator.hasNext()) {
            AD next = iterator.next();
            if (next.getOuther().equals(user)) {
                iterator.remove();
                FileUtil.serializeAdsList(ads);
            }
        }
    }

    public void printItemByCategory(Category category) {
        for (AD ad : ads) {
            if (ad.getCategory() == category) {
                System.out.println(ad);
            }
        }
    }

}
