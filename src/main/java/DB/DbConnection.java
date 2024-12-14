package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	private static final String CONEXION = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USUARIO = "EMPRESAS";
    private static final String CLAVE = "EMPRESAS";
    
    private static Connection conn = null;
    
    public static Connection getDbConn() {
        try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(CONEXION, USUARIO, CLAVE);
			}
		} catch (SQLException e) {
			System.out.println("ERROR DE CONEXION");
			e.printStackTrace();
		}
        return conn;
    }

    public static void closeDbConn() {
        if (conn != null) {
            try {
            	conn.close();
            } catch (SQLException e) {
                System.err.println( e.getMessage());
            }
        }
    }
}
