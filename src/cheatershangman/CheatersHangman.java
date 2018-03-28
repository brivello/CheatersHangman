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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class CheatersHangman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Map<Integer,HashSet<String>> list =fileToMap("dictionary.txt");
        //HashSet testSet=guessWork(list.get(3),"q");
        
        ArrayList keyList=getKey("_a__d_","e");
        for (int i=0; i<keyList.size(); i++){
            System.out.println(keyList.get(i));
        }
    }
    
    public static ArrayList<String> guessWork (ArrayList<String>list , String guess){
        Map<Integer[], Set<String>>words = new HashMap<Integer[], Set<String>>();
        Set<String> containsGuess=new HashSet<String>();
        Set<String> notContainsGuess=new HashSet<String>();
        for (String word: list){
            if(word.contains(guess)){
                containsGuess.add(word);
            } else {
                notContainsGuess.add(word);
            }
        }
        
        words.put(new Integer[]{0}, notContainsGuess);
     
        int len = list.get(0).length();

        for (int i = 0; i < len; i++) {
            Set<String> s = new HashSet<String>();
            for (String word : containsGuess) {

            }
        }
        
        return null;
    }
    
    public static Map<Integer, HashSet<String>> fileToMap(String dictFilename) throws FileNotFoundException{
        Map<Integer, HashSet<String>>dict=new HashMap <Integer, HashSet<String>>();
        try{
            Scanner in = new Scanner(new File(dictFilename));
            while (in.hasNextLine()){
                String word=in.nextLine();
                if (dict.containsKey(word.length())){
                    HashSet name=dict.get(word.length());
                    name.add(word);
                    //dict.put(word.length(), word);
                } else {
                    List <String>newList=new ArrayList<String>();
                    newList.add(word);
                    dict.putIfAbsent(word.length(), (HashSet<String>) newList);
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
}
