package br.ufrj.dcc.thacilima.newsandyou.model;

public class NewsCharacteristic {
	
	private int idNews;
	private int idAttribute;
	private double possibility;
	
	public NewsCharacteristic() {
		super();
	}

	public int getIdNews() {
		return idNews;
	}

	public void setIdNews(int idNews) {
		this.idNews = idNews;
	}
	
	public int getIdAttribute() {
		return idAttribute;
	}

	public void setIdAttribute(int idAttribute) {
		this.idAttribute = idAttribute;
	}

	public double getPossibility() {
		return possibility;
	}

	public void setPossibility(double possibility) {
		this.possibility = possibility;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NewsCharacteristic [idNews=").append(idNews).append(", idAttribute=").append(idAttribute)
				.append(", possibility=").append(possibility).append("]");
		return builder.toString();
	}
}
