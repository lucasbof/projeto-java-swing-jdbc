package modelo.entidades;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Editora {

	private int id;
	private String nome;
	private String url;
	
	private Set<Livro> livros;

	public Editora(int id, String nome, String url) {
		this.id = id;
		this.nome = nome;
		this.url = url;
		this.livros = new TreeSet<Livro>(new Comparator<Livro>() {
			@Override
			public int compare(Livro livro1, Livro livro2) {
				return livro1.getTitulo().compareToIgnoreCase(livro2.getTitulo());
			}
		});
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addLivroNaEditora(Livro livro) {
		livros.add(livro);
	}
	
	public Set<Livro> getLivrosDaEditora() {
		return livros;
	}
	
	public void excluiTodosOsLivrosDaEditora() {
		livros.clear();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Editora outraEditora = (Editora) obj;
		if (id != outraEditora.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
}
