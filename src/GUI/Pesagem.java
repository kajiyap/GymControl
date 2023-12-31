package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import ConnectionFactory.ConnectionFactory;
import DAO.AlunoDAO;
import DAO.HistPesoDAO;
import Models.Aluno;
import Models.HistPeso;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Pesagem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Aluno aluno;
	private JComboBox histData;
	private JLabel pesoLabel;
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
		contentPane.setLayout(new GridLayout(8, 1, 0, 0));
		
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
		        
		    }
		});
		contentPane.add(histData);
		
		JPanel pesoPanel = new JPanel();
		pesoPanel.setBackground(Color.WHITE);
		contentPane.add(pesoPanel);
		pesoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		pesoLabel = new JLabel("Peso: kg");
		pesoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		pesoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pesoPanel.add(pesoLabel);
		
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
		alterarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AlterarPeso altPeso = new AlterarPeso();
					
					HistPesoDAO dao = new HistPesoDAO(ConnectionFactory.getConnection());
					
					HistPeso peso = dao.getByData(histData.getSelectedItem().toString(), aluno.getId());
					
					altPeso.setAluno(aluno);
					altPeso.setHistPeso(peso);
					altPeso.setVisible(true);
					setVisible(false);
				}catch(ParseException error) {
					error.printStackTrace();
				}
				
			}
		});
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
						}
						
					}catch (RuntimeException error) {
						JOptionPane.showMessageDialog(null, "Não foi possível excluir esse registro", "Erro", JOptionPane.ERROR_MESSAGE);
						error.printStackTrace();
					}
				}
			}
		});
		buttonPanel.add(excluirBtn);
		
		JButton imcBtn = new JButton("Calcular IMC");
		imcBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!histData.getSelectedItem().toString().isBlank()) {
					
					HistPesoDAO dao = new HistPesoDAO(ConnectionFactory.getConnection());
					HistPeso peso = dao.getByData(histData.getSelectedItem().toString(), aluno.getId());
					
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

					 int returnValue = jfc.showSaveDialog(null);

					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jfc.getSelectedFile();
						
						try {
							String filePath = selectedFile.getAbsolutePath();
							if(!filePath.endsWith(".txt")) {
								filePath += ".txt";
							}
							
							FileWriter fw = new FileWriter(filePath, true);
						    BufferedWriter bw = new BufferedWriter(fw);
						
						    StringBuilder builder = new StringBuilder();
						    
						    String dataAtual = new SimpleDateFormat("[dd/MM/yyyy] - ").format(Calendar.getInstance().getTime());
						    builder.append(dataAtual);
						    
						    builder.append(aluno.getCpf() + " - ");
						    builder.append(aluno.getNome() + " - ");
						    
						    double imc = peso.calcIMC(aluno.getAltura());
						    builder.append("IMC: "+ String.format("%,.2f - ", imc));
						    
						    String situacao = "";
						    if(imc < 18.5) {
						    	situacao = "Abaixo do peso";
						    }else if(imc >= 18.5 && imc <= 24.9) {
						    	situacao = "Peso adequado";
						    }else if(imc >= 25 && imc <= 29.9) {
						    	situacao = "Sobrepeso";
						    }else if(imc >= 30 && imc <= 34.9) {
						    	situacao = "Obesidade grau 1";
						    }else if(imc >= 35 && imc < 39.9) {
						    	situacao = "Obesidade grau 2";
						    }else if(imc >= 40) {
						    	situacao = "Obesidade extrema";
						    }
						    
						    builder.append("Situação: " + situacao);
						    
						    
						    bw.write(builder.toString());
						    bw.newLine();
						    bw.close();
						    
						    JOptionPane.showMessageDialog(null, "Cálculo registrado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
						    
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Não foi possível registrar o cálculo", "Erro", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Nenhuma pesagem foi selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(imcBtn);
		
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
