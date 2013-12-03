package MyThreads;
import java.io.PrintStream;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import PartTWO.Authenticate.*;
import PartTWO.GetTwitterData.*;
import PartTWO.OAuth.*;

class NewThread extends Thread {
	int thread_count=0;
	String last_mapped;
	OAuthConsumer oc;
	OAuthToken ot;
	public static String last_mapped_num;
	NewThread(int thread_count, String last_mapped) {
		super("Demo Thread");
		this.thread_count=thread_count;
		this.last_mapped=last_mapped;
      
		System.out.println("Child thread: " + this);
		start();
   }

   public void run() {
	   Integer status=0;
	   status=rountine();
   } 
   
   public Integer rountine() {
	   Integer status=0;
	   //String last_mapped_num=null;
	   try{
			oc = findCredentials(this.thread_count);
			Authenticate aue = new Authenticate(oc);
		    ot = aue.GetUserAccessKeySecret();
		    if(ot.getAccessToken().equals(null)|| ot.getAccessSecret().equals(null) ) {
		    	System.out.println("Empty Toekns");
		    	status=1;
		    	return status;
		    }
		    GetTwitterData rae = new GetTwitterData(oc,ot);
		    NewThread.last_mapped_num=rae.fetchData(this.last_mapped);
		    System.out.println(ot.toString());
		} catch(Exception e) {
			   System.out.println("Error in OauthExample");
			   e.printStackTrace();
		}
		return status;
	}
   
   
   
   public OAuthConsumer findCredentials(Integer counter) {
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer("","");
		switch (counter) {
			case 1:
		       	System.out.println("CONSUMER1");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY1,OAuthUtils.CONSUMER_SECRET1);
		       	break;
			case 2:
		       	System.out.println("CONSUMER2");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY2,OAuthUtils.CONSUMER_SECRET2);
		       	break;
			case 3:
				System.out.println("CONSUMER3");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY3,OAuthUtils.CONSUMER_SECRET3);
		       	break;
			case 4:
				System.out.println("CONSUMER4");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY4,OAuthUtils.CONSUMER_SECRET4);
		       	break;
			case 5:
				System.out.println("CONSUMER5");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY5,OAuthUtils.CONSUMER_SECRET5);
		       	break;
			case 6:
				System.out.println("CONSUMER6");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY6,OAuthUtils.CONSUMER_SECRET6);
		       	break;
			case 7:
				System.out.println("CONSUMER7");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY7,OAuthUtils.CONSUMER_SECRET7);
		       	break;
			case 8:
				System.out.println("CONSUMER8");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY8,OAuthUtils.CONSUMER_SECRET8);
		       	break;
			case 9:
				System.out.println("CONSUMER9");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY9,OAuthUtils.CONSUMER_SECRET9);
		       	break;
			case 10:
				System.out.println("CONSUMER10");
		       	consumer = new CommonsHttpOAuthConsumer(OAuthUtils.CONSUMER_KEY10,OAuthUtils.CONSUMER_SECRET10);
		       	break;
				
	       	default:
	       		System.out.println("Something`s Wrong");
	       		
       }
		return consumer;
	}
}

public class MyThreads {
	public static void main(String args[]) {
		NewThread.last_mapped_num="12850";
		PrintStream orgStream = System.out;
		try {
			for(int i=6;i<=10;i++) {
				final NewThread t = new NewThread(i,NewThread.last_mapped_num); 
				System.out.println("Thread "+i);
				t.join();
				System.setOut(orgStream);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
      System.out.println("Main thread exiting.");
   }
}