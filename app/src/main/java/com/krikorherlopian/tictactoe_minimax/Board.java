package com.krikorherlopian.tictactoe_minimax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Represents the Tic Tac Toe board.
 */
public class Board {

    List<Point> pointsAvailable;
    int[][] board = new int[3][3];
    List<PointsAndScores> scores;

    public void placeAMove(Point point, int player) {
        board[point.x][point.y] = player;
    }


    public List<Point> getStates() {
        pointsAvailable = new ArrayList<>();
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                if (board[row][col] == 0) {
                    pointsAvailable.add(new Point(row, col));
                }
            }
        }
        return pointsAvailable;
    }


    public int returnMaximum(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public Point perfectMove() {
        int maximum = -100000;
        int best = -1;

        for (int val = 0; val < scores.size(); ++val) {
            if (maximum < scores.get(val).score) {
                maximum = scores.get(val).score;
                best = val;
            }
        }

        return scores.get(best).point;
    }

    public void callMinimaxFunction(int depth, int turn){
        scores = new ArrayList<>();
        minimax(depth, turn);
    }

    public int minimax(int depth, int turn) {
        List<Point> pointsAvailable = getStates();
        List<Integer> scoreList = new ArrayList<>();
        if(board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)
            return -1;
        else if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) {
            return -1;
        }
        else if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1) {
            return +1;
        }
        else if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1)
            return +1;
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                return +1;
            }
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                return -1;
            }
        }

        if (pointsAvailable.isEmpty()){
            return 0;
        }

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Point point = pointsAvailable.get(i);
            if (turn == 2) {
                placeAMove(point, 2);
                scoreList.add(minimax(depth + 1, 1));
            }
            else if (turn == 1) {
                placeAMove(point, 1);
                int currentScore = minimax(depth + 1, 2);
                scoreList.add(currentScore);

                if (depth == 0)
                    scores.add(new PointsAndScores(currentScore, point));

            }
            board[point.x][point.y] = 0;
        }
        if(turn == 1)
            return returnMaximum(scoreList);
        else
            return returnMinimum(scoreList);

    }


    public int returnMinimum(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

}