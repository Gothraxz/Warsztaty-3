package pl.coderslab.util;

import pl.coderslab.model.Group;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

public class Utils {

	public static boolean userExists (User[] users, int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean solutionExists (Solution[] solutions, int id) {
		for (Solution solution : solutions) {
			if (solution.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean groupExists (Group[] groups, int id) {
		for (Group group : groups) {
			if (group.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    int i = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
}
