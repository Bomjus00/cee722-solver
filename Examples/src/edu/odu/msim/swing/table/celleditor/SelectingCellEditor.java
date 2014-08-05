package edu.odu.msim.swing.table.celleditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class SelectingCellEditor extends DefaultCellEditor 
{
	private static final long serialVersionUID = 1L;

	private Object value;

	public SelectingCellEditor(final JTextField textField) 
	{
		super(textField);

		textField.addFocusListener(new FocusAdapter()
		{
			public void focusGained(final FocusEvent e)
			{
				textField.selectAll();
			}
		});
	}

	public Object getCellEditorValue()  
	{  
		String cellEntry = "";
		try  
		{  
			cellEntry = super.getCellEditorValue().toString();
			Double result = Double.parseDouble(cellEntry);  
			
			return result;  
		}  
		catch (NumberFormatException e)  
		{  
			System.out.println(cellEntry + " is not a valid entry.\n\nEnter a double value.");

			return value;  // restore the original value  
		}  
	}

	public Component getTableCellEditorComponent(JTable table, Object value,  
			boolean isSelected, int row, int column)  
	{  
		this.value = value;  // store the value, just in case entry is invalid     
		Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);  
		((JComponent) c).setBorder(new LineBorder(Color.BLACK));  
		
		return c;  
	} 
}