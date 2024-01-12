package com.danick.e2.IDE2;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class About extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public About() {
		setTitle("About E2");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);		
		
		JTextArea Text = new JTextArea();
		Text.append("E2 Version: " + "3.0.5" + "\n");
		Text.append("IDE Version: " + "1.0.0" + "\n\n");
		Text.append("Developer: Danick Imholz (klimdanick) \nWebsite: https://www.klimdanick.nl");
		Text.setEditable(false);
		contentPane.add(Text, BorderLayout.CENTER);
		
		BufferedImage myPicture = null;
		//try {
			//myPicture = ImageIO.read(About.class.getResourceAsStream("E2Banner2.png"));
		//} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		//}
		//JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		//picLabel.setOpaque(false);
		//picLabel.setBorder(null);
		//picLabel.setBackground(new Color(0xFF262626).darker().darker());
		//contentPane.add(picLabel, BorderLayout.WEST);
	}

}
