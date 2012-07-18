package de.dustboystudios.jarfinder.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.dustboystudios.jarfinder.thread.JarFinderThread;
import de.dustboystudios.jarfinder.thread.JarFinderThread.JarFinderThreadFinishedListener;

/**
 * The main window
 *
 * @author Manuel Vögele
 */
public class MainWindow extends JFrame implements ActionListener, JarFinderThreadFinishedListener
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4138018760054153432L;

	/**
	 * The field for the class name
	 */
	private JTextField nameField;
	
	/**
	 * The file selector
	 */
	private FileSelector fileSelector;
	
	/**
	 * The text area for the output
	 */
	private JTextArea result;
	
	/**
	 * The search button
	 */
	private JButton searchButton;
	
	/**
	 * The thread searching for the jar files
	 */
	private JarFinderThread jarFinderThread;

	/**
	 * Initializes a new main window
	 */
	public MainWindow()
	{
		setTitle("jarfinder");
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				dispose();
				System.exit(0);
			}
		});
		
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		add(panel);
		
		JLabel pathLabel = new JLabel("Search path");
		fileSelector = new FileSelector();
		JLabel nameLabel = new JLabel("Classname");
		nameField = new JTextField(50);
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		result = new JTextArea();
		result.setRows(5);
		result.setEditable(false);
		
		Group vertical = layout.createSequentialGroup();
		vertical.addComponent(pathLabel);
		vertical.addComponent(fileSelector);
		vertical.addComponent(nameLabel);
		vertical.addComponent(nameField);
		vertical.addComponent(searchButton);
		vertical.addComponent(result);
		layout.setVerticalGroup(vertical);
		
		Group horizontal = layout.createParallelGroup();
		horizontal.addComponent(pathLabel);
		horizontal.addComponent(fileSelector);
		horizontal.addComponent(nameLabel);
		horizontal.addComponent(nameField);
		horizontal.addComponent(searchButton);
		horizontal.addComponent(result);
		layout.setHorizontalGroup(horizontal);
		
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		searchButton.setEnabled(false);
		searchButton.setText("searching…");
		jarFinderThread = new JarFinderThread(nameField.getText(), new File(fileSelector.getText()), this);
		jarFinderThread.start();
	}
	
	@Override
	public void onJarFinderThreadFinished(JarFinderThread thread)
	{
		searchButton.setText("search");
		searchButton.setEnabled(true);
		result.setText(thread.getResult());
	}
}
