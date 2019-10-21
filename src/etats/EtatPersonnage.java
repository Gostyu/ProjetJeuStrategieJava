package etats;

public interface EtatPersonnage {
//	 	String nom = "";
		public void move();
	    public void attaque();
		public void surveille();
		public void recolte();
		public void estAttaque(int degats);//changer le blaze
}
