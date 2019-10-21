package elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.SSLException;

import affichage.AffichageMap;
import civilisation.Personnages;
import observer.Observateur;

public class Boue extends TypeCase {
	private Personnages unite;
	int timeout;
	public Boue(int x,int y) {
		setType("boue");
		setPosBoue(new Point(x,y));
		setCouleurBoue(new Color(139,69,19)); //couleur marron
	}
	//dessine un carré marron de 70*70
	public void dessinBoue(Graphics2D g) {
		super.dessin(getCouleurBoue(), getPosBoue(), g);
	}
	public Point getPosBoue() {
		return super.getPosition();
	}
	public void setPosBoue(Point posBoue) {
		super.setPosition(posBoue);
	}
	public Color getCouleurBoue() {
		return super.getCouleur();
	}
	public void setCouleurBoue(Color couleurBoue) {
		super.setCouleur(couleurBoue);
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
	public Personnages getUnite() {
		return unite;
	}
	public void setUnite(Personnages unite) {
		this.unite = unite;
	}
	public void ralentir() {
		for(int i=0; i<AffichageMap.getListePersos().size(); i++ ) {
			int compteur=0;
			Personnages p= AffichageMap.getListePersos().get(i);
			if(getPosBoue().x==p.getPositionCaseX() && getPosBoue().y==p.getPositionCaseY() && compteur%2==0) {
				System.out.println("Une unité marche dans la boue");
			}
			compteur++;
		}
	}
}
