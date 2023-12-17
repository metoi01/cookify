/**
 * UserMemory
 * 
 * This class is a temporary memory that updates every time that app runs.
 * This class's mission is save the current user's informations
 */


public class UserMemory {
    // Users saved variables
    public static String username;
    public static String password;

    // Get the current user's username
    public static String getName(){
        return username;
    }

    // Get the current user's password
    public static String getPassword(){
        return password;
    }

    // This will update the current user and its informations
    public static void storeUser(String newUsername, String newPassword){
        username = newUsername;
        password = newPassword;
    }
    
}