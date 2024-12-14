package Helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FunctionHelper {
	
	Connection conn;
	
	public FunctionHelper(Connection conn) {
		this.conn = conn;
	}
	
	public void crearFuncionEj2() {
	    String sql = """
	            CREATE OR REPLACE FUNCTION F1_CALICCHIAVALENTINA (
                        P_CODIGO_EMPRESA IN NUMBER
                    ) RETURN VARCHAR2 IS
                        V_COUNT             NUMBER;
                        V_NUM_DEPARTAMENTOS NUMBER := 0;
                        V_TOTAL_EMPLEADOS   NUMBER := 0;
                    BEGIN
                        SELECT COUNT(*) INTO V_COUNT
                        FROM EMPRESAS.EMPRESAS
                        WHERE CODEMPRE = P_CODIGO_EMPRESA;
                
                        IF V_COUNT > 0 THEN
                            SELECT COUNT(*) INTO V_NUM_DEPARTAMENTOS
                            FROM EMPRESAS.DEPARTAMENTOS
                            WHERE CODEMPRE = P_CODIGO_EMPRESA;
                
                            FOR rec IN (
                                SELECT CODDEPART FROM EMPRESAS.DEPARTAMENTOS WHERE CODEMPRE = P_CODIGO_EMPRESA
                            ) LOOP
                                SELECT COUNT(*) INTO V_COUNT
                                FROM EMPRESAS.EMPLEADOS
                                WHERE CODDEPART = rec.CODDEPART;
                
                                V_TOTAL_EMPLEADOS := V_TOTAL_EMPLEADOS + V_COUNT;
                            END LOOP;
                
                            RETURN V_NUM_DEPARTAMENTOS || ', ' || V_TOTAL_EMPLEADOS;
                        ELSE
                            RETURN '-1, -1';
                        END IF;
                    END;
                """;

	    try (Statement stmt = conn.createStatement()) {
	        stmt.execute(sql);
	        System.out.println("Función almacenada creada correctamente.");
	    } catch (SQLException e) {
	        System.err.println("Error al crear la función almacenada: " + e.getMessage());
	    }
	}
	
	public Integer[] ejecutarFuncionEj2(int empresa) {
        Integer[] resultArray = null;
        String sql =  "SELECT EMPRESAS.F1_CALICCHIAVALENTINA(?) FROM DUAL";;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empresa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String result = rs.getString(1);
                    String[] parts = result.split(", ");
                    resultArray = new Integer[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        resultArray[i] = Integer.parseInt(parts[i]);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error SQLState: " + e.getSQLState() + "Error Code: " + e.getErrorCode());
            e.printStackTrace();
            resultArray = new Integer[]{-1, -1};
        }

        return resultArray;
    }

}
