/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import Tools.Tools;

public class Menu extends Vector<String> {

    public List<String> list = new ArrayList<String>();

    ;
   
    public Menu() {
        super();
    }

    public Menu(String[] items) {
        super();
        for (String item : items) {
            this.add(item);
        }
    }

//    public int getChoice(){
//        int result=0;
//        System.out.println("____________________________");
//        System.out.print("Select 1 ... 7: ");
//        Scanner sc = new Scanner(System.in);
//        result = Integer.parseInt(sc.nextLine()); // get user choice
//        return result;
//    }
    public int getChoice(String title) {
        int input = 0;
        System.out.println("\n---------------------------------------");
        System.out.println(title);
        System.out.println("---------------------------------------");
        for (int i = 0; i < this.size() - 1; i++) {
            System.out.println((i + 1) + "- " + this.get(i));
        }
        System.out.println(this.get(this.size() - 1));
        System.out.println("---------------------------------------");
        System.out.print("Choose 1 ... " + (this.size() - 1) + ": ");
//        Scanner sc = new Scanner(System.in);
        try {
            input = Integer.parseInt(Tools.sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[NOTICE]: Goodbye!");
        }
        return input;
    }

    public int getChoice2(String title) {
        int input = 0;
//        System.out.println("\n---------------------------------------");
        System.out.println("\n---------------------------------------");
        System.out.println("" + title);
        System.out.println("---------------------------------------\n");
//        System.out.println("---------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + " - " + list.get(i));
        }
        System.out.println("Others - EXIT!\n");
        System.out.print("Choose your option [1-2]:");
//        Scanner sc = new Scanner(System.in);
        try {
            input = Integer.parseInt(Tools.sc.nextLine());
        } catch (NumberFormatException e) {
        }
        return input;
    }
}
