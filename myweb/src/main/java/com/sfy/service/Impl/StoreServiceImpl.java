package com.sfy.service.Impl;

import com.sfy.dao.StoreDao;
import com.sfy.common.Page ;
import com.sfy.common.Result;
import com.sfy.domain.store.SiteBaseInfo;
import com.sfy.domain.store.SiteExtendInfo;
import com.sfy.domain.store.SyncSiteInfoMq;
import com.sfy.domain.store.Store;

import com.sfy.service.StoreService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yanbingxin
 * Date: 15-9-6
 * Time: 下午7:27
 * To change this template use File | Settings | File Templates.
 */
@Service("storeService")
public class StoreServiceImpl implements StoreService {

    private static final org.apache.commons.logging.Log logger = LogFactory.getLog(StoreServiceImpl.class);

    @Autowired
    private StoreDao storeDao;

    @Override
    public void deleteStore(List<Long> ids) {
        storeDao.deleteStore(ids);
    }

    @Override
    public Page<Store> queryForPageStore(Page<Store> page) {
        return storeDao.queryForPageStore(page);
    }

    @Override
    public Integer getIsDeleteCount(List ids) {
        return storeDao.getIsDeleteCount(ids);
    }

    @Override
    public Integer updateStoreObject(Store store) {
        return storeDao.updateStoreObject(store);
    }

    @Override
    public List<Store> queryStoreByNo(Store store){
       return storeDao.queryStoreByNo(store);
    }

    @Override
    public List<SiteBaseInfo> querySiteBaseInfo(String queryInfo){
        return storeDao.querySiteBaseInfo(queryInfo);
    }

    @Override
    public SiteBaseInfo querySiteExtendInfo(SiteBaseInfo siteBaseInfo) {
        return storeDao.querySiteExtendInfo(siteBaseInfo);
    }

    /**
     * 根据站点名称得到站点基本信息
     * @param siteBaseInfo
     * @return
     */
    public SiteBaseInfo getOneSiteBaseInfo(SiteBaseInfo siteBaseInfo){
        return storeDao.getOneSiteBaseInfo(siteBaseInfo);
    }
    @Override
    public Result<List<Object>> queryWebsiteInfo(Store store) {
        return null;
    }

    @Override
    public List<SiteBaseInfo> queryStoreByManager(String manager_erp) {
        return storeDao.queryStoreByManager(manager_erp);
    }

    @Override
    public Result<List<SiteBaseInfo>> querySiteBaseInfoForPage(SiteBaseInfo siteBaseInfo) {
        Result<List<SiteBaseInfo>> queryResult = new Result<List<SiteBaseInfo>>(false);
        try {

            List<SiteBaseInfo> siteBaseInfoList = storeDao.querySiteBaseInfoForPage(siteBaseInfo);
            queryResult.setSuccess(true);
            queryResult.setResult(siteBaseInfoList);
        } catch(Exception e) {
            queryResult.setErrorCode("500");
            queryResult.setLocalizedMessage(e.getLocalizedMessage());
            queryResult.setErrorStack(e.toString());
        }
        return queryResult;
    }

