package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import Models.Empresa;


public class EmpresaService {

    private final Connection conn;

    public EmpresaService(Connection conn) {
        this.conn = conn;
    }

    public List<Empresa> get() {
        List<Empresa> companies = new ArrayList<>();
        String query = "SELECT * FROM EMPRESAS.EMPRESAS";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                companies.add(map(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener las empresas de la base de datos: " + e.getMessage());
        }

        return companies;
    }

    public Optional<Empresa> getPorId(int id) {
        String query = "SELECT * FROM EMPRESAS.EMPRESAS WHERE CODEMPRE = ?";
        Empresa empresa = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empresa = map(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar la empresa con ID " + id + ": " + e.getMessage());
        }

        return Optional.ofNullable(empresa);
    }

    private Empresa map(ResultSet rs) throws SQLException {
        int empresaCode = rs.getInt("CODEMPRE");
        String name = rs.getString("NOMBRE");
        String address = rs.getString("DIRECCION");
        String phone = rs.getString("TLF");
        float budget = rs.getFloat("PRESUPUESTO");
        if (rs.wasNull()) {
            budget = 0.0f;
        }
        int sectorCode = rs.getInt("CODSECTOR");
        int headquarters = rs.getInt("SEDE");

        return new Empresa(empresaCode, name, address, phone, budget, sectorCode, headquarters);
    }
}
