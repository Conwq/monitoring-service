package ru.patseev.monitoringservice.domain;

import java.util.Objects;

/**
 * Represents a type of meter with a unique identifier and a name.
 */
public class MeterType {

	/**
	 * The unique identifier for the meter type.
	 */
	private Integer meterTypeId;

	/**
	 * The name of the meter type.
	 */
	private String typeName;

	/**
	 * Constructs an empty MeterType object.
	 */
	public MeterType() {}

	/**
	 * Constructs a MeterType object with the specified parameters.
	 *
	 * @param meterTypeId The unique identifier for the meter type
	 * @param typeName    The name of the meter type
	 */
	public MeterType(Integer meterTypeId, String typeName) {
		this.meterTypeId = meterTypeId;
		this.typeName = typeName;
	}

	/**
	 * Retrieves the unique identifier for the meter type.
	 *
	 * @return The meterTypeId
	 */
	public Integer getMeterTypeId() {
		return meterTypeId;
	}

	/**
	 * Sets the unique identifier for the meter type.
	 *
	 * @param meterTypeId The meterTypeId to set
	 */
	public void setMeterTypeId(Integer meterTypeId) {
		this.meterTypeId = meterTypeId;
	}

	/**
	 * Retrieves the name of the meter type.
	 *
	 * @return The typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Sets the name of the meter type.
	 *
	 * @param typeName The typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
		MeterType meterType = (MeterType) o;
		return Objects.equals(meterTypeId, meterType.meterTypeId) &&
				Objects.equals(typeName, meterType.typeName);
	}

	/**
	 * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
	 *
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(meterTypeId, typeName);
	}
}