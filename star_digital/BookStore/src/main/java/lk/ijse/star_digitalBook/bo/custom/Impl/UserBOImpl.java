package lk.ijse.star_digitalBook.bo.custom.Impl;

import lk.ijse.star_digitalBook.bo.custom.UserBO;
import lk.ijse.star_digitalBook.dao.DAOFactory;
import lk.ijse.star_digitalBook.dao.custom.OrderItemsDAO;
import lk.ijse.star_digitalBook.dao.custom.UserDAO;
import lk.ijse.star_digitalBook.dto.UserDto;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    UserDAO userDAO  =(UserDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.USER);

    @Override
    public boolean userDetails(UserDto userDto) throws SQLException {
        return userDAO.userDetails(userDto);
    }
}
