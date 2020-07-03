package diegameserver;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import DieGameApp.*;

public class Server {
	
	public static void main (String args []){
		
		try {
						
			//Init orb
			ORB orb = ORB.init(args, null);
			
			//get rootpoa ref
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			
			// create servant register it with ORB and get reference as "server"
			ServerServant serverservant = new ServerServant () ;
			serverservant.setORB(orb);
			org.omg.CORBA.Object objRef = rootpoa.servant_to_reference(serverservant);
			ServerInt server = ServerIntHelper.narrow(objRef);
			
			//get namingcontextext
			NamingContextExt ncRef = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
			
			//bind servant reference in naming context in naming context
			String name = "Server";
			NameComponent path [] = ncRef.to_name(name);
			ncRef.rebind(path, server);
			
			System.out.println("Server listening");
			
			//Wait for players invokations
			orb.run();
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Server exit");
		
	}
	
}
