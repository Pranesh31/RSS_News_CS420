import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsFeed implements Iterable<News>{
    final String heading;
    final String desc;
    final String url;
    public int length;

    final ArrayList<News> news = new ArrayList<>();

    public NewsFeed(String heading, String desc, String url) {
        this.heading = heading;
        this.url = url;
        this.desc = desc;
    }

    @Override
    public Iterator<News> iterator() {
        return news.iterator();
    }

    public void setLength( int num) {
        this.length = num;
    }
    public List<News> getFeed() {
        return news;
    }

    @Override
    public String toString() {
        return "Heading =" + heading + ", Description =" + desc
                + ", Link=" + url ;
    }

}
