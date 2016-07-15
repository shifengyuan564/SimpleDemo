package testSpring.mybatis.Service;

import org.apache.log4j.LogManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import testSpring.mybatis.dao.UserDao;
import testSpring.mybatis.domain.OrdersCustom;
import testSpring.mybatis.domain.UserCustom;
import testSpring.mybatis.manager.UserManager;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.logging.Logger;

//这里把service层当做manager层了，每个方法内如果含有两个以上的dao操作，就需要加上事务
// Service("userService"); 如果不使用这种形式，就可以用getBean("userServiceImpl");

@Service("userService")
public class UserServiceImpl implements UserService {

    private final static org.apache.log4j.Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Resource
    private UserManager userManager;

    @Override
    public UserCustom findUserById(int id) throws Exception {
        return userManager.findUserById(id);
    }

    @Override
    public List<OrdersCustom> selectAllOrdersByUserId(int id){
        return userManager.selectAllOrdersByUserId(id);
    }

    /**
     * Spring的事务管理默认只对出现运行期异常(java.lang.RuntimeException及其子类)进行回滚。
     * addUser方法中,想让事务回滚aop生效：
     * (1)不使用try/catch;
     * (2)使用try｛｝catch｛｝，事务就不回滚了，如果想让事务回滚必须再往外抛try｛｝catch｛throw Exception｝
     *
     * 默认：rollbackFor = {RuntimeException.class}
     */
    //@Transactional(value = "userTX", rollbackFor = {Exception.class},propagation = Propagation.REQUIRED, readOnly = false, timeout = 10)

    @Transactional(value = "uuu")
    @Override
    public int addUser(UserCustom userCustom) throws Exception {

        int primaryKey = userManager.addUserCorrect(userCustom);
        userManager.addUserFail(userCustom);    // 这句会导致整个事务回滚，上一条也会插入失败

        /*
        try {
            FileInputStream fis = new FileInputStream("G:\\aaa.xls");       // checked exception (FileNotFoundException )默认不受事务管理（可以配置rollbackFor更改）

            // int a = Integer.parseInt("123sss");                             // unchecked exception (runtime exception) 默认受事务管理

        } catch (Exception e){
            throw new Exception("---------出现异常 !---------",e);          // 用try/catch的话，必须throw和throws才能触发事务回滚
        }
        */

        return primaryKey;
    }
}