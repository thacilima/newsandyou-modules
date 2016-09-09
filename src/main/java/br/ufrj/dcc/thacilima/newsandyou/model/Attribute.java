package br.ufrj.dcc.thacilima.newsandyou.model;

public class Attribute {
	
	private int idAttribute;
	private String lbUri;
	private String fbUri;
	
	public Attribute() {
		super();
	}
	
	public int getIdAttribute() {
		return idAttribute;
	}
	public void setIdAttribute(int idAtribute) {
		this.idAttribute = idAtribute;
	}
	public String getLbUri() {
		return lbUri;
	}
	public void setLbUri(String lbUri) {
		this.lbUri = lbUri;
	}
	public String getFbUri() {
		return fbUri;
	}
	public void setFbUri(String fbUri) {
		this.fbUri = fbUri;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Attribute [idAttribute=").append(idAttribute).append(", lbUri=").append(lbUri)
				.append(", fbUri=").append(fbUri).append("]");
		return builder.toString();
	}
}
