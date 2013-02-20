package assign1;

import java.util.Random;

public class MinesweeperGrid 
{
	final int SIZE = 10;
	boolean _mineLocations[][];
	private boolean _gameOver = false;
	enum CellState{
		CHANGABLE, SEALED, EXPOSED;
	}
	CellState _cellState[][];

	public MinesweeperGrid() 
	{
		_mineLocations = new boolean[SIZE][SIZE];
		_cellState = new CellState[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++)
			{
				_cellState[i][j] = CellState.CHANGABLE;
			}
	}

	public boolean exposeCell(int row, int column) 
	{
		if(_cellState[row][column] == CellState.EXPOSED || _cellState[row][column] == CellState.SEALED)
			return false;

		_cellState[row][column] = CellState.EXPOSED;

		if(_mineLocations[row][column])
		{
			_gameOver  = true;
			return true; 
		}

		return true;			
	}


	public boolean sealCell(int row, int column) 
	{
		if(_cellState[row][column] == CellState.CHANGABLE)
		{
			_cellState[row][column] = CellState.SEALED;
			return true;
		}
		return false;
	}

	public boolean unSealCell(int row, int column) 
	{
		if(_cellState[row][column] == CellState.SEALED)
		{
			_cellState[row][column]=CellState.CHANGABLE;
			return true;
		}

		return false;
	}

	public boolean isGameOver() 
	{
		if(isGameWin())
			_gameOver=true;
		return _gameOver;
	}

	void setMineAt(int row, int column) 
	{
		_mineLocations[row][column] = true;
	}

	public int getAdjacentCount(int row, int column)
	{
		int adjacentCount = 0;
		if(!_mineLocations[row][column])
		for(int i = row-1; i < row+2; i++)
			for(int j = column-1; j < column+2; j++)
			{
				if(i == row && j == column)
					continue;
				if(i>=0 && i<SIZE && j>=0 && j<SIZE  && _mineLocations[i][j])
					adjacentCount += 1;
			}

		return adjacentCount;
	}

	public boolean exposeNeighboringCells(int row, int column)
	{
		if(getAdjacentCount(row, column) != 0)
			return false;
		for(int i = row-1; i < row+2; i++)
			for(int j = column-1; j < column+2; j++)
			{
				if(i == row && j == column)continue;
				if(i < SIZE && i >=0 && j < SIZE && j >=0 && _cellState[i][j] == CellState.CHANGABLE)
				{
					_cellState[i][j] = CellState.EXPOSED;
					exposeNeighboringCells(i, j);
				}
			}
		return true;
	}

	public char displayCell(int row, int column)
	{
		if(_mineLocations[row][column] && _cellState[row][column] == CellState.EXPOSED)
			return '*';
		if(_cellState[row][column]==CellState.SEALED)
			return 's';
		if(getAdjacentCount(row, column)==0 && _cellState[row][column] == CellState.EXPOSED)
			return ' ';	
		if(getAdjacentCount(row, column)!=0 && _cellState[row][column] == CellState.EXPOSED)
		return (char) ('0'+getAdjacentCount(row, column));
		
		return 'u';
	}
	
	public boolean isGameWin()
	{
		for(int i = 0; i < SIZE; i++)
			for(int  j = 0; j < SIZE; j++)
			{
				if(_mineLocations[i][j] && _cellState[i][j] != CellState.SEALED)
					return false;
				if(!_mineLocations[i][j] && _cellState[i][j] != CellState.EXPOSED)
					return false;
			}
		return true;
	}

	public boolean placeMines()
	{
		Random rand = new Random();
		int row, column;
		for(int i = 0;i < SIZE; )
		{
			row = rand.nextInt(SIZE);
			column = rand.nextInt(SIZE);
			if(!_mineLocations[row][column])
			{
				setMineAt(row, column);
				i++;
			}	
		}
		return true;
	}

	
}