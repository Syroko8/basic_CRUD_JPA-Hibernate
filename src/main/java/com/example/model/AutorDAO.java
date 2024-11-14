package com.example.model;

import com.example.controller.AutorController;
import com.example.view.DeleteAuthorView;
import com.example.view.ModifyAuthorView;
import com.example.view.ShowAuthorsView;

import net.bytebuddy.asm.Advice.This;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JFrame;

public class AutorDAO {

	// Atributos.
    private EntityManagerFactory emf;
    private AutorController miAutorController;
    private EntityManager em;
    private JFrame[] views;
    
    // Constructor.
    public AutorDAO(){
        this.emf = Persistence.createEntityManagerFactory("example-unit");
    }
    
    public void setController(AutorController newAutorController) {
    	this.miAutorController = newAutorController;
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

    // Este método comprueba si existe un autor.
	public boolean existsAuthor(String name) {
		openEm();
		
		// Como el método find usa la clave primaria, y nosotros sabemos el nombre, usaremos una query.
		String jpql = "select a from Autor a where a.nombre = :nombre";

		TypedQuery<Autor> query = em.createQuery(jpql, Autor.class);
		
		// Insertamos el nombre.
		query.setParameter("nombre", name); 
		
		try {
			// Ejecutamos la query. Sabiendo que nos dará un resultado como máximo, utilizaremos getSingleResult().
			Autor author = query.getSingleResult();
			// Si no encuentra el autor, se producirá una excepción, que usaremos para devolver true o false.
			return true;
		} catch (Exception e) {
			// Si se produce la excepción, no existe el autor.
			return false;
		}finally {
			closeEm();
        }
	}

	// Método que añade un autor a la base de datos.
	public String addAuthor(String name) {
		
		try {
			openEm();
			// Creamos un autor con el nombre.
			Autor author = new Autor();
			author.setNombre(name);
			// Guardamos el autor.
			em.persist(author);
			em.getTransaction().commit();
			closeEm();
			return "El autor se ha creado correctamente.";

		} catch (Exception e) {
			// Si algo falla, además de devolver el mensaje, hacemos un rollback.
			em.getTransaction().rollback();
			return e.getMessage();
		} finally {
			closeEm();
        }
	}

	// Método que devuelve todos los autores existentes.
	public List<Autor> getAuthors() {
		openEm();
		// Obtenemos los autores.
		List<Autor> authors = em.createQuery("from Autor", Autor.class).getResultList();
		closeEm();
		
		// Si la lista es nula, devolvemos un arrayList vacío.
		if(authors == null) {
			return new ArrayList<Autor>();
		}
		
		return authors;
	}
	
	// Método que adquiere todos los autores y los introduce en el objeto de la vista ShowAuthorsView.
	public void getAuthorsForShow() {
		
		try {

			ShowAuthorsView showAuthorsView = (ShowAuthorsView) views[2];
			// Los mandamos a la vista.
			showAuthorsView.setAuthors(getAuthors());
		
		} catch (Exception e) {
			// Si ocurre alguna excepción, la mostramos en la vista.
			ShowAuthorsView showAuthorsView = (ShowAuthorsView) views[2];
			showAuthorsView.showresult(e.getMessage());
		}	
	}
	// Método que adquiere todos los autores y los introduce en el objeto de la vista ModifyAuthorView.
	public void getAuthorsForModify() {
		
		try {

			ModifyAuthorView modifyAuthorView = (ModifyAuthorView) views[3];
			modifyAuthorView.setAuthors(getAuthors());
	
		} catch (Exception e) {
			// Si ocurre alguna excepción, la mostramos en la vista.
			ModifyAuthorView modifyAuthorView = (ModifyAuthorView) views[3];
			modifyAuthorView.showresult(e.getMessage());
		}
	}
	
	// Método que introduce los cambios realizados en un autor en la base de datos.
	public String modifyAuthor(Long authorId, String name) {
		
		Autor author = new Autor();
		author.setId(authorId);
		author.setNombre(name);
		
		try {
			
			// Obtenemos los libros del autor seleccionado.
			openEm();
			List<Libro> books = em.find(Autor.class, authorId).getLibros();
			
			// Los introducimos en el autor y guardamos el cambio en la base de datos.
			author.setLibros(books);
			em.merge(author);
			// Hacemos el commit.
			em.getTransaction().commit();
			closeEm();
			
		} catch (Exception e) {
			// Hacemos un rollback y devolvemos el mensaje de error.
			em.getTransaction().rollback();
			closeEm();
			return e.getMessage();
		}

		return "Se ha modificado el autor.";
	}
	
	// Método que adquiere todos los autores y los introduce en el objeto de la vista DeleteAuthorView.
	public void getAuthorsForDelete() {

		try {

			DeleteAuthorView deleteAuthorView = (DeleteAuthorView) views[4];
			deleteAuthorView.setAuthors(getAuthors());
	
		} catch (Exception e) {
			// Si ocurre alguna excepción, la mostramos en la vista.
			DeleteAuthorView deleteAuthorView = (DeleteAuthorView) views[4];
			deleteAuthorView.showresult(e.getMessage());
		}
	}

	// Método que elimina un autor de la base de datos por su id.
	public String deleteAuthor(Long selectedAuthorId) {
		
		try {
			openEm();
			// Obtenemos el autor y lo eliminamos.
			Autor author = em.find(Autor.class, selectedAuthorId);
			em.remove(author);
			// Hacemos un commit.
			em.getTransaction().commit();
			closeEm();
		} catch (Exception e) {
			// Hacemos un rollblack y devolvemos el mensaje de error.
			em.getTransaction().rollback();
			closeEm();
			return e.getMessage();
		}
		
		return "Se ha eliminado el autor correctamente";
	}

}
