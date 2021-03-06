package com.bittle.telegram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Created by oscartorres on 6/27/17.
 */

// allows user to run commands such as /py,
// careful with this
public class ScriptRunner {
     public static String python(String prg) {
         if (prg.contains("ping")) {
             return "No pinging aloud.";
         }
         else {
             try {

                 BufferedWriter out = new BufferedWriter(new FileWriter("test1.py"));
                 out.write(prg);
                 out.close();

                 ProcessBuilder pb = new ProcessBuilder("python", "test1.py");
                 Process p = pb.start();

                 BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                 StringBuilder output = new StringBuilder();
                 String line;
                 while ((line = in.readLine()) != null) {
                     output.append(line);
                 }
                 if(output.indexOf("oscar")!=-1){
                     return "Personal information.";
                 }
                 else
                    return output.toString();
             } catch (Exception e) {
                 return "ERROR IN PYTHON CODE:\n" + e.toString();
             }
         }

     }
}
