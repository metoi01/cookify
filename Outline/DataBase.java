/*
 * DataBase
 * 
 * This class is responsible for connecting, writing and reading from the server
 */

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DataBase {

    //
    // LOGIN & REGISTER METHODS
    //

    //TODO This method will return password of the user.
    public String getPasswordOf(String username){
        return null;
    }

    //TODO This method will get username and password then create a new user in the server.
    public void saveNewUser(String username, String password){
    }

    //TODO This method will check if the inputed name is a user in the server; if it is return true, else return false.
    public boolean doesUserExists(String name){
        return true;
    }


    //
    // PROFILE EDIT METHODS
    //

    //TODO This method will change the password of the given user.
    public void changePasswordTo(String username, String newPassword){
    }

    //TODO This method will change the personal info of the given user.
    public void changePersonalInfoTo(String username, String newPersonalInfo){
    }

    //TODO This method will change the profile photo of the given user.
    public void changeProfilePhotoTo(String username, BufferedImage image){
    }

    //TODO This method will add a new recipe to the given user.
    public void addRecipe(String username, int recipeID){
    }

    //TODO This method will remove recipe from the given user.
    public void removeRecipe(String username, int recipeID){
    }

    //TODO This method will change the vote of the given user to the reciever user. If the user has never given a vote for other user you will add the username to voter list, however if user has alredy given vote to the other user just change the vote.
    public void voteTo(String votedBy, String votedTo, int voteRate){
    }

    //TODO This method will make a user to follow another user.
    public void followTo(String follower, String followed){
    }

    //
    // USER ATTRIBUTES 
    // (Likes, comments, votes...)
    //

    //TODO This method will get the current voteRate.
    public float getVoteRateOf(String username){
        return 0;
    }

    //TODO This method will get the current followers.
    public int getFollowerCountOf(String username){
        return 0;
    }

    //TODO This method will get the personal info.
    public String getPersonalInfoOf(String username){
        return null;
    }

    //TODO This method will get the ID's of recipes.
    public ArrayList<Integer> getRecipeListOf(String username){
        return null;
    }

    //TODO This method will get the profile photo.
    public BufferedImage getProfilePhotoOf(String username){
        return null;
    }

    //
    // RECIPE ATTIRIBUTES
    //

    //TODO Ths method will create a new recipe and return its ID
    public String createNewRecipe(){
        return null;
    }

    //TODO This method will get the name of the recipe
    public String getRecipeNameOf(int ID){
        return null;
    }

    //TODO This method will get the chef name
    public String getChefOf(int ID){
        return null;
    }

    //TODO This method will get the rate of votes
    public float getVoteRateOf(int ID){
        return 0;
    }

    //TODO This method will get the like count
    public int getLikeCountOf(int ID){
        return 0;
    }

    //TODO This method will get the cooked count
    public int getCookCountOf(int ID){
        return 0;
    }

    //TODO This method will get the comment amount
    public int getCommentCountOf(int ID){
        return 0;
    }

    //TODO This method will get the share count
    public int getShareCountOf(int ID){
        return 0;
    }

    //TODO This method will get the list of comments
    public ArrayList<String> getCommentsOf(int ID){
        return null;
    }

    //TODO This method will get the recipe explanation
    public String getRecipeExplanation(int ID){
        return null;
    }

    //TODO This method will get the image of recipe
    public BufferedImage getImageOf(int ID){
        return null;
    }

    //TODO This method will get the sections. The recipe sections are explained in sections.txt part
    public boolean[] getSectionsOf(int ID){
        return null;
    }

    //TODO This method will get the recipe list by returning their IDs and amount and amount kind in a int array list. 
    //An ingridient will store 3 things: Their ID, their amount, their amount type like: 2067, 300 , 5; which seen like: "yogurt", 300, "grams" 
    //See AmountKind.txt
    public ArrayList<int[]> getIngridientListOf(int ID){
        return null;
    }

    //
    // INGRIDIENT METHODS
    //

    //TODO This method will get the the ID of xth ingridient: For example x is 5 than it will return 5th ingridient in the server's ingiridient list, maybe "yogurt".
    public int getIngiridient(int index){
        return 0;
    }
}

