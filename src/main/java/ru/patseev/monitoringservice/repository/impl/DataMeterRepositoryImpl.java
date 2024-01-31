package ru.patseev.monitoringservice.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.manager.ConnectionProvider;
import ru.patseev.monitoringservice.domain.DataMeter;
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
	private final ConnectionProvider connectionProvider;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<DataMeter> findLastDataMeter(int userId) {
		final String selectLastDataSql = "SELECT * FROM meters_data WHERE user_id = ? ORDER BY meter_data_id DESC LIMIT 1";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionProvider.takeConnection();
			statement = connection.prepareStatement(selectLastDataSql);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();

			if (!resultSet.next()) {
				return Optional.empty();
			}

			DataMeter dataMeter = extractData(resultSet);
			return Optional.of(dataMeter);

		} catch (SQLException e) {
			System.out.println("Ошибка операции");
			return Optional.empty();
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement, resultSet);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDataMeter(DataMeter dataMeter) {
		final String insertMeterDataSql =
				"INSERT INTO meters_data (submission_date, value, meter_type_id, user_id) VALUES (?, ?, ?, ?)";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionProvider.takeConnection();
			connection.setAutoCommit(false);

			statement = connection.prepareStatement(insertMeterDataSql);
			statement.setTimestamp(1, dataMeter.getSubmissionDate());
			statement.setLong(2, dataMeter.getValue());
			statement.setInt(3, dataMeter.getMeterTypeId());
			statement.setInt(4, dataMeter.getUserId());
			statement.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				System.out.println("Ошибка отката");
			}
			System.out.println("Ошибка операции");
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataMeter> getMeterDataForSpecifiedMonth(int userId, int month) {
		final String selectMeterDataForMonthSql =
				"SELECT * FROM meters_data WHERE user_id = ? AND extract(month from submission_date) = ?";

		List<DataMeter> dataMeterList = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionProvider.takeConnection();
			statement = connection.prepareStatement(selectMeterDataForMonthSql);
			statement.setInt(1, userId);
			statement.setInt(2, month);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				DataMeter dataMeter = extractData(resultSet);
				dataMeterList.add(dataMeter);
			}
		} catch (SQLException e) {
			System.out.println("Ошибка операции");
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement, resultSet);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
		return dataMeterList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DataMeter> getAllMeterData(int userId) {
		final String selectAllMeterDataSql = "SELECT * FROM meters_data WHERE user_id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionProvider.takeConnection();
			statement = connection.prepareStatement(selectAllMeterDataSql);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();

			ArrayList<DataMeter> dataMetersList = new ArrayList<>();

			while (resultSet.next()) {
				DataMeter dataMeter = extractData(resultSet);
				dataMetersList.add(dataMeter);
			}
			return dataMetersList;
		} catch (SQLException e) {
			System.out.println("Ошибка операции");
			return new ArrayList<>();
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement, resultSet);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, List<DataMeter>> getDataFromAllMeterUsers() {
		final String selectAllMeterDataSql = "SELECT * FROM meters_data";
		Map<Integer, List<DataMeter>> allMeterData = new HashMap<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionProvider.takeConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectAllMeterDataSql);

			while (resultSet.next()) {
				DataMeter dataMeter = extractData(resultSet);
				if (!allMeterData.containsKey(dataMeter.getUserId())) {
					allMeterData.put(dataMeter.getUserId(), new ArrayList<>());
				}
				allMeterData.get(dataMeter.getUserId()).add(dataMeter);
			}
		} catch (SQLException e) {
			System.out.println("Ошибка операции");
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement, resultSet);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
		return allMeterData;
	}

	/**
	 * Extracts and creates a {@code DataMeter} object from the provided {@code ResultSet}.
	 *
	 * @param resultSet The ResultSet containing data from a database query.
	 * @return A {@code DataMeter} object representing the extracted data.
	 * @throws SQLException If a database access error occurs or this method is
	 *                      called on a closed result set.
	 */
	private DataMeter extractData(ResultSet resultSet) throws SQLException {
		int meterDataId = resultSet.getInt("meter_data_id");
		Timestamp submissionDate = resultSet.getTimestamp("submission_date");
		long value = resultSet.getLong("value");
		int meterTypeId = resultSet.getInt("meter_type_id");
		int userId = resultSet.getInt("user_id");

		return new DataMeter(meterDataId, submissionDate, value, meterTypeId, userId);
	}
}