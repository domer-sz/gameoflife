package pl.shimoon.gameoflife.mathtools;

public class IndexConverter {
    public IndexConverter() {
    }

    public long convertToSingleIndex(int x, int y, int boardWidth){
        return (y * boardWidth) + x;
    }
}
