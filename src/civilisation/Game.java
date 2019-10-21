package civilisation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import affichage.AffichageMap;

public class Game {

private List<Personnages> persos;
private AffichageMap map;
public Game(List<Personnages> persos) {
	persos=new ArrayList<Personnages>();
//	persos.add(new Personnages(13, "link", new Point(10, 10), false));
//	persos.add(new Personnages(13, "mario", new Point(80, 80), false));
//	persos.add(new Personnages(13, "luigi", new Point(10, 80), false));
//	persos.add(new Personnages(13, "tod", new Point(80, 10), false));
	
	//map=new AffichageMap();

}

public List<Personnages> getPersonnages() {
	return persos;
}
	
}
