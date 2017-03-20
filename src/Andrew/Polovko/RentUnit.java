package Andrew.Polovko;


import java.util.Arrays;

public class RentUnit {
    private SportEquipment[] units;

    public SportEquipment[] getUnits() {
        return units;
    }

    public void setUnits(SportEquipment[] units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "RentUnit{" +
                "units=" + Arrays.toString(units) +
                '}';
    }
}

