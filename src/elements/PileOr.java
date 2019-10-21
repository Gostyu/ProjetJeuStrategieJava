package elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class PileOr extends TypeCase {
	private int pieces = 100;
	public PileOr(int x, int y) {
		setCouleurPile(Color.YELLOW);
		setPosPileOr(new Point(x,y));
		setType("pile");
	}
	//dessine un carre jaune de 70*70
	public void dessinPileOr(Graphics2D g) {
		super.dessin(getCouleurPile(),getPosPileOr(),g);
	}
	public int getQuantiteOr() {
		return pieces;
	}
	public void setQuantiteOr(int nb) {
		pieces = nb;
	}
	//getters & setters
	public Color getCouleurPile() {
		return super.getCouleur();
	}
	public void setCouleurPile(Color couleurPile) {
		super.setCouleur(couleurPile);
	}
	public boolean estTraversable() {
		return super.estTraversable();
	}
	public boolean nonTraversable() {
		return !super.estTraversable();
	}
	public Point getPosPileOr() {
		return super.getPosition();
	}
	public void setPosPileOr(Point posPileOr) {
		super.setPosition(posPileOr);
	}
	public String getType() {
		return super.getTypeCase();
	}
	public void setType(String type) {
		super.setTypeCase(type);
	}
	@Override
	public String toString() {
		return getType();
	}

}
