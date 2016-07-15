package testSpring.mybatis.manager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import testSpring.mybatis.dao.UserDao;
import testSpring.mybatis.domain.OrdersCustom;
import testSpring.mybatis.domain.UserCustom;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 事务控制层 实现类
 * Author: shifengyuan@jd.com
 * Date: 2016/1/15
 */

@Service("userManager")
public class UserManagerImpl implements UserManager {

    @Resource
    private UserDao userDao;

    @Override
    public UserCustom findUserById(int id) throws Exception {
        return userDao.findUserById(id);
    }

    @Override
    public List<OrdersCustom> selectAllOrdersByUserId(int id) {
        return userDao.selectAllOrdersByUserId(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)  // 单独开一个事务，不受外层service里其它影响
    @Override
    public int addUserCorrect(UserCustom userCustom) {
        return userDao.addUserCorrect(userCustom);
    }

    @Transactional
    @Override
    public int addUserFail(UserCustom userCustom) {
        return userDao.addUserFail(userCustom);
    }
}
