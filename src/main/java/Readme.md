In this project, we will read the RSS feed from the web. When we launch the program, it will generate the GUI box which
shows the news headlines from the wall street journal. When you hover on the news, it will show pop up box with
some description of that news. If you do one click on the headline, it will open that particular news in the web browser.

CLASS:NewsApp extends Application
     This is the main method that will launch the programme. Start method take the url fr the rss webpage. It also set
the stage, create the labels, and listview. OpenLink method takes the URL and lauches the web browser when you click on
the headlines. showpop method will get the title, headline and description of the rss news and show on the screen.
CLASS: News
    This class create an object. It has getter, setter and toString method. 
CLASS: NewsFeed
    This is iterable class of news. 
CLASS: NewsReader
    readfeed method convert string to document. readUrl takes the url as a string and read it by using buffer reader. 
StringToDoc method takes the string and converts it into the document.
