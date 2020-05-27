package simonadimitrova.store;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public final List<ItemQuantity> items = new ArrayList<>();

    public Client(ItemQuantity[] items) {
        for (ItemQuantity item : items) {
            this.items.add(item);
        }
    }
}
