package civilisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
//import javax.swing.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
//import javax.swing.Timer;

public class Fenetre extends JPanel implements MouseListener, ActionListener, MouseMotionListener, KeyListener {

	// array sauvegarde un chemin plus court de dijskra pour chaque perso
	private int[][] array;

	// classe point
	/*class PointT {
		public int x, y, depth;

		public PointT(int x, int y, int depth) {
			this.x = x;
			this.y = y;
			this.depth = depth;
		}
	}*/

	// Des vecteurs de déplacement haut, droite, bas, gauche
	private final PointT[] directions = new PointT[] { new PointT(-1, 0, 0), new PointT(0, -1, 0), new PointT(1, 0, 0),
			new PointT(0, 1, 0) };

	// Notre pile de ce qu'il reste à explorer
	private Queue<PointT> pile;

	// Là où nous allons marquer les distances qui séparent chaque case à la sortie

	// vue
	private int vueX = 0;
	private int vueY = 0;
	//keylistener
	private char c;
	private int f;
	// Liste de pesos
	protected ArrayList<Personnages> listePersos = new ArrayList<Personnages>();
	protected Point p;
	// coordonnées des points x et y du rectangle de selection
	private int selectX;
	private int selectY;
	private int destSelectX;
	private int destSelectY;
	// nombre de persos selectionnés
	private int count;
	private Image[] img = new Image[200];
	private Rectangle selection;
	private Point dessin;
	// position du clic X et Y
	protected int posClickX;
	protected int posClickY;
	// position cases X et Y
	protected int posCaseX;
	protected int posCaseY;
	private Timer timer;
	// nombre de colonnes
	public static final int colonnes = 180;
	// nombre de lignes
	public static final int lignes = 180;
	// taille des cases en pixels
	public static final int tailleCases = 70;
	// nombre de pixels separant chaque unitée dans une meme case du coté
	public static final int distance_unités_horizontale = tailleCases / 3;
	// nombre de pixels separant chaque unitée dans une meme case du sommet/bas de
	// la case
	public static final int distance_unités_verticale = tailleCases / 5;
	// capacitée case
	private int[] capaciteCase = new int[colonnes * lignes];
	// type de texte utilisé
	private static final Font FONT = new Font("Tahoma", Font.BOLD, 25);
	// le jeu
	private Civilisation game;
	// tableau de cases
	private Cases[] cases;
	int k;
	private ArrayList<Point> pl = new ArrayList<Point>();
	private ArrayList<int[][]> listeArray = new ArrayList<int[][]>();
	private Point[] memCasePrecedente;

	// constructeur
	public Fenetre(Civilisation game) {
//	    listePersos.add(new Personnages(13, "link", new Point(10, 10), false));
//		listePersos.add(new Personnages(13, "mario", new Point(80, 80), false));
//		listePersos.add(new Personnages(13, "luigi", new Point(10, 80), false));
//		listePersos.add(new Personnages(13, "tod", new Point(80, 10), false));
		// memoire de la case du perso
		this.memCasePrecedente = new Point[listePersos.size()];
		this.game = game;
		game.setSize(1000, 1000);
		this.cases = new Cases[lignes * colonnes];

		this.cases[2] = Cases.Obstacle;
		for(int i=0;i<6;i++) {
		
		this.cases[i+lignes*3]=Cases.Obstacle;
			
		}
		setBackground(Color.BLACK);

		setPreferredSize(new Dimension(colonnes * tailleCases, lignes * tailleCases));
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);

