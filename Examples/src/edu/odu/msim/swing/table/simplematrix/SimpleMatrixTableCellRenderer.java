package edu.odu.msim.swing.table.simplematrix;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Logger;

public class SimpleMatrixTableCellRenderer extends JLabel implements TableCellRenderer
{  
	private static final long serialVersionUID = 1L;

	// logger
	private static Logger log = Logger.getLogger(SimpleMatrixTableCellRenderer.class.getName());
	
	public enum AlgorithmStep { NONE, STEP0A, STEP0B, STEP1, STEP2, STEP3, STEP4 }
	
	private int targetRowCell;
	private int targetColumnCell;
	
	private AlgorithmStep stepType;
	private boolean isTargetCell;
	
	public SimpleMatrixTableCellRenderer()  
	{  
		this.setHorizontalAlignment(JLabel.CENTER);
		
		// must do this for background to show up.
		this.setOpaque(true); 
		
		this.targetRowCell = -1;  
		this.targetColumnCell = -1;
		
		this.stepType = AlgorithmStep.NONE;
		this.isTargetCell = false;
	}  

	public Component getTableCellRendererComponent(JTable table, Object value, 
		boolean isSelected, boolean hasFocus, int row, int column)  
	{  
		// highlight if selected
		this.highlightSelection(table, isSelected);
		
		// highlight selected target for each steps
		this.highlightSelectTarget(table, row, column);
				
		// set the text
		this.setText(String.valueOf(value));  
		
		return this;  
	}
	
	private void highlightSelectTarget(JTable table, int row, int column)
	{
		if (this.stepType == AlgorithmStep.NONE)
		{
			this.performNone(table, row, column);
		}
		else if (this.stepType == AlgorithmStep.STEP0A)
		{
			this.performStep0A(table, row, column);
		}
		else if (this.stepType == AlgorithmStep.STEP0B)
		{
			this.performStep0B(table, row, column);
		}
		else if (this.stepType == AlgorithmStep.STEP1)
		{
			this.performStep1(table, row, column);
		}
		else if (this.stepType == AlgorithmStep.STEP2)
		{
			this.performStep2(table, row, column);
		}
		else if (this.stepType == AlgorithmStep.STEP3)
		{
			this.performStep3(table, row, column);
		}
		else if (this.stepType == AlgorithmStep.STEP4)
		{
			this.performStep4(table, row, column);
		}
	}
	
	private void setTargetCellBackground(JTable table)
	{
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(BorderFactory.createLineBorder(Color.red));  
		this.setFont(table.getFont().deriveFont(Font.BOLD));  
	}
	
	private void setCellBackground(JTable table)
	{
		this.setBackground(Color.YELLOW);
	}
	
	private void resetTargetCellBackground(JTable table)
	{
		this.setBackground(table.getBackground());
		this.setBorder(null);  
		this.setFont(table.getFont());
	}
	
	public void setTargetCell(int row, int col, AlgorithmStep stepType)  
	{  
		this.targetRowCell = row;  
		this.targetColumnCell = col;
		this.stepType = stepType;
		this.isTargetCell = false;
	}
	
	public void setTargetCell(int row, int col, AlgorithmStep stepType, boolean isTargetCell)  
	{  
		this.targetRowCell = row;  
		this.targetColumnCell = col;
		this.stepType = stepType;
		this.isTargetCell = isTargetCell;
	}

	private void highlightSelection(JTable table, boolean isSelected)
	{
		if (isSelected)  
		{  
			this.setBackground(table.getSelectionBackground());  
			this.setForeground(table.getSelectionForeground());  
		}  
		else  
		{  
			this.setBackground(table.getBackground());  
			this.setForeground(table.getForeground());  
		}
	}
	
//	private void highlightSelection(JTable table, int row, int column)
//	{
//		if (table instanceof SimpleMatrixTable)
//		{
//			for (CellEntry entry : ((SimpleMatrixTable)table).getWorkerToJobAssignment())
//			{
//				if (row == entry.getRowIndex() && column == entry.getColumnIndex() && this.isTargetCell)
//				{
//					this.setBorder(null);  
//					this.setFont(table.getFont());
//					setTargetCellBackground(table);
//				}
//			}
//		}
//	}
	
	private void highlightSelectedRows(JTable table, int row)
	{
		if (table instanceof SimpleMatrixTable)
		{
			for (Integer rowIndex : ((SimpleMatrixTable)table).getLocationToRowHighlight())
			{
				if (rowIndex == row)
				{
					this.setBorder(null);  
					this.setFont(table.getFont());
					setCellBackground(table);
				}
			}
		}
	}
	
	private void highlightSelectedColumns(JTable table, int column)
	{
		if (table instanceof SimpleMatrixTable)
		{
			for (Integer columnIndex : ((SimpleMatrixTable)table).getLocationToColumnHighlight())
			{
				if (columnIndex == column)
				{
					
					this.setBorder(null);  
					this.setFont(table.getFont());
					setCellBackground(table);
				}
			}
		}
	}
	
	private void performNone(JTable table, int row, int column)
	{
		if (row == this.targetRowCell && column == this.targetColumnCell)
		{
			setTargetCellBackground(table);
		}
		else
		{
			resetTargetCellBackground(table);
		}
	}
	
	private void performStep0A(JTable table, int row, int column)
	{
		
	}
	
	private void performStep0B(JTable table, int row, int column)
	{
		if (row == this.targetRowCell && column == this.targetColumnCell)
		{
			setTargetCellBackground(table);
		}
		else
		{
			resetTargetCellBackground(table);				
		}		
	}
	
	private void performStep1(JTable table, int row, int column)
	{
		if (row == this.targetRowCell && column == this.targetColumnCell)
		{
			setTargetCellBackground(table);
		}
		else
		{
			if (row == this.targetRowCell)
			{
				this.setBorder(null);  
				this.setFont(table.getFont());
				setCellBackground(table);
			}
			else
			{
				resetTargetCellBackground(table);
			}				
		}
	}
	
	private void performStep2(JTable table, int row, int column)
	{
		if (row == this.targetRowCell && column == this.targetColumnCell)
		{
			setTargetCellBackground(table);
		}
		else
		{
			if (column == this.targetColumnCell)
			{
				this.setBorder(null);  
				this.setFont(table.getFont());
				setCellBackground(table);
			}
			else
			{
				resetTargetCellBackground(table);
			}	
		}
	}
	
	private void performStep3(JTable table, int row, int column)
	{
		if (row == this.targetRowCell && column == this.targetColumnCell && this.isTargetCell)
		{
			log.debug("performStep3 - row: " + row + ", column: " + column + ", isTargetCell: " + this.isTargetCell);
		
			setTargetCellBackground(table);
		}
		else
		{			
			log.debug("performStep3 - row: " + row + ", column: " + column);
			this.setBorder(null);  
			this.setFont(table.getFont());

			// highlight selected row
			this.highlightSelectedRows(table, row);
				
			// highlight selected column
			this.highlightSelectedColumns(table, column);
		}
	}
	
	private void performStep4(JTable table, int row, int column)
	{
		if (row == this.targetRowCell && column == this.targetColumnCell && this.isTargetCell)
		{
			log.debug("performStep4 - row: " + row + ", column: " + column + ", isTargetCell: " + this.isTargetCell);
		
			setTargetCellBackground(table);
		}
		else
		{			
			log.debug("performStep4 - row: " + row + ", column: " + column);
			this.setBorder(null);  
			this.setFont(table.getFont());
			
			//this.highlightSelection(table, row, column);
		}
	}

}
