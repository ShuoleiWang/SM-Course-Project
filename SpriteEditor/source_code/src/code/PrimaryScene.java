package code;

/* 
 *   You must run this java file at first to start Sprite Editor    
 */

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PrimaryScene extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
 
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 25, 25, 25));
		
 
		Text scenetitle = new Text("Please enter grid's width and height:");
		scenetitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
 
		Label width = new Label("Width:");
		grid.add(width, 0, 1);
 
		TextField widthTextField = new TextField();
		grid.add(widthTextField, 1, 1);
		
		Label height = new Label("Height:");
		grid.add(height, 0, 2);
 
		TextField heightTextField = new TextField();
		grid.add(heightTextField, 1, 2);
		
		Button button=new Button("Submit");
		//
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(button);
		grid.add(hbBtn, 1, 4);
		//
		button.setCursor(Cursor.HAND);
		button.setOnAction(e->{
			Main open=new Main();
			
			try
			{
				String w = widthTextField.getText();//.toString();
				int w1 = Integer.parseInt(w);				
				Main.width = w1;
				//System.out.println("the input width is: "+w1);
				
				String h = heightTextField.getText();//.toString();
				int h1 = Integer.parseInt(h);				
				Main.height = h1;
				//System.out.println("the input height is: "+h1);
				
				open.start(new Stage());
				primaryStage.hide(); 
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
		
		Scene scene = new Scene(grid, 400, 275);
		primaryStage.setScene(scene);
 
		primaryStage.show();
	}
}