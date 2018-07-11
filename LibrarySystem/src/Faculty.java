import java.io.File;

public class Faculty extends User{
	//empty constructor
		public Faculty(){
			
		}
		public Faculty(String newName, String newID, String newUserType){
			name = newName;
			ID = newID;
			userType = newUserType;
			setNumOfItems(getName(), getID());
		}
}
