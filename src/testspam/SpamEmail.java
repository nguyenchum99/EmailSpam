
package testspam;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author NGUYEN
 */
public class SpamEmail {

    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
      // NaiveBayes nb = new NaiveBayes();
       File f1 = new File("spam");
       File f2 = new File("ham");
       File f = new File("sumWord");
       File f4 = new File("train");
       File f3 = new File("test");
       //Test t = new Test();
       Test t = new Test();
       t.classFrequency(f1, f2);
       t.sumWord(f);
       t.processTrain(f4);
       t.count(f3);
       System.out.println(t.TestFile());
       
    }
    
}
