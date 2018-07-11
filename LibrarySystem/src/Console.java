import java.io.*;
import java.util.*;

public class Console {
	
	private final static int MAX_NUM_OF_ITEMS_STUDENT = 6;
	private final static int MAX_NUM_OF_ITEMS_FACULTY = 12;

	public static int menu(User userObj){
		int choice = 0;
		Scanner input = null;
		
		if (userObj instanceof Librarian){
			// loop keeps running until valid input from user entered
			while (choice <= 0 | choice > 4){
				System.out.println("1) Borrow document");
				System.out.println("2) Return document");
				System.out.println("3) Add document");
				System.out.println("4) Remove document");
				System.out.print("Enter choice: ");
				input = new Scanner(System.in);
				// Exception handler if user enters letters instead of numbers
				try {
					choice = input.nextInt();
				} catch (InputMismatchException e){
					
				}
				// if statement used to prompt user to enter the right input
				// when incorrect choice entered as well
				if (choice <= 0){
					System.out.println("Incorrect input\nPlease enter one of the provided choices");
				}
				else if (choice > 4){
					System.out.println("Incorrect input\nPlease enter one of the provided choices");
				}
			}
		}
		else {
			// loop keeps running until valid input from user entered
			while (choice <= 0 | choice > 2){
				System.out.println("1) Borrow document");
				System.out.println("2) Return document");
				System.out.print("Enter choice: ");
				input = new Scanner(System.in);
				// Exception handler if user enters letters instead of numbers
				try {
					choice = input.nextInt();
				} catch (InputMismatchException e){
					
				}
				// if statement used to prompt user to enter the right input
				// when incorrect choice entered as well
				if (choice <= 0){
					System.out.println("Incorrect input\nPlease enter 1 to borrow document or 2 to return document");
				}
				else if (choice > 2){
					System.out.println("Incorrect input\nPlease enter 1 to borrow document or 2 to return document");
				}
			}
		}
		
		// choice from user
		switch(choice){
			case 1:
				return 1;
			case 2:
				return 2;
			case 3:
				return 3;
			case 4:
				return 4;
		}
		return 0;
	}
	
	static boolean canUserBorrowDoc(User userObj){
		// checks user number of copies of the user
		if (userObj instanceof Student){
			if (userObj.getNumOfItems() >=  MAX_NUM_OF_ITEMS_STUDENT){
				return false;
			}
		}
		else {
			if (userObj.getNumOfItems() >= MAX_NUM_OF_ITEMS_FACULTY){
				return false;
			}
		}
		return true;
	}
	
	public static void borrowBook(Book bookObj, User userObj){
		Scanner input = new Scanner(System.in);
		String book;
		System.out.println("\nBorrow book!");
		System.out.print("Search book to borrow: ");
		book = input.nextLine();
		List <String> foundBooksList = new ArrayList<>();
		String confirm = null;
		// method called to get the books found according to user input
		foundBooksList = LibDirectory.findDocumentToBorrow(book, "book");
		
		String searchAgain = null;
		// choose the book from the presented list if any book found
		if (foundBooksList.isEmpty() == false){
			String[] bookFoundInfo;
			int bookChoiceIndex = -1;
			//output found books and prompt user to choose book to borrow
			int i = 0;
			for (i = 0; i < foundBooksList.size(); i++){
				bookFoundInfo = foundBooksList.get(i).split(";");
				//output list of books found from the user input with formatted output
				System.out.printf("%d" + ".) " + "%-30s" + "%-15s" + "%-15s"
				 + "%-18s" + "%-4s" + "%s" + "\n", 
				 (i + 1),bookFoundInfo[0], bookFoundInfo[1], bookFoundInfo[2], bookFoundInfo[3], bookFoundInfo[4], bookFoundInfo[5] );
				if (i + 1 == foundBooksList.size()){
					System.out.print("Choose the book you wish to borrow: ");
					Scanner input1 = new Scanner(System.in);
					//handles error if string entered instead of a number
					try {
						bookChoiceIndex = input1.nextInt();
						// handles error if a choice outside of the presented scope chosen
						if ((bookChoiceIndex < 1) || (bookChoiceIndex > foundBooksList.size())){
							System.out.print("Please choose the available book choices\n");
							i = -1;
						}
					} catch (InputMismatchException e){
						System.out.print("Please choose the available book choices\n");
						i = -1;
					}
				}
			}
			// post: book index chosen successfully to get book chosen
			
			bookFoundInfo = foundBooksList.get(bookChoiceIndex - 1).split(";");
			// book object constructor initialized 
			bookObj = new Book(bookFoundInfo[0], bookFoundInfo[1], bookFoundInfo[2], bookFoundInfo[3], bookFoundInfo[4], bookFoundInfo[5]);
			
			System.out.println("Confirm to borrow " + bookObj.getTitle());
			System.out.print("Enter yes or no: ");
			confirm = input.nextLine();
			
			while (confirm.equals("yes") != true && confirm.equals("no") != true){
				System.out.print("Please enter yes or no: ");
				confirm = input.nextLine();
			}
			
			if (confirm.contains("yes")){
				//due date calculated
				String dueDateString = LoanTransaction.getDueDate(bookObj);
				//System.out.println("Recording transaction!");
				String[] dueDate = dueDateString.split(" ");
				LoanTransaction.recordTransaction(userObj.getID(), bookObj.getTitle(), dueDate[0] + " " + dueDate[1] + " " + dueDate[2] + " " + dueDate[5]);
				//System.out.println("Logging transaction");
				LoanTransaction.updateLogFile(userObj.getName(), userObj.getID(), bookObj.getTitle(), "borrowed");
				//System.out.println("Updating book database");
				LibDirectory.updateBookDatabase(((Book) bookObj).getISBN(), "sub");
				//System.out.println("Updating userNumOfItemsFile");
				userObj.updateNumOfItems(userObj, "borrow");
			}
			else {
				System.out.println("Not confirmed!");
			}
		}
		else {
			System.out.println("No book found!");
		}
	}
	
