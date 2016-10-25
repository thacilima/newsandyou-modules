package br.ufrj.dcc.thacilima.newsandyou.newsMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import br.ufrj.dcc.thacilima.newsandyou.data.NewsData;
import br.ufrj.dcc.thacilima.newsandyou.model.News;

public class NewsContentCrawler {
	
	private static int countTrialsToConnectURL = 5;
	
	private static String nmeNewsBaseURL = "http://www.nme.com";
	
	public static void main(String[] args) {
		
		try {
			NewsData newsData = new NewsData();
			findNewsContent(newsData);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void findNewsContent(NewsData newsData) {
		List<News> newsList =  new ArrayList<News>();
		try {
			newsList = newsData.getAllWithNoTxtCollected();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (News news : newsList) {
			
			searchNewsContentInUrl(newsData, nmeNewsBaseURL + news.getUri(), news.getIdNews());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void searchNewsContentInUrl(NewsData newsData, String url, int idNews) {
		Document doc = null;
		while (NewsContentCrawler.countTrialsToConnectURL > 0) {
			
			//try to connect the url until countTrialsToConnectURL is zero
			NewsContentCrawler.countTrialsToConnectURL -= 1;
			
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
				Element article = content.getElementsByClass("primary").get(0).select("article.post").get(0);
				Element articleHeader = article.getElementsByClass("page-header").get(0).getElementsByTag("h1").get(0);
				Element articleText = content.getElementsByClass("entry-content").get(0).
						getElementsByAttributeValue("itemprop", "articleBody").get(0);
				
				String headerString = articleHeader.text();
				
				// Get only text in p
				String textString = "";
				for (Element p : articleText.getElementsByTag("p")) {
					if (!p.text().isEmpty()) { 
						textString += p.text() + " ";
					}
				}
				if (textString.isEmpty()) {
					textString = articleText.text();
				}
				
				//Save in new file
				File file = new File("news/txt/new"+ idNews +".txt");
				
				try {
					if (!file.exists()) {
						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(headerString);
					bw.newLine();
					bw.write(textString);
					bw.close();
					
					System.out.println(url + " - " + file.getName());
					
					newsData.updateTxtCollectedForIdNews(idNews);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				break;
			}
		}
		
		NewsContentCrawler.countTrialsToConnectURL = 5;
	}
}
