package application.Project;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class Food {
    private SimpleStringProperty id;
    private SimpleStringProperty nameOfFood;
    private boolean serveCold;
    private ArrayList<String> listOfIngredients;

    public Food() { }
    public Food(String id, String nameOfFood, boolean serveCold, ArrayList<String> listOfingredients)
    {
        this.id = new SimpleStringProperty(id);
        this.nameOfFood = new SimpleStringProperty(nameOfFood);
        this.serveCold = serveCold;
        this.listOfIngredients = listOfingredients;
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public String getNameOfFood() {
        return nameOfFood.get();
    }

    public void setNameOfFood(String nameOfFood) {
        this.nameOfFood = new SimpleStringProperty(nameOfFood);
    }

    public boolean isServeCold() {
        return serveCold;
    }

    public void setServeCold(boolean serveCold) {
        this.serveCold = serveCold;
    }

    public ArrayList<String> getListOfIngredients() {
        return listOfIngredients;
    }

    public void setListOfIngredients(ArrayList<String> listOfIngredients) {
        this.listOfIngredients = listOfIngredients;
    }

    
}
