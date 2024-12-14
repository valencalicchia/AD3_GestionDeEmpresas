package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import Models.Departamento;

public class DepartamentoService {

    private final Connection conn;

    public DepartamentoService(Connection conn) {
        this.conn = conn;
    }

    public List<Departamento> get() {
        List<Departamento> departments = new ArrayList<>();
        String query = "SELECT * FROM EMPRESAS.DEPARTAMENTOS";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                departments.add(map(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los departamentos: " + e.getMessage());
        }

        return departments;
    }

    public Optional<Departamento> getPorId(int id) {
        String query = "SELECT * FROM EMPRESAS.DEPARTAMENTOS WHERE CODDEPART = ?";
        Departamento departamento = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    departamento = map(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar el departamento por ID: " + e.getMessage());
        }

        return Optional.ofNullable(departamento);
    }

    public List<Departamento> getPorEmpresa(int companyId) {
        List<Departamento> departments = new ArrayList<>();
        String query = "SELECT * FROM EMPRESAS.DEPARTAMENTOS WHERE CODEMPRE = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, companyId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    departments.add(map(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los departamentos por empresa: " + e.getMessage());
        }

        return departments;
    }

    private Departamento map(ResultSet rs) throws SQLException {
        int departmentId = rs.getInt("CODDEPART");
        String name = rs.getString("NOMBRE");
        String address = rs.getString("DIRECCION");
        String city = rs.getString("LOCALIDAD");
        int managerId = rs.getInt("CODJEFEDEPARTAMENTO");
        int companyId = rs.getInt("CODEMPRE");

        return new Departamento(departmentId, name, address, city, managerId, companyId);
    }
    
    
    public boolean insert(Departamento dpto) {
		String query = "INSERT INTO EMPRESAS.DEPARTAMENTOS (CODDEPART, NOMBRE, DIRECCION, LOCALIDAD, CODJEFEDEPARTAMENTO, CODEMPRE) VALUES (?,?,?,?,?,?)";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			
			stmt.setInt(1, dpto.getCodDepart());
			stmt.setString(2, dpto.getNombre());
			stmt.setString(3, dpto.getDireccion());
			stmt.setString(4, dpto.getLocalidad());
			stmt.setInt(5, dpto.getCodJefeDepartamento());
			stmt.setInt(6, dpto.getCodEmpre());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}