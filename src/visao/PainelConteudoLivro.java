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

class PainelConteudoLivro extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField textoBusca;
	private TabelasLivro tabelas;

	PainelConteudoLivro() {
		setLayout(new BorderLayout());
		tabelas = new TabelasLivro();

		JLabel busca = new JLabel("Digite o título do livro:");
		busca.setPreferredSize(new Dimension(130, 20));
		textoBusca = new JTextField();
		textoBusca.setPreferredSize(new Dimension(180, 20));

		JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEADING));
		painelBusca.add(busca);
		painelBusca.add(textoBusca);

		JScrollPane scroolPaneLivros = tabelas.getJScroolPaneLivros();
		scroolPaneLivros.setPreferredSize(new Dimension(780, 343));
		JLabel campoLivros = new JLabel("Livro(s)");
		campoLivros.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JPanel painelCampoLivros = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelCampoLivros.add(campoLivros);
		JPanel painelLivros = new JPanel(new BorderLayout());
		painelLivros.add(painelCampoLivros, BorderLayout.PAGE_START);
		painelLivros.add(scroolPaneLivros, BorderLayout.CENTER);

		JPanel painelLivroBusca = new JPanel(new BorderLayout());
		painelLivroBusca.add(painelBusca, BorderLayout.PAGE_START);
		painelLivroBusca.add(painelLivros, BorderLayout.CENTER);

		JScrollPane scroolPaneAutores = tabelas.getJScroolPaneAutores();
		scroolPaneAutores.setPreferredSize(new Dimension(300, 87));
		JLabel campoAutores = new JLabel("Autor(es)");
		campoAutores.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JPanel painelCampoAutores = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelCampoAutores.add(campoAutores);
		JPanel painelAutores = new JPanel(new BorderLayout());
		painelAutores.add(painelCampoAutores, BorderLayout.PAGE_START);
		painelAutores.add(scroolPaneAutores, BorderLayout.CENTER);

		JScrollPane scroolPaneEditora = tabelas.getJScroolPaneEditora();
		scroolPaneEditora.setPreferredSize(new Dimension(300, 39));
		JLabel campoEditora = new JLabel("Editora");
		campoEditora.setFont(new Font("Times New Roman", Font.BOLD, 16));
		JPanel painelcampoEditora = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelcampoEditora.add(campoEditora);
		JPanel painelEditora = new JPanel(new BorderLayout());
		painelEditora.add(painelcampoEditora, BorderLayout.PAGE_START);
		painelEditora.add(scroolPaneEditora, BorderLayout.CENTER);

		JPanel painelAutoresEditora = new JPanel(new BorderLayout());
		painelAutoresEditora.add(painelAutores, BorderLayout.PAGE_START);
		painelAutoresEditora.add(painelEditora, BorderLayout.CENTER);

		JPanel painelSecAutoresEditora = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 35));
		painelSecAutoresEditora.add(painelAutoresEditora);

		JPanel painelSecLivroBusca = new JPanel();
		painelSecLivroBusca.add(painelLivroBusca);

		add(painelSecLivroBusca, BorderLayout.CENTER);
		add(painelSecAutoresEditora, BorderLayout.LINE_END);
	}

	String getTituloDigitado() {
		return textoBusca.getText();
	}

	void addComportamentoTabelaLivrosLivro(MouseListener ml) {
		tabelas.addComportamentoTabelaLivros(ml);
	}

	void atualizaDadosLivros(Set<Livro> livros) {
		tabelas.atualizaDadosLivros(livros);
	}

	Livro getLivroSelecionado() {
		return tabelas.getLivroSelecionado();
	}

	void preenchaTabelaAutoresLivro(Map<Integer, Autor> autores) {
		tabelas.preenchaTabelaAutoresLivro(autores);
	}

	void preenchaTabelaEditoraLivro(Editora editora) {
		tabelas.preenchaTabelaEditoraLivro(editora);
	}

	void addComportamentoBuscaLivro(DocumentListener dl) {
		textoBusca.getDocument().addDocumentListener(dl);
	}

	void limpaTabelasLivro() {
		tabelas.limpaTabelasLivro();
	}

	void addComportamentoExcluiEEditaLivro(AbstractAction cEditaLivro, AbstractAction cExcluiLivro) {
		tabelas.addComportamentoExcluirEEditarLivro(cEditaLivro, cExcluiLivro);
	}
}
