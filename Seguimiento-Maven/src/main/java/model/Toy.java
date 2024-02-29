package model;

import mapping.enums.ToyType;

public class Toy {
        private String name;
        private ToyType type;
        private double price;
        private int quantities;

    public Toy(String name, ToyType type, double price, int quantities) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantities = quantities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToyType getType() {
        return type;
    }

    public void setType(ToyType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantities() {
        return quantities;
    }

    public void setQuantities(int quantities) {
        this.quantities = quantities;
    }

    @Override
    public String toString() {
        return "Toy{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", quantities=" + quantities +
                '}';
    }
}

