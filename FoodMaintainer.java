package application;

import java.util.ArrayList;

import application.Project.Appetizer;
import application.Project.Dessert;
import application.Project.Food;
import application.Project.RecipeBook;
import application.Project.SecondMeal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FoodMaintainer {
	private RecipeBook currentBook;
	private ObservableList<Food> data;
	private TableView<RecipeBook> bookTable;
	private TableView<Food> foodTable;
	private Button saveDbButton;
	private Button goBackToBooks;
	private TextField newBookName;
	private Button createBookButton;
	private Label newBookNameLable;
	private ChoiceBox<String> foodTypeDropDownList;
	private Button createNewFoodButton;
	
	public FoodMaintainer(RecipeBook currentBook, TableView<RecipeBook> bookTable,
			TableView<Food> foodTable, Button saveDbButton, Button goBackToBooks,
			TextField newBookName, Button createBookButton, Label newBookNameLable,
			ChoiceBox<String> foodTypeDropDownList, Button createNewFoodButton) {
		
		this.bookTable = bookTable;
		this.foodTable = foodTable;
		this.saveDbButton = saveDbButton;
		this.goBackToBooks = goBackToBooks;
		this.createBookButton = createBookButton;
		this.newBookName = newBookName;
		this.newBookNameLable = newBookNameLable;
		this.foodTypeDropDownList = foodTypeDropDownList;
		this.createNewFoodButton = createNewFoodButton;
		
		data = FXCollections.observableArrayList(currentBook.getListOfFoods());
	}
	
	public void showFoodTable() {
		System.out.println("Food table showed.");
		this.bookTable.setVisible(false);
		this.foodTable.setVisible(true);
		this.saveDbButton.setVisible(false);
		this.goBackToBooks.setVisible(true);
		this.createBookButton.setVisible(false);
		this.newBookName.setVisible(false);
		this.newBookNameLable.setVisible(false);
		this.foodTypeDropDownList.setVisible(true);
		
		this.foodTypeDropDownList.getItems().clear();
		this.foodTypeDropDownList.getItems().addAll("Appetizer", "Second Meal", "Dessert");
		this.foodTypeDropDownList.setValue("Appetizer");
		
		this.createNewFoodButton.setVisible(true);
	}
	
	public void loadTable() {
		TableColumn<Food, String> col1 = new TableColumn<>("Food ID");
		col1.setMinWidth(100);
		col1.setCellFactory(TextFieldTableCell.forTableColumn());
		col1.setCellValueFactory(new PropertyValueFactory<Food, String>("id"));
		
		col1.setEditable(false);
		
		
		
		TableColumn<Food, String> col2 = new TableColumn<>("Food Name");
		col2.setMinWidth(200);
		col2.setCellFactory(TextFieldTableCell.forTableColumn());
		col2.setCellValueFactory(new PropertyValueFactory<Food, String>("nameOfFood"));
		
		col2.setEditable(true);
		
		col2.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Food, String>>() {
					@Override
					public void handle(TableColumn.CellEditEvent<Food, String> event) {
						( (Food) event.getTableView().getItems().get(
								event.getTablePosition().getRow())
								).setNameOfFood(event.getNewValue());
					}
				}
		);
		
		
		
		TableColumn<Food ,Void> col3 = new TableColumn<>(" ");
		Callback<TableColumn<Food ,Void>, TableCell<Food, Void>> cellFactory1 = 
				new Callback<TableColumn<Food ,Void>, TableCell<Food, Void>>(){
					@Override
					public TableCell<Food, Void> call(TableColumn<Food, Void> arg0) {
						final TableCell<Food, Void> cell = new TableCell<Food, Void>() {
							private final Button button = new Button("Delete food");
							
							{
								button.setOnAction((ActionEvent event) -> {
									Food food = getTableView().getItems().get(getIndex());
									
									for(var c : currentBook.getListOfFoods()) {
										System.out.println("--------"+ c.getNameOfFood());
									}
//									currentBook.getListOfFoods().remove(food);
		                            
		                            getTableView().getItems().remove(getIndex());
		                            System.out.println("Selected data (" + food.getId() + ") was deleted.\n");
		                            
		                            System.out.println("The foods stored currently:");
		                            
		                            if(currentBook.getListOfFoods().size() == 0)
			                            System.out.println("\tNo books found.");
		                            
		                            for(var f : currentBook.getListOfFoods()) {
			                            System.out.println("\t- " +f.getId() + ": " + f.getNameOfFood());
		                            }
		                            
		                            System.out.println("\n");
		                        });
		                    }
	
		                    @Override
		                    public void updateItem(Void item, boolean empty) {
		                        super.updateItem(item, empty);
		                        if (empty) {
		                            setGraphic(null);
		                        } else {
		                            setGraphic(button);
		                        }
		                    }
						};
						return cell;
					}
			
		};
		col3.setCellFactory(cellFactory1);

		
		
		TableColumn<Food ,Void> col4 = new TableColumn<>(" ");
		Callback<TableColumn<Food ,Void>, TableCell<Food, Void>> cellFactory2 = 
				new Callback<TableColumn<Food ,Void>, TableCell<Food, Void>>(){
					@Override
					public TableCell<Food, Void> call(TableColumn<Food, Void> arg0) {
						final TableCell<Food, Void> cell = new TableCell<Food, Void>() {
							private final Button button = new Button("Show food's details");
							
							{
								//button that brings the food detail
								button.setOnAction((ActionEvent event) -> {
									Food food = getTableView().getItems().get(getIndex());
									
									String foodType = "Appetizer";
									if(food instanceof Appetizer)
										foodType = "Appetizer";
									else if(food instanceof SecondMeal)
										foodType = "Second Meal";
									else
										foodType = "Dessert";
									
									
		                            System.out.println("Selected data (" + food.getId() + ").\n");
		                            
		                            Stage popUpWindow = new Stage();
		                            
		                            GridPane gp = new GridPane();

		                            TextFlow tf1 = new TextFlow();
		                            
		                            TextField tfi1 = new TextField();
		                            tfi1.setText(food.getNameOfFood());
		                            tfi1.setEditable(false);
		                            TextField tfi2 = new TextField();
		                            tfi2.setText("It's a(n) " + foodType);
		                            tfi2.setEditable(false);
		                            
		                            tf1.getChildren().addAll(tfi1, tfi2);
		                            gp.getChildren().add(tf1);
		                            
		                            
		                            
		                            TextFlow tf2 = new TextFlow();
		                            
		                            tfi1 = new TextField();
		                            tfi1.setText("Food Ingredients");
		                            tfi1.setEditable(false);
		                           
		                            ArrayList<String> listOfIngredeients = food.getListOfIngredients();
									if(foodType.equals("Second Meal")) {
										SecondMeal secondMeal = (SecondMeal) food;
										listOfIngredeients.addAll(secondMeal.getListOfSpices());                          	
		                            }
									
		                            ListView<String> list = new ListView<String>();
		                            ObservableList<String> items =FXCollections.observableArrayList (listOfIngredeients);
		                            list.setItems(items);
		                            
		                            tf2.getChildren().addAll(tfi1,list);
		                            gp.addRow(1, tf2);
		                            
		                            
		                            
		                           //food details come here
		                            gp.addRow(2, createTextFlowForFoodTypes(foodType, food));
		                            
		                            //create pop up window as a scene
		                            Scene dialogScene = new Scene(gp, 500, 600);
		                            popUpWindow.setScene(dialogScene);
		                            popUpWindow.show();
		                            
		                            System.out.println("\n");
		                        });
							}
	
		                    @Override
		                    public void updateItem(Void item, boolean empty) {
		                        super.updateItem(item, empty);
		                        if (empty) {
		                            setGraphic(null);
		                        } else {
		                            setGraphic(button);
		                        }
		                    }
						};
						return cell;
					}
			
		};
		col4.setCellFactory(cellFactory2);
		
		foodTable.getColumns().clear();
		
		if(foodTable.getColumns().addAll(col1, col2, col3, col4)) {
			System.out.println("Table's cells created successfully.\n\n");
		}
		foodTable.setItems(data);
	}

	
	private TextFlow createTextFlowForFoodTypes(String foodType, Food food) {
		 TextFlow textFlow = new TextFlow();
         
		 if(foodType.equals("Appetizer")) {
			 Appetizer appetizer = (Appetizer) food;
			 
			 String timeToPrepare = "Time to prepare: " + appetizer.getTimeToPrepare().toString();
			 String serving = "Best to serve warm.";
			 if(appetizer.isServeCold()) {
				serving = "Best to serve cold.";
			 }
			 
			 TextField textField1 = new TextField();
	         textField1.setText(serving);
	         textField1.setEditable(false);
	         
	         TextField textField2 = new TextField();
	         textField2.setText(timeToPrepare);
	         textField2.setEditable(false);
	         
	         textFlow.getChildren().addAll(textField1, textField2);
	         
		 }else if(foodType.equals("Dessert")) {
			 Dessert dessert = (Dessert) food;
			 
			 String timeToPrepare = "Time to prepare: " + dessert.getTimeToPrepare().toString();
			 String serving = "Best to serve warm.";
			 if(dessert.isServeCold()) {
				serving = "Best to serve cold.";
			 }
			 
			 String toCook = "Doesn't need to cook it in the oven.";
			 if(dessert.isNeedToCook()) {
				 toCook = "Need to cook it in the oven.";
			 }
			 
			 TextField textField1 = new TextField();
	         textField1.setText(serving);
	         textField1.setEditable(false);
	         
	         TextField textField2 = new TextField();
	         textField2.setText(toCook);
	         textField2.setEditable(false);
	         
	         TextField textField3 = new TextField();
	         textField3.setText(timeToPrepare);
	         textField3.setEditable(false);
	         
	         textFlow.getChildren().addAll(textField1, textField2, textField3);
	         
		 }else {
			 SecondMeal secondMeal = (SecondMeal) food;
			 
			 String timeToPrepare = "Time to prepare: " + secondMeal.getTimeToPrepare().toString();
			 String toCook = "Doesn't need to cook it in the oven.";
			 if(secondMeal.isNeedToCook()) {
				 toCook = "Need to cook it in the oven.";
			 }
			 
			 TextField textField1 = new TextField();
	         textField1.setText(timeToPrepare);
	         textField1.setEditable(false);
	         
	         TextField textField2 = new TextField();
	         textField2.setText(toCook);
	         textField2.setEditable(false);
	         
	         textFlow.getChildren().addAll(textField1, textField2);
		 }
		 
         return textFlow;
	}
}
