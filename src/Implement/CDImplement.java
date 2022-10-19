package Implement;

import Product.CDProduct;
import Tools.Tools;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static Tools.Tools.sc;
import java.sql.Date;
import java.util.Arrays;

/**
 *
 * @author Walking Bag
 */
public class CDImplement implements ItemBlueprint {

    private final String filename = "item.dat";
    private List<CDProduct> items;
    //Create a new obj which held the data.
    private CDProduct[] list;
    //Our tempo data which will be added to the file.
    private int numOfItem;
    //First this is stored at 0, each CD info will be tempo stored at each array slot
    //Since it was asked to temporary store 2 CD info, it's best to work with array.
    private final int MAX = 2; //Max CD temp list.
    private final String[] collectionList = {"Game", "Movie", "Music"};
    private final String[] cdList = {"Audio", "Video"};

    public CDImplement() {
        //Establish starter values here.
        items = new ArrayList<>();
        list = new CDProduct[MAX];
        numOfItem = 0;
    }

    //What needed to be fixed here:
    /*
        - Might want to change how it looks, that's it.
     */
    @Override
    public void getNewItems() {
        if (numOfItem == 0) {
            System.out.println("[NOTICE]: The list is empty!");
        } else {
            System.out.print("\n=========================================\n");
            System.out.print("                ITEM LIST\n");
            System.out.println("=========================================\n");
            for (int i = 0; i < numOfItem; i++) {
                System.out.println("[ID]: " + list[i].getId() + "\n"
                        + "[Title]: " + list[i].getTitle() + "\n" + "[Collection]: " + list[i].getCollection().toUpperCase() + "\n"
                        + "[CD Type]: " + list[i].getCdType().toUpperCase() + "\n" + "[Price]: " + list[i].getUnitPrice() + " $" + "\n" + "[Publishing Year]: " + list[i].getPublishDate() + "\n");
            }
            System.out.println("=========================================");
        }
    }

    @Override
    public List<CDProduct> getAllItems() {
        items = Tools.loadFromFile(filename, items); //load data
        if (items.isEmpty()) {
            System.out.println("");
        }
        List<CDProduct> list = new ArrayList<>(); //create new list to store and display
        for (CDProduct item : items) {
            list.add(item);
        }
        Collections.sort(items);
        return items;
    }

    @Override
    public CDProduct searchItemByID(int comparedID) {
        for (CDProduct item : list) {
            if (item != null) {
                if (item.getId() == comparedID) {
                    return item;
                }
            }
        }
        return null;
    }

