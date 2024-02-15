package ru.patseev.monitoringservice.in.validator;

/**
 * The Validator interface defines a contract for validating objects of type T.
 *
 * @param <T> The type of object to validate.
 */
public interface Validator<T> {

	/**
	 * Validates the given object.
	 *
	 * @param t The object to validate.
	 * @return true if the object is valid, false otherwise.
	 */
	boolean validate(T t);
}
