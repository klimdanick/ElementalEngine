package com.danick.e2.IDE;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.gpl.JSplitButton.JSplitButton;
import org.gpl.JSplitButton.action.SplitButtonActionListener;

import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Window;

import com.danick.e2.main.AbstractGame;

public class IDEWindow extends Window{
	
	public static BufferedImage img = null;
	
	public static JTextArea console;
	public static JScrollPane scroll;
	public static String Root = System.getProperty("user.home");
	private static String projectPath = Root + "/Elemental-Workspace";
	public static JList list;
	

	public IDEWindow(IDEGameContainer gc) {
		super();
		
		this.gc = gc;
		image = new BufferedImage(gc.width, gc.height, BufferedImage.TYPE_INT_ARGB);
		canvas = new Canvas();
		Dimension s = new Dimension((int)(gc.width), (int)(gc.height));
		
		frame = new JFrame(gc.title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		
		
		JPanel TopBox = new JPanel();
		JPanel LeftBox = new JPanel();
		JPanel BottomBox = new JPanel();
		
		JButton play = new JButton();
		JButton stop = new JButton();
		play.setText(" ▶ ");
		stop.setText(" ■ ");
		
		play.setBackground(new Color(0xFF00991D));
		stop.setBackground(new Color(0xFFBA324F));
		play.setForeground(new Color(0xFF262626));
		stop.setForeground(new Color(0xFF262626));
		
		//play.setOpaque(true);
		//play.setBorderPainted(false);
		
		//stop.setOpaque(true);
		//stop.setBorderPainted(false);
		
		play.addActionListener(new PlayListener());
		play.addActionListener(new StopListener());
		
		TopBox.add(play);
		TopBox.add(stop);
		
		console = new JTextArea();
		console.setPreferredSize(new Dimension(gc.width,gc.height/4));
		console.setLineWrap(true);
		console.setEditable(false);
		scroll = new JScrollPane(console);
		BottomBox.add(scroll);
		//System.out.println(Paths.get(projectPath));
		File[] dirFiles = null;
		File[] files;
		do {
			if (projectPath == null)
				list = new JList();
			else {
				dirFiles = new File(projectPath).listFiles();
				if (dirFiles != null) { 
					files = new File[dirFiles.length + 1];
					files[0] = new File(new File(projectPath).getPath());
					for (int i = 1; i < files.length; i++) {
						files[i] = dirFiles[i - 1];
					}
					list = new JList(files);
				} else {
					list = new JList();
					try {
						Files.createDirectories(Paths.get(projectPath+"/NewProject"));
						File main = new File(projectPath+"/NewProject"+"/main.java");
						main.createNewFile();
						try {
						      FileWriter myWriter = new FileWriter(projectPath+"/NewProject"+"/main.java");
						      myWriter.write(IDEGameContainer.DefaultCode);
						      myWriter.close();
						} catch (IOException e) {
						      console.append("An error occurred. \n");
						      console.append(e.toString());
						      console.append("\n");
						}
						console.append("Directory " + projectPath + " Created!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} while (dirFiles == null);
		
		list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
		list.setCellRenderer(new MyCellRenderer());
		list.setPreferredSize(new Dimension(100, 80));
		list.setName("displayList");
		//list.setSelectionBackground(new Color(0xFF004A7F));
		//list.setBackground(new Color(0xFF262626));
		//list.setForeground(Color.white);
		//list.setSelectionForeground(Color.white);
		
		//LeftBox.setBackground(new Color(0xFF262626));
		//BottomBox.setBackground(new Color(0xFF262626));
		//TopBox.setBackground(new Color(0xFF262626).darker());
		//console.setBackground(new Color(0xFF262626).darker());
		//console.setForeground(Color.white);
		//scroll.setBorder(null);

		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				//jList1ValueChanged(evt);
				if (list.getSelectedValue() != null);
					//console.append(list.getSelectedValue().toString()+" ");
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		    	if (evt.getButton() == 3) {
		    		JList List = (JList)evt.getSource();
		    		var value = List.getSelectedValue();
		        	if (value instanceof File) {
						File file = (File) value;
			            // Double-click detected
						if (!file.isDirectory()) {
							JPopupMenu m = new JPopupMenu();
				    		JButton b = new JButton("Set as main abstract game");
				    		m.add(b);
				    		b.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try {
										Object o;
										o = Class.forName("").getConstructor().newInstance();
										if (o instanceof AbstractGame) {
											AbstractGame game = (AbstractGame) o;
											gc.changeScene(game);
										}
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} 
								}
				    			
				    		});
							m.add(new JButton("Edit"));
							m.show(list, 50, 10);
							return;
						}
					}
		    	}
		        JList List = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	//console.append(evt.toString());
		        	var value = List.getSelectedValue();
		        	if (value instanceof File) {
						File file = (File) value;
			            // Double-click detected
						if (!file.isDirectory()) {
				        	try {
								Desktop.getDesktop().open(file);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		        		} else {
		        			File[] dirFiles = null;
		        			File[] files;
		        			while (dirFiles == null) {
		        				if (projectPath != null) {
		        					dirFiles = new File(file.getPath()).listFiles();
		        					if (dirFiles != null) { 
		        						files = new File[dirFiles.length + 1];
		        						files[0] = new File(new File(projectPath).getPath());
		        						for (int i = 1; i < files.length; i++) {
		        							files[i] = dirFiles[i - 1];
		        						}
		        						list.removeAll();
		        						list.setListData(files);
		        						//console.append(files.toString()+" "+files.length);
		        					}
		        				}
		        			}
		        			
		        		}
		        	}
		        }
		        if (evt.getClickCount() == 3) {
		        	//console.append(evt.toString());
		        	var value = list.getSelectedValue();
		        	if (value instanceof File) {
						File file = (File) value;
			            // Double-click detected
			        	try {
							Desktop.getDesktop().open(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	}
		        }
		    }
		});
		list.setPreferredSize(new Dimension(gc.width/3,gc.height/2));
		LeftBox.add(list);
		
		frame.add(TopBox, BorderLayout.NORTH);
		frame.add(LeftBox, BorderLayout.WEST);
		frame.add(BottomBox, BorderLayout.SOUTH);
		
		
		
		try {
			img = ImageIO.read(IDEWindow.class.getResourceAsStream("/E2logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.print(img);

		frame.setIconImage(img);
		
		
		JMenuBar MenuBar = new JMenuBar();
		var FileMenu = new JMenu("File");
		var EditMenu = new JMenu("Edit");
		var HelpMenu = new JMenu("Help");
		var SettingsMenu = new JMenu("Settings");
		
		var About = new JMenuItem(new MenuActionListener());
		var Docs = new JMenuItem(new MenuActionListener());
		
		var Preferences = new JMenuItem(new MenuActionListener());
		
		About.setLabel("About E2");
		Docs.setLabel("Documentation");
		Preferences.setLabel("Preferences");
		
		HelpMenu.add(About);
		HelpMenu.add(Docs);
		
		SettingsMenu.add(Preferences);
		
		MenuBar.add(FileMenu);
		MenuBar.add(EditMenu);
		MenuBar.add(SettingsMenu);
		MenuBar.add(HelpMenu);
		
		//MenuBar.setBackground(new Color(0xFF262626).darker().darker());
		//MenuBar.setBorderPainted(false);
		
		//HelpMenu.setForeground(Color.white);
		//EditMenu.setForeground(Color.white);
		//FileMenu.setForeground(Color.white);
		
		frame.setJMenuBar(MenuBar);
		
		//frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.pack();
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		frame.pack();
		scroll.setPreferredSize(new Dimension(canvas.getWidth(),canvas.getWidth()/6));
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}
	
	public void update() {
		
		if ((double)canvas.getHeight()/(double)canvas.getWidth() != (double)gc.height/(double)gc.width) {
			//canvas.setSize(canvas.getWidth(), (int) (canvas.getWidth()*gc.AspectRatio)+40);
			//console.setPreferredSize(new Dimension(canvas.getWidth(),canvas.getWidth()/6));
			canvas.createBufferStrategy(1);
			bs = canvas.getBufferStrategy();
			g = bs.getDrawGraphics();
			frame.pack();
		}
		
		//console.setPreferredSize(new Dimension(canvas.getWidth(),canvas.getWidth()/6));
		scroll.setPreferredSize(new Dimension(canvas.getWidth(),canvas.getWidth()/6));
		g.drawImage(image, 0, 0, frame.getWidth(), (int) (frame.getWidth()*gc.AspectRatio), null);
		bs.show();
		canvas.paint(g);
	}
	
	private static class MyCellRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (value instanceof File) {
				File file = (File) value;
				setText(file.getName());
				// setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
					
				} else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}
				setEnabled(list.isEnabled());
				setFont(list.getFont());
				setOpaque(true);
			}
			return this;
		}
	}
	
	
	private static class MenuActionListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			//console.append(e.getActionCommand() + " " + e.getID());
			if (e.getActionCommand() == "About E2") {
				JFrame aboutFrame = new JFrame("About E2");
				aboutFrame.setIconImage(img);
				var Text = new JTextArea();
				Text.append("E2 Version: " + GameContainer.version + "\n");
				Text.append("IDE Version: " + IDE.version + "\n\n");
				Text.append("Developer: Danick Imholz (klimdanick) \nWebsite: https://www.klimdanick.nl");
				Text.setEditable(false);
				aboutFrame.add(Text, BorderLayout.CENTER);
				aboutFrame.setPreferredSize(new Dimension(800, 600));
				
				//aboutFrame.setBackground(new Color(0xFF262626).darker().darker());
				//Text.setBackground(new Color(0xFF262626).darker().darker());
				//Text.setForeground(Color.white);
				BufferedImage myPicture = null;
				try {
					myPicture = ImageIO.read(IDEWindow.class.getResourceAsStream("/E2Banner.png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JLabel picLabel = new JLabel(new ImageIcon(myPicture));
				picLabel.setOpaque(false);
				picLabel.setBorder(null);
				//picLabel.setBackground(new Color(0xFF262626).darker().darker());
				aboutFrame.add(picLabel, BorderLayout.WEST);
				aboutFrame.pack();
				aboutFrame.setVisible(true);
			}
			
			if (e.getActionCommand() == "Documentation") {
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				    try {
						Desktop.getDesktop().browse(new java.net.URI("https://www.engine.klimdanick.nl"));
					} catch (IOException | URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			if (e.getActionCommand() == "Preferences") {
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					JFrame preferences = new JFrame("Preferences");
					preferences.setIconImage(img);
					 
					JPopupMenu themeSelector = new JPopupMenu();
					JMenuItem light = new JMenuItem("Light");
					JMenuItem dark = new JMenuItem("Dark");
					themeSelector.add(light); themeSelector.add(dark);
					preferences.add(themeSelector);
					
					preferences.setPreferredSize(new Dimension(300, 200));
					preferences.pack();
					preferences.setVisible(true);
				}
			}
		}
		
	}
	
	private static class PlayListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//runProcess("cd \""+projectPath+"\"");
				runProcess("javac -cp \".;"+projectPath+"/lib/*\" -d "+projectPath+"/bin "+projectPath+"/NewProject/*.java");
				runProcess("java -cp \".;"+projectPath+"/lib/*;"+projectPath+"/bin\" NewProject.main");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		private static void runProcess(String command) throws Exception {
			System.out.println(command);
	        Process pro = Runtime.getRuntime().exec(command);
	        
	        printLines(command + " stdout:", pro.getInputStream());
	        printLines(command + " stderr:", pro.getErrorStream());
	        pro.waitFor();
	        //console.append(command + " exitValue() " + pro.exitValue()+"\n");
	    }
		
		private static void printLines(String cmd, InputStream ins) throws Exception {
	        String line = null;
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(ins));
	        while ((line = in.readLine()) != null) {
	            console.append(line + "\n");
	        }
		}
		
	}
	
	private static class StopListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}



