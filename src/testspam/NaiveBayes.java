
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
public class NaiveBayes {
    
   public HashMap<String,Integer> HamWordCount = new  HashMap<>();
   public HashMap<String,Double> HFreq = new HashMap<>();
   
   public HashMap<String,Integer> SpamWordCount = new HashMap<>();
   public HashMap<String,Double> SFreq = new HashMap<>();
   
   public HashMap<String,Integer> Word = new HashMap<>(); 
 
   
   public double PSpam; // tan so xuat hien cua moi email spam trong tap training
   public double PHam; // tan so xuat hien cua moi email ham trong tap training 
   public int sumWord = 0; // tong so tu
   
   // kiem tra tu
    public boolean isWord(String str){
        String pattern = "^[a-zA-Z]*$";
        return str.matches(pattern);
    }
    
    // tinh tan so P(Spam),P(Ham)
    public void classFrequency(File file1,File file2)throws IOException{
        File[] filesInDir1 = file1.listFiles();
        File[] filesInDir2 = file2.listFiles();
        int number1 = filesInDir1.length;
        int number2 = filesInDir2.length;
        int sum = number1 + number2;
        PSpam = (double) number1/(double)sum;
        PHam = (double) number2/(double)sum;
       // System.out.println("P(Ham)= " + PHam+ "  P(Spam)= "+ PSpam);
        
    }
    
    // tong so tu
    public void sumWord(File file)throws IOException{
        File[] filesInDir = file.listFiles();
        System.out.println(filesInDir.length);
        HashMap<String, Integer> temp = new HashMap<>();
      //  HashMap<String,Integer> temp2 = new HashMap<>();
        for (int i = 0; i < filesInDir.length; i++){
            // thu nhap danh sach cac tu cu the va dat vao ban do tam thoi
            Scanner scanner = new Scanner(filesInDir[i]);
            while(scanner.hasNext()){
                String word = scanner.next();
                if (isWord(word)) {
                    if (!temp.containsKey(word)) {
                        temp.put(word, 1);
                    }
         
                }
           }  
        }
        for(Map.Entry<String,Integer> entry: temp.entrySet()){
            if (Word.containsKey(entry.getKey())){
            int oldCount = Word.get(entry.getKey());
                Word.put(entry.getKey(), oldCount + 1);
            }else{
                Word.put(entry.getKey(), 1);
            }
        }
        
        for(Map.Entry<String,Integer> entry: Word.entrySet()){
            sumWord += 1;
        }
        
       // System.out.println("Sum Word: " + sumWord);
       
    }
    
   // tinh tan so P(W/Ham)
    public void trainHamFrequency(File file) throws IOException{
        File[] filesInDir = file.listFiles();
        System.out.println("Training...");
        System.out.println(filesInDir.length);
        int ham = 0;
       // HashMap<String,Integer> temp = new HashMap<>();
        for(Map.Entry<String,Integer> entry: Word.entrySet()){
            HamWordCount.put(entry.getKey(), 1);
        }
        for (int i = 0; i < filesInDir.length; i++){
        
            // thu nhap danh sach cac tu cu the va dat vao ban do tam thoi
            Scanner scanner = new Scanner(filesInDir[i]);
            while(scanner.hasNext()){
                String word = scanner.next();
                if (isWord(word)) {
                    if (!HamWordCount.containsKey(word)) {
                        HamWordCount.put(word, 1);                   
                    }
                    else{
                        HamWordCount.put(word,HamWordCount.get(word) + 1);
                    }
               }
           }
        
        } 
        
       /* for(Map.Entry<String,Integer> entry: HamWordCount.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }*/
        
        // tinh tong so lan xuat hien cua tu
        for(Map.Entry<String,Integer> entry: HamWordCount.entrySet()){
            ham += entry.getValue();
        }
       // System.out.println("Sum: " + ham);
        // tinh tan so word/ham
        // luu vao HFreq    
        for(Map.Entry<String,Integer> entry: HamWordCount.entrySet()){
            double pWH = (double)(entry.getValue())/(double)(ham);
            HFreq.put(entry.getKey(),pWH);
        }
        /* for(Map.Entry<String,Double> entry: HFreq.entrySet()){
             System.out.println(entry.getKey() + " " + entry.getValue());
         }*/
        
 
   } 
    
    // xu ly thu mua spam va thu muc ham
    public void processTrain(File file){
        if (file.isDirectory()){
            if (file.getName().equals("ham")){
                try {
                    trainHamFrequency(file);
                }catch (IOException e){
                    e.printStackTrace();
                }
                System.out.println("DONE Folder: ../ham");
            }else if(file.getName().equals("spam")) {
                try {
                    trainSpamFrequency(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("DONE Folder: ../spam");
            }else {
                File[] filesInDir = file.listFiles();
                for (int i = 0; i < filesInDir.length; i++){
                    processTrain(filesInDir[i]);
                }
            }
        }
    }
    
   // tinh tan so P(W.Spam)
    public void trainSpamFrequency(File file) throws IOException{
        File[] filesInDir = file.listFiles();
        System.out.println("Training...");
        System.out.println(filesInDir.length);
        int ham = 0;
       // HashMap<String,Integer> temp = new HashMap<>();
        for(Map.Entry<String,Integer> entry: Word.entrySet()){
            SpamWordCount.put(entry.getKey(), 1);
        }
        for (int i = 0; i < filesInDir.length; i++){
        
            // thu nhap danh sach cac tu cu the va dat vao ban do tam thoi
            Scanner scanner = new Scanner(filesInDir[i]);
            while(scanner.hasNext()){
                String word = scanner.next();
                if (isWord(word)) {
                    if (!SpamWordCount.containsKey(word)) {
                        SpamWordCount.put(word, 1);                   
                    }
                    else{
                        SpamWordCount.put(word,SpamWordCount.get(word) + 1);
                    }
               }
           }
        
        } 
        
       /* for(Map.Entry<String,Integer> entry: SpamWordCount.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }*/
        
        // tinh tong so lan xuat hien cua tu
        for(Map.Entry<String,Integer> entry: SpamWordCount.entrySet()){
            ham += entry.getValue();
        }
        //System.out.println("Sum: " + ham);
        // tinh tan so word/spam
        // luu vao SFreq    
        for(Map.Entry<String,Integer> entry: SpamWordCount.entrySet()){
            double pWH = (double)(entry.getValue())/(double)(ham);
            SFreq.put(entry.getKey(),pWH);
        }
       /*  for(Map.Entry<String,Double> entry: SFreq.entrySet()){
             System.out.println(entry.getKey() + " " + entry.getValue());
         }
        */
          
    }
        
}  
   
   
   
