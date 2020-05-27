package simonadimitrova.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Receipt extends Entity {
    private final Cashier cashier;
    private final Date dateIssued;
    private final List<ItemQuantity> items;

    private static int ID = 0;

    // we need this, as receipts are created on separate threads
    private static final Object ID_LOCK = new Object();
    private static int generateID() {
        synchronized (ID_LOCK) {
            return ID++;
        }
    }

    public Receipt(Cashier cashier, Date dateIssued, List<ItemQuantity> items) {
        this(generateID(), cashier, dateIssued, items);
    }

    public Receipt(int id, Cashier cashier, Date dateIssued, List<ItemQuantity> items) {
        super(id);
        this.cashier = cashier;
        this.dateIssued = new Date();
        this.items = new ArrayList<>(items);
    }

    public Cashier getCashier() {
        return cashier;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public List<ItemQuantity> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (ItemQuantity item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
                builder
                        .append("Receipt #").append(id).append(": ").append(items.size()).append(" items\n\n");

                int i = 1;
                for (ItemQuantity item : items) {
                    builder.append("  ").append(i++).append(". ").append(item).append('\n');
                }
                builder.append('\n');

                builder.append(String.format("Total: %.2f BGN\n", getTotal()));

                builder.append("----------\n");
                builder.append("Cashier: ").append(cashier).append('\n');
                builder.append("Issued: ").append(dateIssued).append('\n');
        return builder.toString();
    }
}
