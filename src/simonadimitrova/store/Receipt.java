package simonadimitrova.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Receipt extends Entity {
    private static int ID = 0;

    public static class Builder {
        private final Cashier cashier;
        private final List<ItemCount> items = new ArrayList<>();

        public Builder(Cashier cashier) {
            this.cashier = cashier;
        }

//        public void add(Merchandise merchandise, double )
    }

    private final Cashier cashier;
    private final Date dateIssued;
    private final List<ItemCount> items;

    private Receipt(Cashier cashier, Date dateIssued, List<ItemCount> items) {
        super(ID++);
        this.cashier = cashier;
        this.dateIssued = new Date();
        this.items = Collections.unmodifiableList(items);
    }

    public double getTotal() {
        double total = 0;
        for (ItemCount item : items) {
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
