package com.company;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import java.awt.event.MouseListener;
import javax.swing.JMenuBar;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBoxMenuItem;

public class Main extends JPanel implements MouseListener {
    static int fontX = 10;
    static int fontY = 375;
    static int[][] startNumPoint = new int[8][8];
    static int startPoint = 8;
    static int numBRB = 0;
    static int timeE = 0;

    static int move = 2;
    static int black = 0;
    static int white = 0;
    static int grey = 0;

    static boolean noOneWin = false;
    static JPanel jPPPPanel = new JPanel();

    public static void main(String[] args) {
        start();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }


    public Main() {                                                                          // Windows
        JFrame jFFFFrame = new JFrame();
        jFFFFrame.setTitle("РЕВЕРСИ 2.0");
        jFFFFrame.setLocationRelativeTo(null);
        jFFFFrame.setLocation(450, 200);
        jPPPPanel.setPreferredSize(new Dimension(481, 505));
        jFFFFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFFFFrame.setResizable(false);

        JMenuBar menuBBBBar = new JMenuBar();
        JMenu file = new JMenu("Файл");
        JMenuItem newGame = new JMenuItem("Новая игра");
        JCheckBoxMenuItem orderMenu = new JCheckBoxMenuItem("Показ возможных ходов");
        JMenuItem exitGame = new JMenuItem("Закрыть приложение ");
        menuBBBBar.add(file);

        file.add(newGame);
        file.add(orderMenu);
        orderMenu.setSelected(true);
        file.addSeparator();
        file.add(exitGame);

        JMenu gameSize = new JMenu("Размер поля ");
        JMenuItem x8 = new JMenuItem("8X8");

        menuBBBBar.add(gameSize);
        // gameSize.add(x4);
        gameSize.add(x8);

        newGame.addActionListener(e -> {
            startNumPoint = new int[startPoint][startPoint];
            move = 2;
            start();
            schetchik();
            jPPPPanel.repaint();
        });
        orderMenu.addActionListener(e -> jPPPPanel.repaint());
        exitGame.addActionListener(e -> System.exit(0));

        x8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startPoint = 8;
                fontX = 10;
                fontY = 498;
                jFFFFrame.setLocation(390, 80);
                jFFFFrame.setPreferredSize(new Dimension(481, 505));
                jFFFFrame.setSize(487, 557);
                startNumPoint = new int[8][8];
                move = 2;
                start();
                schetchik();
                jPPPPanel.repaint();
            }

        });

        jPPPPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < startPoint; i++)
                    for (int j = 0; j < startPoint; j++) {
                        g.setColor(new Color(67, 53, 82));
                        g.fillRect(j * 60, i * 60, 60, 60);
                        g.setColor(Color.black);
                        g.drawRect(j * 60, i * 60, 60, 60);
                    }
                for (int i = 0; i < startNumPoint.length; i++) {
                    for (int j = 0; j < startNumPoint[i].length; j++) {
                        switch (startNumPoint[i][j]) {
                            case 0:
                                break;

                            case 1:
                                g.setColor(Color.BLACK);
                                g.fillOval(5 + i * 60, 5 + j * 60, 50, 50);
                                break;
                            case 2:
                                g.setColor(Color.WHITE);
                                g.fillOval(5 + i * 60, 5 + j * 60, 50, 50);
                                break;
                            case -1:
                                if (orderMenu.getState()) {
                                    g.setColor(Color.GRAY);
                                    g.fillOval(20 + i * 60, 20 + j * 60, 10, 10);
                                }
                                break;

                        }
                    }
                }
                g.setColor(Color.BLACK);
                g.setFont(new Font("Новая задача", Font.BOLD, 15));
                if (numBRB == 0) {
                    if (black > white) {
                        g.drawString("Черные победили         Ч= " + black + "   Б= " + white, fontX, fontY);
                    } else if (black == white || noOneWin) {
                        g.drawString("Никто не победил        Ч= " + black + "   Б= " + white, fontX, fontY);
                    } else {
                        g.drawString("Белые победили          Ч= " + black + "   Б= " + white, fontX, fontY);
                    }
                } else {
                    if (move == 1) {
                        g.drawString("Черные Ходят          Ч= " + black + "  Б= " + white, fontX, fontY);
                    } else {
                        g.drawString("Белые Ходят           Ч= " + black + "  Б= " + white, fontX, fontY);
                    }
                }
            }

            @Override                                                               // DONT REWRITE
            public Dimension getPreferredSize() {                                   // фикс поля
                return new Dimension(481, 505);
            }
        };

        jFFFFrame.add(jPPPPanel);
        jFFFFrame.setJMenuBar(menuBBBBar);
        jPPPPanel.addMouseListener(this);
        jFFFFrame.pack();
        jFFFFrame.setVisible(true);                                                   //новый кард после добавления всех компонентов

    }


    static void start() {                                                            //старт(как ни странно)
        startNumPoint[(startPoint / 2) - 1][(startPoint / 2) - 1] = 1;
        startNumPoint[startPoint / 2][(startPoint / 2) - 1] = 2;
        startNumPoint[(startPoint / 2) - 1][startPoint / 2] = 2;
        startNumPoint[startPoint / 2][startPoint / 2] = 1;
        orderToMove();
        schetchik();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {                                   //че делать после щелчка
        int x, y, i = 0, j = 0;
        x = arg0.getX();
        y = arg0.getY();
        i = x / 60;
        j = y / 60;
        if (accept(i, j)) {
            if (move == 1) {
                startNumPoint[i][j] = move;
                moveAfterMove(i, j);
                move = 2;
            } else {
                startNumPoint[i][j] = move;
                moveAfterMove(i, j);
                move = 1;
            }
            orderToMove();

            jPPPPanel.repaint();
        }
    }

    boolean accept(int i, int j) {                                        // проверка принятия поля
        if (i < startPoint && j < startPoint) {
            if (startNumPoint[i][j] == -1) {
                return true;
            }
        }
        return false;
    }

    static void orderToMove() {                                            // возможность выбора поля
        for (int i = 0; i < startNumPoint.length; i++) {
            for (int j = 0; j < startNumPoint[i].length; j++) {
                if (startNumPoint[i][j] == -1) {
                    startNumPoint[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < startNumPoint.length; i++) {
            for (int j = 0; j < startNumPoint[i].length; j++) {
                if (startNumPoint[i][j] == move) {
                    Chek(i, j);
                }
            }
        }
        schetchik();
        if (grey == 0 && numBRB != 0) {
            timeE++;
            if (move == 1) {
                move = 2;
            } else {
                move = 1;
            }
            if (timeE > 1) {
                noOneWin = true;
            }
            if (!noOneWin) {
                orderToMove();
            }
        } else {
            timeE = 0;
        }

    }

    static void Chek(int i, int j) {                                             // проверка возможного выбора поля
        int notTurn;
        int oI = i;
        int oJ = j;
        boolean done = false;
        if (move == 1) {
            notTurn = 2;
        } else {
            notTurn = 1;
        }

        //up
        while (i >= 0 && i < startPoint && j - 1 >= 0 && j - 1 < startPoint && !done) {
            if (startNumPoint[i][j - 1] == notTurn) {
                if (i >= 0 && i < startPoint && j - 2 >= 0 && j - 2 < startPoint) {
                    if (startNumPoint[i][j - 2] == 0) {
                        startNumPoint[i][j - 2] = -1;
                        done = true;
                    } else if (startNumPoint[i][j - 2] == notTurn) {
                        j = j - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        while (i + 1 >= 0 && i + 1 < startPoint && j - 1 >= 0 && j - 1 < startPoint && !done) {
            if (startNumPoint[i + 1][j - 1] == notTurn) {
                if (i + 2 >= 0 && i + 2 < startPoint && j - 2 >= 0 && j - 2 < startPoint) {
                    if (startNumPoint[i + 2][j - 2] == 0) {
                        startNumPoint[i + 2][j - 2] = -1;
                        done = true;
                    } else if (startNumPoint[i + 2][j - 2] == notTurn) {
                        j = j - 1;
                        i = i + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }
        i = oI;
        j = oJ;
        done = false;

        while (i + 1 >= 0 && i + 1 < startPoint && j >= 0 && j < startPoint && !done) {
            if (startNumPoint[i + 1][j] == notTurn) {
                if (i + 2 >= 0 && i + 2 < startPoint && j >= 0 && j < startPoint) {
                    if (startNumPoint[i + 2][j] == 0) {
                        startNumPoint[i + 2][j] = -1;
                        done = true;
                    } else if (startNumPoint[i + 2][j] == notTurn) {
                        i = i + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        while (i + 1 >= 0 && i + 1 < startPoint && j + 1 >= 0 && j + 1 < startPoint && !done) {
            if (startNumPoint[i + 1][j + 1] == notTurn) {
                if (i + 2 >= 0 && i + 2 < startPoint && j + 2 >= 0 && j + 2 < startPoint) {
                    if (startNumPoint[i + 2][j + 2] == 0) {
                        startNumPoint[i + 2][j + 2] = -1;
                        done = true;
                    } else if (startNumPoint[i + 2][j + 2] == notTurn) {
                        i = i + 1;
                        j = j + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        while (i >= 0 && i < startPoint && j + 1 >= 0 && j + 1 < startPoint && !done) {
            if (startNumPoint[i][j + 1] == notTurn) {
                if (i >= 0 && i < startPoint && j + 2 >= 0 && j + 2 < startPoint) {
                    if (startNumPoint[i][j + 2] == 0) {
                        startNumPoint[i][j + 2] = -1;
                        done = true;
                    } else if (startNumPoint[i][j + 2] == notTurn) {
                        j = j + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        while (i - 1 >= 0 && i - 1 < startPoint && j + 1 >= 0 && j + 1 < startPoint && !done) {
            if (startNumPoint[i - 1][j + 1] == notTurn) {
                if (i - 2 >= 0 && i - 2 < startPoint && j + 2 >= 0 && j + 2 < startPoint) {
                    if (startNumPoint[i - 2][j + 2] == 0) {
                        startNumPoint[i - 2][j + 2] = -1;
                        done = true;
                    } else if (startNumPoint[i - 2][j + 2] == notTurn) {
                        j = j + 1;
                        i = i - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        while (i - 1 >= 0 && i - 1 < startPoint && j >= 0 && j < startPoint && !done) {
            if (startNumPoint[i - 1][j] == notTurn) {
                if (i - 2 >= 0 && i - 2 < startPoint && j >= 0 && j < startPoint) {
                    if (startNumPoint[i - 2][j] == 0) {
                        startNumPoint[i - 2][j] = -1;
                        done = true;
                    } else if (startNumPoint[i - 2][j] == notTurn) {
                        i = i - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        while (i - 1 >= 0 && i - 1 < startPoint && j - 1 >= 0 && j - 1 < startPoint && !done) {
            if (startNumPoint[i - 1][j - 1] == notTurn) {
                if (i - 2 >= 0 && i - 2 < startPoint && j - 2 >= 0 && j - 2 < startPoint) {
                    if (startNumPoint[i - 2][j - 2] == 0) {
                        startNumPoint[i - 2][j - 2] = -1;
                        done = true;
                    } else if (startNumPoint[i - 2][j - 2] == notTurn) {
                        i = i - 1;
                        j = j - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

    }

    static void moveAfterMove(int i, int j) {                                        // Хотьба (изменение после перемещения)
        int dontMove;
        int timeI = i;
        int timeJ = j;
        boolean done = false;
        if (move == 1) {
            dontMove = 2;
        } else {
            dontMove = 1;
        }

        while (i >= 0 && i < startPoint && j - 1 >= 0 && j - 1 < startPoint && !done) {
            if (startNumPoint[i][j - 1] == dontMove) {
                if (i >= 0 && i < startPoint && j - 2 >= 0 && j - 2 < startPoint) {
                    if (startNumPoint[i][j - 2] == move) {
                        for (int k = j - 1; k <= timeJ; k++) {
                            startNumPoint[i][k] = move;
                            System.out.println("верз заполнить " + i + " " + k);
                        }
                        done = true;
                    } else if (startNumPoint[i][j - 2] == dontMove) {
                        j = j - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = timeI;
        j = timeJ;
        done = false;

        while (i + 1 >= 0 && i + 1 < startPoint && j - 1 >= 0 && j - 1 < startPoint && !done) {
            if (startNumPoint[i + 1][j - 1] == dontMove) {
                if (i + 2 >= 0 && i + 2 < startPoint && j - 2 >= 0 && j - 2 < startPoint) {
                    if (startNumPoint[i + 2][j - 2] == move) {
                        int m = i + 1;
                        for (int k = j - 1; k < timeJ; k++) {
                            System.out.println("верх-право" + m + " " + k);
                            startNumPoint[m][k] = move;
                            m--;
                        }
                        done = true;
                    } else if (startNumPoint[i + 2][j - 2] == dontMove) {
                        j = j - 1;
                        i = i + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }
        i = timeI;
        j = timeJ;
        done = false;

        while (i + 1 >= 0 && i + 1 < startPoint && j >= 0 && j < startPoint && !done) {
            if (startNumPoint[i + 1][j] == dontMove) {
                if (i + 2 >= 0 && i + 2 < startPoint && j >= 0 && j < startPoint) {
                    if (startNumPoint[i + 2][j] == move) {
                        for (int k = i + 1; k > timeI; k--) {
                            startNumPoint[k][j] = move;
                            System.out.println("право" + k + " " + j);
                        }
                        done = true;
                    } else if (startNumPoint[i + 2][j] == dontMove) {
                        i = i + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = timeI;
        j = timeJ;
        done = false;

        while (i + 1 >= 0 && i + 1 < startPoint && j + 1 >= 0 && j + 1 < startPoint && !done) {
            if (startNumPoint[i + 1][j + 1] == dontMove) {
                if (i + 2 >= 0 && i + 2 < startPoint && j + 2 >= 0 && j + 2 < startPoint) {
                    if (startNumPoint[i + 2][j + 2] == move) {
                        int m = i + 1;
                        for (int k = j + 1; k > timeJ; k--) {
                            System.out.println("вниз-право" + m + " " + k);
                            startNumPoint[m][k] = move;
                            m--;
                        }
                        done = true;
                    } else if (startNumPoint[i + 2][j + 2] == dontMove) {
                        i = i + 1;
                        j = j + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = timeI;
        j = timeJ;
        done = false;

        while (i >= 0 && i < startPoint && j + 1 >= 0 && j + 1 < startPoint && !done) {
            if (startNumPoint[i][j + 1] == dontMove) {
                if (i >= 0 && i < startPoint && j + 2 >= 0 && j + 2 < startPoint) {
                    if (startNumPoint[i][j + 2] == move) {
                        for (int k = j + 1; k > timeJ; k--) {
                            startNumPoint[i][k] = move;
                            System.out.println("вниз" + i + " " + k);
                        }
                        done = true;
                    } else if (startNumPoint[i][j + 2] == dontMove) {
                        j = j + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = timeI;
        j = timeJ;
        done = false;

        while (i - 1 >= 0 && i - 1 < startPoint && j + 1 >= 0 && j + 1 < startPoint && !done) {
            if (startNumPoint[i - 1][j + 1] == dontMove) {
                if (i - 2 >= 0 && i - 2 < startPoint && j + 2 >= 0 && j + 2 < startPoint) {
                    if (startNumPoint[i - 2][j + 2] == move) {
                        int m = i - 1;
                        for (int k = j + 1; k > timeJ; k--) {
                            System.out.println("вниз-лево" + m + " " + k);
                            startNumPoint[m][k] = move;
                            m++;
                        }
                        done = true;
                    } else if (startNumPoint[i - 2][j + 2] == dontMove) {
                        j = j + 1;
                        i = i - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = timeI;
        j = timeJ;
        done = false;


        while (i - 1 >= 0 && i - 1 < startPoint && j >= 0 && j < startPoint && !done) {
            if (startNumPoint[i - 1][j] == dontMove) {
                if (i - 2 >= 0 && i - 2 < startPoint && j >= 0 && j < startPoint) {
                    if (startNumPoint[i - 2][j] == move) {
                        for (int k = i - 2; k < timeI; k++) {
                            startNumPoint[k][j] = move;
                            System.out.println("вниз-лево" + k + " " + j);
                        }
                        done = true;
                    } else if (startNumPoint[i - 2][j] == dontMove) {
                        i = i - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = timeI;
        j = timeJ;
        done = false;

        while (i - 1 >= 0 && i - 1 < startPoint && j - 1 >= 0 && j - 1 < startPoint && !done) {
            if (startNumPoint[i - 1][j - 1] == dontMove) {
                if (i - 2 >= 0 && i - 2 < startPoint && j - 2 >= 0 && j - 2 < startPoint) {
                    if (startNumPoint[i - 2][j - 2] == move) {
                        int m = i - 1;
                        for (int k = j - 1; k < timeJ; k++) {
                            System.out.println("Вниз" + m + " " + k);
                            startNumPoint[m][k] = move;
                            m++;
                        }
                        done = true;
                    } else if (startNumPoint[i - 2][j - 2] == dontMove) {
                        i = i - 1;
                        j = j - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

    }

    static void schetchik() {
        black = 0;
        white = 0;
        numBRB = 0;
        grey = 0;
        for (int i = 0; i < startNumPoint.length; i++) {
            for (int j = 0; j < startNumPoint[i].length; j++) {
                if (startNumPoint[i][j] == 1) {
                    black++;
                }
                if (startNumPoint[i][j] == 2) {
                    white++;
                }
                if (startNumPoint[i][j] == 0) {
                    numBRB++;
                }
                if (startNumPoint[i][j] == -1) {
                    grey++;
                }
            }
        }

    }

    @Override                                                                           // НЕ ТРОГАТЬ
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }
}
class Something{
    static void Something() {
    }
}