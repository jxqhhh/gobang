import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GoBang {
	private static int BOARD_SIZE = 15;
	private String[][] board = new String[BOARD_SIZE][BOARD_SIZE];// 用于存储落子情况
	private Random rand = new Random();
	private final int TABLE_WIDTH = 535;
	private final int TABLE_HEIGHT = 536;
	private final int RATE = TABLE_WIDTH / BOARD_SIZE;
	private final int X_OFFSET = 5;
	private final int Y_OFFSET = 6;
	BufferedImage boardImage;
	BufferedImage black;
	BufferedImage white;
	BufferedImage selected;
	ChessBoard myBoard = new ChessBoard();
	MyListener1 listener1 = new MyListener1();
	MyListener2 listener2 = new MyListener2();
	JFrame f = new JFrame();
	int X_prev_black;
	int Y_prev_black;
	int X_prev_white;
	int Y_prev_white;
	int selected_x = -1;
	int selected_y = -1;
	int isLose;// 用于记录比赛结果，为1时表示玩家胜利，为2时表示电脑胜利

	public void initBoard() {
		try {
			boardImage = ImageIO.read(new File("Image//board.jpg"));
			black = ImageIO.read(new File("Image//black.gif"));
			white = ImageIO.read(new File("Image//white.gif"));
			selected = ImageIO.read(new File("Image//selected.gif"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = "";
			}
		}
		myBoard.addMouseListener(listener1);
		myBoard.addMouseMotionListener(listener2);
		myBoard.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		f.add(myBoard);
		f.pack();
		f.setVisible(true);
	}

	/**
	 * 电脑随机落子
	 */
	public void randomProcess() {
		int randomX;
		int randomY;
		while (true) {
			randomX = rand.nextInt(BOARD_SIZE);
			randomY = rand.nextInt(BOARD_SIZE);
			if (board[randomX][randomY].equals("")) {
				board[randomX][randomY] = "○";
				X_prev_white = randomX;
				Y_prev_white = randomY;
				break;
			}
		}
	}

	/**
	 * 判定胜负
	 */
	public boolean determine(int x_pos, int y_pos) {
		String dest = board[x_pos][y_pos];
		// 下列五个try——catch块判断水平方向是否有五个连子
		try {
			if (board[x_pos + 1][y_pos].equals(dest) && board[x_pos + 2][y_pos].equals(dest)
					&& board[x_pos + 3][y_pos].equals(dest) && board[x_pos + 4][y_pos].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos + 1][y_pos].equals(dest) && board[x_pos + 2][y_pos].equals(dest)
					&& board[x_pos + 3][y_pos].equals(dest) && board[x_pos - 1][y_pos].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos + 1][y_pos].equals(dest) && board[x_pos + 2][y_pos].equals(dest)
					&& board[x_pos - 1][y_pos].equals(dest) && board[x_pos - 2][y_pos].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos + 1][y_pos].equals(dest) && board[x_pos - 1][y_pos].equals(dest)
					&& board[x_pos - 2][y_pos].equals(dest) && board[x_pos - 3][y_pos].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos - 1][y_pos].equals(dest) && board[x_pos - 2][y_pos].equals(dest)
					&& board[x_pos - 3][y_pos].equals(dest) && board[x_pos - 4][y_pos].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 下列五个try——catch块判断垂直方向是否有五个连子
		try {
			if (board[x_pos][y_pos + 1].equals(dest) && board[x_pos][y_pos + 2].equals(dest)
					&& board[x_pos][y_pos + 3].equals(dest) && board[x_pos][y_pos + 4].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos][y_pos + 1].equals(dest) && board[x_pos][y_pos + 2].equals(dest)
					&& board[x_pos][y_pos + 3].equals(dest) && board[x_pos][y_pos - 1].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos][y_pos + 1].equals(dest) && board[x_pos][y_pos + 2].equals(dest)
					&& board[x_pos][y_pos - 1].equals(dest) && board[x_pos][y_pos - 2].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos][y_pos + 1].equals(dest) && board[x_pos][y_pos - 1].equals(dest)
					&& board[x_pos][y_pos - 2].equals(dest) && board[x_pos][y_pos - 3].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos][y_pos - 1].equals(dest) && board[x_pos][y_pos - 2].equals(dest)
					&& board[x_pos][y_pos - 3].equals(dest) && board[x_pos][y_pos - 4].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 下列五个try——catch块判断右下（左上）方向是否有五个连子
		try {
			if (board[x_pos + 1][y_pos + 1].equals(dest) && board[x_pos + 2][y_pos + 2].equals(dest)
					&& board[x_pos + 3][y_pos + 3].equals(dest) && board[x_pos + 4][y_pos + 4].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos + 1][y_pos + 1].equals(dest) && board[x_pos + 2][y_pos + 2].equals(dest)
					&& board[x_pos + 3][y_pos + 3].equals(dest) && board[x_pos - 1][y_pos - 1].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos + 1][y_pos + 1].equals(dest) && board[x_pos + 2][y_pos + 2].equals(dest)
					&& board[x_pos - 1][y_pos - 1].equals(dest) && board[x_pos - 2][y_pos - 2].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos + 1][y_pos + 1].equals(dest) && board[x_pos - 1][y_pos - 1].equals(dest)
					&& board[x_pos - 2][y_pos - 2].equals(dest) && board[x_pos - 3][y_pos - 3].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos - 1][y_pos - 1].equals(dest) && board[x_pos - 2][y_pos - 2].equals(dest)
					&& board[x_pos - 3][y_pos - 3].equals(dest) && board[x_pos - 4][y_pos - 4].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 下列五个try——catch块判断左下（右上）方向是否有五个连子
		try {
			if (board[x_pos - 1][y_pos + 1].equals(dest) && board[x_pos - 2][y_pos + 2].equals(dest)
					&& board[x_pos - 3][y_pos + 3].equals(dest) && board[x_pos - 4][y_pos + 4].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos - 1][y_pos + 1].equals(dest) && board[x_pos - 2][y_pos + 2].equals(dest)
					&& board[x_pos - 3][y_pos + 3].equals(dest) && board[x_pos + 1][y_pos - 1].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos - 1][y_pos + 1].equals(dest) && board[x_pos - 2][y_pos + 2].equals(dest)
					&& board[x_pos + 1][y_pos - 1].equals(dest) && board[x_pos + 2][y_pos - 2].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos - 1][y_pos + 1].equals(dest) && board[x_pos + 1][y_pos - 1].equals(dest)
					&& board[x_pos + 2][y_pos - 2].equals(dest) && board[x_pos + 3][y_pos - 3].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (board[x_pos + 1][y_pos - 1].equals(dest) && board[x_pos + 2][y_pos - 2].equals(dest)
					&& board[x_pos + 3][y_pos - 3].equals(dest) && board[x_pos + 4][y_pos - 4].equals(dest)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		GoBang gobang = new GoBang();
		gobang.initBoard();
	}

	class ChessBoard extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(boardImage, 0, 0, null);
			if (selected_x >= 0 && selected_y >= 0 && selected_x < 15 && selected_y < 15) {
				g.drawImage(selected, selected_x * RATE + X_OFFSET, selected_y * RATE + Y_OFFSET, null);
			}
			for (int i = 0; i < BOARD_SIZE; i++) {
				for (int j = 0; j < BOARD_SIZE; j++) {
					if (board[i][j].equals("●")) {
						g.drawImage(black, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
					if (board[i][j].equals("○")) {
						g.drawImage(white, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
				}
			}
		}
	}

	class MyListener1 extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int posx = e.getX();
			int posy = e.getY();
			int x_num = (int) (posx - X_OFFSET) / RATE;
			int y_num = (int) (posy - Y_OFFSET) / RATE;
			if (board[x_num][y_num].equals("")) {
				board[x_num][y_num] = "●";
				X_prev_black = x_num;
				Y_prev_black = y_num;
				randomProcess();
				myBoard.repaint();

				if (determine(X_prev_black, Y_prev_black)) {
					isLose = 1;
					new ResultDialog().init();
				}
				if (determine(X_prev_white, Y_prev_white)) {
					isLose = 2;
				}
			}
		}

		public void mouseExited(MouseEvent e) {
			selected_x = -1;
			selected_y = -1;
			myBoard.repaint();
		}
	}

	class MyListener2 extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {
			selected_x = (e.getX() - X_OFFSET) / RATE;
			selected_y = (e.getY() - Y_OFFSET) / RATE;
			myBoard.repaint();
		}
	}

	class ResultDialog extends JDialog {
		JButton j1 = new JButton("再来一局");
		JButton j2 = new JButton("退出游戏");

		public void init() {
			ResultDialog.this.setLayout(new FlowLayout());
			ResultDialog.this.add(j1);
			ResultDialog.this.add(j2);
			ResultDialog.this.pack();
			ResultDialog.this.setVisible(true);
			j1.addActionListener((ActionListener) new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ResultDialog.this.dispose();
					new GoBang().initBoard();
				}
			});
			j2.addActionListener((ActionListener) new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
	}
}
