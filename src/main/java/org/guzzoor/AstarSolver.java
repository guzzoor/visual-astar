package org.guzzoor; /**
*  A simple implementation of the astar algorithm
*
* @author Jonathan Gustafsson
*/

import java.util.ArrayList;
import java.util.Collections;


public class AstarSolver{

    // Board to be solved

    /** Board to be solved */
    private Node[][] board;
    /** Width of the board */
    private int boardWidth;
    /** Height of the board */
    private int boardHeight;

    /** List containing nodes already examined */
    private ArrayList<Node> closedList;
    /** List containing nodes to be examined */
    private ArrayList<Node> openList;

    /** Will contain the solution if there is any, else null */
    private ArrayList<Node> solution;
 
    /** The start node */
    private Node startNode;

    /** The end node/goal node */
    private Node endNode;

    public AstarSolver(Node[][] board, Node startNode, Node endNode){
        this.board = board;
        this.startNode = startNode;
        this.endNode = endNode;
        this.closedList = new ArrayList<Node>();
        this.openList = new ArrayList<Node>();
        this.boardHeight = this.board.length;
        this.boardWidth = this.board[0].length;
        this.openList.add(this.startNode);
    }

    /**
     * 
     * @param n
     * @return ArrayList containing surrounding nodes of n
     */

    private ArrayList<Node> findNodes(Node n){
        ArrayList<Node> nb = new ArrayList<Node>();

        for(int i = n.getX() - 1; i < n.getX() + 2; i++){
            for(int j = n.getY()-1; j < n.getY() + 2; j++){
                
                // Check if out of index 
                if(i >= this.boardWidth || i < 0 || j >= this.boardHeight || j < 0 ){
                // Check if in closed list or not walkable
                } else {
                    Node tmpNode = this.board[i][j];
                    nb.add(tmpNode);
                }
            }
        }
        return nb;
    }

    /**
     * Will examine the surrounding nodes of current, depending on the properties 
     * of the astar algoritm
     * 
     * @param nodes
     * @param current
     */

    private void nbHandler(ArrayList<Node> nodes, Node current){
        for(Node n:nodes){
            if(!n.isWalkable() || this.closedList.contains(n)){
                continue;
            } else {
                double g = calcGCost(n.getX(), n.getY(), current.getX(), current.getY()); // current.getG() + 1;
                if(!this.openList.contains(n) || n.getG() > g){
                    
                    double  h = calcHCost(n.getX(), n.getY());
                    double f = g + h;
                    n.setCosts(h, g, f);
                    n.setParrent(current);

                    if(!this.openList.contains(n)){
                        this.openList.add(n);
                    }
                }
            }
        }
    }

    /**
     * Will backtrack from the end node to find the way shortest path
     * found by the algoritm
     * 
     * @param current
     */

    private void backtrack(Node current){
        this.solution = new ArrayList<Node>();

        Node cursor = current;

        while (cursor != this.startNode){
            this.solution.add(cursor);
            cursor = cursor.getParrent();
        }

        this.solution.add(this.startNode);

        Collections.reverse(this.solution);
    }

    /**
     * 
     *  The driver function
     * 
     */

    public void solve(){

        while(!this.openList.isEmpty()){
            Node current = lowestFCost();
            this.openList.remove(current);
            this.closedList.add(current);

            if(current.equals(endNode)){
                System.out.println("End node found!");
                backtrack(current);
                printSolution();
            }

            ArrayList<Node> nodeNb = findNodes(current);
            nbHandler(nodeNb, current);
 
        }
    }

    public ArrayList<Node> getSolution() {
        return solution;
    }

    // ----------------------------------- Utility functions -----------------------------------

    /**
     * 
     * @param posX position x of current neighbour to be examined
     * @param posY position y of current neighbour to be examined
     * @param currPosX position x of current node to be examined
     * @param currPosY position y of current to be examined
     * @return g cost of the neighbouring node
     */
    private double calcGCost(int posX, int posY, int currPosX, int currPosY){
        // Above to left
        if(posX - 1 == currPosX && posY - 1 == currPosY){
            return 2;
        // Above to right
        } else if (posX - 1 == currPosX && posY + 1 == currPosY){
            return 2;
        // Bellow to left
        } else if (posX + 1 == currPosX && posY - 1 == currPosY){
            return 2;
        // Bellow to right
        } else if (posX + 1 == currPosX && posY + 1 == currPosY){
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * 
     * @param posX position x of current neighbour to be examined
     * @param posY position y of current neighbour to be examined
     * @return the h cost of the current neighbour
     */

    private double calcHCost(int posX, int posY){
        return Math.sqrt(Math.pow(posX - this.endNode.getX(), 2) + Math.pow(posY - this.endNode.getY(), 2));
    }

    /**
     * 
     * @return Node with lowest f cost in the open list
     */

    private Node lowestFCost(){
        Node tmp = this.openList.get(0);
        for(Node n:this.openList){
            if(n.getF() < tmp.getF()){
                tmp = n;
            }
        }
        return tmp;
    }

    /**
     * Will print the solution
     */

    private void printSolution(){
        for(Node[] row: this.board){
            for(Node n:row){
                if(this.solution.contains(n)){
                    System.out.print(" S ");
                } else if(!n.isWalkable()){
                    System.out.print(" X ");
                } else {
                    System.out.print(" 0 ");
                }
            }
            System.out.println();
        }
    }    

}