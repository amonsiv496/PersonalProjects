import java.util.*;
import java.io.*;

public class LibDirectory {
	
	public static String findUser(String name, String password){
		File data = new File("logins.txt");
		Scanner input = null;
		try {
			input = new Scanner(data);
			input.useDelimiter("(;|,)");
		} catch (FileNotFoundException e) {
			// catch block
			System.out.println("File not found!");
			e.printStackTrace();
		}
		
		// read database to find the user with password
		String nameFound;
		String passwordFound;
		while(input.hasNext()){
			nameFound = input.next();
			passwordFound = input.next();
			if (nameFound.equalsIgnoreCase(name) && passwordFound.equals(password)){
				return nameFound;
			}
		}
		return null;
	}
	
	public static String findUserType(String name){
		File userFile = new File("UserFile.txt");
		try {
			Scanner input = new Scanner(userFile);
			
			String line;
			String[] userType;
			input.nextLine(); input.nextLine();
			while (input.hasNextLine()){
				line = input.nextLine();
				if (line.contains(name)){
					return line;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List <String> findDocumentToBorrow(String doc, String choice){
		if (choice == "book"){
			File bookDatabase = new File("bookfile.txt");
			Scanner input = null;
			try {
				input = new Scanner(bookDatabase);
			} catch (FileNotFoundException e) {
				System.out.println("Book database not found!");
				e.printStackTrace();
			}
			
			input.nextLine();
			input.nextLine();
			
			List <String> bookList = new ArrayList<>();
			List <String> foundBooksList = new ArrayList<>();
			for (int i = 0; input.hasNextLine(); i++){
				bookList.add(input.nextLine());
				if (bookList.get(i).toString().contains(doc)){
					foundBooksList.add(bookList.get(i));
				}
			}
			input.close();
			return foundBooksList;
		}
		else {
			File journalDatabase = new File("journalfile.txt");
			Scanner input = null;
			try {
				input = new Scanner(journalDatabase);
			} catch (FileNotFoundException e) {
				System.out.println("Book database not found!");
				e.printStackTrace();
			}
			input.nextLine();
			
			List <String> journalList = new ArrayList<>();
			List <String> foundJournalList = new ArrayList<>();
			for (int i = 0; input.hasNextLine(); i++){
				journalList.add(input.nextLine());
				if (journalList.get(i).toString().contains(doc)){
					foundJournalList.add(journalList.get(i));
				}
			}
			input.close();
			return foundJournalList;
		}
	}
	
	public static List <String> findBookToReturn(String userId, String book){
		File transactionFile = new File("transaction.txt");
		Scanner input = null;
		try {
			input = new Scanner(transactionFile);
		} catch (FileNotFoundException e) {
			System.out.println("Book database not found!");
			e.printStackTrace();
		}
		
		String line;
		List <String> booksFound = new ArrayList<>();
		line = input.nextLine();
		for (int i = 0; input.hasNextLine(); i++){
			line = input.nextLine();
			if (line.split("/")[0].equals(userId) && line.split("/")[1].contains(book)){
				booksFound.add(line.split("/")[1]);
			}
		}
		input.close();
		return booksFound;
	}

	public static void updateBookDatabase(String ISBN, String addSub) {
		File bookDatabase = new File("bookfile.txt");
		Scanner input = null;
		try {
			input = new Scanner(bookDatabase);
			input.useDelimiter("\t");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List <String> bookFileList = new ArrayList<>();
		input.nextLine(); input.nextLine();
		//books added to bookFileList
		while (input.hasNextLine()){
			bookFileList.add(input.nextLine());
		}
		input.close();
		
		int copiesIntOld = 0;
		int copiesIntNew = 0;
		//gets copies of book and subtracts it
		for (int i = 0; i < bookFileList.size(); i++){
			if (bookFileList.get(i).contains(ISBN)){
				//System.out.println((bookFileList.get(i).toString()));
				String copiesString = bookFileList.get(i).toString().split(";")[4];
				copiesIntOld = Integer.parseInt(copiesString);
				//number of copies subtracted if addSub = sub
				if (addSub == "sub"){
					copiesIntNew = copiesIntOld - 1;
				}
				else {
					copiesIntNew = copiesIntOld + 1;
				}
			}
		}
		String copiesOld = String.valueOf(copiesIntOld);
		String copiesNew = String.valueOf(copiesIntNew);
		
		File oldFile = bookDatabase;
		File newFile = new File("temp.txt");
		try {
			FileWriter fw = new FileWriter(newFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			Scanner input1 = new Scanner(oldFile);
			
			String line;
			input1.nextLine(); input1.nextLine();
			pw.println("Title;" + "Publisher;" + "date;" + "ISBN;" + "Copies;" + "Author");
			pw.println();
			while (input1.hasNextLine()){
				line = input1.nextLine();
				if ((line.contains(ISBN) && line.contains(copiesOld)) != true){
					pw.println(line);
				}
				else {
					String addLine[] = line.split(";");
					pw.print(addLine[0] + ";"); pw.print(addLine[1] + ";");
					pw.print(addLine[2] + ";"); pw.print(addLine[3] + ";");
					pw.print(copiesNew + ";"); pw.println(addLine[5]);
				}
			}
			input1.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File("bookfile.txt");
			newFile.renameTo(dump);
			System.out.println("Bookfile database updated!");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateJournalDatabase(String journal, String addSub){
		File oldJournalFile = new File("journalfile.txt");
		File newJournalFile = new File("temp.txt");
		
		try {
			FileWriter fw = new FileWriter(newJournalFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			Scanner input = new Scanner(oldJournalFile);
			
			String line;
			line = input.nextLine();
			pw.println("Title;PublisherDate;Volume;Issue;publisher;Articles");
			if (addSub == "sub"){
				while (input.hasNextLine()){
					line = input.nextLine();
					if (line.contains(journal) != true){
						pw.println(line);
					}
				}
			}
			else {
				while (input.hasNextLine()){
					line = input.nextLine();
					pw.println(line);
				}
				pw.println(journal);
			}
			
			input.close();
			pw.flush();
			pw.close();
			oldJournalFile.delete();
			File dump = new File("journalfile.txt");
			newJournalFile.renameTo(dump);
			System.out.println("Journal file database updated!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List <String> findJournalToReturn(String userId, String journalTitle){
		List <String> foundJournals = new ArrayList<>();
		File journalDatabase = new File("transaction.txt");
		Scanner input = null;
		try {
			input = new Scanner(journalDatabase);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line;
		line = input.nextLine();
		while (input.hasNextLine()){
			line = input.nextLine();
			if (line.split("/")[0].equals(userId) && line.contains(journalTitle)){
				foundJournals.add(line.split("/")[1]);
			}
		}
		input.close();
		return foundJournals;
	}
	
	public static void addJournal(String title, String publisherDate, String volume, 
								  String issue, String publisher, String articles){
		File docFile = new File("journalfile.txt");
		try {
			
			FileWriter fw = new FileWriter(docFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			
			out.println(title + ";" + publisherDate + ";" + volume + ";" + issue + ";"
			+ publisher + ";" + articles);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addBook(String title, String publisher, String date, String 
			                   ISBN, String copies, String author){
		File docFile = new File("bookfile.txt");
		try {
			
			FileWriter fw = new FileWriter(docFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			
			out.println(title + ";" + publisher + ";" + date + ";" + ISBN + 
					";" + copies + ";" + author);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeBook(String title){
		File oldFile = new File("bookfile.txt");
		File newFile = new File("temp.txt");
		
		try {
			Scanner input = new Scanner(oldFile);
			String line = null;
			line = input.nextLine(); line = input.nextLine();
			
			FileWriter fw = new FileWriter(newFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			
			out.println("Title;Publisher;date;ISBN;Copies;Author\n");
			while (input.hasNextLine()){
				line = input.nextLine();
				if (line.contains(title) != true){
					out.println(line);
				}
			}
			
			input.close();
			
			out.flush();
			out.close();
			oldFile.delete();
			File dump = new File("bookfile.txt");
			newFile.renameTo(dump);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeJournal(String title){
		File oldFile = new File("journalfile.txt");
		File newFile = new File("temp.txt");
		
		try {
			Scanner input = new Scanner(oldFile);
			String line = null;
			line = input.nextLine(); line = input.nextLine();
			
			FileWriter fw = new FileWriter(newFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			
			out.println("Title;PublisherDate;Volume;Issue;publisher;Articles\n");
			while (input.hasNextLine()){
				line = input.nextLine();
				if (line.contains(title) != true){
					out.println(line);
				}
			}
			
			input.close();
			
			out.flush();
			out.close();
			oldFile.delete();
			File dump = new File("journalfile.txt");
			newFile.renameTo(dump);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addDocument(){
		Scanner input = new Scanner(System.in);
		System.out.println("What document would you like to add?");
		System.out.println("1.) Book \n2.) Journal");
		System.out.print("Enter your choice: ");
		String choice = input.nextLine();
		
		String title, publisher, date, ISBN, copies, author;
		if (choice.equals("1")){
			System.out.print("Enter the title of the book: ");
			title = input.nextLine();
			System.out.print("Enter the publisher of the book: ");
			publisher = input.nextLine();
			System.out.print("Enter the date of the book: ");
			date = input.nextLine();
			System.out.print("Enter the book ISBN: ");
			ISBN = input.nextLine();
			System.out.print("Enter the number of copies of the book: ");
			copies = input.nextLine();
			System.out.print("Enter the author of the book: ");
			author = input.nextLine();
			addBook(title, publisher, date, ISBN, copies, author);
			
		}
		else {
			String publisherDate, volume, issue, articles;
			System.out.print("Enter the title of the journal: ");
			title = input.nextLine();
			System.out.print("Enter the publisher date of the journal: ");
			publisherDate = input.nextLine();
			System.out.print("Enter the volume of the journal: ");
			volume = input.nextLine();
			System.out.print("Enter the journal issue: ");
			issue = input.nextLine();
			System.out.print("Enter the publisher of the journal: ");
			publisher = input.nextLine();
			System.out.print("Enter the articles: ");
			articles = input.nextLine();
			addJournal(title, publisherDate, volume, issue, publisher, articles);
		}
	}
	
	public static void removeDocument(){
		Scanner input = new Scanner(System.in);
		System.out.println("What document would you like to remove?");
		System.out.println("1.) Book \n2.) Journal");
		System.out.print("Enter your choice: ");
		String choice = input.nextLine();
		
		if (choice.equals("1")){
			String title = null;
			System.out.print("Enter the title of the book you would like to remove: ");
			title = input.nextLine();
			removeBook(title);
		}
		else {
			String title = null;
			System.out.print("Enter the title of the journal you would like to remove: ");
			title = input.nextLine();
			removeJournal(title);
		}
	}
}
