package com.sfy.mapper;

import com.sfy.domain.store.SiteBaseInfo;
import com.sfy.domain.store.SiteExtendInfo;
import com.sfy.domain.store.SyncSiteInfoMq;
import com.sfy.domain.store.Store;
import com.sfy.common.DataAccessMapper;

import java.util.List;


public interface StoreMapper extends DataAccessMapper<Store> {
	
	public List<Store> queryStoreByNo(Store store);

    public List<SiteBaseInfo> querySiteBaseInfo(String queryInfo);
    public SiteBaseInfo getOneSiteBaseInfo(SiteBaseInfo siteBaseInfo);
    public SiteExtendInfo getOneSiteExtendInfo(SiteBaseInfo siteBaseInfo);

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

}
