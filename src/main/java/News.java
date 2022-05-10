public class News {
    String heading;
    String desc;
    String url;

    public void setHeading(String heading) {
        this.heading = heading;
    }
    public String getHeading() {
        return this.heading;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return " heading=" + heading + ", desc=" + desc
                + ", url=" + url ;
    }

}