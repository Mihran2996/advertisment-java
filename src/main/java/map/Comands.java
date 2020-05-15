package map;

public interface Comands {
    int LOGOUT = 0;
    int ADD_NEW_AD = 1;
    int PRINT_MY_ADS = 2;
    int PRINT_ALL_ADS = 3;
    int PRINT_AD_BY_CATEGORY = 4;
    int PRINT_ALL_ADS_SORT_BY_TITLE = 5;
    int PRINT_ALL_ADS_SORT_BY_DATE  = 6;
    int DELETE_MY_ALL_ADS = 7;
    int  DELETE_AD_BY_ID = 8;
    int IMPORT_ITEM=9;
    int EXPORT_ITEM=10;

    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;
    int IMPORST_USER =3;

    static void printMainComands() {
        System.out.println(EXIT + " For EXIT");
        System.out.println(LOGIN + " For LOGIN");
        System.out.println(REGISTER + " For REGISTER");
        System.out.println(IMPORST_USER+" for import");
    }

    static void printUserComands() {
        System.out.println("Please input " + LOGOUT + " for LOGOUT");
        System.out.println("Please input " + ADD_NEW_AD + " for ADD_NEW_AD");
        System.out.println("Please input " + PRINT_MY_ADS + " for PRINT_MY_ADS");
        System.out.println("Please input " + PRINT_ALL_ADS + " for PRINT_ALL_ADS");
        System.out.println("Please input " + PRINT_AD_BY_CATEGORY + " for PRINT_AD_BY_CATEGORY");
        System.out.println("Please input " + PRINT_ALL_ADS_SORT_BY_TITLE + " for PRINT_ALL_ADS_SORT_BY_TITLE");
        System.out.println("please input " + PRINT_ALL_ADS_SORT_BY_DATE + " for PRINT_ALL_ADS_DORT_BY_DATE");
        System.out.println("Please input " + DELETE_MY_ALL_ADS + " for DELETE_MY_ALL_ADS");
        System.out.println("Please input " + DELETE_AD_BY_ID + " for DELETE_AD_BY_ID");
        System.out.println("Please input " + IMPORT_ITEM+ " for IMPORT_ITEM");
        System.out.println("Please input " + EXPORT_ITEM+ " for EXPORT_ITEM");
    }

}
