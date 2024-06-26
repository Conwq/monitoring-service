package ru.patseev.monitoringservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;

/**
 * Mapper interface for mapping between User and UserDto objects.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

	/**
	 * Converts a UserDto object to a User entity.
	 *
	 * @param userDto The UserDto object to be converted.
	 * @return A User entity.
	 */
	@Mapping(target = "roleId", expression = "java(RoleEnum.USER.getRoleId())")
	User toEntity(UserDto userDto);

	/**
	 * Converts a User entity to a UserDto object.
	 *
	 * @param user     The User entity to be converted.
	 * @param roleEnum The RoleEnum specifying the role of the user.
	 * @return A UserDto object.
	 */
	@Mappings({
			@Mapping(target = "password", ignore = true),
			@Mapping(target = "role", source = "roleEnum")
	})
	UserDto toDto(User user, RoleEnum roleEnum);
}
