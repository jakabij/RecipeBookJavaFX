package application.Project;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.SimpleStringProperty;

public class RecipeBook {
    private UI ui = new UI();
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private ArrayList<Food> listOfFoods;

    public RecipeBook() throws Exception
    {
        this.id = new SimpleStringProperty(ui.idGenerator());
        this.listOfFoods = new ArrayList<>();
    }

    public RecipeBook(String name) throws Exception
    {
        this.id = new SimpleStringProperty(ui.idGenerator());
        this.name= new SimpleStringProperty(name);
        this.listOfFoods = new ArrayList<>();
    }

    public RecipeBook(String name, String id, ArrayList<Food> listOfFoods)
    {
        this.id = new SimpleStringProperty(id);
        this.name= new SimpleStringProperty(name);
        this.listOfFoods = listOfFoods;
    }

    public Food createFood(String typeOfFood, String nameOfFood,boolean serveCold, ArrayList<String> listOfIngredients) throws Exception
    {
        Food food;
            if(typeOfFood.toLowerCase().equals("1"))
            {
                food = createAppetizer(nameOfFood, serveCold, listOfIngredients);
            }
            else if(typeOfFood.toLowerCase().equals("2"))
            {
                food = createSecondMeal(nameOfFood, serveCold, listOfIngredients);
            }

            else if(typeOfFood.toLowerCase().equals("3"))
            {
                food = createDessert(nameOfFood, serveCold, listOfIngredients);
            }
            else
            {
                throw new Exception("NoFoodLikeThatException");
            }

            return food;
    }


