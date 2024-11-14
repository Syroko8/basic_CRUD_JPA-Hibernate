package com.example.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JFrame;

import com.example.controller.AutorController;
import com.example.controller.LibroController;
import com.example.view.DeleteBookView;
import com.example.view.ModifyAuthorView;
import com.example.view.ModifyBookView;
import com.example.view.ShowAuthorsView;
import com.example.view.ShowBooksView;

public class LibroDAO {

	// Atributos.
    private EntityManagerFactory emf;
    private LibroController miLibroController;
    private EntityManager em;
    private JFrame[] views;

    // Constructor.
    public LibroDAO(){
        this.emf = Persistence.createEntityManagerFactory("example-unit");
    }
    
    public void setController(LibroController newLibroController) {
    	this.miLibroController = newLibroController;
    }
    
    public void setViews(JFrame[] newViews){
    	this.views = newViews;
    }
    
    // Método que inicia la transacción.
    private void openEm() {
    	this.em = emf.createEntityManager();
    	this.em.getTransaction().begin();
    }
    
    // Método que cierra la transacción.
    private void closeEm() {
    	this.em.close();
    }
    
    // Método que obtiene un autor por su nombre.
    public Autor getAuthorByName(String name) {
    	openEm();
    	
		String jpql = "select a from Autor a where a.nombre = :nombre";

		TypedQuery<Autor> query = em.createQuery(jpql, Autor.class);
		
		// Insertamos el nombre.
		query.setParameter("nombre", name); 
		
		try {
			// Ejecutamos la query. Sabiendo que nos dará un resultado como máximo, utilizaremos getSingleResult().
			Autor author = query.getSingleResult();
			// Si no encuentra el autor, se producirá una excepción.
			return author;
		} catch (Exception e) {
			// Si se produce la excepción, no existe el autor.
			return null;
		} finally {
			em.close();
		}
    }

    // Método que añade un libro a la base de datos.
	public String addBook(String title, String pubYear, String authorName, String state) {
		// No comprobaremos que no existan libros con el mismo nombre.
		
		// Primero, obtendremos el autor del libro por el nombre, ya que lo necesitamos para crear el objeto Libro.
		Autor author = getAuthorByName(authorName);
		
		Libro book = new Libro();
		book.setTitulo(title);
		book.setFechaPublic(Integer.parseInt(pubYear));
		book.setEstado(state);
		// Si el autor es nulo, no existe en la base de datos, y no es válido.
		if(author == null) {
			return "El autor introducido no existe, introduzca un nombre válido." ;
		} else {
			book.setAutor(author);
		}
		
		// Introducimos el libro.
		try {
			openEm();
			em.persist(book);
			em.getTransaction().commit();
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			closeEm();
		}
		
		return "El libro ha sido introducido correctamente.";
	}

	// Método que recupera todos los libros.
	public List<Libro> getBooks() {
		openEm();
		// Obtenemos los libros.
		List<Libro> books = em.createQuery("from Libro", Libro.class).getResultList();
		closeEm();
		
		// Si la lista es nula, devolvemos un arrayList vacío.
		if(books == null) {
			return new ArrayList<Libro>();
		}
		
		return books;
		
	}
	
	// Método que introduce los libros recuperados en la vista ShowBooksView.
	public void getBooksForShow() {
		
		try {

			ShowBooksView showBooksView = (ShowBooksView) views[6];
			// Los mandamos a la vista.
			showBooksView.setBooks(getBooks());
		
		} catch (Exception e) {
			// Si ocurre alguna excepción, la mostramos en la vista.
			ShowBooksView showBooksView = (ShowBooksView) views[6];
			showBooksView.showresult(e.getMessage());
		}	
	}

	// Método que introduce los libros recuperados en la vista ModifyBookView. 
	public void getBooksForModify() {
		
		try {

			ModifyBookView modifyBookView = (ModifyBookView) views[7];
			// Los mandamos a la vista.
			modifyBookView.setBooks(getBooks());
		
		} catch (Exception e) {
			// Si ocurre alguna excepción, la mostramos en la vista.
			ModifyBookView modifyBookView = (ModifyBookView) views[7];
			modifyBookView.showresult(e.getMessage());
		}	
	}

	// Método que modifica los datos de un libro y lo sobreescribe.
	public String modifyBook(Long selectedBookId, String title, int year, String author, String state) {
		
		Libro book;
		
		try {
			// Obtenemos el libro de la base de datos.
			openEm();
			book = em.find(Libro.class, selectedBookId);
			closeEm();
		} catch (Exception e) {
			return e.getMessage();
		}
		
		// Introducimos el año.
		book.setFechaPublic(year);
		
		// Modificamos los campos necesarios.
		
		if(!title.equals("")) {
			book.setTitulo(title);
		}
		
		if(!author.equals("")) {
			Autor bookAuthor = getAuthorByName(author);
			// Si el autor existe (no es null), lo introducimos, si no, informamos al usuario.
			if(bookAuthor != null) {
				book.setAutor(bookAuthor);
			} else {
				return "No existe un autor con el nombre introducido.";
			}
		}
		
		if(!title.equals("")) {
			book.setTitulo(title);
		}
		
		if(!state.equals("")) {
			book.setEstado(state);
		}
		
		// Una vez realizados los cambios, introducimos el objeto en la base de datos.
		try {
			openEm();
			em.merge(book);
			em.getTransaction().commit();
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			closeEm();
		}
		
		return "El libro se ha modificado correctamente.";
	}
	
	// Método que introduce los libros recuperados en la vista ModifyBookView. 
	public void getBooksForDelete() {
		
		try {

			DeleteBookView deleteBookView = (DeleteBookView) views[8];
			// Los mandamos a la vista.
			deleteBookView.setBooks(getBooks());
		
		} catch (Exception e) {
			// Si ocurre alguna excepción, la mostramos en la vista.
			DeleteBookView deleteBookView = (DeleteBookView) views[8];
			deleteBookView.showresult(e.getMessage());
		}	
		
	}

	// Método que elimina un libro.
	public String deleteBook(Long id) {

		try {
			openEm();
			// Necesitamos introducir el libro a eliminar, por lo que primero lo obtendremos usando el id.
			Libro book = em.find(Libro.class, id);
			em.remove(book);
			em.getTransaction().commit();
	
			return "Se ha eliminado el libro correctamente.";

		} catch (Exception e) {
			return e.getMessage();
		} finally {
			closeEm();
		}
		
	}
	
	
	


}
