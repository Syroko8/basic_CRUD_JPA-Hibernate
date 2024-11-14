package com.example.view;

import java.awt.EventQueue;

import com.example.controller.AutorController;
import com.example.controller.LibroController;
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
import java.time.Year;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class CreateBookView extends JFrame implements BookView {

	private LibroController miController;
	private SwitchView miSwitch;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField textTitle;
	private JTextField textYear;
	private JTextField textAuthor;
	private JTextField textState;
	private JTextPane textResults;

	
	public void setSwitch(SwitchView newSwitch) {
		this.miSwitch = newSwitch;
	}
	
	public void setController(LibroController newLibroController) {
		this.miController = newLibroController;
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
					CreateBookView frame = new CreateBookView();
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
	public CreateBookView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 953, 605);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 197, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Crear Libro");
		lblNewLabel.setBounds(393, 46, 175, 60);
		lblNewLabel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		contentPane.add(lblNewLabel);
		
		JLabel lblTitle = new JLabel("Tiltulo");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitle.setBounds(279, 181, 77, 30);
		contentPane.add(lblTitle);
		
		textTitle = new JTextField();
		textTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textTitle.setColumns(10);
		textTitle.setBounds(382, 186, 216, 19);
		contentPane.add(textTitle);
		
		JButton btnReturn = new JButton("Volver");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textResults.setVisible(false);
				miSwitch.switchViews(5, 0);
			}
		});
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnReturn.setBounds(45, 78, 100, 30);
		contentPane.add(btnReturn);
		
		textYear = new JTextField();
		textYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textYear.setColumns(10);
		textYear.setBounds(382, 246, 216, 19);
		contentPane.add(textYear);
		
		JLabel lblYear = new JLabel("Año de");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYear.setBounds(279, 235, 93, 30);
		contentPane.add(lblYear);
		
		textAuthor = new JTextField();
		textAuthor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textAuthor.setColumns(10);
		textAuthor.setBounds(382, 320, 216, 19);
		contentPane.add(textAuthor);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAutor.setBounds(279, 315, 77, 30);
		contentPane.add(lblAutor);
		
		textState = new JTextField();
		textState.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textState.setColumns(10);
		textState.setBounds(382, 388, 216, 19);
		contentPane.add(textState);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEstado.setBounds(279, 383, 77, 30);
		contentPane.add(lblEstado);
		
		JLabel lblPublicacin = new JLabel("publicación");
		lblPublicacin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPublicacin.setBounds(279, 264, 100, 30);
		contentPane.add(lblPublicacin);
		
		JButton btnCreate = new JButton("Añadir");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Comprobamos que todos los campos estén rellenos.
				if(!textTitle.getText().isEmpty() && !textYear.getText().isEmpty() && 
						!textAuthor.getText().isEmpty() && !textState.getText().isEmpty()) {
					
					// Comprobamos que, en estado, se haya contestado con una de las tres opciones.
					String option1 = "Disponible";
					String option2 = "Prestado";
					String option3 = "Baja";
					String respuesta = textState.getText();
					
					if(respuesta.equalsIgnoreCase(option1) || respuesta.equalsIgnoreCase(option2) 
							|| respuesta.equalsIgnoreCase(option3)) {
						
						int pubYear = 0;
						// Comprobamos que la fecha sea válida (int y no mayor a la actual).
						try {
							pubYear = Integer.parseInt(textYear.getText());
							int actualYear = Year.now().getValue();
							if(pubYear <= actualYear) {
								
								showresult(miController.addBook(textTitle.getText(), textYear.getText(), textAuthor.getText(), 
										textState.getText()));
								
								// Tras introducir el libro, vaciamos los campos.
								textTitle.setText("");
								textYear.setText("");
								textAuthor.setText("");
								textState.setText("");
								
							} else {
								showresult("El año introducido debe ser menor o igual al actual.");
							}
						} catch (Exception e2) {
							showresult("El año introducido debe ser un valor numérico.");
							// Interrumpimos el método.
							return;
						}
						
					} else {
						showresult("El estado debe ser uno de los tres indicados junto al campo.");
					}
						
				} else {
					showresult("Todos los campos deben ser rellenados.");
				}
			}
		});
		btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCreate.setBounds(434, 471, 100, 30);
		contentPane.add(btnCreate);
		
		JLabel lblNewLabel_1 = new JLabel("(Prestado, disponible o baja)");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(609, 387, 248, 22);
		contentPane.add(lblNewLabel_1);
		
		textResults = new JTextPane();
		textResults.setBackground(new Color(0, 128, 192));
		textResults.setBounds(29, 449, 319, 88);
		contentPane.add(textResults);
	}
}
