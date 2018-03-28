/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheatershangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class CheatersHangman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Map<String,HashSet<String>> map =fileToMap("dictionary.txt");
        guessWork(map,"___","q");
        //HashSet testSet=guessWork(list.get(3),"q");
        
        ArrayList keyList=getKey("_a__d_","e");
        for (int i=0; i<keyList.size(); i++){
            System.out.println(keyList.get(i));
        }
    }
    
    public static void guessWork (Map<String,HashSet<String>> map,String key,String guess){
        Set<String> containsGuess=new HashSet<String>();
        Set<String> notContainsGuess=new HashSet<String>();
        for (String word: map.get(key)){
            if(word.contains(guess)){
                containsGuess.add(word);
            } else {
                notContainsGuess.add(word);
            }
        }
        System.out.println(containsGuess);
        deleteAllExcept(map, key);
        //System.out.println(map);
        
        
        
    }
    
    public static Map<String, HashSet<String>> fileToMap(String dictFilename) throws FileNotFoundException{
        Map<String, HashSet<String>>dict=new HashMap <String, HashSet<String>>();
        try{
            Scanner in = new Scanner(new File(dictFilename));
            while (in.hasNextLine()){
                String word=in.nextLine();
                if (dict.containsKey(blankGuessOfLength(word.length()))){
                    HashSet name=dict.get(blankGuessOfLength(word.length()));
                    name.add(word);
                    //dict.put(word.length(), word);
                } else {
                    Set <String>newSet=new HashSet<String>();
                    newSet.add(word);
                    dict.putIfAbsent(blankGuessOfLength(word.length()), (HashSet<String>) newSet);
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return dict;
    }
    public static String keyMaker (String key, String guess, int start){
        int index=key.indexOf("_", start);
        if (index<0){
            return "";
        } else {
             String guessKey=key.substring(0,index)+guess+key.substring(index+1, key.length());
            return guessKey+"|"+keyMaker(guessKey,guess,index+1)+"|"+keyMaker(key,guess,index+1)+"|";
                   
        }
    }
    public static ArrayList<String> getKey(String key, String guess){
        List <String>keyList=new <String>ArrayList();
        String[] keyArr= keyMaker(key,guess,0).split("[|]",0);
        keyList.add(key);
        for(int i=0;i<keyArr.length; i++){
            if (keyArr[i].length()!=0){
                keyList.add(keyArr[i]);
            } 
        }
        return (ArrayList<String>) keyList;   
    }
    public static String blankGuessOfLength(int n){
        String guess="";
        for (int i=0; i<n; i++){
            guess+="_";
        }
        return guess;
    }
    public static void deleteAllExcept (Map<String,HashSet<String>> map, String key){
        Iterator it = map.entrySet().iterator();
        /*for (String mapkey, map.entrySet())*/
    }
}
