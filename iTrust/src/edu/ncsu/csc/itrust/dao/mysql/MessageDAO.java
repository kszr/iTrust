package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.loaders.MessageBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for the logging mechanism.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 *  
 * 
 */
public class MessageDAO {
	private DAOFactory factory;
	private MessageBeanLoader mbLoader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public MessageDAO(DAOFactory factory) {
		this.factory = factory;
		this.mbLoader = new MessageBeanLoader();
	}

	/**
	 * Gets all the messages for a certain user MID.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of MessageBeans.
	 * @throws SQLException
	 * @throws DBException 
	 */

	public List<MessageBean> getMessagesFor(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM message WHERE to_id = ? ORDER BY sent_date DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();

			List<MessageBean> mbList = this.mbLoader.loadList(rs);
			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}

	}

	/**
	 * Gets all the messages for a certain user MID sorted by ascending time.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of MessageBeans.
	 * @throws SQLException
	 * @throws DBException 
	 */
	public List<MessageBean> getMessagesTimeAscending(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM message WHERE to_id = ? ORDER BY sent_date ASC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();

			List<MessageBean> mbList = this.mbLoader.loadList(rs);

			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}

	}

	/**
	 * Gets all the messages for a certain user MID sorted by name ascending.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of MessageBeans.
	 * @throws SQLException
	 * @throws DBException 
	 */
	public List<MessageBean> getMessagesNameAscending(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs;
		try{
			conn = factory.getConnection();
			if(mid >= 999999999){
				ps = conn.prepareStatement("SELECT message.* FROM message, patients WHERE message.from_id=patients.mid AND message.to_id=? ORDER BY patients.lastName ASC, patients.firstName ASC, message.sent_date ASC");
				ps.setLong(1, mid);
				rs = ps.executeQuery();
			}
			else{
				ps = conn.prepareStatement("SELECT message.* FROM message, personnel WHERE message.from_id=personnel.mid AND message.to_id=? ORDER BY personnel.lastName ASC, personnel.firstName ASC, message.sent_date ASC");
				ps.setLong(1, mid);
				rs = ps.executeQuery();
			}

			List<MessageBean> mbList = this.mbLoader.loadList(rs);

			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Gets all the messages for a certain user MID sorted by name descending.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of MessageBeans.
	 * @throws SQLException
	 * @throws DBException 
	 */
	public List<MessageBean> getMessagesNameDescending(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs;
		try{
			conn = factory.getConnection();
			if(mid >= 999999999){
				ps = conn.prepareStatement("SELECT message.* FROM message, patients WHERE message.from_id=patients.mid AND message.to_id=? ORDER BY patients.lastName DESC, patients.firstName DESC, message.sent_date DESC");
				ps.setLong(1, mid);
				rs = ps.executeQuery();
			}
			else{
				ps = conn.prepareStatement("SELECT message.* FROM message, personnel WHERE message.from_id=personnel.mid AND message.to_id=? ORDER BY personnel.lastName DESC, personnel.firstName DESC, message.sent_date DESC");
				ps.setLong(1, mid);
				rs = ps.executeQuery();
			}

			List<MessageBean> mbList = this.mbLoader.loadList(rs);
			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}

	}

	/**
	 * Gets all the messages from a certain user MID.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of MessageBeans.
	 * @throws SQLException
	 * @throws DBException 
	 */

	public List<MessageBean> getMessagesFrom(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM message WHERE from_id = ? ORDER BY sent_date DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();

			List<MessageBean> mbList = this.mbLoader.loadList(rs);

			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}

	}

	/**
	 * Gets all the messages for a certain user MID sorted by ascending time.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of MessageBeans.
	 * @throws SQLException
	 */
	public List<MessageBean> getMessagesFromTimeAscending(long mid) throws DBException, SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM message WHERE from_id = ? ORDER BY sent_date ASC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();

			List<MessageBean> mbList = this.mbLoader.loadList(rs);

			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}


	}

	/**
	 * Gets all the messages for a certain user MID sorted by name ascending.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of MessageBeans.
	 * @throws SQLException
	 * @throws DBException 
	 */
	public List<MessageBean> getMessagesFromNameAscending(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs;
		try{
			conn = factory.getConnection();
			if(mid >= 999999999){
				ps = conn.prepareStatement("SELECT message.* FROM message, patients WHERE message.to_id=patients.mid AND message.from_id=? ORDER BY patients.lastName ASC, patients.firstName ASC, message.sent_date ASC");
				ps.setLong(1, mid);
				rs = ps.executeQuery();
			}
			else{
				ps = conn.prepareStatement("SELECT message.* FROM message, personnel WHERE message.to_id=personnel.mid AND message.from_id=? ORDER BY personnel.lastName ASC, personnel.firstName ASC, message.sent_date ASC");
				ps.setLong(1, mid);
				rs = ps.executeQuery();
			}

			List<MessageBean> mbList = this.mbLoader.loadList(rs);

			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Gets all the messages for a certain user MID sorted by name descending.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of MessageBeans.
	 * @throws SQLException
	 * @throws DBException 
	 */
	public List<MessageBean> getMessagesFromNameDescending(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs;
		try{
			conn = factory.getConnection();
			if(mid >= 999999999){
				ps = conn.prepareStatement("SELECT message.* FROM message, patients WHERE message.to_id=patients.mid AND message.from_id=? ORDER BY patients.lastName DESC, patients.firstName DESC, message.sent_date DESC");
				ps.setLong(1, mid);
				rs = ps.executeQuery();
			}
			else{
				ps = conn.prepareStatement("SELECT message.* FROM message, personnel WHERE message.to_id=personnel.mid AND message.from_id=? ORDER BY personnel.lastName DESC, personnel.firstName DESC, message.sent_date DESC");
				ps.setLong(1, mid);
				rs = ps.executeQuery();
			}

			List<MessageBean> mbList = this.mbLoader.loadList(rs);

			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}


	}

	/**
	 * Adds a message to the database.
	 * @param mBean A bean representing the message to be added.
	 * @throws SQLException
	 * @throws DBException 
	 */
	public void addMessage(MessageBean mBean) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"INSERT INTO message (from_id, to_id, sent_date, message, subject, been_read, parent_msg_id, original_msg_id) "
							+ "  VALUES (?, ?, NOW(), ?, ?, ?, ?, ?)");
			this.mbLoader.loadParameters(ps, mBean);

			ps.executeUpdate();
			ps.close();

		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}

	}

	public void updateRead(MessageBean mBean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE message SET been_read=1 WHERE message_id=?");
			ps.setLong(1, mBean.getMessageId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public List<MessageBean> getCCdMessages(long refID) throws SQLException, DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			ResultSet rs;

			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM message WHERE original_msg_id=?");
			ps.setLong(1, refID);
			rs = ps.executeQuery();

			List<MessageBean> mbList = this.mbLoader.loadList(rs);

			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}


	}

	public List<MessageBean> getMessagesFromTimeDescending(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM message WHERE from_id = ? ORDER BY sent_date DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();

			List<MessageBean> mbList = this.mbLoader.loadList(rs);

			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, ps);
		}
	}

	public List<MessageBean> getMessagesTimeDescending(long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM message WHERE to_id = ? ORDER BY sent_date DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();

			List<MessageBean> mbList = this.mbLoader.loadList(rs);

			rs.close();
			ps.close();
			return mbList;
		} catch(SQLException e) {

			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}
	}

}