	public static void borrowJournal(Journal journalObj, User userObj){
		Scanner input1 = new Scanner(System.in);
		String journal;
		System.out.println("\nBorrow journal");
		System.out.print("Search journal you wish to borrow: ");
		journal = input1.nextLine();
		String[] journalTitle = null;
		
		List <String> foundJournalList = new ArrayList<>();
		foundJournalList = LibDirectory.findDocumentToBorrow(journal, "journal");
		String confirm = null;
		
		// if any journal found, proceed, if not, give option to exit
		if (foundJournalList.isEmpty() == false){
			String[] journalFoundInfo;
			int journalChoiceIndex = -1;
			//output found journals and prompt user to choose journal to borrow
			// fix for loop when bookChoice is entered as 0
			int i = 0;
			for (i = 0; i < foundJournalList.size(); i++){
				journalFoundInfo = foundJournalList.get(i).split(";");
				//output list of books found from the user input with formatted output
				System.out.printf("%d" + ".) " + "%-35s" + "%-15s" + "%-5s"
				 + "%-5s" + "%-10s" + "%s" + "\n", 
				 (i + 1),journalFoundInfo[0], journalFoundInfo[1], journalFoundInfo[2], journalFoundInfo[3], journalFoundInfo[4], journalFoundInfo[5] );
				if (i + 1 == foundJournalList.size()){
					System.out.print("Choose the book you wish to borrow: ");
					Scanner input11 = new Scanner(System.in);
					//handles error if string entered instead of a number
					try {
						journalChoiceIndex = input11.nextInt();
						// handles error if a choice outside of the presented scope chosen
						if ((journalChoiceIndex < 1) || (journalChoiceIndex > foundJournalList.size())){
							System.out.print("Please choose the available journal choices\n");
							i = -1;
						}
					} catch (InputMismatchException e){
						System.out.print("Please choose the available journal choices\n");
						i = -1;
					}
				}
			}
			// post: book index chosen successfully to get book chosen
			
			journalFoundInfo = foundJournalList.get(journalChoiceIndex - 1).split(";");
			journalObj = new Journal(journalFoundInfo[0], journalFoundInfo[1], journalFoundInfo[2], journalFoundInfo[3], journalFoundInfo[4], journalFoundInfo[5]);
			
			System.out.println("Confirm to borrow " + journalObj.getTitle());
			System.out.print("Enter yes or no: ");
			confirm = input1.nextLine();
			
			while (confirm.equals("yes") != true && confirm.equals("no") != true){
				System.out.print("Please enter yes or no: ");
				confirm = input1.nextLine();
			}
			
			if (confirm.contains("yes")){
				//due date calculated
				String dueDateString = LoanTransaction.getDueDate(journalObj);
				//System.out.println("Recording transaction!");
				String[] dueDate = dueDateString.split(" ");
				LoanTransaction.recordTransaction(userObj.getID(), foundJournalList.get(journalChoiceIndex - 1), dueDate[0] + " " + dueDate[1] + " " + dueDate[2] + " " + dueDate[5]);
				//System.out.println("Logging transaction");
				LoanTransaction.updateLogFile(userObj.getName(), userObj.getID(), journalObj.getTitle(), "borrowed");
				//System.out.println("Updating journal database");
				LibDirectory.updateJournalDatabase(foundJournalList.get(journalChoiceIndex - 1), "sub");
				//System.out.println("Updating userNumOfItemsFile");
				userObj.updateNumOfItems(userObj, "borrow");
			}
			else {
				System.out.println("Not confirmed!");
			}
		}
		else {
			System.out.println("No journal matches!");
		}
	}
	
