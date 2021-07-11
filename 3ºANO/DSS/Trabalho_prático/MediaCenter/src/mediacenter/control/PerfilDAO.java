package mediacenter.control;

import java.sql.*;
import mediacenter.model.Perfil;

public class PerfilDAO {

    public PerfilDAO() {
    }

    public boolean containsKey(String email) throws NullPointerException {
        Boolean r = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement stm = conn.createStatement();
            String sql = "SELECT nome FROM Perfil WHERE email='" + email + "'";
            ResultSet rs = stm.executeQuery(sql);
            r = rs.next();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return r;
    }

    public Perfil get(String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dss?user=dssuser&password=dssuser")) {
            Statement stm = conn.createStatement();
            String sql = "SELECT nome,password,imagem,admin FROM Perfil WHERE email='" + email + "'";
            ResultSet rs = stm.executeQuery(sql);
            Perfil perfil = null;
            if (rs.next()) {
                perfil = new Perfil(email, rs.getString(1), rs.getString(2), rs.getString(3), (Boolean) rs.getBoolean(4));
            }
            return perfil;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }
}
