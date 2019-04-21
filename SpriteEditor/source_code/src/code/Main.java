package code;

/* 
 *   You must run the "PrimaryScene" java file at first to start Sprite Editor    
 */

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.stage.FileChooser;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;



public class Main extends Application {
	static int sizeX;
	static int sizeY;
	
	static int width;
	static int height;
	
	static String filepath;
	static URL url;

	WritableImage temp;
	
    @Override
    public void start(Stage stage){
    	Scene scene = new Scene(new VBox());
    	VBox box = (VBox) scene.getRoot();
    	//System.out.println("width: "+width+" height: "+height);
    	
    	//MENU
    	MenuBar menuBar = new MenuBar();
    	box.getChildren().add(menuBar);////
    	Menu fileMenu = new Menu("File");
    	MenuItem openMenuItem = new MenuItem("Open");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(openMenuItem, saveMenuItem,
                new SeparatorMenuItem(), exitMenuItem);
        
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        
        Image image = new Image("/resource/o1.png");
        temp= new WritableImage(width,height);
    	
    	sizeX = (int)image.getWidth();
    	sizeY = (int)image.getHeight();
    	
    	PixelWriter pixelWriter = temp.getPixelWriter();
    	
        //ColorPicker
    	final ColorPicker colorpicker = new ColorPicker();
    	ToolBar tb = new ToolBar();
    	box.getChildren().add(tb);
    	tb.getItems().addAll(colorpicker);
    	
    	//BUTTON
    	ArrayList<Button> buttonlist = new ArrayList<Button>();
    	GridPane gridpane = new GridPane();
    	ScrollPane sp = new ScrollPane();

    	for(int x=0; x<width; x++) {
    		for(int y=0; y<height; y++) {
    			ButtonClass buttonclass = new ButtonClass();
    			buttonclass.setX(x);
    			buttonclass.setY(y);
        		buttonclass.getButton().setMinSize(30, 30);
				/////////new added
        		buttonclass.getButton().setStyle("-fx-background-radius: 0; -fx-border-radius: 0; -fx-border-color: transparent; "
        				+ "-fx-background-color: transparent;");
        		buttonlist.add(buttonclass.getButton());
        		buttonclass.getButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    	Color color = colorpicker.getValue();
                    	
                    	//pixel writer set the correspond position of button
                    	pixelWriter.setColor(buttonclass.getX(), buttonclass.getY(), color);//
                    	//****Testing code****
                    	//System.out.println("Event!");
                    	//System.out.println("color is " + color.getRed() + " "
                    	//+ color.getGreen()+" " + color.getBlue());
                    	
                    	String hex = String.format("#%02x%02x%02x", (int)(color.getRed()*255), 
                    			(int)(color.getGreen()*255), (int)(color.getBlue()*255)); 
                    	buttonclass.getButton().setStyle("-fx-background-color:" + hex +"; -fx-border-radius: 0;"
                    			+ " -fx-background-radius: 0; -fx-border-color:"  + hex);
                    }
        		});
        		gridpane.add(buttonlist.get(height*x+y), x, y);
    		}
    	}
    	
        //Open File Option
        FileChooser fileChooser = new FileChooser();
        openMenuItem.setOnAction(
        		(final ActionEvent e) -> {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        //openFile(file);
                        try {
                        url = file.toURI().toURL();
                        filepath = url.toString();
                		//System.out.println("URL:" + url.toString());
                		Image openedImage = new Image(filepath);
                		
                		if(openedImage.getHeight() > height || openedImage.getWidth() > width) {
                			System.out.println("The opened image is larger than your grid size");
                			System.out.println("The program will terminate");
                			System.exit(0);
                		}
                		
                		//Assign every pixel color in opened image to correspond button
                		PixelReader pixelReader = openedImage.getPixelReader();                		               		
                    	for(int readY=0; readY<openedImage.getHeight(); readY++){
                            for(int readX=0; readX<openedImage.getWidth(); readX++){
                                Color color = pixelReader.getColor(readX,readY);
                                pixelWriter.setColor(readX,readY,color);
                                
                                //Fill the button color
                                String hex = String.format("#%02x%02x%02x", (int)(color.getRed()*255), 
                            			(int)(color.getGreen()*255), (int)(color.getBlue()*255)); 
                            	buttonlist.get(width*readX+readY).setStyle("-fx-background-color:" + hex +"; -fx-border-radius: 0;"
                            			+ " -fx-background-radius: 0; -fx-border-color:"  + hex);
                            }
                    	}
                        } catch(IOException ioe) {} 
                    }
            });
        
    	//Save file option
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {       	
        	//close file option    
			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showSaveDialog(stage.getOwner());
				if (file != null) {
					try {
							ImageIO.write(SwingFXUtils.fromFXImage(temp, null), "png", file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
        
        //box.getChildren().add(sp);
    	box.getChildren().add(gridpane);
    	
    	stage.setScene(scene);
    	stage.show();     
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

class ButtonClass{
	public Button button = new Button("");
	public int x;
	public int y;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Button getButton() {
		return button;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setButton(Button button) {
		this.button = button;
	}
}