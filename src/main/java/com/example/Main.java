package com.example;

import javax.swing.JFrame;

import com.example.controller.*;
import com.example.model.*;
import com.example.view.*;

public class Main {
	
    
public static void main(String[] args) {
        
    	// Instanciamos las clases necesarias.
    	JFrame[] views = new JFrame[9];
    	views[0] = new InitialView();
    	views[1] = new CreateAuthorView();
    	views[2] = new ShowAuthorsView();
    	views[3] = new ModifyAuthorView();
    	views[4] = new DeleteAuthorView();
    	views[5] = new CreateBookView();
    	views[6] = new ShowBooksView();
    	views[7] = new ModifyBookView();
    	views[8] = new DeleteBookView();
    	
    	SwitchView switchView = new SwitchView(views);
    	
    	AutorDAO autorDAO = new AutorDAO();
    	AutorController autorController = new AutorController(autorDAO);
    	autorDAO.setController(autorController);
    	autorDAO.setViews(views);

    	LibroDAO libroDAO = new LibroDAO();
    	LibroController libroController = new LibroController(libroDAO);
    	libroDAO.setController(libroController);
    	libroDAO.setViews(views);
  
    	// Establecemos algubos atributos más para el InitialView.	
    	InitialView initialView = (InitialView) views[0];
    	initialView.setAutorController(autorController);
    	initialView.seLibroController(libroController);
    	initialView.setSwitch(switchView);
    	initialView.setViews(views);
    	
    	// Agregamos los objetos necesarios a todas las vistas.
    	for(int i = 1; i < 5; i++) {
    		((AuthorView)views[i]).setSwitch(switchView);
    		((AuthorView)views[i]).setController(autorController);
    	}
    	
    	for(int i = 5; i < views.length; i++) {
    		((BookView)views[i]).setSwitch(switchView);
    		((BookView)views[i]).setController(libroController);
    	}
    	
    	// El programa iniciará por InitialView.
    	views[0].setVisible(true);
    }
}