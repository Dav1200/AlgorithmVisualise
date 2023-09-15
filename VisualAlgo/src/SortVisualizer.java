import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SortVisualizer extends JFrame {
    private JPanel sortPanel;
    private JButton startButton;
    private JButton ranButton;
    private JComboBox<Object> a;
    private int[] data;
    private int currentIndex = 0;
    private Timer timer;

    public SortVisualizer() {
        setTitle("Bubble Sort Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        a = new JComboBox<>();
        a.addItem("Bubble sort");
        a.addItem("Insertion sort");
        a.addItem("Selection sort");
        a.addItem("Quick sort");
        sortPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBars(g);
            }
        };

        ranButton = new JButton("Random");
        ranButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomData();
                repaint();
            }
        });


        startButton = new JButton("Start Sorting");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                startSorting(a.getSelectedItem().toString());
            }
        });

        setLayout(new BorderLayout());
        add(sortPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
        add(a, BorderLayout.NORTH);
        add(ranButton, BorderLayout.WEST);


        generateRandomData();
    }

    private void generateRandomData() {
        int numBars = 150; // Number of bars to sort
        data = new int[numBars];
        Random rand = new Random();
        for (int i = 0; i < numBars; i++) {
            data[i] = rand.nextInt(numBars); // Random heights for bars
        }
    }

    private void drawBars(Graphics g) {
        int barWidth = sortPanel.getWidth() / data.length;
        int maxHeight = sortPanel.getHeight();
        g.setColor(Color.RED);
        for (int i = 0; i < data.length; i++) {
            int barHeight = data[i];
            int x = i * barWidth;
            int y = maxHeight - barHeight;
            g.fillRect(x, y, barWidth, barHeight);
        }
    }


    public void selection() {
        currentIndex = 0;
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentIndex < data.length - 1) {
                    int minIndex = currentIndex;

                    for (int i = currentIndex + 1; i < data.length; i++) {
                        if (data[i] < data[minIndex]) {
                            minIndex = i;
                        }
                    }
                    // Swap elements
                    int temp = data[currentIndex];
                    data[currentIndex] = data[minIndex];
                    data[minIndex] = temp;

                    currentIndex++;

                    repaint(); // Redraw the bars after each iteration
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }


    public void bubble() {
        currentIndex = 0;
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < data.length - 1) {
                    for (int i = 0; i < data.length - currentIndex - 1; i++) {
                        if (data[i] > data[i + 1]) {
                            // Swap elements
                            int temp = data[i];
                            data[i] = data[i + 1];
                            data[i + 1] = temp;
                        }
                    }
                    currentIndex++;

                    repaint(); // Redraw the bars after each iteration
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    public void insertion() {
        currentIndex = 0;
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < data.length - 1) {
                    int key = data[currentIndex];
                    int j = currentIndex;

                    while (j > 0 && data[j - 1] > key) {
                        data[j] = data[j - 1];
                        j--;
                    }

                    data[j] = key;

                    currentIndex++;
                    repaint();
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }


    public void quickSortWithTimer() {
        currentIndex = 0;

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < data.length) {
                    quickSortStep(data, 0, data.length - 1);
                    currentIndex++;
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.start();
    }

    public void quickSortStep(int[] data, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(data, left, right);
            quickSortStep(data, left, pivotIndex - 1);
            quickSortStep(data, pivotIndex + 1, right);
        }
    }

    public int partition(int[] data, int left, int right) {
        int pivot = data[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (data[j] < pivot) {
                i++;
                int temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }

        int temp = data[i + 1];
        data[i + 1] = data[right];
        data[right] = temp;

        return i + 1;
    }

    private void startSorting(String type) {
        System.out.println(type);
        if(type.equals("Selection sort")){
            selection();
        }
        if(type.equals("Bubble sort")){
            bubble();
        }
        if(type.equals("Insertion sort")){
            insertion();
        }
        if(type.equals("Quick sort")){
            quickSortWithTimer();
        }



    }

    public static void main(String[] args) {

        //USE EDT to ensure smmoth end user experience
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SortVisualizer visualizer = new SortVisualizer();

                visualizer.setVisible(true);
            }
        });
    }
}