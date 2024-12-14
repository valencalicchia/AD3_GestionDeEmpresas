package Services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import Models.Empleado;

public class EmpleadoService {

    private final Connection conn;

    public EmpleadoService(Connection conn) {
        this.conn = conn;
    }

    public List<Empleado> get() {
        List<Empleado> employees = new ArrayList<>();
        String query = "SELECT * FROM EMPRESAS.EMPLEADOS";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                employees.add(map(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los empleados: " + e.getMessage());
        }

        return employees;
    }

    public Optional<Empleado> getPorId(int id) {
        String query = "SELECT * FROM EMPRESAS.EMPLEADOS WHERE CODEMPLE = ?";
        Empleado employee = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employee = map(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar el empleado con ID " + id + ": " + e.getMessage());
        }

        return Optional.ofNullable(employee);
    }

    public List<Empleado> getPorDepartamento(int departmentId) {
        List<Empleado> employees = new ArrayList<>();
        String query = "SELECT * FROM EMPRESAS.EMPLEADOS WHERE CODDEPART = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, departmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(map(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los empleados del departamento con ID " + departmentId + ": " + e.getMessage());
        }

        return employees;
    }

    private Empleado map(ResultSet rs) throws SQLException {
        int employeeCode = rs.getInt("CODEMPLE");
        String name = rs.getString("NOMBRE");
        String address = rs.getString("DIRECCION");
        String city = rs.getString("POBLACION");
        Date hireDate = rs.getDate("FECHAALTA");
        
        int managerCode = rs.getInt("CODENCARGADO");
        if (rs.wasNull()) {
            managerCode = 0;
        }

        int departmentCode = rs.getInt("CODDEPART");
        if (rs.wasNull()) {
            departmentCode = 0;
        }

        int jobCode = rs.getInt("CODOFICIO");
        if (rs.wasNull()) {
            jobCode = 0;
        }

        return new Empleado(employeeCode, name, address, city, hireDate, managerCode, departmentCode, jobCode);
    }
}
