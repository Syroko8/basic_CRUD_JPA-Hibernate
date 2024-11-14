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
import javax.swing.JTextPane;

public class ShowBooksView extends JFrame implements BookView {

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
					ShowBooksView frame = new ShowBooksView();
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
	public ShowBooksView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 953, 605);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 197, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Libros");
		lblNewLabel.setBounds(462, 48, 100, 60);
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
				miSwitch.switchViews(6, 0);
			}
		});
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnReturn.setBounds(45, 78, 100, 30);
		contentPane.add(btnReturn);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(278, 148, 467, 308);
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
		
		textResults = new JTextPane();
		textResults.setBackground(new Color(0, 128, 192));
		textResults.setBounds(278, 486, 467, 60);
		contentPane.add(textResults);
	}
}