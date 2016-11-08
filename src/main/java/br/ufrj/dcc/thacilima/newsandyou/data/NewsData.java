package br.ufrj.dcc.thacilima.newsandyou.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufrj.dcc.thacilima.newsandyou.model.News;
import br.ufrj.dcc.thacilima.newsandyou.util.DBUtil;

public class NewsData {
	private DBUtil db;

	// error, created, existing, updated, deleted, success
	// it's about the last transaction done
	private String status;

	public NewsData() throws ClassNotFoundException, SQLException {
		super();

		this.db = new DBUtil();
	}

	public void insert(int idSourceNews, String uri, String title, String subtitle, String imageUrl)
			throws SQLException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String gottenDateString = dateFormat.format(Calendar.getInstance().getTime());

		String sqlInsertNews = "INSERT IGNORE INTO noticia (id_origem_noticia, uri, titulo, subtitulo, imagem_url, data_coletagem) "
				+ "VALUES ('" + idSourceNews + "', '" + uri + "', '" + title + "', '" + subtitle + "', '" + imageUrl
				+ "', '" + gottenDateString + "')";
		this.db.runInsertUpdateDeleteSql(sqlInsertNews);
	}

	public void insertCharacteristicForNews(String idNews, String musicBrainzArtistId, Double score)
			throws SQLException {

		String sqlInsertNewsCharac = "INSERT IGNORE INTO " +
										"caracteristica_noticia " + 
									"VALUES ( " +
									    "'" + idNews + "', " + 
									    "( " +
											"select " +
												"a.id_atributo " +
											"from " +
												"atributo a " +
											"where " +
												"a.lb_uri = '" + musicBrainzArtistId + "' " +
										"), " + 
									    "" + score + " " +
									")";
		
		
		this.db.runInsertUpdateDeleteSql(sqlInsertNewsCharac);
	}

	public List<News> getAllWithNoTxtCollected() throws SQLException {
		String sql = "select * from noticia where flag_txt = 0";
		ResultSet rs = db.runSql(sql);
		List<News> all = newsListFromResultSet(rs);

		return all;
	}

	public void updateTxtCollectedForIdNews(int idNews) throws SQLException {
		String sql = "UPDATE noticia SET flag_txt = 1 WHERE id_noticia = " + idNews;
		int totalChanges = this.db.runInsertUpdateDeleteSql(sql);
		if (totalChanges <= 0) {
			throw new SQLException();
		}
	}

	private List<News> newsListFromResultSet(ResultSet rs) throws SQLException {
		List<News> all = new ArrayList<News>();
		while (rs.next()) {
			News news = newsFromResultSet(rs);
			all.add(news);
		}
		return all;
	}

	public News newsFromResultSet(ResultSet rs) throws SQLException {
		News news = new News();
		news.setIdNews(rs.getInt("id_noticia"));
		news.setIdSourceNews(rs.getInt("id_origem_noticia"));
		news.setUri(rs.getString("uri"));
		news.setTitle(rs.getString("titulo"));
		news.setSubtitle(rs.getString("subtitulo"));
		news.setImageUrl(rs.getString("imagem_url"));
		news.setGottenDate(rs.getDate("data_coletagem"));
		news.setTxtColected(rs.getBoolean("flag_txt"));

		return news;
	}
}
