package com.insightdatascience;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

 
class MedianDegree {
	public static final Integer START = 1;
	public static final Integer ACTIVE = 1;
	public static final Integer BLOCKED = 0;
	public static final Integer WINDOW_SIZE = 60000;
	public static Integer counter = 0 ;
    public static void main(String[] args) {
    	Map<String,Integer> actorsList = new HashMap<String, Integer>();
    	Map<Integer,Integer> actorsLinks = new HashMap<Integer, Integer>();
        List<Transaction> paymentsList = new ArrayList<Transaction>();
    	BufferedReader bufferedReader = null;
        JSONParser parser = new JSONParser();
        try {
        	 String readSingleLine;
        	 Date latestDate= new Date(0);
             bufferedReader = new BufferedReader(new FileReader("venmo_input//venmo-trans.txt"));
             PrintStream out = new PrintStream(new FileOutputStream("venmo_output//output.txt"));
             while ((readSingleLine = bufferedReader.readLine()) != null) {
                 Object obj = parser.parse(readSingleLine);
                 JSONObject jsonObject = (JSONObject) obj;
                 String target = (String) jsonObject.get("target");
                 String actor = (String) jsonObject.get("actor");
                 String createdTimeJson = (String) jsonObject.get("created_time");
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                 Date createdTime = sdf.parse(createdTimeJson);
                 if(createdTime.getTime() >= latestDate.getTime()){
                	if(!actorsList.containsKey(actor)){
                		actorsList.put(actor, ++counter);
                		actorsLinks.put(actorsList.get(actor), START);
                	}else{
                		actorsLinks.put(actorsList.get(actor), actorsLinks.get(actorsList.get(actor)) +1);
                	}
                	if(!actorsList.containsKey(target)){
                		actorsList.put(target, ++counter);
                		actorsLinks.put(actorsList.get(target), START);
                	}else{
                		actorsLinks.put(actorsList.get(target), actorsLinks.get(actorsList.get(target)) +1);
                	}
                	
                	Transaction transactionObject = new Transaction();
                	transactionObject.setCreatedTime(createdTime);
                	transactionObject.setActor(actorsList.get(actor));
                	transactionObject.setTarget(actorsList.get(target));
                	transactionObject.setStatus(START);
                	//paymentsList.add(transactionObject);
                	
                	Iterator<Transaction> iterate = paymentsList.iterator();
                    while(iterate.hasNext()){
                    	Transaction transaction = iterate.next();
                    	//System.out.println("1--"+createdTime.getTime() +"---" +transaction.getCreatedTime().getTime());
                    	if(transaction.getStatus() == ACTIVE && (createdTime.getTime() - transaction.getCreatedTime().getTime() > WINDOW_SIZE)){
                    		actorsLinks.put(transaction.getActor(), actorsLinks.get(transaction.getActor()) - 1);
                    		actorsLinks.put(transaction.getTarget(), actorsLinks.get(transaction.getTarget()) - 1);
                    		transaction.setStatus(BLOCKED);
                    		//System.out.println("11");
                    		paymentsList.remove(paymentsList.indexOf(transaction));
                    	}
                    latestDate = createdTime;
                    }
                    
                    /*Iterator<Integer> actorsLinksIterator = actorsLinks.keySet().iterator();
                    while (actorsLinksIterator.hasNext()) {
                   	 Integer iterv = actorsLinksIterator.next();
                   	 System.out.println(iterv);   
                    }*/
                
                 }else if(latestDate.getTime() - createdTime.getTime() <= WINDOW_SIZE){
                	 if(!actorsList.containsKey(actor)){
                 		actorsList.put(actor, ++counter);
                 		actorsLinks.put(actorsList.get(actor), START);
                 	}else{
                 		actorsLinks.put(actorsList.get(actor), actorsLinks.get(actorsList.get(actor)) +1);
                 	}
                 	if(!actorsList.containsKey(target)){
                 		actorsList.put(target, ++counter);
                 		actorsLinks.put(actorsList.get(target), START);
                 	}else{
                 		actorsLinks.put(actorsList.get(target), actorsLinks.get(actorsList.get(target)) +1);
                 	}
                 	Transaction transactionObject = new Transaction();
                 	transactionObject.setCreatedTime(createdTime);
                 	transactionObject.setActor(actorsList.get(actor));
                 	transactionObject.setTarget(actorsList.get(target));
                 	transactionObject.setStatus(START);
                 	//paymentsList.add(transactionObject);
                 	
                 	Iterator<Transaction> iterate = paymentsList.iterator();
                 	while(iterate.hasNext()){
                    	Transaction transaction = iterate.next();
                    	if(transaction.getStatus() == ACTIVE && (latestDate.getTime() - transaction.getCreatedTime().getTime() > WINDOW_SIZE)){
                    		actorsLinks.put(transaction.getActor(), actorsLinks.get(transaction.getActor()) - 1);
                    		actorsLinks.put(transaction.getTarget(), actorsLinks.get(transaction.getTarget()) - 1);
                    		transaction.setStatus(BLOCKED);
                    		//paymentsList.remove(paymentsList.indexOf(transaction));
                    	}
                 	}
                 }else{
                	 Iterator<Transaction> iterate = paymentsList.iterator();
                  	while(iterate.hasNext()){
                     	Transaction transaction = iterate.next();
                     	if(transaction.getStatus() == ACTIVE && (latestDate.getTime() - transaction.getCreatedTime().getTime() > WINDOW_SIZE)){
                     		actorsLinks.put(transaction.getActor(), actorsLinks.get(transaction.getActor()) - 1);
                     		actorsLinks.put(transaction.getTarget(), actorsLinks.get(transaction.getTarget()) - 1);
                     		transaction.setStatus(BLOCKED);
                     		//paymentsList.remove(paymentsList.indexOf(transaction));
                     	}
                  	}
                 }
                 Iterator<Transaction> iterate = paymentsList.iterator();
                 List<Entry<Integer, Integer>> list = new LinkedList<>(actorsLinks.entrySet());
                 Collections.sort(list, new Comparator<Object>() {
                     @SuppressWarnings("unchecked")
                     public int compare(Object o1, Object o2) {
                         return ((Comparable<Integer>) ((Map.Entry<Integer, Integer>) (o1)).getValue()).compareTo(((Map.Entry<Integer, Integer>) (o2)).getValue());
                     }
                 });

                 Map<Integer, Integer> result = new LinkedHashMap<>();
                 for (Iterator<Entry<Integer, Integer>> it = list.iterator(); it.hasNext();) {
                     Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) it.next();
                     result.put(entry.getKey(), entry.getValue());
                 }
                 List<Integer> mapValues2 = new ArrayList<>(result.values());
                 System.setOut(out);
                 if(actorsLinks.size() % 2 == 0){
                	 System.out.println(String.format("%.2f",Double.valueOf(mapValues2.get(actorsLinks.size() / 2 - 1)+ mapValues2.get(actorsLinks.size() / 2))/2));
                	 
                 }
                 else{
                	 System.out.println(String.format("%.2f",Double.valueOf(mapValues2.get(actorsLinks.size()/2))));
                	 
                 }   
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}