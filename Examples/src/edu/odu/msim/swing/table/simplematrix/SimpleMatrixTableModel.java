package edu.odu.msim.swing.table.simplematrix;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.ejml.simple.SimpleMatrix;

public class SimpleMatrixTableModel extends AbstractTableModel 
{
	private static final long serialVersionUID = 1L;

	// logger
	private static Logger log = Logger.getLogger(SimpleMatrixTableModel.class.getName());
	
	private ArrayList<Object[]> cache;
	private int columnCount;
	private SimpleMatrix matrix;
	private boolean isEditable = true;
	
	public SimpleMatrixTableModel(SimpleMatrix matrix) 
	{
		this.cache = new ArrayList<Object[]>();
		this.matrix = matrix;
		
		this.updateTableCache(matrix);

		// I think we're supposed to do this, but am uncertain
		this.fireTableChanged(null);
	}
	
	public void setIsEditable(boolean isEditable)
	{
		this.isEditable = isEditable;
	}
	
	public SimpleMatrix getSimpleMatrix()
	{
		return this.matrix;
	}

	public void updateTableCache(SimpleMatrix matrix)
	{
		// clear the table cache
		this.cache.clear();		
		this.columnCount = matrix.numCols()+1;

		//
		for (int row = 0; row < matrix.numRows(); row++)
		{			
			Object[] data = new Object[columnCount];
			for (int col = 0; col <= matrix.numCols(); col++)
			{
				if (col == 0)
				{
					data[col] = new String("Worker " + (row + 1));
				}
				else
				{
					data[col] = matrix.get(row, col-1);
				}
			}

			this.cache.add(data);
		}

		this.fireTableDataChanged();
	}

	public void setValueAt(Object value, int row, int column)
	{		
		if (column == 0)
		{
			return;
		}

		log.debug("setValueAt[" + row + ", " + column + "]: " + value);
		
		(this.cache.get(row))[column] = value;
		Double val = new Double(value.toString());
		this.matrix.set(row, column-1, val);
		
		this.fireTableCellUpdated(row, column);
	}

	public Object getValueAt(int row, int column) 
	{			
		if (column == 0)
		{
			return new String("Worker " + (row + 1));
		}

		return ((this.cache.get(row))[column]);
	}

	@Override
	public boolean isCellEditable(int row, int column) 
	{
		if (column == 0)
		{
			return false;
		}

		return isEditable;
	}


	public boolean isEmpty()
	{
		return (cache.size() == 0);
	}

	@Override
	public int getRowCount()
	{
		return cache.size();
	}

	@Override
	public int getColumnCount()
	{
		return columnCount;
	}

	public String getColumnName(int column) 
	{
		return (column == 0) ? "" : "Job " + column;
	}

	public void removeRow(int row)
	{
		this.cache.remove(row);
		fireTableRowsDeleted(row, row);
	}   

	public void removeAllRow()
	{
		if (this.cache.isEmpty())
		{
			return;
		}

		int firstIndex = 0;      
		int lastIndex = this.cache.size()-1;
		this.cache.clear();

		fireTableRowsDeleted(firstIndex, lastIndex);
	}
	
	public Class<?> getColumnClass(int col)  
    {  
        Object o = this.getValueAt(0, col);  
        if (o == null)  
        {
        	return Object.class;  
        }
        else  
        {
        	return o.getClass();  
        }
    }
	
}
