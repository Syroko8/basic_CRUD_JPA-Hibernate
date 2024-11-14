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
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class DeleteBookView extends JFrame implements BookView {

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
	private JButton btnDelete;
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
	
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteBookView frame = new DeleteBookView();
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
	public DeleteBookView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 953, 605);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 197, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Libros");
		lblNewLabel.setBounds(469, 48, 100, 60);
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
				miSwitch.switchViews(8, 0);
			}
		});
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnReturn.setBounds(45, 78, 100, 30);
		contentPane.add(btnReturn);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(278, 132, 467, 251);
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
		
		btnDelete = new JButton("Eliminar");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Comprobamos que haya una fila selecionada en la tabla.
				if(table.getSelectedRow() != -1) {
					
					// Obtenemos el id del autor seleccionado.
					Long selectedAuthorId = Long.parseLong(dtm.getValueAt(table.getSelectedRow(), 0).toString());
					
					// Eliminamos el autor seleccionado.
					showresult(miController.deleteBook(selectedAuthorId));
					
				} else{
					showresult("Se debe seleccionar un usuario.");
				}
				
				// Al terminar la eliminación, debemos recargar los componentes de la lista.
				dtm.setRowCount(0);
				books.clear();
				miController.showDeleteBooks();
				showBooks();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDelete.setBounds(469, 420, 105, 21);
		contentPane.add(btnDelete);
		
		textResults = new JTextPane();
		textResults.setBackground(new Color(0, 128, 192));
		textResults.setBounds(278, 470, 467, 69);
		contentPane.add(textResults);
	}
}
