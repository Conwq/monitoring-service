package ru.patseev.monitoringservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.patseev.monitoringservice.domain.UserAction;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.enums.ActionEnum;

/**
 * Mapper interface for converting between UserActionDto and UserAction entities.
 */
@Mapper(componentModel = "spring")
public interface AuditMapper {

	/**
	 * Converts ActionEnum and userId to a UserAction entity.
	 *
	 * @param action The action performed.
	 * @param userId The ID of the user performing the action.
	 * @return The UserAction entity.
	 */
	@Mappings({
			@Mapping(target = "userId", source = "userId"),
			@Mapping(target = "action", source = "action"),
			@Mapping(target = "actionAt", expression = "java(java.sql.Timestamp.from(java.time.Instant.now()))")
	})
	UserAction toEntity(ActionEnum action, int userId);

	/**
	 * Converts a UserAction entity to a UserActionDto.
	 *
	 * @param userAction The UserAction entity.
	 * @return The corresponding UserActionDto.
	 */
	UserActionDto toDto(UserAction userAction);
}
