import java.io.*;
import java.util.*;

public class LoanTransaction {
	
	public static void recordTransaction(String userID, String docID, String dueDate){
		File transactionFile = new File ("transaction.txt");
		// record user-id, doc-id, and the due-date 
		try {
			FileWriter fw = new FileWriter(transactionFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			Scanner input = new Scanner(transactionFile);
			if (input.hasNext() != true){
				out.println("User ID;Doc ID;Due Date");
			}
			input.close();
			//record added to the file
			out.println(userID + "/" + docID + "/" + dueDate);
			
			out.flush();
			out.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getDueDate(Document docObj){
		Calendar cal = Calendar.getInstance();
		if (docObj instanceof Journal){
			cal.add(Calendar.DATE, 10);
		}
		else {
			cal.add(Calendar.DATE, 182);
		}
		return cal.getTime().toString();
	}
	
	public static void updateLogFile(String name, String id, String docTitle, String action){
		File logsFile = new File ("logs.txt");
		// record user-id, doc-id, and the due-date 
		try {
			FileWriter fw = new FileWriter(logsFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			
			out.print(name + "\t" + id + "\t" + docTitle + "\t");
			if (action == "borrowed"){
				out.println("borrowed");
			}
			else {
				out.println("returned");
			}
			
			out.flush();
			out.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateTransaction(String id, String title){
		//System.out.println(id + "\t" + title);
		File oldFile = new File("transaction.txt");
		File newFile = new File("temp.txt");
		try {
			FileWriter fw = new FileWriter(newFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			Scanner input = new Scanner(oldFile);
			
			String line = input.nextLine();
			
			String[] compare = null;
			pw.println("User ID;Doc ID;Due Date");
			while(input.hasNextLine()){
				compare = input.nextLine().split("/");
				//System.out.println(line);
				if ((compare[0].equals(id) != true) || (compare[1].equals(title) != true)){
					pw.println(compare[0] + "/" + compare[1] + "/" + compare[2]);
				}
			}
			input.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File("transaction.txt");
			newFile.renameTo(dump);
			System.out.println("transaction file updated!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
