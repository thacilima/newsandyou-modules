package br.ufrj.dcc.thacilima.newsandyou.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.dcc.thacilima.newsandyou.model.Attribute;
import br.ufrj.dcc.thacilima.newsandyou.util.DBUtil;

public class AttributeData {
	private DBUtil db;
	
	//error, created, existing, updated, deleted, success
	//it's about the last transaction done
	private String status;
	
	public AttributeData() throws ClassNotFoundException, SQLException {
		super();
		
		this.db = new DBUtil();
	}
	
	public List<Attribute> getAllDidNotTryCollectFbYet() throws SQLException {
		String sql = "select * from atributo where flag_fb = '0'";
		ResultSet rs = this.db.runSql(sql);
		List<Attribute> all = attributesFromResultSet(rs);
		
		return all;
	}
	
	public List<Attribute> getAllWithNoFacebookData() throws SQLException {
		String sql = "select * from atributo where fb_uri is null or fb_uri = ''";
		ResultSet rs = this.db.runSql(sql);
		List<Attribute> all = attributesFromResultSet(rs);
		
		return all;
	}
	
	public void insert(String linkedBrainzUri) throws SQLException {
		String sqlInsertAtt = "INSERT IGNORE INTO atributo (lb_uri) VALUES ('"+ linkedBrainzUri +"')";
		this.db.runInsertUpdateDeleteSql(sqlInsertAtt);
	}
	
	public void updateFbUriForLbUri(String facebookUri, String linkedBrainzUri) throws SQLException {
		String sqlSetFbUrl = "UPDATE atributo SET fb_uri = '" + facebookUri +"' WHERE lb_uri = '" + linkedBrainzUri + "'";
		int totalChanges = this.db.runInsertUpdateDeleteSql(sqlSetFbUrl);
		if (totalChanges <= 0)
		{
			throw new SQLException();
		}
	}
	
	public void updateTriedRecoverFbFlag(String linkedBrainzUri) throws SQLException {
		String sqlSetFbUrl = "UPDATE atributo SET flag_fb = '1' WHERE lb_uri = '" + linkedBrainzUri + "'";
		int totalChanges = this.db.runInsertUpdateDeleteSql(sqlSetFbUrl);
		if (totalChanges <= 0)
		{
			throw new SQLException();
		}
	}
	
	private List<Attribute> attributesFromResultSet(ResultSet rs) throws SQLException {
		List<Attribute> all = new ArrayList<Attribute>();
		while (rs.next()) {
			Attribute attribute = attributeFromResultSet(rs);
			all.add(attribute);
		}
		return all;
	}
	
	private Attribute attributeFromResultSet(ResultSet rs) throws SQLException {
		Attribute attribute = new Attribute();
		
		attribute.setIdAttribute(rs.getInt("id_atributo"));
		attribute.setLbUri(rs.getString("lb_uri"));
		attribute.setFbUri(rs.getString("fb_uri"));
		
		return attribute;
	}
}
