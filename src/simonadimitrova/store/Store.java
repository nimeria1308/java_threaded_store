package simonadimitrova.store;

import java.util.*;

public class Store {
    private final String name;
    private final Map<Item, Double> items = new HashMap<>();
    private final Set<Cashier> cashiers = new HashSet<>();
    private final Set<CashRegister> cashRegisters = new HashSet<>();
    private final Set<Receipt> receipts = new HashSet<>();

    public Store(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public synchronized double getTotalRevenue() {
        double total = 0;
        for (Receipt receipt : receipts) {
            total += receipt.getTotal();
        }
        return total;
    }

    public synchronized void addCashRegister(CashRegister cashRegister) {
        cashRegisters.add(cashRegister);
    }

    public synchronized void removeCashRegister(CashRegister cashRegister) throws InterruptedException {
        cashRegister.close();
        cashRegisters.remove(cashRegister);
    }

    public synchronized void addCashier(Cashier cashier) {
        cashiers.add(cashier);
    }

    public synchronized void removeCashier(Cashier cashier) throws InterruptedException {
        for (CashRegister cashRegister : cashRegisters) {
            if (cashier.equals(cashRegister.getCashier())) {
                cashRegister.close();
            }
        }

        cashiers.remove(cashier);
    }

    public synchronized void addItem(Item item, double count) {
        count += items.getOrDefault(item, 0.0);
        items.put(item, count);
    }

    public synchronized void removeItem(Item item) {
        items.remove(item);
    }

    public synchronized void removeItem(Item item, double count) {
        count = items.getOrDefault(item, 0.0) - count;
        if (count < 0) {
            throw new IllegalArgumentException("Not enough of " + item);
        }

        items.put(item, count);
    }

    @Override
    public String toString() {
        return String.format(
                "%s: %d items, %d cashiers, %d receipts");
    }
}
