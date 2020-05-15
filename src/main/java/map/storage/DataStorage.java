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
        List<AD> ads1 = FileUtil.exportItem(phoneNumber, password, ads);
        if (ads1 != null) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Example");
            Row row1=sheet.createRow(0);
            row1.createCell(0).setCellValue("ID");
            row1.createCell(1).setCellValue("TITLE");
            row1.createCell(2).setCellValue("TEXT");
            row1.createCell(3).setCellValue("PRICE");
            row1.createCell(4).setCellValue("DATE");
            row1.createCell(5).setCellValue("CATEGORY");
            row1.createCell(6).setCellValue("USER");
            for (int i = 1; i < ads1.size(); i++) {
                for (AD ad : ads1) {
                    Row row = sheet.createRow(i);
                    row.createCell(0).setCellValue(ad.getId());
                    row.createCell(1).setCellValue(ad.getTitle());
                    row.createCell(2).setCellValue(ad.getText());
                    row.createCell(3).setCellValue(ad.getPrice());
                    row.createCell(4).setCellValue(ad.getDate());
                    row.createCell(5).setCellValue(String.valueOf(ad.getCategory()));
                    row.createCell(6).setCellValue(ad.getOuther().toString());
                    break;
                }
            }
            FileOutputStream file=new FileOutputStream("C:\\Users\\MIHRAN\\advertisment-java\\src\\main\\resources\\14.xlsx" );
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
