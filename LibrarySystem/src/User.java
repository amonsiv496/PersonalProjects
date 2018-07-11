import java.io.*;
import java.util.*;

public class User {
	protected String name;
	protected String ID;
	protected String userType;
	protected int numOfItems;
	
	public String getName(){
		return name;
	}
	
	public String getID(){
		return ID;
	}
	
	public String getUserType(){
		return userType;
	}
	
	public int getNumOfItems(){
		return numOfItems;
	}
	
	public int setNumOfItems(String name, String id){
		File fileItems = new File(name + id + "NumOfItems.txt");
		if (fileItems.exists() != true){
			numOfItems = 0;
			return numOfItems;
		}
		Scanner input = null;
		try {
			input = new Scanner(fileItems);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		numOfItems = input.nextInt();
		input.close();
		return numOfItems;
	}
	
	public void updateNumOfItems(User userObj, String choice){
		File fileItems = new File(userObj.getName() + userObj.getID() + "NumOfItems.txt");
		PrintWriter userFileItems = null;
		if (fileItems.exists() != true){ // if file doesn't exits =============
			try {
				userFileItems = new PrintWriter(userObj.getName() + userObj.getID() + "NumOfItems.txt");
				userFileItems.print(1);
				numOfItems = 1;
				userFileItems.flush();
				userFileItems.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end if file doesn't exits ==========================================
		else { // if file exists
			Scanner input = null;
			PrintWriter newNumOfItems = null;
			try {
				input = new Scanner(fileItems);
				int copies = input.nextInt();
				
				newNumOfItems = new PrintWriter(userObj.getName() + userObj.getID() + "NumOfItems.txt");
				if (choice.contains("borrow")){
					newNumOfItems.print(copies + 1);
					numOfItems = copies + 1;
					newNumOfItems.close();
				}
				else {
					newNumOfItems.print(copies - 1);
					numOfItems = copies - 1;
					newNumOfItems.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}