package civilisation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import affichage.AffichageMap;
import elements.Boue;
import elements.PileOr;
import elements.Teleporteur;
import elements.TypeCase;
import etats.Attaquant;
import etats.EtatPersonnage;
import etats.Mort;
import etats.Normal;
import etats.Recolteur;
import etats.Sentinelle;
import observer.Observateur;

public class Personnages implements Sujet {
	// array sauvegarde un chemin plus court de dijskra pour chaque this
	private int[][] array;
	// classe point pour chemin le plus court

	// Des vecteurs de déplacement haut, droite, bas, gauche
	private PointT[] directions = new PointT[] { new PointT(-1, 0, 0), new PointT(0, -1, 0), new PointT(1, 0, 0),
			new PointT(0, 1, 0) };

	// Notre pile de ce qu'il reste à explorer
	private Queue<PointT> pile;
	private EtatPersonnage sentinelle;
	private EtatPersonnage recolteur;
	private EtatPersonnage attaquant;
	private EtatPersonnage normal = new Normal(this);
	private EtatPersonnage mort;
	private EtatPersonnage etat = normal;
	private int pv = 15;
	private int equipe;// id de l'équipe du joueur
	private String nom;
	private Point position;
	private int positionCaseX;
	private int positionCaseY;
	private Point positionDestination;
	private Boolean selected = false;// si le perso est selectionné ou non
	private List<Observateur> observateurs;
	private Point posPrecedente;
	private boolean attaque = false;// savoir si le bouton attaquant est appuyé ou pas
	private boolean protege = false;// savoir si le bouton sentinelle est appuyé ou pas
	private boolean recolte = false;
	private static int time = 500;
	private Point[] tabPosSentinelle = new Point[2];
	private int compteur;
	/* Constructeur de la classe */
	public Personnages(int pv, Point position, int equipe) {
		super();
		positionCaseX = position.x;
		positionCaseY = position.y;
		observateurs = new ArrayList<Observateur>();
		sentinelle = new Sentinelle(this);
		recolteur = new Recolteur(this);
		attaquant = new Attaquant(this);
		// normal=new Normal(this);
		mort = new Mort(this);
		this.position = position;
		this.pv = pv;
//		this.nom = nom;
		this.equipe = equipe;
		this.selected = false;

		// si pv egaux a 0 décés
		if (this.pv == 0) {
			etat = mort;
		}
	}

	public void setAttaque(boolean attaque) {
		this.attaque = attaque;
	}

	public boolean getAttaque() {
		return this.attaque;
	}

	// permet de changer l'etat du personnage meme pour les objets EtatPersonnage
	public void setEtat(EtatPersonnage e) {
		this.etat = e;
		System.out.println("Etat de l'unité : " + getEtatCourant());
	}

	public EtatPersonnage getEtatCourant() {
		return etat;
	}

	// change l'etat d'une unité
	public void nouvelEtat(EtatPersonnage e) {
		setEtat(e);
	}

	public void attaque() {

		etat.attaque();

		actualiserDonnees();
	}

	public void surveille() {
		etat.surveille();
		actualiserDonnees();
	}

	public void recolte() {
		etat.recolte();
		actualiserDonnees();
	}

	public void seDeplace() {
		/*
		 * lorsque j'appuie sur le bouton attaque et qu'un perso est selectionner
		 * attaque devient true si deselection attaque devient false si attaque=true on
		 * attend deux cliques le personnage fais le mouvement normalement et a l'arrivé
		 * dans la case cliqué on change son etat en attaquant
		 * 
		 */
		PileOr mine;
		if (recolte == true) {
			if (selected == true && AffichageMap.compteur == 1) {
				for (int i = 0; i < AffichageMap.getElements().size(); i++) {
					if (AffichageMap.getElements().get(i).getTypeCase() == "pile" && AffichageMap.getElements().get(i).getPosition().x==getPositionDestination().x && AffichageMap.getElements().get(i).getPosition().y==getPositionDestination().y) {
						//TypeCase e = AffichageMap.getElements().get(i);
						System.out.println("oui");
						etat=recolteur;
						recolte=false;
					}
				}
       
				
			}
		}
		if (protege == true) {
			if(selected==true && AffichageMap.compteur==1) {
				if (getPositionDestination() != null) {
				tabPosSentinelle[0] = getPositionDestination();
				}
			}
			if (selected == true && AffichageMap.compteur == 2) {
				/*if (getPositionDestination() != null) {
				if (AffichageMap.compteur == 1) {
					System.out.println("sisso");
				    /*tabPosSentinelle[0] = getPositionDestination();*/
					//}
				//}
				if (getPositionDestination() != null) {
			
						tabPosSentinelle[1] = getPositionDestination();
				
						etat=sentinelle;
					
						protege=false;
						
				}
		
			}
		} else if (attaque == true) {
			if (selected == true && AffichageMap.compteur == 2) {

				// setPositionDestination(new Point(this.getPositionDestination().x,
				// this.getPositionDestination().y));
				move();
				AffichageMap.compteur = 0;
			}
		} else {
			attaque = false;
			etat.move();
		}

		// System.out.println(getPositionDestination());
		// notifyObservateur();
	}

