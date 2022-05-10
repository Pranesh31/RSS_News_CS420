import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URI;

public class NewsApp extends Application {
    Stage window;
    private Label titleLabel ;
    private Label detailsLabel ;
    private FadeTransition fade ;
    private Popup popup ;
    private Node popupNode ;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        window.setTitle("The Wall Street Journal News Feed");
        NewsReader reader = new NewsReader();
        NewsFeed news = reader.readFeed("https://feeds.a.dj.com/rss/RSSWorldNews.xml");

        ListView<Properties> list = new ListView<>();

        titleLabel = new Label();
        titleLabel.setStyle("-fx-font-size: 1.5em ; -fx-font-weight: bold; -fx-font-style: italic");
        detailsLabel = new Label();
        popup = new Popup();
        popupNode = new VBox(2.5, titleLabel, detailsLabel);
        popupNode.setStyle("-fx-background-color: red, -fx-background; "+
                "-fx-background: #99ffff; -fx-padding:12px;");
        popup.getContent().add(popupNode);

        fade = new FadeTransition(Duration.seconds(10), popupNode);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> popup.hide());

        list.setCellFactory(l -> {
            ListCell<Properties> rows = new ListCell<>() {
                @Override
                public void updateItem(Properties item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getHeading());
                    }
                }
            };
            rows.hoverProperty().addListener((obs, wasHovered, isHovered) -> {
                if (isHovered && ! rows.isEmpty()) {
                    showPopup(rows);
                } else {
                    hidePopup();
                }
            });

            return rows ;
        });

        for (News rss : news.getFeed()) {
            list.getItems().add(new Properties(rss.getHeading(), rss.getDesc(), rss.getUrl()));
        }

        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.setOnMouseClicked(click -> {
            if(click.getClickCount()==1) {
                ObservableList<Properties> listProperties = list.getSelectionModel().getSelectedItems();
                for(Properties prop : listProperties)
                    openLink(prop.getLink());
            }
        });

        VBox box = new VBox(5);
        box.setPadding(new Insets(10, 10, 10,10));

        box.getChildren().addAll(list);

        Scene scene1 = new Scene(box, 1080, 500);
        window.setScene(scene1);
        window.show();
    }

public static class Properties {
    private final String heading ;
    private final String desc;
    private final String link;

    public Properties(String heading, String desc, String link) {
        this.heading = heading ;
        this.desc = desc;
        this.link = link;
    }

    public String getHeading() {
        return heading ;
    }
    public String getDesc() {
        return desc;
    }
    public String getLink() {
        return link ;
    }
}
    private void openLink(String str) {
        try {
            URI theURI = new URI(str);
            java.awt.Desktop.getDesktop().browse(theURI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPopup(ListCell<Properties> cell) {
        fade.stop();
        popupNode.setOpacity(3.0);
        Bounds bounds = cell.localToScreen(cell.getBoundsInLocal());
        popup.show(cell, bounds.getMaxX()-5, bounds.getMinY()-5);

        Properties item = cell.getItem() ;
        titleLabel.setText(item.getHeading());
        detailsLabel.setText(String.format("%s.%n", item.getDesc()));
    }

    private void hidePopup() {
        fade.playFromStart();
    }
}