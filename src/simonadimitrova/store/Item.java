package simonadimitrova.store;

import java.time.Duration;

public class Item extends Entity {
    private String name;
    private double price;
    private Duration expiry;

    public Item(int id, String name, double price, Duration expiry) {
        super(id);
        this.name = name;
        this.price = price;
        this.expiry = expiry;
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

    public Duration getExpiry() {
        return expiry;
    }

    public void setDuration(Duration expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return String.format("Item #%d %s %.2f BGN expiration %d days",
                id, name, price, expiry.toDays());
    }
}
