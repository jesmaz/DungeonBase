/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jesmaz;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author jesse
 */
public class Class {
    
    public static final int version = 0;
    
    private HitDie hd;
    private String name;
    
    public Class (ObjectInputStream ois, int ver) throws IOException {
        
        switch (ver) {
            case 0:
                name = ois.readUTF();
            default:
                break;
        }
        
    }
    
    public void saveToFile (ObjectOutputStream oof) throws IOException {
        
        oof.writeUTF (name);
        
    }
    
}