    public boolean checkTempTitle(String comparedTitle) {
        for (CDProduct checkTitle : list) {
            //Since checkTitle gonna encounter null at the first add.
            //We might want to add a condition here, in case it will exit the program.
            //We can add a throw if we want to notify the user.
            if (checkTitle != null) {
                if (checkTitle.getTitle().equalsIgnoreCase(comparedTitle)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkTempID(int comparedID) {
        for (CDProduct checkTemp : list) {
            if (checkTemp != null) {
                if (checkTemp.getId() == comparedID) {
                    return true;
                }
            }
            //There's no reason to repsond if list is null here.
            //It will respond twice since our array limit established is 2.
        }
        return false;
    }

    public boolean checkID(int comparedID) {
        items = Tools.loadFromFile(filename, items);
        for (CDProduct checking : items) {
            if (checking.getId() == comparedID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean seachItemByName(String title) {
        items = Tools.loadFromFile(filename, items);
        for (CDProduct item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CDProduct getItemByID(int ComparedID) {
        items = Tools.loadFromFile(filename, items);
        for (CDProduct item : items) {
            if (item.getId() == ComparedID) {
                return item;
            }
        }
        return null;
    }

    //What needed to be fixed here:
    /*
        - Do not accept blank [Catch null-input on menu]
     */
    @Override
    public List<CDProduct> getItemByName(String title) {
        items = Tools.loadFromFile(filename, items); //load data to items
        if (items.isEmpty()) {
            System.out.println("\n[NOTICE]: Empty file!");
        }
        List<CDProduct> list = new ArrayList<>();
        for (CDProduct item : items) {
            if (item != null && item.getTitle().toLowerCase().contains(title.toLowerCase())) {
                //contains() is case sensitive.
                //Contains will find everything that's equivalence to the input.
                //Even a letter, remind myself that stop abusing the tools to make the same mistakes.
                list.add(item);
            }
        }
        return list;
    }

    @Override
    //What needed to be fixed here:
    /*
        - Reading letter causes ID reading error [resolved]
        - Reading price/collection/CDType must have their prerequisite, cannot enter blank
     */
    public void addItem() {
        int ID,publishingDate;
        String collection, cdType, title;
        float price = 0;
        boolean checkCollection = true, checkDate = true;
        if (numOfItem >= MAX) {
            System.out.println("\n[NOTICE]: The list is full already!");
            System.out.println("[HINT]: Try delete/save your unchanges");
            return;
        }

        System.out.println("Enter New Item Detail: ");
        do {
            ID = Integer.parseInt(Tools.readPattern("Insert ID [Number only!]", "[0-9]+"));
            if (checkID(ID) || checkTempID(ID)) {
                //This will check both memory and file for duplication.
                System.out.println("\n[NOTICE]: Duplicated ID\n");
            } else {
                checkCollection = false;
            }
        } while (checkCollection);
        checkCollection = true;

        do {
            title = Tools.readNonBlank("Insert title");
            if (seachItemByName(title) || checkTempTitle(title)) {
                //This will check both memory and file for duplication.
                System.out.println("\n[NOTICE]: Duplicated title\n");
            } else {
                checkCollection = false;
            }
        } while (checkCollection);
        checkCollection = true;

        do {
            System.out.print("Insert collection [Game/Movie/Music]:");
            collection = sc.nextLine();
            for (int i = 0; i < collectionList.length; ++i) {
                if (collection.equalsIgnoreCase(collectionList[i])) {
                    checkCollection = false;
                }
            }
        } while (checkCollection);
        checkCollection = true;

        do {
            System.out.print("Insert CD Type [Audio/Video]:");
            cdType = sc.nextLine();
            for (int i = 0; i < cdList.length; ++i) {
                if (cdType.equalsIgnoreCase(cdList[i])) {
                    checkCollection = false;
                }
            }
        } while (checkCollection);
        checkCollection = true;

        price = Float.parseFloat(Tools.readPattern("Insert price", "[0-9]+"));

        publishingDate = Integer.parseInt(Tools.readPattern("Insert publishing year", "[0-9]+"));
        /*
        String temp = "";
        do {
            temp = Tools.readPattern("Insert publishing date [dd/MM/yyyy]", "^\\d{2}/\\d{2}/\\d{4}$");
            checkDate = Tools.isItDate(temp, "dd/MM/yyyy");
        } while (!checkDate || temp.isEmpty());
        Date dateString = Tools.parseDate(temp, "dd/MM/yyyy");
        publishDate = Tools.dataToStr(dateString, "dd/MM/yyyy");
        */

        list[numOfItem] = new CDProduct(collection, cdType, title, publishingDate, price, ID);
        numOfItem++;
        //Keep this number runnin
        System.out.println("\n[NOTICE]: New item has been added.");
    }

    @Override
    public void updateItem(CDProduct item) {
        String newCollection, newCDType, newTitle;
        int newDate;
        float newPrice;
        boolean checkCollection = true;
        do {
            System.out.print("Insert new title [Blank to skip]:");
            newTitle = sc.nextLine().trim();
            if (newTitle.isEmpty()) {
                break;
            } else {
                if (seachItemByName(newTitle) || checkTempTitle(newTitle)) {
                    System.out.println("Duplicated title");
                } else {
                    item.setTitle(newTitle);
                    checkCollection = false;
                }
            }
        } while (checkCollection);
        checkCollection = true;

        do {
            System.out.print("Insert new collection [Game/Movie/Music - Blank to skip]:");
            newCollection = sc.nextLine().trim();
            if (newCollection.isEmpty()) {
                break;
            } else {
                for (int i = 0; i < collectionList.length; ++i) {
                    if (newCollection.equalsIgnoreCase(collectionList[i])) {
                        item.setCollection(newCollection);
                        checkCollection = false;
                    }
                }
            }
        } while (checkCollection);
        checkCollection = true;

        do {
            System.out.print("Insert new CD-type [Audio/Video - Blank to skip]:");
            newCDType = sc.nextLine().trim();
            if (newCDType.isEmpty()) {
                break;
            } else {
                for (int i = 0; i < cdList.length; ++i) {
                    if (newCDType.equalsIgnoreCase(cdList[i])) {
                        item.setCdType(newCDType);
                        checkCollection = false;
                    }
                }
            }
        } while (checkCollection);
        checkCollection = true;

        do {
            System.out.print("Insert new price [Blank to skip]:");
            String temp = sc.nextLine().trim();
            if (temp.isEmpty()) {
                break;
            } else {
                try {
                    newPrice = Float.parseFloat(temp);
                    item.setUnitPrice(newPrice);
                    checkCollection = false;
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR]: Wrong Format!");
                    checkCollection = true;
                }
            }
        } while (checkCollection);
        checkCollection = true;

        String tempStr = "";
        do {
            System.out.print("Insert new publishing year [yyyy - Blank to skip]:");
            tempStr = sc.nextLine();
            if (tempStr.isEmpty()) {
                break;
            } else {
                try {
                    newDate = Integer.parseInt(tempStr);
                    item.setPublishDate(newDate);
                    checkCollection = false;
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR]: Wrong Format!");
                    checkCollection = true;
                }
            }
        } while (checkCollection);
        /*if (tempStr.isEmpty()) {
                break;
            }
            Date dateStr = Tools.parseDate(tempStr, "dd/MM/yyyy");
            newDate = Tools.dataToStr(dateStr, "dd/MM/yyyy");
            item.setPublishDate(newDate);
        } while (!checkCollection);*/
        checkCollection = true;
        System.out.println("[NOTICE]: ID " + item.getId() + " is updated!");
    }

    public int seachByCode(CDProduct item) {
        for (int i = 0; i < numOfItem; i++) {
            if (list[i] == item) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void deleteItem(CDProduct item) {
        int pos = seachByCode(item);//Search as array.
        //The add system is array based, so delete should be array based.
        if (pos >= 0 && pos < numOfItem) {
            for (int i = pos; i < (numOfItem - 1); i++) {
                list[i] = list[i + 1];
            }
            numOfItem--;
            System.out.println("[NOTICE]: The ID " + list[pos].getId() + " is deleted!");
        }
    }

    public void deleteAll() {
        //Reset the list after save.
        for (int i = 0; i < 2; i++) {
            Arrays.fill(list, null);
        }
    }

    @Override
//What needed to be fixed here:
/*
        - When saving a file, the number of tempInfo must be reduced and allow the user to continue the program.
     */
    public void saveFile() {
        Tools.saveToFile(list, numOfItem);
        System.out.println("\n[NOTICE]: New item(s) has been saved to the file.");
        numOfItem = 0;
        deleteAll();
    }

}
