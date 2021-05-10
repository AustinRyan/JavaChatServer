# JavaChatServer
Created a multi client-server java chat server that uses TCP protocols.
This project was my final project for my CS350 networking class. I had to display my knowledge of networking using ports, servers, packets, or anything related in that spectrum.

This chat server uses port 8818 but you can change it to whatever port you want to use. It starts a server, and then you can access this server by going on the terminal and typing in "telnet localhost 8818" and then you are connected. You can have 2 terminals up running the on the same server and chat between the terminals. 
Once someone else is online, you will get a notification saying that the user is online. 

Right now I have hard coded the user names and passwords to log on.

How to login:
Once connected to the server, type "login austin password" you are then logged in for the user austin
Another user you can log into as is "login brad password"

If you are already logged into Austin, then Brad gets online, Austin will get a notification saying that Brad is now online.

How to message users:
Type in "msg <user> <message>"
So an example of this would be from the user Austin: "msg brad Hello". Brad would get a notification on his terminal saying Austin has sent you a message: <message>

How to logout:
Just type quit

Improvements:
Since this was my networking final project, I focused on getting the networking tools and functions working before making any super cool features.

But improvements to make would be:
  1.) Making a create an account function (Not having usernames and passwords hard coded)
  2.) Making a group chat function
  3.) Making the chat message able to have spaces
          a.) Anything after the first space gets cut off because the code splits each word into a different token, and a command for msg takes 3 tokens: msg <username> <message>. So the first token is the msg command, the second token is the user you are sending the message to, and the third token is the message, but if/when you include a space in your message it would read that as a fourth token and the command only reacts with the first three tokens.
