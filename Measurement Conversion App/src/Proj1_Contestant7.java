import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import java.awt.Font;

public class Proj1_Contestant7 {

	/* @author: Abhisekh Roka
	 * @program: Conversion App (Skills USA Project 1 GUI)
	 * @version: 1.1 04/13/24
	 */

	/* 
	 * Main frame
	 */
	private JFrame frame;

	/* 
	 * output label used to display output string after conversion
	 */
	private JLabel output;

	/* 
	 * numWarning label used to show warning when the output is large and turns into Scientific notation
	 */
	private JLabel numWarning;

	/* 
	 * titleLabel shows the title of the conversion app and what unit the input is
	 */
	JLabel titleLabel;

	/* 
	 * inputTextField takes in user input
	 */
	private JTextField inputTextField;

	/* 
	 * Used to store previous input and switch it with new input if new input is invalid input
	 */
	BigDecimal previousInput = new BigDecimal(0);

	/* 
	 * Stores all the conversion units
	 */
	HashMap<String, Double> conversionUnit = new HashMap<String, Double>();

	/* 
	 * Combo box for user to select the conversion unit
	 */
	private JComboBox inputType;

	/* 
	 * Stores the output after the conversion
	 */
	String conversionOutput;
	
	
	JTextPane calcHistory;

