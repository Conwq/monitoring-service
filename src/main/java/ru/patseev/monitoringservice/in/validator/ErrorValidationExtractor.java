package ru.patseev.monitoringservice.in.validator;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ErrorValidationExtractor {

	public Set<String> getErrorsFromBindingResult(BindingResult bindingResult) {
		return bindingResult
				.getAllErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toSet());
	}
}
