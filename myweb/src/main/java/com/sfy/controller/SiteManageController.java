package com.sfy.controller;

import com.sfy.service.AreaCascadeService ;
import com.sfy.service.StoreService;
import com.sfy.common.Result;
import com.sfy.domain.store.SiteBaseInfo;
import com.sfy.domain.store.SiteExtendInfo;
import com.sfy.domain.store.SyncSiteInfoMq;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = {"/site"})
public class SiteManageController {

    private static final Logger logger = Logger.getLogger(SiteManageController.class);

    @Autowired
    private StoreService storeService;

//    @Autowired
//    private JSSService jss;

    @Autowired
    private AreaCascadeService areaCascadeService;

    @RequestMapping(value = "/judgeSystemAdmin")
    public boolean judgeSystemAdmin(HttpServletRequest request) {
        return false;
    }

    @RequestMapping(value = "/uploadForPage")
    @ResponseBody
    public Result<Map<String, String>> uploadForPage(MultipartHttpServletRequest request) {
        Result<Map<String, String>> resultMap = new Result<Map<String, String>>(false);
        resultMap.setResult(new HashMap<String, String>());
        Iterator<String> iterator = request.getFileNames();
        Iterator<String> iteratorOperator = request.getFileNames();
        MultipartFile multipartFile;
        Map<String, String> duplicateName = new HashMap<String, String>();
        List<String> fileTypeAllow = new ArrayList<String>();
        fileTypeAllow.add("gif");
        fileTypeAllow.add("jpg");
        fileTypeAllow.add("jpeg");
        fileTypeAllow.add("png");
        fileTypeAllow.add("doc");
        fileTypeAllow.add("xsl");
        fileTypeAllow.add("ppt");
        fileTypeAllow.add("rar");
        fileTypeAllow.add("zip");
        fileTypeAllow.add("pdf");
        boolean isDuplicate = false;
        try {
            while (iterator.hasNext()) {
                multipartFile = request.getFile(iterator.next());
                String fileName = multipartFile.getOriginalFilename();
                long fileSize = multipartFile.getSize();
                String[] arr = fileName.split("\\.");
                int tmp = arr.length;
                String fileType = arr[tmp - 1];
                if (fileSize > 5000000 || !fileTypeAllow.contains(fileType)) {
                    resultMap.setSuccess(false);
                    resultMap.setLocalizedMessage("上传文件不符合规格，请重新上传");
                    resultMap.setErrorStack("上传文件不符合规格，请重新上传");
                    resultMap.setErrorCode("501");
                    return resultMap;
                }
                if (resultMap.getResult().containsKey(fileName)) {
                    isDuplicate = true;
                    duplicateName.put(fileName, "");
                }
            }
            while (!isDuplicate && iteratorOperator.hasNext()) {
                multipartFile = request.getFile(iteratorOperator.next());
                String fileName = multipartFile.getOriginalFilename();
                String contentType = multipartFile.getContentType();
                String uuid = UUID.randomUUID().toString() + "_" + fileName;
//                String url = jss.upload(uuid, contentType, multipartFile.getBytes());
//                resultMap.getResult().put(fileName, url);
            }
        } catch (Exception e) {
            resultMap.setErrorCode("500");
            resultMap.setErrorStack(e.toString());
            resultMap.setLocalizedMessage(e.getLocalizedMessage());
            return resultMap;
        }
        if (duplicateName.size() > 0) {
            resultMap.setSuccess(false);
            resultMap.setResult(duplicateName);
        } else {
            resultMap.setSuccess(true);
        }
        return resultMap;
    }

    @RequestMapping(value = "/deleteUploadedForPage")
    @ResponseBody
    public Result<Boolean> deleteUploadedForPage(HttpServletRequest request) {
        String uploadedList = request.getParameter("uploadedList");
        Result<Boolean> result = new Result<Boolean>(true);
        if (StringUtils.isNotBlank(uploadedList)) {
            String[] splits = uploadedList.split(",");
            for (String splitStr : splits) {
                String deleteName = splitStr.substring(splitStr.lastIndexOf("/") + 1, splitStr.length());
                //jss.delete(deleteName);
            }
        }
        return result;
    }

    @RequestMapping(value = "/insertSiteBaseInfoForPage")
    @ResponseBody
    public Result<Boolean> insertSiteBaseInfoForPage(HttpServletRequest request) {
        SiteBaseInfo siteBaseInfo;
        SiteExtendInfo siteExtendInfo;
        Result<Boolean> resultList;
        try {
            siteBaseInfo = assumeInsertSiteBaseInfoEntity(request, false);
            siteExtendInfo = assumeInsertSiteExtendInfoEntity(request);
            boolean isSuccess = storeService.insertSiteInfo(siteBaseInfo, siteExtendInfo);
            resultList = new Result<Boolean>(true);
            if (isSuccess) {
                resultList.setResult(isSuccess);
            } else {
                resultList.setResult(false);
                resultList.setErrorCode("501");
                resultList.setLocalizedMessage("增加门店记录失败，非代码错误");
                resultList.setErrorStack("增加门店记录失败，非代码错误");
            }
        } catch (Exception e) {
            logger.error("添加门店失败，失败原因："+e.getMessage());
            resultList = new Result<Boolean>(false);
            resultList.setErrorCode("500");
            resultList.setErrorStack(e.toString());
            resultList.setLocalizedMessage(e.getLocalizedMessage());
        }
        return resultList;
    }

