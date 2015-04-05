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

public class Question {
	String url;
	
	public Question(String question) {
		this.url = new String("https://www.quora.com/" + question);
		
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
		Element answerFirstFooter = doc.select("div.action_bar_inner").first();
		Element answerSecondFooter = doc.select("div.action_bar_inner").last();

		// Content
		String contentText = answerContent.text();
		String answerUrl = "https://www.quora.com" + contentFooter.select("a.answer_permalink").first().attr("href");
		//Update View
		
		// Footer
		String wantAnswer = answerFirstFooter.select("div.primary_item").text();
		
		
		//return doc.toString();
		//return answerText.toString();
		//return answerContent.toString();
		//return answerFirstFooter.toString();
		return wantAnswer;
		//return answerUrl;
		
	}

	public static void main(String[] args) throws IOException {
		Question question = new Question("Are-all-the-U-S-Presidents-related-to-each-other");
		System.out.println(question.getAnswerByAuthor("Brian-Roemmele"));
	}
}
