package br.ufrj.dcc.thacilima.newsandyou.newsMapper;

import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.ufrj.dcc.thacilima.newsandyou.data.NewsData;

public class NewsCrawler {
	private static int countTrialsToConnectURL = 5;
	
	private static int pages = 2;
	private static String nmeSearchNewsBaseURL = "http://www.nme.com/news/music/page";
	private static int nmeIdSourceNews = 1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			NewsData newsData = new NewsData();
			searchNewsData(newsData);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void searchNewsData(NewsData newsData) {
		
		for (int i = 1; i <= pages; i++) {
			searchNewsDataInUrl(newsData, nmeSearchNewsBaseURL + "/" + i, nmeIdSourceNews);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void searchNewsDataInUrl(NewsData newsData, String url, int idSourceNews) {

		Document doc = null;
		while (countTrialsToConnectURL > 0) {
			
			//try to connect the url until countTrialsToConnectURL is zero
			countTrialsToConnectURL -= 1;
			
			//get the document in the url
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			}
			
			if (doc != null)
			{
				Element content = doc.getElementById("wrapper").getElementById("content");
				Elements articleList = content.select("article.post");
				
				for (int i = 0; i < articleList.size(); i++) {
					Element article =  articleList.get(i);
					
					if (i == 0) {
						
					}
					else {
						Element img = article.getElementsByClass("entry-media").get(0).getElementsByTag("img").get(0);
						String imageUrl =  img.attr("src");
						
						Element header = article.getElementsByClass("entry-header").get(0);
						
						Element aTitle = header.getElementsByTag("h2").get(0).getElementsByTag("a").get(0);
						String uri = aTitle.attr("href");
						String title = aTitle.text();
						title = title.replace("'", "\\'");
						
						String subtitle = "";
						
						System.out.println("imageURL: " + imageUrl + " uri: " + uri + " title: " + title + " subtitle: " + subtitle);
						
						try {
							newsData.insert(idSourceNews, uri, title, subtitle, imageUrl);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				break;
			}
		}
		
		NewsCrawler.countTrialsToConnectURL = 5;
	}

}
