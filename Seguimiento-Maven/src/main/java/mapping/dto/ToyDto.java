package mapping.dto;

import mapping.enums.ToyType;

public record ToyDto(String name, ToyType type, double price, int quantities) {
    @Override
    public String toString() {
        return "\nToyDto{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", quantities=" + quantities +
                '}';
    }
}
