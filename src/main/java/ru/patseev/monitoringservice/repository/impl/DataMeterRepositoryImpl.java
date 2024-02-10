package ru.patseev.monitoringservice.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.domain.DataMeter;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.repository.DataMeterRepository;

import java.sql.*;
import java.util.*;

/**
 * The DataMeterRepositoryImpl class is an implementation of the DataMeterRepository interface.
 * It provides methods for interacting with user data storage.
 */
@RequiredArgsConstructor
public class DataMeterRepositoryImpl implements DataMeterRepository {

	/**
	 * Provider that provides methods for working with database connections.
	 */
	private final ConnectionManager connectionManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<DataMeter> findLastDataMeter(int userId) {
		final String selectLastDataSql =
				"SELECT * FROM monitoring_service.meters_data WHERE user_id = ? ORDER BY meter_data_id DESC LIMIT 1";
		Optional<DataMeter> optionalDataMeter = Optional.empty();

		try (Connection connection = connectionManager.takeConnection();
			 PreparedStatement statement = connection.prepareStatement(selectLastDataSql)) {

			statement.setInt(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					return optionalDataMeter;
				}
				DataMeter dataMeter = extractDataMeter(resultSet);
				optionalDataMeter = Optional.of(dataMeter);
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return optionalDataMeter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDataMeter(DataMeter dataMeter) {
		Connection connection = null;

		try {
			connection = connectionManager.takeConnection();
			connection.setAutoCommit(false);

			saveDataMeterWithTransaction(connection, dataMeter);

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
	public List<DataMeter> getMeterDataForSpecifiedMonth(int userId, int month) {
		final String selectMeterDataForMonthSql =
				"SELECT * FROM monitoring_service.meters_data WHERE user_id = ? AND extract(month from submission_date) = ?";
		List<DataMeter> dataMeterList = new ArrayList<>();

		try (Connection connection = connectionManager.takeConnection();
			 PreparedStatement statement = connection.prepareStatement(selectMeterDataForMonthSql)) {

			statement.setInt(1, userId);
			statement.setInt(2, month);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					DataMeter dataMeter = extractDataMeter(resultSet);
					dataMeterList.add(dataMeter);
				}
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return dataMeterList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataMeter> getAllMeterData(int userId) {
		final String selectAllMeterDataSql = "SELECT * FROM monitoring_service.meters_data WHERE user_id = ?";
		List<DataMeter> dataMetersList = new ArrayList<>();

		try (Connection connection = connectionManager.takeConnection();
			 PreparedStatement statement = connection.prepareStatement(selectAllMeterDataSql)) {
			statement.setInt(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					DataMeter dataMeter = extractDataMeter(resultSet);
					dataMetersList.add(dataMeter);
				}
				return dataMetersList;
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return dataMetersList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, List<DataMeter>> getDataFromAllMeterUsers() {
		final String selectAllMeterDataSql = "SELECT * FROM monitoring_service.meters_data";
		Map<Integer, List<DataMeter>> allMeterData = new HashMap<>();

		try (Connection connection = connectionManager.takeConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(selectAllMeterDataSql)) {

			while (resultSet.next()) {
				DataMeter dataMeter = extractDataMeter(resultSet);
				if (!allMeterData.containsKey(dataMeter.getUserId())) {
					allMeterData.put(dataMeter.getUserId(), new ArrayList<>());
				}
				allMeterData.get(dataMeter.getUserId()).add(dataMeter);
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return allMeterData;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkMeterDataForCurrentMonth(int userId, int meterTypeId) {
		final String checkMonthlyMeterData = """
				SELECT COUNT(*) FROM monitoring_service.meters_data
				WHERE user_id = ?
				AND meter_type_id = ?
				AND EXTRACT(MONTH FROM submission_date) = EXTRACT(MONTH FROM current_date)
				""";

		boolean dataExists = false;

		try (Connection connection = connectionManager.takeConnection();
			 PreparedStatement statement = connection.prepareStatement(checkMonthlyMeterData)) {

			statement.setInt(1, userId);
			statement.setInt(2, meterTypeId);

			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();
				dataExists = resultSet.getInt(1) > 0;
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return dataExists;
	}

	/**
	 * Stores the DataMeter within a transaction in the database.
	 *
	 * @param connection The database connection to use for the transaction.
	 * @param dataMeter  The DataMeter object to be inserted into the database.
	 * @throws SQLException If a database access error occurs or this method is called on a closed connection.
	 */
	private void saveDataMeterWithTransaction(Connection connection, DataMeter dataMeter) throws SQLException {
		final String insertMeterDataSql =
				"INSERT INTO monitoring_service.meters_data (submission_date, value, meter_type_id, user_id) VALUES (?, ?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(insertMeterDataSql)) {

			statement.setTimestamp(1, dataMeter.getSubmissionDate());
			statement.setLong(2, dataMeter.getValue());
			statement.setInt(3, dataMeter.getMeterTypeId());
			statement.setInt(4, dataMeter.getUserId());
			statement.executeUpdate();
		}
	}

	/**
	 * Extracts and creates a DataMeter object from the provided ResultSet.
	 *
	 * @param resultSet The ResultSet containing data from a database query.
	 * @return A DataMeter object representing the extracted data.
	 * @throws SQLException If a database access error occurs or this method is
	 *                      called on a closed result set.
	 */
	private DataMeter extractDataMeter(ResultSet resultSet) throws SQLException {
		int meterDataId = resultSet.getInt("meter_data_id");
		Timestamp submissionDate = resultSet.getTimestamp("submission_date");
		long value = resultSet.getLong("value");
		int meterTypeId = resultSet.getInt("meter_type_id");
		int userId = resultSet.getInt("user_id");

		return new DataMeter(meterDataId, submissionDate, value, meterTypeId, userId);
	}
}