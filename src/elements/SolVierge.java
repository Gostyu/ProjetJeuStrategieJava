package elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class SolVierge extends TypeCase {

	public SolVierge(int x, int y) {
		setPosSolVierge(new Point(x,y));
		setType("sol");
	}
	public SolVierge(Point p) {
		setPosSolVierge(p);
		setType("sol");
	}
	public SolVierge(Point p, Graphics g) {
		setPosSolVierge(p);
		dessinSolVierge(g);
		setType("sol");
	}
	public void dessinSolVierge(Graphics g) {
//		super.dessin(getPosSolVierge(), g);
	}
	public Point getPosSolVierge() {
		return super.getPosition();
	}
	public boolean estTraversable() {
		return super.estTraversable();
	}
	public void setPosSolVierge(Point posSolVierge) {
		super.setPosition(posSolVierge);
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
