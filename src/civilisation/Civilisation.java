package civilisation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import affichage.AffichageMap;

public class Civilisation extends JFrame {
//	private Fenetre board; //carte du jeu
	private FenetreAction side; // panneau de contrôle
	private AffichageMap map;// carte du jeu
	private List<Personnages> persos;

	public CardLayout card = new CardLayout(0, 0);

	public Civilisation() {
		persos = new ArrayList<Personnages>();
		persos.add(new Personnages(13,new Point(153, 83),1));
		persos.add(new Personnages(13, new Point(107, 154), 1));
		persos.add(new Personnages(13, new Point(165,38),1));
//		persos.add(new Personnages(13, new Point(500, 380),2));
////		persos.add(new Personnages(13, "wario", new Point(500, 380),2, false));
////		persos.add(new Personnages(13, "warluigi", new Point(500, 380),2, false));

		this.setSize(1000,1000);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
//		this.map = new AffichageMap(this, persos);
		this.map = new AffichageMap(this);

		// this.board = new Fenetre(this);
		this.side = new FenetreAction(persos,this);
		
		add(/* board */map, BorderLayout.CENTER);

		add(side, BorderLayout.SOUTH);
		JTextArea textArea = new JTextArea(5, 30);

		JScrollPane scrollPane = new JScrollPane(/* board */map);
		scrollPane.disable();
		setPreferredSize(new Dimension(450, 110));

		add(scrollPane, BorderLayout.CENTER);

		this.setVisible(true);

	}

	public List<Personnages> getPersos() {
		return this.persos;
	}
	public static String getNameFrame() {
		return null;
	}

}