package com.example.yenia;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class HelloController {
    //Variables

    //Gui Variables
    @FXML
    private Button usernameButton,mybasketButton,chiefsButton,myfridgeButton,personalInfoEdit,personalInfoDone;
    @FXML
    private SplitPane mybasketPane,usernamePane,chiefsPane,myfridgePane,recipesPane,settingsPane,logoutPane,otherProfilePane,myFridgeAddIngredientPane,addRecipePane,addIngredientPane,foodPagePane;
    @FXML
    private Pane loginPane,registerPane,mainPane;
    @FXML
    private TextField loginUsername,loginPassword,registerUsername,registerPassword1,registerPassword2,personalInfo,chiefsSearch,myBasketSearch,settingsChangeUsername,settingsChangePassword1,settingsChangePassword2,settingsChangePassword3,settingsDeleteAccount,settingsChangeUsername2,recipesSearch,addIngredientSearch,amount,newRecipeExplanition,voteNumber,foodPageVoteNumber;
    @FXML
    private Text loginError,registerError,chiefs1,chiefs2,chiefs3,chiefs4,chiefs5,chiefs6,rating1,rating2,rating3,rating4,rating5,rating6,chiefsPageNum,otherProfilePersonalInfo,recipesFoodName1,recipesFoodName2,recipesFoodName3,recipesFoodName4,recipesPageNumber,addRecipeIngredients1,addRecipeIngredients2,addRecipeIngredients3,addRecipeIngredients4,foodcode1,foodcode2,foodcode3,foodcode4,foodPageFoodID;
    @FXML
    private Text myBasketFoodName1,myBasketFoodName2,myBasketFoodName3,myBasketFoodName4,myBasketLike1,myBasketLike2,myBasketLike3,myBasketLike4,myBasketPageNumber,settingsPasswordError,settingsUsernameError,settingsDeleteError,recipesLike1,recipesLike2,recipesLike3,recipesLike4,addIngredient1,addIngredient2,addIngredient3,addIngredient4,code1,code2,code3,code4,foodPageName,foodPageUsername,foodPageIngredients,foodPageRecipe,foodPageVote;
    @FXML
    private Label profilePageName,profilePageFollowers,profilePageVoteRate,otherProfileName,otherProfileFollowers,otherProfileVote,myfridge1,myfridge2,myfridge3,myfridge4,myfridgePage;
    @FXML
    private ImageView myBasketImage1,myBasketImage2,myBasketImage3,myBasketImage4,recipesImage1,recipesImage2,recipesImage3,recipesImage4,profilePhoto,otherProfilePhoto,foodPageImage;
    private File image;


    //AL Variables
    private DataBase db=new DataBase();
    private int currentPage=0;
    private int lastPage=0;
    private int chiefsSection=0;
    private int myBasketSection=0;
    private int recipesSection=0;
    private int myfridgeSection=0;
    private int addRecipeSection=0;
    private int type=0;
    private ArrayList<Integer>currentRecipeList=db.getAllRecipes();
    private ArrayList<int[]>fridge=new ArrayList<>();
    //Methods

    //Button Methods
    @FXML
    protected void usernameButtonClick() {
        updateProfilePageGUI();
        changePage(0);
    }
    @FXML
    protected void myfridgeButtonClick()
    {
        fridge=null;
        updateMyFridgeGUI();
        changePage(4);
    }
    @FXML
    protected void chiefsButtonClick() {changePage(2);}
    @FXML
    protected void mybasketButtonClick()
    {
        changePage(1);
        updateMyBasketGUI();
    }
    @FXML
    protected void recipesButtonClick()
    {
        currentRecipeList=db.getAllRecipes();
        updateRecipesGUI();
        changePage(3);
    }
    @FXML
    protected void settingsButtonClick()
    {
        changePage(5);
    }
    @FXML
    protected void logoutButtonClick()
    {
        changePage(6);
    }
    @FXML
    protected void createAccButtonClick()
    {
        loginPane.setVisible(false);
        loginPane.setDisable(true);
        registerPane.setVisible(true);
        registerPane.setDisable(false);
    }
    @FXML
    protected void regLoginButtonClick()
    {
        loginPane.setVisible(true);
        loginPane.setDisable(false);
        registerPane.setVisible(false);
        registerPane.setDisable(true);
    }
    @FXML
    protected void loginButtonClick()
    {
        if(loginUsername.getText().isEmpty()||loginPassword.getText().isEmpty())
        {
            loginError.setText("You must fill all the spaces.");
        }
        else if(db.doesUserExists(loginUsername.getText()))
        {
            if(db.getPasswordOf(loginUsername.getText()).equals(loginPassword.getText()))
            {
                UserMemory.storeUser(loginUsername.getText(),loginPassword.getText());
                loginPane.setVisible(false);
                loginPane.setDisable(true);
                mainPane.setVisible(true);
                mainPane.setDisable(false);
                updateProfilePageGUI();
                updateChiefsPageGUI(db.getAllUsers());
                updateMyBasketGUI();
            }
            else
            {
                loginError.setText("Password is incorrect.");
            }
        }
        else
        {
            loginError.setText("User cannot be found.");
        }
    }
    @FXML
    protected void registerButtonClick()
    {
        if(registerUsername.getText().isEmpty()||registerPassword1.getText().isEmpty()||registerPassword2.getText().isEmpty())
        {
            registerError.setText("You must fill all the spaces.");
        }
        else if(db.doesUserExists(registerUsername.getText()))
        {
            registerError.setText("Username already taken.");
        }
        else if(registerUsername.getText().length()<3||registerUsername.getText().length()>15)
        {
            registerError.setText("Username must be between 3 and 15 characters.");
        }
        else if(registerPassword1.getText().length()<3||registerPassword1.getText().length()>15)
        {
            registerError.setText("Password must be between 3 and 15 characters.");
        }
        else if(!registerPassword1.getText().equals(registerPassword2.getText()))
        {
            registerError.setText("Re-entered password is not valid.");
        }
        else
        {
            db.saveNewUser(registerUsername.getText(),registerPassword1.getText());
            UserMemory.storeUser(registerUsername.getText(),registerPassword1.getText());
            registerPane.setVisible(false);
            registerPane.setDisable(true);
            mainPane.setVisible(true);
            mainPane.setDisable(false);
            updateProfilePageGUI();
            updateChiefsPageGUI(db.getAllUsers());
            updateMyBasketGUI();
        }
    }
    @FXML
    protected void personalInfoEditButtonClick()
    {
        personalInfo.setEditable(true);
        personalInfoEdit.setVisible(false);
        personalInfoEdit.setDisable(true);
        personalInfoDone.setVisible(true);
        personalInfoDone.setDisable(false);
    }
    @FXML
    protected void personalInfoDoneButtonClick()
    {
        personalInfo.setEditable(false);
        personalInfoDone.setVisible(false);
        personalInfoDone.setDisable(true);
        personalInfoEdit.setVisible(true);
        personalInfoEdit.setDisable(false);
        db.changePersonalInfoTo(UserMemory.getName(),personalInfo.getText());
    }
    @FXML
    protected void chiefsSearchButtonClick()
    {
        chiefsSection=0;
        updateChiefsPageGUI(sortListSearch(db.getAllUsers(),chiefsSearch.getText()));
    }
    @FXML
    protected void chiefsUpButtonClick()
    {
        if(chiefsSection>0)
        {
            chiefsSection--;
        }
        updateChiefsPageGUI(sortListSearch(db.getAllUsers(),chiefsSearch.getText()));
    }
    @FXML
    protected void chiefsDownClick()
    {
        if(sortListSearch(db.getAllUsers(),chiefsSearch.getText()).size()>(chiefsSection+1)*6)
        {
           chiefsSection++;
        }
        updateChiefsPageGUI(sortListSearch(db.getAllUsers(),chiefsSearch.getText()));
    }
    @FXML
    protected void chiefs1ButtonClick()
    {
        visitProfile(chiefs1.getText());
    }
    @FXML
    protected void chiefs2ButtonClick()
    {
        visitProfile(chiefs2.getText());
    }
    @FXML
    protected void chiefs3ButtonClick()
    {
        visitProfile(chiefs3.getText());
    }
    @FXML
    protected void chiefs4ButtonClick()
    {
        visitProfile(chiefs4.getText());
    }
    @FXML
    protected void chiefs5ButtonClick()
    {
        visitProfile(chiefs5.getText());
    }
    @FXML
    protected void chiefs6ButtonClick()
    {
        visitProfile(chiefs6.getText());
    }
    @FXML
    protected void myBasketUpButtonClick()
    {
        if(myBasketSection>0) myBasketSection--;
        updateMyBasketGUI();
    }
    @FXML
    protected void myBasketDownButtonClick()
    {
        if(db.getRecipeListOf(UserMemory.getName())!=null) {
            if (db.getRecipeListOf(UserMemory.getName()).size() > myBasketSection * 4) {
                myBasketSection++;
            }
        }
        updateMyBasketGUI();
    }
    @FXML
    protected void mybasketSearchButtonClick()
    {
        updateMyBasketGUI();
    }
    @FXML
    protected void noLogoutButtonClick()
    {
        changePage(3);
    }
    @FXML
    protected void yesLogoutButtonClick()
    {
        changePage(0);
        mainPane.setDisable(true);
        mainPane.setVisible(false);
        loginPane.setVisible(true);
        loginPane.setDisable(false);

    }
    @FXML
    protected void changeUsernameButtonClick()
    {
        settingsUsernameError.setText(" ");
        if(settingsChangeUsername.getText().length()<3||settingsChangeUsername.getText().length()>15)
        {
            settingsUsernameError.setText("New username must be between 3-15 characters.");
        }
        else if(!(settingsChangeUsername2.getText().equals(UserMemory.getPassword())))
        {
            settingsUsernameError.setText("Password is incorrect.");
        }
        else
        {
            db.changeUsernameTo(UserMemory.getName(),settingsChangeUsername.getText());
            UserMemory.storeUser(settingsChangeUsername.getText(),UserMemory.getPassword());
            updateProfilePageGUI();
            updateMyBasketGUI();
            updateChiefsPageGUI(db.getAllUsers());
            settingsUsernameError.setText("Username has successfully been changed.");
        }
    }
    @FXML
    protected void changePasswordButtonClick()
    {
        settingsPasswordError.setText(" ");
        if(!(settingsChangePassword1.getText().equals(UserMemory.getPassword())))
        {
            settingsPasswordError.setText("Password is incorrect.");
        }
        else if(settingsChangePassword2.getText().length()<3||settingsChangePassword2.getText().length()>15)
        {
            settingsPasswordError.setText("New password must be between 3-15 characters.");
        }
        else if(!(settingsChangePassword2.getText().equals(settingsChangePassword3.getText())))
        {
            settingsPasswordError.setText("Passwords do not match.");
        }
        else
        {
            db.changePasswordTo(UserMemory.getName(),settingsChangePassword2.getText());
            UserMemory.storeUser(UserMemory.getName(),settingsChangePassword2.getText());
            settingsPasswordError.setText("Password has successfully been changed.");
        }
    }
    @FXML
    protected void deleteAccountButtonClick()
    {
        settingsDeleteError.setText(" ");
        if(settingsDeleteAccount.getText().equals(UserMemory.getPassword()))
        {
            db.deleteUser(UserMemory.getName());
            changePage(0);
            mainPane.setDisable(true);
            mainPane.setVisible(false);
            loginPane.setVisible(true);
            loginPane.setDisable(false);
            settingsUsernameError.setText(" ");
            settingsPasswordError.setText(" ");
        }
        else
        {
            settingsDeleteError.setText("Password is incorrect.");
        }
    }

    @FXML
    protected void recipesUpButtonClick()
    {
        if(recipesSection>0) recipesSection--;
        updateRecipesGUI();
    }
    @FXML
    protected void recipesDownButtonClick()
    {
        if(db.getRecipeListOf(UserMemory.getName())!=null) {
            if (db.getAllRecipes().size() > recipesSection * 4) {
                recipesSection++;
            }
        }
        updateRecipesGUI();
    }
    @FXML
    protected void recipesSearchButtonClick()
    {
        updateRecipesGUI();
    }
    @FXML
    protected void profileRecipesButtonClick()
    {
        currentRecipeList=db.getRecipeListOf(otherProfileName.getText());
        updateRecipesGUI();
        changePage(3);
    }
    @FXML
    protected void myfridgeUpButtonClick()
    {
        if(myfridgeSection>0) myfridgeSection--;
        updateMyFridgeGUI();
    }
    @FXML
    protected void myfridgeDownButtonClick()
    {
        if(fridge!=null) {
            if (fridge.size() > recipesSection * 4) {
                myfridgeSection++;
            }
        }
        updateMyFridgeGUI();
    }
    @FXML
    protected void myBasketAddRecipeButtonClick()
    {
        changePage(8);
    }
    @FXML
    protected void addImageButtonClick()
    {
        FileChooser fileChooser=new FileChooser();
        image=fileChooser.showOpenDialog(null);
    }
    @FXML
    protected void addRecipeUpButtonClick()
    {
        if(addRecipeSection>0) addRecipeSection--;
        updateAddRecipeGUI();
    }
    @FXML
    protected void addRecipeDownButtonClick()
    {
        if(fridge!=null) {
            if (fridge.size() > addRecipeSection * 4) {
                addRecipeSection++;
            }
        }
        updateAddRecipeGUI();
    }
    @FXML
    protected void addIngredientSearchButtonClick()
    {
        updateAddIngredientGUI();
    }
    @FXML
    protected void addIngredientButtonClick()
    {
        lastPage=currentPage;
        updateAddIngredientGUI();
        changePage(9);
    }
    @FXML
    protected void add1ButtonClick()
    {
        if(!(amount.getText().isEmpty())&&!(code1==null)) {
            int[]a={Integer.parseInt(code1.getText()),Integer.parseInt(amount.getText()),type};
            fridge.add(a);
            updateAddRecipeGUI();
            updateMyFridgeGUI();
            changePage(lastPage);
        }
    }
    @FXML
    protected void add2ButtonClick()
    {
        if(!(amount.getText().isEmpty())&&!(code2==null)) {
            int[]a={Integer.parseInt(code2.getText()),Integer.parseInt(amount.getText()),type};
            fridge.add(a);
            updateAddRecipeGUI();
            updateMyFridgeGUI();
            changePage(lastPage);
        }
    }
    @FXML
    protected void add3ButtonClick()
    {
        if(!(amount.getText().isEmpty())&&!(code3==null)) {
            int[]a={Integer.parseInt(code3.getText()),Integer.parseInt(amount.getText()),type};
            fridge.add(a);
            updateAddRecipeGUI();
            updateMyFridgeGUI();
            changePage(lastPage);
        }
    }
    @FXML
    protected void add4ButtonClick()
    {
        if(!(amount.getText().isEmpty())&&!(code4==null)) {
            int[]a={Integer.parseInt(code4.getText()),Integer.parseInt(amount.getText()),type};
            fridge.add(a);
            updateAddRecipeGUI();
            updateMyFridgeGUI();
            changePage(lastPage);
        }
    }
    @FXML
    protected void menu1ButtonClick() {type=0;}
    @FXML
    protected void menu2ButtonClick() {type=1;}
    @FXML
    protected void menu3ButtonClick() {type=2;}
    @FXML
    protected void menu4ButtonClick() {type=3;}
    @FXML
    protected void menu5ButtonClick() {type=4;}
    @FXML
    protected void menu6ButtonClick() {type=5;}
    @FXML
    protected void menu7ButtonClick() {type=6;}
    @FXML
    protected void addRecipe() throws IOException
    {
        Image ab=convertToFxImage(ImageIO.read(image));
        Recipe recipe=new Recipe(Integer.parseInt(db.createNewRecipe()),ab,"a", newRecipeExplanition.getText(),fridge,null);
    }
    @FXML
    protected void followButtonClick() {db.followTo(UserMemory.getName(),otherProfileName.getText());}
    @FXML
    protected void voteButtonClick() {db.voteTo(UserMemory.getName(),otherProfileName.getText(),Integer.parseInt(voteNumber.getText()));}
    @FXML
    protected void image1ButtonClick()
    {
        foodPageFoodID.setText(foodcode1.getText());
        foodPageUsername.setText(db.getChefOf(Integer.parseInt(foodcode1.getText())));
        foodPageName.setText(db.getNameOf(Integer.parseInt(foodcode1.getText())));
        foodPageImage.setImage(convertToFxImage(db.getProfilePhotoOf(foodPageUsername.getText())));
        foodPageVote.setText(db.getVoteRateOf(Integer.parseInt(foodcode1.getText()))+" ");
        if(!(db.getIngridientListOf(Integer.parseInt(foodcode1.getText()))==null))
        foodPageIngredients.setText(db.getIngridientListOf(Integer.parseInt(foodcode1.getText())).toString());
        if(!(foodPageRecipe==null))
        foodPageRecipe.setText(db.getRecipeExplanation(Integer.parseInt(foodcode1.getText())));
        changePage(10);
    }
    @FXML
    protected void image2ButtonClick()
    {
        foodPageFoodID.setText(foodcode2.getText());
        foodPageUsername.setText(db.getChefOf(Integer.parseInt(foodcode2.getText())));
        foodPageName.setText(db.getNameOf(Integer.parseInt(foodcode2.getText())));
        foodPageImage.setImage(convertToFxImage(db.getProfilePhotoOf(foodPageUsername.getText())));
        foodPageVote.setText(db.getVoteRateOf(Integer.parseInt(foodcode2.getText()))+" ");
        if(!(db.getIngridientListOf(Integer.parseInt(foodcode2.getText()))==null))
        foodPageIngredients.setText(db.getIngridientListOf(Integer.parseInt(foodcode2.getText())).toString());
        if(!(foodPageRecipe==null))
        foodPageRecipe.setText(db.getRecipeExplanation(Integer.parseInt(foodcode2.getText())));
        changePage(10);
    }
    @FXML
    protected void image3ButtonClick()
    {
        foodPageFoodID.setText(foodcode3.getText());
        foodPageUsername.setText(db.getChefOf(Integer.parseInt(foodcode3.getText())));
        foodPageName.setText(db.getNameOf(Integer.parseInt(foodcode3.getText())));
        foodPageImage.setImage(convertToFxImage(db.getProfilePhotoOf(foodPageUsername.getText())));
        foodPageVote.setText(db.getVoteRateOf(Integer.parseInt(foodcode3.getText()))+" ");
        if(!(db.getIngridientListOf(Integer.parseInt(foodcode3.getText()))==null))
        foodPageIngredients.setText(db.getIngridientListOf(Integer.parseInt(foodcode3.getText())).toString());
        if(!(foodPageRecipe==null))
        foodPageRecipe.setText(db.getRecipeExplanation(Integer.parseInt(foodcode3.getText())));
        changePage(10);
    }
    @FXML
    protected void image4ButtonClick()
    {
        foodPageFoodID.setText(foodcode4.getText());
        foodPageUsername.setText(db.getChefOf(Integer.parseInt(foodcode4.getText())));
        foodPageName.setText(db.getNameOf(Integer.parseInt(foodcode4.getText())));
        foodPageImage.setImage(convertToFxImage(db.getProfilePhotoOf(foodPageUsername.getText())));
        foodPageVote.setText(db.getVoteRateOf(Integer.parseInt(foodcode4.getText()))+" ");
        if(!(db.getIngridientListOf(Integer.parseInt(foodcode4.getText()))==null))
        foodPageIngredients.setText(db.getIngridientListOf(Integer.parseInt(foodcode4.getText())).toString());
        if(!(foodPageRecipe==null))
        foodPageRecipe.setText(db.getRecipeExplanation(Integer.parseInt(foodcode4.getText())));
        changePage(10);
    }
    @FXML
    protected void foodPageAddToBasketButtonClick()
    {
        if(!(foodPageFoodID==null))
        db.addRecipe(UserMemory.getName(),Integer.parseInt(foodPageFoodID.getText()));
    }
    @FXML
    protected void foodPageLikeButtonClick()
    {
        if(!(foodPageFoodID==null))
            db.likeTo(UserMemory.getName(),Integer.parseInt(foodPageFoodID.getText()));
    }
    @FXML
    protected void foodPageVoteButtonClick()
    {
        if(!(foodPageFoodID==null))
            db.voteTo(UserMemory.getName(),Integer.parseInt(foodPageFoodID.getText()),Integer.parseInt(foodPageVoteNumber.getText()));
    }

    //AL Methods
    public void visitProfile(String username)
    {
        changePage(7);
        otherProfileName.setText(username);
        otherProfileFollowers.setText(db.getFollowerCountOf(username)+" ");
        otherProfileVote.setText(db.getVoteRateOf(username)+"/5");
        otherProfilePersonalInfo.setText(db.getPersonalInfoOf(username));
        otherProfilePhoto.setImage(convertToFxImage(db.getProfilePhotoOf(username)));
    }
    public ArrayList<Integer> sortIngredientSearch(ArrayList<Integer>list,String searchText)
    {
        ArrayList<Integer>out=new ArrayList<>();
        if(list!=null) {
            for (int i = 0; i < list.size(); i++) {
                boolean isValid = true;
                for (int a = 0; a < searchText.length() && searchText.length() <= Integer.toString(list.get(i)).length(); a++) {
                    if (!(searchText.charAt(a) == db.getIngredientName(list.get(i)).charAt(a))) {
                        isValid = false;
                    }
                }
                if (!(searchText.length() <= db.getIngredientName(list.get(i)).length())) {
                    isValid = false;
                }
                if (isValid) {
                    out.add(list.get(i));
                }
            }
        }
        return out;
    }
    public ArrayList<Integer> sortRecipeSearch(ArrayList<Integer>list,String searchText)
    {
        ArrayList<Integer>out=new ArrayList<>();
        if(list!=null) {
            for (int i = 0; i < list.size(); i++) {
                boolean isValid = true;
                for (int a = 0; a < searchText.length() && searchText.length() <= Integer.toString(list.get(i)).length(); a++) {
                    if (!(searchText.charAt(a) == db.getNameOf(list.get(i)).charAt(a))) {
                        isValid = false;
                    }
                }
                if (!(searchText.length() <= db.getNameOf(list.get(i)).length())) {
                    isValid = false;
                }
                if (isValid) {
                    out.add(list.get(i));
                }
            }
        }
        return out;
    }
    public ArrayList<String> sortListSearch(ArrayList<String>list,String searchText)
    {
        ArrayList<String>out=new ArrayList<>();
        if(list!=null) {
            for (int i = 0; i < list.size(); i++) {
                boolean isValid = true;
                for (int a = 0; a < searchText.length() && searchText.length() <= list.get(i).length(); a++) {
                    if (!(searchText.charAt(a) == list.get(i).charAt(a))) {
                        isValid = false;
                    }
                }
                if (!(searchText.length() <= list.get(i).length())) {
                    isValid = false;
                }
                if (isValid) {
                    out.add(list.get(i));
                }
            }
        }
        return out;
    }
    public ArrayList<Integer> sortRecipes(ArrayList<Integer>recipes)
    {
        for(int i=0;i<recipes.size();i++)
        {
            int temp;
            for (int a=i+1;a<recipes.size();a++)
            {
                if(db.getLikeCountOf(recipes.get(i))<db.getLikeCountOf(recipes.get(a)))
                {
                    temp=recipes.get(i);
                    recipes.set(i,recipes.get(a));
                    recipes.set(a,temp);
                }
            }
        }
        return recipes;
    }
    public ArrayList<String> sortChiefs(ArrayList<String>chiefs)
    {
        for(int i=0;i<chiefs.size();i++)
        {
            String temp;
            for (int a=i+1;a<chiefs.size();a++)
            {
                if(db.getFollowerCountOf(chiefs.get(i))*db.getVoteRateOf(chiefs.get(i))<db.getFollowerCountOf(chiefs.get(a))*db.getVoteRateOf(chiefs.get(a)))
                {
                    temp=chiefs.get(i);
                    chiefs.set(i,chiefs.get(a));
                    chiefs.set(a,temp);
                }
            }
        }
        return chiefs;
    }
    public void changePage(int i)
    {
        getCurrentPage().setVisible(false);
        getCurrentPage().setDisable(true);
        currentPage=i;
        getCurrentPage().setVisible(true);
        getCurrentPage().setDisable(false);
    }
    public SplitPane getCurrentPage()
    {
        if(currentPage==4)return myfridgePane;
        if(currentPage==1)return mybasketPane;
        if(currentPage==2)return chiefsPane;
        if(currentPage==3)return recipesPane;
        if(currentPage==5)return settingsPane;
        if(currentPage==6)return logoutPane;
        if(currentPage==7)return otherProfilePane;
        if(currentPage==8)return addRecipePane;
        if(currentPage==9)return addIngredientPane;
        if(currentPage==10)return foodPagePane;
        return usernamePane;
    }
    public void updateProfilePageGUI()
    {
        usernameButton.setText(UserMemory.getName());
        personalInfo.setText(db.getPersonalInfoOf(UserMemory.getName()));
        profilePageName.setText(UserMemory.getName());
        profilePageFollowers.setText(Integer.toString(db.getFollowerCountOf(UserMemory.getName())));
        profilePageVoteRate.setText(db.getVoteRateOf(UserMemory.getName())+"/5");
        profilePhoto.setImage(convertToFxImage(db.getProfilePhotoOf(UserMemory.getName())));
    }
    public void updateChiefsPageGUI(ArrayList<String> list)
    {
        chiefsPageNum.setText("Page: "+(chiefsSection+1));
        ArrayList<String> sorted=sortChiefs(list);
        chiefs1.setText("");chiefs2.setText("");chiefs3.setText("");chiefs4.setText("");chiefs5.setText("");chiefs6.setText("");
        chiefs1.setDisable(true);chiefs2.setDisable(true);chiefs3.setDisable(true);chiefs4.setDisable(true);chiefs5.setDisable(true);chiefs6.setDisable(true);
        rating1.setText("");rating2.setText("");rating3.setText("");rating4.setText("");rating5.setText("");rating6.setText("");
        if(sorted.size()>chiefsSection*6) {
            chiefs1.setText(sorted.get(chiefsSection * 6));
            chiefs1.setDisable(false);
            rating1.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6)));
        }
        if(sorted.size()>chiefsSection*6+1) {
            chiefs2.setText(sorted.get(chiefsSection*6+1));
            chiefs2.setDisable(false);
            rating2.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+1))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+1)));
        }
        if(sorted.size()>chiefsSection*6+2) {
            chiefs3.setText(sorted.get(chiefsSection*6+2));
            chiefs3.setDisable(false);
            rating3.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+2))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+2)));
        }
        if(sorted.size()>chiefsSection*6+3) {
            chiefs4.setText(sorted.get(chiefsSection*6+3));
            chiefs4.setDisable(false);
            rating4.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+3))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+3)));
        }
        if(sorted.size()>chiefsSection*6+4){
            chiefs5.setText(sorted.get(chiefsSection*6+4));
            chiefs5.setDisable(false);
            rating5.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+4))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+4)));
        }
        if(sorted.size()>chiefsSection*6+5){
            chiefs6.setText(sorted.get(chiefsSection*6+5));
            chiefs6.setDisable(false);
            rating6.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+5))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+5)));
        }
    }
    public void updateMyBasketGUI()
    {
        ArrayList<Integer> list=db.getRecipeListOf(UserMemory.getName());
        list=sortRecipeSearch(list,myBasketSearch.getText());
        list=sortRecipes(list);
        myBasketPageNumber.setText("Page: "+ (myBasketSection+1));
        myBasketFoodName1.setText("No Recipes Found");myBasketFoodName2.setText(" ");myBasketFoodName3.setText(" ");myBasketFoodName4.setText(" ");
        myBasketLike1.setText(" ");myBasketLike2.setText(" ");myBasketLike3.setText(" ");myBasketLike4.setText(" ");
        if(!(list==null))
        {
            if(list.size()>myBasketSection*4)
            {
                myBasketFoodName1.setText(db.getNameOf(list.get(myBasketSection*4)));
                myBasketLike1.setText(db.getLikeCountOf(list.get(myBasketSection*4))+" Likes");
                myBasketImage1.setImage(convertToFxImage(db.getImageOf(list.get(myBasketSection*4))));
                foodcode1.setText(Integer.toString(list.get(myBasketSection*4)));
            }
            if(list.size()>myBasketSection*4+1)
            {
                myBasketFoodName2.setText(db.getNameOf(list.get(myBasketSection*4+1)));
                myBasketLike2.setText(db.getLikeCountOf(list.get(myBasketSection*4+1))+" Likes");
                myBasketImage2.setImage(convertToFxImage(db.getImageOf(list.get(myBasketSection*4+1))));
                foodcode1.setText(Integer.toString(list.get(myBasketSection*4+1)));
            }
            if(list.size()>myBasketSection*4+2)
            {
                myBasketFoodName3.setText(db.getNameOf(list.get(myBasketSection*4+2)));
                myBasketLike3.setText(db.getLikeCountOf(list.get(myBasketSection*4+2))+" Likes");
                myBasketImage3.setImage(convertToFxImage(db.getImageOf(list.get(myBasketSection*4+2))));
                foodcode1.setText(Integer.toString(list.get(myBasketSection*4+2)));
            }
            if(list.size()>myBasketSection*4+3)
            {
                myBasketFoodName4.setText(db.getNameOf(list.get(myBasketSection*4+3)));
                myBasketLike4.setText(db.getLikeCountOf(list.get(myBasketSection*4+3))+" Likes");
                myBasketImage4.setImage(convertToFxImage(db.getImageOf(list.get(myBasketSection*4+3))));
                foodcode1.setText(Integer.toString(list.get(myBasketSection*4+3)));
            }
        }

    }
    public void updateRecipesGUI()
    {
        ArrayList<Integer> list=currentRecipeList;
        list=sortRecipeSearch(list,recipesSearch.getText());
        list=sortRecipes(list);
        recipesPageNumber.setText("Page: "+ (recipesSection+1));
        recipesFoodName1.setText("No Recipes Found");recipesFoodName2.setText(" ");recipesFoodName3.setText(" ");recipesFoodName4.setText(" ");
        recipesLike1.setText(" ");recipesLike2.setText(" ");recipesLike3.setText(" ");recipesLike4.setText(" ");
        if(!(list==null))
        {
            if(list.size()>recipesSection*4)
            {
                recipesFoodName1.setText(db.getNameOf(list.get(recipesSection*4)));
                recipesLike1.setText(db.getLikeCountOf(list.get(recipesSection*4))+" Likes");
                recipesImage1.setImage(convertToFxImage(db.getImageOf(list.get(recipesSection*4))));
                foodcode1.setText(Integer.toString(list.get(recipesSection*4)));
            }
            if(list.size()>recipesSection*4+1)
            {
                recipesFoodName2.setText(db.getNameOf(list.get(recipesSection*4+1)));
                recipesLike2.setText(db.getLikeCountOf(list.get(recipesSection*4+1))+" Likes");
                recipesImage2.setImage(convertToFxImage(db.getImageOf(list.get(recipesSection*4+1))));
                foodcode1.setText(Integer.toString(list.get(recipesSection*4+1)));
            }
            if(list.size()>recipesSection*4+2)
            {
                recipesFoodName3.setText(db.getNameOf(list.get(recipesSection*4+2)));
                recipesLike3.setText(db.getLikeCountOf(list.get(recipesSection*4+2))+" Likes");
                recipesImage3.setImage(convertToFxImage(db.getImageOf(list.get(recipesSection*4+2))));
                foodcode1.setText(Integer.toString(list.get(recipesSection*4+2)));
            }
            if(list.size()>recipesSection*4+3)
            {
                recipesFoodName4.setText(db.getNameOf(list.get(recipesSection*4+3)));
                recipesLike4.setText(db.getLikeCountOf(list.get(recipesSection*4+3))+" Likes");
                recipesImage4.setImage(convertToFxImage(db.getImageOf(list.get(recipesSection*4+3))));
                foodcode1.setText(Integer.toString(list.get(recipesSection*4+3)));
            }
        }

    }
    public void updateMyFridgeGUI()
    {
        ArrayList<int[]>list=fridge;
        myfridgePage.setText("Page: "+ (myfridgeSection+1));
        myfridge1.setText("No Ingredients Added");myfridge2.setText(" ");myfridge3.setText(" ");myfridge4.setText(" ");
        if(!(list==null))
        {
            if(list.size()>myfridgeSection*4)
            {
                myfridge1.setText(db.getIngredientName(list.get(myfridgeSection*4)[0]));
            }
            if(list.size()>myfridgeSection*4+1)
            {
                myfridge2.setText(db.getIngredientName(list.get(myfridgeSection*4+1)[0]));
            }
            if(list.size()>myfridgeSection*4+2)
            {
                myfridge3.setText(db.getIngredientName(list.get(myfridgeSection*4+2)[0]));
            }
            if(list.size()>myfridgeSection*4+3)
            {
                myfridge4.setText(db.getIngredientName(list.get(myfridgeSection*4+3)[0]));
            }
        }

    }
    public void updateAddRecipeGUI()
    {
        ArrayList<int[]>list=fridge;
        addRecipeIngredients1.setText("No Ingredients Added");addRecipeIngredients2.setText(" ");addRecipeIngredients3.setText(" ");addRecipeIngredients4.setText(" ");
        if(!(list==null))
        {
            if(list.size()>addRecipeSection*4)
            {
                addRecipeIngredients1.setText(db.getIngredientName(list.get(addRecipeSection*4)[0]));
            }
            if(list.size()>addRecipeSection*4+1)
            {
                addRecipeIngredients2.setText(db.getIngredientName(list.get(addRecipeSection*4+1)[0]));
            }
            if(list.size()>addRecipeSection*4+2)
            {
                addRecipeIngredients3.setText(db.getIngredientName(list.get(addRecipeSection*4+2)[0]));
            }
            if(list.size()>addRecipeSection*4+3)
            {
                addRecipeIngredients4.setText(db.getIngredientName(list.get(addRecipeSection*4+3)[0]));
            }
        }

    }
    public void updateAddIngredientGUI()
    {
        ArrayList<Integer>list=db.getAllIngredients();
        list=sortIngredientSearch(list,addIngredientSearch.getText());
        addIngredient1.setText("No Ingredients");addIngredient2.setText(" ");addIngredient3.setText(" ");addIngredient4.setText(" ");
        if(!(list==null))
        {
            if(list.size()>0)
            {
                addIngredient1.setText(db.getIngredientName(list.get(0)));
                code1.setText(Integer.toString(list.get(0)));
            }
            if(list.size()>1)
            {
                addIngredient2.setText(db.getIngredientName(list.get(1)));
                code2.setText(Integer.toString(list.get(1)));
            }
            if(list.size()>2)
            {
                addIngredient3.setText(db.getIngredientName(list.get(2)));
                code3.setText(Integer.toString(list.get(2)));
            }
            if(list.size()>3)
            {
                addIngredient4.setText(db.getIngredientName(list.get(3)));
                code4.setText(Integer.toString(list.get(3)));
            }
        }

    }
    public static Image convertToFxImage (BufferedImage i) {
        WritableImage wr = null;
        if (i != null) {
            wr = new WritableImage(i.getWidth(), i.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < i.getWidth(); x++) {
                for (int y = 0; y < i.getHeight(); y++) {
                    pw.setArgb(x, y, i.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

}