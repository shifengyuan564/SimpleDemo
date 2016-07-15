package testSpring.mybatis.Service;

import testSpring.mybatis.domain.OrdersCustom;
import testSpring.mybatis.domain.UserCustom;

import java.util.List;


public interface UserService {

    UserCustom findUserById(int id) throws Exception;

    List<OrdersCustom> selectAllOrdersByUserId(int id);

    int addUser(UserCustom userCustom) throws Exception;
}
