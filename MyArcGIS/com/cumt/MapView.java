package com.cumt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileFilter;

import com.esri.arcgis.beans.TOC.TOCBean;
import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.beans.toolbar.ToolbarBean;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.carto.esriViewDrawPhase;
import com.esri.arcgis.system.AoInitialize;
import com.esri.arcgis.system.EngineInitializer;

public class MapView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	
	private ToolbarBean toolbarBean = null;
	private JSplitPane jSplitPane = null;
	private TOCBean TOCBean = null;
	private MapBean mapBean = null;
	
	private JPanel rightPanel=null;
	static JButton addLayerButton = null;
	static JButton removeLayerButton = null;
	static String devKitHome = null;

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(300);
			jSplitPane.setDividerSize(2);
			jSplitPane.setRightComponent(getMapBean());
			jSplitPane.setLeftComponent(getTOCBean());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes TOCBean
	 * 
	 * @return com.esri.arcgis.beans.TOC.TOCBean
	 */
	private TOCBean getTOCBean() {
		if (TOCBean == null) {
			TOCBean = new TOCBean();
			try {
				TOCBean.setBuddyControl(getMapBean());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return TOCBean;
	}

	/**
	 * This is the default constructor
	 */
	public MapView() {
		super();
		initializeArcGISLicenses();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(802, 572);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("windowClosing()");
				try {
					new AoInitialize().shutdown();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getToolbarBean(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getRightPanel(), java.awt.BorderLayout.EAST);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);

		}
		return jContentPane;
	}

	/**
	 * This method initializes mapBean
	 * 
	 * @return com.esri.arcgis.beans.map.MapBean
	 */
	private MapBean getMapBean() {
		if (mapBean == null) {
			mapBean = new MapBean();
			try {
				mapBean.setDocumentFilename("C:/Users/元龙/Documents/ArcGIS/World/World.mxd");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return mapBean;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		devKitHome = System.getenv("AGSDEVKITJAVA");
		if (devKitHome == null)
		{
			System.out.println("Unable to obtain path to ArcGIS Developer Kit home for Java. Exiting application.");
			System.exit(-1);
		}
		EngineInitializer.initializeVisualBeans();
		MapView map = new MapView();
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map.setVisible(true);
		
		
	}

	/**
	 * This method initializes toolbarBean
	 * 
	 * @return com.esri.arcgis.beans.toolbar.ToolbarBean
	 */
	private ToolbarBean getToolbarBean() {
		if (toolbarBean == null) {
			toolbarBean = new ToolbarBean();
			toolbarBean.setItemsString("4|controls/ControlsOpenDocCommand|0|-1|0|0|1;"
					+ "4|controls/ControlsSaveAsDocCommand|0|-1|0|0|1;"
					+ "10|controls/ControlsMapFullExtentCommand|0|-1|0|0|1;"
					+ "10|controls/ControlsMapZoomInFixedCommand|0|-1|0|0|1;"
					+ "10|controls/ControlsMapZoomOutFixedCommand|0|-1|0|0|1;"
					+ "10|controls/ControlsMapZoomInTool|0|-1|0|0|1;" + "10|controls/ControlsMapZoomOutTool|0|-1|0|0|1;"
					+ "10|controls/ControlsMapPanTool|0|-1|0|0|1;"
					+ "10|controls/ControlsMapZoomToLastExtentBackCommand|0|-1|0|0|1;"
					+ "10|controls/ControlsMapZoomToLastExtentForwardCommand|0|-1|0|0|1;"
					+ "3|controls/ControlsClearSelectionCommand|0|-1|0|0|1;"
					+ "3|controls/ControlsSelectAllCommand|0|-1|0|0|1;"
					+ "3|controls/ControlsSelectByGraphicsCommand|0|-1|0|0|1;"
					+ "3|controls/ControlsZoomToSelectedCommand|0|-1|0|0|1;");
			try {
				toolbarBean.setBuddyControl(getMapBean());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return toolbarBean;
	}

	static void initializeArcGISLicenses() {
		try {
			com.esri.arcgis.system.AoInitialize ao = new com.esri.arcgis.system.AoInitialize();
			if (ao.isProductCodeAvailable(
					com.esri.arcgis.system.esriLicenseProductCode.esriLicenseProductCodeEngine) == com.esri.arcgis.system.esriLicenseStatus.esriLicenseAvailable)
				ao.initialize(com.esri.arcgis.system.esriLicenseProductCode.esriLicenseProductCodeEngine);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JPanel getRightPanel() {
		
		rightPanel = new JPanel();

		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		addLayerButton = new JButton("Add Layer");
		addLayerButton.addActionListener(this);
		removeLayerButton = new JButton("Remove Layer");
		removeLayerButton.addActionListener(this);
		rightPanel.add(addLayerButton);
		rightPanel.add(Box.createVerticalStrut(10));
		rightPanel.add(removeLayerButton);
		rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		return rightPanel;
	}

	public void setRightPanel(JPanel rightPanel) {
		this.rightPanel = rightPanel;
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == addLayerButton)
		{
			if (!loadFile())
			{
				return;
			}
		}
		if (event.getSource() == removeLayerButton)
		{
			// Check if control has any layer
			try
			{
				if (mapBean.getLayerCount() > 0)
				{
					RemoveLayerDialog dialog = new RemoveLayerDialog();
					dialog.setVisible(true);
				}
			}
			catch (Exception e)
			{
				System.out.println("Could not determine layer count.  No layer removed.");
				System.out.println(e.getMessage());
				System.out.println("Continuing ...");
			}
		}
	}

	/**
	 * Method loadFile loads the specified lyr file
	 */
	public boolean loadFile()
	{
		boolean loaded = false;
		JFileChooser chooser = new JFileChooser(devKitHome + "java" + File.separator + "samples" + File.separator + "data" + 
				File.separator + "usa");
		chooser.setFileFilter(new FileFilter()
		{
			public boolean accept(File f)
			{
				return (f.isDirectory() || f.getName().endsWith(".lyr"));
			}

			public String getDescription()
			{
				return ".lyr";
			}
		});
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				String fileChosen = chooser.getSelectedFile().getCanonicalPath();
				System.out.print("Loading " + fileChosen + " ... ");
				mapBean.addLayerFromFile(fileChosen, 0);
			}
			catch (Exception e)
			{
					String fileChosen;
					try {
						fileChosen = chooser.getSelectedFile().getCanonicalPath();
						System.out.println("Could not add layer from file: " + fileChosen);
						System.out.println(e.getMessage());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return false;
					}
					
					

			}
			System.out.println("done");
			loaded = true;
		}
		return loaded;
	}

	/**
	 * This class is used to create a dialog to remove a layer It displays a drop down with list of all the layers that
	 * are currently being added to map control and a removeLayer button
	 */
	class RemoveLayerDialog extends JDialog implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		JComboBox<String> layerCombo = new JComboBox<String>();
		JButton removeButton = new JButton("RemoveLayer");
		JButton cancel = new JButton("Cancel");
		JPanel mainPanel2 = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());

		public RemoveLayerDialog() throws Exception
		{
			super(MapView.this, "Remove Layer");
			setSize(300, 150);
			this.removeButton.addActionListener(this);

			updateLayerDropDown(this.layerCombo);
			this.buttonPanel.add(this.removeButton);
			this.buttonPanel.add(this.cancel);
			this.mainPanel2.add(this.layerCombo, BorderLayout.NORTH);
			this.mainPanel2.add(this.buttonPanel, BorderLayout.SOUTH);
			this.mainPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			getContentPane().add(this.mainPanel2, BorderLayout.CENTER);
		}

		public void updateLayerDropDown(JComboBox<String> layerCombo2) throws Exception
		{
			// Get layer count
			int layerCount = 0;
			layerCount = mapBean.getLayerCount();
			// Add the map's layer names to a list
			for (int i = 0; i < layerCount; i++)
			{
				ILayer layer = mapBean.getLayer(i);
				String name = layer.getName();
				layerCombo2.addItem(name);
			}
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent event)
		 * @param event
		 */
		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource() == this.removeButton)
			{
				int layerIndex = this.layerCombo.getSelectedIndex();
				try
				{
					mapBean.deleteLayer(layerIndex);
					mapBean.refresh(esriViewDrawPhase.esriViewBackground, null, null);
				}
				catch (Exception e)
				{
					System.out.println("Could not remove layer.");
					System.out.println(e.getMessage());
					System.out.println("Continuing ...");
				}
				// dispose the dialog
				dispose();
			}
			if (event.getSource() == this.cancel)
			{
				// Dispose the password dialog
				dispose();
			}
		}
	}


}
	

