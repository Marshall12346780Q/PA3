import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class TicTacToe {

	public JButton[] buttons = new JButton[100];
	protected JButton reset = new JButton();
	protected JButton returnToMenu = new JButton();
	private JFrame frame = new JFrame();
	private JPanel topPane = new JPanel();
	private JPanel bottomPane = new JPanel();
	protected JLabel textfield = new JLabel();
	private JLabel timerlabel = new JLabel("Time Remaining This Turn: 15");
	private int time = 15;
    private boolean p1flag;

    // New variables for version 2
    private int m;
    private int n;
    private int k;



    public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe window = null;
					window.frame.setVisible(false);
				} catch (NullPointerException e) {
					System.out.println("");
				}
			}
		});
	}

	public TicTacToe(int m, int n, int k) {
		this.m = m;
		this.n = n;
		this.k = k;
		initialize();
	}

	public void initialize() {


		System.out.println(m + " " + n + " " + k + " " + Menu.aiorpvp);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500,500);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);

		topPane.add(textfield);
		topPane.add(reset);
		
		reset.setText("RESET");
		returnToMenu.setText("Return to Menu");
		textfield.setText("X's Turn");
		topPane.add(timerlabel);
		bottomPane.setLayout(new GridLayout(m,n));
		frame.add(topPane,BorderLayout.NORTH);
		frame.add(bottomPane);

		final Timer t = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {//if reset is clicked
				time--;
				timerlabel.setText("Time Remaining This Turn: " + String.valueOf(time));
				if (time == 0) {
					final Timer timer = (Timer) e.getSource(); //?
					timer.stop();
					//check for whose turn it was, set other as winner
					if(p1flag)
					{
						timerlabel.setText("O'S TURN TIME EXPIRED: X WINS");
					}
					else
					{
						timerlabel.setText("X'S TURN TIME EXPIRED: O WINS");
					}
					endGame();

				}
			}
		});
		for(int i=0;i<m*n;i++) {
			buttons[i] = new JButton();
			bottomPane.add(buttons[i]);		
			int fontsize = scaleButton();
			buttons[i].setFont(new Font("Sans-Serif" ,Font.BOLD,fontsize)); //make function for just last varibale
			buttons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<m*n;i++) {
						if(e.getSource()==buttons[i]) {
							time = 15; //reset move timer
							if(p1flag) {
								if(buttons[i].getText()=="") {
									buttons[i].setText("O");
									p1flag=false;
									textfield.setText("X's turn");
									boolean check = checkForWinner(i,"O");
									if (check)
									{
										textfield.setText("O WINS");
										endGame();
										t.stop();	
									}
								}
							}
							else {
								if(buttons[i].getText()=="") {
									buttons[i].setText("X");
									p1flag=true;
									textfield.setText("O's turn");
									boolean check = checkForWinner(i,"X");
									if (check)
									{
										textfield.setText("X WINS");
										endGame();
										t.stop();	
									}

								}
							}
						}			
					}
				}
			});

		}

		reset.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				TicTacToe t = new TicTacToe(m, n, k);
			}
		});		
		returnToMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Main.main(null);
			}
		});

		topPane.add(textfield);
		topPane.add(reset);
		topPane.add(returnToMenu);
		t.start();

	}


	protected boolean checkForWinner(int index, String s) //int index s is X or O
	{
		//Checking for solution involves checking for horizontal, vertical, or diagonal k-tuples
		//If a row end, column end, or opponent/empty square is encountered, searching stops in that direction
		//A count is kept for each of the three types. If it reaches k, a win is found
		int leftEnd = (index / m) * m;
		int rightEnd = ((index / m + 1) * m )-1;
		int kcount = 1; //contains tuple size, starts from 1 to include index

		//HORIZONTAL CHECKING
		for(int h = index-1; h >= leftEnd; h--) //checks for tuple going left
		{
			if(buttons[h].getText().equals(s)){kcount++;}
			else{break;	}
		}
		for(int h = index+1; h <= rightEnd; h++) //checks for tuple going right
		{
			if(buttons[h].getText().equals(s)){kcount++;}
			else{break;}
		}
		if(kcount == k) {return true;}
		kcount = 1;
		//VERTICAL CHECKING increments by m
		int topEnd = 0;
		int bottomEnd = m*n;
		for(int v = index-m; v > topEnd; v = v-m) //up
		{
			if(buttons[v].getText().equals(s)){kcount++;}
			else{break;}
		}
		for(int v = index+m; v < bottomEnd; v = v+m) //down
		{
			if(buttons[v].getText().equals(s)){kcount++;}
			else{break;}
		}
		if(kcount == k) {return true;}
		kcount = 0;
		//DIAGONAL CHECKING increments by m-1 and m+1, and stops on hitting a horizontal or vertical end
		//down left and up right share count 
		for(int d = index; (d < (bottomEnd)); d = d+(m-1)) //down left
		{
			if(buttons[d].getText().equals(s))
			{
				kcount++;
				if(isMultiple(d, m, 0))  //break if it is a multiple, indicating left side
				{
					break;
				}

			}
			else{break;}
		}
		kcount--; //the loop must check the index in case it is at a side, but the index character should not be double counted
		for(int d = index; (d >= topEnd); d = d-(m-1)) //up right
		{
			if(buttons[d].getText().equals(s))
			{
				kcount++;
				if(isMultiple(d, m, 1)) //indicates right side
				{
					break;
				}
			}
			else{break;}
		}
		if(kcount == k) {return true;}
		kcount = 0;
		//up left and down right share count
		for(int d = index; (d >= topEnd); d = d-(m+1)) //up left
		{
			if(buttons[d].getText().equals(s))
			{
				kcount++;
				if(isMultiple(d,m,0)) //indicates left side
				{
					break;
				}
			}
			else{break;}
		}
		kcount--; //prevent double counting
		for(int d = index; (d < bottomEnd); d = d+(m+1)) //down right
		{
			if(buttons[d].getText().equals(s))
			{
				kcount++;
				if(isMultiple(d,m,1)) //indicates right side
				{
					break;
				}
			}
			else{break;}
		}
		//System.out.println("kcount: " + kcount);
		if(kcount == k) {return true;}

		return false;
	}


	private boolean isMultiple(int val, int increment, int offset) //returns true if val + offset is a multiple of increment
	{
		for(int i = 0; i < (m*n); i = i+increment)
		{
			if((val + offset) == i)
			{
				return true;
			}
		}
		return false;
	}

	private void endGame()
	{
		for(int i = 0; i < m*n; i++) {
			buttons[i].setEnabled(false);
		}
	}
	
	private int scaleButton() 
	{
		if (m <= 3 && n <= 3) {
			return 120;
		}
		else if (m <= 5 && n <= 5) {
			return 60;
		}
		else if (m <= 7 && n <= 7) {
			return 30;
		}
		else {
			return 12;
		}
		
	}


}