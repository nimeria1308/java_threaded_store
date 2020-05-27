package simonadimitrova.store;

import java.io.*;
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

    public void toFile(String filename) throws IOException {
        try (OutputStream out = new FileOutputStream(filename)) {
            try (DataOutputStream dout = new DataOutputStream(out)) {
                dout.writeInt(id);
                dout.writeInt(cashier.getId());
                dout.writeLong(dateIssued.getTime());

                dout.writeInt(items.size());
                for (ItemQuantity item : items) {
                    dout.writeInt(item.getItem().getId());
                    dout.writeDouble(item.getQuantity());
                }
            }
        }
    }

    public static Receipt fromFile(String filename, Store store) throws IOException {
        try (InputStream in = new FileInputStream(filename)) {
            try (DataInputStream din = new DataInputStream(in)) {
                int id = din.readInt();
                Cashier cashier = store.getCashier(din.readInt());
                Date dateIssued = new Date(din.readLong());

                int count = din.readInt();
                List<ItemQuantity> items = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    items.add(new ItemQuantity(
                            store.getItem(din.readInt()),
                            din.readDouble()
                    ));
                }

                return new Receipt(id, cashier, dateIssued, items);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Receipt #").append(id).append(": ").append(items.size()).append(" items\n\n");

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
