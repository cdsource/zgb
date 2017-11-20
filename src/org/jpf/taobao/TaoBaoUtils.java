/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年5月29日 上午7:54:52 类说明
 */

package org.jpf.taobao;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.utils.AiStringUtil;
import com.asiainfo.utils.ios.AiFileUtil;

/**
 * 
 */
public class TaoBaoUtils {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    private TaoBaoUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     * @category 删除手机详情目录
     * @author 吴平福
     * @param strFileName
     * @param SpecificPath
     * @throws IOException update 2017年6月19日
     */
    public static void removeMediaPath(String strFileName, final String SpecificPath) throws Exception {
        String strFilePath = strFileName.substring(0, strFileName.length() - 4) + java.io.File.separator + SpecificPath;
        // logger.debug(strFilePath);

        AiFileUtil.delDirWithFiles(strFilePath);

    }

    /**
     * 
     * @category 写入到CVS文件
     * @author 吴平福
     * @param strCvsName
     * @param sb
     * @throws Exception update 2017年6月22日
     */
    public static void writeToCsv(String strCvsName, StringBuilder sb) throws Exception {
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(strCvsName), "x-UTF-16LE-BOM");
            writer.write(sb.toString());
            writer.flush();
            sb.delete(0, sb.length());
        } catch (Exception ex) {
            // TODO: handle exception

            logger.error(ex);
            throw ex;
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (Exception ex2) {
                // TODO: handle exception
            }
        }

    }

    /**
     * 
     * @category @author 吴平福
     * @param strCvsName
     * @param vStrings
     * @throws Exception update 2017年6月30日
     */
    public static void writeToCsv(String strCvsName, Vector<String[]> vStrings) throws Exception {
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(strCvsName), "x-UTF-16LE-BOM");

            logger.debug("记录数:" + vStrings.size());

            for (int i = 0; i < vStrings.size(); i++) {
                for (int j = 0; j < vStrings.get(i).length; j++) {
                    if (0 == j) {
                        writer.write(vStrings.get(i)[j]);
                        writer.write("\t");
                    } else {
                        writer.write(vStrings.get(i)[j] + "\t");
                    }
                }
                writer.write("\n");
            }
            writer.flush();
        } catch (Exception ex) {
            // TODO: handle exception
            logger.error(ex);
            throw ex;
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (Exception ex2) {
                // TODO: handle exception
            }
        }

    }

    /**
     * 
     * @category CSV文件追加操作
     * @author 吴平福
     * @param strCsvFileName
     * @param content update 2017年6月29日
     */
    public static void appendCsv(final String strCsvFileName, final String content) {

        BufferedWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(strCsvFileName, true), "x-UTF-16LE-BOM"));
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (Exception ex) {
                // TODO: handle exception
            }
        }
    }

    /**
     * 
     * @category 当前商品的目录
     * @author 吴平福
     * @param strCvsName
     * @return update 2017年10月23日
     */
    public static String getCurrentGoodPath(String strCvsName) {
        return strCvsName.substring(0, strCvsName.length() - 4).trim();
    }
    /**
     * 
     * @category 
     * @author 吴平福 
     * @param strCvsName
     * @return
     * update 2017年10月28日
     */
    public static String getOutPutLastPath(String strCvsName) {
        String strCvsFilePath=getCurrentGoodPath(strCvsName);
        int i = strCvsFilePath.lastIndexOf(File.separator);
        if (i > 0) {
            strCvsFilePath = strCvsFilePath.substring(i, strCvsFilePath.length());
        }else
        {
            i = strCvsFilePath.lastIndexOf("/");
            if (i > 0) {
                strCvsFilePath = strCvsFilePath.substring(i, strCvsFilePath.length());
            }
        }
        return strCvsFilePath;

    }
    /**
     * 
     * @category @author 吴平福
     * @param strPicInfo:D:/胡强101/82_套装_200/contentPic/长袖卫衣连帽三件套装4/434fe05-1.gif
     * @param strCvsName
     * @param strConstPicPath
     * @return
     * @throws Exception update 2017年10月27日
     */
    public static String getPicPath(final String strOldPicInfo, String strCvsName, String strConstPicPath)
            throws Exception {
        // D:/胡强101/82_套装_200/contentPic/长袖卫衣连帽三件套装4/434fe05-1.gif
        //logger.info(strOldPicInfo);
        String strExistPicFile=getOutPutLastPath(strCvsName);
        int i=strOldPicInfo.indexOf(strExistPicFile);
        strExistPicFile=strCvsName.substring(0,strCvsName.length()-4)+strOldPicInfo.substring(i+strExistPicFile.length(), strOldPicInfo.length());
        if (!AiFileUtil.FileExist(strExistPicFile)) {
            // logger.error("文件不存在:{}",strOldPicInfo);
            // throw new Exception("文件不存在:"+strOldPicInfo);
            return null;
        }
        //logger.info(File.separator);
        //String strOldPicPath = getFilePath(strOldPicInfo);
        
        String strNewPicPath = strConstPicPath+strOldPicInfo.substring(i,strOldPicInfo.length());
        String strNewPicInfo = AiStringUtil.ReplaceAll(strOldPicInfo, strOldPicInfo, strNewPicPath);
        // logger.info(strNewPicInfo);
        return strNewPicInfo;
    }
    /**
     * @category获取文件路径
     * @param sFilePathName String
     * @return String
     */
    public static String getFilePath(String sFilePathName) {
        int i = sFilePathName.lastIndexOf(File.separator);
        if (i > 0) {
            sFilePathName = sFilePathName.substring(0, i);
        }else
        {
            i = sFilePathName.lastIndexOf("/");
            if (i > 0) {
                sFilePathName = sFilePathName.substring(0, i);
            }
        }
        return sFilePathName;
    }
    /**
     * 
     * @category 显示描述图片的路径
     * @author 吴平福
     * @param strPicInfo update 2017年6月22日
     */
    public static String getPicsPath(final String strPicInfo, String strCvsName, String strConstPicPath)
            throws Exception {
        
        strCvsName=strCvsName.replaceAll("\\\\","/");
        strConstPicPath=strConstPicPath.replaceAll("\\\\","/");
        //logger.info("strCvsName:"+strCvsName);
        //logger.info("strConstPicPath:"+strConstPicPath);
        String strKey = "<IMG";
        String strKeyFile = "file:///";
        //logger.info(strPicInfo);
        String[] strImgFiles = strPicInfo.replaceAll("<img", "<IMG")  .split(strKey);

        logger.debug(strImgFiles.length);

        if (strImgFiles.length > 1) {

            StringBuilder sb = new StringBuilder();
            sb.append(strImgFiles[0]);
            for (int i = 1; i < strImgFiles.length; i++) {
                // logger.info(strImgFiles[i]);
                int iPosBegin = strImgFiles[i].indexOf(strKeyFile);
                int iPosEnd = strImgFiles[i].indexOf("\"", iPosBegin + strKeyFile.length());
                if (iPosBegin > 0 && iPosEnd > iPosBegin + strKeyFile.length()) {
                    // logger.info(strImgFiles[i].substring(iPosBegin+strKeyFile.length(),
                    // iPosEnd));
                    String strNewPicInfo =
                            getPicPath(strImgFiles[i].substring(iPosBegin + strKeyFile.length(), iPosEnd), strCvsName,
                                    strConstPicPath);
                    if (null != strNewPicInfo) {
                        sb.append(strKey).append(strImgFiles[i].substring(0, iPosBegin)).append(strKeyFile) .append(strNewPicInfo)
                                .append(strImgFiles[i].substring(iPosEnd, strImgFiles[i].length()));
                    }else
                    {
                        int iPos=strImgFiles[i].indexOf(">");
                        sb.append(strImgFiles[i].substring(iPos+1, strImgFiles[i].length()));
                        logger.warn  ("图片不存在："+strImgFiles[i]);
                    }
                    // sb.append(getPicPath(strImgFiles[i].substring(0, j),
                    // strCvsName,strConstPicPath) ).append(strImgFiles[i].substring(j,
                    // strImgFiles[i].length()));
                } else {
                    throw new Exception("非法的图片:"+strImgFiles[i]);
                }
                // sb.append(strKey).append(strImgFiles[i]);
            }
            logger.info(sb);
            return sb.toString();
        } else {
            throw new Exception("没有找到的图片");
        }

    }

}
