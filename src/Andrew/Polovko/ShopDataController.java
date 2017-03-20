package Andrew.Polovko;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class ShopDataController {
    private Shop shop = null;
    private Gson gson = null;

    public ShopDataController(Shop shop) {
        this.shop = shop;
    }

    public ShopDataController(String filename) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            StringBuilder json = new StringBuilder();

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                json.append(line);
            }

            if (gson == null) buildGson();
            String str = json.toString();
            this.shop = gson.fromJson(str, Shop.class);

        } catch (FileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveShopDataToFile(String filename) {
        if (shop != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

                if (gson == null) buildGson();
                String json = gson.toJson(shop);
                bw.write(json);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    private void buildGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Shop.class, new JsonConverter());
        builder.setPrettyPrinting();
        gson = builder.create();
    }
}
