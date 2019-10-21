package civilisation;

public enum Cases {
	Arbre("arbre"),
	Boue("boue"),
	MaisonMere1("maisonM1"),
	MaisonMere2 ("maisonM2"),
	Obstacle("obstacle"),
	PileOr("pileOr"),
	SolVierge("solVierge"),
	Teleporteur("teleporteur");
	private String type;
	private Cases(String type) {
		this.type = type;
	}
	public String getTypeElement() {
		return type;
	}
}

//public enum Cases {
//	Personnage,
//	ZoneSombre,
//	ZoneBlanche,
//	Arbre,
//	ZoneTransparente,
//	Obstacle,
//	CercleOval,
//	PileOr
//}
