package edu.odu.msim.swing.table.simplematrix;

import java.awt.Color;
import java.awt.Component;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import edu.odu.msim.swing.table.celleditor.SelectingCellEditor;

public class SimpleMatrixTable extends JTable
{
	private static final long serialVersionUID = 1L;
	private Border outside = new MatteBorder(1, 1, 1, 0, Color.blue);
	private Border inside = new EmptyBorder(0, 1, 0, 1);
	private Border highlight = new CompoundBorder(outside, inside);
	private boolean isRowOffset = false;
	
	private Set<Integer> rowHighlightList;
	private Set<Integer> columnHighlightList;
		
	public SimpleMatrixTable(TableModel dm)
	{
		super(dm);
		
		TableColumnModel model = super.getColumnModel();
		for (int i = 0; i < super.getColumnCount(); i++) 
		{
			TableColumn tc = model.getColumn(i);
			SelectingCellEditor cellEditor = new SelectingCellEditor(new JTextField());
			cellEditor.setClickCountToStart(1);
						
			tc.setCellEditor(cellEditor);
		}
		

		this.rowHighlightList = new LinkedHashSet<Integer>();
		this.columnHighlightList = new LinkedHashSet<Integer>();
		
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	
	public SimpleMatrixTable(TableModel dm, boolean isRowOffset)
	{
		super(dm);
		
		this.isRowOffset = isRowOffset;

		TableColumnModel model = super.getColumnModel();
		for (int i = 0; i < super.getColumnCount(); i++) 
		{
			TableColumn tc = model.getColumn(i);
			
			SelectingCellEditor cellEditor = new SelectingCellEditor(new JTextField());
			cellEditor.setClickCountToStart(1);
			
			tc.setCellEditor(cellEditor);
		}
		
		this.rowHighlightList = new LinkedHashSet<Integer>();
		this.columnHighlightList = new LinkedHashSet<Integer>();
		
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) 
	{
		Component comp = super.prepareRenderer(renderer, row, column);
		JComponent jcomp = (JComponent)comp;

		// update the row header background
		if (column == 0)
		{
			jcomp.setBackground(new Color(238, 238, 238));

			return jcomp;
		}
		
		if (this.isRowOffset)
		{
			// update offsetting rows
			return this.updateRowOffsetBackground(row, column, comp);
		}

		return comp;
	}
	
	private Component updateRowOffsetBackground(int row, int column, Component comp)
	{
		JComponent jcomp = (JComponent)comp;

		//even index, selected or not selected
		if (row % 2 == 0 && !isCellSelected(row, column)) 
		{
			jcomp.setBackground(Color.lightGray);
		} 
		else 
		{
			jcomp.setBackground(Color.white);
		}

		if (isRowSelected(row))
		{
			jcomp.setBorder(highlight);
		}
		
		return jcomp;
	}
	
	public void setLocationToRowHighlight(Set<Integer> rowHighlightList)
	{
		this.rowHighlightList = rowHighlightList;
	}
	
	public Set<Integer> getLocationToRowHighlight()
	{
		return this.rowHighlightList;
	}

	public void setLocationToColumnHighlight(Set<Integer> columnHighlightList)
	{
		this.columnHighlightList = columnHighlightList;
	}
	
	public Set<Integer> getLocationToColumnHighlight()
	{
		return this.columnHighlightList;
	}
	
	public void addLocationToRowHighlight(Integer row)
	{
		this.rowHighlightList.add(row);
	}
	
	public void clearLocationToRowHighlight()
	{
		this.rowHighlightList.clear();
	}
	
	public void addLocationToColumnHighlight(Integer column)
	{
		this.columnHighlightList.add(column);
	}
	
	public void clearLocationToColumnHighlight()
	{
		this.columnHighlightList.clear();
	}
}
