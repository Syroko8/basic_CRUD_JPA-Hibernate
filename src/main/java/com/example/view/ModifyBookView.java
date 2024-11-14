package com.example.view;

import java.awt.EventQueue;

import com.example.controller.AutorController;
import com.example.controller.LibroController;
import com.example.view.SwitchView;
import com.example.model.Autor;
import com.example.model.Libro;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ModifyBookView extends JFrame implements BookView {

	private LibroController miController;
	private SwitchView miSwitch;
	private List<Libro> books;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTable table;
	private JButton btnReturn;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private JButton btnModify;
	private JTextField textTitle;
	private JTextField textYear;
	private JTextField textAuthor;
	private JTextField textState;
	private JLabel lblYear;
	private JTextPane textResults;


	
	public void setSwitch(SwitchView newSwitch) {
		this.miSwitch = newSwitch;
	}
	
	public void setController(LibroController newLibroController) {
		this.miController = newLibroController;
	}
	
	public void setBooks(List<Libro> newBooks) {
		this.books = newBooks;
	}
	
	public void showresult(String text){
		textResults.setVisible(true);
		textResults.setText(text);
	}
	
public void showBooks() {
		
		try {
			// Obtenemos el modelo de datos de la tabla.
			this.dtm = (DefaultTableModel) table.getModel();
			// Recorremos la lista de autores creando filas en la tabla.
			for(Libro book: books) {
				
				// Obtenemos los datos a mostrar.
				Long id = book.getId();
				String title = book.getTitulo();
				int fechaPub = book.getFechaPublic();
				String author = book.getAutor().getNombre();
				String state = book.getEstado();
				
				// Creamos una nueva fila con los datos.
				String[] newRow = {id.toString(), title, Integer.toString(fechaPub), author, state};
				dtm.addRow(newRow);	
			}
		} catch (Exception e) {
			showresult(e.getMessage());
		}		
	}
	
	// Método que comprueba si el año introducido sea válido (int y no mayor a la actual).
	private boolean checkYear(String year) {
	
		int pubYear = 0;
		
		try {
			// Parseamos el año a interger.
			pubYear = Integer.parseInt(textYear.getText());
			int actualYear = Year.now().getValue();
			if(pubYear <= actualYear) {
				// Si es válido, devolvemos true.
				return true;
			} else {
				showresult("El año introducido debe ser menor o igual al actual.");
				return false;
			}
		} catch (Exception e) {
			showresult("El año introducido debe ser un valor numérico.");
			// Interrumpimos el método.
			return false;
		}
	}
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifyBookView frame = new ModifyBookView();
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
	public ModifyBookView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 953, 605);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 197, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Libros");
		lblNewLabel.setBounds(465, 47, 100, 60);
		lblNewLabel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		contentPane.add(lblNewLabel);
		
		btnReturn = new JButton("Volver");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Al salir de la pantalla, vaciamos la tabla, para que se vuelva a rellenar al entrar.
				dtm.setRowCount(0);
				// Vaciamos el valor de la lista, para rellenarla de nuevo.
				books.clear();
				miSwitch.switchViews(7, 0);
			}
		});
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnReturn.setBounds(45, 78, 100, 30);
		contentPane.add(btnReturn);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 150, 467, 308);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Titulo", "Publicaión", "Autor", "Estado"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		
		btnModify = new JButton("Modificar");
		btnModify.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Comprobamos haya una fila seleccionado en la tabla.
				if(table.getSelectedRow() != -1) {
					// Comprobamos que almenos un campo esté relleno.
					if(!textTitle.getText().isEmpty() || !textYear.getText().isEmpty() ||
							!textAuthor.getText().isEmpty() || !textState.getText().isEmpty()) {
					
						// Obtenemos el id del libro seleccionado.
						Long selectedBookId = Long.parseLong(dtm.getValueAt(table.getSelectedRow(), 0).toString());
						
						// Obtenemos los nuevos datos. Si no se ha rellenado el campo, asignamos null.
						String title = textTitle.getText().isEmpty() ? "" : textTitle.getText();
						String author = textAuthor.getText().isEmpty() ? "" : textAuthor.getText();
						String state = textState.getText().isEmpty() ? "" : textState.getText();

						// Comprobamos que el año sea rellenado.
						if(!textYear.getText().equals("")) {
							
							// Comprobamos que el año sea válido.
							if(checkYear(textYear.getText())) {
								
								int year = Integer.parseInt(textYear.getText()); 
								
								// Pasamos la información necesaria al controlador.
								showresult(miController.modifyBook(selectedBookId, title, year, author, state));
								
								// Vaciamos los campos.
								textTitle.setText("");
								textAuthor.setText("");
								textYear.setText("");
								textState.setText("");
								
							} else {
								return;
							}
							
						} else {
							showresult("Es obligatorio rellenar el año de publicación.");
							return;
						}
						
					} else {
						showresult("Debe rellenar al menos el año de publicación.");
					}
				} else {
					showresult("Debe seleccionar una fila de la tabla.");
				}
				
				// Al terminar el cambio, debemos recargar los componentes de la lista.
				dtm.setRowCount(0);
				books.clear();
				miController.showModifyBooks();
				showBooks();
			}
		});
		btnModify.setBounds(623, 504, 122, 21);
		contentPane.add(btnModify);
		
		JLabel lblTitle = new JLabel("Tiltulo");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitle.setBounds(565, 152, 77, 30);
		contentPane.add(lblTitle);
		
		textTitle = new JTextField();
		textTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textTitle.setColumns(10);
		textTitle.setBounds(668, 157, 216, 19);
		contentPane.add(textTitle);
		
		textYear = new JTextField();
		textYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textYear.setColumns(10);
		textYear.setBounds(668, 217, 216, 19);
		contentPane.add(textYear);
		
		JLabel lblPublicacin = new JLabel("publicación");
		lblPublicacin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPublicacin.setBounds(565, 235, 100, 30);
		contentPane.add(lblPublicacin);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAutor.setBounds(565, 286, 77, 30);
		contentPane.add(lblAutor);
		
		textAuthor = new JTextField();
		textAuthor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textAuthor.setColumns(10);
		textAuthor.setBounds(668, 291, 216, 19);
		contentPane.add(textAuthor);
		
		textState = new JTextField();
		textState.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textState.setColumns(10);
		textState.setBounds(668, 359, 216, 19);
		contentPane.add(textState);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEstado.setBounds(565, 354, 77, 30);
		contentPane.add(lblEstado);
		
		JLabel lblNewLabel_1 = new JLabel("(Prestado, disponible o baja)");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(661, 406, 248, 22);
		contentPane.add(lblNewLabel_1);
		
		lblYear = new JLabel("Año de");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYear.setBounds(565, 206, 93, 30);
		contentPane.add(lblYear);
		
		textResults = new JTextPane();
		textResults.setBackground(new Color(0, 128, 192));
		textResults.setBounds(60, 482, 467, 60);
		contentPane.add(textResults);
	}
}
