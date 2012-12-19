package ee.edisoft.edi.dao.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ee.edisoft.edi.dao.DaoException;
import ee.edisoft.edi.dao.UserDao;
import ee.edisoft.edi.dao.util.ConnectionManager;
import ee.edisoft.edi.dao.util.JdbcUtil;
import ee.edisoft.edi.model.User;
import ee.edisoft.edi.util.PropertiesUtil;

import ee.edisoft.security.SecureEncoder;

/**
 * This class represents a JDBC implementation
 * for the {@link UserDAO} interface.
 *
 */
public class JdbcUserDao implements UserDao {
	
	private static final Logger logger = Logger.getLogger(JdbcUserDao.class);
	
	private static final String SQL_QUERIES_RESOURCE = "users_sql.properties";

	private static final String INSERT_SQL;
	private static final String FIND_EMAIL_SQL;
	private static final String READ_WHERE_EMAIL_SQL;
	private static final String READ_WHERE_ID_SQL;
	private static final String READ_ALL_SQL;
	private static final String UPDATE_SQL;
	private static final String DELETE_SQL;
	private static final String DELETE_ALL_SQL;
	
	static {
		PropertiesUtil sqlResource = new PropertiesUtil(SQL_QUERIES_RESOURCE);
		
		INSERT_SQL = sqlResource.getProperty("insert");
		FIND_EMAIL_SQL = sqlResource.getProperty("find.email");
		READ_WHERE_EMAIL_SQL = sqlResource.getProperty("read.where.email");
		READ_WHERE_ID_SQL = sqlResource.getProperty("read.where.id");
		READ_ALL_SQL = sqlResource.getProperty("read.all");  
		UPDATE_SQL = sqlResource.getProperty("update");    
		DELETE_SQL = sqlResource.getProperty("delete");   
		DELETE_ALL_SQL = sqlResource.getProperty("delete.all");
	}
	
	private ConnectionManager connectionManager;
	
	public JdbcUserDao(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public int create(User user) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement findEmail = null;
		ResultSet rs = null;
		int rowCount = 0;
		try {
			con = connectionManager.getConnection();
			
			findEmail = con.prepareStatement(FIND_EMAIL_SQL);
			findEmail.setString(1, user.getEmail());
			rs = findEmail.executeQuery();
			if (rs.next()) {
				return 0;
			}
			
			ps = con.prepareStatement(INSERT_SQL);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getSalt());
			
			rowCount = ps.executeUpdate();
		} catch (SQLException e) {
			DaoException.logAndThrow(logger, "Error creating records in table 'users'.", e);
		} finally {
			JdbcUtil.close(rs, findEmail);
			JdbcUtil.close(ps, con);
		}
		
		return rowCount;
	}

	@Override
	public User read(String email) {
		if (email == null) {
			return null;
		}
		
		User user = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = connectionManager.getConnection();
			ps = con.prepareStatement(READ_WHERE_EMAIL_SQL);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("first_name"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(email);
				user.setPassword(rs.getString("password"));
				user.setSalt(rs.getString("salt"));
			}
		} catch (SQLException e) {
			DaoException.logAndThrow(logger,
					"Error reading a record for the email \"" + email +
					"\" from the table 'users'.", e);
		} finally {
			JdbcUtil.close(rs, ps, con);
		}
		
		return user;
	}

	@Override
	public List<User> readAll() {
		List<User> users = new ArrayList<User>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = connectionManager.getConnection();
			ps = con.prepareStatement(READ_ALL_SQL);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("first_name"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setSalt(rs.getString("salt"));
				
				users.add(user);
			}
		} catch (SQLException e) {
			DaoException.logAndThrow(logger, "Error reading all records from table 'users'.", e);
		} finally {
			JdbcUtil.close(rs, ps, con);
		}
		
		return users;
	}

	@Override
	public int update(User user) {
		User oldUser = null;
		
		User existingUser = read(user.getEmail());
		if ((existingUser != null) && (existingUser.getId() != user.getId())) {
			// updated email already exists and belongs to another user
			return 0;
		}
		
		/*
		 * At this point we are going have two variants:
		 * 1) oldUser == null, if none of the users has the updated email, or
		 * 2) oldUser is an existing user and his email was not updated,
		 * 	  so we don't have to use fetchOldUser
		 */
		oldUser = existingUser;
		
		Connection con = null;
		PreparedStatement updateUser = null;
		PreparedStatement fetchOldUser = null;
		ResultSet rs = null;
		int rowCount = 0;
		try {
			con = connectionManager.getConnection();
			
			if (oldUser == null) {
				fetchOldUser = con.prepareStatement(READ_WHERE_ID_SQL);
				fetchOldUser.setInt(1, user.getId());
				rs = fetchOldUser.executeQuery();
				
				oldUser = new User();
				if (rs.next()) {
					oldUser.setFirstName(rs.getString("first_name"));
					oldUser.setLastName(rs.getString("last_name"));
					oldUser.setEmail(rs.getString("email"));
					oldUser.setPassword(rs.getString("password"));
					oldUser.setSalt(rs.getString("salt"));
				}
			}
			
			updateUser = con.prepareStatement(UPDATE_SQL);
			updateUser.setString(1, ((user.getFirstName().isEmpty()) ? oldUser.getFirstName() : user.getFirstName()));
			updateUser.setString(2, ((user.getLastName().isEmpty()) ? oldUser.getLastName() : user.getLastName()));
			updateUser.setString(3, ((user.getEmail().isEmpty()) ? oldUser.getEmail() : user.getEmail()));
			if (!user.getPassword().isEmpty()) {
				String salt = SecureEncoder.generateSalt();
				String passwordHash = SecureEncoder.computeHash(user.getPassword(), salt);
				
				updateUser.setString(4, passwordHash);
				updateUser.setString(5, salt);
			} else {
				updateUser.setString(4, oldUser.getPassword());
				updateUser.setString(5, oldUser.getSalt());
			}
			updateUser.setInt(6, user.getId());
			
			rowCount = updateUser.executeUpdate();
		} catch (NoSuchAlgorithmException e) {
			DaoException.logAndThrow(logger, "Error generating password hash.", e);
		} catch (SQLException e) {
			DaoException.logAndThrow(logger, "Error while updating records in table 'users'.", e);
		} finally {
			JdbcUtil.close(rs, fetchOldUser);
			JdbcUtil.close(updateUser, con);
		}
		
		return rowCount;
	}

	@Override
	public int delete(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		int rowCount = 0;
		try {
			con = connectionManager.getConnection();
			ps = con.prepareStatement(DELETE_SQL);
			ps.setInt(1, id);
			rowCount = ps.executeUpdate();
		} catch (SQLException e) {
			DaoException.logAndThrow(logger, "Error while deleting a record for the user with id \"" +
												id + "\" in table 'users'.", e);
		} finally {
			JdbcUtil.close(ps, con);
		}
		
		return rowCount;
	}

	@Override
	public void deleteAll() {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = connectionManager.getConnection();
			ps = con.prepareStatement(DELETE_ALL_SQL);
			ps.execute();
		} catch (SQLException e) {
			DaoException.logAndThrow(logger, "Error while deleting all records in table 'users'.", e);
		} finally {
			JdbcUtil.close(ps, con);
		}
	}

}
