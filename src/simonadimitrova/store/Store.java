package simonadimitrova.store;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Store {
    private final String name;
    private final Map<Integer, ItemQuantity> items = new HashMap<>();
    private final Map<Integer, Cashier> cashiers = new HashMap<>();
    private final Map<Integer, CashRegister> cashRegisters = new HashMap<>();
    private final Map<Integer, Receipt> receipts = new HashMap<>();

    public Store(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public synchronized void addCashRegister(CashRegister cashRegister) {
        cashRegister.setStore(this);
        cashRegisters.put(cashRegister.getId(), cashRegister);
    }

    public synchronized void removeCashRegister(CashRegister cashRegister) throws InterruptedException {
        cashRegister.close();
        cashRegister.setStore(null);
        cashRegisters.remove(cashRegister.getId());
    }

    public synchronized CashRegister getCashRegister(int id) {
        return cashRegisters.get(id);
    }

    public synchronized Collection<CashRegister> getCashRegisters() {
        return cashRegisters.values();
    }

    public synchronized void addCashier(Cashier cashier) {
        cashiers.put(cashier.getId(), cashier);
    }

    public synchronized void removeCashier(Cashier cashier) throws InterruptedException {
        for (CashRegister cashRegister : cashRegisters.values()) {
            if (cashier.equals(cashRegister.getCashier())) {
                cashRegister.close();
            }
        }

        cashiers.remove(cashier.getId());
    }

    public synchronized Cashier getCashier(int id) {
        return cashiers.get(id);
    }

    public synchronized Collection<Cashier> getCashiers() {
        return cashiers.values();
    }

    public synchronized void addItem(Item item, double quantity) {
        if (!items.containsKey(item.getId())) {
            items.put(item.getId(), new ItemQuantity(item));
        }

        items.get(item.getId()).add(quantity);
    }

    public synchronized void removeItem(Item item) {
        items.remove(item.getId());
    }

    public synchronized void removeItem(Item item, double quantity) throws InsufficientItemQuantityException {
        items.get(item.getId()).remove(quantity);
    }

    public synchronized Item getItem(int id) {
        return items.get(id).getItem();
    }

    public synchronized ItemQuantity getItemQuantity(int id) {
        return items.get(id);
    }

    public synchronized Collection<ItemQuantity> getItemQuantities() {
        return items.values();
    }

    public synchronized void addReceipt(Receipt receipt) throws IOException {
        receipts.put(receipt.getId(), receipt);
        receipt.toFile(String.format("receipt-%d.bin", receipt.getId()));
    }

    public synchronized Receipt getReceipt(int id) {
        return receipts.get(id);
    }

    public synchronized Collection<Receipt> getReceipts() {
        return receipts.values();
    }

    public synchronized double getTotalRevenue() {
        double total = 0;
        for (Receipt receipt : receipts.values()) {
            total += receipt.getTotal();
        }
        return total;
    }


    @Override
    public synchronized String toString() {
        return String.format(
                "%s: %d items, %d cash registers, %d cashiers, %d receipts", name,
                items.size(), cashRegisters.size(), cashiers.size(), receipts.size());
    }
}
