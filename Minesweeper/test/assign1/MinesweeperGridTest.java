package assign1;

import java.util.Arrays;

import org.junit.Test;

import assign1.MinesweeperGrid.CellState;
import junit.framework.TestCase;

public class MinesweeperGridTest extends TestCase 
{
	private MinesweeperGrid _grid;

	protected void setUp()
	{
		_grid = new MinesweeperGrid();
	}

	public void testCanary() 
	{
		assertTrue(true);
	}

	public void testExposeCell()
	{
		assertTrue(_grid.exposeCell(1, 2));
	}

	public void testExposeAnExposedCell()
	{
		_grid.exposeCell(1, 2);
		assertFalse(_grid.exposeCell(1, 2));
	}

	public void testSealACellAtPosition()
	{
		assertTrue(_grid.sealCell(2, 3));
	}

	public void testUnSealACellAtPosition()
	{
		_grid.sealCell(2, 3);
		assertTrue(_grid.unSealCell(2, 3));
	}

	public void testGameOverOnCreate()
	{
		assertFalse(_grid.isGameOver());
	}

	@Test
	public void testSealAnExposedCell()
	{
		_grid.exposeCell(1, 2);
		assertFalse(_grid.sealCell(1, 2));
	}

	@Test
	public void testUnsealAnExposedCell()
	{	
		_grid.exposeCell(1, 2);
		assertFalse(_grid.unSealCell(1, 2));
	}

	@Test
	public void testSealASealedCell()
	{
		_grid.sealCell(1, 2);
		assertFalse(_grid.sealCell(1, 2));
	}

	@Test
	public void testUnsealAnUnsealedCell()
	{
		assertFalse(_grid.unSealCell(1, 2));
	}

	@Test
	public void testExposeASealedCell()
	{
		_grid.sealCell(1, 2);
		assertFalse(_grid.exposeCell(1, 2));	
	}

	@Test
	public void testGameLose()
	{
		_grid.setMineAt(1, 2);
		_grid.exposeCell(1, 2);
		assertTrue(_grid.isGameOver());
	}

	@Test
	public void testGetAdjacentCellCountWithNoNeighbourMine()
	{
		assertEquals(0, _grid.getAdjacentCount(8, 9));
	}

	@Test
	public void testGetAdjacentCellCountWithOneNeighbourMine()
	{
		_grid.setMineAt(8, 8);
		assertEquals(1, _grid.getAdjacentCount(7, 8));
	}

	@Test
	public void testGetAdjacentCellCountWithTwoNeighbourMines()
	{
		_grid.setMineAt(8, 8);
		_grid.setMineAt(7, 9);
		assertEquals(2, _grid.getAdjacentCount(7, 8));
	}

	@Test
	public void testGetAdjacentCellCountWithThreeNeighbourMines()
	{
		_grid.setMineAt(8, 8);
		_grid.setMineAt(7, 9);
		_grid.setMineAt(7, 7);
		assertEquals(3, _grid.getAdjacentCount(7, 8));
	}

	@Test
	public void testGetAdjacentCellCountWithFourNeighbourMines()
	{
		_grid.setMineAt(8, 8);
		_grid.setMineAt(7, 9);
		_grid.setMineAt(7, 7);
		_grid.setMineAt(8, 7);
		assertEquals(4, _grid.getAdjacentCount(7, 8));
	}

	@Test
	public void testGetAdjacentCellCountWithFiveNeighbourMines()
	{
		_grid.setMineAt(8, 8);
		_grid.setMineAt(7, 9);
		_grid.setMineAt(7, 7);
		_grid.setMineAt(8, 7);
		_grid.setMineAt(8, 9);
		assertEquals(5, _grid.getAdjacentCount(7, 8));
	}

	@Test
	public void testGetAdjacentCellCountWithSixNeighbourMines()
	{
		_grid.setMineAt(8, 8);
		_grid.setMineAt(7, 9);
		_grid.setMineAt(7, 7);
		_grid.setMineAt(8, 7);
		_grid.setMineAt(8, 9);
		_grid.setMineAt(6, 7);
		assertEquals(6, _grid.getAdjacentCount(7, 8));
	}

	@Test
	public void testGetAdjacentCellCountWithSevenNeighbourMines()
	{
		_grid.setMineAt(8, 8);
		_grid.setMineAt(7, 9);
		_grid.setMineAt(7, 7);
		_grid.setMineAt(8, 7);
		_grid.setMineAt(8, 9);
		_grid.setMineAt(6, 7);
		_grid.setMineAt(6, 8);
		assertEquals(7, _grid.getAdjacentCount(7, 8));
	}

	@Test
	public void testGetAdjacentCellCountWithEightNeighbourMines()
	{
		_grid.setMineAt(8, 8);
		_grid.setMineAt(7, 9);
		_grid.setMineAt(7, 7);
		_grid.setMineAt(8, 7);
		_grid.setMineAt(8, 9);
		_grid.setMineAt(6, 7);
		_grid.setMineAt(6, 8);
		_grid.setMineAt(6, 9);
		assertEquals(8, _grid.getAdjacentCount(7, 8));
	}

	@Test
	public void testGetAdjacentCellCountAtTheBoundary()
	{
		_grid.setMineAt(0, 8);
		assertEquals(1, _grid.getAdjacentCount(0, 9));
	}

