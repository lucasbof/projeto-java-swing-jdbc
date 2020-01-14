package visao;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.DocumentListener;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;

public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PainelConteudoLivro painelConteudoLivro;
	private PainelConteudoEditora painelConteudoEditora;
	private PainelConteudoAutor painelConteudoAutor;

	private JanelasAuxiliares janelasAuxiliares;
	private JanelasDeEdicao janelasDeEdicao;
	private JanelaDeInclusaoLivro janelasDeInclusaoLivro;
	private JanelasDeInclusaoEditoraAutor janelasDeInclusaoEditoraAutor;

	public JanelaPrincipal(ActionListener ctAdiocionaLivro, ActionListener ctAdicionaAutor, ActionListener ctAdicionaEditora) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Livraria Amazônia");
		janelasAuxiliares = new JanelasAuxiliares();
		janelasDeEdicao = new JanelasDeEdicao();
		janelasDeInclusaoLivro = new JanelaDeInclusaoLivro();
		janelasDeInclusaoEditoraAutor = new JanelasDeInclusaoEditoraAutor();
		setLayout(new BorderLayout());

		ImageIcon imagemIcone = new ImageIcon("icone.png");
		JLabel icone = new JLabel(imagemIcone);
		JLabel tema = new JLabel("Livraria Amazônia");
		tema.setFont(new Font("Times New Roman", Font.BOLD, 18));
		JPanel painelTema = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelTema.add(icone);
		painelTema.add(tema);

		JPanel cabecalho = new JPanel();
		cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.PAGE_AXIS));
		JMenuBar menuBar = new JMenuBar();
		JMenu livroMenu = new JMenu("Livro");
		JMenu autorMenu = new JMenu("Autor");
		JMenu editoraMenu = new JMenu("Editora");
		menuBar.add(livroMenu);
		JMenuItem buscarLivro = new JMenuItem("Buscar");
		JMenuItem adicionarLivro = new JMenuItem("Adicionar");
		livroMenu.add(buscarLivro);
		livroMenu.addSeparator();
		livroMenu.add(adicionarLivro);
		menuBar.add(autorMenu);
		JMenuItem buscarAutor = new JMenuItem("Buscar");
		JMenuItem adicionarAutor = new JMenuItem("Adicionar");
		autorMenu.add(buscarAutor);
		autorMenu.addSeparator();
		autorMenu.add(adicionarAutor);
		menuBar.add(editoraMenu);
		JMenuItem buscarEditora = new JMenuItem("Buscar");
		JMenuItem adicionarEditora = new JMenuItem("Adicionar");
		editoraMenu.add(buscarEditora);
		editoraMenu.addSeparator();
		editoraMenu.add(adicionarEditora);

		JPanel painelMenu = new JPanel();
		painelMenu.add(menuBar);
		cabecalho.add(painelTema);
		cabecalho.add(painelMenu);

		painelConteudoLivro = new PainelConteudoLivro();
		painelConteudoAutor = new PainelConteudoAutor();
		painelConteudoEditora = new PainelConteudoEditora();

		CardLayout cl = new CardLayout();
		JPanel painelCard = new JPanel(cl);
		painelCard.add(painelConteudoLivro, "painelLivro");
		painelCard.add(painelConteudoAutor, "painelAutor");
		painelCard.add(painelConteudoEditora, "painelEditora");
		
		adicionarLivro.addActionListener(ctAdiocionaLivro);
		adicionarAutor.addActionListener(ctAdicionaAutor);
		adicionarEditora.addActionListener(ctAdicionaEditora);

		buscarLivro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(painelCard, "painelLivro");
				painelConteudoAutor.limpaTabelasAutor();
				painelConteudoLivro.limpaTabelasLivro();
				painelConteudoEditora.limpaTabelasEditora();
			}
		});

		buscarAutor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(painelCard, "painelAutor");
				painelConteudoAutor.limpaTabelasAutor();
				painelConteudoLivro.limpaTabelasLivro();
				painelConteudoEditora.limpaTabelasEditora();
			}
		});

		buscarEditora.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(painelCard, "painelEditora");
				painelConteudoAutor.limpaTabelasAutor();
				painelConteudoLivro.limpaTabelasLivro();
				painelConteudoEditora.limpaTabelasEditora();
			}
		});

		add(cabecalho, BorderLayout.PAGE_START);
		add(painelCard, BorderLayout.CENTER);
		pack();
	}

	//métodos do PainelConteudoLivro
	public void addComportamentoBuscaLivro(DocumentListener dl) {
		painelConteudoLivro.addComportamentoBuscaLivro(dl);		
	}

	public void addComportamentoTabelaLivrosLivro(MouseListener ml) {
		painelConteudoLivro.addComportamentoTabelaLivrosLivro(ml);
		
	}

	public Livro getLivroSelecionado() {
		return painelConteudoLivro.getLivroSelecionado();
	}

	public void preenchaTabelaAutoresLivro(Map<Integer, Autor> autores) {
		painelConteudoLivro.preenchaTabelaAutoresLivro(autores);		
	}

	public void preenchaTabelaEditoraLivro(Editora editora) {
		painelConteudoLivro.preenchaTabelaEditoraLivro(editora);		
	}

	public String getTituloDigitado() {
		return painelConteudoLivro.getTituloDigitado();
	}

	public void atualizaDadosLivros(Set<Livro> livros) {
		painelConteudoLivro.atualizaDadosLivros(livros);		
	}
	
	public void addComportamentoExcluiEEditaLivro(AbstractAction cEditaLivro, AbstractAction cExcluiLivro) {
		painelConteudoLivro.addComportamentoExcluiEEditaLivro(cEditaLivro, cExcluiLivro);		
	}
	
	//métodos do PainelConteudoAutor
	public void addComportamentoBuscaAutor(DocumentListener dl) {
		painelConteudoAutor.addComportamentoBuscaAutor(dl);
	}
	
	public int getIdAutorExcluido(ActionEvent e) {
		return painelConteudoAutor.getIdAutorExcluido(e);
	}

	public Autor getAutorParaEditar(ActionEvent e) {
		return painelConteudoAutor.getAutorParaEditar(e);
	}

	public String getNomeDigitado() {
		return painelConteudoAutor.getNomeDigitado();
	}

	public String getSobrenomeDigitado() {
		return painelConteudoAutor.getSobrenomeDigitado();
	}

	public void atualizaDadosAutores(Set<Autor> autores) {
		painelConteudoAutor.atualizaDadosAutores(autores);
	}

	public Autor getAutorSelecionado() {
		return painelConteudoAutor.getAutorSelecionado();
	}

	public void preenchaTabelaLivrosAutor(Map<Livro, Integer> livrosDoAutor) {
		painelConteudoAutor.preenchaTabelaLivrosAutor(livrosDoAutor);
	}

	public void addComportamentoTabelaAutoresAutor(MouseListener ml) {
		painelConteudoAutor.addComportamentoTabelaAutoresAutor(ml);
	}

	public Livro getLivroDoAutorSelecionado() {
		return painelConteudoAutor.getLivroDoAutorSelecionado();
	}

	public void preenchaTabelaEditoraAutor(Editora editora) {
		painelConteudoAutor.preenchaTabelaEditoraAutor(editora);
	}

	public void addComportamentoTabelaLivrosDoAutor(MouseListener ml) {
		painelConteudoAutor.addComportamentoTabelaLivrosDoAutor(ml);
	}
	
	public void addComportamentoExcluiEEditaAutor(AbstractAction cEdita, AbstractAction cExclui) {
		painelConteudoAutor.addComportamentoExcluiEEditaAutor(cEdita, cExclui);
	}

	//métodos do painelConteudoEditora
	public String getNomeEditoraDigitado() {
		return painelConteudoEditora.getNomeEditoraDigitado();
	}
	
	public void addComportamentoBuscaNome(DocumentListener dl) {
		painelConteudoEditora.addComportamentoBuscaNome(dl);
	}

	public void atualizaDadosEditoras(Set<Editora> editoras) {
		painelConteudoEditora.atualizaDadosEditoras(editoras);	
	}

	public void addComportamentoBuscaNomeEditora(DocumentListener dl) {
		painelConteudoEditora.addComportamentoBuscaNome(dl);		
	}
	
	public void addComportamentoTabelaLivrosDaEditora(MouseListener ml) {
		painelConteudoEditora.addComportamentoTabelaLivrosDaEditora(ml);
	}

	public Editora getEditoraSelecionada() {
		return painelConteudoEditora.getEditoraSelecionada();	
	}
	
	public Livro getLivroDaEditoraSelecionado() {
		return painelConteudoEditora.getLivroDaEditoraSelecionado();
	}

	public void preenchaTabelaLivrosEditora(Set<Livro> livrosDaEditora) {
		painelConteudoEditora.preenchaTabelaLivrosEditora(livrosDaEditora);
	}

	public void preenchaTabelaAutoresEditora(Map<Integer, Autor> autores) {
		painelConteudoEditora.preenchaTabelaAutoresEditora(autores);	
	}

	public void addComportamentoTabelaEditorasEditora(MouseListener ml) {
		painelConteudoEditora.addComportamentoTabelaEditorasEditora(ml);
	}
	
	public void addComportamentoExcluiEEditaEditora(AbstractAction cEditaEditora, AbstractAction cExcluiEditora) {
		painelConteudoEditora.addComportamentoExcluiEEditaEditora(cEditaEditora, cExcluiEditora);		
	}
	
	//métodos da janela de exclusão
	public void mostraJanelaExclusão(Autor autor, Livro livro, Editora editora, ActionListener al) {
		janelasAuxiliares.mostraJanelaExclusão(autor, livro, editora, al, this);
	}
	
	public void fechaJanelaDeExclusao() {
		janelasAuxiliares.fechaJanelaDeExclusao();
	}

	//mostra janela auxiliar de resultado
	public void mostraJanelaDeResultado(String titulo, String mensagem) {
		janelasAuxiliares.mostraJanelaDeResultado(titulo, mensagem, this);
	}
	
	//métodos das janelas de edição 
	//métodos relacionadas a edição da editora
	public void mostraJanelaEditaEditora(Editora obj, ActionListener cBtSalvar) {
		janelasDeEdicao.mostraJanelaEditaEditora(obj, this, cBtSalvar);
	}
	
	public Editora getEditoraEditada() {
		return janelasDeEdicao.getEditoraEditada();
	}
	
	public void fechaJanelaEdicaoEditora() {
		janelasDeEdicao.fechaJanelaEdicaoEditora();
	}

	//métodos relacionados a edição de autor
	public void mostraJanelaEditaAutor(Autor autor, ActionListener cBtSalvar) {
		janelasDeEdicao.mostraJanelaEditaAutor(autor, this, cBtSalvar);
	}

	public Autor getAutorEditada() {
		return janelasDeEdicao.getAutorEditado();
	}

	public void fechaJanelaEdicaoAutor() {
		janelasDeEdicao.fechaJanelaEdicaoAutor();
		
	}

	//métodos relacionados a edição de livro
	public void mostraJanelaEditaLivro(Livro livro, ActionListener cBtSalvar) {
		janelasDeEdicao.mostraJanelaEditaLivro(livro, this, cBtSalvar);	
	}

	public Livro getLivroEditado() {
		return janelasDeEdicao.getLivroEditado();
	}

	public void fechaJanelaEdicaoLivro() {
		janelasDeEdicao.fechaJanelaEdicaoLivro();		
	}
	
	//métodos da janela de inclusão de livro
	public void mostraJanelaAdicionaLivro(Set<Editora> editoras, Set<Autor> autores, Set<String> isbns, ActionListener cBtAdicionar) {
		janelasDeInclusaoLivro.mostraJanelaAdionacionaLivro(editoras, autores, this, isbns, cBtAdicionar);
	}

	public Livro getNovoLivro() {
		return janelasDeInclusaoLivro.getNovoLivro();
	}
	
	public void fechaJanelaInclusaoLivro() {
		janelasDeInclusaoLivro.fechaJanelaInclusaoLivro();
	}
	
	//métodos de inclusão de autor
	public void mostraJanelaInclusaoAutor(ActionListener cpAdicionarAutor) {
		janelasDeInclusaoEditoraAutor.mostraJanelaInclusaoAutor(this, cpAdicionarAutor);
	}
	
	public void fechaJanelaInclusaoAutor() {
		janelasDeInclusaoEditoraAutor.fechaJanelaInclusaoAutor();
	}

	public Autor getNovoAutor() {
		return janelasDeInclusaoEditoraAutor.getNovoAutor();
	}

	//métodos de inclusão de editora
	public void mostraJanelaInclusaoEditora(ActionListener al) {
		janelasDeInclusaoEditoraAutor.mostraJanelaInclusaoEditora(this, al);
	}
	
	public Editora getNovaEditora() {
		return janelasDeInclusaoEditoraAutor.getNovaEditora();
	}

	public void fechaJanelaInclusaoEditora() {
		janelasDeInclusaoEditoraAutor.fechaJanelaInclusaoEditora();		
	}
}
