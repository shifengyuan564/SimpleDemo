/*

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.util.StringUtils;
import testSpring.mybatis.Service.UserService;
import testSpring.mybatis.dao.common.JsonUtil;
import testSpring.mybatis.domain.OrdersCustom;
import testSpring.mybatis.domain.User;
import testSpring.mybatis.domain.UserCustom;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:xxx.xml"})
public class UserTest {

    private ApplicationContext applicationContext;
    private static final Log logger = LogFactory.getLog(UserTest.class);

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:testSpring/mybatis/Resources/spring/applicationContext.xml");    //得到容器
        userService = (UserService) applicationContext.getBean("userService");
    }

    */
/**
     * 获取配置文件中注册的Bean
     *//*

    @Test
    public void testUserfindUserById() throws Exception {

        System.out.println("=================== 1. 测试getBean (注意在控制台的位置)=====================");

        UserCustom userCustom = null;
        String strJson = "";
        User newUser = null;

        try {
            userCustom = userService.findUserById(1);
            strJson = JsonUtil.toJson(userCustom);   // bean转成json
            newUser = JsonUtil.fromJson("{\"id\":1,\"username\":\"王五\",\"sex\":\"2\",\"company\":\"京东\"}", User.class);
        } catch (Exception e) {
            logger.error("查询User出问题", e);
        }

        System.out.println(userCustom);
        System.out.println(strJson);
        System.out.println(newUser);
    }


    */
/**
     * 获取一组数据
     *//*

    @Test
    public void testSelectList() {

        System.out.println("===================  获取用户 id=1 的所有订单 =====================");
        List<OrdersCustom> ordersCustoms = userService.selectAllOrdersByUserId(1);
        for (OrdersCustom oc : ordersCustoms) {
            System.out.println(oc.toString());
        }
    }

    */
/**
     * 测试事务
     *//*

    @Test
    public void testTransaction() throws Exception {

        UserCustom uc = new UserCustom();
        uc.setUsername("测试事务");
        //uc.setId(12);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = "2015-12-07";
        Date date = sdf.parse(strDate);
        uc.setBirthday(date);

        System.out.println("插入数据的主键值是：" + userService.addUser(uc));
    }


    @Test
    public void testSpringBasis() {

        */
/**
         * 测试cleanPath
         *  主要是将传入的路径规范化，比如将windows的路径分隔符“\\”替换为标准的“/“，
         *  如果路径中含有.(当前文件夹)，或者..(上层文件夹)，则计算出其真实路径。
         *//*

        System.out.println("=================== 1. 测试cleanPath=====================");
        String path1 = "D:\\workspace-home\\spring-custom\\.\\src\\main\\resources\\spring\\..\\spring\\app-context.xml";
        String path2 = "file:core/../core/io/Resource.class";
        String path3 = "classpath:testSpring/mybatis/Resources/spring/applicationContext.xml";

        System.out.println(StringUtils.cleanPath(path3));

        */
/**
         * 测试classloader
         *//*

        System.out.println("=================== 2. 测试classLoader=====================");

        // 此时三个ClassLoader是同一个对象
        System.out.println(Thread.currentThread().getContextClassLoader()); // 当前线程的类加载器
        System.out.println(UserTest.class.getClassLoader()); // 当前类的类加载器
        System.out.println(ClassLoader.getSystemClassLoader()); // 系统初始的类加载器

    }

    @Test
    public void testBoolean(){
        boolean a = false;
        boolean b = true;

        System.out.println(b = b && a);
    }

    @Test
    public void testDate(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        System.out.println(time);
    }
}
*/
