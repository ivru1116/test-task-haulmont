package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.entity.Recipe;

import java.util.List;

public class RecipeService {

    private RecipeDao recipesDao = new RecipeDao();

    private Class<Recipe> recipeClass = Recipe.class;

    public Recipe findRecipe(int id) {
        return recipesDao.find(recipeClass, id);
    }

    public void saveRecipe(Recipe recipe) {
        recipesDao.save(recipe);
    }

    public void deleteRecipe(Recipe recipe) {
        recipesDao.delete(recipe);
    }

    public void updateRecipe(Recipe recipe) {
        recipesDao.update(recipe);
    }

    public List<Recipe> findAllRecipes() {
        return recipesDao.findAll(recipeClass);
    }
}
