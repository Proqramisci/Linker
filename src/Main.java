import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {
	
	public static boolean containsWWW(String text){
		String www = "www";
		String quotationMark = "\"";
		if(text.contains(www) && text.contains(quotationMark)){
			return true;
		}
		else return false;
	}
	
	public static boolean isTextLongEnough(String text){
		//są linki, które zawierają tylko 3 znaki, ale wykluczam taką opcję
		final int THE_SHORTEST_LINK_CHARACTERS = 9;
		
		if(text.length() >= THE_SHORTEST_LINK_CHARACTERS){
			return true;
		}
		else return false;
	}
	
	public static String getLinksFromHTML(String text){
		String www = "www";
		String quotationMark = "\"";
		int wwwIndexFirst;
		int wwwIndexLast;

		wwwIndexFirst = text.indexOf(www);
		wwwIndexLast = text.indexOf(quotationMark, wwwIndexFirst);
		text = text.substring(wwwIndexFirst, wwwIndexLast);
		return text;
	}
	
	public static String readHTMLandReturnLinks(String URLName){
		try{
			URL url = new URL(URLName);
			 BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			 while ((line=in.readLine())!=null){
				 if(containsWWW(line)){
					 line = getLinksFromHTML(line);
					 if(isTextLongEnough(line)){
						 return line + "\n";
					 }
				 }
			}
			 in.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	public static void main(String[] args) {
//		String URLName = args[0];
//		String URLName = "http://www.quadgraphics.pl/tresc";
//		String URLName = "http://www.repostuj.pl";
// 		String URLName = "https://www.onet.pl/";
		new Frame();
	}
}