package modelo.entidades;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Autor {
 
	private int id;
	private String nome;
	private String sobrenome;
	
	private Map<Livro, Integer> livros;

	public Autor(int id, String nome, String sobrenome) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.livros = new TreeMap<Livro, Integer>(new Comparator<Livro>() {

			@Override
			public int compare(Livro l1, Livro l2) {
				return l1.getTitulo().compareToIgnoreCase(l2.getTitulo());
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public void addLivroNoAutor(Livro livro, int seq) {
		livros.put(livro, seq);
	}
	
	public Map<Livro, Integer> getLivrosDoAutor() {
		return livros;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor outroAutor = (Autor) obj;
		if (id != outroAutor.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
}
