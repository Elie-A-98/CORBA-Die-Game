package diegameplayer;

import org.omg.CORBA.ORB;

import DieGameApp.*;

public class PlayerServant extends PlayerIntPOA {

	private String name ;
	
	private static ORB orb;
	public void setORB (ORB _orb){
		orb = _orb ;
	}
	
	//Used in Player.java to set the name of the player
	public void setName (String name){
		this.name = name ;
	}
	
	//called by the server
	public void callback(String msg) {
		System.out.println("From server: " + msg);
	}

}
