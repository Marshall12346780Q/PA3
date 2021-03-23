import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;

public class AITicTacToe {

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
    private int winIndex = -1; //if not -1, a winning move has been found and is stored in this var for the AI to block
    private int winIndexAI = -1; //if not -1, a winning move for the AI has been found and the AI will play it to win if empty

    public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AITicTacToe window = null;
					window.frame.setVisible(false);
				} catch (NullPointerException e) {
					System.out.println("");
				}
			}
		});
	}

	public AITicTacToe(int m, int n, int k) {
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
							if(buttons[i].getText()=="") 
							{
								buttons[i].setText("X");
								p1flag=true;
								textfield.setText("X's turn");
								boolean check = checkForWinner(i,"X", true);
								if (check)
								{
									textfield.setText("X WINS");
									endGame();
									t.stop();	
								}
								else
								{	
									if(checkForWinner(AIMove(), "O", false))
									{
										textfield.setText("O (Computer) WINS");
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
				AITicTacToe t = new AITicTacToe(m, n, k);
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

	/*
	 * If the winIndexAI is not -1, play it to win. 
	 * If the winIndex is not -1, play it to block the player's win
	 * Otherwise (first move) play a random empty square
	 * Run checkForWinner() with AI flag
	 * It's more efficient to store and check for played moves rather than check the whole array for empties
	 * 
	 * next version: run checkforWInner with a k of 1 more than ai's kcount to find next move
	 */
	private int AIMove()
	{
		int moveIndex = -1;
		if(winIndexAI != -1)
		{
			moveIndex = winIndexAI;
		}
		else if(winIndex != -1)
		{
			moveIndex = winIndex;
		}
		else //random between 0 and m*n, if != "" try again
		{
			Random randMove = new Random();
			moveIndex = randMove.nextInt((m*n));
			while(!(buttons[moveIndex].getText().equals("")))
			{
				moveIndex = randMove.nextInt((m*n));
			}
		}
		System.out.println("O Moves: " + moveIndex);
		buttons[moveIndex].setText("O");
		return moveIndex;
		
	}
	
	//needs a boolean parameter to indicate AI or player
	protected boolean checkForWinner(int index, String s, boolean player) //int index s is X or O
	{
		//winIndex = -1;   //	DELET THOS
		System.out.println(s+ "turn");
		//Checking for solution involves checking for horizontal, vertical, or diagonal k-tuples
		//If a row end, column end, or opponent/empty square is encountered, searching stops in that direction
		//A count is kept for each of the three types. If it reaches k, a win is found
		
		//If it reaches k-1, this indicates the player is 1 move away from winning.
		//The index of this winning move is saved so the AI knows to block it with it's turn
		//This creates a naive AI that prevents k-1 tuples from becoming k tuples 
		
		int leftEnd = (index / m) * m;
		int rightEnd = ((index / m + 1) * m )-1;
		int kcount = 1; //contains tuple size, starts from 1 to include index
		int openSquare = -1;
		//HORIZONTAL CHECKING
		for(int h = index-1; h >= leftEnd; h--) //checks for tuple going left
		{
			if(buttons[h].getText().equals(s))
			{
				kcount++;
			} 
			else if (buttons[h].getText().equals(""))
			{
				openSquare = h; //indicate there is a potential winning move now in openSquare
				break;
			}
			else{break;}
		}
		for(int h = index+1; h <= rightEnd; h++) //checks for tuple going right
		{
			if(buttons[h].getText().equals(s))
			{
				kcount++;
			}
			else if (buttons[h].getText().equals(""))
			{
				if(kcount == (k-1)) //now kcount may be properly checked
				{
					openSquare = h;
				}
				break;
			}
			else{break;}
		}
		if(kcount == (k-1)&&(openSquare != -1))
		{
			if(player == true)
			{
				winIndex = openSquare;
			}
			else if (player == false)
			{
				winIndexAI = openSquare;
			}
		}
		if(kcount == k) {return true;}
		
		//VERTICAL CHECKING increments by m
		kcount = 1;
		openSquare = -1;
		int topEnd = 0;
		int bottomEnd = m*n;
		for(int v = index-m; v >= topEnd; v = v-m) //up
		{
			if(buttons[v].getText().equals(s)){kcount++;}
			else if (buttons[v].getText().equals(""))
			{
				openSquare = v; 
				break;
			}
			else{break;}
		}
		for(int v = index+m; v < bottomEnd; v = v+m) //down
		{
			if(buttons[v].getText().equals(s)){kcount++;}
			else if (buttons[v].getText().equals(""))
			{
				if(kcount == (k-1)) //now kcount may be properly checked
				{
					openSquare = v;
				}
				break;
			}
			else{break;}
		}
		if(kcount == (k-1)&&(openSquare != -1))
		{
			if(player == true)
			{
				winIndex = openSquare;
			}
			else if (player == false)
			{
				winIndexAI = openSquare;
			}
		}
		if(kcount == k) {return true;}
		
		//DIAGONAL CHECKING increments by m-1 and m+1, and stops on hitting a horizontal or vertical end
		//down left and up right share count 
		kcount = 0;
		openSquare = -1;
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
			else if (buttons[d].getText().equals("")) 
			{
				openSquare = d; 
				break; //must always break on a "" square
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
			else if (buttons[d].getText().equals(""))
			{
				if(kcount == (k-1))
				{
					openSquare = d; 
				}
				break;
			}
			else{break;}
		}
		if(kcount == (k-1)&&(openSquare != -1))
		{
			if(player == true)
			{
				winIndex = openSquare;
			}
			else if (player == false)
			{
				winIndexAI = openSquare;
			}
		}
		if(kcount == k) {return true;}
		//up left and down right share count
		kcount = 0;
		openSquare = -1;
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
			else if (buttons[d].getText().equals("")) 
			{
				openSquare = d; 
				break; //must always break on a "" square
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
			else if (buttons[d].getText().equals("")) 
			{
				if(kcount == (k-1))
				{
					openSquare = d; 
				}
				break; //must always break on a "" square
			}
			else{break;}
		}
		//if(kcount == (k-1)) {System.out.println("Imminent Win: " + openSquare);}
		if(kcount == (k-1)&&(openSquare != -1))
		{
			if(player == true)
			{
				winIndex = openSquare;
			}
			else if (player == false)
			{
				winIndexAI = openSquare;
			}
		}
		if(kcount == k) {return true;}
		//System.out.println("Imminent Win: " + winIndex);
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