package modelo.dao;

import java.util.Set;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;

public interface DAO {
	
	//métodos de busca
	public Set<Livro> getLivrosPeloTitulo(String titulo);
	
	public Set<Autor> getAutores(String nome, String sobrenome);

	public Set<Editora> getEditorasPeloNome(String nome);
	
	//métodos de inclusão
	public boolean adicionaLivro(Livro livro);

	public boolean adicionaAutoresDoLivro(Livro livro);

	public boolean adicionaAutor(Autor autor);

	public boolean adicionaEditora(Editora editora);
	
	//métodos de exclusão
	public boolean excluiLivros(Set<Livro> livros);

	public boolean excluiAutor(Autor autor);

	public void excluiLivrosXAutores(Set<Livro> livros);

	public boolean excluiEditora(Editora editora);
	
	//métodos de atualização
	public boolean atualizeLivro(Livro livro);
	
	public boolean atualizeAutor(Autor autor);
	
	public boolean atualizeEditora(Editora editora);

	//métodos para a janela de inclusão de livro
	public Set<String> getTodosIsbns();
	
	public Set<Autor> getTodosAutores();
	
	public Set<Editora> getTodasEditoras();
}
