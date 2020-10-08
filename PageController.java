package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import application.Project.Food;
import application.Project.RecipeBook;
import application.Project.Store;
import application.Project.XMLLoader;
import application.Project.XMLSaver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class PageController implements Initializable{

	@FXML
	private GridPane mainPage;
	
	@FXML
	private TableView<RecipeBook> bookTable;
	
	@FXML
	private TableView<Food> foodTable;
	
	@FXML
	private Button saveDbButton;
	
	@FXML
	private Button backToBooksButton;
	
	@FXML
	private TextField newBookName;
	
	@FXML
	private Button createBookButton;
	
	@FXML
	private Label newBookNameLable;
	
	@FXML
	private ChoiceBox<String> foodTypeDropDownList;
	
	@FXML
	private Button createNewFoodButton;
	
	@FXML
	private TextField newFoodName;
	
	@FXML
	private Label newFoodNameLable;
	
	@FXML
	private TextField newSecondMealSpices;
	@FXML
	private Label newSecondMealSpicesLabel;
	
	@FXML
	private TextField newFoodTimeToPrepare;
	@FXML
	private Label newFoodTimeToPrepareLabel;
	
	@FXML
	private TextField newFoodIngredients;
	@FXML
	private Label newFoodIngredientsLabel;

	@FXML
	private ChoiceBox<String> newFoodServeCold;
	@FXML
	private Label newFoodServeColdLabel;

	@FXML
	private ChoiceBox<String> newSecondMealDesserNeedToCook;
	@FXML
	private Label newSecondMealDesserNeedToCookLabel;

	@FXML
	private Button addFoodButton;
	
	
	XMLLoader l = new XMLLoader();
	XMLSaver s = new XMLSaver();
	
	private ObservableList<RecipeBook> data;
	private Store store;
	private int currentBookIndex;
	
	public PageController() {
		try {
			store = l.loadFromFile("src\\application\\ProjectData\\data.xml");
			data = FXCollections.observableArrayList(store.getListOfRecipeBooks());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TableColumn<RecipeBook, String> col1 = new TableColumn<>("Book ID");
		col1.setMinWidth(100);
		col1.setCellFactory(TextFieldTableCell.forTableColumn());
		col1.setCellValueFactory(new PropertyValueFactory<RecipeBook, String>("id"));
		
		col1.setEditable(false);
		
		TableColumn<RecipeBook, String> col2 = new TableColumn<>("Book Title");
		col2.setMinWidth(200);
		col2.setCellFactory(TextFieldTableCell.forTableColumn());
		col2.setCellValueFactory(new PropertyValueFactory<RecipeBook, String>("name"));
		
		col2.setEditable(true);
		
		col2.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<RecipeBook, String>>() {
					@Override
					public void handle(TableColumn.CellEditEvent<RecipeBook, String> event) {
						( (RecipeBook) event.getTableView().getItems().get(
								event.getTablePosition().getRow())
								).setName(event.getNewValue());
					}
				}
		);
		
		TableColumn<RecipeBook ,Void> col3 = new TableColumn<>(" ");
		Callback<TableColumn<RecipeBook ,Void>, TableCell<RecipeBook, Void>> cellFactory1 = 
				new Callback<TableColumn<RecipeBook ,Void>, TableCell<RecipeBook, Void>>(){
					@Override
					public TableCell<RecipeBook, Void> call(TableColumn<RecipeBook, Void> arg0) {
						final TableCell<RecipeBook, Void> cell = new TableCell<RecipeBook, Void>() {
							private final Button button = new Button("Delete book");
							
							{
								button.setOnAction((ActionEvent event) -> {
									RecipeBook book = getTableView().getItems().get(getIndex());
		                            store.getListOfRecipeBooks().remove(book);
		                            
		                            getTableView().getItems().remove(getIndex());
		                            System.out.println("Selected data (" + book.getId() + ") was deleted.\n");
		                            
		                            System.out.println("The books stored currently:");
		                            
		                            if(store.getListOfRecipeBooks().size() == 0)
			                            System.out.println("\tNo books found.");
		                            
		                            for(var b : store.getListOfRecipeBooks()) {
			                            System.out.println("\t- " +b.getId() + ": " + b.getName());
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

		TableColumn<RecipeBook ,Void> col4 = new TableColumn<>(" ");
		Callback<TableColumn<RecipeBook ,Void>, TableCell<RecipeBook, Void>> cellFactory2 = 
				new Callback<TableColumn<RecipeBook ,Void>, TableCell<RecipeBook, Void>>(){
					@Override
					public TableCell<RecipeBook, Void> call(TableColumn<RecipeBook, Void> arg0) {
						final TableCell<RecipeBook, Void> cell = new TableCell<RecipeBook, Void>() {
							private final Button button = new Button("Show book's foods");
							
							{
								button.setOnAction((ActionEvent event) -> {
									currentBookIndex = getIndex();
		                            RecipeBook book = getTableView().getItems().get(currentBookIndex);
		                            System.out.println("Selected data (" + book.getId() + ").\n");
		                            
		                            FoodMaintainer foodMaintainer = 
		                            		new FoodMaintainer(book ,bookTable, 
		                            				foodTable, saveDbButton, backToBooksButton,
		                            				newBookName, createBookButton, newBookNameLable,
		                            				foodTypeDropDownList, createNewFoodButton);

		                            foodMaintainer.loadTable();
		                            foodMaintainer.showFoodTable();
		                            	
		                            
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
		
		
		if(bookTable.getColumns().addAll(col1, col2, col3, col4)) {
			System.out.println("Table's cells created successfully.\n\n");
		}
		bookTable.setItems(data);
	}
	
	public void goBackToBooks() {
		System.out.println("Book table showed.");
		
		this.bookTable.setVisible(true);
		this.foodTable.setVisible(false);
		this.saveDbButton.setVisible(true);
		this.backToBooksButton.setVisible(false);
		this.createBookButton.setVisible(true);
		this.newBookName.setVisible(true);
		this.newBookNameLable.setVisible(true);
		this.foodTypeDropDownList.setVisible(false);
		this.createNewFoodButton.setVisible(false);
	}

	public void saveToDatabase() {
		s.saveToFile("src\\application\\ProjectData\\data.xml", store);
		
		//TODO pop up success window
		
		System.out.println("Saving was successfull");
	}
	
	public void createNewBook() {
		try {
			RecipeBook newBook = new RecipeBook(newBookName.getText());
			store.addRecipeBook(newBook);
			
			//refresh table with the new data
			data.clear();
			data.addAll(store.getListOfRecipeBooks());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addInputPanelsForNewFood() {
		//value of our choice from the drop-down list
		foodTypeDropDownList.setVisible(false);
		createNewFoodButton.setVisible(false);
		
		newFoodName.setVisible(true);
		newFoodNameLable.setVisible(true);
		
		newFoodServeCold.getItems().clear();
		newFoodServeCold.getItems().addAll("True", "False");
		newFoodServeCold.setValue("True");
		newFoodServeCold.setVisible(true);
		newFoodServeColdLabel.setVisible(true);
		
		newFoodIngredients.setVisible(true);
		newFoodIngredientsLabel.setVisible(true);
		
		newFoodTimeToPrepare.setVisible(true);
		newFoodTimeToPrepareLabel.setVisible(true);
		
		addFoodButton.setVisible(true);
		
		String choice = foodTypeDropDownList.getValue().toString();
		if(choice.equals("Appetizer")) {
			
		}else if(choice.equals("Dessert")) {
			newSecondMealDesserNeedToCook.getItems().clear();
			newSecondMealDesserNeedToCook.getItems().addAll("True", "False");
			newSecondMealDesserNeedToCook.setValue("True");
			newSecondMealDesserNeedToCook.setVisible(true);
			newSecondMealDesserNeedToCookLabel.setVisible(true);
		}else {
			newSecondMealSpices.setVisible(true);
			newSecondMealSpicesLabel.setVisible(true);
			
			newSecondMealDesserNeedToCook.getItems().clear();
			newSecondMealDesserNeedToCook.getItems().addAll("True", "False");
			newSecondMealDesserNeedToCook.setValue("True");
			newSecondMealDesserNeedToCook.setVisible(true);
			newSecondMealDesserNeedToCookLabel.setVisible(true);
		}
	}
	
	public void addCreatedFoodToList() {
		foodTypeDropDownList.setVisible(true);
		createNewFoodButton.setVisible(true);

		newFoodName.setVisible(false);
		newFoodNameLable.setVisible(false);
		
		newFoodServeCold.setVisible(false);
		newFoodServeColdLabel.setVisible(false);
		
		newFoodIngredients.setVisible(false);
		newFoodIngredientsLabel.setVisible(false);
		
		newFoodTimeToPrepare.setVisible(false);
		newFoodTimeToPrepareLabel.setVisible(false);
		
		newSecondMealSpices.setVisible(false);
		newSecondMealSpicesLabel.setVisible(false);
		
		newSecondMealDesserNeedToCook.setVisible(false);
		newSecondMealDesserNeedToCookLabel.setVisible(false);
		
		addFoodButton.setVisible(false);
		
		String choice = foodTypeDropDownList.getValue().toString();
		String nameOfFood = newFoodName.getText();
		String serveCold = newFoodServeCold.getValue().toString();
		ArrayList<String> listOfIngredients;
		String timeToPrepare = newFoodTimeToPrepare.getText();
		
		
		String[] ingredients = newFoodIngredients.getText().split(",");
		listOfIngredients = new ArrayList<String>(Arrays.asList(ingredients));
		
		Food food = null;
		
		if(choice.equals("Appetizer")) {
			System.out.println("Creating Appetizer...");
			
			food = bookTable.getItems().get(currentBookIndex).createAppetizerJavaFX(nameOfFood, serveCold, listOfIngredients, timeToPrepare);
			
		}else if(choice.equals("Dessert")) {
			System.out.println("Creating Dessert...");
			
			String needToCook = newSecondMealDesserNeedToCook.getValue();
			
			food = bookTable.getItems().get(currentBookIndex).createDessertJavaFX(nameOfFood, serveCold, listOfIngredients, timeToPrepare, needToCook);
			
		}else {
			System.out.println("Creating Second Meal...");
			
			String needToCook = newSecondMealDesserNeedToCook.getValue();
			ArrayList<String> listOfSpices;
			
			String[] spices = newSecondMealSpices.getText().split(",");
			listOfSpices = new ArrayList<String>(Arrays.asList(spices));
			
			food = bookTable.getItems().get(currentBookIndex).createSecondMealJavaFX(nameOfFood, serveCold, listOfIngredients, timeToPrepare, needToCook, listOfSpices);
			
		}
		bookTable.getItems().get(currentBookIndex).addFood(food);
		
		ObservableList<Food> foodData = FXCollections.observableArrayList(
				bookTable.getItems().get(currentBookIndex).getListOfFoods());
		
		foodData.clear();
		foodData.addAll(bookTable.getItems().get(currentBookIndex).getListOfFoods());
		
		foodTable.setItems(foodData);
	}
}
