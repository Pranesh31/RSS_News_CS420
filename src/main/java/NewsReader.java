import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class NewsReader {
    public NewsFeed readFeed (String url) {
        Document doc = stringToDoc(readURL(url));
        assert doc != null;
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("item");

        String heading = "";
        String desc = "";
        String link = "";

        NewsFeed news = new NewsFeed(heading, desc, link);

        for (int i = 0; i < list.getLength(); i++) {
            Node node= list.item(i);
            News feed = new News();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element ele = (Element) node;
                feed.setHeading(ele.getElementsByTagName("headline").item(0).getTextContent());
                feed.setDesc(ele.getElementsByTagName("description").item(0).getTextContent());
                feed.setUrl(ele.getElementsByTagName("link").item(0).getTextContent());
                news.getFeed().add(feed);
            }
        }
        news.setLength(list.getLength());
        return news;
    }

    private static String readURL(String url) {
        try {
            URL rssURL = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(rssURL.openStream()));
            StringBuilder str = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<description><p>")){
                    line = line.replace("<description><p>", "<description>");
                }
                if (line.contains("<title>")) {
                    line = line.replace("<title>", "<headline>");
                    line = line.replace("</title>", "</headline>");
                }
                if(line.contains("<headline>"))
                    str.append("\n").append(line).append("\n");
                else
                    str.append(line).append("\n");
            }
            reader.close();
            return str.toString();
        }catch (MalformedURLException e) {
            System.out.println("Malformed URL");
        }catch (IOException io) {
            System.out.println("Something wrong with the file");
        }
        return null;
    }

    private static Document stringToDoc(String xmlSource) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlSource)));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}