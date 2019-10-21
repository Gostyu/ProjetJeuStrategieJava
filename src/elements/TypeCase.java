package elements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public abstract class TypeCase {
	Point pos; //position
	int capacite;
	Color couleur;
	int tailleCase = 69; //en pixels
	boolean traversable = true;
	private String typeCase;
	public static enum choix{Arbre};
	public void dessin(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g2d.setColor(getCouleur());
		g2d.fillRect(getX(),getY(),tailleCase,tailleCase);
	}
	public void dessin(Color couleur,Point pos,Graphics2D g) {
//
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g.setColor(couleur);
		g.fillRect(getX(),getY(),tailleCase,tailleCase);
	}
	public void dessin(Point pos,Graphics g) {
		g.fillRect(getX(),getY(),tailleCase,tailleCase);
	}
	public boolean estTraversable() {
		return traversable;
	}
	public String getTypeCase() {
		return typeCase;
	}
	 void setTypeCase(String typeCase) {
		this.typeCase = typeCase;
	}
	void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	public Color getCouleur() {
		return couleur;
	}
	void setPosition(Point p) {
		pos = p;
	}
	public Point getPosition() {
		return pos;
	}
	public int getX() {
		return pos.x;
	}
	public int getY() {
		return pos.y;
	}
//	@Override
//	public String toString() {
//		return "la case est " + getTypeCase();
//	}
}
