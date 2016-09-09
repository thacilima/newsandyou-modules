package br.ufrj.dcc.thacilima.newsandyou.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufrj.dcc.thacilima.newsandyou.model.NewsCharacteristic;
import br.ufrj.dcc.thacilima.newsandyou.util.DBUtil;

public class NewsCharacteristicData {
	private DBUtil db;
	
	//error, created, existing, updated, deleted, success
	//it's about the last transaction done
	private String status;
	
	public NewsCharacteristicData() throws ClassNotFoundException, SQLException {
		super();
		
		this.db = new DBUtil();
	}
	
	public NewsCharacteristic userCharacteristicFromResultSet(ResultSet rs) throws SQLException {
		NewsCharacteristic newsCharacteristic = new NewsCharacteristic();
		
		newsCharacteristic.setIdNews(rs.getInt("id_noticia"));
		newsCharacteristic.setIdAttribute(rs.getInt("id_atributo"));
		newsCharacteristic.setPossibility(rs.getDouble("possibilidade"));
		
		return newsCharacteristic;
	}
}
