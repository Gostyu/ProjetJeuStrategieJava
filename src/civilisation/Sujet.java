package civilisation;

import observer.Observateur;

public interface Sujet {
public void ajouterObservateur(Observateur o) ;
public void supprimerObservateur(Observateur o);
public void notifyObservateur();
}
