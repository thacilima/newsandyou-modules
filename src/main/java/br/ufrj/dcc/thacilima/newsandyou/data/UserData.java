package br.ufrj.dcc.thacilima.newsandyou.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufrj.dcc.thacilima.newsandyou.model.User;
import br.ufrj.dcc.thacilima.newsandyou.util.DBUtil;

public class UserData {
	
	private DBUtil db;
	
	//error, created, existing, updated, deleted, success
	//it's about the last transaction done
	private String status;
	
	public UserData() throws ClassNotFoundException, SQLException {
		super();
		
		this.db = new DBUtil();
	}
	
	public User userFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		
		user.setIdUser(rs.getInt("id_usuario"));
		user.setEmail(rs.getString("email"));
		user.setName(rs.getString("nome"));
		user.setFbUri(rs.getString("fb_uri"));
		user.setLastUpdateDate(rs.getDate("data_ultima_atualizacao"));
		
		return user;
	}
}
