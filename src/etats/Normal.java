package etats;

import java.util.Queue;

import civilisation.Civilisation;
import civilisation.Personnages;
import civilisation.PointT;

public class Normal implements EtatPersonnage{
	private Personnages perso;
	private Queue<PointT> pile;
	private Civilisation c;



	public Normal(Personnages perso) {
		this.perso = perso;

	}
	
	public void move() {
		perso.move();
	}

	@Override
	public void attaque() {
		//Notification:ne peut pas attaquer
		
	}

	@Override
	public void surveille() {
		// TODO Auto-generated method stub

	}

	@Override
	public void recolte() {
		// TODO Auto-generated method stub

	}

	@Override
	public void estAttaque(int degats) {
		// TODO Auto-generated method stub

	}



}
