package com.bittle.bot.util;

import com.bittle.internet.pastebin.Pastebin;
import com.bittle.internet.urban.UrbanDictionary;

public class StringUtil {
    private static UrbanDictionary dictionary = new UrbanDictionary();

    public static String getResponse(String msg){
        if(msg.startsWith("/ud ")){
            String lookup = msg.substring(msg.indexOf(" ")).trim();
            if(!lookup.isEmpty()){
                if(dictionary.searchWord(lookup)){
                    return dictionary.getRandomDefinition().getDefinition();
                }else{
                    return "No definition found.";
                }
            }
            else{
                return "No definition found.";
            }
        }   // end of /ud

        else if(msg.startsWith("/p ")){
            // pastebin
            String lookup = msg.substring(msg.indexOf(" ")).trim();
            return Pastebin.getRaw(lookup);
        }
        else{
            return "";
        }
    }

}
