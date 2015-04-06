/**
 * This is an unofficial Quora API written in Java.
 * @author Tian Zheng NaitGnehz@gmail.com
 * @author Gang Wu ustcwg@gmail.com
 * Carnegie Mellon University
 * March 18th, 2015
 */

package edu.cmu.cs.JQuora;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Question {
	String url;
	HashMap<String, String> question;
	
	public Question(String name) throws IOException {
		this.url = new String("https://www.quora.com/" + name);
		this.question = new HashMap<String, String>();
		
		Document doc = Jsoup.connect(url).get();
		// Parse title
		String title = doc.title();
		title = title.substring(0, title.length() - 8);
		question.put("title", title);
		
		// Parse want answers 
		Element QuestionFooter = doc.select("div.action_bar_inner").first();
		String wantAnswer = QuestionFooter.select("span.count").first().text();
//		// Seperate digits and letters
//		wantAnswer = wantAnswer.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[1];
		question.put("wantAnswer", wantAnswer);
	}
	
	// Get Answer from Specific Author
	public String getAnswerByAuthor(String author) throws IOException {
		url += "/answer/" + author;
		Document doc = Jsoup.connect(url).get();		
		return parseAnswer(doc);
	}
	
	private String parseAnswer(Document doc) {
		if (doc == null) return "Error: Invalid URL.";
		// The hashmap that stores <attribute, content>
		HashMap<String, String> answer = new HashMap<String, String>();
		
		Element answerText = doc.select("div.answer_text").first();
		Element answerContent = answerText.select("div.answer_content").first();
		Element contentFooter = answerContent.select("div.AnswerFooter").first();
		Element answerFooter = doc.select("div.action_bar_inner").last();
		
		
		// Content
		String contentText = answerContent.text();
		String answerUrl = "https://www.quora.com" + contentFooter.select("a.answer_permalink").first().attr("href");
		//Update View

		
		
		//return doc.toString();
		//return answerText.toString();
		//return answerContent.toString();
		return answerFooter.toString();
		//return answerUrl;
		
	}

	
	public HashMap<String, String> getQuestion() {
		return question;
	}

	public void setQuestion(HashMap<String, String> question) {
		this.question = question;
	}

	public static void main(String[] args) throws IOException {
		Question quest = new Question("Are-all-the-U-S-Presidents-related-to-each-other");
		System.out.println(question.getAnswerByAuthor("Brian-Roemmele"));
	}
}
