package edu.odu.msim.swing.table.javafx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.ejml.simple.SimpleMatrix;

import edu.odu.msim.utils.MatrixUtils;


/**
 * SwingInterop
 *
 * Note using the browser might require setting the properties
 *  - http.proxyHost
 *  - http.proxyPort
 *
 * e.g. -Dhttp.proxyHost=webcache.mydomain.com -Dhttp.proxyPort=8080
 * 
 */
public class SimpleMatrixTableModelTest extends JApplet 
{
	private static final long serialVersionUID = 1L;
	
	private static final int PANEL_WIDTH_INT = 600;
	
	private static final int PANEL_HEIGHT_INT = 400;
	
	private  final int TABLE_PANEL_HEIGHT_INT = 100;
	
	private JFXPanel chartFxPanel;
	
	private SimpleMatrixTableModel tableModel;
	
	private Chart chart;

	@Override
	public void init() 
	{
		int rows = 10;
		int columns = 10;
		int min = 1;
		int max = 100;

		SimpleMatrix matrix = MatrixUtils.makeRandomMatrix(rows, columns, min, max);
		String[] columnNames = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

		this.tableModel = new SimpleMatrixTableModel(matrix, columnNames);

		// create javafx panel for charts
		this.chartFxPanel = new JFXPanel();
		this.chartFxPanel.setPreferredSize(new Dimension(PANEL_WIDTH_INT, PANEL_HEIGHT_INT));


		// JTable
		JTable table = new JTable(this.tableModel);
		table.setAutoCreateRowSorter(true);
		table.setGridColor(Color.DARK_GRAY);
		SimpleMatrixTableModelTest.DecimalFormatRenderer renderer = 
			new SimpleMatrixTableModelTest.DecimalFormatRenderer();
		renderer.setHorizontalAlignment(JLabel.RIGHT);
		
		for (int i = 0; i < table.getColumnCount(); i++) 
		{
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		
		JScrollPane tablePanel = new JScrollPane(table);
		tablePanel.setPreferredSize(new Dimension(PANEL_WIDTH_INT, TABLE_PANEL_HEIGHT_INT));

		JPanel chartTablePanel = new JPanel();
		chartTablePanel.setLayout(new BorderLayout());

		//Split pane that holds both chart and table
		JSplitPane jsplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		jsplitPane.setTopComponent(chartTablePanel);
		jsplitPane.setBottomComponent(tablePanel);
		jsplitPane.setDividerLocation(410);
		chartTablePanel.add(this.chartFxPanel, BorderLayout.CENTER);

		//add(tablePanel, BorderLayout.CENTER);
		add(jsplitPane, BorderLayout.CENTER);

		// create JavaFX scene
		Platform.runLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				SimpleMatrixTableModelTest.this.createScene();
			}
		});
	}

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} 
				catch (Exception e) 
				{

				}

				JFrame frame = new JFrame("SimpleMatrix JTable Swing JavaFX Test");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JApplet applet = new SimpleMatrixTableModelTest();
				applet.init();

				frame.setContentPane(applet.getContentPane());

				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

				//applet.start();
			}
		});
	}

	private void createScene() 
	{
		//this.chart = createBarChart();
		//this.chartFxPanel.setScene(new Scene(this.chart));
	}

	private BarChart createBarChart() 
	{
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setCategories(FXCollections.<String>observableArrayList(
			this.tableModel.getColumnNames()));
		xAxis.setLabel("Year");

		double tickUnit = this.tableModel.getTickUnit();

		NumberAxis yAxis = new NumberAxis();
		yAxis.setTickUnit(tickUnit);
		yAxis.setLabel("Units Sold");

		final BarChart chart = new BarChart(xAxis, yAxis, 
			this.tableModel.getBarChartData());
		this.tableModel.addTableModelListener(new TableModelListener() 
		{
			public void tableChanged(TableModelEvent e) 
			{
				if (e.getType() == TableModelEvent.UPDATE) 
				{
					final int row = e.getFirstRow();
					final int column = e.getColumn();
					final Object value = 
						((SimpleMatrixTableModel)e.getSource()).getValueAt(row, column);

					Platform.runLater(new Runnable() 
					{
						public void run() {
							XYChart.Series<String, Number> s = 
								(XYChart.Series<String, Number>) chart.getData().get(row);
							BarChart.Data data = s.getData().get(column);
							data.setYValue(value);
						}
					});
				}
			}
		});
		
		return chart;
	}

	private static class DecimalFormatRenderer extends DefaultTableCellRenderer 
	{
		private static final long serialVersionUID = 1L;
		private static final DecimalFormat formatter = new DecimalFormat("#.0");

		public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column) 
		{
			value = formatter.format((Number) value);
			
			return super.getTableCellRendererComponent(table, value, isSelected, 
				hasFocus, row, column);
		}
	}
}
