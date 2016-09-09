package br.ufrj.dcc.thacilima.newsandyou.model;

import java.sql.Date;

public class User {
	
	private int idUser;
	private String email;
	private String name;
	private String fbUri;
	private Date lastUpdateDate;
	
	public User() {
		
	}

	public User(String email, String name, String fbUri) {
		this.email = email;
		this.name = name;
		this.fbUri = fbUri;
	}
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFbUri() {
		return fbUri;
	}
	public void setFbUri(String fbUri) {
		this.fbUri = fbUri;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [idUser=").append(idUser).append(", email=").append(email).append(", name=").append(name)
				.append(", fbUri=").append(fbUri).append(", lastUpdateDate=").append(lastUpdateDate).append("]");
		return builder.toString();
	}
}
