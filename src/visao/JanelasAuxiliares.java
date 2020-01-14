package visao;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;

class JanelasAuxiliares {
	
	private JDialog janelaExclusao;
	

	void mostraJanelaExclusão(Autor autor, Livro livro, Editora editora, ActionListener al, JFrame janelaPai) {
		janelaExclusao = new JDialog(janelaPai);
		janelaExclusao.setTitle("Exclusão");
		janelaExclusao.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaExclusao.setModal(true);
		janelaExclusao.setLayout(new BorderLayout());
		JLabel lMens = new JLabel();
		JLabel lMens2 = new JLabel();
		JPanel painelMens = new JPanel();
		painelMens.setLayout(new BoxLayout(painelMens, BoxLayout.PAGE_AXIS));
		painelMens.add(lMens);
		if(autor != null) {
			lMens.setText("Deseja realmente excluir este autor?");
			lMens2.setText("Caso positivo, os livros relacionados a este autor também serão excluídos");
			painelMens.add(lMens2);
		}
		else if(livro != null) {
			lMens.setText("Deseja realmente excluir este livro?");
		}
		else {
			lMens.setText("Deseja realmente excluir esta editora?");
			lMens2.setText("Caso positivo, os livros relacionados a esta editora também serão excluídos");
			painelMens.add(lMens2);
		}
		JButton btOk = new JButton("Ok");
		btOk.addActionListener(al);
		JButton btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				janelaExclusao.dispose();			
			}
		});
		
		janelaExclusao.add(painelMens, BorderLayout.PAGE_START);
		janelaExclusao.add(btOk, BorderLayout.LINE_START);
		janelaExclusao.add(btCancelar, BorderLayout.CENTER);
		
		janelaExclusao.pack();
		janelaExclusao.setVisible(true);
	}
	
	void fechaJanelaDeExclusao() {
		janelaExclusao.dispose();
	}
	
	void mostraJanelaDeResultado(String titulo, String mensagem, JFrame janelaPai) {
		JDialog janelaExito = new JDialog(janelaPai);
		janelaExito.setLayout(new BorderLayout());
		janelaExito.setModal(true);
		janelaExito.setTitle(titulo);
		JLabel lMsg = new JLabel(mensagem);
		janelaExito.add(lMsg, BorderLayout.PAGE_START);
		JButton btOk = new JButton("Ok");
		btOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				janelaExito.dispose();
			}
		});
		janelaExito.add(btOk, BorderLayout.CENTER);
		janelaExito.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaExito.pack();
		janelaExito.setVisible(true);		
	}
	
	
}
