package Helpers;

import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class InformeHelper {

	private final Connection conn;
	
	public InformeHelper(Connection conn) {
		this.conn = conn;
	}
	
	public void crear() {
        String workingDirectory = System.getProperty("user.dir");
        String filePath = workingDirectory + "/ListadoDepartamentos.pdf";

        try {        	
            InputStream reportStream = getClass().getClassLoader().getResourceAsStream( "resources/informe.jrxml");
            
            if (reportStream == null) {
                throw new RuntimeException("No se encontró la plantilla en resources/informe.jrxml");
            }
            
            Map<String, Object> params = new HashMap<>();
            params.put("titulo", "LISTADO DE DEPARTAMENTOS");
            params.put("autor", "Valentina Alessandra Calicchia");
            params.put("fecha", (new java.util.Date()).toString());
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint report = JasperFillManager.fillReport(jasperReport, params, conn);
            JasperViewer.viewReport(report, false);
            JasperExportManager.exportReportToPdfFile(report, filePath);
            System.out.println("ARCHIVOS CREADOS");
            
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
	}
	
}
