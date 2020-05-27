package simonadimitrova.store;

import java.util.ArrayList;
import java.util.List;

public class InsufficientItemsException extends StoreException {
    private final List<ItemQuantity> items;

    public InsufficientItemsException(List<ItemQuantity> items) {
        this.items = new ArrayList<>(items);
    }

    public List<ItemQuantity> getItems() {
        return items;
    }

    @Override
    public String getMessage() {
        StringBuilder b = new StringBuilder();
        b.append(items.size()).append(" insufficient items: ");

        for (ItemQuantity item : items) {
            b.append(String.format("  insufficient quantity %.2f of item %s\n",
                    item.getQuantity(), item.getItem()));
        }

        return b.toString();
    }
}
