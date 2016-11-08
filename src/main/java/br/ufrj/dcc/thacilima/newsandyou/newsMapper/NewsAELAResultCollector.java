package br.ufrj.dcc.thacilima.newsandyou.newsMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.ufrj.dcc.thacilima.newsandyou.data.NewsData;

public class NewsAELAResultCollector {

	public NewsData newsData;

	public NewsAELAResultCollector(NewsData newsData) {
		this.newsData = newsData;
	}

	public void moveFile(String fileName, String dirFrom, String dirTo) {
		File fileTxt = new File(dirFrom+fileName);
		fileTxt.renameTo(new File(dirTo + fileName));
	}
	
	public void moveFiles(String fileNameWithoutExtention) {
		this.moveFile(fileNameWithoutExtention+".txt", "news/txt/", "evaluatedNews/txt/");
		this.moveFile(fileNameWithoutExtention+".json", "news/ner/", "evaluatedNews/ner/");
		this.moveFile(fileNameWithoutExtention+".json", "news/ned/", "evaluatedNews/ned/");
	}
	
	public void readNewsFiles() {
		JSONParser parser = new JSONParser();

		File folder = new File("news/ned/");
		for (final File file : folder.listFiles()) {
			String fileName = file.getName();
			String fileNameWithoutExtention = fileName.replace(".json", "");
			if (fileName.contains("json")) {

				Object obj = null;
				try {
					obj = parser.parse(new FileReader(file.getAbsolutePath()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (obj != null) {
					String idNews = fileNameWithoutExtention.replace("new", "");
					System.out.println("================");
					System.out.println(idNews);
					System.out.println("================");

					JSONObject jsonObject = (JSONObject) obj;

					JSONArray annotationJsonList = (JSONArray) jsonObject.get("annotations");

					for (Object annotation : annotationJsonList) {

						JSONObject annotationJson = (JSONObject) annotation;

						JSONArray candidatesJsonList = (JSONArray) annotationJson.get("candidates");

						for (Object candidate : candidatesJsonList) {

							JSONObject candidateJson = (JSONObject) candidate;
							String linkedBrainzURI = (String) candidateJson.get("uri");

							if (linkedBrainzURI.contains("artist") && linkedBrainzURI.contains("musicbrainz")) {

								System.out.println(linkedBrainzURI);

								String scoreString = (String) candidateJson.get("score");

								Double score = Double.valueOf(scoreString.replace(",", "."));

								String[] substrings = linkedBrainzURI.split("/");
								String musicBrainzArtistId = substrings[substrings.length - 1];

								try {
									this.newsData.insertCharacteristicForNews(idNews, musicBrainzArtistId, score);
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					}
					
					this.moveFiles(fileNameWithoutExtention);
				}
			}
		}
	}

	public static void main(String[] args) {

		try {
			NewsData newsData = new NewsData();
			NewsAELAResultCollector newsAELAResultCollector = new NewsAELAResultCollector(newsData);
			newsAELAResultCollector.readNewsFiles();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
