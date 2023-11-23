package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import ConnectionFactory.ConnectionFactory;
import DAO.AlunoDAO;
import Models.Aluno;

import java.awt.SystemColor;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

public class Cadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nomeField;
	private JFormattedTextField cpfField;
	private JTextField alturaField;
	private JFormattedTextField pesoField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cadastro frame = new Cadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public Cadastro() throws ParseException {
		setBounds(100, 100, 450, 465);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelCadastro = new JPanel();
		panelCadastro.setBorder(new EmptyBorder(50, 100, 50, 100));
		panelCadastro.setBackground(Color.WHITE);
		getContentPane().add(panelCadastro, BorderLayout.CENTER);
		panelCadastro.setLayout(new GridLayout(13, 1, 1, 1));
		
		JLabel lblNewLabel = new JLabel("Cadastro");
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelCadastro.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		panelCadastro.add(lblNewLabel_1);
		
		nomeField = new JTextField();
		nomeField.setFont(new Font("Arial", Font.PLAIN, 12));
		nomeField.setHorizontalAlignment(SwingConstants.LEFT);
		panelCadastro.add(nomeField);
		nomeField.setColumns(1);
		
		JLabel lblNewLabel_1_1 = new JLabel("CPF");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 16));
		panelCadastro.add(lblNewLabel_1_1);
		
		cpfField = new JFormattedTextField(new MaskFormatter("###.###.###/##"));
		cpfField.setHorizontalAlignment(SwingConstants.LEFT);
		cpfField.setFont(new Font("Arial", Font.PLAIN, 12));
		cpfField.setColumns(1);
		panelCadastro.add(cpfField);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Data de nascimento");
		lblNewLabel_1_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_3_1.setFont(new Font("Arial", Font.BOLD, 16));
		panelCadastro.add(lblNewLabel_1_3_1);
		
		JFormattedTextField dataNascField = new JFormattedTextField(new MaskFormatter("##/##/####"));
		dataNascField.setHorizontalAlignment(SwingConstants.LEFT);
		dataNascField.setFont(new Font("Arial", Font.PLAIN, 12));
		dataNascField.setColumns(1);
		panelCadastro.add(dataNascField);
		
		JLabel lblNewLabel_1_2 = new JLabel("Altura");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_2.setFont(new Font("Arial", Font.BOLD, 16));
		panelCadastro.add(lblNewLabel_1_2);
		
		alturaField = new JTextField();
		alturaField.setHorizontalAlignment(SwingConstants.LEFT);
		alturaField.setFont(new Font("Arial", Font.PLAIN, 12));
		alturaField.setColumns(1);
		panelCadastro.add(alturaField);
		
		JLabel lblNewLabel_1_3 = new JLabel("Peso");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_3.setFont(new Font("Arial", Font.BOLD, 16));
		panelCadastro.add(lblNewLabel_1_3);
		
		pesoField = new JFormattedTextField();
		pesoField.setHorizontalAlignment(SwingConstants.LEFT);
		pesoField.setFont(new Font("Arial", Font.PLAIN, 12));
		pesoField.setColumns(1);
		panelCadastro.add(pesoField);
		
		JButton enviarBtn_1 = new JButton("Enviar");
		enviarBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlunoDAO dao = new AlunoDAO(ConnectionFactory.getConnection());
				
				Aluno aluno = new Aluno();
				aluno.setNome(nomeField.getText());
				aluno.setCpf(cpfField.getText());
				aluno.setDataNasc(dataNascField.getText());
				aluno.setAltura(Double.parseDouble(alturaField.getText()));
				
				try {
					dao.insert(aluno);
					JOptionPane.showMessageDialog(null, "Aluno cadastrado", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				}catch(RuntimeException error) {
					if(error.getMessage().contains("Duplicate entry")) {
						JOptionPane.showMessageDialog(null, "Esse nome já está sendo utilizado, por favor escolha outro", "Nome inválido", JOptionPane.ERROR_MESSAGE);
					}else {
						System.out.println(error);
					}
				}
				
			}
		});
		enviarBtn_1.setBackground(SystemColor.control);
		panelCadastro.add(enviarBtn_1);
		
		JButton btnNewButton = new JButton("Voltar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inicio ini = new Inicio();
				ini.setVisible(true);
				setVisible(false);
			}
		});
		panelCadastro.add(btnNewButton);

	}
}
