package com.example.view;

import com.example.controller.AutorController;
import com.example.controller.LibroController;
import com.example.view.ShowAuthorsView;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InitialView extends JFrame {

	private SwitchView miSwitch;
	private AutorController miAuthorControler;
	private LibroController miBookController;
	private ShowAuthorsView showAuthorsView;
	private JFrame[] views;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JButton btnUpdateAuthor;
	private JButton btnCreateAuthor;
	private JButton btnDeleteAuthor;
	private JButton btnShowAuthors;
	private JButton btnDeleteBook;
	private JButton btnShowBooks;
	private JButton btnNewBook;
	private JButton btnUpdateBook;
	
	public void setSwitch(SwitchView newSwitch) {
		this.miSwitch = newSwitch;
	}
	
	public void setViews(JFrame[] views) {
		this.views = views;
	}
	
	public void setAutorController(AutorController newAuthorControler) {
		this.miAuthorControler = newAuthorControler;
	}
	
	public void seLibroController(LibroController newBookController) {
		this.miBookController = newBookController;
	}
	
	public void setShowAuthorsView(ShowAuthorsView newShowAuthorsView) {
		this.showAuthorsView = newShowAuthorsView;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitialView frame = new InitialView();
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
	public InitialView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 953, 605);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 197, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Biblioteca");
		lblNewLabel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		lblNewLabel.setBounds(392, 48, 160, 60);
		contentPane.add(lblNewLabel);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		lblAutor.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblAutor.setBounds(195, 147, 105, 60);
		contentPane.add(lblAutor);
		
		JLabel lblLibro = new JLabel("Libro");
		lblLibro.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		lblLibro.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblLibro.setBounds(664, 147, 89, 60);
		contentPane.add(lblLibro);
		
		btnCreateAuthor = new JButton("Crear");
		btnCreateAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miSwitch.switchViews(0, 1);
			}
		});
		btnCreateAuthor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCreateAuthor.setForeground(new Color(240, 176, 72));
		btnCreateAuthor.setBackground(new Color(255, 255, 255));
		btnCreateAuthor.setBounds(87, 274, 132, 60);
		contentPane.add(btnCreateAuthor);
		
		btnUpdateAuthor = new JButton("Actualizar");
		btnUpdateAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Recuperamos los autores.
				miAuthorControler.showModifyAuthors();
				miSwitch.switchViews(0, 3);
				// Hacemos que la vista muestre los autores.
				ModifyAuthorView modifyAuthorView = (ModifyAuthorView) views[3];
				modifyAuthorView.showAuthors();
			}
		});
		btnUpdateAuthor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnUpdateAuthor.setForeground(new Color(240, 176, 72));
		btnUpdateAuthor.setBackground(new Color(255, 255, 255));
		btnUpdateAuthor.setBounds(87, 389, 132, 60);
		contentPane.add(btnUpdateAuthor);
		
		btnDeleteAuthor = new JButton("Eliminar");
		btnDeleteAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Recuperamos los autores.
				miAuthorControler.showDeleteAuthors();
				miSwitch.switchViews(0, 4);
				// Hacemos que la vista muestre los autores.
				DeleteAuthorView deleteAuthorView = (DeleteAuthorView) views[4];
				deleteAuthorView.showAuthors();
			}
		});
		btnDeleteAuthor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDeleteAuthor.setForeground(new Color(240, 176, 72));
		btnDeleteAuthor.setBackground(new Color(255, 255, 255));
		btnDeleteAuthor.setBounds(273, 389, 132, 60);
		contentPane.add(btnDeleteAuthor);
		
		btnShowAuthors = new JButton("Leer");
		btnShowAuthors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Recuperamos los autores.
				miAuthorControler.showAuthors();
				miSwitch.switchViews(0, 2);
				// Hacemos que la vista muestre los autores.
				ShowAuthorsView authorsView = (ShowAuthorsView) views[2];
				authorsView.showAuthors();
			}
		});
		btnShowAuthors.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnShowAuthors.setForeground(new Color(240, 176, 72));
		btnShowAuthors.setBackground(new Color(255, 255, 255));
		btnShowAuthors.setBounds(273, 274, 132, 60);
		contentPane.add(btnShowAuthors);
		
		btnDeleteBook = new JButton("Eliminar");
		btnDeleteBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Recuperamos los libros.
				miBookController.showDeleteBooks();
				miSwitch.switchViews(0, 8);
				// Hacemos que la vista muestre los libros.
				DeleteBookView deleteBookView = (DeleteBookView) views[8];
				deleteBookView.showBooks();
			}
		});
		btnDeleteBook.setForeground(new Color(240, 176, 72));
		btnDeleteBook.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDeleteBook.setBackground(new Color(255, 255, 255));
		btnDeleteBook.setBounds(736, 389, 132, 60);
		contentPane.add(btnDeleteBook);
		
		btnShowBooks = new JButton("Leer");
		btnShowBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Recuperamos los libros.
				miBookController.showBooks();
				miSwitch.switchViews(0, 6);
				// Hacemos que la vista muestre los libros.
				ShowBooksView showBooksView = (ShowBooksView) views[6];
				showBooksView.showBooks();
			}
		});
		btnShowBooks.setForeground(new Color(240, 176, 72));
		btnShowBooks.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnShowBooks.setBackground(new Color(255, 255, 255));
		btnShowBooks.setBounds(736, 274, 132, 60);
		contentPane.add(btnShowBooks);
		
		btnNewBook = new JButton("Crear");
		btnNewBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miSwitch.switchViews(0, 5);
			}
		});
		btnNewBook.setForeground(new Color(240, 176, 72));
		btnNewBook.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewBook.setBackground(new Color(255, 255, 255));
		btnNewBook.setBounds(550, 274, 132, 60);
		contentPane.add(btnNewBook);
		
		btnUpdateBook = new JButton("Actualizar");
		btnUpdateBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Recuperamos los libros.
				miBookController.showModifyBooks();
				miSwitch.switchViews(0, 7);
				// Hacemos que la vista muestre los libros.
				ModifyBookView modifyBookView = (ModifyBookView) views[7];
				modifyBookView.showBooks();
			}
		});
		
		btnUpdateBook.setForeground(new Color(240, 176, 72));
		btnUpdateBook.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnUpdateBook.setBackground(new Color(255, 255, 255));
		btnUpdateBook.setBounds(550, 389, 132, 60);
		contentPane.add(btnUpdateBook);
	}
}
