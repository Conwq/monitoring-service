package ru.patseev.monitoringservice.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.patseev.monitoringservice.domain.MeterType;
import ru.patseev.monitoringservice.exception.MeterTypeNotFoundException;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.repository.MeterTypeRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The MeterTypeRepositoryImpl class is an implementation of the MeterTypeRepository interface.
 */
@Repository
public class MeterTypeRepositoryImpl implements MeterTypeRepository {


	/**
	 * The data source used for obtaining a database connection.
	 */
	private final DataSource dataSource;

	/**
	 * Provider that provides methods for working with database connections.
	 */
	private final ConnectionManager connectionManager;

	/**
	 * Constructs an MeterTypeRepositoryImpl object with the provided DataSource and ConnectionManager.
	 *
	 * @param dataSource        The DataSource instance used for obtaining database connections.
	 * @param connectionManager The ConnectionManager instance providing methods for working with connections.
	 */
	@Autowired
	public MeterTypeRepositoryImpl(DataSource dataSource, ConnectionManager connectionManager) {
		this.dataSource = dataSource;
		this.connectionManager = connectionManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MeterType> findAllMeterType() {
		final String selectMeterTypes = "SELECT * FROM monitoring_service.meter_types";
		List<MeterType> meterTypeList = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(selectMeterTypes)) {
			while (resultSet.next()) {
				MeterType meterType = extractMeterType(resultSet);
				meterTypeList.add(meterType);
			}
		} catch (SQLException e) {
			System.out.println("Ошибка операции");
		}
		return meterTypeList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveMeterType(MeterType meterType) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			saveMeterTypeWithTransaction(connection, meterType);

			connection.commit();
		} catch (SQLException e) {
			connectionManager.rollbackTransaction(connection);
		} finally {
			connectionManager.closeConnection(connection);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MeterType getMeterTypeById(int meterTypeId) {
		final String selectMeterTypeSql = "SELECT * FROM monitoring_service.meter_types WHERE meter_type_id = ?";
		MeterType meterType = null;

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(selectMeterTypeSql)) {
			statement.setInt(1, meterTypeId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					throw new MeterTypeNotFoundException("Счетчик не найден");
				}
				meterType = extractMeterType(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return meterType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkMeterTypeExistence(String typeName) {
		final String selectMeterTypeSql = "SELECT * FROM monitoring_service.meter_types WHERE type_name = ?";
		boolean meterTypeExistence = false;
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(selectMeterTypeSql)) {
			statement.setString(1, typeName);
			try (ResultSet resultSet = statement.executeQuery()) {
				meterTypeExistence = resultSet.next();
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return meterTypeExistence;
	}

	/**
	 * Extracts and creates a MeterType object from the provided ResultSet.
	 *
	 * @param resultSet The ResultSet containing data from a database query.
	 * @return A MeterType object representing the extracted data.
	 * @throws SQLException If a database access error occurs or this method is called on a closed result set.
	 */
	private MeterType extractMeterType(ResultSet resultSet) throws SQLException {
		int meterTypeId = resultSet.getInt("meter_type_id");
		String typeName = resultSet.getString("type_name");
		return new MeterType(meterTypeId, typeName);
	}

	/**
	 * Stores the MeterType within a transaction in the database.
	 *
	 * @param connection The database connection to use for the transaction.
	 * @param meterType  The MeterType object to be inserted into the database.
	 * @throws SQLException If a database access error occurs or this method is called on a closed connection.
	 */
	private void saveMeterTypeWithTransaction(Connection connection, MeterType meterType) throws SQLException {
		final String insertMeterTypeSql = "INSERT INTO monitoring_service.meter_types (type_name) VALUES (?)";

		try (PreparedStatement statement = connection.prepareStatement(insertMeterTypeSql)) {
			statement.setString(1, meterType.getTypeName());
			statement.executeUpdate();
		}
	}
}