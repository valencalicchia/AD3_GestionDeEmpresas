package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import Models.Oficio;

public class OficioService {

    private final Connection conn;

    public OficioService(Connection conn) {
        this.conn = conn;
    }

    public List<Oficio> get() {
        List<Oficio> jobs = new ArrayList<>();
        String query = "SELECT * FROM EMPRESAS.OFICIOS";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jobs.add(map(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los oficios de la base de datos: " + e.getMessage());
        }

        return jobs;
    }

    public Optional<Oficio> getPorId(int id) {
        String query = "SELECT * FROM EMPRESAS.OFICIOS WHERE CODOFICIO = ?";
        Oficio oficio = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    oficio = map(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar el oficio con ID " + id + ": " + e.getMessage());
        }

        return Optional.ofNullable(oficio);
    }

    private Oficio map(ResultSet rs) throws SQLException {
        int jobCode = rs.getInt("CODOFICIO");
        String name = rs.getString("NOMBRE");
        float salary = rs.getFloat("SALARIOMES");
        if (rs.wasNull()) {
            salary = 0.0f;
        }
        float payScale = rs.getFloat("PRECIOTRIENIO");
        if (rs.wasNull()) {
            payScale = 0.0f;
        }

        return new Oficio(jobCode, name, salary, payScale);
    }
}
