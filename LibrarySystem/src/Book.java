
public class Book extends Document{
	private String date;
	private String ISBN;
	private String copies;
	private String author;
	
	Book(){
		
	}
	Book(String newTitle, String newPublisher, String newDate, String newISBN, String newCopies, String newAuthor){
		title = newTitle;
		publisher = newPublisher;
		date = newDate;
		ISBN = newISBN;
		copies = newCopies;
		author = newAuthor;
	}
	
	public String getPublisher(){
		return publisher;
	}
	
	public String getDate(){
		return date;
	}
	
	public String getISBN(){
		return ISBN;
	}
	
	public String getCopies(){
		return copies;
	}
	
	public String getAuthor(){
		return author;
	}
}
