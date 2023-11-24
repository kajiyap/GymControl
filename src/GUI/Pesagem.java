package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ConnectionFactory.ConnectionFactory;
import DAO.AlunoDAO;
import DAO.HistPesoDAO;
import Models.Aluno;
import Models.HistPeso;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Pesagem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Aluno aluno;
	private JComboBox histData;
	private JLabel pesoLabel;
	private JLabel imcLabel;
	private JLabel nomeLabel;

	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
		
		// Preenche combobox
		HistPesoDAO dao = new HistPesoDAO(ConnectionFactory.getConnection());
		List<String> datas = dao.getDatas(aluno.getId());
		
		histData.setModel(new DefaultComboBoxModel<Object>(datas.toArray()));
		
		// Preenche labels
		nomeLabel.setText(aluno.getNome());
		
		if(!datas.isEmpty()) {
			// Preenche labels
			HistPeso histPeso = dao.getByData(histData.getSelectedItem().toString(), aluno.getId());
			pesoLabel.setText("Peso: " + String.valueOf(histPeso.getPeso()) + "kg");	
			imcLabel.setText("IMC: " + String.valueOf(histPeso.calcIMC(aluno.getAltura())));
		}
		
		
		
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pesagem frame = new Pesagem();
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
	public Pesagem() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(25, 25, 25, 25));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Histórico de Peso");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 22));
		contentPane.add(lblNewLabel);
		
		nomeLabel = new JLabel("");
		nomeLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(nomeLabel);

		
		histData = new JComboBox();
		histData.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        HistPesoDAO dao = new HistPesoDAO(ConnectionFactory.getConnection());
		        
		        HistPeso histPeso = dao.getByData(histData.getSelectedItem().toString(), aluno.getId());
		        
		        // Preenche labels
		        pesoLabel.setText("Peso: " + String.valueOf(histPeso.getPeso()) + "kg");
		        imcLabel.setText("IMC: " + String.valueOf(histPeso.calcIMC(aluno.getAltura())));
		        
		    }
		});
		contentPane.add(histData);
		
		JPanel pesoPanel = new JPanel();
		pesoPanel.setBackground(Color.WHITE);
		contentPane.add(pesoPanel);
		pesoPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		pesoLabel = new JLabel("Peso: kg");
		pesoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		pesoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pesoPanel.add(pesoLabel);
		
		imcLabel = new JLabel("IMC: ");
		imcLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		imcLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pesoPanel.add(imcLabel);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(1, 1, 0, 0));
		
		JButton registroBtn = new JButton("Resgistrar peso");
		registroBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Peso pes = new Peso();
				
				pes.setAluno(aluno);
				pes.setVisible(true);
				setVisible(false);
			}
		});
		buttonPanel.add(registroBtn);
		
		JButton alterarBtn = new JButton("Alterar");
		buttonPanel.add(alterarBtn);
		
		JButton excluirBtn = new JButton("Excluir");
		excluirBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse registro?");
				if(confirm == 0) {
					try {
						HistPesoDAO dao = new HistPesoDAO(ConnectionFactory.getConnection());
						
						HistPeso peso = dao.getByData(histData.getSelectedItem().toString(), aluno.getId());
						dao.delete(peso.getId());
						
						// Refresh
						
						List<String> datas = dao.getDatas(aluno.getId());
						histData.setModel(new DefaultComboBoxModel<Object>(datas.toArray()));
						if(!datas.isEmpty()) {
							// Preenche labels
							HistPeso histPeso = dao.getByData(histData.getSelectedItem().toString(), aluno.getId());
							pesoLabel.setText("Peso: " + String.valueOf(histPeso.getPeso()) + "kg");	
							imcLabel.setText("IMC: " + String.valueOf(histPeso.calcIMC(aluno.getAltura())));
						}
						
					}catch (RuntimeException error) {
						JOptionPane.showMessageDialog(null, "Não foi possível excluir esse registro", "Erro", JOptionPane.ERROR_MESSAGE);
						error.printStackTrace();
					}
				}
			}
		});
		buttonPanel.add(excluirBtn);
		
		JLabel lblNewLabel_1 = new JLabel("");
		contentPane.add(lblNewLabel_1);
		
		JButton voltarBtn = new JButton("Voltar");
		contentPane.add(voltarBtn);
		voltarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Consulta con = new Consulta();
				
				con.setVisible(true);
				setVisible(false);
			}
		});
	}

}
