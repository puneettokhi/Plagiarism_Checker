package com.puneettokhi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by puneettokhi on 4/11/17.
 */


public class Parser {


    static String string_value;    // variable to hold string_value

    String str;     // to hold the string_value in the constructor

    static Scanner scanner;

    public Parser(String str) {     // constructor with one argument
        this.str = string_value;
    }


    public static void get_Corpus() {  // method to get the corpus file which is entered in the command line arguments

        try {

            scanner = new Scanner(new File("src/corpus.txt"));   // if files exists, get the corpus file
        }
        catch (FileNotFoundException file) {  // throws exception if file not found

            file.printStackTrace();  // displays exception message
        }
    }


    public static void get_Target() {   // method to get the target file

        Scanner scanner = null;
        String target_file = " ";    // blank strong variable
        try {
            scanner = new Scanner(new File("src/target.txt")); // if files exists, get the target file

        } catch (FileNotFoundException file) {   // if not found, throw exception message

            file.printStackTrace();   // displays exception message
        }

        while (scanner.hasNextLine()) {     // loop until the end of file

            target_file = target_file + scanner.nextLine() + " ";  // adds the string variable with contents of the target filr
        }

        string_value = target_file;    // string value holds the final contents of the file
    }


        public static void parse_file( int length){  // method to parse the file

            get_Corpus();

            String[] string_array = null;   // array of strings initialized to null

            int corpusFileNumber;  // variable used for CorpusFileNumber


            // Creating a hashtable with a queue and integer string_value. Queue data structure is used in the hash table to achieve the
            // complexity of 0(n). Since, the Queue has a constant insertion and deletion, it is used in the hashtable to acheive the
            // desired linear complexity.
            Hashtable<Queue<String>, Integer> corpus_Hashtable = new Hashtable<>();  // creating a new hashtable with a key of type Queue and value of type Integer.

            String temp;

            while (scanner.hasNextLine()) {   // while loop which executes until there is no more data in the scanner

                temp = scanner.nextLine().toLowerCase();  // converting data to lower case
                temp = temp.replaceAll("\\. ", " ");
                temp = temp.replaceAll("\"", "");
                string_array = temp.split(":|\\s"); // using regex to split and making sure file contains :

                corpusFileNumber = Integer.parseInt(string_array[0]);  // getting the corpus.txt as first command line argument

                Queue<String> corpusQueueList = new LinkedList<String>();  // a linked list implementation of queue of strings

                for (int i = 1; i < string_array.length; i++) {   // for loop to add the string_array to the queue

                    corpusQueueList.add(string_array[i]);  // adding the string_array to the queue

                    if (i >= length) {   // only if counter >= length, then a new Queue is created

                        Queue<String> resultQueue = new LinkedList<String>(corpusQueueList);
                        corpus_Hashtable.put(resultQueue, corpusFileNumber);

                        corpusQueueList.remove();   // remove the corpusQueueList
                    }

                }
            }


            String[] words = string_value.toLowerCase().split("\\s");  // using regular expressions to split into sub arrays

            boolean is_Plagiarized = false;   // boolean variable to determine if target file is plagiarized or not.

            Queue<String> queueList = new LinkedList<String>();   // creating a new QueueList of strings

            for (int i = 0; i < words.length; i++) {    // for loop that loops until the length of the array


                queueList.add(words[i]);    // adds the string array to the queue

                if (i + 1 >= length) {

                    if (corpus_Hashtable.containsKey(queueList)) {   // if the hashtable, contains the key, then it is plagiarized

                        System.out.println("Plagiarized from " + corpus_Hashtable.get(queueList));  // get the list from the hash table
                        is_Plagiarized = true;
                    }
                    queueList.remove();    // removes the duplicates from the queueList

                }
                if (i == words.length - 1) {   // if counter = length of array - 1, then it is not plagiarized
                    if (is_Plagiarized = false) {
                        System.out.println("Not Plagiarized");
                    }

                }
            }
        }

    public static void main(String[] args) {

        // get Corpus
        String corpusFile = args[0];   // first command line argument

        if (corpusFile.equalsIgnoreCase("src/corpus.txt")) {   // corpus file

            get_Corpus();   // gets the corpus file

        } else {
            System.out.println("Please enter a valid corpus name");
        }


        String targetFile = args[1];   // second command line argument

        if (targetFile.equalsIgnoreCase("src/target.txt")) {   // target file

            get_Target();   // gets the target file

        } else {

            System.out.println("Please enter a valid target name"); // if the command line argument are entered wrong, this is displayed
        }

        int sequence_number = Integer.parseInt(args[2]);   // length of the match sequence set of third command line argument

        Parser parser = new Parser(string_value);   // creating an instance of Parser class by calling the constructor with the argument of string_value

        // using the parse_file method to determine if the target file has been plagiarized from the corpus file or not

        parser.parse_file(sequence_number);  // using the parse_file method with the argument of sequence_number



    }

}