	/////////////////////////// Main Method ///////////////////////////

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Proj1_Contestant7 window = new Proj1_Contestant7();
					window.frame.setVisible(true);
					window.frame.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/////////////////////////// Constructors ///////////////////////////
	/**
	 * Create the application.
	 */
	public Proj1_Contestant7() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(124, 154, 157));
		frame.setBounds(100, 100, 564, 225);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		initialize();
	}

	/////////////////////////// Methods ///////////////////////////
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Adds Conversion units 
		conversionUnit.put("mi/h", 1.6);

		/* 
		 *  Contestant 7 label
		 */
		JLabel contestantLabel = new JLabel("Contestant 7");
		contestantLabel.setBounds(185, 169, 88, 16);
		frame.getContentPane().add(contestantLabel);

		/* 
		 *  Project 2 label
		 */
		JLabel projectLevel = new JLabel("Project #1");
		projectLevel.setBounds(276, 169, 78, 16);
		frame.getContentPane().add(projectLevel);

		/* 
		 *  Input text field takes in user input
		 *  upon each input sets output label text to converted unit
		 */
		inputTextField = new JTextField();
		inputTextField.setBounds(135, 49, 130, 26);
		frame.getContentPane().add(inputTextField);
		inputTextField.setColumns(10);
		inputTextField.getDocument().addDocumentListener(new MyDocumentListener());

		/* 
		 *  input label
		 */
		JLabel inputLabel = new JLabel("Input                  :");
		inputLabel.setBounds(16, 54, 121, 16);
		frame.getContentPane().add(inputLabel);


		/* 
		 *  Button clears input and output
		 */
		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(287, 76, 67, 29);
		frame.getContentPane().add(clearButton);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				inputTextField.setText("");
			}
		});

		/* 
		 *  Button closes the application
		 */
		JButton closeBtn = new JButton("Exit");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		closeBtn.setBounds(0, 164, 67, 29);
		frame.getContentPane().add(closeBtn);


		/* 
		 * Output display panel and label
		 * Changes when text field receives input
		 */
		JPanel outputPanel = new JPanel();
		outputPanel.setBackground(new Color(124, 154, 157));
		outputPanel.setBounds(0, 101, 354, 46);
		frame.getContentPane().add(outputPanel);
		outputPanel.setLayout(null);

		JLabel outputLabel = new JLabel("Output:");
		outputLabel.setBounds(6, 14, 61, 16);
		outputPanel.add(outputLabel);
		output = new JLabel("----------");
		output.setBounds(61, 6, 287, 32);
		output.setBorder(BorderFactory.createLineBorder(Color.black));
		outputPanel.add(output);


		/* 
		 * displays message when conversion turns into scientific notation
		 */
		numWarning = new JLabel("");
		numWarning.setBounds(10, 134, 361, 36);
		frame.getContentPane().add(numWarning);

		/* 
		 * Calculate button returns a 
		 * pop up with KiloMeters value when clicked
		 */
		JButton calculateBtn = new JButton("Calculate");
		calculateBtn.setBounds(266, 49, 88, 29);
		calculateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, conversionOutput);
			}
		});
		frame.getContentPane().add(calculateBtn);

		/* 
		 *  Conversion app title label
		 */
		titleLabel = new JLabel("Conversion App (Miles per hour - Kilometers per hour)");
		titleLabel.setBounds(10, 6, 354, 16);
		frame.getContentPane().add(titleLabel);


		/* 
		 *  Combo Box so user can select conversion type 
		 *  Upon change in conversion unit also sets output
		 */
		inputType = new JComboBox();
		inputType.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		for (int x =  conversionUnit.size()-1; x >= 0; x--)
			inputType.addItem(conversionUnit.keySet().toArray()[x]);
		inputType.setBounds(48, 50, 75, 27);
		inputType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTextField.setText(inputTextField.getText());
				titleLabel.setText("Conversion App (" +inputType.getSelectedItem().toString() +"- Kilometers)");
			}

		});
		frame.getContentPane().add(inputType);
		
		calcHistory = new JTextPane();
		calcHistory.setBounds(366, 35, 192, 156);
		frame.getContentPane().add(calcHistory);
		
		JLabel lblNewLabel = new JLabel("History");
		lblNewLabel.setBounds(411, 16, 61, 16);
		frame.getContentPane().add(lblNewLabel);



	}

	/* 
	 *  resetInput method to reset the input text field to previous input if the input was invalid
	 *  
	 *  @param input - Previous input that was valid
	 */
	public void resetInput( BigDecimal input ) {
		inputTextField.setVisible(false);
		inputTextField = new JTextField(input.toString());
		inputTextField.setBounds(135, 49, 130, 26);
		frame.getContentPane().add(inputTextField);
		inputTextField.setColumns(10);
		inputTextField.getDocument().addDocumentListener(new MyDocumentListener());
	}

	/* 
	 * 	Method for converting user input to the selected conversion unit
	 *  @param input - integer turned into BigDecimal received from user input  
	 *  @param conversionValue - the conversion unit we are using
	 *  @returns String after the selected unit is converted into Kilometers
	 */

	public String convert(BigDecimal input, double conversionValue) {
		String in;
		if (input.compareTo(new BigDecimal(800000000)) == 1 ) {
			BigDecimal inBigDeci = input.multiply(new BigDecimal(conversionValue)).round(new MathContext(1));
			in = inBigDeci.toString();	
		}
		else {
			in = String.valueOf(Math.round(input.doubleValue()*conversionValue));
			int n = in.length()-3;

			for(int index = 0; index < in.length()/3 && n > 0; index++) {
				in = in.substring(0, n) + "," + in.substring(n);
				n -= 3;
			}

		}
		return in + " Kilometers";
	}

	////////////////////////// Listeners ///////////////////////////

	public class MyDocumentListener implements DocumentListener {

		/**
		 * Changes user input into BigDecimal
		 * user input is then used as a parameter for convert with the selected conversion unit
		 * sets the output label to new measurements or an error label in case of invalid input
		 */
		private void update(DocumentEvent e) {

			/* 
			 *  Checks if input was blank and if so returns a prompt asking for input
			 */
			if (inputTextField.getText().isBlank()) {
				output.setText("");
				numWarning.setText("");
				conversionOutput = "Please input an integer!";
			}

			/* 
			 *  Converts user input and selected conversion unit into Kilometers
			 */
			else {
				BigDecimal input = new BigDecimal(0);

				try {
					input = new BigDecimal(inputTextField.getText().replaceAll(" ", "").replaceAll(",", ""));

					if (input.compareTo(new BigDecimal(800000000)) == 1)
						numWarning.setText("Results are no longer precise. Input is too big!");
					else
						numWarning.setText("");

					if (input.compareTo(new BigDecimal(0)) == 1) {

						String result = convert(input, conversionUnit.get(inputType.getSelectedItem().toString()));
						
						output.setText("   " + result );

						conversionOutput = result;
						
						calcHistory.setText(input.toString() + " Miles = "  + result + "\n" + calcHistory.getText() );
						
						
					}
					else {
						output.setText("Measurement can't be negative!!");
						conversionOutput = "Measurement can't be negative!!";
					}


				} 

				/* 
				 *  Catches invalid inputs 
				 */
				catch (Exception ex) {
					input = previousInput;
					output.setText("Input invalid! Input must be a positive integer!");
					conversionOutput = "Input invalid! Input must be a positive integer!";
					resetInput(input);
					System.out.println(ex);
				}

				/* 
				 *  Sets previousInput so it can be used to replace future input in case it's invalid
				 */
				previousInput = input;

			}
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			update(e);

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			update(e);

		}

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

	}
}