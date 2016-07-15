package com.sfy.dao;

import com.sfy.common.Page ;
import com.sfy.domain.store.SiteBaseInfo;
import com.sfy.domain.store.SiteExtendInfo;
import com.sfy.domain.store.SyncSiteInfoMq;
import com.sfy.domain.store.Store;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yanbingxin
 * Date: 15-9-6
 * Time: 下午7:21
 * To change this template use File | Settings | File Templates.
 */
public interface StoreDao {
    /**
     * 删除门店
     *
     * @param ids
     */
    void deleteStore(List<Long> ids);

    Page<Store> queryForPageStore(Page<Store> page);
    
    List<Store> queryStoreByNo(Store store);

    List<SiteBaseInfo> querySiteBaseInfo(String queryInfo);

    SiteBaseInfo querySiteExtendInfo(SiteBaseInfo siteBaseInfo);

    public Integer getIsDeleteCount(List ids);

    public Integer updateStoreObject(Store store);

    public List<SiteBaseInfo> queryStoreByManager(String manager_erp);

    List<SiteBaseInfo> querySiteBaseInfoForPage(SiteBaseInfo siteBaseInfo);

    int querySiteBaseInfoCount(SiteBaseInfo siteBaseInfo);

    int updateSiteBaseInfoBatchForManager(List<SiteBaseInfo> list);

    long insertSiteBaseInfo(SiteBaseInfo siteBaseInfo);

    boolean insertSiteExtendInfo(SiteExtendInfo siteExtendInfo);

    boolean deleteSiteBaseInfo(SiteBaseInfo siteBaseInfo);

    boolean deleteSiteExtendInfo(SiteExtendInfo siteExtendInfo);

    SiteBaseInfo queryOneSiteBaseInfoForPage(SiteBaseInfo siteBaseInfo);

    SiteExtendInfo queryOneSiteExtendInfoForPage(SiteExtendInfo siteExtendInfo);

    boolean updateSiteBaseInfoForPage(SiteBaseInfo siteBaseInfo);

    boolean updateSiteExtendInfoForPage(SiteExtendInfo siteExtendInfo);

    boolean updateSiteUploadInfo(SiteExtendInfo siteExtendInfo);

    String queryMaxSiteNoByOrgNo(SiteBaseInfo siteBaseInfo);
    int getSiteCount(SyncSiteInfoMq syncSiteInfoMq);

    int insertSyncSiteInfo(SyncSiteInfoMq syncSiteInfoMq);

    void updateSyncSiteInfo(SyncSiteInfoMq syncSiteInfoMq);

    void deleteSyncSiteInfo(SyncSiteInfoMq syncSiteInfoMq);

    List<SyncSiteInfoMq> querySyncSiteInfo(String siteNo);

    List<String> querySystemAdmin(String loginUser);

    List<SiteBaseInfo> querySiteBaseInfoForPageByMultiOrg(SiteBaseInfo siteBaseInfo);

    int querySiteBaseInfoCountByMultiOrg(SiteBaseInfo siteBaseInfo);
    public SiteBaseInfo getOneSiteBaseInfo(SiteBaseInfo siteBaseInfo);
}
