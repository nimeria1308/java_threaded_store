package simonadimitrova.store;

public class Cashier extends Entity {
    private String name;

    public Cashier(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Cashier #%d %s", id, name);
    }
}
