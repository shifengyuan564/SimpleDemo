package com.sfy.dao.Impl;

import com.sfy.common.Page ;
import com.sfy.domain.store.SiteBaseInfo;
import com.sfy.domain.store.SiteExtendInfo;
import com.sfy.domain.store.SyncSiteInfoMq;
import com.sfy.domain.store.Store;
import com.sfy.mapper.StoreMapper;
import com.sfy.dao.StoreDao;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository("storeDao")
public class StoreDaoImpl implements StoreDao {

    @javax.annotation.Resource
    private StoreMapper storeMapper;

    @Override
    public void deleteStore(List<Long> ids) {
        storeMapper.delete(ids);
    }

    @Override
    public Page<Store> queryForPageStore(Page<Store> page) {
        page.setTotal(storeMapper.count(page));
        page.setData(storeMapper.queryForPage(page));
        return page;
    }
    
    public List<Store> queryStoreByNo(Store store){
    	return storeMapper.queryStoreByNo(store);
    }

    @Override
    public List<SiteBaseInfo> querySiteBaseInfo(String queryInfo) {
        return storeMapper.querySiteBaseInfo(queryInfo);
    }

    @Override
    public SiteBaseInfo querySiteExtendInfo(SiteBaseInfo siteBaseInfo) {
        SiteBaseInfo baseInfoResult = storeMapper.getOneSiteBaseInfo(siteBaseInfo);
        SiteExtendInfo extendInfoResult = storeMapper.getOneSiteExtendInfo(siteBaseInfo);
        baseInfoResult.setSiteExtendInfo(extendInfoResult);

        return baseInfoResult;
    }
    /**
     * 根据站点名称得到站点基本信息
     * @param siteBaseInfo
     * @return
     */
    public SiteBaseInfo getOneSiteBaseInfo(SiteBaseInfo siteBaseInfo) {
        SiteBaseInfo baseInfoResult = storeMapper.getOneSiteBaseInfo(siteBaseInfo);

        return baseInfoResult;
    }

    @Override
    public Integer getIsDeleteCount(List ids) {
        return storeMapper.getIsDeleteCount(ids);
    }

    @Override
    public Integer updateStoreObject(Store store) {
        return storeMapper.updateStoreObject(store);
    }

    @Override
    public List<SiteBaseInfo> queryStoreByManager(String manager_erp){
        return storeMapper.queryStoreByManager(manager_erp);
    }


    @Override
    public List<SiteBaseInfo> querySiteBaseInfoForPage(SiteBaseInfo siteBaseInfo) {
        return storeMapper.querySiteBaseInfoForPage(siteBaseInfo);
    }

    @Override
    public int querySiteBaseInfoCount(SiteBaseInfo siteBaseInfo) {
        return storeMapper.querySiteBaseInfoCount(siteBaseInfo);
    }

    @Override
    public long insertSiteBaseInfo(SiteBaseInfo siteBaseInfo) {
        return storeMapper.insertSiteBaseInfo(siteBaseInfo);
    }

    @Override
    public boolean insertSiteExtendInfo(SiteExtendInfo siteExtendInfo) {
        return storeMapper.insertSiteExtendInfo(siteExtendInfo);
    }

    @Override
    public boolean deleteSiteBaseInfo(SiteBaseInfo siteBaseInfo) {
        return storeMapper.deleteSiteBaseInfo(siteBaseInfo);
    }

    @Override
    public boolean deleteSiteExtendInfo(SiteExtendInfo siteExtendInfo) {
        return storeMapper.deleteSiteExtendInfo(siteExtendInfo);
    }

    @Override
    public SiteBaseInfo queryOneSiteBaseInfoForPage(SiteBaseInfo siteBaseInfo) {
        return storeMapper.queryOneSiteBaseInfoForPage(siteBaseInfo);
    }

    @Override
    public SiteExtendInfo queryOneSiteExtendInfoForPage(SiteExtendInfo siteExtendInfo) {
        return storeMapper.queryOneSiteExtendInfoForPage(siteExtendInfo);
    }

    @Override
    public boolean updateSiteBaseInfoForPage(SiteBaseInfo siteBaseInfo) {
        return storeMapper.updateSiteBaseInfoForPage(siteBaseInfo);
    }

    @Override
    public boolean updateSiteExtendInfoForPage(SiteExtendInfo siteExtendInfo) {
        return storeMapper.updateSiteExtendInfoForPage(siteExtendInfo);
    }

    @Override
    public boolean updateSiteUploadInfo(SiteExtendInfo siteExtendInfo) {
        return storeMapper.updateSiteUploadInfo(siteExtendInfo);
    }

    @Override
    public String queryMaxSiteNoByOrgNo(SiteBaseInfo siteBaseInfo) {
        return storeMapper.queryMaxSiteNoByOrgNo(siteBaseInfo);
    }
    @Override
    public int updateSiteBaseInfoBatchForManager(List<SiteBaseInfo> list) {
        return storeMapper.updateSiteBaseInfoBatchForManager(list);
    }

    @Override
    public int getSiteCount(SyncSiteInfoMq syncSiteInfoMq){
        return storeMapper.getSiteCount(syncSiteInfoMq);
    }

    @Override
    public int insertSyncSiteInfo(SyncSiteInfoMq syncSiteInfoMq) {
        return storeMapper.insertSyncSiteInfo(syncSiteInfoMq);
    }

    @Override
    public void updateSyncSiteInfo(SyncSiteInfoMq syncSiteInfoMq) {
        storeMapper.updateSyncSiteInfo(syncSiteInfoMq);
    }

    @Override
    public void deleteSyncSiteInfo(SyncSiteInfoMq syncSiteInfoMq) {
        storeMapper.deleteSyncSiteInfo(syncSiteInfoMq);
    }

    @Override
    public List<SyncSiteInfoMq> querySyncSiteInfo(String siteNo) {
        return storeMapper.querySyncSiteInfo(siteNo);
    }

    @Override
    public List<String> querySystemAdmin(String loginUser) {
        return storeMapper.querySystemAdmin(loginUser);
    }

    @Override
    public List<SiteBaseInfo> querySiteBaseInfoForPageByMultiOrg(SiteBaseInfo siteBaseInfo) {
        return storeMapper.querySiteBaseInfoForPageByMultiOrg(siteBaseInfo);
    }

    @Override
    public int querySiteBaseInfoCountByMultiOrg(SiteBaseInfo siteBaseInfo) {
        return storeMapper.querySiteBaseInfoCountByMultiOrg(siteBaseInfo);
    }
}
