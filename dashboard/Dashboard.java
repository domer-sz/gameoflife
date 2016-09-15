package pl.shimoon.gameoflife.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class Dashboard {
    private List<Boolean> board;
    private int boardWidth;



    public Dashboard(int boardSize, int boardWidth) {
        this.boardWidth = boardWidth;
        this.board = new ArrayList<>(boardSize);
        IntStream.iterate(0, i -> ++i)
                .limit(boardSize)
                .forEach(i -> {
                    board.add(false);
                });
    }

    public void setStartLivingCells(List<Integer> starLivingCellsIndex) {
        starLivingCellsIndex.stream().forEach(cellIndex -> {
            if (cellIndex < board.size()) {
                board.set(cellIndex, true);
            }
        });
    }

    public void printBoardToConsole() {
        printList(board);
    }

    public void calculateNewBoard() {
        ArrayList<Boolean> newBoard = new ArrayList<>(board.size());

        for (int y = 0; y < board.size() / boardWidth; y++) {
            for (int x = 0; x < boardWidth; x++) {
//                System.out.print((y * boardWidth) + x + " ");
//                newBoard.set((y * boardWidth) + x, checkIfAlive(board, (y * boardWidth) + x));
                newBoard.add(checkIfAlive(board, (y * boardWidth) + x));
            }
//            System.out.println();
        }
//        System.out.println();
//        printList(newBoard);
        board = newBoard;

    }

    private Boolean checkIfAlive(List<Boolean> board, int currentIndex) {
        return returnNewCellState(board, currentIndex, checkliveNeighborNumber(board, currentIndex));
    }

    private Boolean returnNewCellState(List<Boolean> board, int currentIndex, int aliveNeighborNumber) {
        if (board.get(currentIndex)) {
            return returnNewStateIfCellIsAlive(aliveNeighborNumber);
        } else {
            return returnNewStateIfCellIsDead(aliveNeighborNumber);
        }
    }

    private Boolean returnNewStateIfCellIsDead(int aliveNeighborNumber) {
        return aliveNeighborNumber == 3;
    }

    private Boolean returnNewStateIfCellIsAlive(int aliveNeighborNumber) {
        return aliveNeighborNumber == 2 || aliveNeighborNumber == 3;
    }

    private byte checkliveNeighborNumber(List<Boolean> board, int currentIndex) {
        byte aliveNeightboarNumber = 0;
        //zbieranie liczby zywych sasiadow z lini nad obecna komorka
        for (int i = -1; i < 3; i++) {
            try {
                if (board.get(currentIndex - boardWidth + i)) {
                    aliveNeightboarNumber++;
                }
            } catch (IndexOutOfBoundsException e) {}
        }
        //zbieranie liczby zywych sasiadow z lini pod obecna komorka
        for (int i = -1; i < 3; i++) {
            try {
                if (board.get(currentIndex + boardWidth + i)) {
                    aliveNeightboarNumber++;
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }
        //sprawdzeia sasiada z lewej
        try {
            if (board.get(currentIndex - 1)) aliveNeightboarNumber++;
        } catch (IndexOutOfBoundsException e) {
        }
        //sprawdzenie sasiada z prawej
        try {
            if (board.get(currentIndex + 1)) aliveNeightboarNumber++;
        } catch (IndexOutOfBoundsException e) {
        }

        return aliveNeightboarNumber;
    }

    private void printList(List<Boolean> newBoard){
        final int[] counter = {0};
        newBoard.stream().forEach(
                cell -> {
                    counter[0]++;
                    if (cell) System.out.print(1 + " ");
                    else System.out.print(0 + " ");
                    if (counter[0] % boardWidth == 0) System.out.println();
                }
        );
    }


    public List<Boolean> getBoard() {
        return board;
    }

    public long getBoardSize() {
        return board.size();
    }

    public int getBoardWidth() {
        return boardWidth;
    }
}
