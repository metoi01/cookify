package com.example.yenia;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

public class HelloController {

    @FXML
    private Button usernameButton,mybasketButton,chiefsButton,myfridgeButton;
    @FXML
    private SplitPane mybasketPane,usernamePane,chiefsPane,myfridgePane;

    private int currentPage=3;
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
    protected void chiefsButtonClick()
    {
        changePage(2);
    }
    @FXML
    protected void mybasketButtonClick()
    {
        changePage(1);
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
        return usernamePane;
    }

}