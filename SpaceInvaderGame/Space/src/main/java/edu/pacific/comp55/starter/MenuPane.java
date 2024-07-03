package edu.pacific.comp55.starter;

import java.awt.*;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private final MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
//	private final GButton rect;

	public MenuPane(MainApplication app) {
		super();
		program = app;
		new MainMenu();
	}

	@Override
	public void showContents() {
//		program.add(rect);
	}

	@Override
	public void hideContents() {
//		program.remove(rect);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
//		if (obj == rect) {
//			program.switchToSome();
//		}
	}
}
