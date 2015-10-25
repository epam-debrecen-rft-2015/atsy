package com.epam.rft.atsy.service.mapping;


import com.epam.rft.atsy.persistence.entities.UserEntity;
import com.epam.rft.atsy.service.domain.UserDTO;
import org.modelmapper.ModelMapper;
/**
 * Created by mates on 10/25/2015.
 */
public class UserMapper {

    static UserEntity mapAutomatically(UserDTO uDTO) {
        UserDTO userDTO = uDTO;
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        //assertOrdersEqual(uDTO, userEntity);
        return userEntity;
    }

    static UserDTO mapAutomatically(UserEntity uEntity) {
        UserEntity userEntity = uEntity;
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        return userDTO;
        //assertOrdersEqual(uDTO, userEntity);
    }
}
