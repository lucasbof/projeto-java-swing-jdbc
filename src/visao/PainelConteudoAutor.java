package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
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

class PainelConteudoAutor extends JPanel {

	private static final long serialVersionUID = 1L;

	private TabelasAutor tabelas;
	private JTextField tFNomeAutor;
	private JTextField tFSobrenomeAutor;

	PainelConteudoAutor() {
		tabelas = new TabelasAutor();
		setLayout(new BorderLayout());
		JLabel lNome = new JLabel("Digite o nome do autor:");
		lNome.setPreferredSize(new Dimension(170, 20));
		tFNomeAutor = new JTextField();
		tFNomeAutor.setPreferredSize(new Dimension(150, 20));
		JLabel lSobrenome = new JLabel("Digite o sobrenome do autor:");
		lSobrenome.setPreferredSize(new Dimension(170, 20));
		tFSobrenomeAutor = new JTextField();
		tFSobrenomeAutor.setPreferredSize(new Dimension(150, 20));

		JPanel painelBusca = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		painelBusca.add(lNome, c);
		c.gridx = 1;
		c.gridy = 0;
		painelBusca.add(tFNomeAutor, c);
		c.gridx = 0;
		c.gridy = 1;
		painelBusca.add(lSobrenome, c);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		painelBusca.add(tFSobrenomeAutor, c);

		JScrollPane scroolPaneAutores = tabelas.getJScroolPaneAutores();
		scroolPaneAutores.setPreferredSize(new Dimension(400, 300));
		JLabel campoAutores = new JLabel("Autores(s)");
		campoAutores.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JPanel painelCampoAutores = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelCampoAutores.add(campoAutores);
		JPanel painelAutores = new JPanel(new BorderLayout());
		painelAutores.add(painelCampoAutores, BorderLayout.PAGE_START);
		painelAutores.add(scroolPaneAutores, BorderLayout.CENTER);
		
		JPanel painelAutores2 = new JPanel(new FlowLayout());
		painelAutores2.add(painelAutores);

		JPanel painelAutoresBusca = new JPanel(new BorderLayout());
		painelAutoresBusca.add(painelBusca, BorderLayout.PAGE_START);
		painelAutoresBusca.add(painelAutores2, BorderLayout.CENTER);

		JScrollPane scroolPaneLivros = tabelas.getJScroolPaneLivros();
		scroolPaneLivros.setPreferredSize(new Dimension(600, 200));
		JLabel campoLivros = new JLabel("Livros(s)");
		campoLivros.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JPanel painelCampoLivro = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelCampoLivro.add(campoLivros);
		JPanel painelLivro = new JPanel(new BorderLayout());
		painelLivro.add(painelCampoLivro, BorderLayout.PAGE_START);
		painelLivro.add(scroolPaneLivros, BorderLayout.CENTER);

		JScrollPane scroolPaneEditora = tabelas.getJScroolPaneEditora();
		scroolPaneEditora.setPreferredSize(new Dimension(300, 39));
		JLabel campoEditora = new JLabel("Editora");
		campoEditora.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JPanel painelcampoEditora = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelcampoEditora.add(campoEditora);
		JPanel painelEditora = new JPanel(new BorderLayout());
		painelEditora.add(painelcampoEditora, BorderLayout.PAGE_START);
		painelEditora.add(scroolPaneEditora, BorderLayout.CENTER);

		JPanel painelLivrosEditora = new JPanel(new BorderLayout());
		painelLivrosEditora.add(painelLivro, BorderLayout.PAGE_START);
		painelLivrosEditora.add(painelEditora, BorderLayout.CENTER);

		JPanel painelSecLivrosEditora = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 51));
		painelSecLivrosEditora.add(painelLivrosEditora);

		JPanel painelSecAutoresBusca = new JPanel();
		painelSecAutoresBusca.add(painelAutoresBusca);

		add(painelSecAutoresBusca, BorderLayout.LINE_START);
		add(painelSecLivrosEditora, BorderLayout.CENTER);
	}

	void addComportamentoBuscaAutor(DocumentListener dl) {
		tFNomeAutor.getDocument().addDocumentListener(dl);
		tFSobrenomeAutor.getDocument().addDocumentListener(dl);
	}
	
	int getIdAutorExcluido(ActionEvent e) {
		return tabelas.getIdAutorExcluido(e);
	}

	Autor getAutorParaEditar(ActionEvent e) {
		return tabelas.getAutorParaEditar(e);
	}

	String getNomeDigitado() {
		return tFNomeAutor.getText();
	}

	String getSobrenomeDigitado() {
		return tFSobrenomeAutor.getText();
	}

	void atualizaDadosAutores(Set<Autor> autores) {
		tabelas.atualizaDadosAutores(autores);
	}

	Autor getAutorSelecionado() {
		return tabelas.getAutorSelecionado();
	}
	
	Livro getLivroDoAutorSelecionado() {
		return tabelas.getLivroDoAutorSelecionado();
	}

	void preenchaTabelaLivrosAutor(Map<Livro, Integer> livrosDoAutor) {
		tabelas.preenchaTabelaLivros(livrosDoAutor);
	}

	void addComportamentoTabelaAutoresAutor(MouseListener ml) {
		tabelas.addComportamentoTabelaAutoresAutor(ml);
	}

	void preenchaTabelaEditoraAutor(Editora editora) {
		tabelas.preenchaTabelaEditora(editora);
	}

	void addComportamentoTabelaLivrosDoAutor(MouseListener ml) {
		tabelas.addComportamentoTabelaLivrosDoAutor(ml);
	}

	void limpaTabelasAutor() {
		tabelas.limpaTabelasAutor();
	}
	
	void addComportamentoExcluiEEditaAutor(AbstractAction cEdita, AbstractAction cExclui) {
		tabelas.addComportamentoExcluiEEditaAutor(cEdita, cExclui);
	}

}
