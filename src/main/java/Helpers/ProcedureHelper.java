package Helpers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class ProcedureHelper {

	Connection conn;
	
	public ProcedureHelper(Connection conn) {
		this.conn = conn;
	}
	
	public void crearProcedureEj2() {
		String sql = """
				CREATE OR REPLACE PROCEDURE P1_CALICCHIAVALENTINA(
                CODIGO_EMPLEADO IN NUMBER,
                NOMBRE_EMPLEADO IN VARCHAR2,
                DIRECCION_EMPLEADO IN VARCHAR2,
                POBLACION_EMPLEADO IN VARCHAR2,
                CODIGOENCARGADO_EMPLEADO IN NUMBER,
                CODIGODEPARTAMENTO_EMPLEADO IN NUMBER,
                CODIGOOFICIO_EMPLEADO IN NUMBER,
                RESULTADO OUT VARCHAR2
            ) AS
                EMPLEADO_EXISTE     NUMBER := 0;
                ENCARGADO_EXISTE    NUMBER := 0;
                DEPARTAMENTO_EXISTE NUMBER := 0;
                OFICIO_EXISTE       NUMBER := 0;
            BEGIN
                SELECT
                    COUNT(*) INTO EMPLEADO_EXISTE
                FROM
                    EMPRESAS.EMPLEADOS
                WHERE
                    CODEMPLE = CODIGO_EMPLEADO;
                SELECT
                    COUNT(*) INTO ENCARGADO_EXISTE
                FROM
                    EMPRESAS.EMPLEADOS
                WHERE
                    CODENCARGADO = CODIGOENCARGADO_EMPLEADO;
                SELECT
                    COUNT(*) INTO DEPARTAMENTO_EXISTE
                FROM
                    EMPRESAS.DEPARTAMENTOS
                WHERE
                    CODDEPART = CODIGODEPARTAMENTO_EMPLEADO;
                SELECT
                    COUNT(*) INTO OFICIO_EXISTE
                FROM
                    EMPRESAS.OFICIOS
                WHERE
                    CODOFICIO = CODIGOOFICIO_EMPLEADO;
                IF EMPLEADO_EXISTE > 0 THEN
                    RESULTADO := 'EMPLEADO '|| CODIGO_EMPLEADO ||' YA EXISTE';
                END IF;

                IF ENCARGADO_EXISTE = 0 THEN

                    RESULTADO := RESULTADO
                                 || chr(10) || 'ENCARGADO '|| CODIGOENCARGADO_EMPLEADO || ' NO EXISTE';
                END IF;

                IF DEPARTAMENTO_EXISTE = 0 THEN
                    RESULTADO := RESULTADO
                                 || chr(10) || 'DEPARTAMENTO ' || CODIGODEPARTAMENTO_EMPLEADO || ' NO EXISTE';
                END IF;

                IF OFICIO_EXISTE = 0 THEN

                    RESULTADO := RESULTADO
                                 ||chr(10) || 'OFICIO ' || CODIGOOFICIO_EMPLEADO ||' NO EXISTE';
                END IF;

                IF RESULTADO IS NULL THEN
                    INSERT INTO EMPRESAS.EMPLEADOS (
                        CODEMPLE,
                        NOMBRE,
                        DIRECCION,
                        POBLACION,
                        FECHAALTA,
                        CODENCARGADO,
                        CODDEPART,
                        CODOFICIO
                    ) VALUES (
                        CODIGO_EMPLEADO,
                        NOMBRE_EMPLEADO,
                        DIRECCION_EMPLEADO,
                        POBLACION_EMPLEADO,
                        SYSDATE,
                        CODIGOENCARGADO_EMPLEADO,
                        CODIGODEPARTAMENTO_EMPLEADO,
                        CODIGOOFICIO_EMPLEADO
                    );
                    RESULTADO := 'EMPLEADO ' || CODIGO_EMPLEADO || ' INSERTADO EN LA TABLA';
                END IF;
            EXCEPTION
                WHEN OTHERS THEN
                    RESULTADO := chr(10) || 'OCURRIÓ UN ERROR: '
                                 || SQLERRM;
            END P1_CALICCHIAVALENTINA;
""";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
	}
	

  
	public String PruebaProcedimiento(
            Integer codEmpl, String nombre, String direccion, String localidad,
            Integer jefe, Integer departamento, Integer oficio
    ) {

        String prueba = String.format(
                "PRUEBA %d: (%d, \"%s\", \"%s\", \"%s\", %d, %d, %d)",
                codEmpl + 1, codEmpl, nombre, direccion, localidad, jefe, departamento, oficio);
		System.out.println(prueba);
		System.out.println();
		
        String result = null;

        String procedure = "{ call P1_CALICCHIAVALENTINA(?, ?, ?, ?, ?, ?, ?, ?) }";

        try (CallableStatement stmt = conn.prepareCall(procedure)) {
            stmt.setInt(1, codEmpl);
            stmt.setString(2, nombre);
            stmt.setString(3, direccion);
            stmt.setString(4, localidad);
            stmt.setInt(5, jefe);
            stmt.setInt(6, departamento);
            stmt.setInt(7, oficio);
            stmt.registerOutParameter(8, Types.VARCHAR);

            stmt.execute();

            result = stmt.getString(8);

        } catch (SQLException e) {
            result = e.getMessage();
        }

        return result;
    }
	
}
