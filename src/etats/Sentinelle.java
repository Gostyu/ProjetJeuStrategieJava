package etats;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import affichage.AffichageMap;
import civilisation.Personnages;
import elements.MaisonMere;

public class Sentinelle implements EtatPersonnage {
	private Personnages perso;
	private Personnages cible;
	private int compteur = 1;

	public Sentinelle(Personnages perso) {
		this.perso = perso;
		surveille();

	}

	public boolean estDansZone(Personnages p) {

		for (int i = perso.getPositionCaseX() - 3; i <= perso.getPositionCaseX() + 3; i++) {
			for (int j = perso.getPositionCaseY() - 3; j <= perso.getPositionCaseY() + 3; j++) {
				if (p.getPositionCaseX() == i && p.getPositionCaseY() == j) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void attaque() {
		// TODO Auto-generated method stub

	}

	public void surveille() {

		Timer tic = new Timer();

		tic.schedule(new TimerTask() {
			@Override
			public void run() {

				if (perso.getEtatCourant() == perso.getEtatSentinelle()) {

					if (AffichageMap.getListePersos() != null) {
						for (int i = 0; i < AffichageMap.getListePersos().size(); i++) {
							if (AffichageMap.getListePersos().get(i).getEquipe() != perso.getEquipe()
									&& estDansZone(AffichageMap.getListePersos().get(i))
									&& AffichageMap.getListePersos().get(i) != perso)
								cible = AffichageMap.getListePersos().get(i);
						}
						if (cible != null) {
							// perso.setPositionDestination(new Point(perso.getPositionCaseX(),
							// perso.getPositionCaseY()));

							cible.estAttaque(1);
							if (cible.getPv() > 0) {
								System.out.println(cible.getPv());

							} else {

								AffichageMap.getListePersos().remove(cible);
								cible = null;

								/*
								 * compteur=1; surveille2(); cancel();
								 */

							}

						} else {

							Point p = new Point(perso.getPositionCaseX(), perso.getPositionCaseY());

							if (compteur == 1) {

								if (perso.getTabPosSentinelle()[0] != null) {

									if (p.x != perso.getTabPosSentinelle()[0].x
											|| p.y != perso.getTabPosSentinelle()[0].y) {

										perso.setPositionDestination(perso.getTabPosSentinelle()[0]);

									} else if (p.x == perso.getTabPosSentinelle()[0].x
											&& p.y == perso.getTabPosSentinelle()[0].y) {

										cancel();
										compteur = 2;// ça remet le compteur a 2 meme si surveille 2 est activée et le
														// compteur==1
										surveille2();
										// System.out.println("je lance");
									}

								}
								perso.move();
							}

						}

					}

				}

			}

		}, 0, 1000);

	}

	public void surveille2() {

		Timer tic = new Timer();

		tic.schedule(new TimerTask() {
			@Override
			public void run() {

				if (perso.getEtatCourant() == perso.getEtatSentinelle()) {
					/*
					 * MaisonMere m=(MaisonMere)AffichageMap.getElements().get(0);
					 * if(perso.getPositionCaseX()==m.getPosition().getX()+2) { if(m.getFonds()>0) {
					 * m.setFonds(m.getFonds()-1);
					 * 
					 * System.out.println(m.getFonds()); } }
					 */

					if (AffichageMap.getListePersos() != null) {
						for (int i = 0; i < AffichageMap.getListePersos().size(); i++) {
							if (AffichageMap.getListePersos().get(i).getEquipe() != perso.getEquipe()
									&& estDansZone(AffichageMap.getListePersos().get(i))
									&& AffichageMap.getListePersos().get(i) != perso)
								cible = AffichageMap.getListePersos().get(i);
						}
						if (cible != null) {
							// perso.setPositionDestination(new Point(perso.getPositionCaseX(),
							// perso.getPositionCaseY()));

							cible.estAttaque(1);
							if (cible.getPv() > 0) {
								System.out.println(cible.getPv());

							} else {

								AffichageMap.getListePersos().remove(cible);
								cible = null;
								System.out.println("dd");
								/*
								 * cancel(); compteur=2; surveille();
								 */

							}

						} else {

							Point p = new Point(perso.getPositionCaseX(), perso.getPositionCaseY());

							if (compteur == 2 && p != null) {
								// System.out.println(perso.getTabPosSentinelle()[1]+" "+p);
								if (p.x != perso.getTabPosSentinelle()[1].x
										|| p.y != perso.getTabPosSentinelle()[1].y) {
									perso.setPositionDestination(perso.getTabPosSentinelle()[1]);

								} else if (p.x == perso.getTabPosSentinelle()[1].x
										&& p.y == perso.getTabPosSentinelle()[1].y) {

									// ça remet le compteur a 2 meme si surveille 2 est activée et le compteur==1
									cancel();
									compteur = 1;
									surveille();
									// System.out.println("je relance");
									// System.out.println(compteur);

								}
								perso.move();
							}
					  }

				   }
				}

			}

		}, 0, 1000);

	}

	@Override
	public void recolte() {
		// TODO Auto-generated method stub

	}

	@Override
	public void estAttaque(int degats) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {

	}

}
