import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Recipe {
    public int ID;
    public BufferedImage image;
    public String name;
    public String recipeExplanation;

    // ***********
    // READ THIS!!
    // ***********
    /* 
     * This variable will store the ingredients with their IDs, amount and amount kinds
     * 
     * For example if you want to store: 200 mililiter milk, 3 spoon sugar and 2 glasses flour the list should be like: 
     * See AmountKind.txt
     * (These are not real IDs but for the example think that: 
     *  -milk ID: 1021, 
     *  -sugar ID: 1103, 
     *  -flour ID: 1030 )
     * 
     *  Then the list will be:
     *  [
     *      [1021, 200, 7]
     *      [1103, 3, 3]
     *      [1030, 2, 5]
     *  ]
     */
    public ArrayList<int[]> ingridientList;

    // ***********
    // READ THIS!!
    // ***********
    /*
     * This variable will store which sections does recipe applied on
     * 
     * For example lets say our recipe is a choclate cake.
     * Then we can say that it is a desert, cake and a choclate from sections. ( See sections.txt )
     * then it its section list should look like:
     * sections:
     * {
     *      false,
     *      true,
     *      false,
     *      true,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      false,
     *      true,
     *      false,
     *      false,
     * }
     * 
     */
    public boolean[] sections;
    
    //
    // RECIPE CREATE METHODS
    //

    // This method is for ready ingredient recipes.
    public Recipe(int ID, BufferedImage image, String name, String recipeExplanation, ArrayList<int[]> ingridienList, boolean[] sections){
        this.ID = ID;
        this.image = image;
        this.name = name;
        this.recipeExplanation = recipeExplanation;
        this.ingridientList = ingridienList;
        this.sections = sections;
    }

    // This method is for empty ingredient recipes. 
    // And this method has no ID initializers 
    //      because when it is being generated the recipes dont have an ID they get an ID when they are uploaded to the server.
    public Recipe(BufferedImage image, String name, String recipeExplanation){
        this.image = image;
        this.name = name;
        this.recipeExplanation = recipeExplanation;
        this.ingridientList = new ArrayList<int[]>();
        this.sections = new boolean[]{
            false, false, false, false,
            false, false, false, false, 
            false, false, false, false, 
            false, false, false, false, 
            false, false, false, false, 
            false, false, false, false, };
    }

    // This method is for adding a ingridient with seperate ID, amount and amount kind.
    public void addIngredient(int ID, int amount, int amountKind){
        int[] ingredient = new int[]
        {
            ID,
            amount,
            amountKind
        };

        ingridientList.add(ingredient);
    }

    // This method is for adding a ingridient with full info: ID, amount, amount kind
    public void addIngredient(int[] ingredient){
        ingridientList.add(ingredient);
    }

    // This method is for deleting the given ingredient of ID from the ingredient list.
    public void removeIngredient(int ID){
        for(int i = 0; i < ingridientList.size(); i++){
            if( ingridientList.get(i)[0] == ID )
                ingridientList.remove(i);
        }
    }

    // This method is for deleting the given ingredient of ID from the ingredient list.
    public void removeIngredient(float index){
        ingridientList.remove( (int)index );
    }

    // This method is for enabling a section. See sections.txt
    public void enableSection(int index){
        sections[index] = true;
    }

    // This method is for disabling a section. See sections.txt
    public void disableSection(int index){
        sections[index] = false;
    }

    // This method is for disable if the section is enabled, or disable if the section is enabled. See sections.txt
    public void changeSectionEnability(int index){
        sections[index] = !sections[index];
    }

    // To check variables you can use variables themselves.
    // For example if you want to check a section you can use recipe.sections[index].
    // So for most of th eget methods you should call variables

}
