package br.ufrj.dcc.thacilima.newsandyou.model;

import java.util.Date;

public class News {
	private int idNews;
	private int idSourceNews;
	private String uri;
	private String title;
	private String subtitle;
	private String imageUrl;
	private Date gottenDate;
	private boolean txtColected;
	
	public News() {
		
	}
	
	public News(String uri) {
		this.uri = uri;
	}

	public int getIdNews() {
		return idNews;
	}

	public void setIdNews(int idNews) {
		this.idNews = idNews;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Date getGottenDate() {
		return gottenDate;
	}

	public void setGottenDate(Date gottenDate) {
		this.gottenDate = gottenDate;
	}

	public boolean isTxtColected() {
		return txtColected;
	}

	public void setTxtColected(boolean txtColected) {
		this.txtColected = txtColected;
	}

	public int getIdSourceNews() {
		return idSourceNews;
	}

	public void setIdSourceNews(int idSourceNews) {
		this.idSourceNews = idSourceNews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("News [idNews=").append(idNews).append(", idSourceNews=").append(idSourceNews).append(", uri=")
				.append(uri).append(", title=").append(title).append(", subtitle=").append(subtitle)
				.append(", imageUrl=").append(imageUrl).append(", gottenDate=").append(gottenDate)
				.append(", txtColected=").append(txtColected).append("]");
		return builder.toString();
	}
}
