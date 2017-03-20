package Andrew.Polovko;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Shop implements Serializable {
    private Map<SportEquipment, Integer> goods = new HashMap<>();
    private Map<SportEquipment, Integer> rentedGoods = new HashMap<>();

    public Shop() {
    }

    public Shop(Map<SportEquipment, Integer> goods, Map<SportEquipment, Integer> rentedGoods) {
        this.goods = goods;
        this.rentedGoods = rentedGoods;
    }

    public void addGoods(SportEquipment equipment, int amount) {
        goods.merge(equipment, amount, Integer::sum);
    }

    public void rentGoods(RentUnit rentUnit) throws MyRentException {
        if (rentedGoods.size() + rentUnit.getUnits().length > 3)
            throw new MyRentException("\tERROR: You can't rent more than 3 units of equipment");
        for (SportEquipment equipment : rentUnit.getUnits()) {
            if (goods.get(equipment) != null) {
                if (rentedGoods.containsKey(equipment)) rentedGoods.put(equipment, rentedGoods.get(equipment) + 1);
                else
                    rentedGoods.put(equipment, 1);

                if (goods.get(equipment) > 1) goods.put(equipment, goods.get(equipment) - 1);
                else
                    goods.remove(equipment);
            } else
                throw new MyRentException("\tERROR: We don't have in store equipment you are trying to rent.");
        }

    }

    public String getGoodsAsString() {
        //Assuming that average map element as String will be about 20 symbols long.
        StringBuilder builder = new StringBuilder(goods.size() * 20);
        goods.forEach((k, v) -> builder.append(k.toString()).append("\tAvailable:\t").append(v).append('\n'));
        return builder.toString();
    }

    public String getRentedGoodsAsString() {
        StringBuilder builder = new StringBuilder(rentedGoods.size() * 20);
        rentedGoods.forEach((k, v) -> builder.append(k.toString()).append(v).append('\n')); //TODO fix text representation
        return builder.toString();
    }

    public Map<Integer, SportEquipment> getInfoForRent() {
        StringBuilder builder = new StringBuilder(goods.size() * 20);
        int currentEquipmentNumber = 1;
        Map<Integer, SportEquipment> map = new HashMap<>();
        for (Map.Entry<SportEquipment, Integer> entry : goods.entrySet()) {
            map.put(currentEquipmentNumber, entry.getKey());
            currentEquipmentNumber += 1;
        }
        return map;
    }


    public Map<SportEquipment, Integer> getGoods() {
        return goods;
    }

    public void setGoods(Map<SportEquipment, Integer> goods) {
        this.goods = goods;
    }

    public Map<SportEquipment, Integer> getRentedGoods() {
        return rentedGoods;
    }

    public void setRentedGoods(Map<SportEquipment, Integer> rentedGoods) {
        this.rentedGoods = rentedGoods;
    }
}
