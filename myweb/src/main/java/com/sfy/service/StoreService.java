package com.sfy.service;

import com.sfy.common.Page ;
import com.sfy.common.Result;
import com.sfy.domain.store.SiteBaseInfo;
import com.sfy.domain.store.SiteExtendInfo;
import com.sfy.domain.store.SyncSiteInfoMq;
import com.sfy.domain.store.Store;

import java.util.List;
import java.util.Map;

/**
 * 门店相关服务接口
 */
public interface StoreService {

    void deleteStore(List<Long> ids);

    Page<Store> queryForPageStore(Page<Store> page);

    public Integer getIsDeleteCount(List ids);

    public Integer updateStoreObject(Store store);

    public List<Store> queryStoreByNo(Store store);

    public List<SiteBaseInfo> querySiteBaseInfo(String queryInfo);

    public SiteBaseInfo querySiteExtendInfo(SiteBaseInfo siteBaseInfo);

    public SiteBaseInfo getOneSiteBaseInfo(SiteBaseInfo siteBaseInfo);

    Result<List<Object>> queryWebsiteInfo(Store store);

    public List<SiteBaseInfo> queryStoreByManager(String manager_erp);
    Result<List<SiteBaseInfo>> querySiteBaseInfoForPage(SiteBaseInfo siteBaseInfo);

    int querySiteBaseInfoCount(SiteBaseInfo siteBaseInfo);

    boolean insertSiteInfo(SiteBaseInfo siteBaseInfo, SiteExtendInfo siteExtendInfo);

    boolean insertSiteExtendInfo(SiteExtendInfo siteExtendInfo);

    boolean deleteSiteInfo(SiteBaseInfo siteBaseInfo);
    int updateSiteBaseInfoBatchForManager(List<SiteBaseInfo> list);

    SiteBaseInfo queryOneSiteBaseInfoForPage(SiteBaseInfo siteBaseInfo);

    SiteExtendInfo queryOneSiteExtendInfoForPage(SiteExtendInfo siteExtendInfo);

    boolean updateSiteInfoForPage(SiteBaseInfo siteBaseInfo, SiteExtendInfo siteExtendInfo);

    boolean updateSiteUploadInfo(SiteExtendInfo siteExtendInfo);

    String queryMaxSiteNoByOrgNo(SiteBaseInfo siteBaseInfo);

    public boolean updateExtendSiteInfoForPage(SiteExtendInfo siteExtendInfo);

    List<SyncSiteInfoMq> querySyncSiteInfo(String siteNo);

    List<String> querySystemAdmin(String loginUser);

    Map<String, List<SyncSiteInfoMq>> queryBunchSyncSiteInfo(List<String> bunchInfoList);

    Result<List<SiteBaseInfo>> querySiteBaseInfoForPageByMultiOrg(SiteBaseInfo siteBaseInfo);

    int querySiteBaseInfoCountByMultiOrg(SiteBaseInfo siteBaseInfo);
}
