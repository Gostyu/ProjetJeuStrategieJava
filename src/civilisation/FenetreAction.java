package civilisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import affichage.AffichageMap;
import elements.MaisonMere;
import elements.TypeCase;

public class FenetreAction extends JPanel implements ActionListener{
	
	private List<Personnages> perso;
	private final Object lock=new Object();
	Dimension d=new Dimension(90,90);
	protected JButton [] j=new JButton[7];
	private Civilisation game;
	public FenetreAction(List<Personnages>perso,Civilisation game) {
		this.game = game;
		this.perso=perso;
		for(int i=0;i<j.length;i++) {
			j[i]=new JButton();
			j[i].addActionListener(this);
			j[i].setBackground(Color.WHITE);
			j[i].setSize(d);
			j[i].setPreferredSize(d);
			this.add(j[i]);
		}
		int taille=100;
		setPreferredSize(new Dimension(100,100));
		setBackground(Color.WHITE);
		for(int i=0;i<7;i++) {
			j[i].setLocation(0,taille);
			taille=taille+100;
		}
	   
	   j[0].setText("Attaque");
	   j[1].setText("Sentinelle");
	   j[2].setText("Recolte");
	   j[3].setText("Annuler");
	   j[4].setText("Creer");
	}


	

	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==j[0]) {
			for(int i=0;i<perso.size();i++) {
				if(perso.get(i).getSelected()==true) {
					AffichageMap.compteur=0;
					
			perso.get(i).setAttaque(true);
			JOptionPane.showMessageDialog(null,"Choisissez une case pour l'attaquant ou clic droit pour annuler","deplacement",JOptionPane.INFORMATION_MESSAGE);
					//perso.get(i).setEtat(perso.get(i).getEtatAttaquant());
			
				}
			}
	    //f.listePersos.get(1).setSelected(false);
		}
		if(e.getSource()==j[1]) {
			for(int i=0;i<perso.size();i++) {
				if(perso.get(i).getSelected()==true) {
					AffichageMap.compteur=0;
					
			perso.get(i).setProtege(true);
			JOptionPane.showMessageDialog(null,"Choisissez deux cases pour la sentinelle ou clic droit pour annuler","deplacement",JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
	    //f.listePersos.get(1).setSelected(false);
		}
		if(e.getSource() == j[2]) {
			for(int i=0;i<perso.size();i++) {
				if(perso.get(i).getSelected()==true) {
					AffichageMap.compteur=0;
					perso.get(i).setRecolte(true);
					JOptionPane.showMessageDialog(null,"Choisissez une pile d'or cases pour le recolteur ou clic droit pour annuler","deplacement",JOptionPane.INFORMATION_MESSAGE);

				}
			}
		}
		if(e.getSource()==j[4]) {
			JOptionPane.showMessageDialog(null,"Choisissez une case vierge autour de la maison mère","Créer une unité",JOptionPane.INFORMATION_MESSAGE);
			
			TypeCase [] casesDispo = new TypeCase[12];
			Random rand = new Random();
			int index=0;
			if(game.getName().equals("frame0") ){
//				AffichageMap.getListePersos().add(new Personnages(13,new Point(28,168),1));
				for(int i=0; i<casesDispo.length; i++) {
					if(AffichageMap.getElement(i).getTypeCase().equals("sol")) {
						casesDispo[i]=AffichageMap.getElement(i);
//						System.out.println(casesDispo[i]);
//						System.out.println("Position (x,y)="+casesDispo[i].getPosition());
//						System.out.println(i + " "+ AffichageMap.getElement(i));
//						System.out.println("Position (x,y) = "+ AffichageMap.getElement(i).getX()+","+AffichageMap.getElement(i).getY());
						if(AffichageMap.getElement(i+1).getTypeCase().equals("maisonM1")) {
							break;
						}
					} 
				}
				index = rand.nextInt(casesDispo.length);
				System.out.println("-----------------");
				System.out.println("Case choisie alétoirement : ");
//				if(casesDispo[index]!=null) {
//					System.out.println(casesDispo[index]);
//					System.out.println("Position (x,y)="+casesDispo[index].getPosition());
//					System.out.println(casesDispo[index].getX()+);
//					System.out.println("-----------------");
//					System.out.println((casesDispo[index].getX()+48)+","+(casesDispo[index].getY()+100));
//				}
//				System.out.println((casesDispo[index].getX()+pixels)+","+(casesDispo[index].getY()*pixels));
				Point nouvellePosition = new Point(casesDispo[index].getX()+28,casesDispo[index].getY()+100);
				AffichageMap.getListePersos().add(new Personnages(13,nouvellePosition,1));
			}else if(game.getName().equals("frame1")) {
				AffichageMap.getListePersos().add(new Personnages(13,new Point(28,168),2));
				for(int i=0; i<casesDispo.length; i++) {
					if(AffichageMap.getElement(i).getTypeCase().equals("sol")) {
						casesDispo[i]=AffichageMap.getElement(i);
						System.out.println(casesDispo[i]);
						System.out.println("Position (x,y)="+casesDispo[i].getPosition());
//						System.out.println(i + " "+ AffichageMap.getElement(i));
//						System.out.println("Position (x,y) = "+ AffichageMap.getElement(i).getX()+","+AffichageMap.getElement(i).getY());
						if(AffichageMap.getElement(i+1).getTypeCase().equals("maisonM2")) {
							break;
						}
					} 
			}
			//verifier les donnees 
//			System.out.println("Creation d'une unité");
		}	
		}
	}
}