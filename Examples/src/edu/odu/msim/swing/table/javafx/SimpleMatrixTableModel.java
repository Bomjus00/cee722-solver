package edu.odu.msim.swing.table.javafx;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;

import javax.swing.table.AbstractTableModel;

import org.ejml.simple.SimpleMatrix;

/**
 * SampleTableModel
 */
public class SimpleMatrixTableModel extends AbstractTableModel 
{
	private static final long serialVersionUID = 1L;

	private static ObservableList<BarChart.Series> bcData;
    
    private String[] columnNames;
 
    private Object[][] data;

    private int rows;
    private int columns;
    
    public SimpleMatrixTableModel(SimpleMatrix sm, String[] columnNames)
    {
    	this.rows = sm.numRows();
    	this.columns = sm.numCols();
    	
    	this.data = new Object[this.rows][this.columns];
    	for (int row = 0; row < this.rows; row++)
    	{
    		for (int column = 0; column < this.columns; column++)
    		{
    			Double value = new Double(sm.get(row, column));
    			this.data[row][column] = value;
    		}
    	}
    	
    	this.columnNames = columnNames;
    }
    
    public double getTickUnit() 
    {
        return 1000;
    }
    
    public List<String> getColumnNames() 
    {
        return Arrays.asList(this.columnNames);
    }

    @Override
    public int getRowCount() 
    {
        return this.data.length;
    }

    @Override
    public int getColumnCount() 
    {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) 
    {
        return this.data[row][column];
    }

    @Override
    public String getColumnName(int column) 
    {
        return this.columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int column) 
    {
        return getValueAt(0, column).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column) 
    {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int column) 
    {
        if (value instanceof Double) 
        {
        	this.data[row][column] = (Double)value;
        }

        fireTableCellUpdated(row, column);
    }

    public ObservableList<BarChart.Series> getBarChartData() 
    {
        if (this.bcData == null) 
        {
        	this.bcData = FXCollections.<BarChart.Series>observableArrayList();
            for (int row = 0; row < getRowCount(); row++) 
            {
                ObservableList<BarChart.Data> series = FXCollections.<BarChart.Data>observableArrayList();
                for (int column = 0; column < getColumnCount(); column++) 
                {
                    series.add(new BarChart.Data(getColumnName(column), getValueAt(row, column)));
                }
                
                this.bcData.add(new BarChart.Series(series));
            }
        }
        
        return this.bcData;
    }
}