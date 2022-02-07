package nz.ac.massey.cs.webtech.s_14062696.server;


import java.io.FileReader; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 

 
public class Read { 
    
//   Reads a JSON file from a path.
    
    public static String Json(int option, String path){
        String selected = "";
        try{
            
            FileReader reader = new FileReader(path);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            if (option == 1){
                String gameCount = (String) jsonObject.get("gameCount");
                selected = gameCount;
            }
            if (option == 2){
                String userWins = (String) jsonObject.get("userWins");
                selected = userWins;
            }
            reader.close();
        }catch(Exception ex){
            
        }

    return selected;
}
 
} 
 