	public void estAttaque(int degats) {
		etat.estAttaque(degats);
		if (pv > 0) {
			pv = pv - degats; // ajouter timmer pour enlever toutes les 1 secondes
		}
		actualiserDonnees();
	}

	// ces methodes return l'etat actuel du personnage
	public EtatPersonnage getEtatSentinelle() {
		return sentinelle;
	}

	public EtatPersonnage getEtatRecolteur() {
		return recolteur;
	}

	public EtatPersonnage getEtatNormal() {
		return normal;
	}

	public EtatPersonnage getEtatAttaquant() {
		return attaquant;
	}

	//
	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	//
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	//
	public void setEquipe(int id) {
		equipe = id;
	}

	public int getEquipe() {
		return equipe;
	}

	//
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	//
	public void setPositionX(int position) {
		this.position.x = position;
	}

	public void setPositionY(int position) {
		this.position.y = position;
	}

	// position du this en cases X
	public void setPositionCaseX(int newPositionX) {
		setPositionX(newPositionX * 70);
	}

	public int getPositionCaseX() {
		return getPosition().x / 70;
	}

	// position du this en cases Y
	public void setPositionCaseY(int newPositionY) {
		setPositionY(newPositionY * 70);
	}

	public int getPositionCaseY() {
		return getPosition().y / 70;
	}

	//
	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	@Override
	// Ajouter Observateurs
	public void ajouterObservateur(Observateur o) {
		observateurs.add(o);
	}

	@Override
	// Supprimer Observateurs
	public void supprimerObservateur(Observateur o) {
		int i = observateurs.indexOf(o);
		if (i >= 0) {
			observateurs.remove(i);
		}
	}

	@Override
	// notifier Observateurs
	public void notifyObservateur() {
		for (int i = 0; i < observateurs.size(); i++) {
			Observateur observateur = (Observateur) observateurs.get(i);
			observateur.actualiser();
		}

	}

	// Appelle juste notifyObservateurs..
	public void actualiserDonnees() {
		notifyObservateur();
	}

	public int[][] getArray() {
		return array;
	}

	public void setArray(int[][] array) {
		this.array = array;
	}

	public Queue<PointT> getPile() {
		return pile;
	}

	public void setPile(Queue<PointT> pile) {
		this.pile = pile;
	}

	public PointT[] getDirections() {
		return directions;
	}

	public Point getPosPrecedente() {
		return posPrecedente;
	}

	public void setPosPrecedente(Point posPrecedente) {
		this.posPrecedente = posPrecedente;
	}

	public Point getPositionDestination() {
		return positionDestination;
	}

	public void setPositionDestination(Point positionDestination) {
		this.positionDestination = positionDestination;
	}

	public void setDirections(PointT[] directions) {
		this.directions = directions;
	}

	public void setTime(int t) {
		time = t;
	}

	public static int getTime() {
		return time;
	}

	// ralentit l'unite traversant une flaque de boue
//	public void ralentir(int x) {
//		setTimer(getTimer() * x);
//	}

	public void move() {

		// si la nouvelle position selectionnée est differente de la position du
		// personnage situer a la l'indice i de la liste des persos
		// on decremente de -1 la capacitée de la case precedente si celle ci est
		// superieur a 0
		// if (this.getPosition() != new Point(/*posCaseX*/0,/*posCaseY*/0)) {
		// if (capaciteCase[(pl.get(i).x) * (pl.get(i).y)] > 0) {
		// capaciteCase[(pl.get(i).x) * (pl.get(i).y)] -= 1;
		// System.out.println(capaciteCase[(pl.get(i).x) * (pl.get(i).y)]);
		// }
		// }
		// on modifie la position du this i dans la liste
		// pl.set(i, new Point(/*posCaseX*/0,/*posCaseY*/0));
		// System.out.println(this.getPositionDestination());
		RechercheEtExplore(new Point(this.getPositionDestination().x, this.getPositionDestination().y));
		// test(i,lignes,colonnes,getPersoCaseX(0),getPersoCaseY(0),posCaseX,posCaseY,new
		// int[][] {{1,0},{0,1}});
		if (attaque == true) {
			etat = attaquant;
			attaque = false;
		}
		
	}

