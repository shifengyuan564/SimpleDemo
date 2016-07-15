package testSpring.mybatis.manager;

import testSpring.mybatis.domain.OrdersCustom;
import testSpring.mybatis.domain.UserCustom;

import java.util.List;

/**
 * Description: 事务控制层 接口
 * Author: shifengyuan@jd.com
 * Date: 2016/1/15
 */
public interface UserManager {

    UserCustom findUserById(int id) throws Exception;

    List<OrdersCustom> selectAllOrdersByUserId(int id);

    int addUserCorrect(UserCustom userCustom) throws Exception;

    int addUserFail(UserCustom userCustom) throws Exception;
}
