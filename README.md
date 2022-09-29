# BasicChatClient
Basic networked server and client system for online chatrooms using TCP for CS342: Software Design, written in Java.

This was written with a partner, whose GitHub I will link here if I can track her down! Some of my other group projects had a lot of difficulties, partners who were AWOL or didn't have their environment set up to begin with, but this one was fantastic! We troubleshooted very well together and came up with the ideas that made the project possible together.

This project utilized Java threads and sockets, new concepts to us at the time.

Executing one instance of the Server process and one or more of the Client processes from the main menu will allow for communication between clients via packets sent to and from the server. Clients also have the option to directly message another using the client ID of the recipient.

Things this project taught me:
1. Threads are COMPLICATED!
2. Sending a data object via TCP allows a lot of options for processing different kinds of data in the Server.
3. A basic, readable user interface is the foundation for a positive user experience.
4. I really enjoy the idea of pursuing UI design! Obviously the UI of this project is extrmely minimal and system-y, but it has me feeling inspired to create clean, user-friendly applications as I expand my knowlege of the possibilites of user interface creation.
5. A good partner makes all the difference! Having a second set of eyes on you while you write your code makes it so much easier to avoid the slew of dumb mistakes that you tend to make while working with new concepts.

Demonstration:

Three clients each send each other messages.

![1](https://user-images.githubusercontent.com/113747039/192051363-d23df073-f344-4934-be37-9a330bba1ea7.png)

Client 1 decides to directly message Client 2...

![2](https://user-images.githubusercontent.com/113747039/192052007-c49ec634-8c89-48f3-a2de-107827f17a6e.png)

Client 2 disconnects, Client 4 signs on.

![3](https://user-images.githubusercontent.com/113747039/192052032-ac375d7f-146c-412e-a4bf-4d6bafe24d86.png)
