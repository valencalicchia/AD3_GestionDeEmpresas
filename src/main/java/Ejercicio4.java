import DB.DbConnection;

import Helpers.InformeHelper;

public class Ejercicio4 {
	
	public static void main(String[] args) {
		InformeHelper helper = new InformeHelper(DbConnection.getDbConn());
		helper.crear();
		DbConnection.closeDbConn();
	}
}
