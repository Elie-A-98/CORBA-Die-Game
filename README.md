# CORBA-Die-Game

A project on the ORB design specification CORBA implementing a Server/Client with callbacks.


a) The client (player) program enters his name, connect to the server and call the server method MRandom (player_name).

b) The server executes this method: draw a random number between 1 and 7 , and returns the result to the client. At the same time, the server will store the name of the player and the number drawn.

c) The client displays the number returned by the server and stay waiting for 5 minutes.

d) After 7 drawn numbers (7 connected clients), the server send back to all clients the name of the winner: the player who draw the highest random number.

