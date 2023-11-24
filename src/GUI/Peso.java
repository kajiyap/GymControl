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

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class Peso extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pesoPane;
	private JTextField pesoField;
	private JLabel nomeLabel;
	private Aluno aluno;
	private JFormattedTextField dataField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Peso frame = new Peso();
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
	public Peso() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pesoPane = new JPanel();
		pesoPane.setBackground(Color.WHITE);
		pesoPane.setBorder(new EmptyBorder(50, 100, 50, 100));

		setContentPane(pesoPane);
		pesoPane.setLayout(new GridLayout(6, 1, 0, 1));
		
		JLabel lblNewLabel = new JLabel("Registrar peso");
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pesoPane.add(lblNewLabel);
		
		this.nomeLabel = new JLabel("");
		pesoPane.add(nomeLabel);
		
		try {
			dataField = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pesoPane.add(dataField);
		dataField.setColumns(10);
		
		pesoField = new JTextField();
		pesoPane.add(pesoField);
		pesoField.setColumns(10);
		
		JButton registrarBtn = new JButton("Registrar");
		registrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HistPesoDAO dao = new HistPesoDAO(ConnectionFactory.getConnection());
				
				HistPeso histPeso = new HistPeso();
				histPeso.setPeso(Double.valueOf(pesoField.getText()));
				histPeso.setData(dataField.getText());
				histPeso.setIdAluno(getAluno().getId());
				
				try {
					dao.insert(histPeso);
					JOptionPane.showMessageDialog(null, "Peso registrado", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					
				}catch (RuntimeException error) {
					JOptionPane.showMessageDialog(null, "Algo deu errado", "Erro", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		pesoPane.add(registrarBtn);
		
		JButton voltarBtn = new JButton("Voltar");
		voltarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pesagem pes = new Pesagem();
				
				pes.setAluno(aluno);
				pes.setVisible(true);
				setVisible(false);
			}
		});
		pesoPane.add(voltarBtn);
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.nomeLabel.setText(aluno.getNome());
		this.aluno = aluno;
	}
	
	

}
