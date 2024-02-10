package ru.patseev.monitoringservice.domain;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * The DataMeter class represents metered data for a specific user on a particular submissionDate.
 * It includes information such as submissionDate, heating data, cold water data, hot water data, and the associated username.
 */
public class DataMeter {

	/**
	 * The unique identifier for the recorded meter data.
	 */
	private Integer meterDataId;

	/**
	 * The submissionDate for which the metered data is recorded.
	 */
	private Timestamp submissionDate;

	/**
	 * The metered value for the specified submissionDate.
	 */
	private Long value;

	/**
	 * The type of the meter associated with the recorded data.
	 */
	private Integer meterTypeId;

	/**
	 * The user ID associated with the recorded meter data.
	 */
	private Integer userId;

	/**
	 * Constructs an empty DataMeter object.
	 */
	public DataMeter() {
	}

	/**
	 * Constructs a DataMeter object with the specified parameters.
	 *
	 * @param meterDataId    The unique identifier for the recorded meter data
	 * @param submissionDate The submissionDate for which the metered data is recorded
	 * @param value          The metered value for the specified submissionDate
	 * @param meterTypeId    The type of the meter associated with the recorded data
	 * @param userId         The user ID associated with the recorded meter data
	 */
	public DataMeter(Integer meterDataId, Timestamp submissionDate, Long value, Integer meterTypeId, Integer userId) {
		this.meterDataId = meterDataId;
		this.submissionDate = submissionDate;
		this.value = value;
		this.meterTypeId = meterTypeId;
		this.userId = userId;
	}

	/**
	 * Retrieves the unique identifier for the recorded meter data.
	 *
	 * @return The meterDataId
	 */
	public Integer getMeterDataId() {
		return meterDataId;
	}

	/**
	 * Sets the unique identifier for the recorded meter data.
	 *
	 * @param meterDataId The meterDataId to set
	 */
	public void setMeterDataId(Integer meterDataId) {
		this.meterDataId = meterDataId;
	}

	/**
	 * Retrieves the submissionDate for which the metered data is recorded.
	 *
	 * @return The submissionDate
	 */
	public Timestamp getSubmissionDate() {
		return submissionDate;
	}

	/**
	 * Sets the submissionDate for which the metered data is recorded.
	 *
	 * @param submissionDate The submissionDate to set
	 */
	public void setSubmissionDate(Timestamp submissionDate) {
		this.submissionDate = submissionDate;
	}

	/**
	 * Retrieves the metered value for the specified submissionDate.
	 *
	 * @return The value
	 */
	public Long getValue() {
		return value;
	}

	/**
	 * Sets the metered value for the specified submissionDate.
	 *
	 * @param value The value to set
	 */
	public void setValue(Long value) {
		this.value = value;
	}

	/**
	 * Retrieves the type of the meter associated with the recorded data.
	 *
	 * @return The meterTypeId
	 */
	public Integer getMeterTypeId() {
		return meterTypeId;
	}

	/**
	 * Sets the type of the meter associated with the recorded data.
	 *
	 * @param meterTypeId The meterTypeId to set
	 */
	public void setMeterTypeId(Integer meterTypeId) {
		this.meterTypeId = meterTypeId;
	}

	/**
	 * Retrieves the user ID associated with the recorded meter data.
	 *
	 * @return The userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Sets the user ID associated with the recorded meter data.
	 *
	 * @param userId The userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 *
	 * @param o The reference object with which to compare.
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DataMeter dataMeter = (DataMeter) o;
		return Objects.equals(meterDataId, dataMeter.meterDataId) && Objects.equals(submissionDate, dataMeter.submissionDate) && Objects.equals(value, dataMeter.value) && Objects.equals(meterTypeId, dataMeter.meterTypeId) && Objects.equals(userId, dataMeter.userId);
	}

	/**
	 * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
	 *
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(meterDataId, submissionDate, value, meterTypeId, userId);
	}
}
