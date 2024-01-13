package com.danick.e2.IDE2;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import com.danick.e2.main.GameContainer;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class IDEWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	public static int theme = 3;
	public static IDEWindow frame;
	public static JFrame settingsMenu = new SettingsMenu();
	public static JFrame a = new About();
	public static IDEGameContainer gc = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (System.getProperty("os.name").toLowerCase().contains("win")) FlatMacDarkLaf.setup();
		else {
			FlatDarculaLaf.setup();
			theme = 1;
		}
		gc = new IDEGameContainer(new GamePreview(), 300, 300, 1, "preview");
		frame = new IDEWindow(gc);
		frame.setVisible(true);
		gc.createBuffer();
		gc.start();
	}

	/**
	 * Create the frame.
	 */
	public IDEWindow(GameContainer gc) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("res\\E2Logo.png"));
		setTitle("Title");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1150, 654);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_1 = new JMenu("Edit");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("New menu item");
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("New menu item");
		mnNewMenu_1.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Settings");
		mntmNewMenuItem.addActionListener(new SettingAction());
		menuBar.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("About");
		mntmNewMenuItem_1.addActionListener(new AboutAction());
		menuBar.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_1.setRightComponent(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setEnabled(false);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setLeftComponent(splitPane_2);
		
		JPanel panel = new JPanel();
		splitPane_2.setLeftComponent(panel);
		
		JButton btnNewButton_2 = new JButton("â–¶");
		btnNewButton_2.setBackground(new Color(0xff009A42));		
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("â�¹");
		btnNewButton_1.setBackground(new Color(0xffDB0029));	
		panel.add(btnNewButton_1);
		
		JTabbedPane panel_1 = new JTabbedPane();
		splitPane_2.setRightComponent(panel_1);
		
		Canvas canvas = new Canvas();
		canvas.setMinimumSize(new Dimension(100, 400));
		panel_1.add(canvas);
		panel_1.setTitleAt(0, "Game Preview");
		Canvas canvas2 = new Canvas();
		canvas2.setMinimumSize(new Dimension(100, 400));
		panel_1.add(canvas2);
		panel_1.setTitleAt(1, "World Editor");
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		File dir = new File("C:/Users/Danick/Elemental-Workspace");
		JTree tree = new JTree((addNodes(null, dir)));
		scrollPane.setViewportView(tree);
	}

	private class SettingAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			settingsMenu = new SettingsMenu();
			settingsMenu.setVisible(true);
			System.out.println("CLICK");
		}
	}
	
	private class AboutAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			a = new About();
			a.setVisible(true);
			System.out.println("CLICK");
		}
	}
	
	DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
	    String curPath = dir.getPath();
	    String[] dirs = curPath.split("\\\\");
	    DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(dirs[dirs.length-1]);
	    if (curTop != null) { // should only be null at root
	      curTop.add(curDir);
	    }
	    Vector ol = new Vector();
	    String[] tmp = dir.list();
	    for (int i = 0; i < tmp.length; i++)
	      ol.addElement(tmp[i]);
	    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
	    File f;
	    Vector files = new Vector();
	    // Make two passes, one for Dirs and one for Files. This is #1.
	    for (int i = 0; i < ol.size(); i++) {
	      String thisObject = (String) ol.elementAt(i);
	      String newPath;
	      if (curPath.equals("."))
	        newPath = thisObject;
	      else
	        newPath = curPath + File.separator + thisObject;
	      if ((f = new File(newPath)).isDirectory())
	        addNodes(curDir, f);
	      else
	        files.addElement(thisObject);
	    }
	    // Pass two: for files.
	    for (int fnum = 0; fnum < files.size(); fnum++)
	      curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
	    return curDir;
	  }
}
