import java.sql.Connection;
import java.util.Scanner;

import DB.DbConnection;
import Helpers.FunctionHelper;
import Helpers.ProcedureHelper;
import Helpers.ScannerHelper;

public class Ejercicio2 {
	
    private static FunctionHelper funcHelper;
    private static ProcedureHelper procHelper;
	
	public static void main(String[] args) {
		
		Connection conn = DbConnection.getDbConn();
		
    	funcHelper = new FunctionHelper(conn);
    	procHelper = new ProcedureHelper(conn);
		
		run();
		
		DbConnection.closeDbConn();
	}
	
	public static void run() {
    	
        try {
            System.out.println("=================================================================================");
            System.out.println("EJERCICIO 2");
            System.out.println("Nombre del alumno: Valentina Alessandra Calicchia");
            System.out.println("=================================================================================");

            funcHelper.crearFuncionEj2();
            procHelper.crearProcedureEj2();
            
            pruebasFuncion();
            pruebasProcedure();
            

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void pruebasFuncion(){
    	Scanner teclado = new Scanner(System.in);
    	while (true) {
            System.out.print("Introduce un código de empresa: ");
            
            int codEmpresa = ScannerHelper.recogerValor(teclado);

            if (codEmpresa == 0) {
                break;
            }
            
            Integer[] resultado = funcHelper.ejecutarFuncionEj2(codEmpresa);
            System.out.println("\tNúmero de departamentos: " + resultado[0]);
            System.out.println("\tNúmero de empleados: " + resultado[1]);
            System.out.println("---------------------------------------------------------------------------------");
        }
    	
    	teclado.close();
    }
    
    public static void pruebasProcedure() {
    	
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("PROBANDO LAS LLAMADAS AL PROCEDIMIENTO");
        System.out.println("---------------------------------------------------------------------------------");

        Integer[] empleados = {301, 1, 1, 1, 301, 1, 1};
        String nombre = "Maria";
        String direccion = "C/Peñaflor 2";
        String localidad = "Berrocalejo";
        Integer[] jefes = {101, 1, 1, 101, 1, 101, 101};
        Integer[] departamentos = {11, 1, 1, 1, 1, 11, 11};
        Integer[] trabajos = {250, 1, 250, 1, 1, 1, 1};

        for (int i = 0; i < empleados.length; i++) {
            
            String procedureResult = procHelper.PruebaProcedimiento(empleados[i], nombre, direccion, localidad, jefes[i], departamentos[i], trabajos[i]);
            System.out.println(procedureResult);
            System.out.println("---------------------------------------------------------------------------------");
        }
    }
}
