package testSpring.mybatis.dao.common;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;

import static org.springframework.util.Assert.notNull;

/**
 * Description:
 * <p/>
 * Author: shifengyuan@jd.com
 * Date: 2015/12/4
 */

public class MySessionDaoSupport extends DaoSupport{

    private SqlSession sqlSession;

    private boolean externalSqlSession;

    /**
     *  方法一（显示）:    @Qualifier("receiptSqlSessionFactory") SqlSessionFactory sqlSessionFactory
     *  方法二:            把参数名改成和.xml中配置的bean id一样 （这里使用的是方法二）
     *  mybatis-spring 1.2版本后需要在重写的自定义XXXSqlSessionDaoSupport中为setSqlSessionFactory添加@Autowired注解，否则报错
     */
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory myCustomFactory) {
        if (!this.externalSqlSession) {
            this.sqlSession = new SqlSessionTemplate(myCustomFactory);
        }
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSession = sqlSessionTemplate;
        this.externalSqlSession = true;
    }

    /**
     * Users should use this method to get a SqlSession to call its statement methods
     * This is SqlSession is managed by spring. Users should not commit/rollback/close it
     * because it will be automatically done.
     *
     * @return Spring managed thread safe SqlSession
     */
    public SqlSession getSqlSession() {
        return this.sqlSession;
    }

    /**
     * {@inheritDoc}
     */
    protected void checkDaoConfig() {
        notNull(this.sqlSession, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
    }
}
