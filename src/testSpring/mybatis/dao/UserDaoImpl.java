package testSpring.mybatis.dao;

import org.apache.ibatis.session.SqlSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import testSpring.mybatis.dao.common.MySessionDaoSupport;
import testSpring.mybatis.domain.OrdersCustom;
import testSpring.mybatis.domain.UserCustom;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends MySessionDaoSupport implements UserDao {


    @Override
	public UserCustom findUserById(int id) throws Exception {

		SqlSession sqlSession = this.getSqlSession();       // sqlSession继承自SqlSessionDaoSupport

        UserCustom userCustom = sqlSession.selectOne("testSpring.mybatis.dao.UserDao.findUserById", id);
		return userCustom;
	}

    @Override
    public int addUserCorrect(UserCustom userCustom) {

        SqlSession sqlSession = this.getSqlSession();
        sqlSession.insert("testSpring.mybatis.dao.UserDao.addUserCorrect",userCustom);
        return userCustom.getId();  // 获取通过Mybatis selectKey生成的主键ID
    }

    @Override
    public int addUserFail(UserCustom userCustom) {

        SqlSession sqlSession = this.getSqlSession();
        sqlSession.insert("testSpring.mybatis.dao.UserDao.addUserFail",userCustom);
        return userCustom.getId();  // 获取通过Mybatis selectKey生成的主键ID
    }

    @Override
    public List<OrdersCustom> selectAllOrdersByUserId(int id) {
        SqlSession sqlSession = this.getSqlSession();

        return sqlSession.selectList("testSpring.mybatis.dao.UserDao.selectAllOrdersByUserId", id);
    }

}
