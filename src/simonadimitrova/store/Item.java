package simonadimitrova.store;

public class Item {
    private static int ID = 0;

    private final int id;
    private String name;
    private double price;
    private int expiry; // in days

    public Item(String name, double price, int expiry) {
        this(ID++, name, price, expiry);
    }

    public Item(int id, String name, double price, int expiry) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expiry = expiry;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getExpiry() {
        return expiry;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return String.format("Item #%d %s %.2f BGN expiration %d days",
                id, name, price, expiry);
    }
}
