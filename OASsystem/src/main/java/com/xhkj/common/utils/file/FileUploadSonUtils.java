package com.xhkj.common.utils.file;


import com.xhkj.common.utils.security.Md5Utils;
import com.xhkj.framework.config.MainConfig;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文件上传工具类
 *
 * @author lws
 */
public class FileUploadSonUtils {
    /**
     * 默认上传的地址
     */
//    private static String defaultBaseDir = "F:"+File.separator+"upload";
    private static String defaultBaseDir = MainConfig.getProfile();
    /**
     * 图片上传的地址
     */
    private static String defaultImgBaseDir = "img";
    /**
     * 文件上传的地址
     */
    private static String defaultFileBaseDir = "file";
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 52428800;

    /**
     * 允许图片类型
     */
    public static final String IMAGE_JPG_EXTENSION = "jpg,gif,png,ico,bmp,jpeg";
    /**
     * word pdf 类型
     */
    public static final String WORD_PDF_EXTENSION = "pdf,doc,docx";
    private static int counter = 0;

    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadSonUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final String upload(MultipartFile file) throws IOException {
        try {
            String fileType=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
            return upload(getDefaultBaseDir(), file, fileType);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            String fileType=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
            return upload(baseDir, file, fileType);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir                   相对应用的基目录
     * @param file                      上传的文件
     * @param extension                 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws IOException                          比如读写文件出错时
     */
    public static final String upload(String baseDir, MultipartFile file, String extension)
            throws FileSizeLimitExceededException, IOException{

        assertAllowed(file);

        String fileName = encodingFilename(file.getOriginalFilename(), extension);
        baseDir = getPathByType(baseDir,extension);
        String filePath = baseDir +File.separator+ fileName;
        File desc = getAbsoluteFile(filePath);
        file.transferTo(desc);
        return filePath;
    }

    private static final String getPathByType(String baseDir,String fileType){
        String dateStr = new DateTime(new Date()).toString("yyyyMMdd");
        if (IMAGE_JPG_EXTENSION.contains(fileType.trim().toLowerCase())) {
            return baseDir + File.separator + defaultImgBaseDir + File.separator + dateStr;
        }else {
            return baseDir + File.separator + defaultFileBaseDir + File.separator + dateStr;
        }
    }
    private static final File getAbsoluteFile(String filePath) throws IOException {
        File desc = new File(filePath);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 编码文件名
     */
    private static final String encodingFilename(String filename, String extension) {
        filename = filename.replace("_", " ");
        filename = Md5Utils.hash(filename + System.nanoTime() + counter++) + extension;
        return filename;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     */
    public static final void assertAllowed(MultipartFile file) throws FileSizeLimitExceededException {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException("not allowed upload upload", size, DEFAULT_MAX_SIZE);
        }
    }
    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    public static boolean checkImgFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            // 获取文件后缀
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if (IMAGE_JPG_EXTENSION.contains(suffix.trim().toLowerCase())) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    public static boolean checkWordFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            // 获取文件后缀
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if (WORD_PDF_EXTENSION.contains(suffix.trim().toLowerCase())) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    public static boolean checkImgFiles(MultipartFile[] files) {
        try {
            if(files != null && files.length > 0) {
                for(MultipartFile file:files) {
                    String fileName = file.getOriginalFilename();
                    // 获取文件后缀
                    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                    if (!IMAGE_JPG_EXTENSION.contains(suffix.trim().toLowerCase())) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
