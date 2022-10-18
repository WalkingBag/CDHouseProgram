package Product;

import java.io.Serializable;

/**
 *
 * @author Walking Bag
 */
public class CDProduct implements Serializable, Comparable {

    //Serializable is a function that turn an object data into bytestream which translate to machines in 8 bits representation.
    //In other words, turn data types into block of hex data.
    String collection,title,cdType,publishDate;
    float unitPrice;
    int id;
    int cdCounting;
    //Count the number of CDs

    public CDProduct(String collection, String cdType, String title,String publishDate, float unitPrice, int id) {
        this.collection = collection;
        this.cdType = cdType;
        this.title = title;
        this.publishDate = publishDate;
        this.unitPrice = unitPrice;
        this.id = id;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    
    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getCdType() {
        return cdType;
    }

    public void setCdType(String cdType) {
        this.cdType = cdType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCdCounting() {
        return cdCounting;
    }

    public void setCdCounting(int cdCounting) {
        this.cdCounting = cdCounting;
    }

    public void print() {
        System.out.println(id + "," + title + "," + collection + "," + cdType + "," + unitPrice + "," + publishDate);
    }

    @Override
    public int compareTo(Object comparedItem) {
        return this.getTitle().compareTo(((CDProduct) comparedItem).getTitle());
        //For Collections.sort based by name, compared to each in the list.

    }
}
