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

/**
 * A textbox with a browse button to select a file
 *
 * @author Manuel Vögele
 */
public class FileSelector extends JPanel implements ActionListener
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9091708971005977150L;
	
	/**
	 * The text field for the file name
	 */
	private JTextField textField;

	/**
	 * Initializes a new FileSelector
	 */
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
	
	/**
	 * Returns the filename entered in the text box
	 * @return the filename entered in the text box
	 */
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
