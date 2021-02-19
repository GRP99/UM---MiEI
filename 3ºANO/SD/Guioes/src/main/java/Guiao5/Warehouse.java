package Guiao5;

import java.util.HashMap;

public class Warehouse {
    private HashMap<String, Item> stock;

    public Warehouse() {
        this.stock = new HashMap<String, Item>();
        stock.put("Item1", new Item());
        stock.put("Item2", new Item());
        stock.put("Item3", new Item());
    }

    public void supply(String item, int quantity) {
        this.stock.get(item).supply(quantity);
    }

    public void consume(String[] items) {
        for (String s : items) {
            System.out.println("Consumer: a consumir " + s);
            this.stock.get(s).consume();
            System.out.println("Consumer: consumi " + s);
        }
    }
}
