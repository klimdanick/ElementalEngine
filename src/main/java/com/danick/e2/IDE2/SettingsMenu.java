package com.danick.e2.IDE2;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.SpringLayout;

public class SettingsMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public SettingsMenu() {
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Appearance", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		panel.add(list, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		SpringLayout sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);
		
		JLabel lblNewLabel = new JLabel("Theme");
		sl_panel_2.putConstraint(SpringLayout.NORTH, lblNewLabel, 20, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, lblNewLabel, 136, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, lblNewLabel, -182, SpringLayout.SOUTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, lblNewLabel, -244, SpringLayout.EAST, panel_2);
		panel_2.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox(new String[] {"Light", "Dark", "Mac OS Light", "Mac OS Dark"});
		sl_panel_2.putConstraint(SpringLayout.NORTH, comboBox, 1, SpringLayout.NORTH, lblNewLabel);
		sl_panel_2.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, lblNewLabel);
		sl_panel_2.putConstraint(SpringLayout.EAST, comboBox, -133, SpringLayout.EAST, panel_2);
		panel_2.add(comboBox);
		comboBox.setSelectedIndex(IDEWindow.theme);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("FullScreen");
		sl_panel_2.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox, 8, SpringLayout.SOUTH, lblNewLabel);
		sl_panel_2.putConstraint(SpringLayout.WEST, chckbxNewCheckBox, 0, SpringLayout.WEST, lblNewLabel);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, chckbxNewCheckBox, 74, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, chckbxNewCheckBox, 218, SpringLayout.WEST, panel_2);
		panel_2.add(chckbxNewCheckBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        JComboBox cb = (JComboBox)e.getSource();
		        String theme = (String)cb.getSelectedItem();
		        FlatLaf T;
		        switch (theme) {
		        	case "Light":
		        		T = new FlatIntelliJLaf();
		        		IDEWindow.theme = 0;
		        		break;
		        	case "Dark":
		        		T = new FlatDarculaLaf();
		        		IDEWindow.theme = 1;
		        		break;
		        	case "Mac OS Light":
		        		T = new FlatMacLightLaf();
		        		IDEWindow.theme = 2;
		        		break;
		        	case "Mac OS Dark":
		        		T = new FlatMacDarkLaf();
		        		IDEWindow.theme = 3;
		        		break;
		        	default:
		        		T = new FlatDarkLaf();
		        		IDEWindow.theme = 1;
		        		break;
		        }
		        FlatLaf.setup(T);
		        JOptionPane.showMessageDialog(getContentPane(),
					    "Restart required!",
					    "Restart warning",
					    JOptionPane.WARNING_MESSAGE);
		        IDEWindow.frame.setVisible(false);
		        IDEWindow.frame = new IDEWindow(IDEWindow.gc);
		        //IDEWindow.gc.createBuffer();
		        IDEWindow.frame.setVisible(true);
		        IDEWindow.settingsMenu.setVisible(false);
		    }
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
	}

}
