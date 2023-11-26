package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import ConnectionFactory.ConnectionFactory;
import DAO.HistPesoDAO;
import Models.Aluno;
import Models.HistPeso;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class AlterarPeso extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField dataField;
	private JTextField pesoField;
	
	private Aluno aluno; // Util apenas para o botao voltar
	private HistPeso histPeso;
	
	

	public HistPeso getHistPeso() {
		return histPeso;
	}

	public void setHistPeso(HistPeso histPeso) {
		this.histPeso = histPeso;
		this.dataField.setText(histPeso.getData());
		this.pesoField.setText(String.valueOf(histPeso.getPeso()));
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlterarPeso frame = new AlterarPeso();
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
	public AlterarPeso() throws ParseException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(25, 50, 25, 50));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(8, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Alterar peso");
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		JLabel dataLabel = new JLabel("Data");
		dataLabel.setFont(new Font("Arial", Font.BOLD, 16));
		contentPane.add(dataLabel);
		
		dataField = new JFormattedTextField(new MaskFormatter("##/##/####"));
		dataField.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.add(dataField);
		dataField.setColumns(10);
		
		JLabel pesoLabel = new JLabel("Peso");
		pesoLabel.setFont(new Font("Arial", Font.BOLD, 16));
		contentPane.add(pesoLabel);
		
		pesoField = new JTextField();
		pesoField.setFont(new Font("Arial", Font.PLAIN, 12));
		pesoField.setColumns(10);
		contentPane.add(pesoField);
		
		JButton alterarBtn = new JButton("Alterar");
		alterarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("apertou");
					HistPesoDAO dao = new HistPesoDAO(ConnectionFactory.getConnection());
					
					histPeso.setData(dataField.getText());
					histPeso.setPeso(Double.valueOf(pesoField.getText()));
					
					dao.update(histPeso);
					
					JOptionPane.showMessageDialog(null, "Pesagem alterada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					
				}catch (RuntimeException error) {
					if(error.getMessage().contains("Duplicate entry")) {
						JOptionPane.showMessageDialog(null, "Já existe uma pesagem nessa data para esse aluno", "Data inválida", JOptionPane.ERROR_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Não foi possível alterar essa pesagem", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					error.printStackTrace();
				}
				
				
			}
		});
		contentPane.add(alterarBtn);
		
		JLabel lblNewLabel_1 = new JLabel("");
		contentPane.add(lblNewLabel_1);
		
		JButton voltarBtn = new JButton("Voltar");
		voltarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pesagem pes = new Pesagem();
				
				pes.setAluno(aluno);
				pes.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(voltarBtn);
	}

}
