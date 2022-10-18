package Implement;

import java.util.List;
import Product.CDProduct;

/**
 *
 * @author Walking Bag
 */
public interface ItemBlueprint {
    void getNewItems();
    List<CDProduct> getAllItems();
    CDProduct searchItemByID(int ComparedID);
    boolean seachItemByName(String title);
    CDProduct getItemByID(int ComparedID);
    List<CDProduct> getItemByName(String title);
    void addItem();
    void updateItem(CDProduct item);
    void deleteItem(CDProduct item);
    void saveFile();
    
}
