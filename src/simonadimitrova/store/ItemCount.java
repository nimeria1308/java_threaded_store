package simonadimitrova.store;

public class ItemCount {
    private final Item item;
    private final double count;

    public ItemCount(Item item, double count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public double getCount() {
        return count;
    }

    public double getPrice() {
        return item.getPrice() * count;
    }

    @Override
    public String toString() {
        return String.format(
                "        %f x %.2f\n" +
                        "%s (#%d) %.2f",
                count, item.getPrice(),
                item.getName(), item.getId(), getPrice()
        );
    }
}
