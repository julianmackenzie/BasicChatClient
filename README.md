# BasicChatClient
Basic networked server and client system for online chatrooms using TCP for CS342: Software Design.

This project utilized Java threads and sockets, new concepts to me at the time.

Executing one instance of the Server process and one or more of the Client processes from the main menu will allow for communication between clients via packets sent to and from the server. Clients also have the option to directly message another using the client ID of the recipient.

Things this project taught me:
1. Threads are COMPLICATED!
2. Sending a data object via TCP allows a lot of options for processing different kinds of data in the Server.
3. A basic, readable user interface is the foundation for a positive user experience.
4. I really enjoy the idea of pursuing UI design! Obviously the UI of this project is extrmely minimal and system-y, but it has me feeling inspired to create clean, user-friendly applications as I expand my knowlege of the possibilites of user interface creation.

Demonstration:

Three clients each send each other messages.

![1](https://user-images.githubusercontent.com/113747039/192051363-d23df073-f344-4934-be37-9a330bba1ea7.png)

Client 1 decides to directly message Client 2...

![2](https://user-images.githubusercontent.com/113747039/192052007-c49ec634-8c89-48f3-a2de-107827f17a6e.png)

Client 2 disconnects, Client 4 signs on.

![3](https://user-images.githubusercontent.com/113747039/192052032-ac375d7f-146c-412e-a4bf-4d6bafe24d86.png)
