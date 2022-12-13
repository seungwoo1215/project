package snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame {
    JFrame frame;

    //고정값
    final int SCORE_PANEL_WIDTH = 200;
    final int FRAME_WIDTH = 120;
    final int FRAME_HEIGHT = 80;
    final int size = 10;

   
    int speed;
    int x;
    int y;
    int xDirection;
    int yDirection;
    
    
    int fooda;
    int foodb;
    int food1;
    int food2;
    int foodX;
    int foodY;
    int score;
    
   
    ArrayList<Madi> snake = new ArrayList<>();
 

    public SnakeGame() {
        setupUI();
        reset();
    }

    void setupUI() {
        frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamepanel = new GamePanel();
      frame.getContentPane().add(gamepanel);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (yDirection == 0) {
                            up();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (yDirection == 0) {
                            down();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (xDirection == 0) {
                            left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (xDirection == 0) {
                            right();
                        }
                        break;
                    case KeyEvent.VK_F5:
                        reset();
                        break;
                }
            }
        });
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final int w = FRAME_WIDTH * size;
        final int h = FRAME_HEIGHT * size;
        frame.setSize(w, h);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( (d.width-w)/2, (d.height-h)/2 );

        frame.setResizable(false);
        frame.setVisible(true);
    }

    void reset() {
        x = 10;
        y = 10;
        xDirection = 1;
        yDirection = 0;
        food1 = 0;
        food2 = 0;
        foodX = 0;
        foodY = 0;
        fooda = 0;
        foodb = 0;
        speed = 100;
        score = 0;
        
        snake.clear();
        snake.add(new Madi(x, y));
        snake.add(new Madi(x-1, y));
        snake.add(new Madi(x-2, y));
        snake.add(new Madi(x-3, y));
        foodX = (int) (Math.random() * (frame.getWidth()-SCORE_PANEL_WIDTH));
        foodY = (int) (Math.random() * FRAME_HEIGHT);
        fooda = (int) (Math.random() * (frame.getWidth()-SCORE_PANEL_WIDTH));
        foodb = (int) (Math.random() * FRAME_HEIGHT);
        food1 = (int) (Math.random() * (frame.getWidth()-SCORE_PANEL_WIDTH));
        food2 = (int) (Math.random() * FRAME_HEIGHT);
        correctFoodLoc();
    }

    private void up() {
        xDirection = 0;
        yDirection = -1;
    }

    private void down() {
        xDirection = 0;
        yDirection = 1;
    }

    private void left() {
        xDirection = -1;
        yDirection = 0;
    }

    private void right() {
        xDirection = 1;
        yDirection = 0;
    }
    public void play() {
        while (true) {

            x += xDirection;
            y += yDirection;

            int length = snake.size();
            int lastX = snake.get(length - 1).getX();
            int lasta = snake.get(length - 1).getX();
            int last1 = snake.get(length - 1).getX();
            int lastb = snake.get(length - 1).getY();
            int lastY = snake.get(length - 1).getY();
            int last2 = snake.get(length - 1).getY();
            for (int i = length - 1; i > 0; i--) {
                snake.get(i).setX(snake.get(i - 1).getX());
                snake.get(i).setY(snake.get(i - 1).getY());
            }
            snake.get(0).setX(snake.get(0).getX() + xDirection);
            snake.get(0).setY(snake.get(0).getY() + yDirection);


            if ((snake.get(0).getX() * size == foodX) && (snake.get(0).getY() * size == foodY)) {
                snake.add(new Madi(lastX, lastY));
                foodX = (int) (Math.random() * (frame.getWidth()-SCORE_PANEL_WIDTH));
                foodY = (int) (Math.random() * FRAME_HEIGHT) - size;       
                score += 50;
            }
            if ((snake.get(0).getX() * size == fooda) && (snake.get(0).getY() * size == foodb)) {
               
            	speed +=20;
                fooda = (int) (Math.random() * (frame.getWidth()-SCORE_PANEL_WIDTH));
                foodb = (int) (Math.random() * FRAME_HEIGHT) - size; 
                score -= 10;
            }
            if ((snake.get(0).getX() * size == food1) && (snake.get(0).getY() * size == food2)) {
                
             	 speed -=20;
                 food1 = (int) (Math.random() * (frame.getWidth()-SCORE_PANEL_WIDTH));
                 food2 = (int) (Math.random() * FRAME_HEIGHT) - size; 
                 score += 30;
             }
            frame.repaint();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int snakeSize = snake.size();

            if (y == 0) {
                if (snakeSize > 3) {
                    snake.remove(snakeSize-1);
                }
                down();
            }
            else if (y == FRAME_HEIGHT-5) {
                if (snakeSize > 3) {
                    snake.remove(snakeSize-1);
                }
                up();
            }
            else if (x == 0) {
                if (snakeSize > 3) {
                    snake.remove(snakeSize-1);
                }
                right();
            }
            else if (xDirection == 1 && snake.get(0).getX() * size == frame.getWidth()-SCORE_PANEL_WIDTH-size) {
                if (snakeSize > 3) {
                    snake.remove(snakeSize-1);
                }
                left();
            }
        }
    }
    void correctFoodLoc() {// 먹이가 경계를 벗어나지 않는 위치에 그려지도록 보정하는 코드
        if (foodX % size > 0) {            
            foodX = foodX - (foodX % size);            
        }
        if (foodY % size > 0) {
            foodY = foodY - (foodY % size);
        }
        if (foodX <= 0) {
            foodX = size;
        }
        if (foodY <= 0) {
            foodY = size;
        }
        if (foodX > frame.getWidth()-SCORE_PANEL_WIDTH) {
            foodX = frame.getWidth()-SCORE_PANEL_WIDTH - size;
        }
        if (foodY > frame.getHeight()) {
            foodY = frame.getHeight() - size;
        }
        if (fooda % size > 0) {
          fooda = fooda - (fooda % size);
      }
      if (foodb % size > 0) {
          foodb = foodb - (foodb % size);
      }
      if (fooda <= 0) {
          fooda = size;
      }
      if (foodb <= 0) {
          foodb = size;
      }
      if (fooda > frame.getWidth()-SCORE_PANEL_WIDTH) {
          fooda = frame.getWidth()-SCORE_PANEL_WIDTH - size;
      }
      if (foodb > frame.getHeight()) {
          foodb = frame.getHeight() - size;
      }
    
    if (food1 % size > 0) {
      food1 = food1 - (food1 % size);
  }
  if (food2 % size > 0) {
      food2 = food2 - (food2 % size);
  }
  if (food1 <= 0) {
      food1 = size;
  }
  if (food2 <= 0) {
      food2 = size;
  }
  if (food1 > frame.getWidth()-SCORE_PANEL_WIDTH) {
      food1 = frame.getWidth()-SCORE_PANEL_WIDTH - size;
  }
  if (food2 > frame.getHeight()) {
      food2 = frame.getHeight() - size;
  }
}
   

    

    final Font fontTitle = new Font("Comic Sans", Font.BOLD,20);
    final BasicStroke defaultStroke = new BasicStroke(1);
    final BasicStroke thickerStroke = new BasicStroke(3); // 굵은 붓

    private class GamePanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon image = new  ImageIcon("./image/jandi.jpg");
            Image img = image.getImage();
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),null);

            int count = 0;
            for (Madi madi : snake) {
            	
                if (count == 0) {
                    if (snake.size() == 3) {//뱀의 길이가 3이면 머리는 그린
                        g.setColor(Color.green);
                    }
                    else if (snake.size() <= 5) {// 뱀의 길이가 4 ~ 5 이면 머리는 자줏빛
                        g.setColor(Color.MAGENTA);
                    }
                    else if (snake.size() <= 8) {// 뱀의 길이가 6 ~ 8 이면 머리는 노랑
                        g.setColor(Color.yellow);
                    }
                    else if (snake.size() <= 11) {// 뱀의 길이가 9 ~ 11 이면 머리는 빨강
                    	g.setColor(Color.red);
                    }                       
                    else if (snake.size() >= 12) {// 슈퍼모드 뱀의 길이가 12 이상이면 머리는 화이트 + 몸 전체 빨강
                    	
                        g.setColor(Color.white);
                        ((Graphics2D)g).setStroke(thickerStroke);                                                                                   
                    } 
                    g.fillRect(madi.getX() * size, madi.getY() * size, size, size);
                    g.setColor(Color.red);
                    g.drawRect(madi.getX() * size, madi.getY() * size, size, size);
                }
                else {
                	if(snake.size() >= 12) {
                		g.setColor(Color.red);
                		g.fillRect(madi.getX() * size, madi.getY() * size, size, size);
                        g.setColor(Color.red);
                        g.drawRect(madi.getX() * size, madi.getY() * size, size, size);
                	}
                	else {
                    ((Graphics2D)g).setStroke(defaultStroke);
                    g.setColor(Color.black);
                    g.fillRect(madi.getX() * size, madi.getY() * size, size, size);
                    g.setColor(Color.red);
                    g.drawRect(madi.getX() * size, madi.getY() * size, size, size);
                }
                }  
                	count++;             
            }
            ((Graphics2D)g).setStroke(defaultStroke);

            correctFoodLoc();
            g.setColor(Color.blue);
            g.fillOval(foodX, foodY, size, size);
            
            g.setColor(Color.yellow);
            g.fillRect(fooda, foodb, size, size);
            
            g.setColor(Color.red);
            g.fillRect(food1, food2, size, size);
            

            g.setColor(Color.black);
            g.fillRect(getSize().width - SCORE_PANEL_WIDTH + size, 0, SCORE_PANEL_WIDTH, getSize().height);

            g.setColor(Color.red);
            g.setFont(fontTitle);
            g.drawString(score < 10? "Score:     " + score :
                         score < 100? "Score:    " + score :
                         "Score:   " + score, getSize().width - 170, 100);

            g.drawString("Size: " + snake.size(), getSize().width - 170, 150);//길이도 표시

            g.drawString("F5 : Restart", getSize().width - 170, 250);
        }
    }
}