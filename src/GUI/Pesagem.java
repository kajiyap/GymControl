package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ConnectionFactory.ConnectionFactory;
import DAO.HistPesoDAO;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class Pesagem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int idAluno;
	private JComboBox histData;

	public int getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
		HistPesoDAO dao = new HistPesoDAO(ConnectionFactory.getConnection());
		List<String> datas = dao.getDatas(idAluno);
		
		histData.setModel(new DefaultComboBoxModel<Object>(datas.toArray()));
		
		
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
		contentPane.setBorder(new EmptyBorder(25, 25, 25, 25));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Histórico de Peso");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 22));
		contentPane.add(lblNewLabel);

		
		histData = new JComboBox();
		contentPane.add(histData);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		panel.add(lblNewLabel_2);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnNewButton = new JButton("Alterar");
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Excluir");
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Voltar");
		contentPane.add(btnNewButton_2);
	}

}
