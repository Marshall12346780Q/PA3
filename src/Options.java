import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.awt.event.ActionEvent;

public class Options extends JFrame {

	private JFrame frame;
	private JTextField MText;
	private JTextField NText;
	private JTextField KText;
	public static String player;

	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Options window = new Options();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Options() {
		initialize();
	}



	/**
	 * Create the frame.
	 */
	public void initialize() {


		frame = new JFrame();
		frame.setBounds(100, 100, 350, 356);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Game Options");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(0, 46, 336, 13);
		frame.getContentPane().add(lblNewLabel);

		MText = new JTextField();
		MText.setBounds(141, 91, 144, 19);
		frame.getContentPane().add(MText);
		MText.setColumns(10);

		JLabel MField = new JLabel("M");
		MField.setBounds(61, 94, 70, 13);
		frame.getContentPane().add(MField);

		JLabel KField = new JLabel("K");
		KField.setBounds(61, 168, 70, 13);
		frame.getContentPane().add(KField);

		NText = new JTextField();
		NText.setBounds(141, 165, 144, 19);
		frame.getContentPane().add(NText);
		NText.setColumns(10);

		JLabel NField = new JLabel("N");
		NField.setBounds(61, 132, 70, 13);
		frame.getContentPane().add(NField);

		JLabel lblNewLabel_4 = new JLabel("Success");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_4.setBounds(70, 257, 193, 13);
		frame.getContentPane().add(lblNewLabel_4);
		lblNewLabel_4.setVisible(false);

		KText = new JTextField();
		KText.setBounds(141, 129, 144, 19);
		frame.getContentPane().add(KText);
		KText.setColumns(10);

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // hide is deprecated but still works for now.
				int m = Integer.parseInt(MText.getText());
				int n = Integer.parseInt(KText.getText());
				int k = Integer.parseInt(NText.getText());
				if (Menu.aiorpvp.equals("pvp")) {
					TicTacToe t = new TicTacToe(m, n, k);
					t.NewScreen();
				}
				if (Menu.aiorpvp.equals("ai")) {
					AITicTacToe ait = new AITicTacToe(m, n, k);
					ait.NewScreen();
				}
				

			}
		});
		btnNewButton.setBounds(119, 210, 85, 21);
		frame.getContentPane().add(btnNewButton);


	}
}