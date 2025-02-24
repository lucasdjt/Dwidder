package dao;

import java.util.List;
import dto.Ingredient;

public interface DAOIngredient {
    List<Ingredient> findAll();

    Ingredient findById(int id);

    void save(Ingredient ingredient);
}
