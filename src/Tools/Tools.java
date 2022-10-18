package Tools;

import Product.CDProduct;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Walking Bag
 */
public class Tools {

    /*
 * Class MyTool: class for validating input and inputing data using a condition
 * Date format: y: year, M: month in year, d: day in month
 *              Separators: - / : but they are not mixed
 *              Example: yyyy/MM/dd   dd:MM:yyyy   MM/dd/yyyy:
 * Regular expression for pattern
 *    Phone no 10 or 11 digits: "\\d{10}|\\d{11}"
 *    Phone no 10 to 11 digits: "\\d{10,11}"
 *    ID format X0000 : "X\\d{4}"
 *    ID format X0000 or M000: "X\\d{4}|M\\d{3}"
     */
    public static final String filename = "item.dat";
    public static final Scanner sc = new Scanner(System.in);

    //Checking whether str matches a pattern or not
    //Use the method String.matches (regEx) - IMPLEMENT IT
    public static boolean validStr(String str, String regEx) {
        boolean input = false;
        input = str.matches(regEx);
        return input;
    }

    /*Checking a password with minLen in which it contains at least a character,
      a number and 1 specific character
    .* : there may be one or more any character
       \\d : digit   \\W : [^a-zA-Z0-9] : it is not a character and not a digit
     */
    public static boolean validPassword(String str, int minLen) {
        if (str.length() < minLen) {
            return false;
        }
        return str.matches(".*[a-zA-Z]+.*")
                && //AT LEAST 1 CHARACTER
                str.matches(".*[\\d]+.*")
                && //AT LEAST 1 DIGIT
                str.matches(".*[\\W]+.*"); //AT LEAST 1 SPECIAL CHAR
    }

