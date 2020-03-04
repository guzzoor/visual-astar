package org.guzzoor;

public class Node{

    /** 
     * The position of the node on the board
     */
    private int x, y;

    /** The parrent of a node, used for backtracking to get solution
     *  if any exist
     */
    private Node parrent;

    /** 
     * If node is an obstacle this will be one, if walkable 0
    */
    private int walkable = 0;

    /** Cost from the starting node to get to this node */
    private double gCost;
    /** Estimated cost from current node to end node */
    private double hCost;
    /** gCost + hCost */
    private double fCost;

    
    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }


    public void setWalkable(int w){
        this.walkable = w;
    }

    public void setParrent(Node p){
        this.parrent = p;
    }

    public Node getParrent(){
        return this.parrent;
    }

    public boolean isWalkable(){
        return this.walkable == 0;
    }

    public void setObstacle(){
        this.walkable = 1;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public double getF(){
        return this.fCost;
    }

    public double getH(){
        return this.hCost;
    }

    public double getG(){
        return this.gCost;
    }

    public void setCosts(double h, double g, double f){
        this.hCost = h;
        this.gCost = g;
        this.fCost = f;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean equals(Node n){
        return(this.getX() == n.getX() && this.getY() == n.getY());
    }

}