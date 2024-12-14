import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import DB.DbConnection;
import Helpers.ScannerHelper;
import Models.*;
import Services.*;

public class Ejercicio1 {

    private static EmpresaService empresaServ;
    private static DepartamentoService deptoServ;
    private static EmpleadoService empleadoServ;
    private static OficioService oficioServ;

	
	public static void main(String[] args) {
		
		Connection conn = DbConnection.getDbConn();
		
        empresaServ = new EmpresaService(conn);
        deptoServ = new DepartamentoService(conn);
        empleadoServ = new EmpleadoService(conn);
        oficioServ = new OficioService(conn);
        
		run();
		
		DbConnection.closeDbConn();
	}
	
	
	  public static void run() {
	    	
	    	Scanner teclado = new Scanner(System.in);
	    	
	        try {
	            System.out.println("=================================================================================");
	            System.out.println("EJERCICIO 1");
	            System.out.println("Nombre del alumno: Valentina Alessandra Calicchia");
	            System.out.println("=================================================================================");

	            while (true) {
	                System.out.print("Introduce un código de empresa: ");
	                
	                int codEmpresa = ScannerHelper.recogerValor(teclado);

	                if (codEmpresa == 0) {
	                    break;
	                }

	                mostrarEmpresa(codEmpresa);
	            }

	        } catch (Exception e) {
	            System.err.println("Error inesperado: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	        	teclado.close();
	        }
	    }

	    private static void mostrarEmpresa(int codEmpresa) {
	        Optional<Empresa> empresaOpt = empresaServ.getPorId(codEmpresa);

	        if (empresaOpt.isPresent()) {
	            List<Departamento> departamentos = deptoServ.getPorEmpresa(codEmpresa);
	            Empresa emp = empresaOpt.get();

	            System.out.println("---------------------------------------------------------------------------------");
	            System.out.printf("COD-EMPRESA: %-7d NOMBRE: %-20s%n", codEmpresa, emp.getNombre());
	            System.out.printf("DIRECCIÓN: %-30s Número de departamentos: %-2d%n", emp.getDireccion(), departamentos.size());
	            System.out.println("---------------------------------------------------------------------------------");

	            mostrarDepartamentos(departamentos);

	        } else {
	            System.out.println("---------------------------------------------------------------------------------");
	            System.out.println("EL CÓDIGO DE EMPRESA NO EXISTE");
	            System.out.println("---------------------------------------------------------------------------------");
	        }
	    }

	    private static void mostrarDepartamentos(List<Departamento> departamentos) {
	        if (!departamentos.isEmpty()) {
	            for (Departamento dpto : departamentos) {
	                System.out.printf("COD-DEPARTAMENTO: %d NOMBRE: %s LOCALIDAD: %s%n",
	                        dpto.getCodDepart(), dpto.getNombre(), dpto.getLocalidad());
	                System.out.println("---------------------------------------------------------------------------------");

	                List<Empleado> empleados = empleadoServ.getPorDepartamento(dpto.getCodDepart());

	                if (!empleados.isEmpty()) {
	                    mostrarEmpleados(empleados);
	                } 
	                
	                String jefeDpto = empleadoServ.getPorId(dpto.getCodJefeDepartamento())
	                        .map(Empleado::getNombre)
	                        .orElse("***");

	                System.out.printf("\tNúmero de empleados del departamento: %d%n", empleados.size());
	                System.out.printf("\tNombre del jefe del departamento: %s%n", jefeDpto);
	                System.out.println();
	            }
	        } else {
	            System.out.println("LA EMPRESA NO TIENE DEPARTAMENTOS");
	        }
	        System.out.println("---------------------------------------------------------------------------------");
	    }

	    private static void mostrarEmpleados(List<Empleado> empleados) {
	        System.out.printf("\t%-7s %-20s %-25s %-20s%n", "COD-EMP", "NOMBRE", "OFICIO", "NOMBRE ENCARGADO");
	        System.out.println("\t------- -------------------- ------------------------- ----------------");

	        for (Empleado emp : empleados) {
	            String jefe = emp.getCodEncargado() != 0
	                    ? empleadoServ.getPorId(emp.getCodEncargado()).map(Empleado::getNombre).orElse("***")
	                    : "***";
	            String jobName = oficioServ.getPorId(emp.getCodOficio()).map(Oficio::getNombre).orElse("***");
	            System.out.printf("\t%7d %-20s %-25s %-20s%n", emp.getCodEmple(), emp.getNombre(), jobName, jefe);
	        }
	        System.out.println();

	    }
}
