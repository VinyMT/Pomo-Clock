package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;

public class PomodoroClock implements ActionListener{

	private JFrame frame;
	private int mm = 00;
	private int ss = 00;
	private int cont;
	private JButton stopButton;
	private JLabel timeLabel;
	private Timer ciclo5;
	private Timer ciclo25;
	private Timer cicloAtual;
	private JButton startButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PomodoroClock window = new PomodoroClock();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PomodoroClock() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		stopButton = new JButton("Stop");
		springLayout.putConstraint(SpringLayout.NORTH, stopButton, 181, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, stopButton, 260, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, stopButton, -89, SpringLayout.EAST, frame.getContentPane());
		stopButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		stopButton.addActionListener(this);
		frame.getContentPane().add(stopButton);
		
		timeLabel = new JLabel(String.format("%02d", mm) + ":" + String.format("%02d", ss));
		springLayout.putConstraint(SpringLayout.NORTH, timeLabel, 65, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, timeLabel, -27, SpringLayout.NORTH, stopButton);
		timeLabel.setForeground(Color.BLACK);
		springLayout.putConstraint(SpringLayout.WEST, timeLabel, 134, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, timeLabel, 296, SpringLayout.WEST, frame.getContentPane());
		timeLabel.setBackground(Color.WHITE);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 55));
		frame.getContentPane().add(timeLabel);
		
		startButton = new JButton("Start");
		startButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		springLayout.putConstraint(SpringLayout.NORTH, startButton, 27, SpringLayout.SOUTH, timeLabel);
		springLayout.putConstraint(SpringLayout.WEST, startButton, 95, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, startButton, 0, SpringLayout.SOUTH, stopButton);
		springLayout.putConstraint(SpringLayout.EAST, startButton, -72, SpringLayout.WEST, stopButton);
		startButton.addActionListener(this);
		frame.getContentPane().add(startButton);
		
		//Instancia o timer do ciclo 25 e a ação que irá ocorrer nele
		ciclo25 = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont++;
				ss = cont % 60;
				mm = (cont / 60) % 60;
				timeLabel.setText(String.format("%02d", mm) + ":" + String.format("%02d", ss));
				if(mm == 25) {
					cont = 0;
					ciclo25.stop();
					cicloAtual = ciclo5;
					ciclo5.start();
				}
			}
		});
		
		//Instancia o timer do ciclo 5 e a ação que irá ocorrer nele
		ciclo5 = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont++;
				ss = cont % 60;
				mm = (cont / 60) % 60;
				timeLabel.setText(String.format("%02d", mm) + ":" + String.format("%02d", ss));
				if(mm == 5) {
					cont = 0;
					ciclo5.stop();
					cicloAtual = ciclo25;
					ciclo25.start();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Checa se a fonte da ação foi o botão de start, se sim, inicia o timer
		if(e.getSource() == startButton) {
			ciclo25.start();
			cicloAtual = ciclo25;
		}
		
		//Checa se a fonte da ação foi o botão stop, se sim, para o timer
		if(e.getSource() == stopButton) {
			cicloAtual.stop();
			cont = 0;
			timeLabel.setText("00:00");
		}
		
	}
}
