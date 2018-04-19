package astroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class ReadFile {
    
    public Score readFile() throws Exception{
    Score s = new Score();            
    String fileName = "data.bin";
    try{
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
        s = (Score) is.readObject();
        System.out.println("Success");
        is.close();
    }
    catch (FileNotFoundException e){
        e.printStackTrace();
    }
    catch (IOException e){
       e.printStackTrace();
    }
    return s;
  }
    }
