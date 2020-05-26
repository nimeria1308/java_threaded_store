package simonadimitrova.store;

public class Cashier extends Entity {
    private static int ID = 0;

    private String name;

    public Cashier(String name) {
        this(ID++, name);
    }

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
