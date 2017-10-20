package com.bittle.telegram;

import java.io.File;

/**
 * Created by oscartorres on 6/30/17
 */
public class Directory {

    private File root;

    public Directory() {

        File home = new File(System.getProperty("user.home"));
        root = new File(home.getAbsolutePath()+File.separator+"Bittle_Bot");

        if(!root.exists()){
            if(root.mkdir()){
                System.out.println("Created root directory.");
            }
            else{
                System.out.println("Error creating directory: "+root);
            }
        }

        // create all inner directories
        images_dir();
        games_dir();
        fonts_dir();
        settings_dir();
    }

    public String images_dir() {
        File a = new File(root.getAbsolutePath()+File.separator+"Images");
        if (!a.exists())
            a.mkdir();
        return a.getPath() + File.separator;
    }

    public String games_dir() {
        File a = new File(root.getAbsolutePath()+File.separator+"Games");
        if (!a.exists())
            a.mkdir();
        return a.getPath() + File.separator;
    }

    public String fonts_dir() {
        File a = new File(root.getAbsolutePath()+File.separator+"Fonts");
        if (!a.exists())
            a.mkdir();
        return a.getPath() + File.separator;
    }

    public String settings_dir() {
        File a = new File(root.getAbsolutePath()+File.separator+"Settings");
        if (!a.exists())
            a.mkdir();
        return a.getPath() + File.separator;
    }

    public String global_dir(){
        File a = new File(root.getAbsolutePath()+File.separator+"Global");
        if(!a.exists())
            a.mkdir();
        return a.getPath() + File.separator;
    }

    public String rootDirectory() {
        return root.getAbsolutePath();
    }
}
