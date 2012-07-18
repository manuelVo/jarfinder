package de.dustboystudios.jarfinder.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileSelector extends JPanel implements ActionListener
{
	private JTextField textField;

	public FileSelector()
	{
		textField = new JTextField();
		JButton button = new JButton();
		button.setText("Browse…");
		button.addActionListener(this);

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		Group horizontal = layout.createSequentialGroup();
		horizontal.addComponent(textField);
		horizontal.addComponent(button);
		layout.setHorizontalGroup(horizontal);
		
		Group vertical = layout.createParallelGroup();
		vertical.addComponent(textField);
		vertical.addComponent(button);
		layout.setVerticalGroup(vertical);
	}
	
	public String getText()
	{
		return textField.getText();
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				textField.setText(jFileChooser.getSelectedFile().getCanonicalPath());
			}
			catch (IOException e)
			{
				// Nothing to do
			}
		}
	}
}