	public static void returnBook(User userObj){
		System.out.println("Return book!");
		Scanner input1 = new Scanner(System.in);
		String book;
		System.out.print("Enter the book you wish to return: ");
		book = input1.nextLine();
		
		// finds book from transaction file
		List <String> foundBooksList = new ArrayList<>();
		foundBooksList = LibDirectory.findBookToReturn(userObj.getID(), book);
		
		if (foundBooksList.isEmpty() == false){
			int bookChoiceIndex = -1;
			//output found books and prompt user to choose book to borrow
			// fix for loop when bookChoice is entered as 0
			int i = 0;
			for (i = 0; i < foundBooksList.size(); i++){
				System.out.printf("%d " + "%s\n", i + 1, foundBooksList.get(i));
				
				if (i + 1 == foundBooksList.size()){
					System.out.print("Choose the book you wish to return: ");
					Scanner input2 = new Scanner(System.in);
					//handles error if string entered instead of a number
					try {
						bookChoiceIndex = input2.nextInt();
						// handles error if a choice outside of the presented scope chosen
						if ((bookChoiceIndex < 1) || (bookChoiceIndex > foundBooksList.size())){
							System.out.print("Please choose the available book choices\n");
							i = -1;
						}
					} catch (InputMismatchException e){
						System.out.print("Please choose the available book choices\n");
						i = -1;
					}
				}
			}
			// post: book index chosen successfully to get book chosen
			
			//System.out.println("Updating transaction!"); // works
			LoanTransaction.updateTransaction(userObj.getID(), foundBooksList.get(bookChoiceIndex - 1));
			//System.out.println("Logging transaction!"); // works
			LoanTransaction.updateLogFile(userObj.getName(), userObj.getID(), foundBooksList.get(bookChoiceIndex - 1), "returned");
			//System.out.println("Updating bookfile.txt"); // works
			LibDirectory.updateBookDatabase(foundBooksList.get(bookChoiceIndex - 1), "add");
			//System.out.println("Updating userNumOfItmesFile"); // works
			userObj.updateNumOfItems(userObj, "return");
		}
		else {
			System.out.println("No books found!");
		}
	}
	
	public static void returnJournal(User userObj){
		Scanner input1 = new Scanner(System.in);
		System.out.println("Search journal to return: ");
		String journalTitle = input1.nextLine();
		
		// only works when a journal title entered
		List <String> foundJournals = LibDirectory.findJournalToReturn(userObj.getID(), journalTitle);
		
		if (foundJournals.isEmpty() == false){
			int journalChoiceIndex = -1;
			//output found books and prompt user to choose book to borrow
			int i = 0;
			String journalFound = null;
			// fix for loop when bookChoice is entered as 0
			for (i = 0; i < foundJournals.size(); i++){
				journalFound = foundJournals.get(i);
				
				System.out.printf("%d" + ".) " + "%-35s" + "\n", 
						 (i + 1),journalFound);
				
				if (i + 1 == foundJournals.size()){
					System.out.println("Choose the journal you wish to return");
					Scanner input2 = new Scanner(System.in);
					//handles error if string entered instead of a number
					try {
						journalChoiceIndex = input2.nextInt();
						// handles error if a choice outside of the presented scope chosen
						if ((journalChoiceIndex < 1) || (journalChoiceIndex > foundJournals.size())){
							System.out.print("Please choose the available journal choices\n");
							i = -1;
						}
					} catch (InputMismatchException e){
						System.out.print("Please choose the available journal choices\n");
						i = -1;
					}
				}
			}
			
			// post: journal index chosen successfully to get book chosen
			
			//System.out.println("Updating transaction!"); // works
			LoanTransaction.updateTransaction(userObj.getID(), foundJournals.get(journalChoiceIndex - 1));
			//System.out.println("Logging transaction!"); // works
			LoanTransaction.updateLogFile(userObj.getName(), userObj.getID(), foundJournals.get(journalChoiceIndex - 1).split(";")[0], "returned");
			//System.out.println("Updating journalfile.txt");
			LibDirectory.updateJournalDatabase(foundJournals.get(journalChoiceIndex - 1), "add");
			//System.out.println("Updating userNumOfItemsFile");
			userObj.updateNumOfItems(userObj, "return");
		}
		else {
			System.out.println("No journal matches!");
		}
	}

