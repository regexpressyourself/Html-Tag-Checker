import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Stack;

/**
 * Created by devon on 10/30/2015.
 */
public class Parser  {
    public static void Parse() throws Exception{
        GUI gui = new GUI();
    URL oracle = new URL(gui.getURL);
    BufferedReader in = new BufferedReader(
            new InputStreamReader(oracle.openStream()));

    Stack<String> s = new Stack<String>();

    String closingTag;
    String openingTag;
    String inputLine;
    boolean spaceReached;
    char[] inputArray;

    while ((inputLine = in.readLine()) != null) { // Go through each line

        closingTag = "";
        openingTag = "";
        inputArray = inputLine.toCharArray();
        // I found it easier to iterate through an array of characters to look for tags.

        for (int i = 0; i < inputArray.length; i++) {

            if (inputArray[i] == '<') {
                if (inputArray[i + 1] == '/') {
                    closingTag += "<";
                    // closing tag is stored without the '/' so it can be easily compared to the opening.

                    for (int j = i+2; j < inputArray.length; j++) {
                        // Since the first two characters, "</", are accounted for, we start at the third
                        closingTag += inputArray[j];

                        if (inputArray[j] == '>') {
                            break;
                        } //end if

                    } // end for


                    // Check the last opening tag after a closing tag has been found
                    // This section still needs some work
                    if (closingTag.equals(openingTag)) {
                        System.out.println(openingTag + " -- " + closingTag);
                    } //end if
                    else { //This doesn't seem to work if there are two tags next to each other. for example,
                        //<li><a>
                        System.out.println("Uh oh....");
                        System.out.println("-------------------");
                        System.out.println(openingTag + " -- " + closingTag);
                        System.out.println("-------------------");
                    }

                } //end if


                else {
                    // else, it must be an opening tag.

                    openingTag += "<";
                    spaceReached = false;
                    // spaceReached is used to find the end of the tag name and the start of
                    // things like id, name, etc. We dont want to store these things, so
                    // we can just skip over them.

                    for (int j = i+1; j < inputArray.length; j++) {
                        // Since "<" has already been acounted for, we don't can start at i+1

                        if (inputArray[j] == ' ') {
                            //check for the spacereached

                            spaceReached = true;
                        } //end if

                        if (inputArray[j] == '>' && inputArray[j-1] != '/') {
                            // This finds the end of the tag, and only pushes it if it is
                            // not a self-closing tag like <meta>, <br>, etc.

                            openingTag += inputArray[j];
                            s.push(openingTag);
                            break;
                        } //end if

                        else if (!spaceReached) {
                            // If it's not the end of the tag and a space has not been found,
                            // the string is updated with the new character

                            openingTag += inputArray[j];
                        }  //end if
                    } // end for
                } //end else
            } // end if

        } //end for

    } //end while



        /*
         a better way to do this would be to pop a div when you get a /div
         because it would let you keep better track of both the html doc's structure
         and your position on the page. All this logic tells us is if there is an incorrect number
         of opening and closing div tags. It might be useful though idk
         The code below might work..mostly just so i can remember my thinking lol


         while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("div"))
                s.push("div");
            if(inputLine.contains("/div"))
                s2.pop();
            System.out.println(inputLine); // This could be erased if you don't want to see the html in console
        }


        Your old code ##############

        String inputLine;
        Stack s = new Stack();
        Stack s2 = new Stack();
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("div"))
                s.push("div");
            if(inputLine.contains("/div"))
                s2.push("/div");
            System.out.println(inputLine);
        }
        try {
            while(!s.empty()||!s2.empty()) {
                System.out.println(s.pop()+" ------ "+s2.pop());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ----------------------------------------------------------------------------------------

          All right, finally got a chance to dive into this. A couple things I notice right away:

          1) The point of using the stack would be to compare the closing tags with
          the last pushed opening tag and make sure they match. For example, you
          find a closing </div>, and you pop off the stack to make sure an opening
          <div> was the last thing pushed. Since we don't need to store the closing value,
          we should be able to get away with just one stack.

          2) The way you're locating the tags is going to cause problems later on.
          We should design the program to find tags regardless of their text. The
          most obvious way to do this is to look for the angle brackets <>.

          My above code seems to be working for a normal html file. There are some
          edge cases we'll have to take into account. It's throwing a lot of errors
          for big sites like Twitter, but working for basic html files. I'm a beer
          too far in to fix it right now, but we're at least moving forward.


         */
}
}
