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
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
public class CheatersHangman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Map<String,HashSet<String>> map =fileToMap("dictionary.txt");
        guessWork(map,"_ _ _ _","e");
        System.out.println(map);
        guessWork(map, "a");
        System.out.println(map);
        guessWork(map, "e");
        System.out.println(map);
        guessWork(map, "i");
        System.out.println(map);
        guessWork(map, "o");
        System.out.println(map);
    }
    
    
    public static void guessWork (Map<String,HashSet<String>> map,String guess){
        guessWork(map, map.entrySet().iterator().next().getKey().toString(), guess);
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
        deleteAllExcept(map, "");
        ArrayList possibleKeys=getKey(key,guess);
        for (int j=1; j<possibleKeys.size(); j++){
            map.put(possibleKeys.get(j).toString(),new <String>HashSet());
        }
        map.put(possibleKeys.get(0).toString(), (HashSet<String>) notContainsGuess);
        for (String current: containsGuess){
            //System.out.println(current);
            String keyPattern="";
            int currentIndex=0;
            for(int i=0; i<key.length();i++){
                if (i%2==0) {
                    if (current.charAt(currentIndex)== guess.charAt(0)){
                        keyPattern+=guess;
                    } else {
                        keyPattern+=key.charAt(i);
                    }
                    currentIndex++;
                } else {
                    keyPattern+=" ";
                }
            }
            //System.out.println(keyPattern);
            map.get(keyPattern).add(current);
        }
        deleteAllExcept(map,largestKey(map));
    }
    
    public static String largestKey (Map<String,HashSet<String>> map){
        String largestKey="";
        int size=0;
        Iterator it = map.entrySet().iterator();
            while (it.hasNext()){
                Entry item = (Entry) it.next();
                HashSet tempSet=(HashSet) item.getValue();
                int tempSize=tempSet.size();
                if (tempSize>size){
                    largestKey=item.getKey().toString();
                    size=tempSize;
                }
            }
        return largestKey;
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
            if (i==n-1){
                guess+="_";
            } else {
                guess+="_ ";
            }
        }
        return guess;
    }
    public static void deleteAllExcept (Map<String,HashSet<String>> map, String key){
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()){
            Entry item = (Entry) it.next();
            if(!item.getKey().equals(key)){
                it.remove();
            }
        }
    }
}