	// fonction d'exploration
	private void explore(int[][] array) {
		/*
		 * 
		 * for(int i=0;i<array.length;i++) { for(int j=0;j<array.length;j++) {
		 * System.out.print(array[i][j]); } System.out.println(); }
		 * System.out.println();
		 */
		PointT point = this.getPile().poll();
		// On marque la profondeur (= l'éloignement de la sortie)
		array[point.x][point.y] = point.depth;
		if (this.getArray()[this.getPositionCaseX()][this.getPositionCaseY()] == 0)
			// Pour chaque voisin accessible
			for (int di = 0; di < this.getDirections().length; di++) {
				int nx = point.x + this.getDirections()[di].x;
				int ny = point.y + this.getDirections()[di].y;

				// Si depuis point, nous pouvons nous déplacer selon le vecteur directions[di]
				if (peutSeDeplacer(point.x, point.y, this.getDirections()[di].x, this.getDirections()[di].y))
					// Si il n'a pas déjà été visité, alors
					if (this.getArray()[nx][ny] == 0)
						// On l'ajoute à la pile
						this.getPile().add(new PointT(nx, ny, point.depth + 1));// add(new Personnages.PointT(nx, ny,
																				// point.depth + 1));
				// regler ce soucis
			}
	}

	private void RechercheEtExplore(Point p) {
		// Initialisation du tableau du labyrinthe
		this.setArray(new int[180][180]);// avoir taille fenetre
		// Initialisation de notre Queue avec une LinkedList comme implémentation
		// this.setPile(new LinkedList<PointT>());
		this.setPile(new LinkedList<PointT>());

		// On part de la sortie
		// C'est-à-dire du point vers lequel notre personnage doit aller !

		this.getPile().add(new PointT(p.x, p.y, 1));

		// Tant que l'on a des choses à explorer
		while (!this.getPile().isEmpty())

			explore(this.getArray());
		// Nous avons exploré (depuis la sortie), tout ce qui pouvait l'être
		// Si il n'y a pas de solution
		// Car l'exploration n'a pas atteint la case du personnage
		if (!this.getPile().isEmpty()) {
			// ### - Pas de solution

			return;
		}
		marchePerso();

	}

	public boolean peutSeDeplacer(int x, int y, int destX, int destY) {

		// if (x + destX >= 0 && y + destY >= 0 && x + destX <= 180 && y + destY <= 180
		// /*&& cases[x + destX + (y + destY) * lignes] != Cases.Obstacle*/) {
		// return true;
		// } else {
		// return false;
		// }
		int lignes = AffichageMap.getLignes();
		int colonnes = AffichageMap.getColonnes();
		TypeCase caseSuiv = AffichageMap.getCase((x + destX), (y + destY));
		if (x + destX >= 0 && y + destY >= 0 && x + destX <= colonnes && y + destY <= lignes
				&& caseSuiv!=null && caseSuiv.estTraversable()) {

			return true;
		}
//		System.out.println("x="+getPositionCaseX());
//		System.out.println("y"+getPositionCaseY());
		return false;

	}

	private void marchePerso() {

		this.setSelected(false);
		setTime(500);
		Timer tic= new Timer();
		tic.schedule(new TimerTask() {
			public void run() {
				int nx=0;
				int ny=0;
				// On va juste suivre à chaque étape, le plus petit voisin
				for (int di = 0; di < getDirections().length; di++) {
					 nx = getPositionCaseX() + getDirections()[di].x;
					 ny = getPositionCaseY() + getDirections()[di].y;
					 TypeCase flaque = AffichageMap.getCase(nx, ny);
						if(flaque!=null && flaque.getTypeCase()=="boue" && compteur%5==0) {
							System.out.println("Traverse de la boue");
							
						}
					// Si on peut s'y rendre
					if (peutSeDeplacer(getPositionCaseX(), getPositionCaseY(), getDirections()[di].x,
							getDirections()[di].y))
						// Si il a été visité par l'étalement
						if (getArray()[nx][ny] != 0)
							// Si il est plus proche de la sortie
							if (getArray()[nx][ny] < getArray()[getPositionCaseX()][getPositionCaseY()]) {		
								System.out.println(nx +","+ny);
								
								// On y va !
								setPositionCaseX(nx);// adapté a case
								setPositionCaseY(ny);// pareil
								// notifie les observateurs
								actualiserDonnees();
								// ### - Après déplacement
								return;
							}
					compteur++;
				}
				//fin du chemin
				TypeCase portail = AffichageMap.getCase(nx,ny-1);
				if(portail.getTypeCase()=="teleporteur") {
					Teleporteur porte = (Teleporteur) portail;
					porte.teleportation();
				}
				
//				System.out.println(getArray()[0][1]);

				cancel();
				if (getAttaque() == true) {
					setEtat(getEtatAttaquant());
					setAttaque(false);
				}
				// notifie les observateurs
				actualiserDonnees();
			}
		}, 0, getTime());
	}

	public boolean isProtege() {
		return protege;
	}

	public void setProtege(boolean protege) {
		this.protege = protege;
	}

	public Point[] getTabPosSentinelle() {
		return tabPosSentinelle;
	}

	public void setTabPosSentinelle(Point[] tabPosSentinelle) {
		this.tabPosSentinelle = tabPosSentinelle;
	}
	public boolean isRecolte() {
		return recolte;
	}

	public void setRecolte(boolean recolte) {
		this.recolte = recolte;
	}
}
