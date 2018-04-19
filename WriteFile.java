package astroid;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Formatter;

public class WriteFile {
    
    public void writeFile(Score input)throws Exception {
    //Score score = new Score(input.getScore()[0],input.getScore()[1],input.getScore()[2],input.getScore()[3],input.getScore()[4]);
    
    String fileName = "data.bin";
    try{
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
        os.writeObject(input);
        os.close();
    }
    catch (FileNotFoundException e){
        e.printStackTrace();
    }
    catch (IOException e){
       e.printStackTrace();
    }
        System.out.println("Done");
  }
}
