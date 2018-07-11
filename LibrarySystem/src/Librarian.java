
public class Librarian extends User{
	//empty constructor
		public Librarian(){
			
		}
		public Librarian(String newName, String newID, String newUserType){
			name = newName;
			ID = newID;
			userType = newUserType;
			setNumOfItems(getName(), getID());
		}
}
