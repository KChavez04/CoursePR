//Written by Jerry Gannod, comments written by Kasandra Chavez
package edu.tntech.csc2310;

import java.util.ArrayList;
import java.util.HashMap;

public class CoursePR {

    private final long id;

    private final ArrayList<HashMap<String, String>> adjlist;

    /**
     * Sets in the values for ID and the adjacency list.
     *
     * @param id - Holds the identifying integer (long).
     * @param adjlist - Holds the adjacency list.
     */
    public CoursePR(long id, ArrayList<HashMap<String, String>> adjlist) {
        this.id = id;
        this.adjlist = adjlist;
    }

    /**
     * @return adjList - Returns the adjacency list.
     */
    public ArrayList<HashMap<String, String>> getAdjacencyList() {
        return adjlist;
    }

    /**
     * @return id - Returns the ID.
     */
    public long getId() {
        return id;
    }

}
