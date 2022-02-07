/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_14062696.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;


/**
 *
 * @author Tong - 14062696
 */
public class Write {
    
//    Writes a JSON file to a path

    public static void JSON(int count, int win, String path) throws IOException {
   
        JSONObject jsonObject = new JSONObject();
                //Inserting key-value pairs into the json object
                jsonObject.put("gameCount", String.valueOf(count));
                jsonObject.put("userWins", String.valueOf(win));
                File f = new File(path); 
                FileWriter filewriter = new FileWriter(f, true);
                
                try {
                        filewriter.write(jsonObject.toString()); // Pass json object.
                        filewriter.flush();
                        filewriter.close();
                     // Pass json object.
                } catch (IOException e) {
                    // TODO Auto-generated catch block

                } finally{
                    filewriter.close();
                } 
        System.out.println("JSON file created: "+jsonObject);
        
    }

//    deletes a JSON file to a path    
    public static void delete(String path) { 
    File myObj = new File(path);
    
    if (myObj.delete()) { 
      System.out.println("Deleted the file: " + myObj.getName());
    } else {
      System.out.println("Failed to delete the file.");
    } 
  } 
    
}