    @Override
    public int querySiteBaseInfoCount(SiteBaseInfo siteBaseInfo) {
        return storeDao.querySiteBaseInfoCount(siteBaseInfo);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertSiteInfo(SiteBaseInfo siteBaseInfo, SiteExtendInfo siteExtendInfo) {
        boolean isSuccess = false;
        long result = storeDao.insertSiteBaseInfo(siteBaseInfo);
        if(result > 0) {
            isSuccess = true;
            siteExtendInfo.setSiteNo(siteBaseInfo.getSiteNo());
            isSuccess = isSuccess && storeDao.insertSiteExtendInfo(siteExtendInfo);
        }
        return isSuccess;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertSiteExtendInfo(SiteExtendInfo siteExtendInfo) {
        return storeDao.insertSiteExtendInfo(siteExtendInfo);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean deleteSiteInfo(SiteBaseInfo siteBaseInfo) {
        Long siteNo = siteBaseInfo.getSiteNo();
        SiteExtendInfo siteExtendInfo = new SiteExtendInfo();
        siteExtendInfo.setSiteNo(siteNo);
        boolean isSuccess = storeDao.deleteSiteBaseInfo(siteBaseInfo);
        isSuccess = isSuccess && storeDao.deleteSiteExtendInfo(siteExtendInfo);
        return isSuccess;
    }

    public int updateSiteBaseInfoBatchForManager(List<SiteBaseInfo> list) {
        return storeDao.updateSiteBaseInfoBatchForManager(list);
    }

    @Override
    public SiteBaseInfo queryOneSiteBaseInfoForPage(SiteBaseInfo siteBaseInfo) {
        return storeDao.queryOneSiteBaseInfoForPage(siteBaseInfo);
    }

    @Override
    public SiteExtendInfo queryOneSiteExtendInfoForPage(SiteExtendInfo siteExtendInfo) {
        return storeDao.queryOneSiteExtendInfoForPage(siteExtendInfo);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean updateSiteInfoForPage(SiteBaseInfo siteBaseInfo, SiteExtendInfo siteExtendInfo) {
        if(siteBaseInfo == null || siteExtendInfo == null) {
            return false;
        }
        boolean isSuccess = storeDao.updateSiteBaseInfoForPage(siteBaseInfo);
        SiteExtendInfo siteExtendInfoByQuery = queryOneSiteExtendInfoForPage(siteExtendInfo);
        if(siteExtendInfoByQuery != null && siteExtendInfoByQuery.getSiteNo() > 0) {
            // update
            isSuccess = isSuccess && storeDao.updateSiteExtendInfoForPage(siteExtendInfo);
        } else {
            // insert
            isSuccess = isSuccess && storeDao.insertSiteExtendInfo(siteExtendInfo);
        }
        return isSuccess;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean updateSiteUploadInfo(SiteExtendInfo siteExtendInfo) {
        if(siteExtendInfo == null) {
            return false;
        }
        return storeDao.updateSiteUploadInfo(siteExtendInfo);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean updateExtendSiteInfoForPage(SiteExtendInfo siteExtendInfo) {
        if(siteExtendInfo == null) {
            return false;
        }
        return storeDao.updateSiteExtendInfoForPage(siteExtendInfo);
    }

    @Override
    public String queryMaxSiteNoByOrgNo(SiteBaseInfo siteBaseInfo) {
        return storeDao.queryMaxSiteNoByOrgNo(siteBaseInfo);
    }

    @Override
    public List<SyncSiteInfoMq> querySyncSiteInfo(String siteNo) {
        logger.info("----------------StoreServiceImpl------------querySyncSiteInfo 查询同步信息，siteNo="+siteNo);
        return storeDao.querySyncSiteInfo(siteNo);
    }

    @Override
    public List<String> querySystemAdmin(String loginUser) {
        return storeDao.querySystemAdmin(loginUser);
    }

    @Override
    public Map<String, List<SyncSiteInfoMq>> queryBunchSyncSiteInfo(List<String> bunchInfoList) {
        if(bunchInfoList == null || bunchInfoList.isEmpty()){
            return null;
        }

        Map<String, List<SyncSiteInfoMq>> siteMap = new HashMap<String, List<SyncSiteInfoMq>>();

        for(String bi : bunchInfoList){
            siteMap.put(bi, storeDao.querySyncSiteInfo(bi));
        }

        return siteMap;
    }

    /**
     * 多区域支持
     * @param siteBaseInfo
     * @return
     */
    @Override
    public Result<List<SiteBaseInfo>> querySiteBaseInfoForPageByMultiOrg(SiteBaseInfo siteBaseInfo) {
        Result<List<SiteBaseInfo>> queryResult = new Result<List<SiteBaseInfo>>(false);
        try {
            List<SiteBaseInfo> siteBaseInfoList = storeDao.querySiteBaseInfoForPageByMultiOrg(siteBaseInfo);
            queryResult.setSuccess(true);
            queryResult.setResult(siteBaseInfoList);
        } catch(Exception e) {
            queryResult.setErrorCode("500");
            queryResult.setLocalizedMessage(e.getLocalizedMessage());
            queryResult.setErrorStack(e.toString());
        }
        return queryResult;
    }

    /**
     * 多区域支持
     * @param siteBaseInfo
     * @return
     */
    @Override
    public int querySiteBaseInfoCountByMultiOrg(SiteBaseInfo siteBaseInfo) {
        return storeDao.querySiteBaseInfoCountByMultiOrg(siteBaseInfo);
    }


}
