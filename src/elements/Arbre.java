package elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Arbre extends TypeCase{
	public Arbre(int x,int y) {
		setCouleurArbre(Color.GREEN);
		setPosArbre(new Point(x,y));
		setType("arbre");
	}
	//dessine un carre vert de 70*70
	public void dessinArbre(Graphics2D g) {
		super.dessin(getCouleurArbre(),getPosArbre(),g);
	}
	public Color getCouleurArbre() {
		return getCouleur();
	}
	public void setCouleurArbre(Color couleurArbre) {
		super.setCouleur(couleurArbre);
	}
	public void setPosArbre(Point posArbre) {
		super.setPosition(posArbre);
	}
	public Point getPosArbre() {
		return super.getPosition();
	}
	public String getType() {
		return super.getTypeCase();
	}
	public void setType(String type) {
		super.setTypeCase(type);
	}
	@Override
	public boolean estTraversable() {
		return !super.estTraversable();
	}
	@Override
	public String toString() {
		return getType();
	}
}
