package ru.levchenko.hibernateCRUD.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRolesConverter implements AttributeConverter<UserRolesENUM, String> {

    @Override
    public String convertToDatabaseColumn(UserRolesENUM userRolesENUM) {
        return userRolesENUM.name();
    }

    @Override
    public UserRolesENUM convertToEntityAttribute(String s) {
        return UserRolesENUM.getRole(s);
    }
}
