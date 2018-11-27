
package testspam;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author NGUYEN
 */
public class Test extends NaiveBayes {
    
   public HashMap<String,Integer> countWord = new HashMap<>();
   
   public double pH = 1; // tan so cua P(Ham/W)
   public double pS = 1; // tan so cua P(Spam/W);
   
    // dem so lan xuat  hien cua tu
    public void count(File file) throws IOException{
       File[] f = file.listFiles();
       for (int i = 0; i < f.length; i++){
            Scanner sc = new Scanner(f[i]);
        
            while(sc.hasNext()){
                String word = sc.next();
                    if (isWord(word)) {
                        if (!countWord.containsKey(word)) {
                            countWord.put(word, 1);                   
                        }
                        else{
                        countWord.put(word,countWord.get(word) + 1);
                        }
                    }
            }
       }
       
       /* for(Map.Entry<String,Integer> entry: countWord.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }*/
      
    }
    
    //tinh tan so P(Spam)
    public String TestFile(){
       
        
        for(Map.Entry<String,Double> entry: HFreq.entrySet()){
            for(Map.Entry<String,Integer> temp: countWord.entrySet()){
                if(entry.getKey().equals(temp.getKey())){
                    pH *= Math.pow(entry.getValue(), temp.getValue());
                }
                    
            }
        }
        
        for(Map.Entry<String,Double> entry: SFreq.entrySet()){
            for(Map.Entry<String,Integer> temp: countWord.entrySet()){
                if(entry.getKey().equals(temp.getKey())){
                    pS *= Math.pow(entry.getValue(), temp.getValue());
                }
                    
            }
        }
        
        pH = pH*PHam;
        pS = pS*PSpam;
        
        //System.out.println(pH + " " + pS);
        
        pH = pH/(pH + pS);
        pS = 1  - pH;
        //System.out.println(pH + " " + pS);
        
        if(pH > pS)
            return "Email is Ham";
        else
            return "Email is Spam";
        

    }
    
}
