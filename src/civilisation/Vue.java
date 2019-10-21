package civilisation;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Vue extends JPanel {
	
	// nombre de colonnes
		public static final int colonnes = 180;
		// nombre de lignes
		public static final int lignes = 180;
		// taille des cases en pixels
		public static final int tailleCases = 70;
public Vue() {
}
	public void  paintComponent(Graphics g) {
		super.paintComponent(g);
		setPreferredSize(new Dimension(colonnes * tailleCases, lignes * tailleCases));
		
		
	}
}
