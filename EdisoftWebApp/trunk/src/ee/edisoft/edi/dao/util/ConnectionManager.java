package ee.edisoft.edi.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface that abstracts getting {@link Connection} object.
 *
 */
public interface ConnectionManager {

	public Connection getConnection() throws SQLException;
}