    @RequestMapping(value = "/deleteSiteInfoForPage")
    @ResponseBody
    public Result<Boolean> deleteSiteInfoForPage(HttpServletRequest request) {
        String siteNo = request.getParameter("siteNo");
        Result<Boolean> result = new Result<Boolean>(false);
        if (StringUtils.isBlank(siteNo) || "#".equals(siteNo)) {
            result.setErrorCode("500");
            result.setErrorStack("门店编号为空");
            result.setLocalizedMessage("门店编号为空");
        } else {
            result = new Result<Boolean>(true);
            SiteBaseInfo siteBaseInfo = new SiteBaseInfo();
            siteBaseInfo.setSiteNo(Long.parseLong(siteNo));
            boolean isSuccess = storeService.deleteSiteInfo(siteBaseInfo);
            if (isSuccess) {
                result.setResult(isSuccess);
            } else {
                result.setResult(false);
                result.setErrorCode("501");
                result.setLocalizedMessage("删除门店记录失败，非代码错误");
                result.setErrorStack("删除门店记录失败，非代码错误");
            }

        }
        return result;
    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public void exportExcelForPage(HttpServletRequest request, HttpServletResponse response) {
        SiteBaseInfo siteBaseInfo;
        OutputStream outputStream = null;
        try {
            response.setContentType("application/msexcel;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode("JDHMS-EXPORT-SITE.xls", "UTF-8"))));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            siteBaseInfo = assumeSiteBaseInfoEntity(request);
            siteBaseInfo.setErp(request.getParameter("loginUser"));
            siteBaseInfo = changeParamForMutilOrg(siteBaseInfo); //多区域优化
            Result<List<SiteBaseInfo>> result = storeService.querySiteBaseInfoForPageByMultiOrg(siteBaseInfo);
            if (result.isSuccess()) {
                List<SiteBaseInfo> siteBaseInfoList = result.getResult();
                if (siteBaseInfoList.size() > 0) {
                    /* 关联数据查询 */
                    List<String> querySyncList = new ArrayList<String>();
                    for(SiteBaseInfo siteBaseInfoQuery : siteBaseInfoList) {
                        querySyncList.add(String.valueOf(siteBaseInfoQuery.getSiteNo()));
                    }
                    Map<String, List<SyncSiteInfoMq>> compositeMap = storeService.queryBunchSyncSiteInfo(querySyncList);
                    /* 导出EXCEL开始 */
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    List<String> listCol = new ArrayList<String>();
                    listCol.add("区域");
                    listCol.add("运营中心");
                    listCol.add("门店名称");
                    listCol.add("业务编号");
                    listCol.add("门店类型");
                    listCol.add("省");
                    listCol.add("市");
                    listCol.add("县");
                    listCol.add("乡镇");
                    listCol.add("详细地址");
                    listCol.add("地址经度");
                    listCol.add("地址纬度");
                    listCol.add("管家");
                    listCol.add("店长");
                    listCol.add("商城账号");
                    listCol.add("门店级别");
                    listCol.add("门店状态");
                    listCol.add("开业日期");
                    listCol.add("创建时间");
                    listCol.add("联系电话");
                    listCol.add("承运站点编号");
                    listCol.add("安维网点编号");
                    HSSFSheet sheet = workbook.createSheet();
                    HSSFRow headRow = sheet.createRow(0);
                    for (int j = 0; j < listCol.size(); j++) {
                        HSSFCell cell = headRow.createCell(j);
                        cell.setCellValue(listCol.get(j));
                    }
                    for (int i = 1; i <= siteBaseInfoList.size(); i++) {
                        HSSFRow row = sheet.createRow(i);
                        for (int j = 0; j < listCol.size(); j++) {
                            HSSFCell cell = row.createCell(j);
                            if (j == 0) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getOrgName());
                            } else if (j == 1) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getDistributeName());
                            } else if (j == 2) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getSiteName());
                            } else if (j == 3) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getBussinessSiteNo());
                            } else if (j == 4) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getSiteType());
                            } else if (j == 5) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getProvinceName());
                            } else if (j == 6) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getCityName());
                            } else if (j == 7) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getCountryName());
                            } else if (j == 8) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getTownName());
                            } else if (j == 9) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getAddress());
                            } else if (j == 10) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getGpsLongitude());
                            } else if (j == 11) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getGpsLatitude());
                            } else if (j == 12) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getManagerName());
                            } else if (j == 13) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getSiteManager());
                            } else if (j == 14) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getJdAccount());
                            } else if (j == 15) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getSiteLevel());
                            } else if (j == 16) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getSiteStatus());
                            } else if (j == 17) {
                                Date openTime = siteBaseInfoList.get(i - 1).getOpenTime();
                                if(openTime != null) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String formater = sdf.format(openTime);
                                    cell.setCellValue(formater);
                                } else {
                                    cell.setCellValue("");
                                }
                            } else if (j == 18) {
                                Date createTime = siteBaseInfoList.get(i - 1).getCreateTime();
                                if(createTime != null) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String formater = sdf.format(createTime);
                                    cell.setCellValue(formater);
                                } else {
                                    cell.setCellValue("");
                                }
                            } else if (j == 19) {
                                cell.setCellValue(siteBaseInfoList.get(i - 1).getTelephone());
                            } else if (j == 20) {
                                if(compositeMap.containsKey(String.valueOf(siteBaseInfoList.get(i - 1).getSiteNo()))) {
                                    List<SyncSiteInfoMq> syncSiteInfoMqList = compositeMap.get(String.valueOf(siteBaseInfoList.get(i - 1).getSiteNo()));
                                    StringBuilder sb = new StringBuilder();
                                    for(int ii = 0; ii < syncSiteInfoMqList.size(); ii ++) {
                                        int systemSource = syncSiteInfoMqList.get(ii).getSystemSource();
                                        if(systemSource == 0) {
                                            sb.append(syncSiteInfoMqList.get(ii).getWebsiteNo());
                                            sb.append(",");
                                        }
                                    }
                                    String[] splitArray = sb.toString().split(",");
                                    String str = "";
                                    for(int jj = 0; jj < splitArray.length; jj ++) {
                                        str = str + splitArray[jj];
                                        if(jj + 1 != splitArray.length) {
                                            str = str + ",";
                                        }
                                    }
                                    cell.setCellValue(str);
                                }
                            } else if (j == 21) {
                                if(compositeMap.containsKey(String.valueOf(siteBaseInfoList.get(i - 1).getSiteNo()))) {
                                    List<SyncSiteInfoMq> syncSiteInfoMqList = compositeMap.get(String.valueOf(siteBaseInfoList.get(i - 1).getSiteNo()));
                                    StringBuilder sb = new StringBuilder();
                                    for(int ii = 0; ii < syncSiteInfoMqList.size(); ii ++) {
                                        int systemSource = syncSiteInfoMqList.get(ii).getSystemSource();
                                        if(systemSource == 1) {
                                            sb.append(syncSiteInfoMqList.get(ii).getWebsiteNo());
                                            sb.append(",");
                                        }
                                    }
                                    String[] splitArray = sb.toString().split(",");
                                    String str = "";
                                    for(int jj = 0; jj < splitArray.length; jj ++) {
                                        str = str + splitArray[jj];
                                        if(jj + 1 != splitArray.length) {
                                            str = str + ",";
                                        }
                                    }
                                    cell.setCellValue(str);
                                }
                            }
                        }
                    }
                    outputStream = response.getOutputStream();
                    workbook.write(outputStream);
                    /* 导出EXCEL结束 */
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        } finally{
            try{
                if(outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/querySiteBaseInfoForPage")
    @ResponseBody
    public Result<List<SiteBaseInfo>> querySiteBaseInfoForPage(HttpServletRequest request) {
        SiteBaseInfo siteBaseInfo;
        Result<List<SiteBaseInfo>> resultList;
        int totalPage;
        try {
            siteBaseInfo = assumeSiteBaseInfoEntity(request);
            siteBaseInfo.setErp(request.getParameter("erp"));
            siteBaseInfo = changeParamForMutilOrg(siteBaseInfo); //多区域优化
            int totalSize = storeService.querySiteBaseInfoCountByMultiOrg(siteBaseInfo);
            resultList = storeService.querySiteBaseInfoForPageByMultiOrg(siteBaseInfo);
            resultList.setTotalSize(String.valueOf(totalSize));
            if (totalSize % siteBaseInfo.getPageSize() > 0) {
                totalPage = totalSize / siteBaseInfo.getPageSize() + 1;
            } else {
                totalPage = totalSize / siteBaseInfo.getPageSize();
            }
            resultList.setTotalPage(String.valueOf(totalPage));
        } catch (Exception e) {
            e.printStackTrace();
            resultList = new Result<List<SiteBaseInfo>>(false);
            resultList.setErrorCode("500");
            resultList.setErrorStack(e.toString());
            resultList.setLocalizedMessage(e.getLocalizedMessage());
        }
        return resultList;
    }

    @RequestMapping(value = "/querySiteBaseInfoCountForPage")
    @ResponseBody
    public Result<Integer> querySiteBaseInfoCountForPageCount(HttpServletRequest request) {
        SiteBaseInfo siteBaseInfo;
        Result<Integer> result;
        try {
            siteBaseInfo = assumeSiteBaseInfoEntity(request);
            int resultInt = storeService.querySiteBaseInfoCount(siteBaseInfo);
            result = new Result<Integer>(true);
            result.setResult(resultInt);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result<Integer>(false);
            result.setErrorCode("500");
            result.setErrorStack(e.toString());
            result.setLocalizedMessage(e.getLocalizedMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateSiteUploadInfo")
    @ResponseBody
    public Result<Boolean> updateSiteUploadInfo(HttpServletRequest request) {
        Result<Boolean> result = new Result<Boolean>(false);
        try {
            SiteExtendInfo siteExtendInfo = assumeUpdateSiteUploadInfoEntity(request);
            boolean isSuccess = storeService.updateSiteUploadInfo(siteExtendInfo);
            if(isSuccess) {
                result.setSuccess(true);
                result.setResult(isSuccess);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorCode("500");
            result.setErrorStack(e.toString());
            result.setLocalizedMessage(e.getLocalizedMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertOrUpdateSiteInfoForPage")
    @ResponseBody
    public Result<Boolean> insertOrUpdateSiteInfoForPage(HttpServletRequest request) {
        Result<Boolean> result = new Result<Boolean>(false);
        try {
            SiteBaseInfo siteBaseInfoForQuery = assumeInsertSiteBaseInfoEntity(request, true);
            SiteExtendInfo siteExtendInfoForQuery = assumeInsertSiteExtendInfoEntity(request);
            boolean isSuccess = storeService.updateSiteInfoForPage(siteBaseInfoForQuery, siteExtendInfoForQuery);
            if(isSuccess) {
                result.setSuccess(true);
                result.setResult(isSuccess);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorCode("500");
            result.setErrorStack(e.toString());
            result.setLocalizedMessage(e.getLocalizedMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "/queryOneSiteInfoForPage")
    @ResponseBody
    public Result<SiteBaseInfo> queryOneSiteInfoForPage(HttpServletRequest request) {
        Result<SiteBaseInfo> result = new Result<SiteBaseInfo>(false);
        SiteBaseInfo siteBaseInfoForQuery = new SiteBaseInfo();
        SiteExtendInfo siteExtendInfoForQuery = new SiteExtendInfo();
        String siteNo = request.getParameter("siteNo");
        String loginUser = request.getParameter("loginUser");
        if (StringUtils.isBlank(siteNo) || "#".equals(siteNo)) {
            result.setErrorCode("500");
            result.setErrorStack("门店编号为空");
            result.setLocalizedMessage("门店编号为空");
        } else if(StringUtils.isBlank(loginUser)) {
            result.setErrorCode("501");
            result.setErrorStack("获取登陆用户失败");
            result.setLocalizedMessage("获取登陆用户失败");
        } else {
            siteBaseInfoForQuery.setSiteNo(Long.parseLong(siteNo));
            siteExtendInfoForQuery.setSiteNo(Long.parseLong(siteNo));
            SiteBaseInfo siteBaseInfo = storeService.queryOneSiteBaseInfoForPage(siteBaseInfoForQuery);
            List<String> roleList = storeService.querySystemAdmin(loginUser);
            if(roleList.contains("系统管理员")) {
                result.setSystemAdmin(true);
            } else {
                result.setSystemAdmin(false);
            }
            if (siteBaseInfo != null) {
                SiteExtendInfo siteExtendInfo = storeService.queryOneSiteExtendInfoForPage(siteExtendInfoForQuery);
                if (siteExtendInfo != null) {
                    siteBaseInfo.setSiteExtendInfo(siteExtendInfo);
                    List<SyncSiteInfoMq> syncInfo = storeService.querySyncSiteInfo(siteNo);
                    result.setSyncResult(syncInfo);
                }
                result.setResult(siteBaseInfo);
                result.setSuccess(true);
            }
        }
        return result;
    }

    private SiteExtendInfo assumeUpdateSiteUploadInfoEntity(HttpServletRequest request) throws ParseException {
        SiteExtendInfo siteExtendInfo = new SiteExtendInfo();
        String siteNo = request.getParameter("siteNo");
        String jssAttachement = request.getParameter("jssAttachement");
        if(StringUtils.isNotBlank(siteNo)) {
            siteExtendInfo.setSiteNo(Long.parseLong(siteNo));
        }
        if(StringUtils.isNotBlank(jssAttachement)) {
            siteExtendInfo.setJssAttachement(jssAttachement);
        } else {
            siteExtendInfo.setJssAttachement("#");
        }
        return siteExtendInfo;
    }

    private SiteExtendInfo assumeInsertSiteExtendInfoEntity(HttpServletRequest request) throws ParseException {
        SiteExtendInfo siteExtendInfo = new SiteExtendInfo();
        String createUser = request.getParameter("createUser");
        String siteNo = request.getParameter("siteNo");
        String companyName = request.getParameter("companyName");
        String companyType = request.getParameter("companyType");
        String bussinessLicenseNo = request.getParameter("bussinessLicenseNo");
        String bussinessLicenseAddress = request.getParameter("bussinessLicenseAddress");
        String bussinessLicenseTermValidity = request.getParameter("bussinessLicenseTermValidity");
        String taxType = request.getParameter("taxType");
        String taxRate = request.getParameter("taxRate");
        String taxRegistrationNo = request.getParameter("taxRegistrationNo");
        String bankDepositName = request.getParameter("bankDepositName");
        String bankDepositAddress = request.getParameter("bankDepositAddress");
        String bankAccount = request.getParameter("bankAccount");
        String bankAccountName = request.getParameter("bankAccountName");
        String jssAttachement = request.getParameter("jssAttachement");
        String remark = request.getParameter("remark");
        siteExtendInfo.setCreateTime(new Date());
        siteExtendInfo.setUpdateTime(new Date());
        if (StringUtils.isBlank(createUser)) {
            siteExtendInfo.setCreateUser("#");
            siteExtendInfo.setUpdateUser("#");
        } else {
            siteExtendInfo.setCreateUser(createUser);
            siteExtendInfo.setUpdateUser(createUser);
        }
        if (StringUtils.isNotBlank(siteNo)) {
            siteExtendInfo.setSiteNo(Long.parseLong(siteNo));
        }
        if (StringUtils.isBlank(companyName)) {
            siteExtendInfo.setCompanyName("#");
        } else {
            siteExtendInfo.setCompanyName(companyName);
        }
        if (StringUtils.isBlank(companyType)) {
            siteExtendInfo.setCompanyType(-1);
        } else {
            siteExtendInfo.setCompanyType(Integer.parseInt(companyType));
        }
        if (StringUtils.isBlank(bussinessLicenseNo)) {
            siteExtendInfo.setBussinessLicenseNo("#");
        } else {
            siteExtendInfo.setBussinessLicenseNo(bussinessLicenseNo);
        }
        if (StringUtils.isBlank(bussinessLicenseAddress)) {
            siteExtendInfo.setBussinessLicenseAddress("#");
        } else {
            siteExtendInfo.setBussinessLicenseAddress(bussinessLicenseAddress);
        }
        if (StringUtils.isNotBlank(bussinessLicenseTermValidity)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(bussinessLicenseTermValidity);
            siteExtendInfo.setBussinessLicenseTermValidity(date);
        }
        if (StringUtils.isBlank(taxType)) {
            siteExtendInfo.setTaxType(-1);
        } else {
            siteExtendInfo.setTaxType(Integer.parseInt(taxType));
        }
        if (StringUtils.isBlank(taxRate)) {
            siteExtendInfo.setTaxRate("#");
        } else {
            siteExtendInfo.setTaxRate(taxRate);
        }
        if (StringUtils.isBlank(taxRegistrationNo)) {
            siteExtendInfo.setTaxRegistrationNo("#");
        } else {
            siteExtendInfo.setTaxRegistrationNo(taxRegistrationNo);
        }
        if (StringUtils.isBlank(bankDepositName)) {
            siteExtendInfo.setBankDepositName("#");
        } else {
            siteExtendInfo.setBankDepositName(bankDepositName);
        }
        if (StringUtils.isBlank(bankDepositAddress)) {
            siteExtendInfo.setBankDepositAddress("#");
        } else {
            siteExtendInfo.setBankDepositAddress(bankDepositAddress);
        }
        if (StringUtils.isBlank(bankAccount)) {
            siteExtendInfo.setBankAccount("#");
        } else {
            siteExtendInfo.setBankAccount(bankAccount);
        }
        if (StringUtils.isBlank(bankAccountName)) {
            siteExtendInfo.setBankAccountName("#");
        } else {
            siteExtendInfo.setBankAccountName(bankAccountName);
        }
        if (StringUtils.isBlank(jssAttachement)) {
            siteExtendInfo.setJssAttachement("#");
        } else {
            siteExtendInfo.setJssAttachement(jssAttachement);
        }
        if (StringUtils.isBlank(remark)) {
            siteExtendInfo.setRemark("#");
        } else {
            siteExtendInfo.setRemark(remark);
        }
        return siteExtendInfo;
    }

    private SiteBaseInfo assumeInsertSiteBaseInfoEntity(HttpServletRequest request, boolean isModify) throws ParseException {
        SiteBaseInfo siteBaseInfo = new SiteBaseInfo();
        String orgNo = request.getParameter("orgNo");
        String orgName = request.getParameter("orgName");
        String distributeNo = request.getParameter("distributeNo");
        String distributeName = request.getParameter("distributeName");
        String siteName = request.getParameter("siteName");
        String siteNo = request.getParameter("siteNo");
        String siteStatus = request.getParameter("siteStatus");
        String startTime = request.getParameter("startTime");
        String openTime = request.getParameter("openTime");
        String provinceNo = request.getParameter("provinceNo");
        String provinceName = request.getParameter("provinceName");
        String cityNo = request.getParameter("cityNo");
        String cityName = request.getParameter("cityName");
        String countryNo = request.getParameter("countryNo");
        String countryName = request.getParameter("countryName");
        String townNo = request.getParameter("townNo");
        String townName = request.getParameter("townName");
        String address = request.getParameter("address");
        String gpsLongitude = request.getParameter("gpsLongitude");
        String gpsLatitude = request.getParameter("gpsLatitude");
        String jdAccount = request.getParameter("jdAccount");
        String siteManager = request.getParameter("siteManager");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String weixin = request.getParameter("weixin");
        String managerErp = request.getParameter("managerErp");
        String managerName = request.getParameter("managerName");
        String createUser = request.getParameter("createUser");
        String bussinessSiteNo = request.getParameter("bussinessSiteNo");
        siteBaseInfo.setSiteLevel(-1);
        siteBaseInfo.setSiteType(-1);
        siteBaseInfo.setCreateTime(new Date());
        siteBaseInfo.setUpdateTime(new Date());
        if(StringUtils.isBlank(bussinessSiteNo)) {
            siteBaseInfo.setBussinessSiteNo("#");
        } else {
            siteBaseInfo.setBussinessSiteNo(bussinessSiteNo);
        }
        if (StringUtils.isBlank(orgNo) || "-1".equals(orgNo)) {
            siteBaseInfo.setOrgNo("#");
        } else {
            siteBaseInfo.setOrgNo(orgNo);
        }
        if (StringUtils.isBlank(orgName)) {
            siteBaseInfo.setOrgName("#");
        } else {
            siteBaseInfo.setOrgName(orgName);
        }
        if (StringUtils.isBlank(distributeName)) {
            siteBaseInfo.setDistributeName("#");
        } else {
            siteBaseInfo.setDistributeName(distributeName);
        }
        if (StringUtils.isBlank(distributeNo) || "-1".equals(distributeNo)) {
            siteBaseInfo.setDistributeNo("#");
        } else {
            siteBaseInfo.setDistributeNo(distributeNo);
        }
        if (StringUtils.isBlank(siteName)) {
            siteBaseInfo.setSiteName("#");
        } else {
            siteBaseInfo.setSiteName(siteName);
        }
        if (StringUtils.isNotBlank(siteNo)) {
            siteBaseInfo.setSiteNo(Long.parseLong(siteNo));
        }
        if (StringUtils.isBlank(siteStatus)) {
            siteBaseInfo.setSiteStatus(0);
        } else {
            siteBaseInfo.setSiteStatus(Integer.parseInt(siteStatus));
        }
        if (StringUtils.isNotBlank(startTime) && "true".equals(startTime)) {
            siteBaseInfo.setStartTime(new Date());
        }
        if (StringUtils.isNotBlank(openTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(openTime);
            siteBaseInfo.setOpenTime(date);
        }
        if (StringUtils.isBlank(provinceNo) || "-1".equals(provinceNo)) {
            siteBaseInfo.setProvinceNo("#");
        } else {
            siteBaseInfo.setProvinceNo(provinceNo);
        }
        if (StringUtils.isBlank(provinceName) || "请选择".equals(provinceName)) {
            siteBaseInfo.setProvinceName("#");
        } else {
            siteBaseInfo.setProvinceName(provinceName);
        }
        if (StringUtils.isBlank(cityNo) || "-1".equals(cityNo)) {
            siteBaseInfo.setCityNo("#");
        } else {
            siteBaseInfo.setCityNo(cityNo);
        }
        if (StringUtils.isBlank(cityName) || "请选择".equals(cityName)) {
            siteBaseInfo.setCityName("#");
        } else {
            siteBaseInfo.setCityName(cityName);
        }
        if (StringUtils.isBlank(countryNo) || "-1".equals(countryNo)) {
            siteBaseInfo.setCountryNo("#");
        } else {
            siteBaseInfo.setCountryNo(countryNo);
        }
        if (StringUtils.isBlank(countryName) || "请选择".equals(countryName)) {
            siteBaseInfo.setCountryName("#");
        } else {
            siteBaseInfo.setCountryName(countryName);
        }
        if (StringUtils.isBlank(townNo) || "-1".equals(townNo)) {
            siteBaseInfo.setTownNo("#");
        } else {
            siteBaseInfo.setTownNo(townNo);
        }
        if (StringUtils.isBlank(townName) || "请选择".equals(townName)) {
            siteBaseInfo.setTownName("#");
        } else {
            siteBaseInfo.setTownName(townName);
        }
        if (StringUtils.isBlank(address)) {
            siteBaseInfo.setAddress("#");
        } else {
            siteBaseInfo.setAddress(address);
        }
        if (StringUtils.isBlank(gpsLongitude)) {
            siteBaseInfo.setGpsLongitude(0);
        } else {
            siteBaseInfo.setGpsLongitude(Double.parseDouble(gpsLongitude));
        }
        if (StringUtils.isBlank(gpsLatitude)) {
            siteBaseInfo.setGpsLatitude(0);
        } else {
            siteBaseInfo.setGpsLatitude(Double.parseDouble(gpsLatitude));
        }
        if (StringUtils.isBlank(jdAccount)) {
            siteBaseInfo.setJdAccount("#");
        } else {
            siteBaseInfo.setJdAccount(jdAccount);
        }
        if (StringUtils.isBlank(siteManager)) {
            siteBaseInfo.setSiteManager("#");
        } else {
            siteBaseInfo.setSiteManager(siteManager);
        }
        if (StringUtils.isBlank(telephone)) {
            siteBaseInfo.setTelephone("#");
        } else {
            siteBaseInfo.setTelephone(telephone);
        }
        if (StringUtils.isBlank(email)) {
            siteBaseInfo.setEmail("#");
        } else {
            siteBaseInfo.setEmail(email);
        }
        if (StringUtils.isBlank(weixin)) {
            siteBaseInfo.setWechat("#");
        } else {
            siteBaseInfo.setWechat(weixin);
        }
        if (StringUtils.isBlank(managerErp)) {
            if (!isModify) {
                siteBaseInfo.setManagerErp("#");
            }
        } else {
            if (!isModify) {
                siteBaseInfo.setManagerErp(managerErp);
            }
        }
        if (StringUtils.isBlank(managerName)) {
            siteBaseInfo.setManagerName("#");
        } else {
            siteBaseInfo.setManagerName(managerName);
        }
        if (StringUtils.isBlank(createUser)) {
            siteBaseInfo.setCreateUser("#");
            siteBaseInfo.setUpdateUser("#");
        } else {
            siteBaseInfo.setCreateUser(createUser);
            siteBaseInfo.setUpdateUser(createUser);
        }
        return siteBaseInfo;
    }

    private SiteBaseInfo assumeSiteBaseInfoEntity(HttpServletRequest request) throws ParseException {
        SiteBaseInfo siteBaseInfo = new SiteBaseInfo();
        String orgNo = request.getParameter("orgNo");
        String distributeNo = request.getParameter("distributeNo");
        String siteName = request.getParameter("siteName");
        String siteNo = request.getParameter("siteNo");
        String siteStatus = request.getParameter("siteStatus");
        String siteLevel = request.getParameter("siteLevel");
        String provinceNo = request.getParameter("provinceNo");
        String cityNo = request.getParameter("cityNo");
        String countryNo = request.getParameter("countryNo");
        String townNo = request.getParameter("townNo");
        String jdAccount = request.getParameter("jdAccount");
        String managerName = request.getParameter("managerName");
        String createTimeBegin = request.getParameter("createTimeBegin");
        String createTimeEnd = request.getParameter("createTimeEnd");
        String startTimeBegin = request.getParameter("startTimeBegin");
        String startTimeEnd = request.getParameter("startTimeEnd");
        String openTimeBegin = request.getParameter("openTimeBegin");
        String openTimeEnd = request.getParameter("openTimeEnd");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        String nextPage = request.getParameter("nextPage");
        String prevPage = request.getParameter("prevPage");
        String bussinessSiteNo = request.getParameter("bussinessSiteNo");
        String orgAllList = request.getParameter("orgAllList");
        if(StringUtils.isNotBlank(bussinessSiteNo)) {
            siteBaseInfo.setBussinessSiteNo(bussinessSiteNo);
        }
        if (!StringUtils.isBlank(orgNo) && !"-1".equals(orgNo)) {
            siteBaseInfo.setOrgNo(orgNo);
        }
        if (!StringUtils.isBlank(distributeNo) && !"-1".equals(distributeNo)) {
            siteBaseInfo.setDistributeNo(distributeNo);
        }
        if (!StringUtils.isBlank(siteName)) {
            siteBaseInfo.setSiteName(siteName);
        }
        if (!StringUtils.isBlank(siteNo)) {
            siteBaseInfo.setSiteNo(Long.parseLong(siteNo));
            siteBaseInfo.setSiteNoName(siteNo);
        }
        if (!StringUtils.isBlank(siteStatus)) {
            siteBaseInfo.setSiteStatus(Integer.parseInt(siteStatus));
        }
        if (!StringUtils.isBlank(siteLevel) && !"-1".equals(siteLevel)) {
            siteBaseInfo.setSiteLevel(Integer.parseInt(siteLevel));
        }
        if (!StringUtils.isBlank(provinceNo) && !"-1".equals(provinceNo)) {
            siteBaseInfo.setProvinceNo(provinceNo);
        }
        if (!StringUtils.isBlank(cityNo) && !"-1".equals(cityNo)) {
            siteBaseInfo.setCityNo(cityNo);
        }
        if (!StringUtils.isBlank(countryNo) && !"-1".equals(countryNo)) {
            siteBaseInfo.setCountryNo(countryNo);
        }
        if (!StringUtils.isBlank(townNo) && !"-1".equals(townNo)) {
            siteBaseInfo.setTownNo(townNo);
        }
        if (!StringUtils.isBlank(jdAccount)) {
            siteBaseInfo.setJdAccount(jdAccount);
        }
        if (!StringUtils.isBlank(managerName)) {
            siteBaseInfo.setManagerName(managerName);
        }
        if (!StringUtils.isBlank(createTimeBegin)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(createTimeBegin);
            siteBaseInfo.setCreateTimeBegin(date);
        }
        if (!StringUtils.isBlank(createTimeEnd)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(createTimeEnd);
            siteBaseInfo.setCreateTimeEnd(date);
        }
        if (!StringUtils.isBlank(startTimeBegin)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(startTimeBegin);
            siteBaseInfo.setStartTimeBegin(date);
        }
        if (!StringUtils.isBlank(startTimeEnd)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(startTimeEnd);
            siteBaseInfo.setStartTimeEnd(date);
        }
        if (!StringUtils.isBlank(openTimeBegin)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(openTimeBegin);
            siteBaseInfo.setOpenTimeBegin(date);
        }
        if (!StringUtils.isBlank(openTimeEnd)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(openTimeEnd);
            siteBaseInfo.setOpenTimeEnd(date);
        }
        if (!StringUtils.isBlank(currentPage)) {
            siteBaseInfo.setCurrentPage(Integer.parseInt(currentPage));
        }
        if (!StringUtils.isBlank(pageSize)) {
            siteBaseInfo.setPageSize(Integer.parseInt(pageSize));
        }
        if (!StringUtils.isBlank(nextPage)) {
            int tempNextPage = Integer.parseInt(nextPage);
            if (tempNextPage > 1) {
                siteBaseInfo.setNextPage(tempNextPage);
                siteBaseInfo.setStartNum(Integer.parseInt(pageSize) * (tempNextPage - 1));
            }
        }
        if (!StringUtils.isBlank(prevPage)) {
            int tempPrevPage = Integer.parseInt(prevPage);
            if (tempPrevPage >= 1) {
                siteBaseInfo.setPrevPage(tempPrevPage);
                siteBaseInfo.setStartNum((tempPrevPage - 1) * Integer.parseInt(pageSize));
            }
        }
        if(StringUtils.isNotBlank(orgAllList)) {
            String[] splitArray = orgAllList.split(",");
            List<String> orgAllNumsList = new ArrayList<String>();
            for(String splitStr : splitArray) {
                orgAllNumsList.add(splitStr);
            }
            siteBaseInfo.setOrgAllNums(orgAllNumsList);
        }
        return siteBaseInfo;
    }

    @RequestMapping(value = "/querySiteBaseInfoForManager")
    @ResponseBody
    public Result<List<SiteBaseInfo>> querySiteBaseInfoForManager(HttpServletRequest request) {
        SiteBaseInfo siteBaseInfo;
        Result<List<SiteBaseInfo>> resultList;
        try {
            siteBaseInfo = assumeSiteBaseInfoEntity(request);
            siteBaseInfo.setErp(request.getParameter("loginUser"));
            siteBaseInfo = changeParamForMutilOrg(siteBaseInfo); //多区域优化
            resultList = storeService.querySiteBaseInfoForPage(siteBaseInfo);
        } catch (Exception e) {
            e.printStackTrace();
            resultList = new Result<List<SiteBaseInfo>>(false);
            resultList.setErrorCode("500");
            resultList.setErrorStack(e.toString());
            resultList.setLocalizedMessage(e.getLocalizedMessage());
        }
        return resultList;
    }


    private SiteBaseInfo changeParamForMutilOrg(SiteBaseInfo siteBaseInfo){

        try{
            String userNo = siteBaseInfo.getErp();
            String orgNoTemp = siteBaseInfo.getOrgNo();
            String distributeNoTemp = siteBaseInfo.getDistributeNo();
            if("".equals(orgNoTemp)||"null".equals(orgNoTemp)||orgNoTemp==null){
                //选择的全部，需要获取当前用户所在区域
                //根据当前用户获取 所有所属区域
                List<Map<String, String>> list = areaCascadeService.getUserArea(userNo);
                for(Map<String, String> map : list){
                    String orgNo = map.get("org_no");
                    if("#".equals(orgNo)||list.size()>7){
                        //有最大权限，代表所有
                        list = null;
                        break;
                    }else if("#".equals(map.get("distribute_no"))){
                        //将#变成空格，方便sqlmapper中判断
                        map.put("distribute_no","");
                    }
                }
                siteBaseInfo.setOrgNos(list);
            }else if("".equals(distributeNoTemp)||"null".equals(distributeNoTemp)||distributeNoTemp==null){
                //有区域信息，但是运营中心为全部
                List<Map<String, String>> list = areaCascadeService.getOperateCenter(userNo,orgNoTemp);
                List<String> listDistribute =new ArrayList<String>();
                for(Map<String, String> map : list){
                    String disributeNo = map.get("distribute_no");
                    if("#".equals(disributeNo)){
                        //有最大权限，代表所有
                        listDistribute = null;
                        break;
                    }
                    listDistribute.add(disributeNo);
                }
                siteBaseInfo.setDistributeNos(listDistribute);
            }
        }catch (Exception e){
            logger.error("StoreServiceImpl changeParamForMutilOrg error:"+e.getMessage());
        }
        logger.info("StoreServiceImpl changeParamForMutilOrg change result:"+siteBaseInfo);
        return siteBaseInfo;
    }
}
