package com.example.view;

import java.awt.EventQueue;

import com.example.controller.AutorController;
import com.example.view.SwitchView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class CreateAuthorView extends JFrame implements AuthorView {

	private AutorController miController;
	private SwitchView miSwitch;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField textName;
	private JTextPane textResults;

	
	public void setSwitch(SwitchView newSwitch) {
		this.miSwitch = newSwitch;
	}
	
	public void setController(AutorController newAutorController) {
		this.miController = newAutorController;
	}
	
	/**
	 * Launch the application.
	 */
	
	private void showresult(String text){
		textResults.setVisible(true);
		textResults.setText(text);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAuthorView frame = new CreateAuthorView();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateAuthorView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 953, 605);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 197, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Crear Autor");
		lblNewLabel.setBounds(371, 48, 189, 60);
		lblNewLabel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		contentPane.add(lblNewLabel);
		
		JLabel lblName = new JLabel("Nombre");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblName.setBounds(279, 181, 77, 30);
		contentPane.add(lblName);
		
		textName = new JTextField();
		textName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textName.setColumns(10);
		textName.setBounds(371, 186, 216, 19);
		contentPane.add(textName);
		
		JButton btnReturn = new JButton("Volver");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textResults.setVisible(false);
				miSwitch.switchViews(1, 0);
			}
		});
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnReturn.setBounds(45, 78, 100, 30);
		contentPane.add(btnReturn);
		
		JButton btnAdd = new JButton("Añadir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Si se pulsa el botón, verificamos que se haya rellenado el campo, y que no exista el autor.
				if(!textName.getText().isEmpty()) {

					if(!miController.existsAuthor(textName.getText())){
						textResults.setVisible(false);
						// Si no existe, lo agregamos.
						showresult(miController.addAuthor(textName.getText()));
						// Vaciamos el campo.
						textName.setText("");
					}
				} else{
					showresult("Se debe rellenar el campo.");
				}
				
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAdd.setBounds(429, 264, 100, 30);
		contentPane.add(btnAdd);
		
		textResults = new JTextPane();
		textResults.setBackground(new Color(0, 128, 192));
		textResults.setBounds(262, 386, 435, 91);
		contentPane.add(textResults);
	}
}
