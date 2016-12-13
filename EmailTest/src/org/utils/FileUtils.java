package org.utils;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * File Utils
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Copy file
     *
     * @param srcFileName
     * @param descFileName
     * @return
     */
    public static boolean copyFile(String srcFileName, String descFileName) {
        return FileUtils.copyFileCover(srcFileName, descFileName, false);
    }

    /**
     * Copy file
     *
     * @param srcFileName
     * @param descFileName
     * @param coverlay
     * @return
     */
    public static boolean copyFileCover(String srcFileName, String descFileName, boolean coverlay) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            logger.debug("Copy failure, source file " + srcFileName + " is not exist .");
            return false;
        } else if (!srcFile.isFile()) {
            logger.debug("Copy failure, source file" + srcFileName + " is not file.");
            return false;
        }
        File descFile = new File(descFileName);
        if (descFile.exists()) {
            if (coverlay) {
                logger.debug("target file is exist, will delete.");
                if (!FileUtils.delFile(descFileName)) {
                    logger.debug("delete failure, file - " + descFileName);
                    return false;
                }
            } else {
                logger.debug("Copy failure , target file - " + descFileName + " is exist.");
                return false;
            }
        } else {
            if (!descFile.getParentFile().exists()) {
                logger.debug("target file directory is not exist.");
                if (!descFile.getParentFile().mkdirs()) {
                    logger.debug("Create directory failure.");
                    return false;
                }
            }
        }

        int readByte = 0;
        InputStream ins = null;
        OutputStream outs = null;
        try {
            ins = new FileInputStream(srcFile);
            outs = new FileOutputStream(descFile);
            byte[] buf = new byte[1024];
            while ((readByte = ins.read(buf)) != -1) {
                outs.write(buf, 0, readByte);
            }
            logger.debug("Copy file - " + srcFileName + " to " + descFileName);
            return true;
        } catch (Exception e) {
            logger.debug("Copy failure, " + e.getMessage());
            return false;
        } finally {
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException oute) {
                    oute.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException ine) {
                    ine.printStackTrace();
                }
            }
        }
    }

    /**
     * Copy directory
     *
     * @param srcDirName
     * @param descDirName
     * @return
     */
    public static boolean copyDirectory(String srcDirName, String descDirName) {
        return FileUtils.copyDirectoryCover(srcDirName, descDirName,
                false);
    }

    /**
     * Copy directory
     *
     * @param srcDirName
     * @param descDirName
     * @param coverlay
     * @return
     */
    public static boolean copyDirectoryCover(String srcDirName,
                                             String descDirName, boolean coverlay) {
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            return false;
        } else if (!srcDir.isDirectory()) {
            return false;
        }
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            if (coverlay) {
                if (!FileUtils.delFile(descDirNames)) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            if (!descDir.mkdirs()) {
                return false;
            }
        }

        boolean flag = true;
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = FileUtils.copyFile(files[i].getAbsolutePath(),
                        descDirName + files[i].getName());
                if (!flag) {
                    break;
                }
            }
            if (files[i].isDirectory()) {
                flag = FileUtils.copyDirectory(files[i]
                        .getAbsolutePath(), descDirName + files[i].getName());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            return false;
        }
        return true;

    }

    /**
     * Delete file
     *
     * @param fileName
     * @return
     */
    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            logger.debug(fileName + " file is not exist.");
            return true;
        } else {
            if (file.isFile()) {
                return FileUtils.deleteFile(fileName);
            } else {
                return true;
            }
        }
    }

    /**
     * Delete file
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isExistFile(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static String getMimeType(File file) {
/*        try {
            MagicMatch magic = Magic.getMagicMatch(file, false, true);
            return magic.getMimeType().toLowerCase();
        } catch (Exception e) {
            return "";
        }*/
        // Added by Carey on 2015-08-13 for temp solution of opening PDF in browser
        if (StringUtils.isNotBlank(file.getName())) {
            String ext = FilenameUtils.getExtension(file.getName());
            if (StringUtils.isNotBlank(ext)) {
                return "application/" + ext;
            }
        }
        return "";
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, String filePath, String displayName) throws IOException {
        File file = new File(filePath);

        if (StringUtils.isEmpty(displayName)) {
            displayName = file.getName();
        }

        if (!file.exists() || !file.canRead()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("The requested file does not exist.");
            return;
        }

        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);

        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);

        response.setContentType("application/x-download");
        response.setContentLength((int) file.length());

        String displayFilename = displayName.substring(displayName.lastIndexOf("_") + 1);
        displayFilename = displayFilename.replace(" ", "_");
        if (isIE) {
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");
        } else {
            displayFilename = new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
        }
        BufferedInputStream is = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            is = new BufferedInputStream(new FileInputStream(file));
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static void downloadPdf(HttpServletRequest request, HttpServletResponse response, String filePath, String displayName) throws IOException {
        File file = new File(filePath);

        if (StringUtils.isEmpty(displayName)) {
            displayName = file.getName();
        }

        if (!file.exists() || !file.canRead()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("You download a file does not exist.");
            return;
        }

        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);

        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);

        response.setContentType("application/pdf");
        response.setContentLength((int) file.length());

        String displayFilename = displayName.substring(displayName.lastIndexOf("_") + 1);
        displayFilename = displayFilename.replace(" ", "_");
        if (isIE) {
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");
        } else {
            displayFilename = new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
        }
        BufferedInputStream is = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            is = new BufferedInputStream(new FileInputStream(file));
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * Download file
     *
     * @param fileName
     * @param
     */
    public static void downloadFile(String fileName, HttpServletResponse response) {
        File file = new File(fileName);
        if (file.exists()) {
            // setting some response headers
            response.reset();
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("multipart/form-data");
            //if download pdf file, please add
            response.setHeader("Content-disposition", "attachment; filename=" + EncodeUtils.urlEncode(fileName));

            ServletOutputStream out;
            try {
                FileInputStream inputStream = new FileInputStream(file);
                out = response.getOutputStream();

                int b = 0;
                byte[] buffer = new byte[512];
                while (b != -1) {
                    b = inputStream.read(buffer);
                    out.write(buffer, 0, b);
                }
                inputStream.close();
                out.close();
                out.flush();
            } catch (Exception e) {
                /*try {
                    response.sendError(500, "Error: report display failure.");
				} catch (Exception ex) {
				}*/
            }
        } else {
            /*try {
                response.sendError(500, "Error: report file is not exist.");
			} catch (Exception e) {
			}*/
        }
    }

    /**
     * writer collection to text file
     *
     * @param collection
     * @param exceptFieldNames
     * @param fileName
     * @return
     */
    public static boolean writeFile(final Collection collection, final String exceptFieldNames, final String fileName) {
        boolean result = true;
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    return false;
                }
            }
            FileOutputStream out = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(osw);

            //insert data
            for (Object obj : collection) {
                Field[] fields = obj.getClass().getDeclaredFields();
                StringBuffer sb = new StringBuffer();
                for (Field field : fields) {
                    //if (!exceptFieldNames.contains(field.getName())) {
                    if (StringUtils.isNotEmpty(sb.toString())) {
                        sb.append(",");
                    }
                    Object value = PropertyUtils.getProperty(obj, field.getName());
                    if (value != null) {
                        if (field.getType().getSimpleName().equals("String")) {
                            sb.append("\"" + value.toString() + "\"");
                        } else {
                            sb.append(value.toString());
                        }
                    } else {
                        sb.append("");
                    }
                    //}
                }
                bw.write(sb.toString());
                bw.write("\n");
            }

            //close
            bw.close();
            osw.close();
            out.close();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * writer content to a text file
     *
     * @param content
     * @param fileName
     * @return
     */
    public static boolean writeFile(final String content, final String fileName) {
        boolean result = true;
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    return false;
                }
            }
            FileOutputStream out = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(osw);

            // insert data
            bw.write(content);
            // bw.write("\n");

            // close
            bw.close();
            osw.close();
            out.close();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public static int countLine(final String fullPathFileName) {
        int count = 0;

        FileInputStream stream;
        try {
            stream = new FileInputStream(fullPathFileName);
            byte[] buffer = new byte[8192];
            int n;
            while ((n = stream.read(buffer)) > 0) {
                for (int i = 0; i < n; i++) {
                    if (buffer[i] == '\n') count++;
                }
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    public static boolean zip(String zipRootPath, final List<String> inputFilenames) {
        return zipAndEncrypt(zipRootPath, inputFilenames, null);
    }

    public static boolean zipAndEncrypt(String zipRootPath, final List<String> sourceFiles, String
            password) {
        byte[] buffer = new byte[4096];
        if (!zipRootPath.endsWith(File.separator)) {
            zipRootPath = zipRootPath + File.separator;
        }
        File zipRootPathFile = new File(zipRootPath);
        if(!zipRootPathFile.exists()){
            zipRootPathFile.mkdirs();
        }
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String currentTime = df.format(calendar.getTime());
        try {

            FileOutputStream fos = new FileOutputStream(zipRootPath + currentTime + ".zip");
            net.lingala.zip4j.io.ZipOutputStream zos = new net.lingala.zip4j.io.ZipOutputStream(fos);

            ZipParameters parameters = new ZipParameters();

            if (StringUtils.isNotBlank(password)) {
                parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                parameters.setEncryptFiles(true);
                parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                parameters.setPassword(password);
            }

            for (String file : sourceFiles) {
                File f = new File(file);
                zos.putNextEntry(f, parameters);
                FileInputStream in = new FileInputStream(file);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                in.close();
            }

            zos.finish();

        } catch (IOException | ZipException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }


    public static List<String> listFilesForFolder(File folder) {
        List<String> list = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                list.add(fileEntry.getAbsolutePath());
                //log.info("listFilesForFolder() file found: "+fileEntry.getAbsolutePath());
            }
        }
        return list;
    }

    public static boolean moveFile(String fileWithPath, String destFolderPath) {
        return moveFile(fileWithPath, destFolderPath, true);
    }

    public static boolean moveFile(String fileWithPath, String destFolderPath, boolean addTimeStamp) {
        Path pathSrc = Paths.get(fileWithPath);
        String filenameDest = pathSrc.getFileName().toString();
        if (addTimeStamp) {
            String dateFormat = "MMddHHmmss";
            filenameDest += "_" + DateUtils.getDate(dateFormat);
        }
        Path pathDest = Paths.get(destFolderPath).resolve(filenameDest);
        //log.info("moveFile() pathSrc: "+pathSrc.toString()+", pathDest: "+pathDest.toString());
        try {
            Files.move(pathSrc, pathDest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("moveFile() failed to move from " + pathSrc.toString() + " to " + pathDest.toString());
            return false;
        }
        return true;
    }

    public static String concatFileToPath(String path, String filename) {
        if (path == null || path.isEmpty()) {
            return filename.trim();
        } else if (filename == null || filename.isEmpty()) {
            return path.trim();
        } else if (path.trim().endsWith("\\") || path.trim().endsWith("/")) {
            return path.trim() + filename.trim();
        } else {
            return path.trim() + File.separator + filename.trim();
        }
    }

    public static boolean createDirectoryIfNotExist(String tempPath) {
        File directory = new File(tempPath);
        if (directory.exists() && directory.isFile()) {
            logger.error("createDirectoryIfNotExist() " + tempPath + " is a file");
            return false;
        } else {
            try {
                if (!directory.exists()) {
                    directory.mkdir();
                }
            } catch (Exception e) {
                System.out.println("prompt for error");
            }
        }
        return true;
    }

    public static void writeByteToFile(byte[] data, String imageName) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(new File(imageName));
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

    public static String getFilenameFromPath(String pathWithFilename) {
        Path p = Paths.get(pathWithFilename);
        if (p == null || p.getFileName() == null || p.getFileName().toString().isEmpty()) {
            logger.error("getFilenameFromPath() cannot get filename from " + pathWithFilename);
            return "";
        }
        return p.getFileName().toString();
    }

}
