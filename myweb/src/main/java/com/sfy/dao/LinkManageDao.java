package com.sfy.dao;


import com.sfy.common.Page;
import com.sfy.domain.Link;

import java.util.List;

public interface LinkManageDao {

    /** 查询一条链接信息 **/
    Link querySingleLink(Link link);

    /**
     * 分页查询友情链接信息列表
     * @param pageBean 分页实体对象
     * @param link 友情链接信息Domain
     * @return 友情链接信息列表
     */
    Page<Link> pageQuery(Page<Link> pageBean, Link link);

    boolean addLink(Link link);
}
