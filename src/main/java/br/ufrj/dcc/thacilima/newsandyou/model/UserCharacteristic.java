package br.ufrj.dcc.thacilima.newsandyou.model;

import java.util.Date;

/**
 * @author thacilima
 *
 */
public class UserCharacteristic {
	
	private int idUser;
	private int idAttribute;
	private Date gottenDate;
	private double possibility;
	
	public UserCharacteristic() {
		super();
	}

	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdAttribute() {
		return idAttribute;
	}
	public void setIdAttribute(int idAttribute) {
		this.idAttribute = idAttribute;
	}
	public Date getGottenDate() {
		return gottenDate;
	}
	public void setGottenDate(Date gottenDate) {
		this.gottenDate = gottenDate;
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
		builder.append("UserCharacteristic [idUser=").append(idUser).append(", idAttribute=").append(idAttribute)
				.append(", gottenDate=").append(gottenDate).append(", possibility=").append(possibility).append("]");
		return builder.toString();
	}
}
