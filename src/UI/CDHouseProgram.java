package UI;

import Implement.CDImplement;
import Implement.ItemBlueprint;
import Tools.Tools;
import ui.Menu;
import Product.CDProduct;
import java.util.ArrayList;
import java.util.List;
import ui.Menu;

/**
 *
 * @author Walking Bag
 */
public class CDHouseProgram {

    public static void main(String[] args) {
        String[] options = {"Add CD to the catalog",
            "Search CD by CD name",
            "Display the catalog",
            "Update CD",
            "Save the catalog to file",
            "Print list CDs from file",
            "Others - Quit"
        };
        Menu mnu = new Menu(options);
        int choice = 0, inputID = 0;
        String response, input;
        CDProduct item;
        boolean changed = false;
        ItemBlueprint imp = new CDImplement();
        do {
            int choice2;
            boolean quit = false;
            choice = mnu.getChoice("              CDs MANAGER");
            System.out.println("---------------------------------------");
            switch (choice) {
                case 1:
                    //Not resolved, check [imp.addItem] for more details.
                    imp.addItem();
                    changed = true;
                    break;
                case 2:
                    //Not resolved, check [imp.getItemByName] for more details.
                    System.out.print("Enter name of the item: ");
                    input = Tools.sc.nextLine().trim().toLowerCase();
                    List<CDProduct> list = new ArrayList<>();
                    list = imp.getItemByName(input);
                    if (list.isEmpty()) {
                        System.out.println("[NOTICE]: Invalid CDs");
                    } else {
                        System.out.print("\n=========================================\n");
                        System.out.print("                 CD LIST\n");
                        System.out.println("=========================================\n");
                        for (CDProduct items : list) {
                            System.out.println("[ID]: " + items.getId() + "\n"
                                    + "[Title]: " + items.getTitle() + "\n" + "[Collection]: " + items.getCollection().toUpperCase() + "\n"
                                    + "[CD Type]: " + items.getCdType().toUpperCase() + "\n" + "[Price]: " + items.getUnitPrice() + " $" + "\n" + "[Publishing Year]: " + items.getPublishDate() + "\n");
                        }
                        System.out.println("=========================================");
                    }
                    break;
                case 3:
                    imp.getNewItems();
                    break;
                case 4:
                    do {
                        mnu.list.clear();
                        mnu.list.add("Update a CD");
                        mnu.list.add("Delete a CD");
                        choice2 = mnu.getChoice2("              UPDATE CDs!");
                        String tempString = "";
                        boolean flag = true;
                        switch (choice2) {
                            case 1:
                                do {
                                    System.out.print("Enter ID of the CD:");
                                    tempString = Tools.sc.nextLine();
                                    try {
                                        inputID = Integer.parseInt(tempString);
                                        flag = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("\n[ERROR]: Wrong Format!\n");
                                        flag = true;
                                    }
                                } while (flag);
                                item = imp.searchItemByID(inputID);
                                if (item != null) {
                                    imp.updateItem(item);
                                } else {
                                    System.out.println("[NOTICE]: INVALID CD!");
                                }
                                System.out.print("Do you wish to continue [Y/N]:");
                                response = Tools.sc.nextLine().toUpperCase();
                                if (response.startsWith("N")) {
                                    quit = true;
                                }
                                changed = true;
                                break;
                            case 2:
                                do {
                                    System.out.print("Enter ID of the CD:");
                                    tempString = Tools.sc.nextLine();
                                    try {
                                        inputID = Integer.parseInt(tempString);
                                        flag = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("\n[ERROR]: Wrong Format!\n");
                                        flag = true;
                                    }
                                } while (flag);
                                item = imp.searchItemByID(inputID);
                                if (item != null) {
                                    imp.deleteItem(item);
                                } else {
                                    System.out.println("[NOTICE]: INVALID CD!");
                                }
                                System.out.print("Do you wish to continue [Y/N]:");
                                response = Tools.sc.nextLine().toUpperCase();
                                if (response.startsWith("N")) {
                                    quit = true;
                                }
                                changed = true;
                                break;
                            default:
                                quit = true;
                        }
                    } while (!quit);
                    break;
                case 5:
                    //Not resolved, check [imp.saveFile] for more details.
                    imp.saveFile();
                    changed = false;
                    break;
                case 6:
                    System.out.print("\n=========================================\n");
                    System.out.print("                 CD LIST\n");
                    System.out.println("=========================================\n");
                    for (CDProduct items : imp.getAllItems()) {
                        //imp.getAllItems return list.
                        if (items != null) {
                            System.out.println("[ID]: " + items.getId() + "\n"
                                    + "[Title]: " + items.getTitle() + "\n" + "[Collection]: " + items.getCollection().toUpperCase() + "\n"
                                    + "[CD Type]: " + items.getCdType().toUpperCase() + "\n" + "[Price]: " + items.getUnitPrice() + " $" + "\n" + "[Publishing Year]: " + items.getPublishDate() + "\n");
                        }
                    }
                    System.out.println("=========================================");
                    break;
                default:
                    //This needed to be changed when the user alr made saves, so it doesn't have to ask.
                    //Approach: Needs to manually turn false when saved.
                    if (changed) {
                        System.out.print("There's unsaved changes, save? [Y/N]: ");
                        response = Tools.sc.nextLine().toUpperCase();
                        if (response.startsWith("Y")) {
                            imp.saveFile();
                        }
                    }
            }
        } while (choice > 0 && choice < 7);
    }
}