		// perso
		try {
			for (int i = 0; i < listePersos.size(); i++) {
				img[i] = ImageIO.read(new File("perso.gif"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < listePersos.size(); i++) {
			this.pl.add(new Point());
		}
		for (int i = 0; i < listePersos.size(); i++) {
			this.listeArray.add(array);
		}

	}

	public void setCases(int x, int y, Cases type) {
		cases[y * lignes + x] = type;

	}
	public void setCaseColor(Graphics g,int s) {
		g.setColor(Color.WHITE);
		g.fillRect(getPersoCaseX(s)*70,getPersoCaseY(s)*70, 70, 70);

		
	}

	public Cases getCases(int x, int y) {
		return cases[y * lignes + x];
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
	for(int j=0;j<listePersos.size();j++) {
			setCaseColor(g,j);
			
	}

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(vueX, vueY, 490, 490);

		// Rectangle de selection
		if (selection != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.RED);
			g2d.draw(selection);
			for (int i = 0; i < listePersos.size(); i++) {
				if (selectY < getPersoPixelY(i) && getPersoPixelY(i) < destSelectY + selectY
						&& selectX < getPersoPixelX(i) && getPersoPixelX(i) < destSelectX + selectX) {
					count = count + 1;
					listePersos.get(i).setSelected(true);

				}
			}
		}

		for (int i = 0; i < listePersos.size(); i++) {
			if (listePersos.get(i).getSelected() == true) {
				g.setColor(Color.RED);
				g.drawRect(getPersoPixelX(i), getPersoPixelY(i), 50, 50);
			}
		}

		for (int i = 0; i < listePersos.size(); i++) {
			g.drawImage(img[i], listePersos.get(i).getPosition().x, listePersos.get(i).getPosition().y, null);
		}
		// boucle sur chaque case et la dessine si elle n'est pas null
		for (int x = 0; x < colonnes; x++) {
			for (int y = 0; y < lignes; y++) {
				Cases type = getCases(x, y);
				if (type != null) {
					drawTile(x * tailleCases, y * tailleCases, type, g);
				}
			}
		}

		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		for (int x = 0; x < colonnes; x++) {
			for (int y = 0; y < lignes; y++) {
				g.drawLine(x * tailleCases, 0, x * tailleCases, getHeight());
				g.drawLine(0, y * tailleCases, getWidth(), y * tailleCases);
			}
		}

	}

	private void drawTile(int x, int y, Cases type, Graphics g) {

		switch (type) {

//		case ZoneSombre:
//
//			g.setColor(Color.BLACK);
//			g.fillRect(x, y, tailleCases, tailleCases);
//
//		case ZoneBlanche:
//			g.setColor(Color.WHITE);
//			g.fillRect(x, y, tailleCases, tailleCases);
//
//		case Arbre:
//			g.setColor(Color.GREEN);
//			g.fillRect(x, y, tailleCases, tailleCases);
//
//		case Obstacle:
//			g.setColor(Color.BLACK);
//			g.fillRect(x, y, tailleCases, tailleCases);
//			break;

		}
	}

	public int getPersoCaseX(int index) {
		// return persoPosX / 70;
		return (listePersos.get(index).getPosition().x) / 70;
	}

	public int getPersoCaseY(int index) {
		// return persoPosY / 70;
		return (listePersos.get(index).getPosition().y) / 70;
	}

	public void setPersoCaseX(int index, int posX) {
		listePersos.get(index).setPositionX(posX * 70);

	}

	public void setPersoCaseY(int index, int posY) {
		listePersos.get(index).setPositionY(posY * 70);

	}

	public int getPersoPixelX(int index) {
		return listePersos.get(index).getPosition().x;
	}

	public int getPersoPixelY(int index) {
		return listePersos.get(index).getPosition().y;
	}

	public void setK(int x) {
		this.k = x;
	}

	class RemindTask2 extends TimerTask {
		int r;

		public RemindTask2(int i) {

			this.r = i;
		}

		public void run() {
			// On va juste suivre à chaque étape, le plus petit voisin
			for (int di = 0; di < directions.length; di++) {
				int nx = getPersoCaseX(r) + directions[di].x;
				int ny = getPersoCaseY(r) + directions[di].y;

				// Si on peut s'y rendre
				if (peutSeDeplacer(getPersoCaseX(r), getPersoCaseY(r), directions[di].x, directions[di].y, r))
					// Si il a été visité par l'étalement
					if (listeArray.get(r)[nx][ny] != 0)
						// Si il est plus proche de la sortie
						if (listeArray.get(r)[nx][ny] < listeArray.get(r)[getPersoCaseX(r)][getPersoCaseY(r)]) {
							// On y va !

							setPersoCaseX(r, nx);
							setPersoCaseY(r, ny);
					
							repaint();

							// ### - Après déplacement
							return;
						}
			}
			if (getPersoCaseX(r) == pl.get(r).x && getPersoCaseY(r) == pl.get(r).y) {
				if (capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)] < 4) {
					if (capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)] == 0) {

						listePersos.get(r).getPosition().x = listePersos.get(r).getPosition().x;
						System.out.println(capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)]);
						System.out.println(listePersos.get(r).getPosition().x);

