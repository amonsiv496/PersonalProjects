

public class Student extends User{
	//empty constructor
	public Student(){
		
	}
	public Student(String newName, String newID, String newUserType){
		name = newName;
		ID = newID;
		userType = newUserType;
		setNumOfItems(getName(), getID());
	}
}