    public Food createAppetizerJavaFX(String nameOfFood, String serveCold,
    		ArrayList<String> listOfIngredients, String timeToPrepare) {
    	
    	 String id = null;
		try {
			id = ui.idGenerator();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	 boolean isServeCold = false;
    	 if(serveCold.equals("True"))
    		 isServeCold = true;
    	 
    	 Time toPrepare = Time.valueOf(timeToPrepare);
    	 
    	 return new Appetizer(id, nameOfFood, isServeCold, listOfIngredients, toPrepare);
    }
    
    
    private Food createAppetizer(String nameOfFood, boolean serveCold, ArrayList<String> listOfIngredients) throws Exception
        {
            String id = ui.idGenerator();
            Time timeToPrepare;

            String userInput = ui.getInputFromUser("\nThe time to prepare it [in 0:0:0 format]: ");
            String[] timeAfterSplit = userInput.split(":");
            if (timeAfterSplit.length < 3 || timeAfterSplit.length > 3)
            {
                throw new Exception("ParseError");
            }
            else
            {
                int hoursIndex = 0;
                int minutesIndex = 1;
                int secondsIndex = 2;
                

                try
                {
                    int hours = Integer.parseInt(timeAfterSplit[hoursIndex]);
                    int minutes = Integer.parseInt(timeAfterSplit[minutesIndex]);
                    int seconds = Integer.parseInt(timeAfterSplit[secondsIndex]);
                    timeToPrepare = new Time(hours, minutes, seconds);
                }
                catch(Exception ex)
                {
                    throw new Exception("ParseError");
                }
            }
            return new Appetizer(id, nameOfFood, serveCold, listOfIngredients, timeToPrepare);
        }
    	public Food createSecondMealJavaFX(String nameOfFood, String serveCold,
        		ArrayList<String> listOfIngredients, String timeToPrepare, String needToCook, ArrayList<String> listOfSpices) {
    		
    		String id = null;
			try {
				id = ui.idGenerator();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		boolean isServeCold = false;
	       	if(serveCold.equals("True"))
	       		 isServeCold = true;
	       	 
	       	Time toPrepare = Time.valueOf(timeToPrepare);
	       	boolean isNeedToCook = false;
	       	if(needToCook.equals("True"))
	       		isNeedToCook = true;
    		
	       	return new SecondMeal(id, nameOfFood, isServeCold, listOfIngredients, isNeedToCook, toPrepare, listOfSpices);
	       	
    	}
    
        private Food createSecondMeal(String nameOfFood, boolean serveCold, ArrayList<String> listOfIngredients) throws Exception
        {
            String id = ui.idGenerator();
            String userInput = ui.getInputFromUser("\n[yes or no]\nNeed to cook: ");
            boolean needToCook;
            if (userInput.toLowerCase().equals("yes") || userInput.toLowerCase().equals("y"))
            {
                needToCook = true;
            }
            else if (userInput.toLowerCase().equals("no") || userInput.toLowerCase().equals("n"))
            {
                needToCook = false;
            }
            else
            {
                throw new Exception("ParseError");
            }


            Time timeToPrepare;
            userInput = ui.getInputFromUser("\nThe time to prepare it [in 0:0:0 format]: ");
            String[] inputSplitting = userInput.split(":");
            if (inputSplitting.length < 3 || inputSplitting.length > 3)
            {
                throw new Exception("ParseError");
            }
            else
            {
                int hoursIndex = 0;
                int minutesIndex = 1;
                int secondsIndex = 2;
                
                try
                {
                    int hours = Integer.parseInt(inputSplitting[hoursIndex]);
                    int minutes = Integer.parseInt(inputSplitting[minutesIndex]);
                    int seconds = Integer.parseInt(inputSplitting[secondsIndex]);
                    timeToPrepare = new Time(hours, minutes, seconds);
                }
                catch(Exception ex)
                {
                    throw new Exception("ParseError");
                }
            }

            userInput = ui.getInputFromUser("\nList of the spices separated by ',': ");
            inputSplitting = userInput.split(",");

            ArrayList<String> listOfSpices = new ArrayList<String>();
            Collections.addAll(listOfSpices, inputSplitting);
           
            String toCheck = "'~Ë‡+^!Ë�%Â°/Ë›=`Â´ËťÂ¨\\|â‚¬Ă·Ă—Ĺ‚Ĺ�$Ăź#&@<?;.:*";
            for(var spice : listOfSpices)
            {
                for (int i = 0; i < toCheck.length(); i++)
                {
                    if (spice.contains(""+toCheck.charAt(i)))
                        throw new Exception("InvalidAttribute");
                }
            }

            return new SecondMeal(id, nameOfFood, serveCold, listOfIngredients, needToCook, timeToPrepare, listOfSpices);
        }

        
        public Food createDessertJavaFX(String nameOfFood, String serveCold,
        		ArrayList<String> listOfIngredients, String timeToPrepare, String needToCook) {
        	
        	String id = null;
			try {
				id = ui.idGenerator();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		boolean isServeCold = false;
	       	if(serveCold.equals("True"))
	       		 isServeCold = true;
	       	 
	       	Time toPrepare = Time.valueOf(timeToPrepare);
	       	boolean isNeedToCook = false;
	       	if(needToCook.equals("True"))
	       		isNeedToCook = true;
    		
	       	return new Dessert(id, nameOfFood, isServeCold, listOfIngredients, isNeedToCook, toPrepare);
        }
        
        private Food createDessert(String nameOfFood, boolean serveCold, ArrayList<String> listOfIngredients) throws Exception
        {
            String id = ui.idGenerator();
            String userInput = ui.getInputFromUser("Need to cook it: ");
            boolean needToCook;
            if (userInput.toLowerCase().equals("yes") || userInput.toLowerCase().equals("y"))
            {
                needToCook = true;
            }
            else if (userInput.toLowerCase().equals("no") || userInput.toLowerCase().equals("n"))
            {
                needToCook = false;
            }
            else
            {
                throw new Exception("ParseError");
            }

            Time timeToPrepare;
            userInput = ui.getInputFromUser("The time to prepare it: ");
            String[] inputSplitting = userInput.split(":");
            if (inputSplitting.length < 3 || inputSplitting.length > 3)
            {
                throw new Exception("ParseError");
            }
            else
            {
                int hoursIndex = 0;
                int minutesIndex = 1;
                int secondsIndex = 2;
                
                try
                {
                    int hours = Integer.parseInt(inputSplitting[hoursIndex]);
                    int minutes = Integer.parseInt(inputSplitting[minutesIndex]);
                    int seconds = Integer.parseInt(inputSplitting[secondsIndex]);
                    timeToPrepare = new Time(hours, minutes, seconds);
                }
                catch(Exception ex)
                {
                    throw new Exception("ParseError");
                }
            }

            return new Dessert(id, nameOfFood, serveCold, listOfIngredients, needToCook, timeToPrepare);
        }



    public void addFood(Food food)
    {
        this.listOfFoods.add(food);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public ArrayList<Food> getListOfFoods() {
        return listOfFoods;
    }

    public void setListOfFoods(ArrayList<Food> listOfFoods) {
        this.listOfFoods = listOfFoods;
    }
}
