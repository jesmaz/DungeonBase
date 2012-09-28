package com.github.jesmaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dungeonbase extends Activity {
    
    public static final int version = 0;
    
    protected static Class [] classes;
    
    private String campaign, ruleset;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate (savedInstanceState);
        setContentView (R.layout.main);
        
    }
    
    public void viewClasses (View view) {
        
        Intent intent = new Intent (this, Classes.class);
        startActivity (intent);
        
    }
    
    @Override
    public void onPause () {
        
        save ();
        
    }
    
    @Override
    public void onResume () {
        
        load ();
        
    }
    
    private boolean load () {
        
        try {
            
            ObjectInputStream ois = new ObjectInputStream (new FileInputStream 
                    ("ruleset/" + ruleset + ".dat"));
            
            int baseVer = ois.readInt();
            int classVer = ois.readInt();
            int l = ois.readInt();
            classes = new Class [l];
            for (int i=0; i<l; ++i) {
                classes [i] = new Class (ois, classVer);
            }
            
        } catch (StreamCorruptedException ex) {
            
            Logger.getLogger(Dungeonbase.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            
            Logger.getLogger(Dungeonbase.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return true;
        
    }
    
    private boolean save () {
        
        try {
            
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream 
                    ("ruleset/" + ruleset + ".dat"));
            
            oos.writeInt (version);
            oos.writeInt (Class.version);
            oos.writeInt (classes.length);
            for (Class c : classes) {
                c.saveToFile (oos);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(Dungeonbase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        return true;
    }
    
}