    //Date format: yyyy/MM/dd, MM/dd/yyyy, dd/MM/yyyy, ...
    //yyyy/dd/MM 2000/30/02 -> 2000/01/03 automatically
    //Date string will be changed to a valid date value automatically
    public static Date parseDate(String dateStr, String dateFormat) {
        SimpleDateFormat dF = (SimpleDateFormat) SimpleDateFormat.getInstance();
        dF.applyPattern(dateFormat);
        try {
            long t = dF.parse(dateStr).getTime();
            return new Date(t);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return null;
    }

    public static boolean isItDate(String dateStr, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);
        try {
            format.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String dataToStr(Date date, String dateFormat) {
        SimpleDateFormat dF = (SimpleDateFormat) SimpleDateFormat.getInstance();
        dF.applyPattern(dateFormat); //Apply format
        return dF.format(date); //Turns date into String
    }

    //Use the class SimpleDateFormat
    //Use the method applyPatter(str) to apply a specific format
    //Use the method format(date) to convert date -> String
    //IMPLEMENT IT
//    public static String dataToStr(Date date, String dateFormat) {
//        return null;
//    }
    //Convert bool string to boolean
    public static boolean parseBool(String boolStr) {
        char c = boolStr.trim().toUpperCase().charAt(0);
        return (c == '1' || c == 'Y' || c == 'T');
    }

    //Tools for inputting data
    public static String readNonBlank(String message) {
        String input = "";
        do {
            System.out.print(message + ": ");
            input = sc.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    public static String readPattern(String message, String pattern) {
        String input = "";
        boolean valid;
        do {
            System.out.print(message + ": ");
            input = sc.nextLine().trim();
            valid = validStr(input, pattern);
        } while (!valid);
        return input;
    }

    public static boolean readBool(String message) {
        String input;
        System.out.print(message + "[1/0-Y/N-T/F]: ");
        input = sc.nextLine().trim();
        if (input.isEmpty()) {
            return false;
        }
        char c = Character.toUpperCase(input.charAt(0));
        return (c == '1' || c == 'Y' || c == 'T');
    }

    public static List<CDProduct> loadFromFile(String fName, List<CDProduct> list) {
        if (list.size() > 0) {
            list.clear();
        }
        //Clean list each time calling load
        FileInputStream fi = null;
        ObjectInputStream fo = null;
        try {
            fi = new FileInputStream(fName);
            fo = new ObjectInputStream(fi);
            CDProduct obj; //Create a new obj based on data type object CDProduct.
            while (true) {
                obj = (CDProduct) (fo.readObject());
                list.add(obj);
                //Run through the whole list and add each elements into the list.
            }
        } catch (Exception e) {
//            System.out.println(e);
        } finally {
            if (fi != null) {
                try {
                    fi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /*Method for reading lines from text file
    Create an array list, named as list
    Open file
    while (still read succesfully a line in the file) {
         trim the line;
         if line is not empty, add line to the list
    }
    Close file
    return list;
    IMPLEMENT IT
     */
    public static List<String> readLinesFromFile(String filename) {
        List<String> list = new ArrayList<String>();
        try {
            FileInputStream fi = new FileInputStream(filename); // read()
            ObjectInputStream fo = new ObjectInputStream(fi); // read Object()
            String b;
            while ((b = (String) (fo.readObject())) != null) {
                //Type casting the read objects into strings.
                list.add(b);
            }
            fo.close();
            fi.close();
        } catch (EOFException e) {
            //end of file reached, do nothing
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    //check if the file has the object saved in or not
    public static boolean hasObject(String filePath) {
        FileInputStream fi;
        //Since we're working with byte stream instead of non-converted data type.
        //We must turn all file to reading stream instead.
        boolean check = true;
        try {
            fi = new FileInputStream(filePath);
            ObjectInputStream fo = new ObjectInputStream(fi);
            if (fo.readObject() == null) {
                check = false;
            }
            fo.close();
        } catch (FileNotFoundException e) {
            check = false;
        } catch (IOException e) {
            check = false;
        } catch (ClassNotFoundException e) {
            check = false;
            e.printStackTrace();
        }
        return check;
    }

    public static void saveToFile(CDProduct[] list, int numOfItem) {
        FileOutputStream fi = null;
        ObjectOutputStream fo = null;
        try {
            if (!hasObject(filename)) {
                fi = new FileOutputStream(filename);
                fo = new ObjectOutputStream(fi);
            } else {
                fi = new FileOutputStream(filename, true);
                fo = new ObjectOutputStream(fi) {
                    @Override
                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };
            }
            for (int i = 0; i < numOfItem; i++) {
                fo.writeObject(list[i]);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (fi != null) {
                try {
                    fi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //PrintStackTrace is for tracing the previous logged.
                }
            }
        }
    }

    /*Method for writing a list to a text file line-by-line
    Open the file for writing
    For each object in the list, write the object to file
    Close the file
    IMPLEMENT IT
     */
    public static void writeFile(String filename, List list) {
        try {
            File f = new File(filename);
            FileWriter fw = new FileWriter(f); // write()
            PrintWriter pw = new PrintWriter(fw); // println()
            for (Object object : list) {
                pw.print(object);
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
    //Test- It is optional
    public static void main(String[] args) {
        
        //Phone: 9 or 11 digits - OK
        System.out.println("Tests with phone numbers: ");
        System.out.println(validStr("012345678", "\\d{9}|\\d{11}"));
        System.out.println(validStr("01234567891", "\\d{9}|\\d{11}"));
        System.out.println(validStr("12345678", "\\d{9}|\\d{11}"));
        
        //Test password - OK
        System.out.println(validPassword("qwerty", 8)); //false
        System.out.println(validPassword("qwertyABC", 8)); //false
        System.out.println(validPassword("12345678", 8)); //false
        System.out.println(validPassword("qbc123456", 8)); //false
        System.out.println(validPassword("qbc@123456", 8)); //true
        
        //ID format D000 -> OK
        System.out.println("Tests with IDs: ");
        System.out.println(validStr("A0001", "D\\d{3}"));
        System.out.println(validStr("10001", "D\\d{3}"));
        System.out.println(validStr("D0001", "D\\d{3}"));
        System.out.println(validStr("D101", "D\\d{3}"));
        
        //Test date format -> OK
        Date d = parseDate("2022:12:07", "yyyy:MM:dd");
        System.out.println(d);
//        System.out.println(dataToStr(d, "dd/MM/yyyy")); //test OK
        d = parseDate("12/07/2022", "MM/dd/yyyy");
        System.out.println(d);
        d = parseDate("2022/07/12", "yyyy/dd/MM");
        System.out.println(d);
        d = parseDate("2000/29/02", "yyyy/dd/MM");
        System.out.println(d);
        d = parseDate("2000/30/02", "yyyy/dd/MM");
        System.out.println(d);
        d = parseDate("2000/40/16", "yyyy/dd/MM");
        System.out.println(d);
        
        //Test input data -> OK
        String input = readNonBlank("Input a non-blank string");
        System.out.println(input); //OK
        input = readPattern("Phone 9/11 digits", "\\d{9}|\\d{11}");
        System.out.println(input); //OK
        input = readPattern("ID- format X00000", "X\\d{5}");
        System.out.println(input); //OK
        boolean b = readBool("Input boolean");
        System.out.println(b); //OK
        
    } 
     */
} //Class MyTool

