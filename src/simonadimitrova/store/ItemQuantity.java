package simonadimitrova.store;

public class ItemQuantity {
    private final Item item;
    private double count;

    public ItemQuantity(Item item) {
        this(item, 0);
    }

    public ItemQuantity(Item item, double count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        if (count < 0) {
            throw new IllegalArgumentException("Not enough quantity of " + item.getName());
        }
    }

    public void add(double count) {
        setCount(this.count + count);
    }

    public void remove(double count) {
        setCount(this.count - count);
    }

    public double getPrice() {
        return item.getPrice() * count;
    }

    @Override
    public String toString() {
        return String.format(
                "%s (#%d) %.2f: %f x %.2f",
                item.getName(), item.getId(), getPrice(),
                count, item.getPrice()
        );
    }
}
