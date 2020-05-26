package simonadimitrova.store;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Receipt extends Entity {
    private final Cashier cashier;
    private final Date dateIssued;
    private final List<ItemQuantity> items;

    // we need this, as Receipts are created on separate threads
    private static int ID = 0;
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
        this.items = Collections.unmodifiableList(items);
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
        StringBuilder builder = new StringBuilder()
                .append("Receipt ---").append(id).append("\n\n");

                builder
                .append("  Cashier: ").append(cashier)
                .append("  Issued: ").append(dateIssued)
                ;
        return super.toString();
    }
}
