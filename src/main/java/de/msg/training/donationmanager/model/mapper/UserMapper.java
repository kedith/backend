package de.msg.training.donationmanager.model.mapper;

import de.msg.training.donationmanager.model.User;
import de.msg.training.donationmanager.model.dtos.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserDto userDtoFromUser(User user);

    public abstract User userFromUserDto(UserDto userDto);

    public abstract List<UserDto> dtosFromUsers(List<User> users);
}
