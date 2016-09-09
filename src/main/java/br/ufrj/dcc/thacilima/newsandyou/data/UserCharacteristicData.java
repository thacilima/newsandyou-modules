package br.ufrj.dcc.thacilima.newsandyou.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufrj.dcc.thacilima.newsandyou.model.UserCharacteristic;
import br.ufrj.dcc.thacilima.newsandyou.util.DBUtil;

public class UserCharacteristicData {
	
	private DBUtil db;
	
	//error, created, existing, updated, deleted, success
	//it's about the last transaction done
	private String status;
	
	public UserCharacteristicData() throws ClassNotFoundException, SQLException {
		super();
		
		this.db = new DBUtil();
	}
	
	public UserCharacteristic userCharacteristicFromResultSet(ResultSet rs) throws SQLException {
		UserCharacteristic userCharacteristic = new UserCharacteristic();
		
		userCharacteristic.setIdUser(rs.getInt("id_usuario"));
		userCharacteristic.setIdAttribute(rs.getInt("id_atributo"));
		userCharacteristic.setGottenDate(rs.getDate("data_coletagem"));
		userCharacteristic.setPossibility(rs.getDouble("possibilidade"));
		
		return userCharacteristic;
	}
}
