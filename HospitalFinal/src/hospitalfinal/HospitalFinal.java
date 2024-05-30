/*
Samuel Curry, Austin Grahlman, Matthew Costantini
Read/write by sam
5/21/2024
 */
package hospitalfinal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class HospitalFinal {

    public static Scanner reader;
    public static File file;
    public static PrintWriter fileWriter;
    public static String[] fileDump = new String[7];

    public static void main(String[] args) {
        
        loadFile("Test");
        for (int i = 0 ; i<fileDump.length ; i++) {
            System.out.println(fileDump[i]);
        }
        writeTo("Test");

    }

    public static void loadFile(String patient) {

        try {

            file = new File("..\\" + patient + ".txt");
            reader = new Scanner(file);

        } catch (IOException io) {

            System.err.println("Can't find file");
            System.exit(0);

        }

        boolean check = true;

        while (check) {

            try {

                getChunk(reader.nextLine());
                
            } catch (NoSuchElementException nse) {

                check = false;

            }
        }
        
        reader.close();

    }

    public static String getChunk(String original) {

        String chunk;
        int lastPosition = 0;
        int length = original.length() - 1;
        int i = 0;
        boolean check = true;

        do {

            int indexOfSpace = original.indexOf("*", lastPosition);

            if (indexOfSpace != length) {

                chunk = original.substring(lastPosition, indexOfSpace);
                chunk = decrypt(chunk);
                
                fileDump[i] = chunk;
                i++;
                
                lastPosition = (indexOfSpace + 1);

            } else {

                check = false;

                chunk = original.substring(lastPosition, length);
                chunk = decrypt(chunk);
                
                fileDump[i] = chunk;
                i++;
                
            }

        } while (check == true);

        return chunk;

    }
    
    public static void writeTo(String patient) {
        
        String newFile = "";
        
        try {
            fileWriter = new PrintWriter(new FileWriter("..\\"+ patient +".txt"));
        } catch (IOException io) {
            System.err.println("Can't find file");
            System.exit(0);
        }
        
        String userI = JOptionPane.showInputDialog("1. Name\n2. Sex\n3. Age\n4. Height\n5. Weight\n6. Postal code\n7. Adress");
        int user = Integer.parseInt(userI) - 1;
        
        userI = JOptionPane.showInputDialog("Enter the new value");
        userI = encrypt(userI);
        
        fileDump[user] = userI;
        
        for (int i = 0 ; i<fileDump.length ; i++) {
            
            newFile += fileDump[i] + "*";
            
        }
        
        fileWriter.println(newFile);
        
        fileWriter.close();
        
    }
    
    public static String encrypt(String chunk) {
        
        String a = "";
        int length = chunk.length() - 1;
        int temp = 0;
        char b;
  
        for (int i = 0 ; i<=length ; i++) {
            
            b = chunk.charAt((0 + i));
            
            if (b >= 65 && b <= 122) {
            
                if ((b + 15) > 122) {
                
                    temp = Math.abs((b + 15) - 122);
                    b = 96;
                    b += temp;
                    a += b;
                
                } else {
                
                    b += 15;
                    a += b;
                
                }
                
            } else if (b >= 48 && b <= 57) {
                
                if ((b + 7) > 57) {
                
                    temp = Math.abs((b + 7) - 57);
                    b = 47;
                    b += temp;
                    a += b;
                
                } else {
                
                    b += 7;
                    a += b;
                
                }
                
            }
                
        }
        
        return a;
        
    }
    
    public static String decrypt(String chunk) {
        
        String a = "";
        int length = chunk.length() - 1;
        int temp = 0;
        char b;
        
        for (int i = 0 ; i<=length ; i++) {
            
            b = chunk.charAt((0 + i));
            
            if (b >= 65 && b <= 122) {
            
                if ((b - 15) < 97) {
                
                    temp = Math.abs((b - 15) + 97);
                    b = 122;
                    b -= temp;
                    a += b;
                
                } else {
                
                    b -= 15;
                    a += b;
                
                }
            
            } else if (b >= 48 && b <= 57) {
                
                if ((b - 7) < 48) {
                
                    temp = 7  - ((b - 48) + 1);
                    b = 57;
                    b -= temp;
                    a += b;

                } else {
                
                    b -= 7;
                    a += b;
                
                }
                
            }
                
        }
        
        return a;
        
    }

}
