package org.guzzoor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    public Node[][] createBoard(){
        int[][] m1 =
                {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        Node[][] nodeMaze = new Node[10][10];

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                nodeMaze[i][j] = new Node(i, j);
                if(m1[i][j] == 1){
                    nodeMaze[i][j].setObstacle();
                }
            }
        }
        return nodeMaze;
    }

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        GridPane gp = new GridPane();

        Node[][] n = createBoard();
        AstarSolver astarSolver = new AstarSolver(n, n[0][0], n[9][9]);
        astarSolver.solve();

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Button tmp = new Button();
                gp.add(tmp, i, j);
            }
        }

        for(Node node:astarSolver.getSolution()){
            Button tmp = new Button();
            tmp.setStyle("-fx-base: green;");
            gp.add(tmp, node.getY(), node.getX());
        }


        var scene = new Scene(gp, 640, 480);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}