						// on increment de +1 la nouvelle case ou le perso est arriv

					}
					if (capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)] == 1) {

						listePersos.get(r).getPosition().x = listePersos.get(r).getPosition().x + 20;
						System.out.println(capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)]);
						System.out.println(listePersos.get(r).getPosition().x);

						// on increment de +1 la nouvelle case ou le perso est arriv

					}
					if (capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)] == 2) {

						listePersos.get(r).getPosition().y = listePersos.get(r).getPosition().y + 30;
						System.out.println(capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)]);
						System.out.println(listePersos.get(r).getPosition().x);

						// on increment de +1 la nouvelle case ou le perso est arriv

					}
					if (capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)] == 3) {

						listePersos.get(r).getPosition().x = listePersos.get(r).getPosition().x + 20;
						listePersos.get(r).getPosition().y = listePersos.get(r).getPosition().y + 30;
						System.out.println(capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)]);
						System.out.println(listePersos.get(r).getPosition().x);

						// on increment de +1 la nouvelle case ou le perso est arriv

					}
					capaciteCase[getPersoCaseX(r) * getPersoCaseY(r)] += 1;
				}
			}
			this.cancel();
			repaint();
		}
	}

	public void marchePerso(int i) {

		listePersos.get(i).setSelected(false);
		Timer tic = new Timer();
		tic.schedule(new RemindTask2(i) {
		}, 0, 500);

	};

	public void marche() {

		for (int i = 0; i < listePersos.size(); i++) {
			if (listePersos.get(i).getSelected() == true) {
				// si la nouvelle position selectionnée est differente de la position du
				// personnage situer a la l'indice i de la liste des persos
				// on decremente de -1 la capacitée de la case precedente si celle ci est
				// superieur a 0
				if (pl.get(i) != new Point(posCaseX, posCaseY)) {
					if (capaciteCase[(pl.get(i).x) * (pl.get(i).y)] > 0) {
						capaciteCase[(pl.get(i).x) * (pl.get(i).y)] -= 1;
						System.out.println(capaciteCase[(pl.get(i).x) * (pl.get(i).y)]);
					}
				}
				// on modifie la position du perso i dans la liste
				pl.set(i, new Point(posCaseX, posCaseY));
				RechercheEtExplore(i, pl.get(i));
				// test(i,lignes,colonnes,getPersoCaseX(0),getPersoCaseY(0),posCaseX,posCaseY,new
				// int[][] {{1,0},{0,1}});
			}

		}
	}

	void setCount(int x) {
		this.count = x;
	}

	public void mapDiscover(int x, int y, Cases type) {

		cases[x * y] = type;

	}

	// fonction d'exploration
	private void explore(int[][] array, int perso) {

		/*
		 * for(int i=0;i<array.length;i++) { for(int j=0;j<array.length;j++) {
		 * System.out.print(array[i][j]); } System.out.println(); }
		 * System.out.println();
		 */

		PointT point = pile.poll();
		// On marque la profondeur (= l'éloignement de la sortie)
		array[point.x][point.y] = point.depth;
		if (array[getPersoCaseX(perso)][getPersoCaseY(perso)] == 0)
			// Pour chaque voisin accessible
			for (int di = 0; di < directions.length; di++) {
				int nx = point.x + directions[di].x;
				int ny = point.y + directions[di].y;

				// Si depuis point, nous pouvons nous déplacer selon le vecteur directions[di]
				if (peutSeDeplacer(point.x, point.y, directions[di].x, directions[di].y, perso))
					// Si il n'a pas déjà été visité, alors
					if (array[nx][ny] == 0)
						// On l'ajoute à la pile
						pile.add(new PointT(nx, ny, point.depth + 1));

			}
	}

	public void RechercheEtExplore(int perso, Point p) {
		// Initialisation du tableau du labyrinthe
		listeArray.set(perso, new int[colonnes][lignes]);
		// Initialisation de notre Queue avec une LinkedList comme implémentation
		pile = new LinkedList<PointT>();

		// On part de la sortie
		// C'est-à-dire du point vers lequel notre personnage doit aller !

		pile.add(new PointT(p.x, p.y, 1));

		// Tant que l'on a des choses à explorer
		while (!pile.isEmpty())
			
			explore(listeArray.get(perso), perso);
		// Nous avons exploré (depuis la sortie), tout ce qui pouvait l'être
		// Si il n'y a pas de solution
		// Car l'exploration n'a pas atteint la case du personnage
		if (!pile.isEmpty()) {
			// ### - Pas de solution

			return;
		}

		marchePerso(perso);

	}

	public boolean peutSeDeplacer(int x, int y, int destX, int destY, int perso) {

		if (x + destX >= 0 && y + destY >= 0 && x + destX <= 180 && y + destY <= 180
				&& cases[x + destX + (y + destY) * lignes] != Cases.Obstacle) {
			return true;
		} else {
			return false;
		}

	}

	public void mouseClicked(MouseEvent e) {
		
		posClickX = e.getX();
		posClickY = e.getY();

		this.posCaseX = e.getX() / 70;
		this.posCaseY = e.getY() / 70;

		if (e.getButton() == MouseEvent.BUTTON2) {
//
//			if (cases[posCaseX + posCaseY * lignes] == Cases.Obstacle) {
//				cases[posCaseX + posCaseY * lignes] = Cases.ZoneBlanche;
//			} else {
//				cases[posCaseX + posCaseY * lignes] = Cases.Obstacle;
//			}
		}

		setClickX(e.getX());
		setClickY(e.getY());

		// Clic gauche de la souris
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (count != 0) {

				marche();

			}

			// Récupération de la position
			Point position = e.getPoint();

		}
		// clic de la souris droit
		if (e.getButton() == MouseEvent.BUTTON3) {

			for (int i = 0; i < listePersos.size(); i++) {
				listePersos.get(i).setSelected(false);
			}

		}

	};

	void setVueX(int x) {
		vueX = x;
	}

	void setVueY(int y) {
		vueY = y;
	}

	public void drawRe(int x, int y, Graphics g2d) {

		/*
		 * g2d.setColor(Color.WHITE); g2d.fillRect(x, y, 490, 490);
		 */

	}

	public void addNotify() {
		super.addNotify();
		requestFocus();

	}

	// J'utilise keyPressed pour reconnaitre la touche presser et deplacer la vue
	public void keyPressed(KeyEvent e) {

		int limiteVueX = colonnes * tailleCases - 350;
		int limiteVueY = lignes * tailleCases - 350;
		if (vueX < 0) {
			setVueX(0);
		}
		if (vueY < 0) {
			setVueY(0);
		}
		if (vueX + 350 > colonnes * tailleCases) {
			setVueX(limiteVueX);
		}
		if (vueY + 350 > lignes * tailleCases) {
			setVueY(limiteVueY);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			setVueX(this.vueX - 70);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			setVueX(this.vueX + 70);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			setVueY(this.vueY - 70);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			setVueY(this.vueY + 70);
		}
	}

	public void keyReleased(KeyEvent e) {
		repaint();
	}

	public void keyTyped(KeyEvent e) {

		c = e.getKeyChar();
		f = e.getKeyChar();
		System.out.println(f);
		System.out.println(c);

		repaint();

	}

	public void setPosPerso(int index, Point nouvellePos) {
		listePersos.get(index).setPosition(nouvellePos);
		;
	}

	public Point getPosPerso(int index) {
		return listePersos.get(index).getPosition();
	}

	public void setClickX(int x) {
		this.posClickX = x;
	}

	public void setClickY(int x) {
		this.posClickY = x;
	}
	@Override
	public void mouseExited(MouseEvent e) {

	};
	@Override
	public void mousePressed(MouseEvent e) {
		// Position du point de la souris
		dessin = e.getPoint();
		// Rectangle
		selection = new Rectangle(dessin);
	};
	@Override
	public void mouseReleased(MouseEvent e) {

		selection = null;
		repaint();
	};

	public void mouseEntered(MouseEvent e) {

	};
	@Override
	public void mouseDragged(MouseEvent e) {
		// minimum entre dessin.x ou y et poistion du clic a la coordonnée x ou y
		selectX = (int) Math.min(dessin.x, e.getX());
		selectY = (int) Math.min(dessin.y, e.getY());
		destSelectX = (int) Math.abs(e.getX() - dessin.x);
		destSelectY = (int) Math.abs(e.getY() - dessin.y);
		selection.setBounds(selectX, selectY, destSelectX, destSelectY);

		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
