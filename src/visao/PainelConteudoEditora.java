package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;

class PainelConteudoEditora extends JPanel{

	private static final long serialVersionUID = 1L;

	private TabelasEditora tabelas;
	private JTextField tFNomeEditora;
	
	PainelConteudoEditora() {
		tabelas = new TabelasEditora();
		setLayout(new BorderLayout());
		JLabel lNome = new JLabel("Digite o nome da Editora:");
		lNome.setPreferredSize(new Dimension(170, 20));
		tFNomeEditora = new JTextField();
		tFNomeEditora.setPreferredSize(new Dimension(150, 20));

		JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEADING));
		painelBusca.add(lNome);
		painelBusca.add(tFNomeEditora);

		JScrollPane scroolPaneEditora = tabelas.getJScroolPaneEditora();
		scroolPaneEditora.setPreferredSize(new Dimension(500, 300));
		JLabel campoEditora = new JLabel("Editora(s)");
		campoEditora.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JPanel painelCampoEditora = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelCampoEditora.add(campoEditora);
		JPanel painelEditora = new JPanel(new BorderLayout());
		painelEditora.add(painelCampoEditora, BorderLayout.PAGE_START);
		painelEditora.add(scroolPaneEditora, BorderLayout.CENTER);

		JPanel painelEditorasBusca = new JPanel(new BorderLayout());
		painelEditorasBusca.add(painelBusca, BorderLayout.PAGE_START);
		painelEditorasBusca.add(painelEditora, BorderLayout.CENTER);

		JScrollPane scroolPaneLivros = tabelas.getJScroolPaneLivros();
		scroolPaneLivros.setPreferredSize(new Dimension(530, 200));
		JLabel campoLivros = new JLabel("Livros(s)");
		campoLivros.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JPanel painelCampoLivro = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelCampoLivro.add(campoLivros);
		JPanel painelLivro = new JPanel(new BorderLayout());
		painelLivro.add(painelCampoLivro, BorderLayout.PAGE_START);
		painelLivro.add(scroolPaneLivros, BorderLayout.CENTER);

		JScrollPane scroolPaneAutores = tabelas.getJScroolPaneAutores();
		scroolPaneAutores.setPreferredSize(new Dimension(300, 87));
		JLabel campoAutor = new JLabel("Autor(es)");
		campoAutor.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JPanel painelcampoAutor = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelcampoAutor.add(campoAutor);
		JPanel painelAutor = new JPanel(new BorderLayout());
		painelAutor.add(painelcampoAutor, BorderLayout.PAGE_START);
		painelAutor.add(scroolPaneAutores, BorderLayout.CENTER);

		JPanel painelLivrosAutor = new JPanel(new BorderLayout());
		painelLivrosAutor.add(painelLivro, BorderLayout.PAGE_START);
		painelLivrosAutor.add(painelAutor, BorderLayout.CENTER);

		JPanel painelSecLivrosAutor = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 35));
		painelSecLivrosAutor.add(painelLivrosAutor);

		JPanel painelSecEditorasBusca = new JPanel();
		painelSecEditorasBusca.add(painelEditorasBusca);

		add(painelSecEditorasBusca, BorderLayout.LINE_START);
		add(painelSecLivrosAutor, BorderLayout.CENTER);
	}

	String getNomeEditoraDigitado() {
		return tFNomeEditora.getText();
	}
	
	void addComportamentoBuscaNome(DocumentListener dl) {
		tFNomeEditora.getDocument().addDocumentListener(dl);
	}
	
	void addComportamentoTabelaLivrosDaEditora(MouseListener ml) {
		tabelas.addComportamentoTabelaLivrosDaEditora(ml);
	}
	
	Editora getEditoraSelecionada() {
		return tabelas.getEditoraSelecionada();	
	}
	
	Livro getLivroDaEditoraSelecionado() {
		return tabelas.getLivroDaEditoraSelecionado();
	}
	
	void atualizaDadosEditoras(Set<Editora> editoras) {
		tabelas.atualizaDadosEditora(editoras);
	}

	void preenchaTabelaLivrosEditora(Set<Livro> livrosDaEditora) {
		tabelas.preenchaTabelaLivrosEditora(livrosDaEditora);
		
	}

	void preenchaTabelaAutoresEditora(Map<Integer, Autor> autores) {
		tabelas.preenchaTabelaAutoresEditora(autores);		
	}

	void addComportamentoTabelaEditorasEditora(MouseListener ml) {
		tabelas.addComportamentoTabelaEditorasEditora(ml);		
	}

	void limpaTabelasEditora() {
		tabelas.limpaTabelasEditora();
	}

	void addComportamentoExcluiEEditaEditora(AbstractAction cEditaEditora, AbstractAction cExcluiEditora) {
		tabelas.addComportamentoExcluiEEditaEditora(cEditaEditora, cExcluiEditora);		
	}
}
