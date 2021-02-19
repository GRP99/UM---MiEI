package mediacenter.control;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mediacenter.model.Conteudo;
import mediacenter.model.InfoMusica;

public class BGDAO {

    public BGDAO() {
    }

    public int getIDConteudo(String nomeC, String artistaC, String albumC, String tipoC) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement st = conn.createStatement();
            String sql = "select ID from Conteudo where nome = '" + nomeC + "' and artista = '" + artistaC + "'"
                    + " and album = '" + albumC + "' and tipo = '" + tipoC + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Operacao invalida");
        }
        return -1;
    }

    public void novoDono(int idConteudo, String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try ( Connection con = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement st = con.createStatement();
            String sql = "insert into DonosConteudo values ( " + idConteudo + ",'" + email + "')";
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Operacao invalida");
        }
    }

    public void putConteudo(Conteudo c) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Connection conn = null;
        String sql;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser");
            Statement stm = conn.createStatement();
            // COnsideramos conteudo iguas como mesmo nome , mesmo artista, album e tipo(se e musica ou video);   
            if (getIDConteudo(c.getNome(), c.getArtista(), c.getAlbum(), c.getTipo()) == -1) {
                sql = "INSERT INTO Conteudo (nome, artista, album, tipo, imagem, ficheiro) values('" + c.getNome() + "','" + c.getArtista() + "','" + c.getAlbum() + "','" + c.getTipo() + "','" + c.getImagem() + "','" + c.getFicheiro() + "')";
                stm.executeUpdate(sql);
            }
            int id = getIDConteudo(c.getNome(), c.getArtista(), c.getAlbum(), c.getTipo());
            if (id == -1) {
                throw new SQLException("Erro a criar o conteudo na base de dados.");
            }
            conn.setAutoCommit(false);
            for (String r : c.getCategorias().keySet()) {
                for (String categoria : c.getCategorias().get(r)) {
                    sql = "INSERT INTO CategoriaConteudo values(" + id + ",'" + categoria + "'" + "'" + r + "')";
                    stm.executeUpdate(sql);
                }
            }
            for (String dono : c.getDonos()) {
                sql = "INSERT INTO DonosConteudo values(" + id + ",'" + dono + "')";
                System.out.println("-" + sql + "-");
                stm.executeUpdate(sql);
            }
            conn.commit();
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            try {
                conn.rollback();
                sql = "DELETE FROM Conteudo  where nome = '" + c.getNome() + "' and artista = '" + c.getArtista() + "'"
                        + " and album = '" + c.getAlbum() + "' and tipo = '" + c.getTipo() + "'";
            } catch (Exception z) {
                System.out.println("Erro: " + z.getMessage());
            }
        }
        try {
            if (!conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Map<String, List<String>> getCategorias(int idConteudo) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Map<String, List<String>> res = new HashMap<>();
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement stm = conn.createStatement();
            String sql = "SELECT categoria,IDPerfil FROM CategoriaConteudo  WHERE IDConteudo=" + idConteudo;
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                List<String> categoria;
                if (res.containsKey(rs.getString(2))) {
                    categoria = res.get(rs.getString(2));
                } else {
                    categoria = new ArrayList<>();
                }
                categoria.add(rs.getString(1));
                res.put(rs.getString(2), categoria);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return res;
    }

    public void putCategoria(String categoria, int idConteudo, String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try (
                 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement stm = conn.createStatement();
            String sql = "INSERT INTO CategoriaConteudo (IDConteudo,categoria,IDPerfil) values(" + idConteudo + ",'" + categoria + "'," + "'" + email + "')";
            stm.executeUpdate(sql);
            System.out.println("fim");
        } catch (SQLException e) {
            System.out.println("Erro: Id invalido;");
        }
    }

    public void removeCategoria(String categoria, String email, int idConteudo) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try (
                 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement stm = conn.createStatement();
            String sql = "Delete from CategoriaConteudo  where IDConteudo=" + idConteudo + " and IDPerfil='" + email + "' and categoria='" + categoria + "';";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro: entrada inval;");
        }
    }

    public Conteudo get(int id) {
        Conteudo c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement stm = conn.createStatement();
            String sql = "SELECT nome,artista,album,tipo,imagem,ficheiro FROM Conteudo  WHERE ID=" + id;
            ResultSet rs = stm.executeQuery(sql);
            String[] dados = new String[6];
            if (rs.next()) {
                for (int i = 0; i < 6; i++) {
                    dados[i] = rs.getString(i + 1);
                }
            }
            List<String> donos = new ArrayList();
            sql = "SELECT donoEmail FROM DonosConteudo  WHERE IDConteudo=" + id;
            ResultSet rs2 = stm.executeQuery(sql);
            while (rs2.next()) {
                donos.add(rs2.getString(1));
            }
            Map<String, List<String>> categorias = new HashMap<>();
            sql = "SELECT categoria,IDPerfil  FROM CategoriaConteudo WHERE IDConteudo=" + id;
            ResultSet rs3 = stm.executeQuery(sql);
            while (rs3.next()) {
                List<String> categoria;
                if (categorias.containsKey(rs3.getString(2))) {
                    categoria = categorias.get(rs3.getString(2));
                } else {
                    categoria = new ArrayList<>();
                }
                categoria.add(rs3.getString(1));
                categorias.put(rs3.getString(2), categoria);
            }
            System.out.println("tamanho key" + categorias.size() + " e o tamanho dos valores" + categorias.values().size());
            c = new Conteudo(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], categorias, donos);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return c;
    }

    public List<InfoMusica> mostraBiblioteca_geral() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        List<InfoMusica> res = new ArrayList<>();
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement stm = conn.createStatement();
            String sql = "SELECT ID,nome,artista,album,imagem,tipo FROM Conteudo";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                InfoMusica aux = new InfoMusica(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                res.add(aux);
            }
        } catch (SQLException e) {
            System.out.println("Erro: Id invalido;");
        }
        return res;
    }

    public List<InfoMusica> mostraBiblioteca_individual(String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        List<InfoMusica> res = new ArrayList<>();
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement stm = conn.createStatement();
            String sql = "SELECT c.ID,c.nome,c.artista,c.album,c.imagem,c.tipo FROM Conteudo c,DonosConteudo dc where c.ID=IDConteudo AND dc.donoEmail='" + email + "'";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                InfoMusica aux = new InfoMusica(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                res.add(aux);
            }
        } catch (SQLException e) {
            System.out.println("Erro: Id invalido;");
        }
        return res;
    }
}
