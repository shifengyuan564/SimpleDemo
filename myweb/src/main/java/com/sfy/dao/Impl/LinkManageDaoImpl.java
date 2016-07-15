package com.sfy.dao.Impl;

import com.sfy.common.Page;
import com.sfy.dao.LinkManageDao;
import com.sfy.dao.common.MySessionDaoSupport;
import com.sfy.domain.Link;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("linkManageDao")
public class LinkManageDaoImpl extends MySessionDaoSupport implements LinkManageDao {

    @Override
    public Link querySingleLink(Link link) {
        return this.getSqlSession().selectOne("com.sfy.dao.LinkManageDao.querySingleLink", link);
    }

    @Override
    public Page<Link> pageQuery(Page<Link> pageBean, Link link) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("links", link);
        pageBean.setParam(param);

        int totalCount = this.getSqlSession().selectOne("com.sfy.dao.LinkManageDao.pageCount",pageBean);
        List<Link> linkList = this.getSqlSession().selectList("com.sfy.dao.LinkManageDao.pageQuery",pageBean);

        pageBean.setTotal(totalCount);
        pageBean.setData(linkList);
        return pageBean;
    }

}
