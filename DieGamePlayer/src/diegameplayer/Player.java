package diegameplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import DieGameApp.*;

// Called after a timer exceeds it time - Iterrput the thread to end orb.run () 
class PlayerExit extends TimerTask {
	
	Thread player_thread;
	
	public PlayerExit (Thread _player_thread){
		player_thread = _player_thread;
	}
	
	// execute when the timer exceeds
	public void run () {
		player_thread.interrupt();
	}
}

public class Player extends Thread {

	private static ServerInt server ;
	
	public static void main (String args []) throws IOException{
		
		String player_name;
		
		System.out.println("Enter your name:");
		System.out.flush();
		
		BufferedReader in1=new BufferedReader(new InputStreamReader(System.in));
		player_name = in1.readLine();
				
		try {
			
			//Init ORB
			ORB orb = ORB.init(args, null);
			
			//get root POA and ref
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			
			//create player servant register it with ORB and get reference as "player"
			PlayerServant playerservant = new PlayerServant () ;
			playerservant.setORB(orb);
			playerservant.setName(player_name);
			org.omg.CORBA.Object objRef = rootpoa.servant_to_reference(playerservant);
			PlayerInt player = PlayerIntHelper.narrow(objRef);
			
			//Get NamingContextExt
			NamingContextExt ncRef = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
			
			//Get Server servant from naming context
			String name = "Server";
			server = ServerIntHelper.narrow(ncRef.resolve_str(name));
			
			//START REGISTERING CLIENT WITH SERVER
			
			//get random number
			int random_number = server.MRandom(player_name);
			
			//print out random number
			System.out.println("Your number is " + random_number);
			
			//register with server to be called back
			server.RegisterCallback(player);
			
			
			// Interrupt thread after 5 min to remove the block of orb.run ()
			Timer t = new Timer () ;
			t.schedule(new PlayerExit (Thread.currentThread()), 5 * 60 * 1000); // Call PlayerExit().run() after 5 minutes
			
			orb.run();
			
		}catch (Exception e){
			// If the exception is caused by an interrupt --> interrupt the thread
			if (e.getClass().getName().equalsIgnoreCase("java.lang.ArithmeticException ")){
				Thread.currentThread().interrupt();
			}
			
			e.printStackTrace(System.out);
		}
		
		
		//Player left
		System.out.println("Left");
		
	}
	
}
