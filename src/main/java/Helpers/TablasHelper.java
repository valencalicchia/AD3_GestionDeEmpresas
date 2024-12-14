package Helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TablasHelper {
	
	Connection conn;
	
	public TablasHelper(Connection conn) {
		this.conn = conn;
	}
	
	 public boolean existeEnTabla(String tabla, String columna, int valor) {
	        String query = "SELECT 1 FROM " + tabla + " WHERE " + columna + " = ?";

	        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setInt(1, valor);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                return rs.next();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return false;
	    }
	    
	    public boolean existeColumna() {
	        try (PreparedStatement pstme = conn.prepareStatement( """
	                SELECT CASE
	                WHEN COUNT(*) > 0 THEN 1
	                ELSE 0
	                END AS column_exists
	                FROM all_tab_columns
	                WHERE table_name = 'EMPRESAS'
	                AND column_name = ?
	                AND owner = 'EMPRESAS'""")) {
	            pstme.setString(1, "CONTDEPT");
	            try (ResultSet rs = pstme.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt("column_exists") == 1;
	                }
	                return false;
	            }
	        } catch (SQLException e) {
	            System.out.println("Error al comprobar la existencia de la columna. ERROR: " + e.getMessage());
	        }
	        return false;
	    }

	    public boolean crearColumna() {
	        try (Statement stmt = conn.createStatement()) {
	            stmt.executeUpdate("ALTER TABLE EMPRESAS.EMPRESAS ADD CONTDEPT NUMBER DEFAULT 0");
	            return true;
	        } catch (SQLException e) {
	            return false;
	        }
	    }
	
}
