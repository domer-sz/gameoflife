package pl.shimoon.gameoflife;

import pl.shimoon.gameoflife.dashboard.Dashboard;
import pl.shimoon.gameoflife.mathtools.IndexConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class MainLoop extends JPanel{
    public static Dashboard dashboard;
    public static final IndexConverter indexConverter = new IndexConverter();

    public void paint(Graphics g) {
        Image img = createCurrentIterationView(dashboard);
        g.drawImage(img, 0, 0,this);
    }

    private Image createCurrentIterationView(Dashboard dashboard) {
        BufferedImage bufferedImage = new BufferedImage(1000,400,BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        for(int y = 0 ; y < dashboard.getBoardSize() / dashboard.getBoardWidth() ; y++ ){
            for(int x = 0; x < dashboard.getBoardWidth() ; x++){
                long currentIndex = indexConverter.convertToSingleIndex(x, y, dashboard.getBoardWidth());
                g.setColor(Color.BLUE);
                try {
                    if (dashboard.getBoard().get((int) currentIndex)) {
                        g.setColor(Color.CYAN);
                    }
                    g.drawRect(x*20,y*20,10,10);
               }catch (IndexOutOfBoundsException e){}
            }
        }

        return bufferedImage;
    }

    public MainLoop() {
        configDashboard(1000);
        
        JFrame frame = new JFrame();
        frame.getContentPane().add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020, 420);
        frame.setVisible(true);
    }

    public  void startGameLoop(int iterationNumber) {
        for (int i = 0; i < iterationNumber; i++) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Iteration: " + i);
            dashboard.calculateNewBoard();
            this.repaint();
        }
    }

    private  void configDashboard(int boardSize) {
        dashboard = new Dashboard(boardSize, 50);
        dashboard.setStartLivingCells(generateStartdashbardState(boardSize));
    }

    private  List<Integer> generateStartdashbardState(int boardSize) {
        Random randomGenerator = new Random();
        List<Integer> collect = IntStream.iterate(0, i -> ++i)
                .limit(dashboard.getBoardSize())
                .map(i -> randomGenerator.nextInt(boardSize))
                .boxed()
                .collect(toList());
        return collect;
    }
}
