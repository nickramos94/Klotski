[HOME](../README.md)

# Manual
###  Game description
Klotski is a game based on a set of pieces arranged on a chessboard of a certain size and number of pieces, with the goal of "removing" a specific piece from the board.

Our implementation of Klotski features a keyboard that is four squares wide and five squares tall, with ten pieces arranged on it, and the ultimate goal is to get the largest piece among them to the red strip at the bottom of the chessboard.

The program allows you to choose from multiple initial arrangements of the pieces from the main menu and also to "pause" the game by saving the arrangement of the pieces in a file that can be reloaded later.

To make solving the puzzle less challenging, the program provides the possibility to know the most appropriate move to make in order to win the game: by connecting to an external server, the program receives a list of all the necessary moves to win. It is also possible to undo previously made moves using the "Undo" button, which the program keeps track of.

Moving the squares is done by the user by dragging the pieces with the mouse pointer.

### Installation and execution
To run the program, simply execute the **.jar** file that comes with it.

### Runtime enviroment

The program has been developed, tested, and executed with **Java 11**. Its functionality has not been tested with earlier or later versions of the runtime.

### External libraries
#### Jackson

**Jackson** is a popular library used to handle **JSON** files. It is particularly useful for saving the program's state and communicating with the solver running in a Node.JS environment on an external server.

The JSON is mapped to an ObjectMapper object for reading/writing, and then ArrayNode and ObjectNode objects are used depending on the data to be managed.

For example, the createArrayNode() function, used by an ObjectMapper object, allows you to create an array that will be written/read in the JSON. Subsequently, a JSON ObjectNode can be created by assigning the value obtained from the addObject() method of the array. The values of this object can be modified using its put(fieldName, valueName) method, which allows you to write the key and value of the object. Since this object was created from a method of ArrayNode, it is already present in the array.

#### HTTPUrlConnection
The library allows you to manage connections based on the **HTTP** protocol. In this particular program, it is used to send a POST request to the external server containing the JSON of the arrangement of the keyboard pieces, which will be used by the solving program. HTTPUrlConnection is also responsible for receiving the response from the external server, which also contains a JSON with the list of moves that lead to victory.

```JAVA
HttpURLConnection con = (HttpURLConnection) url.openConnection();
con.setRequestMethod("POST");
con.setDoOutput(true);
con.setRequestProperty("Content-Type", "application/json");
con.setRequestProperty("Accept", "application/json");
```

The following example code opens a socket named "con" that establishes a connection to the URL specified in the previously defined "url" variable. Then, it defines a POST request with a JSON as its content.

Once done, the JSON is sent to the server using the write() function of the OutputStream object. The server's response JSON will be received through an instance of an InputStream object, and its content will be read and written into a string using a BufferedReader.

Finally, this string will be processed by the Jackson library to turn it into a proper JSON, which the program will use to suggest the next moves to the user.
