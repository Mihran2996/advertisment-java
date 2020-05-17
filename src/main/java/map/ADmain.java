package map;


import map.model.AD;
import map.model.Category;
import map.model.Gender;
import map.model.User;
import map.storage.DataStorage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;


public class ADmain implements Comands {

    public static Scanner scanner = new Scanner(System.in);
    public static DataStorage dataStorage = new DataStorage();
    public static User currentUser = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        boolean isRun = true;
        while (isRun) {
            dataStorage.initData();
            Comands.printMainComands();
            int comand;
            try {
                comand = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                comand = -1;
                System.out.println("Please input number!");
            }
            switch (comand) {
                case EXIT:
                    isRun = false;
                    System.out.println("Byby");
                    break;
                case LOGIN:
                    loginUsre();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                case IMPORST_USER:
                    importUsers();
                    break;
                default:
                    System.out.println("Wrong comand!");
            }
        }
    }

    private static void importUsers() {
        System.out.println("Please select xlsx.path");
        String xlsxpath = scanner.nextLine();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(xlsxpath);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                String name = row.getCell(0).getStringCellValue();
                String surname = row.getCell(1).getStringCellValue();
                Double age = row.getCell(2).getNumericCellValue();
                Gender gender = Gender.valueOf(row.getCell(3).getStringCellValue());
                String phoneNumber = row.getCell(4).getCellType() == CellType.NUMERIC ? String.valueOf(Double.valueOf(row.getCell(4).getNumericCellValue()).intValue()) : row.getCell(4).getStringCellValue();
                String password = row.getCell(5).getCellType() == CellType.NUMERIC ? String.valueOf(Double.valueOf(row.getCell(5).getNumericCellValue()).intValue()) : row.getCell(5).getStringCellValue();
                User user = new User();
                user.setName(name);
                user.setSurname(surname);
                user.setAge(age.intValue());
                user.setGender(gender);
                user.setPhoneNumber(phoneNumber);
                user.setPassword(password);
                dataStorage.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("User was success");

    }

    private static void registerUser() {
        System.out.println("Please input user data `name,surname,gender(MALE,FEMALE),age,phoneNumber,passvord");
        try {
            String registrStr = scanner.nextLine();
            String[] registrArr = registrStr.split(",");
            User userFromStorage = dataStorage.getUser(registrArr[4]);
            if (userFromStorage == null) {
                User user = new User();
                user.setName(registrArr[0]);
                user.setSurname(registrArr[1]);
                user.setGender(Gender.valueOf(registrArr[2]));
                user.setAge(Integer.parseInt(registrArr[3]));
                user.setPhoneNumber(registrArr[4]);
                user.setPassword(registrArr[5]);
                dataStorage.add(user);
                System.out.println("User was successfully added");
            } else {
                System.out.println("User already exsist");
            }
        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            System.out.println("Wrong comand " + e);
        }

    }

    private static void loginUsre() {
        System.out.println("Please input `phoneNumber,Password");
        try {
            String loginStr = scanner.nextLine();
            String[] loginArr = loginStr.split(",");
            User user = dataStorage.getUser(loginArr[0]);
            if (user != null && user.getPassword().equals(loginArr[1])) {
                currentUser = user;
                loginSuccess();
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Wrong data " + e);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void loginSuccess() throws IOException, ClassNotFoundException {
        System.out.println("Welcome " + currentUser.getName() + " (!)");
        boolean isRun = true;
        while (isRun) {
            Comands.printUserComands();
            int comand;
            try {
                comand = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                comand = -1;
            }
            switch (comand) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_NEW_AD:
                    addNewAd();
                    break;
                case PRINT_MY_ADS:
                    dataStorage.printAdByUser(currentUser);
                    break;
                case PRINT_ALL_ADS:
                    dataStorage.printAds();
                    break;
                case PRINT_AD_BY_CATEGORY:
                    printByCategory();
                    break;
                case PRINT_ALL_ADS_SORT_BY_TITLE:
                    dataStorage.printAdsOrderByTitle();
                    break;
                case PRINT_ALL_ADS_SORT_BY_DATE:
                    dataStorage.printAdsOrderByDate();
                    break;
                case DELETE_MY_ALL_ADS:
                    dataStorage.deleteByUser(currentUser);
                    break;
                case DELETE_AD_BY_ID:
                    deleteById();
                    break;
                case IMPORT_ITEM:
                    importItem();
                    break;
                case EXPORT_ITEM:
                    dataStorage.exportItem(currentUser.getPhoneNumber(),currentUser.getPassword());
                    break;
                default:
                    System.out.println("Wrong comand!");
            }
        }
    }

    private static void importItem() {
        System.out.println("Please select xlsx path");
        try {
            String xlsxpath = scanner.nextLine();
            XSSFWorkbook workbook = new XSSFWorkbook(xlsxpath);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();
            for (int i = 1; i <=lastRow; i++) {
                Row row = sheet.getRow(i);
                String title = row.getCell(1).getStringCellValue();
                String text = row.getCell(2).getStringCellValue();
                int price = Double.valueOf(row.getCell(3).getNumericCellValue()).intValue();
                Category category = Category.valueOf(row.getCell(4).getStringCellValue());
                AD ads = new AD();
                ads.setOuther(currentUser);
                ads.setCategory(category);
                ads.setPrice(price);
                ads.setTitle(title);
                ads.setText(text);
                ads.setDate(new Date());
                dataStorage.add(ads);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Item was success");
    }

    private static void deleteById() {
        System.out.println("Please choose id from list");
        dataStorage.printAdByUser(currentUser);
        try {
            long id = Long.parseLong(scanner.nextLine());
            AD adById = dataStorage.getAdById(id);
            if (adById != null && adById.getOuther().equals(currentUser)) {
                dataStorage.deletById(id);
            } else {
                System.out.println("Wrong id!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong id!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printByCategory() {
        System.out.println("Please choose category from name from list " + Arrays.toString(Category.values()));
        try {
            String cat = scanner.nextLine();
            Category category = Category.valueOf(cat);
            dataStorage.printItemByCategory(category);
        } catch (Exception e) {
            System.out.println("Wromg category!");
        }
    }

    private static void addNewAd() {
        System.out.println("Please input item data ` title,text,price,category");
        System.out.println("Please choose category from name from list " + Arrays.toString(Category.values()));
        try {
            String adStr = scanner.nextLine();
            String[] adArr = adStr.split(",");
            AD ad = new AD();
            ad.setTitle(adArr[0]);
            ad.setText(adArr[1]);
            ad.setPrice(Integer.parseInt(adArr[2]));
            ad.setDate(new Date());
            ad.setCategory(Category.valueOf(adArr[3]));
            ad.setOuther(currentUser);
            dataStorage.add(ad);
            System.out.println("Item was successfully added");
        } catch (Exception e) {
            System.out.println("Wrong data!");
        }
    }
}