	@Test
	public void testExposeNeighbouringEmptyCellsAfterExposingEmptyCell()
	{
		_grid.exposeCell(4, 5);
		_grid.exposeNeighboringCells(4, 5);
		for(int i = 3; i < 6; i++)
			for(int j = 4; j < 7; j++)
			{
				if(i == 4 && j == 5)continue;
				assertEquals(CellState.EXPOSED, _grid._cellState[i][j]);
			}
	}

	@Test
	public void testExposeNeighbouringEmptyCellsNextToAnExposedEmptyCell()
	{
		_grid.exposeCell(4, 5);
		_grid.exposeNeighboringCells(4, 5);
		assertEquals(CellState.EXPOSED, _grid._cellState[1][2]);
	}

	@Test
	public void testExposeAdjacentCellNeighbouringAnExposedEmptyCell()
	{
		_grid.setMineAt(2, 2);
		_grid.exposeCell(2, 4);
		_grid.exposeNeighboringCells(2, 4);
		assertEquals(CellState.EXPOSED, _grid._cellState[2][3]);
	}

	@Test
	public void testExposeNeighbouringCellsNextToAnExposedAdjacentCell()
	{
		_grid.setMineAt(2, 2);
		_grid.exposeCell(2, 3);
		assertFalse(_grid.exposeNeighboringCells(2, 3));
	}

	@Test
	public void testDisplayCountOnExposingAdjacentCell()
	{
		_grid.setMineAt(1, 2);
		_grid.setMineAt(1, 4);
		_grid.exposeCell(1, 3);
		assertEquals('2', _grid.displayCell(1,3));

	}

	@Test
	public void testDisplayOnExposingEmptyCell()
	{
		_grid.exposeCell(1, 2);
		assertEquals(' ',_grid.displayCell(1, 2));
	}

	@Test
	public void testDisplayOnExposingMineCell()
	{
		_grid.setMineAt(1, 2);
		_grid.exposeCell(1, 2);
		assertEquals('*', _grid.displayCell(1, 2));
	}
	
	@Test
	public void testDisplayAdjacentCountOnExposeMine()
	{
		_grid.setMineAt(1, 2);
		_grid.setMineAt(1, 3);
		assertEquals(0,_grid.getAdjacentCount(1, 3));
	}
	
	@Test
	public void testDisplayOnSealCell()
	{
		_grid.sealCell(1, 2);
		assertEquals('s', _grid.displayCell(1, 2));
	}
	
	@Test
	public void testDisplayUnexposedCell()
	{
		assertEquals('u', _grid.displayCell(1, 2));
	}
		
	
	@Test
	public void testGameWinOnNotAllMinesSealed()
	{
		for(int i = 0; i < 10; i++)
			_grid.setMineAt(9, i);
		
		_grid.sealCell(9, 1);
		
		assertFalse(_grid.isGameWin());
		
	}
	
	@Test
	public void testGameWinOnAllMineCellsSealed()
	{
		for(int i = 0; i < 10; i++)
			_grid.setMineAt(9, i);
		
		for(int i = 0; i < 10; i++)
			_grid.sealCell(9, i);
		
		_grid.exposeCell(1, 2);
		_grid.exposeNeighboringCells(1, 2);
		assertTrue(_grid.isGameWin());
	}
	
		
	@Test
	public void testGameWinOnAllNonMineCellsSealed()
	{
		_grid.setMineAt(2, 3);
		_grid.sealCell(2, 3);
		_grid.sealCell(2, 4);
		_grid.exposeCell(8, 8);
		_grid.exposeNeighboringCells(8, 8);
		assertFalse(_grid.isGameWin());
		
	}
	
	@Test
	public void testGameWinOnNotAllCellsExposed()
	{
		for(int i = 0; i < 10; i++)
		{
			_grid.setMineAt(9, i);
			_grid.sealCell(9, i);
		}
		_grid.exposeCell(8, 1);
		assertFalse(_grid.isGameWin());	
	}

	@Test
	public void testGameWin()
	{
		for(int i = 0; i < 10; i++)
			_grid.setMineAt(9, i);
		
		for(int i = 0; i < 10; i++)
			_grid.sealCell(9, i);
		
		_grid.exposeCell(1, 2);
		_grid.exposeNeighboringCells(1, 2);
		assertTrue(_grid.isGameWin());	
	}

	@Test
	public void testGameOverOnGameWin()
	{
		for(int i = 0; i < 10; i++)
			_grid.setMineAt(9, i);

		for(int i = 0; i < 10; i++)
			_grid.sealCell(9, i);

		_grid.exposeCell(1, 2);
		_grid.exposeNeighboringCells(1, 2);
		assertTrue(_grid.isGameOver());	
	}

	@Test
	public void testPlaceMinesOnGameGrid()
	{
		assertTrue(_grid.placeMines());
	}
	
	@Test
	public void testTenMinesArePlacedOnGameGrid()
	{
		_grid.placeMines();
		int countMines = 0;
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++)
				if(_grid._mineLocations[i][j]) countMines++;
		assertEquals(10, countMines);
	}
	@Test
	public void testSetMinesOnGridOnGameLoad()
	{
		int countMines = 0;
		_grid.placeMines();
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++)
				if(_grid._mineLocations[i][j]) countMines++;
		assertEquals(10,countMines);
	}
	
	@Test
	public void testDuplicateLoadHasSameMinePositions()
	{
		MinesweeperGrid _nextGrid = new MinesweeperGrid();
		_grid.placeMines();
		assertFalse(Arrays.deepEquals(_grid._mineLocations, _nextGrid._mineLocations));
	}
}
