package modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import modelo.entidades.Autor;
import modelo.entidades.Editora;
import modelo.entidades.Livro;

public class DAOPostgre implements DAO{
	
	private static final String url = "jdbc:postgresql:livraria";
	private static final String usuario = "postgres";
	private static final String senha = "1234";
	
	//métodos de busca
	@Override
	public Set<Livro> getLivrosPeloTitulo(String titulo) {
		String query = "SELECT 	b.title, " + 
				"		b.isbn, " + 
				"		b.price, " + 
				"		p.publisher_id, " + 
				"		p.name AS \"publisher_name\", " + 
				"		p.url, " + 
				"		a.author_id, " + 
				"		a.name AS \"author_lname\", " + 
				"		a.fname AS \"author_fname\", " + 
				"		ba.seq_no " + 
				"FROM Books b " + 
				"INNER JOIN Publishers p ON(b.publisher_id = p.publisher_id) " + 
				"INNER JOIN BooksAuthors ba ON(ba.isbn = b.isbn) " + 
				"INNER JOIN Authors a ON(ba.author_id = a.author_id) " + 
				"WHERE LOWER(b.title) LIKE LOWER(?) " + 
				"ORDER BY b.title;";
		PreparedStatement st = null;
		ResultSet rs = null;
		Set<Livro> lista = new TreeSet<>(new Comparator<Livro>() {
			@Override
			public int compare(Livro livro1, Livro livro2) {
				return livro1.getTitulo().compareToIgnoreCase(livro2.getTitulo());
			}
		});
		
		try (Connection conexao = DriverManager.getConnection(url, usuario, senha)){
			st = conexao.prepareStatement(query);
			st.setString(1,"%"+titulo+"%");
			rs = st.executeQuery();
			while (rs.next()) {
				Autor autor = new Autor(rs.getInt("author_id"), rs.getString("author_fname").trim(), rs.getString("author_lname").trim());
				Editora editora = new Editora(rs.getInt("publisher_id"), rs.getString("publisher_name").trim(), rs.getString("url").trim());
				Livro livro = new Livro(rs.getString("title").trim(), rs.getString("isbn").trim(), rs.getDouble("price"), editora);
				for(Livro l : lista) {
					if(l.equals(livro)) {
						livro = l;
					}
				}
				livro.addAutor(rs.getInt("seq_no"), autor);
				
				lista.add(livro);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@Override
	public Set<Autor> getAutores(String nome, String sobrenome) {
		String query = "SELECT 	b.title, " + 
				"		b.isbn, " + 
				"		b.price,  " + 
				"		p.publisher_id, " + 
				"		p.name AS \"publisher_name\", " + 
				"		p.url, " + 
				"		a.author_id,  \r\n" + 
				"		a.name AS \"author_lname\", " + 
				"		a.fname AS \"author_fname\", " + 
				"		ba.seq_no " + 
				"FROM Authors a " + 
				"LEFT JOIN BooksAuthors ba ON(ba.author_id = a.author_id) " + 
				"LEFT JOIN Books b ON(ba.isbn = b.isbn) " + 
				"LEFT JOIN Publishers p ON(b.publisher_id = p.publisher_id)  " + 
				"WHERE LOWER(a.fname) LIKE LOWER(?) AND LOWER(a.name) LIKE LOWER(?) " + 
				"ORDER BY a.fname, a.name, ba.seq_no;";
		PreparedStatement st = null;
		ResultSet rs = null;
		Set<Autor> lista = new TreeSet<>(new Comparator<Autor>() {
			@Override
			public int compare(Autor a1, Autor a2) {
				if(!a1.equals(a2)) {
					return a1.getNome().compareToIgnoreCase(a2.getNome());
				}
				return a1.getSobrenome().compareToIgnoreCase(a2.getSobrenome());
			}
		});
		
		try (Connection conexao = DriverManager.getConnection(url, usuario, senha)){
			st = conexao.prepareStatement(query);
			st.setString(1,"%"+nome+"%");
			st.setString(2, "%"+sobrenome+"%");
			rs = st.executeQuery();
			while (rs.next()) {
				Autor autor = new Autor(rs.getInt("author_id"), rs.getString("author_fname").trim(), rs.getString("author_lname").trim());
				for(Autor a : lista) {
					if(a.equals(autor)) {
						autor = a;
						break;
					}
				}
				Editora editora = null;
				if(rs.getInt("publisher_id") != 0)
					editora = new Editora(rs.getInt("publisher_id"), rs.getString("publisher_name").trim(), rs.getString("url").trim());
				Livro livro = null;
				if(rs.getString("isbn") != null) {
					livro = new Livro(rs.getString("title").trim(), rs.getString("isbn").trim(), rs.getDouble("price"), editora);
					autor.addLivroNoAutor(livro, rs.getInt("seq_no"));
				}
				lista.add(autor);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@Override
	public Set<Editora> getEditorasPeloNome(String nome) {
		String query = "SELECT 	b.title,  " + 
				"		b.isbn, " + 
				"		b.price, " + 
				"		p.publisher_id,  " + 
				"		p.name AS \"publisher_name\",  " + 
				"		p.url,  " + 
				"		a.author_id, " + 
				"		a.name AS \"author_lname\",  " + 
				"		a.fname AS \"author_fname\", " + 
				"		ba.seq_no " + 
				"FROM Publishers p " + 
				"LEFT JOIN Books b ON(b.publisher_id = p.publisher_id)  " + 
				"LEFT JOIN BooksAuthors ba ON(ba.isbn = b.isbn)  " + 
				"LEFT JOIN Authors a ON(ba.author_id = a.author_id)  " + 
				"WHERE LOWER(p.name) LIKE LOWER(?) " + 
				"ORDER BY p.name;";
		PreparedStatement st = null;
		ResultSet rs = null;
		Set<Editora> lista = new TreeSet<>(new Comparator<Editora>() {
			@Override
			public int compare(Editora e1, Editora e2) {
				if(!e1.equals(e2)) {
					return e1.getNome().compareToIgnoreCase(e2.getNome());
				}
				return e1.getUrl().compareToIgnoreCase(e2.getUrl());
			}
		});
		
		try (Connection conexao = DriverManager.getConnection(url, usuario, senha)){
			st = conexao.prepareStatement(query);
			st.setString(1,"%"+nome+"%");
			rs = st.executeQuery();
			while (rs.next()) {
				Autor autor = null;
				if(rs.getInt("author_id") != 0)
					autor = new Autor(rs.getInt("author_id"), rs.getString("author_fname").trim(), rs.getString("author_lname").trim());
				Editora editora = new Editora(rs.getInt("publisher_id"), rs.getString("publisher_name").trim(), rs.getString("url").trim());
				for(Editora e : lista) {
					if(e.equals(editora)) {
						editora = e;
						break;
					}
				}
				Livro livro = null;
				if(rs.getString("isbn") != null) {
					livro = new Livro(rs.getString("title").trim(), rs.getString("isbn").trim(), rs.getDouble("price"), editora);
					for(Livro l : editora.getLivrosDaEditora()) {
						if(l.equals(livro)) {
							livro = l;
							break;
						}
					}
					livro.addAutor(rs.getInt("seq_no"), autor);
					editora.addLivroNaEditora(livro);
				}
				else {
					livro = null;
				}
				
				
				lista.add(editora);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	//métodos de inclusão
	@Override
	public boolean adicionaLivro(Livro livro) {
		String query = "INSERT INTO Books VALUES(?, ?, ?, ?);";
		PreparedStatement ps = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			ps.setString(1, livro.getTitulo());
			ps.setString(2, livro.getIsbn());
			ps.setInt(3, livro.getEditora().getId());
			ps.setDouble(4, livro.getPreco());
			int linha = ps.executeUpdate();
			if(linha == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean adicionaAutoresDoLivro(Livro livro) {
		String query = "INSERT INTO BooksAuthors VALUES(?, ?, ?);";
		PreparedStatement ps = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			ps.setString(1, livro.getIsbn());
			int linha = 0;
			Map<Integer, Autor> autores = livro.getAutoresDoLivro();
			for(int i = 1; i <= autores.size(); i++) {
				ps.setString(1, livro.getIsbn());
				ps.setInt(2, autores.get(i).getId());
				ps.setInt(3, i);
				linha += ps.executeUpdate();
			}
			if(linha == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean adicionaAutor(Autor autor) {
		String query = "INSERT INTO Authors (fname, name) VALUES (?, ?);";
		PreparedStatement ps = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			ps.setString(1, autor.getNome());
			ps.setString(2, autor.getSobrenome());
			int linha = ps.executeUpdate();
			if(linha == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean adicionaEditora(Editora editora) {
		String query = "INSERT INTO Publishers (name, url) VALUES (?, ?);";
		PreparedStatement ps = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			ps.setString(1, editora.getNome());
			ps.setString(2, editora.getUrl());
			int linha = ps.executeUpdate();
			if(linha == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	//métodos de exclusão
	@Override
	public boolean excluiLivros(Set<Livro> livros) {
		String query = "DELETE FROM Books WHERE isbn = ?;";
		PreparedStatement ps = null;
		int linha = 0;
		try (Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			for(Livro l : livros) {
				ps.setString(1, l.getIsbn());
				linha += ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(linha == 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean excluiAutor(Autor autor) {
		Set<Livro> livros = autor.getLivrosDoAutor().keySet();
		excluiLivrosXAutores(livros);
		excluiLivros(livros);
		String query = "DELETE FROM Authors WHERE author_id = ?;";
		PreparedStatement ps = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			ps.setInt(1, autor.getId());
			int linha = ps.executeUpdate();
			if(linha == 0) {
				return false;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void excluiLivrosXAutores(Set<Livro> livros) {
		String query = "DELETE FROM BooksAuthors WHERE isbn = ?;";
		PreparedStatement ps = null;
		try (Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			for(Livro l : livros) {
				ps.setString(1, l.getIsbn());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean excluiEditora(Editora editora) {
		Set<Livro> livrosDaEditora = editora.getLivrosDaEditora();
		excluiLivrosXAutores(livrosDaEditora);
		excluiLivros(livrosDaEditora);
		String query = "DELETE FROM Publishers WHERE publisher_id = ?;";
		PreparedStatement ps = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			ps.setInt(1, editora.getId());
			int linha = ps.executeUpdate();
			if(linha == 0) {
				return false;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//métodos de atualização
	@Override
	public boolean atualizeLivro(Livro livro) {
		String query = "UPDATE Books SET title = ?, price = ? WHERE isbn = ?";
		PreparedStatement ps = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			ps.setString(1, livro.getTitulo());
			ps.setDouble(2, livro.getPreco());
			ps.setString(3, livro.getIsbn());
			int linha = ps.executeUpdate();
			if(linha == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean atualizeAutor(Autor autor) {
			String query = "UPDATE Authors SET fname = ?, name = ? WHERE author_id = ?";
			PreparedStatement ps = null;
			try(Connection c = DriverManager.getConnection(url, usuario, senha)){
				ps = c.prepareStatement(query);
				ps.setString(1, autor.getNome());
				ps.setString(2, autor.getSobrenome());
				ps.setInt(3, autor.getId());
				int linha = ps.executeUpdate();
				if(linha == 0) {
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}

	@Override
	public boolean atualizeEditora(Editora editora) {
		String query = "UPDATE Publishers SET name = ?, url = ? WHERE publisher_id = ?";
		PreparedStatement ps = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			ps = c.prepareStatement(query);
			ps.setString(1, editora.getNome());
			ps.setString(2, editora.getUrl());
			ps.setInt(3, editora.getId());
			int linha = ps.executeUpdate();
			if(linha == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//métodos para a janela de inclusão de livro
	@Override
	public Set<String> getTodosIsbns() {
		Set<String> isbns = new TreeSet<>();
		String query = "SELECT isbn FROM Books;";
		Statement st = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			st = c.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				isbns.add(rs.getString("isbn"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return isbns;
	}
	
	@Override
	public Set<Autor> getTodosAutores() {
		Set<Autor> autores = new TreeSet<>(new Comparator<Autor>() {
			@Override
			public int compare(Autor a1, Autor a2) {
				if(!a1.equals(a2)) {
					return a1.getNome().compareToIgnoreCase(a2.getNome());
				}
				return a1.getSobrenome().compareToIgnoreCase(a2.getSobrenome());
			}
		});
		String query = "SELECT * FROM Authors ORDER BY fname, name";
		Statement st = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			st = c.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				autores.add(new Autor(rs.getInt("author_id"), rs.getString("fname"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autores;
	}
	
	@Override
	public Set<Editora> getTodasEditoras() {
		Set<Editora> editoras =  new TreeSet<>(new Comparator<Editora>() {
			@Override
			public int compare(Editora e1, Editora e2) {
				if(!e1.equals(e2)) {
					return e1.getNome().compareToIgnoreCase(e2.getNome());
				}
				return e1.getUrl().compareToIgnoreCase(e2.getUrl());
			}
		});
		String query = "SELECT * FROM Publishers ORDER BY name";
		Statement st = null;
		try(Connection c = DriverManager.getConnection(url, usuario, senha)){
			st = c.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				editoras.add(new Editora(rs.getInt("publisher_id"), rs.getString("name"), rs.getString("url")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return editoras;
	}
}
