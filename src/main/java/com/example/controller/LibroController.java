package com.example.controller;

import com.example.model.LibroDAO;

public class LibroController {
	
	// Atributos.
		private LibroDAO miLibroDAO;

		// Constructor.
		public LibroController(LibroDAO newDAO) {
			this.miLibroDAO = newDAO;
		}

		public String addBook(String title, String pubYear, String author, String state) {
			return miLibroDAO.addBook(title, pubYear, author, state);
		}

		public void showBooks() {
			miLibroDAO.getBooksForShow();
		}

		public void showModifyBooks() {
			miLibroDAO.getBooksForModify();
			
		}

		public void showDeleteBooks() {
			miLibroDAO.getBooksForDelete();
		}

		public String modifyBook(Long selectedBookId, String title, int year, String author, String state) {
			return miLibroDAO.modifyBook(selectedBookId, title, year, author, state);
		}

		public String deleteBook(Long selectedAuthorId) {
			return miLibroDAO.deleteBook(selectedAuthorId);
		}

		
		
		
}
