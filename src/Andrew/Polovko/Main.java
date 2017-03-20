package Andrew.Polovko;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String FILENAME = "shopGoods.JSON";

    public static void main(String[] args) {
        ShopDataController shopDataController = null;

        try {
            shopDataController = new ShopDataController(FILENAME);
        } catch (FileNotFoundException e) {
            System.out.println("\tERROR: It looks like we can't find shop data file\n" +
                    "\tCreating new shop ...");
            shopDataController = new ShopDataController(new Shop());
        }

        Shop shop = shopDataController.getShop();


        Scanner menuScanner = new Scanner(System.in);
        while (true) {
            System.out.println("\tEnter command:\n" +
                    "1 - to Show goods in shop\n" +
                    "2 - to Show rented goods\n" +
                    "3 - to rent goods\n" +
                    "4 - to Exit\n");

            switch (menuScanner.nextInt()) {
                case 1:
                    System.out.println(shop.getGoodsAsString());
                    break;
                case 2:
                    System.out.println(shop.getRentedGoodsAsString());
                    break;
                case 3:
                    Map<Integer, SportEquipment> infoForRent = shop.getInfoForRent();
                    System.out.println(infoForRent);
                    System.out.println("Now enter numbers of items that you want to rent:\n");
                    Scanner rentScanner = new Scanner(System.in);
                    String rentInput = rentScanner.nextLine();
                    String[] numbers = rentInput.split(" ");
                    int[] numbersParsed = new int[numbers.length];

                    try {
                        for (int i = 0; i < numbers.length; i++) {
                            numbersParsed[i] = Integer.parseInt(numbers[i]);
                            if (numbersParsed[i] > shop.getGoods().size()) throw new Exception("Wrong input !");
                        }
                    } catch (Exception e) {
                        System.out.println("Wrong input !");
                        break;
                    }


                    RentUnit rentUnit = new RentUnit();
                    SportEquipment[] equipmentForRent = new SportEquipment[numbersParsed.length];

                    for (int i = 0; i < numbersParsed.length; i++) {
                        equipmentForRent[i] = infoForRent.get(numbersParsed[i]);
                    }
                    rentUnit.setUnits(equipmentForRent);
                    try {
                        shop.rentGoods(rentUnit);
                    } catch (MyRentException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("You successfully rented:");
                    System.out.println(rentUnit.toString());
                    break;
                case 4:
                    shopDataController.saveShopDataToFile(FILENAME);
                    return;
            }
        }
    }


}

