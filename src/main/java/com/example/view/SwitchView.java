package com.example.view;

import javax.swing.JFrame;


public class SwitchView {

	private JFrame[] views;
	
	public SwitchView(JFrame[] newViews) {
		this.views = newViews;
	}
	
  // Crearemos aquí el método para alternar entre vistas.
	public void switchViews(int origin, int destination) {
		views[origin].setVisible(false);
		views[destination].setVisible(true);
	}
	
}
