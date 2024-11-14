package com.example.controller;

import com.example.model.Autor;
import com.example.model.AutorDAO;

public class AutorController {

	// Atributos.
	private AutorDAO miAutorDAO;
	
	public AutorController(AutorDAO newDAO) {
		this.miAutorDAO = newDAO;
	}
	
	// Este método comprueba si un autor existe por su nombre.
	public boolean existsAuthor(String name) {
		return this.miAutorDAO.existsAuthor(name);		
	}

	public String addAuthor(String name) {
		return this.miAutorDAO.addAuthor(name);
	}

	public void showAuthors() {
		miAutorDAO.getAuthorsForShow();
	}

	public void showModifyAuthors() {
		miAutorDAO.getAuthorsForModify();
	}

	public String modifyAuthor(Long authorId, String name) {
		return miAutorDAO.modifyAuthor(authorId, name);
	}

	public String deleteAuthor(Long selectedAuthorId) {
		return miAutorDAO.deleteAuthor(selectedAuthorId);
	}

	public void showDeleteAuthors() {
		miAutorDAO.getAuthorsForDelete();
	}


}
