package com.example.yenia;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class HelloController {
    //Variables

    //Gui Variables
    @FXML
    private Button usernameButton,mybasketButton,chiefsButton,myfridgeButton,personalInfoEdit,personalInfoDone;
    @FXML
    private SplitPane mybasketPane,usernamePane,chiefsPane,myfridgePane,recipesPane,settingsPane,logoutPane;
    @FXML
    private Pane loginPane,registerPane,mainPane;
    @FXML
    private TextField loginUsername,loginPassword,registerUsername,registerPassword1,registerPassword2,personalInfo,chiefsSearch;
    @FXML
    private Text loginError,registerError,chiefs1,chiefs2,chiefs3,chiefs4,chiefs5,chiefs6,rating1,rating2,rating3,rating4,rating5,rating6,chiefsPageNum;
    @FXML
    private Label profilePageName,profilePageFollowers,profilePageVoteRate;


    //AL Variables
    private DataBase db=new DataBase();
    private int currentPage=0;
    private int chiefsSection=0;
    //Methods

    //Button Methods
    @FXML
    protected void usernameButtonClick()
    {
        changePage(0);
    }
    @FXML
    protected void myfridgeButtonClick()
    {
        changePage(4);
    }
    @FXML
    protected void chiefsButtonClick() {changePage(2);}
    @FXML
    protected void mybasketButtonClick()
    {
        changePage(1);
    }
    @FXML
    protected void recipesButtonClick()
    {
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
        updateChiefsPageGUI(sortListSearch(db.getAllUsers(),chiefsSearch.getText()));
        chiefsSection=0;
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

    //AL Methods
    public ArrayList<String> sortListSearch(ArrayList<String>list,String searchText)
    {
        ArrayList<String>out=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            boolean isValid=true;
            for(int a=0;a<searchText.length();a++)
            {
               if(!(searchText.charAt(a)==list.get(i).charAt(a)))
               {
                   isValid=false;
               }
            }
            if(isValid)
            {
                out.add(list.get(i));
            }
        }
        return out;
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
        return usernamePane;
    }
    public void updateProfilePageGUI()
    {
        usernameButton.setText(UserMemory.getName());
        personalInfo.setText(db.getPersonalInfoOf(UserMemory.getName()));
        profilePageName.setText(UserMemory.getName());
        profilePageFollowers.setText(Integer.toString(db.getFollowerCountOf(UserMemory.getName())));
        profilePageVoteRate.setText(db.getVoteRateOf(UserMemory.getName())+"/5");
    }
    public void updateChiefsPageGUI(ArrayList<String> list)
    {
        chiefsPageNum.setText("Page: "+(chiefsSection+1));
        ArrayList<String> sorted=sortChiefs(list);
        chiefs1.setText(" ");chiefs2.setText(" ");chiefs3.setText(" ");chiefs4.setText(" ");chiefs5.setText(" ");chiefs6.setText(" ");
        rating1.setText(" ");rating2.setText(" ");rating3.setText(" ");rating4.setText(" ");rating5.setText(" ");rating6.setText(" ");
        if(sorted.size()>chiefsSection*6) chiefs1.setText(sorted.get(chiefsSection*6));
        if(sorted.size()>chiefsSection*6+1)chiefs2.setText(sorted.get(chiefsSection*6+1));
        if(sorted.size()>chiefsSection*6+2)chiefs3.setText(sorted.get(chiefsSection*6+2));
        if(sorted.size()>chiefsSection*6+3)chiefs4.setText(sorted.get(chiefsSection*6+3));
        if(sorted.size()>chiefsSection*6+4)chiefs5.setText(sorted.get(chiefsSection*6+4));
        if(sorted.size()>chiefsSection*6+5)chiefs6.setText(sorted.get(chiefsSection*6+5));
        if(sorted.size()>chiefsSection*6) rating1.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6)));
        if(sorted.size()>chiefsSection*6+1)rating2.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+1))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+1)));
        if(sorted.size()>chiefsSection*6+2)rating3.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+2))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+2)));
        if(sorted.size()>chiefsSection*6+3)rating4.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+3))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+3)));
        if(sorted.size()>chiefsSection*6+4)rating5.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+4))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+4)));
        if(sorted.size()>chiefsSection*6+5)rating6.setText("Rating: "+db.getVoteRateOf(sorted.get(chiefsSection*6+5))+"/5 Followers: "+db.getFollowerCountOf(sorted.get(chiefsSection*6+5)));
    }

}