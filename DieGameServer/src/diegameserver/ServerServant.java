package diegameserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import org.omg.CORBA.ORB;

import DieGameApp.*;

// Hold player's information (name, score, player_ref : PlayerServant)
class Player_Info {
	public String name ;
	public int score ;
	public PlayerInt player_ref; // Reference to the Player Servant passed with the RegisterCallback function
	
	@Override
	public String toString () {
		return name + " " + score + " " + player_ref;
	}
}

public class ServerServant extends ServerIntPOA {
	
	private static int counter = 0 ; // When counter % 7 == 0, reset all the variables in this class
	
	private String player_name ;
	private int player_score ;
	private PlayerInt player_ref;
	
	private Connection conn = null ;

	// Will hold the 7 player's info that register -- Clear when counter % 7 == 0
	private ArrayList <Player_Info> players_info_list = new ArrayList <Player_Info> () ;
	
	private static ORB orb;
	public void setORB (ORB _orb){
		orb = _orb;
	}
	
	private Connection getConnection()throws Exception{
		String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url="jdbc:sqlserver://localhost:1433;databaseName=CORBA_DIE_GAME_DB";
		String username="sa";
		String password="qazqwewsx";
		Class.forName(driver);
		return DriverManager.getConnection(url,username,password);
	}
	
	private void InsertPlayer (String player_name, int score){ // Insert player in database
		
		String query = "INSERT INTO scores (player_name, score) VALUES "
                	+ "('"+player_name+"', '"+score+"')";
		
		try {
			conn = getConnection () ;
			Statement stmt = conn.createStatement () ; 
			
			stmt.execute(query);
			
		}catch (Exception e){
			e.printStackTrace();
			System.exit(-1);
		}finally {
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
		
	}
	
	// Return a random number and set player_name and player_score
	public int MRandom(String _player_name) { 
		Random random = new Random (System.currentTimeMillis()) ;
		int random_number = random.nextInt(7) + 1;
		
		player_name = _player_name ;
		player_score = random_number ;
		
		return random_number ;
	}
	
	
	// Get the PlayerServant reference to pass it as a parameter to the callback function of the player
	// add a Player_Info to the players_info_list
	// Insert the player's information in the database
	// increment counter by 1
	// Check for a winner
	public void RegisterCallback(PlayerInt playerref) {
		player_ref = playerref ; // Get Player Servant reference

		Player_Info player_info = new Player_Info () ;
		player_info.name = player_name ;
		player_info.score = player_score;
		player_info.player_ref = player_ref;
		
		players_info_list.add (player_info); //add a Player_Info to the players_info_list 
		
		InsertPlayer (player_name, player_score); // Insert in database
		
		counter ++ ;
				
		CheckWinner () ;
	}
	
	
	private void CheckWinner () {
		
		if (counter % 7 != 0){ // If number of current players is not 7, don't check for a winner (return)
			return ;
		}
		
		//Get the player with the highest score from the players_info_list
		Player_Info winner_info = this.players_info_list.get(0) ;
		for ( int i = 1 ; i < this.players_info_list.size() ; i ++ ){
			Player_Info current_player_info = this.players_info_list.get(i);
			if (current_player_info.score > winner_info.score){
				winner_info = current_player_info;
			}
		}
		
		
		String results_msg = "Player: " + winner_info.name + " is the winner with a score of: " + winner_info.score;
		
		String players_left_msg = "Players left: "; // List of the players that left the game before doing the callback
		
		for ( int i = 0 ; i < this.players_info_list.size() ; i ++ ){
			
			// If an exception is thrown, that means this player is not a valid object: this player left the game
			try {
				this.players_info_list.get(i).player_ref.callback(results_msg);
				this.players_info_list.get(i).player_ref.callback(players_left_msg);
			}catch (Exception e){
				players_left_msg += this.players_info_list.get(i).name;
			}
			
			
		}
		
		//Reset variables
		counter = 0;
		this.players_info_list.clear();
		
	}

}
