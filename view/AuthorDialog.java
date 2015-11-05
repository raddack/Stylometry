package app.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import app.models.Author;


public class AuthorDialog {

	private static JFrame frmAddAnAuthor;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorDialog window = new AuthorDialog();
					window.frmAddAnAuthor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AuthorDialog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddAnAuthor = new JFrame();
		frmAddAnAuthor.setTitle("Add An Author");
		frmAddAnAuthor.setResizable(false);
		frmAddAnAuthor.setBounds(100, 100, 264, 123);
		frmAddAnAuthor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblCreateNewAuthor = new JLabel("Create New Author:");
		
		textField = new JTextField();
		textField.setToolTipText("Enter author's name here.");
		textField.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	AuthorDialog.frmAddAnAuthor.setVisible(false);
            }
            
        });
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	if(textField.getText().equals(""))
            		AuthorDialog.frmAddAnAuthor.setVisible(true);
            	else {
            		new Author().load(textField.getText()).save();
            		AuthorDialog.frmAddAnAuthor.setVisible(false);
            	}
            }
            
        });
		
		
		GroupLayout groupLayout = new GroupLayout(frmAddAnAuthor.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCreateNewAuthor)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(btnCancel)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnConfirm))
							.addComponent(textField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(188, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCreateNewAuthor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnConfirm))
					.addContainerGap(177, Short.MAX_VALUE))
		);
		frmAddAnAuthor.getContentPane().setLayout(groupLayout);
	}
	
	public void run() {
		try {
			AuthorDialog window = new AuthorDialog();
			window.frmAddAnAuthor.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
