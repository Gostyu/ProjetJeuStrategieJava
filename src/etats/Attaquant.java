package etats;

import java.awt.Point;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import affichage.AffichageMap;
import civilisation.Personnages;
import civilisation.PointT;


public class Attaquant implements EtatPersonnage {
	private Personnages perso;
	private Personnages cible;
	private Queue<PointT> pile;
  
	

	public Attaquant(Personnages perso) {
		this.perso=perso;
		surveille();
		
	}
	public boolean estDansZone(Personnages p) {

		for(int i=perso.getPositionCaseX()-3;i<=perso.getPositionCaseX()+3;i++) {
			for(int j=perso.getPositionCaseY()-3;j<=perso.getPositionCaseY()+3;j++) {
				if(p.getPositionCaseX()==i && p.getPositionCaseY()==j) {
					return true;
				}
			}
		}
		return false;
	}
	
	

	

	@Override
	public void attaque() {
		
		
	}
	
	public void surveille() {
	
		Timer toc = new Timer();
		toc.schedule(new RemindTask(), 0, 500);

		}
	
		class RemindTask extends TimerTask {
		
		public RemindTask() {

		}
		@Override
		public void run() {
		if(perso.getEtatCourant()==perso.getEtatAttaquant()) {
	if(AffichageMap.getListePersos()!=null) {
		for(int i=0;i<AffichageMap.getListePersos().size();i++) {
			if(AffichageMap.getListePersos().get(i).getEquipe()!=perso.getEquipe() && estDansZone(AffichageMap.getListePersos().get(i)))
			cible=AffichageMap.getListePersos().get(i);
		
		}
		if(cible!=null) {
			
			if(cible.getPositionCaseX()==perso.getPositionCaseX()+1 || cible.getPositionCaseX()==perso.getPositionCaseX()-1) {
				cible.estAttaque(1);
				if(cible.getPv()!=0) {
					System.out.println(cible.getPv());
					//cible.setEtat(cible.getEtatMort());
				}else {
					AffichageMap.getListePersos().remove(cible);
				}
				
			}
			
		if(cible.getPositionCaseX()>perso.getPositionCaseX()) {
			perso.setPositionDestination(new Point(cible.getPositionCaseX()-1,cible.getPositionCaseY()));
		}
		if(cible.getPositionCaseX()<perso.getPositionCaseX()) {
			perso.setPositionDestination(new Point(cible.getPositionCaseX()+1,cible.getPositionCaseY()));
		}
			perso.move();
			
			
				}
	}

		}

		/*if(perso!=null) {
			
		if(perso.getEtatCourant()==perso.getEtatAttaquant()) {
		
				
				//for(int i=0;i<c.getPersos().size();i++) {
					/*if(c.getPersos(1).getEquipe()!=perso.getEquipe()&& c.getPersos().get(1).getPositionCaseX()==perso.getPositionCaseX()) {
						System.out.println("on est la");
						perso.setPositionCaseX(1);
					}*/
				}
			
		}
		


	@Override
	public void recolte() {
		System.out.println("cette unité ne recolte pas!");
		
	}



	@Override
	public void estAttaque(int degats) {
		perso.setPv(perso.getPv()-degats);
		
	}


	@Override
	public void move() {

		
	}









}
