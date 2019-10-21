package elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import affichage.AffichageMap;
import civilisation.Personnages;

public class Teleporteur extends TypeCase{
	public Teleporteur(Point p,Color teleporteur,Graphics2D g) {
		setCouleurTeleporteur(teleporteur);
		setPosTeleporteur(p);
		dessinTeleporteur(g);
		setType("teleporteur");
	}
	public Teleporteur(int x,int y) {
		setCouleurTeleporteur(Color.CYAN);
		setPosTeleporteur(new Point(x,y));
		setType("teleporteur");
	}
	public Teleporteur(Point p) {
		setCouleurTeleporteur(Color.CYAN);
		setPosTeleporteur(p);
		setType("teleporteur");
	}
	//dessine un carré gris noir de 70*70
	public void dessinTeleporteur(Graphics2D g) {
		super.dessin(getCouleurTeleporteur(), getPosTeleporteur(), g);
	}
	public Point getPosTeleporteur() {
		return super.getPosition();
	}
	public void setPosTeleporteur(Point posTeleporteur) {
		super.setPosition(posTeleporteur);
	}
	public Color getCouleurTeleporteur() {
		return super.getCouleur();
	}
	public void setCouleurTeleporteur(Color couleurTeleporteur) {
		super.setCouleur(couleurTeleporteur);
	}
	public String getType() {
		return super.getTypeCase();
	}
	public void setType(String type) {
		super.setTypeCase(type);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getType();
	}
	public void teleportation() {
		for(int i=0; i< AffichageMap.getListePersos().size(); i++) {
			Personnages p= AffichageMap.getListePersos().get(i);
//			System.out.println(getPosTeleporteur());
//			System.out.println(p.getPositionDestination());
//			System.out.println("(x,y)="+p.getPositionCaseX()+","+p.getPositionCaseY());
//			System.out.println("porte"+getPosTeleporteur());
			if(p.getPositionDestination()!=null && p.getPositionDestination().equals(getPosTeleporteur())) {
//				System.out.println(p.getPositionDestination());
//				if(p.getEquipe() == 1) {
//					System.out.println(p.getPosition());
//					if(i==0) {
						p.setPosition(new Point(8,171));
//					}
//					if(i==1) {
//						p.setPosition(new Point(35,170));
//					}
//				}else if(p.getEquipe() == 2){
////					
//				}
			}
		}
	}
	
}
