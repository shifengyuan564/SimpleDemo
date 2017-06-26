package com.sfy.service.Impl;

import com.google.gson.Gson;
import com.sfy.common.JsonUtil;
import com.sfy.common.Result;
import com.sfy.common.Page;
import com.sfy.dao.LinkManageDao;
import com.sfy.domain.Link;
import com.sfy.service.LinkManageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LinkManageServiceImpl implements LinkManageService {

    private Logger logger = Logger.getLogger(LinkManageServiceImpl.class);

    //@Resource(name = "lasCache")
    //private LasCache lasCache;

    @Autowired
    LinkManageDao linkManageDao;

    @Override
    public Link querySingleLink(Link link){
/*
        logger.info("LinkManageServiceImpl querySingleLink link =" + new Gson().toJson(link));
        List<Link> links = new ArrayList<>();
        Result<List<Link>> result = new Result<>(false);

        if (null == link) {
            logger.info("LinkManageServiceImpl querySingleLink  参数link为空");
            result.setSuccess(false);
            result.setLocalizedMessage("LinkManageServiceImpl querySingleLink  参数link为空");
            return null; // return result;
        }

        //查询缓存
        String key = "QUERY_LINK_" + link.getId() + "_" + link.getTitle() + "_" + link.getAddress();
        logger.info("LinkManageServiceImpl querySingleLink 缓存key=" + key);
        links = (List<Link>) lasCache.get(key);
        //如果缓存有直接返回
        if (null != links && links.size() > 0) {
            result.setSuccess(true);
            result.setResult(links);
            logger.info("LinkManageServiceImpl querySingleLink 通过缓存查询返回值list个数=" + links.size());
            return links.get(0); //return result;
        }*/

        //缓存查不到，查询数据库
        return linkManageDao.querySingleLink(link);
    }

    @Override
    public Page<Link> pageQuery(Page<Link> pageBean, Link link) {
        return linkManageDao.pageQuery(pageBean,link);
    }

    @Override
    public boolean addLink(Link link) {
        return linkManageDao.addLink(link);
    }
}
