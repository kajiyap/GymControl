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
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
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
		contentPane.setLayout(new GridLayout(9, 1, 2, 2));
		
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
		
		JButton btnNewButton_1 = new JButton("Alterar Dados");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AlterarDados alt = new AlterarDados();
					AlunoDAO dao = new AlunoDAO(ConnectionFactory.getConnection());
					Aluno aluno = dao.getByNome(nomeAlunos.getSelectedItem().toString());
					alt.setAluno(aluno);
					
					alt.setVisible(true);
					setVisible(false);
					
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnNewButton_1);
		
		JButton excluirBtn = new JButton("Excluir aluno");
		excluirBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				AlunoDAO dao = new AlunoDAO(ConnectionFactory.getConnection());
				Aluno aluno = dao.getByNome(nomeAlunos.getSelectedItem().toString());
				int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse usuário?");
				
				if(confirm == 0) {
					try {
						dao.delete(aluno);
						
						List<String> nomes = dao.getNomes();
						
						nomeAlunos.setModel(new DefaultComboBoxModel<Object>(nomes.toArray()));
						
					}catch(RuntimeException error) {
						JOptionPane.showMessageDialog(null, "Algo deu errado", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		contentPane.add(excluirBtn);
		
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
		
		JButton btnNewButton_2 = new JButton("Histórico de peso/IMC");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pesagem pes = new Pesagem();
				pes.setVisible(true);
				setVisible(false);
				AlunoDAO dao = new AlunoDAO(ConnectionFactory.getConnection());
				Aluno aluno = dao.getByNome(nomeAlunos.getSelectedItem().toString());
				
				pes.setIdAluno(aluno.getId());
			}
		});
		contentPane.add(btnNewButton_2);
	}

}
