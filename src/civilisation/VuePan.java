package civilisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class VuePan extends JPanel {

	// nombre de colonnes
		public static final int colonnes = 180;
		// nombre de lignes
		public static final int lignes = 180;
		// taille des cases en pixels
		public static final int tailleCases = 70;
	public VuePan(){

this.setLocation(new Point(10,10));


Color c=new Color(Color.TRANSLUCENT);
		setBackground(c);
		this.setPreferredSize(new Dimension(10,10));

		
	}




public void paintComponent(Graphics g) {
	super.paintComponent(g);
	this.show(true);
	/*for(int i=0;i<listePersos.size();i++) {
		
		this.setCases(getPersoCaseX(i),getPersoCaseY(i),Cases.ZoneBlanche);*/
	
}
}