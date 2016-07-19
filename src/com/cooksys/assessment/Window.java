package com.cooksys.assessment;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Window {
	JList<String> leftJList, rightJList;
	ItemList leftList;
	ItemList rightList;
	
	private JFrame frame;

	/**
	 * Launch the application. The main method is the entry point to a Java application. 
	 * For this assessment, you shouldn't have to add anything to this.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. This is the constructor for this Window class.
	 * All of the code here will be executed as soon as a Window object is made.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This is where Window Builder
	 * will generate its code.
	 */
	
	//initialize lists
	void initializeLists(){
		leftList = new ItemList();
		rightList = new ItemList();
		leftList.addItem("Case");
		leftList.addItem("Motherboard");
		leftList.addItem("CPU");
		leftList.addItem("GPU");
		leftList.addItem("PSU");
		leftList.addItem("RAM");
		leftList.addItem("HDD");
	}
	
	public void initialize() {
		//frame
		frame = new JFrame("Assemble your computer");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		//Menubar and menu items
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load");
		loadMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightList = loadFromFile();
				rightJList.setListData(rightList.toArray());
			}
		});
		menu.add(loadMenuItem);		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveListToFile();
			}
		});
		menu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(exitMenuItem);
		menuBar.add(menu);

		initializeLists();
		
		//menu to the north
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
				
		//prepare left panel
		leftJList = new JList<String>(leftList.toArray());		
		leftJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane leftScrollPane = new JScrollPane(leftJList);
		
		//center panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));		
		
		//prepare right panel
		rightJList = new JList<String>();
		rightJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane rightScrollPane = new JScrollPane(rightJList);
		
		//Use 1x3 grid as the main container
		JPanel mainPanel = new JPanel(new GridLayout(1, 3));
		mainPanel.add(leftScrollPane);
		mainPanel.add(centerPanel);
		
		//add two buttons
		JButton removeButton = new JButton("<<");
		JButton addButton = new JButton(">>");
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addItem();
			}
		});
		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				removeItem();
			}
		});
		removeButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		addButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		//Buttons added inside a box 
		Box box = new Box(BoxLayout.Y_AXIS);		
		box.add(Box.createVerticalGlue());
		box.add(addButton);
		box.add(removeButton);
		box.add(Box.createVerticalGlue());
		
		centerPanel.add(box);
		mainPanel.add(rightScrollPane);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
	
	//adds item to the right list
	void addItem(){
		int selection = leftJList.getSelectedIndex();
		String item = selection!=-1?leftList.getItem(selection):null;
		if(item != null)
			rightList.addItem(item);
		leftJList.setListData(leftList.toArray());
		rightJList.setListData(rightList.toArray());
	}
	
	//remove item from the right list
	void removeItem(){
		int selection = rightJList.getSelectedIndex();
		if(selection != -1)
			rightList.removeItem(selection);
		rightJList.setListData(rightList.toArray());
	}
	
	//open file selection dialog box
	void saveListToFile(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select output file");
		int userSelection = fileChooser.showSaveDialog(frame);
		
		if(userSelection == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			saveListAsXml(rightList, file);
		}
	}
	
	//saves list to passed file in xml format
	void saveListAsXml(ItemList list, File file){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(ItemList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(list, file);
			JOptionPane.showMessageDialog(frame, "List saved successfully");
		} catch(JAXBException e){
			e.printStackTrace();
		}
	}
	
	//show dialog box to choose file to open
	ItemList loadFromFile(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select file");
		int userSelection = fileChooser.showOpenDialog(frame);
		
		if(userSelection == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			return loadListFromXml(file);
		}
		return null;
	}
	
	//load xml file to right panel list
	ItemList loadListFromXml(File file){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ItemList.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ItemList list = (ItemList) jaxbUnmarshaller.unmarshal(file);
			return list;
		} catch (JAXBException e){
			e.printStackTrace();
			return null;
		}
	}
}

