


public class Journal extends Document{
	private String articles;
	private String publisherDate;
	private String volume;
	private String issue;
	
	public Journal(){
		
	}
	
	public Journal(String newTitle, String newPublisherDate, String newVolume,
			String newIssue, String newPublisher, String newArticles){
		title = newTitle;
		publisherDate = newPublisherDate;
		volume = newVolume;
		issue = newIssue;
		publisher = newPublisher;
		articles = newArticles;
		
	}
	
	public String getArticles(){
		return articles;
	}
	
	public String getPublisherDate(){
		return publisherDate;
	}
	
	public String getVolume(){
		return volume;
	}
	
	public String getIssue(){
		return issue;
	}
}
