package ru.patseev.monitoringservice.meter_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.manager.ConnectionProvider;
import ru.patseev.monitoringservice.meter_service.domain.MeterType;
import ru.patseev.monitoringservice.meter_service.repository.MeterTypeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The MeterTypeRepositoryImpl class is an implementation of the MeterTypeRepository interface.
 * It provides methods for interacting with meter type data storage.
 */
@RequiredArgsConstructor
public class MeterTypeRepositoryImpl implements MeterTypeRepository {

	/**
	 * Provider that provides methods for working with database connections.
	 */
	private final ConnectionProvider connectionProvider;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MeterType> findAllMeterType() {
		final String selectMeterTypes = "SELECT * FROM meter_types";

		try (Connection connection = connectionProvider.takeConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(selectMeterTypes)) {

			List<MeterType> meterTypeList = new ArrayList<>();

			while (resultSet.next()) {
				MeterType meterType = extractData(resultSet);
				meterTypeList.add(meterType);
			}
			return meterTypeList;
		} catch (SQLException e) {
			System.out.println("Ошибка операции");
			return new ArrayList<>();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveMeterType(MeterType meterType) {
		final String insertMeterTypeSql = "INSERT INTO meter_types (type_name) VALUES (?)";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionProvider.takeConnection();
			connection.setAutoCommit(false);

			statement = connection.prepareStatement(insertMeterTypeSql);
			statement.setString(1, meterType.getTypeName());
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
	public MeterType getMeterTypeById(int meterTypeId) {
		final String selectMeterTypeSql = "SELECT * FROM meter_types WHERE meter_type_id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionProvider.takeConnection();

			statement = connection.prepareStatement(selectMeterTypeSql);
			statement.setInt(1, meterTypeId);

			resultSet = statement.executeQuery();
			resultSet.next();

			return extractData(resultSet);
		} catch (SQLException e) {
			System.out.println("Ошибка операции");
			return null;
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement, resultSet);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
	}

	/**
	 * Extracts and creates a MeterType object from the provided ResultSet.
	 *
	 * @param resultSet The ResultSet containing data from a database query.
	 * @return A MeterType object representing the extracted data.
	 * @throws SQLException If a database access error occurs or this method is called on a closed result set.
	 */
	private MeterType extractData(ResultSet resultSet) throws SQLException {
		int meterTypeId = resultSet.getInt("meter_type_id");
		String typeName = resultSet.getString("type_name");
		return new MeterType(meterTypeId, typeName);
	}
}