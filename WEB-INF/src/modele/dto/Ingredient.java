package dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Ingredient {
    private int id;
    private String name;

    @JsonAlias({"price", "prix"})
    private int price;

    public Ingredient(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Ingredient(){}

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for price
    public int getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(int price) {
        this.price = price;
    }
}