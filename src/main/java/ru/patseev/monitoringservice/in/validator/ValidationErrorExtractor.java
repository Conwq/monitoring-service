package ru.patseev.monitoringservice.in.validator;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility component for extracting error messages from a BindingResult object.
 */
@Component
public class ValidationErrorExtractor {

	/**
	 * Extracts error messages from a BindingResult object.
	 *
	 * @param bindingResult the BindingResult object containing validation results
	 * @return a set of strings containing error messages
	 */
	public Set<String> getErrorsFromBindingResult(BindingResult bindingResult) {
		return bindingResult
				.getAllErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toSet());
	}
}
