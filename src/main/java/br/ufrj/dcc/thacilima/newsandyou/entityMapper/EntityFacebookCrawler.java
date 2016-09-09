package br.ufrj.dcc.thacilima.newsandyou.entityMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.ufrj.dcc.thacilima.newsandyou.data.AttributeData;
import br.ufrj.dcc.thacilima.newsandyou.model.Attribute;

public class EntityFacebookCrawler {
	
	private static int countTrialsToConnectURL = 5;
	
	public static void main(String[] args) {
		
		try {
			AttributeData attributeData = new AttributeData();
			findFacebookDataForEntities(attributeData);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void findFacebookDataForEntities(AttributeData attributeData) {
		List<Attribute> attributes =  new ArrayList<Attribute>();
		try {
			attributes = attributeData.getAllWithNoFacebookData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Attribute attribute : attributes) {
			
			searchFBDataInUrl(attributeData, attribute.getLbUri());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void searchFBDataInUrl(AttributeData attributeData, String url) {

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
				//get the ul element that contains external links on linked brainz website
				Elements ulExternalLinksList = doc.getElementsByClass("external_links");
				Element ulExternalLinks = ulExternalLinksList.get(0);
				
				//get the li element that contains fb info
				Elements liFacebookInfoList = ulExternalLinks.getElementsByClass("facebook-favicon");
				
				if (liFacebookInfoList.size() > 0) {
					Element liFacebookInfo = liFacebookInfoList.get(0);
					
					//get the a element that contains the link to facebook page 
					Elements aFacebookLinkList = liFacebookInfo.getElementsByTag("a");
					Element aFacebookLink = aFacebookLinkList.get(0);
					
					String facebookLink = aFacebookLink.attr("href");
					//Formatting facebook link
					facebookLink = facebookLink.replace("https", "").replace("http", "")
							.replace(":", "").replace("/", "").replace("www", "").replace(".", "");
					
					
					System.out.println(url + " - " + facebookLink);
					
					try {
						attributeData.updateFbUriForLbUri(facebookLink, url);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				else {
					System.out.println(url + " - " + "facebook link didn't found");
				}
				
				break;
			}
		}
		
		EntityFacebookCrawler.countTrialsToConnectURL = 5;
	}
}
