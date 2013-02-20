package assign1.ui;

import assign1.MinesweeperGrid;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MinesweeperGUI extends Application
{
	private MinesweeperGrid _minesweeperGrid;
	private Button[][] buttonArray;
	private IntegerProperty timer = new SimpleIntegerProperty(15);
	private Label timerLabel = new Label();
	private Timeline timeline;
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{	
		_minesweeperGrid = new MinesweeperGrid();
		_minesweeperGrid.placeMines();
		buttonArray = new Button[10][10];
		GridPane boardGrid = new GridPane();

		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
				initializeButton(i, j, boardGrid);

		initializeTimer(boardGrid);
		initializeScene(primaryStage, boardGrid);
	}

	private void initializeScene(Stage primaryStage, GridPane boardGrid)
	{
		Scene scene = new Scene(boardGrid, boardGrid.getMaxWidth(),boardGrid.getMaxHeight() , Color.DIMGREY);
		primaryStage.setTitle("Minesweeper");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void initializeTimer(GridPane boardGrid)
	{
		Label timeLabel = new Label("Timer");
		timerLabel.textProperty().bind(timer.asString());
		timerLabel.setTextFill(Color.BLACK);
		timeLabel.setTextFill(Color.BLACK);
		timeLabel.setFont(new Font("Lucidia Sans Unicode", 20));
		timerLabel.setFont(new Font("Lucidia Sans Unicode", 20));
		boardGrid.add(timeLabel, 12, 8);
		boardGrid.add(timerLabel, 12, 9);
	}


	private void initializeButton(int i, int j, GridPane grid) 
	{
		buttonArray[i][j] = new Button();
		buttonArray[i][j].autosize();
		buttonArray[i][j].setFocusTraversable(false);
		buttonArray[i][j].setMinSize(30, 30);
		timer.set(0);
		buttonArray[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) 
			{
				onClick(event);
				timeline = new Timeline();
				timeline.getKeyFrames().add(
						new KeyFrame(Duration.seconds(Integer.MAX_VALUE),
								new KeyValue(timer, Integer.MAX_VALUE)));
				timeline.playFromStart();
			}		
		});
		grid.add(buttonArray[i][j], j, i);	
	}

	private void onClick(MouseEvent event)
	{
		int row = GridPane.getRowIndex((Node) event.getSource());
		int column = GridPane.getColumnIndex((Node) event.getSource());
		if(event.getButton() == MouseButton.PRIMARY)
		{
			_minesweeperGrid.exposeCell(row, column);
			onExpose(row, column);
		}
		if(event.getButton() == MouseButton.SECONDARY)
			toggleSeal(row, column);

		if(_minesweeperGrid.isGameOver())
		{
			timerLabel.setVisible(false);
			showResult();
		}
	}					

	private void onExpose(int row, int column) 
	{
		if(_minesweeperGrid.displayCell(row, column) == '*')
			onMineExposed();

		if(_minesweeperGrid.getAdjacentCount(row, column) != 0 
				&& _minesweeperGrid.displayCell(row, column) != 's')
		{
			buttonArray[row][column].setText(""+_minesweeperGrid.displayCell(row, column));
			buttonArray[row][column].setDisable(true);
		}

		if(_minesweeperGrid.displayCell(row, column) == ' ')
			onEmptyCellExposed(row, column);
	}

	private void onEmptyCellExposed(int row, int column) 
	{
		_minesweeperGrid.exposeNeighboringCells(row, column);
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
				if(_minesweeperGrid.displayCell(i, j) != '*' && _minesweeperGrid.displayCell(i, j) != 's' 
				&& _minesweeperGrid.displayCell(i, j) != 'u')
				{
					buttonArray[i][j].setText(""+_minesweeperGrid.displayCell(i, j));
					buttonArray[i][j].setDisable(true);
				}
	}

	private void onMineExposed() 
	{
		for(int i=0; i<10;i++)
			for(int j=0;j<10;j++)
			{
				_minesweeperGrid.exposeCell(i, j);
				if(_minesweeperGrid.displayCell(i, j) == '*')
					buttonArray[i][j].setText(""+_minesweeperGrid.displayCell(i, j));

				buttonArray[i][j].setDisable(true);	
			}
	}

	private void toggleSeal(int row, int column) 
	{
		if(_minesweeperGrid.displayCell(row, column) == 'u')
		{
			_minesweeperGrid.sealCell(row, column);
			buttonArray[row][column].setText(""+_minesweeperGrid.displayCell(row, column));
		}
		else
		{
			_minesweeperGrid.unSealCell(row, column);
			buttonArray[row][column].setText(" ");
		}
	}

	private void showResult() 
	{
		final Stage myDialog = new Stage();
		myDialog.initModality(Modality.WINDOW_MODAL);
		Button okButton = new Button("CLOSE");
		okButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				myDialog.close();
				Platform.exit();
			}	
		});

		showGameResult(myDialog, okButton);
	}

	void showGameResult(final Stage myDialog, Button okButton)
	{
		Scene myDialogScene;
		String gameResult = getGameResult();

		myDialogScene = new Scene(VBoxBuilder.create()
				.children(new Text(gameResult), okButton)
				.alignment(Pos.CENTER)
				.padding(new Insets(25))
				.build());

		myDialog.setTitle("Message");
		myDialog.setWidth(200);
		myDialog.setScene(myDialogScene);
		myDialog.show();
	}

	private String getGameResult()
	{
		if(_minesweeperGrid.isGameWin())
			return "Yuppie!, you won!!!\n Time Taken: "+timer.getValue()+" Seconds";
		return "Hard luck!!!...You Lose!\n";
	}

}
