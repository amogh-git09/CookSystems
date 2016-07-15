package com.cooksys.assessment;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class Window {
	JList leftJList, rightJList;
	ArrayList<String> leftList = new ArrayList<String>();
	ArrayList<String> rightList = new ArrayList<String>();
	
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
	
	void initializeList(){
		leftList.add("Case");
		leftList.add("Motherboard");
		leftList.add("CPU");
		leftList.add("GPU");
		leftList.add("PSU");
		leftList.add("RAM");
		leftList.add("HDD");
	}
	
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load");
		menu.add(loadMenuItem);		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		menu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		menu.add(exitMenuItem);
		menuBar.add(menu);

		initializeList();
		
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
				
		leftJList = new JList(leftList.toArray());		
		leftJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane leftScrollPane = new JScrollPane(leftJList);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));		
		
		rightJList = new JList();
		rightJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane rightScrollPane = new JScrollPane(rightJList);
		
		JPanel mainPanel = new JPanel(new GridLayout(1, 3));
		mainPanel.add(leftScrollPane);
		mainPanel.add(centerPanel);
		
		JButton removeButton = new JButton("<<");
		JButton addButton = new JButton(">>");
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				transferItem(leftList, leftJList, rightList, rightJList);
			}
		});
		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				transferItem(rightList, rightJList, leftList, leftJList);
			}
		});
		removeButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		addButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		Box box = new Box(BoxLayout.Y_AXIS);		
		box.add(Box.createVerticalGlue());
		box.add(addButton);
		box.add(removeButton);
		box.add(Box.createVerticalGlue());
		
		centerPanel.add(box);
		mainPanel.add(rightScrollPane);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
	
	void transferItem(ArrayList<String> srcList, JList srcJList, ArrayList<String> dstList, JList dstJList){
		int selection = srcJList.getSelectedIndex();
		String item = selection!=-1?srcList.remove(selection):null;
		if(item != null)
			dstList.add(item);
		srcJList.setListData(srcList.toArray());
		dstJList.setListData(dstList.toArray());
	}
}
