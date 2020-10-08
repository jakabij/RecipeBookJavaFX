package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Project.Food;
import application.Project.RecipeBook;
import application.Project.Store;
import application.Project.XMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
	
	XMLLoader l = new XMLLoader();
	private ArrayList<RecipeBook> recipebooks; 
	private ObservableList<RecipeBook> data;
	
	public PageController() {
		try {
			Store s = l.loadFromFile("src\\application\\ProjectData\\data.xml");
			recipebooks = s.getListOfRecipeBooks();
			data = FXCollections.observableArrayList(recipebooks);
			
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
		                            recipebooks.remove(book);
		                            
		                            getTableView().getItems().remove(getIndex());
		                            System.out.println("Selected data (" + book.getId() + ") was deleted.\n");
		                            
		                            System.out.println("The books stored currently:");
		                            
		                            if(recipebooks.size() == 0)
			                            System.out.println("\tNo books found.");
		                            
		                            for(var b : recipebooks) {
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
		                            RecipeBook book = getTableView().getItems().get(getIndex());
		                            System.out.println("Selected data (" + book.getId() + ").\n");
		                            
		                            FoodMaintainer foodMaintainer = 
		                            		new FoodMaintainer(book.getListOfFoods() ,bookTable, 
		                            				foodTable, saveDbButton, backToBooksButton);

		                            foodMaintainer.showFoodTable();
		                            foodMaintainer.loadTable();	
		                            
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
		//TODO the back
		System.out.println("Book table showed.");
		this.bookTable.setVisible(true);
		this.foodTable.setVisible(false);
		this.saveDbButton.setVisible(true);
		this.backToBooksButton.setVisible(false);
	}

}
