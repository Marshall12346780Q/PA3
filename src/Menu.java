import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Menu {

	private JFrame frame = new JFrame();

	private JButton pvpTTT = new JButton("pvp");
	private JButton aiTTT = new JButton("ai");

	public static String aiorpvp;

	public Menu() {

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 427, 370);
		//frame.getContentPane().setBackground(new Color(50,50,50));
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JLabel lblNewLabel = new JLabel("List Options");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 25, 393, 28);
		frame.getContentPane().add(lblNewLabel);

		pvpTTT.setBounds(10, 82, 393, 28);
		pvpTTT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aiorpvp = "pvp";
				frame.dispose(); // hide is deprecated but still works for now.
				Options op = new Options();
				op.NewScreen();
			}
		});

		aiTTT.setBounds(10, 131, 393, 28);
		aiTTT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aiorpvp = "ai";
				frame.dispose(); // hide is deprecated but still works for now.
				Options op = new Options();
				op.NewScreen();
			}
		});

		frame.getContentPane().add(pvpTTT);
		frame.getContentPane().add(aiTTT);

	}

}