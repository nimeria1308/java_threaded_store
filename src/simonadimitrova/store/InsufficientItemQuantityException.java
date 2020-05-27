package simonadimitrova.store;

public class InsufficientItemQuantityException extends StoreException {
    private final ItemQuantity itemQuantity;

    public InsufficientItemQuantityException(ItemQuantity itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public ItemQuantity getItemQuantity() {
        return itemQuantity;
    }

    @Override
    public String getMessage() {
        return String.format("Insufficient quantity %.2f of item %s",
                itemQuantity.getQuantity(), itemQuantity.getItem());
    }
}
