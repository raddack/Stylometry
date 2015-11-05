import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;


public class DocumentManager {

	private JFrame frmDocumentEditor;
	private JTextField TitleField;
	private JTextField AuthorField;
	private JTextField DateField;
	private JTextField FileNameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DocumentManager window = new DocumentManager();
					window.frmDocumentEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DocumentManager() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDocumentEditor = new JFrame();
		frmDocumentEditor.setTitle("Document Editor");
		frmDocumentEditor.setBounds(100, 100, 487, 202);
		frmDocumentEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblTitle = new JLabel("Title");
		
		TitleField = new JTextField();
		TitleField.setColumns(10);
		
		JLabel lblAuthor = new JLabel("Author");
		
		AuthorField = new JTextField();
		AuthorField.setColumns(10);
		
		JLabel lblDate = new JLabel("Date");
		
		DateField = new JTextField();
		DateField.setColumns(10);
		
		JLabel lblFilename = new JLabel("Filename");
		
		FileNameField = new JTextField();
		FileNameField.setColumns(10);
		
		JButton CancelButton = new JButton("Cancel");
		
		JButton SaveButton = new JButton("Save");
		
		JButton btnSelect = new JButton("Browse");
		GroupLayout groupLayout = new GroupLayout(frmDocumentEditor.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTitle)
								.addComponent(lblAuthor)
								.addComponent(lblDate)
								.addComponent(lblFilename))
							.addGap(19)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(AuthorField, Alignment.LEADING)
								.addComponent(TitleField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
								.addComponent(DateField, Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(FileNameField, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnSelect))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(CancelButton)
							.addPreferredGap(ComponentPlacement.RELATED, 297, Short.MAX_VALUE)
							.addComponent(SaveButton)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitle)
						.addComponent(TitleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAuthor)
						.addComponent(AuthorField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDate)
						.addComponent(DateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFilename)
						.addComponent(FileNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelect))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(CancelButton)
						.addComponent(SaveButton))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		frmDocumentEditor.getContentPane().setLayout(groupLayout);
	}

}
