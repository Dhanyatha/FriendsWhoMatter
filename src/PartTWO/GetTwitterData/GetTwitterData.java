package PartTWO.GetTwitterData;

import PartTWO.Authenticate.*;
import PartTWO.OAuth.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.json.JSONArray;
import org.json.JSONObject;

import au.com.bytecode.opencsv.CSVReader;


public class GetTwitterData
{
	static final String FILE_PATH="C:\\Users\\Tejas\\workspace\\ProjectMickey\\data\\RUN3\\";
	static final Long MAX_TWEETS = new Long(3200);
	static final Long HIGH = new Long(100);
	static final Long LOW = new Long(10);
    BufferedWriter OutFileWriter;
    OAuthConsumer Consumer;
    OAuthToken OAuthTokens;
    //public static LinkedList<Node> L = new LinkedList<Node>();
    //public static LinkedList<Post> P = new LinkedList<Post>();
    static FileWriter realFriends;
    static File id_track=new File(FILE_PATH,"realFriends.csv");
    static FileWriter posttracker;
    static File post_track=new File(FILE_PATH,"postTracker.csv");
    

    public GetTwitterData(OAuthConsumer oc,OAuthToken ot) {
    	try {
			this.Consumer = new DefaultOAuthConsumer(oc.getConsumerKey(),oc.getConsumerSecret());
			this.Consumer.setTokenWithSecret(ot.getAccessToken(),ot.getAccessSecret());
			OAuthToken tokensecret = new OAuthToken(ot.getAccessToken(),ot.getAccessSecret());
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
    public OAuthConsumer GetConsumer()
    {
        OAuthConsumer consumer = new DefaultOAuthConsumer(PartTWO.utils.OAuthUtils.CONSUMER_KEY1,PartTWO.utils.OAuthUtils.CONSUMER_SECRET1);
        consumer.setTokenWithSecret(OAuthTokens.getAccessToken(),OAuthTokens.getAccessSecret());
        return consumer;
    }
    public void LoadTwitterToken()
    {
        OAuthTokens = OAuthExample.DEBUGUserAccessSecret();
    }
*/
    public String fetchData(String last_mapped)
    {
        //RESTApiExample rae = new RESTApiExample();
        //rae.LoadTwitterToken();
        //rae.Consumer = rae.GetConsumer();
        /**** for every id_str read from the file do the following ***/   
        PrintStream out=null;
        String mapping_number=null;
        String prev_num=null;
        
        //New fetch
        Long id = (Long.parseLong(last_mapped));
    	Integer map= 5000;
        
        try
        {	 
        	/*out = new PrintStream(new FileOutputStream(FILE_PATH+"console1.txt", true));
        	System.setOut(out);
        	CSVReader reader = new CSVReader(new FileReader(FILE_PATH+"mapping.csv"));
        	String [] nextLine;
        	Integer count=0;
        	while ((nextLine = reader.readNext()) != null) {
        	
        		if(last_mapped.equals(nextLine[0])) break;
        	}
        	while ((nextLine = reader.readNext()) != null){
        		prev_num=mapping_number;
        		mapping_number=nextLine[0];
        		String id_str=nextLine[1];
        		int status = tweetParser(id_str,mapping_number);
        		if(status == 429) {
        			mapping_number=prev_num;
        			break;
        		}
        		
        	}
        	
        	reader.close();
        	return mapping_number;*/
        	
        	 //New fetch
        	while (true) {
        		int status = tweetParser(id.toString(),map.toString());
        		if(status == 429) {
        			break;
        		}
        		Random r = new Random();
        		Long R = r.nextLong(HIGH-LOW) + LOW;
        		id=id+R;
        		
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
      	  //out.close();
      	  System.out.println("Web crawling ended for current Thread");
        }
        //return mapping_number;
        //New fetch
        return id.toString();
        
   }
     
    //public void countMentions(String mapping_number,String user_id,String mentioned_user) 
	public static void countMentions(LinkedList<Node> RF,Node item)
    {
        try {
			
			//Node item=new Node(mapping_number,user_id,mentioned_user,1);
			//System.out.println("contains item? "+ L.contains(item));
			if(RF.contains(item))
			{
				int indexOfItem=RF.indexOf(item);
				if (item.mentioned_node.equals("-1")) 
					return;
				else
				RF.get(indexOfItem).count++;
			}
			else 
			{
				if(item.seed_node.equals(item.mentioned_node))
				{
					System.out.println("Self Obsession");
				}
				else
					RF.addLast(item);
			}
			System.out.println("size of the LinkedList is" + RF.size());
			System.out.println("*********************************************************************************************************************************************************************");
			//displayLinkedList(RF);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void displayLinkedList(LinkedList<Node> RF) {
		
		System.out.println("size of the LinkedList is" + RF.size());
		System.out.println("Its contents are");
		for (Node n : RF) {
			System.out.print("seed: " + n.seed_node);
			System.out.print("mentioned:" + n.mentioned_node);
			System.out.println("count:" + n.count);
		}
		System.out.println("*********************************************************************************************************");
	}

	public void writeToFile(LinkedList<Node> RF,LinkedList<Post> PT,String mapping_number,String user_id,String number_of_posts,String followers_count,String friends_count) throws IOException {
		realFriends= new FileWriter(id_track,true);	
		Post element=new Post(mapping_number,user_id,followers_count,friends_count,number_of_posts,0);
		for(Node n: RF)
		{
			if(n.seed_node.equals(user_id))
			{
			realFriends.append(n.map_num);
			realFriends.append(',');
			realFriends.append(n.seed_node);
			realFriends.append(',');
			realFriends.append(n.mentioned_node);
			realFriends.append(',');
			realFriends.append(n.count.toString());
			realFriends.append('\n');
			 if(n.count>=2)
					 element.actual_friends++;
			}
			
		}
		PT.addLast(element);
		posttracker= new FileWriter(post_track,true);	
		posttracker.append(element.map_num);
		posttracker.append(',');
		posttracker.append(element.seed_node);
		posttracker.append(',');
		posttracker.append(element.followers_count);
		posttracker.append(',');
		posttracker.append(element.friends_count);
		posttracker.append(',');
		posttracker.append(element.number_of_posts);
		posttracker.append(',');
		posttracker.append(element.actual_friends.toString());
		posttracker.append('\n');
		posttracker.flush();
		posttracker.close();
		realFriends.flush();
		realFriends.close();
	}

	public void ConnectionObjectNull(LinkedList<Node> RF, LinkedList<Post> PT, String user_id,String mapping_number) throws IOException 
	{
		realFriends= new FileWriter(id_track,true);	
		Post element=new Post(mapping_number,user_id,"-2","-2","-2",-2);
		/*Node item=new Node(mapping_number,user_id,"-2",-2);
		RF.addLast(item);
		realFriends.append(mapping_number);
		realFriends.append(',');
		realFriends.append(user_id);
		realFriends.append(',');
		realFriends.append("-2");
		realFriends.append(',');
		realFriends.append("-2");
		realFriends.append('\n');*/
		
		PT.addLast(element);
		posttracker= new FileWriter(post_track,true);	
		posttracker.append(mapping_number);
		posttracker.append(',');
		posttracker.append(user_id);
		posttracker.append(',');
		posttracker.append("-2");
		posttracker.append(',');
		posttracker.append("-2");
		posttracker.append(',');
		posttracker.append("-2");
		posttracker.append(',');
		posttracker.append("-2");
		posttracker.append('\n');
		posttracker.flush();
		posttracker.close();
		realFriends.flush();
		realFriends.close();
	}
	public int tweetParser(String id_str,String mapping_number)
	{
		JSONArray c= null;
		String user_id=null;
		String numberOfPosts=null;
		String followers_count=null;
		String friends_count=null;
		Long IntNumberOfPosts=new Long(0);
		int loopcount=0;
		int status=0;
		long max_id=-1;
		boolean flag=false;
		LinkedList<Node> RF = new LinkedList<Node>();
	    LinkedList<Post> PT = new LinkedList<Post>();
	    System.out.println("Running tweet parser for "+ id_str);
		try{
			do
			{
				HttpURLConnection huc = connectToTwitter(id_str,max_id);
				if(huc==null)
				{
					System.out.println("Connection object NULL: writing -1 to the file for id:"+ id_str);
					//ConnectionObjectNull(RF,PT,id_str,mapping_number);
					status = 4500;
					return status;	
				}
				else if(huc.getResponseCode() == 429) {
					status = 429;
					return status;
				}
				else if(huc.getResponseCode() == 200)
				{	
					status=200;
					StringBuilder page = bufferTwitterData(huc);               
					c = new JSONArray(page.toString()); 
					if(c.length()==0)
					{
						huc.disconnect();
						GetProfile(RF,PT,id_str,mapping_number);
						flag=true;
						break;
					}
					for (int i=0; i<=c.length()-1; i++)
					{

						JSONObject user = c.getJSONObject(i).getJSONObject("user"); 
						user_id= user.get("id").toString();
						followers_count=user.get("followers_count").toString();
						friends_count=user.get("friends_count").toString();
						System.out.println("user id: "+ user_id);
						//String tweet = c.getJSONObject(i).get("text").toString();
						//System.out.println("tweet "+ i + "is "+ tweet);
						numberOfPosts=user.get("statuses_count").toString();
						
						
						//New fetch
						if (Long.parseLong(numberOfPosts) > MAX_TWEETS || Long.parseLong(followers_count) > 2000 || Long.parseLong(friends_count) > 2000 ) {
							return status;
						}
						
						if(loopcount==0)
							{
							IntNumberOfPosts=new Long((int)user.get("statuses_count"));
							}

						if(i==c.length()-1)
						{
							max_id=Long.parseLong(c.getJSONObject(i).get("id_str").toString());
							max_id--;
						}

						JSONArray mention_array = (JSONArray) c.getJSONObject(i).getJSONObject("entities").get("user_mentions");
						String O=mention_array.toString();
		
						boolean empty=O.equals("[]");
						
						
						String mentioned_user_id=null;
							for (int k = 0; k < mention_array.length(); k++) 
							{
								mentioned_user_id= mention_array.getJSONObject(k).get("id").toString();
								Node item=new Node(mapping_number,user_id,mentioned_user_id,1);
								countMentions(RF,item);

							}
							if(empty)
							{	/*
								Node item=new Node(mapping_number,user_id,"-1",0);
								countMentions(RF,item);
								*/
							}
					}//end of for
					if(loopcount==0 && IntNumberOfPosts>MAX_TWEETS)
						{
							IntNumberOfPosts=MAX_TWEETS; 
							
						}
					loopcount++;
					IntNumberOfPosts=IntNumberOfPosts-200;
					System.out.println("Current Num of posts" +IntNumberOfPosts);
					if(IntNumberOfPosts<=0)
						break;
				} //end of else-end of one user`s tweets

				
			}while(IntNumberOfPosts>0 && IntNumberOfPosts<MAX_TWEETS);
			if(flag==false)
			writeToFile(RF,PT,mapping_number,user_id,numberOfPosts,followers_count,friends_count);
		}//end of try
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return status;
	}

   public StringBuilder bufferTwitterData(HttpURLConnection huc) throws IOException 
   {
	   BufferedReader bRead = new BufferedReader(new InputStreamReader((InputStream) huc.getContent()));
	   StringBuilder page = new StringBuilder();
	   String temp= "";
	   while((temp = bRead.readLine())!=null)
	   {
		   page.append(temp);
	   }
	   bRead.close();
	   return page;
   }

   public HttpURLConnection connectToTwitter(String id_str,long max_id) throws MalformedURLException,
   IOException, OAuthMessageSignerException,
   OAuthExpectationFailedException, OAuthCommunicationException 
   {
	   URL url;
	   url=(max_id==-1)?new URL("https://api.twitter.com/1.1/statuses/user_timeline.json?user_id="+id_str+"&count="+200):new URL("https://api.twitter.com/1.1/statuses/user_timeline.json?user_id="+id_str+"&max_id="+max_id+"&count="+200);
	   
		   
	   HttpURLConnection huc = (HttpURLConnection) url.openConnection();
	   try
	   {
		   huc.setReadTimeout(5000);           
		   Consumer.sign(huc);
		   while(true)
		   {
		   huc.connect();
		   if(huc.getResponseCode()==429)
           {
           	    try
           	    {
               	System.out.println("HTTP 429: Too many requests! Switching to sleep mode");
                //Thread.sleep(950000);
                huc.disconnect();    
                //continue;
                break;
           	    }
           	    catch(Exception e)
           	    {
           	    	e.printStackTrace();
           	    }
                     
           }
           
           if(huc.getResponseCode()==500 || huc.getResponseCode()==502 || huc.getResponseCode()==503)
           {
           	System.out.println("Response code is "+ huc.getResponseCode());           	
           	return null;    
           }
		   if(huc.getResponseCode()==401)
           {
           	System.out.println("HTTP 401: Unauthorized");
           	return null;
           }
		   if(huc.getResponseCode()== 403)
		   {
		   System.out.print("HTTP 403: The request was a valid request, but the server is refusing to respond to it");
		   return null;
		   }
           if(huc.getResponseCode()==404)
           {
           	System.out.println("HTTP 404:Resource Not found! Passing on to the next user");
           	return null;
           }
           if(huc.getResponseCode()==200)
           {
        	   break;
           }
		   } 
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	   return huc;
   }
   public void GetProfile(LinkedList<Node> RF,LinkedList<Post> PT,String id_str,String mapping_number) 
   {
	   try 
	   {
	        System.out.println("Processing profile of "+id_str);
	       // boolean flag = true;
	        URL url = new URL("https://api.twitter.com/1.1/users/show.json?user_id="+id_str);
	        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
	    	huc.setReadTimeout(5000);
	    	huc.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
	        // Step 2: Sign the request using the OAuth Secret
	        Consumer.sign(huc);
	        huc.connect();
	        int status = huc.getResponseCode();
	        if (status != HttpURLConnection.HTTP_OK) 
	    	{
	    		if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER)
	    		System.out.println("The HTTP Connection Status is" + status);
	    	}
	    
	        BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));
	    	String inputLine;
	    	StringBuffer html = new StringBuffer();
	     
	    	while ((inputLine = in.readLine()) != null)
	    	{
	    		html.append(inputLine);
	    	}
	    	in.close();
	    	System.out.println("Length of Output\n" + html.length());
	    	System.out.println("URL Content... \n" + html.toString());
	    	//Node item=new Node(mapping_number,id_str,"-1",0);
	    	//L.addLast(item);
	    	JSONObject c = new JSONObject(html.toString());
	    	String numberOfPosts=c.get("statuses_count").toString();
	    	String followers_count=c.get("followers_count").toString();
	    	String friends_count=c.get("friends_count").toString();
	    	writeToFile(RF,PT,mapping_number,id_str,numberOfPosts,followers_count,friends_count);
	     
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return;
   } 
}
