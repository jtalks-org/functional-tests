package utils;

import org.apache.commons.lang.ArrayUtils;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.ext.db2.Db2DataTypeFactory;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.mckoi.MckoiDataTypeFactory;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.netezza.NetezzaDataTypeFactory;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

/**
 * @author masyan
 */
public class DBUnitConfig {
	/**
	 * Array contains browser types.
	 */
	private static String[] browsers = {"db2", "h2", "hsqldb", "mckoi", "mssql", "mysql", "oracle", "oracle", "postgresql", "netezza"};

	/**
	 * @param db Name  of RDMBS. Values:  db2, h2, hsqldb, mckoi, mssql, mysql, oracle, oracle, postgresql, netezza
	 * @return Returns driver  for create RemoteWebDriver (Selenium)
	 */
	public static DefaultDataTypeFactory getDataTypeFactory(String db) {

		byte browserInd = (byte) ArrayUtils.indexOf(browsers, db);

		switch (browserInd) {
			case 0:
				return new Db2DataTypeFactory();
			case 1:
				return new H2DataTypeFactory();
			case 2:
				return new HsqldbDataTypeFactory();
			case 3:
				return new MckoiDataTypeFactory();
			case 4:
				return new MsSqlDataTypeFactory();
			case 5:
				return new MySqlDataTypeFactory();
			case 6:
				return new OracleDataTypeFactory();
			case 7:
				return new Oracle10DataTypeFactory();
			case 8:
				return new PostgresqlDataTypeFactory();
			case 9:
				return new NetezzaDataTypeFactory();
			default:
				return new HsqldbDataTypeFactory();
		}
	}
}
