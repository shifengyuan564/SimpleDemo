package testSpring.mybatis.dao;

import testSpring.mybatis.domain.OrdersCustom;
import testSpring.mybatis.domain.UserCustom;

import java.util.List;


public interface UserDao {



	//根据id查询用户信息
	UserCustom findUserById(int id) throws Exception;


    // 插入一条数据，测试事务
    int addUserCorrect(UserCustom userCustom);

    int addUserFail(UserCustom userCustom);

    // 获取指定用户的所有订单
    List<OrdersCustom> selectAllOrdersByUserId(int id);
}
