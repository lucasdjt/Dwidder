package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAOList implements DAOIngredient {
    private static List<Ingredient> ingredients = new ArrayList<>();
    static{
        ingredients.add(new Ingredient(1, "Poivrons", 20));
        ingredients.add(new Ingredient(2, "Chorizo", 3));
        ingredients.add(new Ingredient(3, "Lardons", 100));
    }

    @Override
    public List<Ingredient> findAll() {
        return new ArrayList<>(ingredients);
    }

    @Override
    public Ingredient findById(int id) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == id) {
                return ingredient;
            }
        }
        return null;
    }

    @Override
    public void save(Ingredient ingredient) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getId() == ingredient.getId()) {
                ingredients.set(i, ingredient);
                return;
            }
        }
        ingredients.add(ingredient);
    }
}