	public static void main(String args[]){
		
		User userObj = null;
		Student studentObj = new Student();
		Faculty facultyObj = new Faculty();
		Librarian librarianObj = new Librarian();
		
		Document docObj = new Document();
		Book bookObj = new Book();
		Journal journalObj = new Journal();
		
		String name;
		String password;
		String loggedName = null;
		Scanner input = new Scanner(System.in);
		do {
			// input name and password
			System.out.print("Enter name: ");
			name = input.nextLine();
			System.out.print("Enter password: ");
			password = input.nextLine();
			loggedName = LibDirectory.findUser(name, password);
			if (loggedName == null)
				System.out.println("Incorrent name or password\n");
			else
				System.out.println("Looged in successfully!\n");
		} while (loggedName == null);
		
		
		// after user found in logins database, find user type in UserFile, objects with constructors initialized
		String user[] = LibDirectory.findUserType(loggedName).split("(;|\n)");
		if (user == null){
			System.out.println("User not found!");
		}else if (user[2].contains("student")){
			studentObj = new Student(user[0], user[1], user[2]);
			userObj = studentObj;
		} else if (user[2].contains("faculty")){
			userObj = new Faculty(user[0], user[1], user[2]);
		} else {
			userObj = new Librarian(user[0], user[1], user[2]);
			System.out.println("Librarian object created!");
		}
		
		
		
		int loopChoice = 1;
		// loop until user enters exiting option
		while (loopChoice != 0){
			// display menu options 
			switch (menu(userObj)){
				//borrow
				case 1: 
					if (canUserBorrowDoc(userObj) == false){
						System.out.println("Can't borrow document, user has " +
						userObj.getNumOfItems() + " items borrowed!");
						break;
					}
					
					int choice = -1;
					// loops keep running until right input entered for document chosen
					while (choice == 0 || choice < 0 | choice > 2){
						System.out.println("\nChoose document to borrow");
						System.out.println("1) Book" + "\n2) Journal");
						System.out.print("Enter choice: ");
						input = new Scanner(System.in);
						try {
							choice = input.nextInt();
						} catch (InputMismatchException e){
						}
						if (choice <= 0){
							System.out.println("Incorrect input\nPlease enter 1 to borrow book or 2 to borrow journal");
						}
						else if (choice > 2 && ((userObj instanceof Student) && (userObj instanceof Faculty))){
							System.out.println("Incorrect input\nPlease enter 1 to borrow book or 2 to borrow journal");
						}
					}
					if (choice == 1){ 
						borrowBook(bookObj, userObj);
					} 
					else { 
						borrowJournal(journalObj, userObj);
					}
					break;
				//return
				case 2: // return 
					Scanner input1 = new Scanner(System.in);
					System.out.println("\nReturn document!");
					System.out.println("Choose document to return");
					System.out.println("1) Book" + "\n2) Journal");
					System.out.print("Enter choice: ");
					int choice1 = input1.nextInt();
					if (choice1 == 1){ 
						returnBook(userObj);
					} 
					else {
						returnJournal(userObj);
					}
					break;
				case 3: // add document
					LibDirectory.addDocument();
					break;
				case 4: // remove document
					LibDirectory.removeDocument();
			}
			Scanner inputChoice = new Scanner(System.in);
			System.out.print("Enter 0 to exit or any other number to continue: ");
			loopChoice = inputChoice.nextInt();
		}//end of loop
		System.out.println("Exiting!");
		System.exit(0);
	}
}
