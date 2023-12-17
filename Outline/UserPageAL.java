/*
 * UserPageAL
 * 
 * This class will progress the algorithm of user page
 */

import java.util.ArrayList;

public class UserPageAL {
    
    //TODO This method will open a file selection page which will be used to pick a new photo and then upload it to the server with using DataBase.changeProfilePhotoTo(). If format is not readable or picked nothing return false.
    public boolean changeProfilePhotoTo(String username){
        return true;
    }

    /**TODO This method will sort the recipes with comparing their likes and return int array list. 
     * 
     * For example: "mantı" - ID: 23045 - 32 likes, "lahmacun" - ID: 11056 - 64 likes, "şiş kebap" - ID: 10432 - 40 likes; then the method should return [23045, 11056, 10432]
     */
    public ArrayList<Integer> sortTheRecipesOf(String username){
        return null;
    }

    // ************
    // READ HERE!!!
    // ************
    // This part will determine if we can edit the profile or not.
    //TODO This method will check if the current user page is ours and return true if it is.
    public boolean isOurProfile(String username){
        return true;
    }

    
}