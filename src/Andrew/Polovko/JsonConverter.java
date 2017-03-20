package Andrew.Polovko;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JsonConverter implements JsonSerializer<Shop>, JsonDeserializer<Shop> {
    public JsonElement serialize(Shop shop, Type type,
                                 JsonSerializationContext context) {
        JsonArray equipment = new JsonArray();
        JsonObject object = null;
        Map<SportEquipment, Integer> goods = shop.getGoods();
        for (Map.Entry<SportEquipment, Integer> entry : goods.entrySet()) {
            object = new JsonObject();
            object.addProperty("category", entry.getKey().getCategory().toString());
            object.addProperty("title", entry.getKey().getTitle());
            object.addProperty("price", entry.getKey().getPrice());
            object.addProperty("amount", entry.getValue());
            equipment.add(object);
        }
        return equipment;
    }

    public Shop deserialize(JsonElement json, Type type,
                            JsonDeserializationContext context) throws JsonParseException {
        Shop shop = new Shop();
        Map<SportEquipment, Integer> goods = new HashMap<>();
        SportEquipment tmpSE = null;
        JsonObject object = null;

        JsonArray equipment = json.getAsJsonArray();
        for (JsonElement jsonElement : equipment) {
            object = jsonElement.getAsJsonObject();
            tmpSE = new SportEquipment(
                    SportEquipment.Category.valueOf(object.get("category").getAsString()),
                    object.get("title").getAsString(),
                    object.get("price").getAsInt());
            goods.put(tmpSE, object.get("amount").getAsInt());
        }
        shop.setGoods(goods);
        return shop;
    }
}