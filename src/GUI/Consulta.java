package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ConnectionFactory.ConnectionFactory;
import DAO.AlunoDAO;
import Models.Aluno;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;

public class Consulta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Consulta frame = new Consulta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Consulta() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 516);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(55, 35, 50, 35));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(7, 1, 2, 2));
		
		JLabel lblNewLabel = new JLabel("Consulta de alunos");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 22));
		contentPane.add(lblNewLabel);
		
		AlunoDAO dao = new AlunoDAO(ConnectionFactory.getConnection());
		List<String> nomes = dao.getNomes();
		
		JComboBox nomeAlunos = new JComboBox(nomes.toArray());
		contentPane.add(nomeAlunos);
		
		JLabel lblNewLabel_1 = new JLabel("");
		contentPane.add(lblNewLabel_1);
		
		JButton registrarBtn = new JButton("Registrar peso");
		registrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlunoDAO dao = new AlunoDAO(ConnectionFactory.getConnection());
				Aluno aluno = dao.getByNome(nomeAlunos.getSelectedItem().toString());
				
				Peso pes = new Peso();
				pes.setAluno(aluno);
				
				pes.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(registrarBtn);
		
		JButton btnNewButton = new JButton("Gerar relatório");
		contentPane.add(btnNewButton);
		
		JButton voltarBtn = new JButton("Voltar");
		voltarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inicio ini = new Inicio();
				ini.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(voltarBtn);
	}

}
