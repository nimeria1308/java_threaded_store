package simonadimitrova.store;

public class ItemQuantity {
    private final Item item;
    private double quantity;

    public ItemQuantity(Item item) {
        this(item, 0);
    }

    public ItemQuantity(Item item, double quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) throws InsufficientItemQuantityException {
        if (quantity < 0) {
            throw new InsufficientItemQuantityException(new ItemQuantity(item, -quantity));
        }

        this.quantity = quantity;
    }

    public void add(double quantity) {
        try {
            setQuantity(this.quantity + quantity);
        } catch (InsufficientItemQuantityException e) {
            // this should never happen (i.e. quantity shan't be negative
            throw new IllegalStateException(e);
        }
    }

    public void remove(double quantity) throws InsufficientItemQuantityException {
        setQuantity(this.quantity - quantity);
    }

    public double getPrice() {
        return item.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format(
                "%s (#%d) %.2f BGN: %.2f x %.2f BGN",
                item.getName(), item.getId(), getPrice(),
                quantity, item.getPrice()
        );
    }
}
