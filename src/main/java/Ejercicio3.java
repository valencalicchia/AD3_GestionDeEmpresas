import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DbConnection;
import Helpers.TablasHelper;
import Models.Departamento;
import Services.DepartamentoService;

public class Ejercicio3 {
	
    private static Connection conn;
    private static TablasHelper tablasHelper;
    private static DepartamentoService deptoServ;
	
	public static void main(String[] args) {
		
		conn = DbConnection.getDbConn();
		
		deptoServ = new DepartamentoService(conn);
		tablasHelper = new TablasHelper(conn);

        System.out.println("=================================================================================");
        System.out.println("EJERCICIO 3");
        System.out.println("Nombre del alumno: Valentina Alessandra Calicchia");
        System.out.println("=================================================================================");
        
		Integer [] departamentos = {11,111,11,112,114};
        Integer[] jefe = {101,10,10,101,101};
        Integer [] empresa = {1,123,123,1,2};
        
        for (int i = 0; i < departamentos.length; i++) {
        	
        	System.out.println((i+1)+")");
        	ejercicio3(departamentos[i], "GESTIÓN", "C/Mayor 17", "Madrid", jefe[i], empresa[i]);
        	System.out.println("\n");
        	
        }
		
		DbConnection.closeDbConn();
	}
	
	 public static void ejercicio3(Integer departamento, String nombre, String direccion,
			 
			String localidad, Integer jefe, Integer empresa) {
			if(!tablasHelper.existeColumna()) {
				tablasHelper.crearColumna();
			}
			String errores = verificarDatos(departamento, jefe, empresa);
			
			if (!errores.isEmpty()) {
			     System.out.print(errores);
			     System.out.print("HAY ERRORES, NO SE INSERTARÁ EL REGISTRO");
			     return;
			 }
			
			 if(deptoServ.insert(new Departamento(departamento, nombre, direccion, localidad, jefe, empresa))) {
			 	System.out.print("Registro INSERTADO....");
			 	actualizarContadorEmpresa(empresa);
			 }
	}
		
	 private static void actualizarContadorEmpresa(int codEmpresa) {
		 String updateSQL = "UPDATE EMPRESAS SET CONTDEPT = CONTDEPT + 1 WHERE CODEMPRE = ?";
		
		 try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
		     pstmt.setInt(1, codEmpresa);
		     pstmt.executeUpdate();
		     System.out.println("Se ha sumado 1 a la empresa: " + codEmpresa);
		 } catch (SQLException e) {
		     System.err.println("Error al actualizar el contador: " + e.getMessage());
		 }
	}
		
	private static String verificarDatos(int codDepartamento, int codJefe, int codEmpresa) {
		 StringBuilder errores = new StringBuilder();
		
		 if (tablasHelper.existeEnTabla("DEPARTAMENTOS", "CODDEPART", codDepartamento)) {
		     errores.append("EL DEPARTAMENTO ").append(codDepartamento).append(", ya existe\n");
		 }
		
		 if (!tablasHelper.existeEnTabla("EMPLEADOS", "CODEMPLE", codJefe)) {
		     errores.append("EL JEFE DE DEPARTAMENTO ").append(codJefe).append(", NO existe\n");
		 }
		
		 if (!tablasHelper.existeEnTabla("EMPRESAS", "CODEMPRE", codEmpresa)) {
		     errores.append("EL CODIGO DE EMPRESA ").append(codEmpresa).append(", NO existe\n");
		 }
		
		 return errores.toString();
	}
	
}
