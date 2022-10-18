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
        String[] options = {"Add item to the catalog",
            "Search item by item name",
            "Display the catalog",
            "Update item",
            "Save the catalog to file",
            "Print list items from file",
            "Others- Quit"
        };
        Menu mnu = new Menu(options);
        int choice = 0, inputID;
        String response, input;
        CDProduct item;
        boolean changed = false;
        ItemBlueprint imp = new CDImplement();
        do {
            int choice2;
            boolean quit = false;
            choice = mnu.getChoice("ITEM MANAGER");
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
                        System.out.println("No Item Found!");
                    } else {
                        System.out.print("\n=========================================\n");
                        System.out.print("                ITEM LIST\n");
                        System.out.println("=========================================\n");
                        for (CDProduct items : list) {
                            System.out.println("[ID]: " + items.getId() + "\n"
                                    + "[Title]: " + items.getTitle() + "\n" + "[Collection]: " + items.getCollection().toUpperCase() + "\n"
                                    + "[CD Type]: " + items.getCdType().toUpperCase() + "\n" + "[Price]: " + items.getUnitPrice() + " $" + "\n" + "[Publishing date]: " + items.getPublishDate() + "\n");
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
                        mnu.list.add("Update a item");
                        mnu.list.add("Delete a item");
                        choice2 = mnu.getChoice2("Update items");
                        switch (choice2) {
                            case 1:
                                inputID = Integer.parseInt(Tools.readNonBlank("Enter ID of the CD"));
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
                                inputID = Integer.parseInt(Tools.readNonBlank("Enter ID of the CD"));
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
                    System.out.print("                ITEM LIST\n");
                    System.out.println("=========================================\n");
                    for (CDProduct items : imp.getAllItems()) {
                        //imp.getAllItems return list.
                        if (items != null) {
                            System.out.println("[ID]: " + items.getId() + "\n"
                                    + "[Title]: " + items.getTitle() + "\n" + "[Collection]: " + items.getCollection().toUpperCase() + "\n"
                                    + "[CD Type]: " + items.getCdType().toUpperCase() + "\n" + "[Price]: " + items.getUnitPrice() + " $" + "\n" + "[Publishing date]: " + items.getPublishDate() + "\n");
                        }
                    }
                    System.out.println("=========================================");
                    break;
                default:
                    //This needed to be changed when the user alr made saves, so it doesn't have to ask.
                    //Approach: Needs to manually turn false when saved.
                    if (changed) {
                        System.out.print("There's unsaved changes [Y/N]: ");
                        response = Tools.sc.nextLine().toUpperCase();
                        if (response.startsWith("Y")) {
                            imp.saveFile();
                        }
                    }
            }
        } while (choice > 0 && choice < 7);
    }
}
