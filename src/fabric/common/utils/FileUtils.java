/**
 * @(#)FileUtils.java, 2013-8-6. 
 * 
 */
package fabric.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likaihua
 */
public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 拷贝指定文件到指定目录下
     * 
     * @param fileSource
     *            String
     * @param dirDest
     *            String
     * @throws IOException
     */
    public static void copyFile(String fileSource, String dirDest,
        String destFileName) throws IOException {
        FileOutputStream output = null;
        FileInputStream input = null;
        int BUFFER_SIZE = 1024 * 64;
        File fileFrom = new File(fileSource);
        File fileToDir = new File(dirDest);
        //检查文件是够存在
        if (!fileFrom.exists()) {
            throw new IOException("source file not exist.");
        }
        if (!fileToDir.exists())
            fileToDir.mkdirs();
        String filename = fileFrom.getName();
        //指定目录下的文件
        File fileTo = new File(dirDest + File.separator + filename);
        if (destFileName != null && !destFileName.isEmpty()) {
            fileTo = new File(dirDest + File.separator + destFileName);
        }

        if (fileTo.exists()) {
            fileTo.deleteOnExit();
        }

        try {
            input = new FileInputStream(fileFrom);
            output = new FileOutputStream(fileTo);
            int length = input.available();
            int bufferSize = BUFFER_SIZE;
            if (length < BUFFER_SIZE) {
                bufferSize = length;
            }

            byte[] buffer = new byte[bufferSize];
            int readTotal = 0;
            int readSize = 0;
            while ((readSize = input.read(buffer)) > -1) {
                readTotal += readSize;
                if (readTotal > length) {
                    int over = readSize - (readTotal - length);
                    output.write(buffer, 0, over);
                    output.flush();
                    break;
                } else if (readTotal == length) {
                    output.write(buffer, 0, readSize);
                    output.flush();
                    break;
                } else {
                    output.write(buffer, 0, readSize);
                    output.flush();
                }
            }
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        } catch (SecurityException e) {
            logger.info(e.getMessage());
        } finally {
            if (output != null)
                output.close();
            if (input != null)
                input.close();
        }
    }

    /**
     * 删除单个文件
     * 
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除某个文件夹下的所有文件夹和文件
     * 
     * @param delpath
     *            String
     * @throws FileNotFoundException
     * @throws IOException
     * @return boolean
     */
    public static boolean deleteFiles(String delpath) {
        File file = new File(delpath);
        // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
        if (!file.isDirectory()) {
            file.delete();
        } else if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File delfile = new File(delpath + "\\" + filelist[i]);
                if (!delfile.isDirectory()) {
                    delfile.delete();
                    logger.info(delfile.getAbsolutePath() + "删除文件成功");
                } else if (delfile.isDirectory()) {
                    deleteFiles(delpath + "\\" + filelist[i]);
                }
            }
            logger.info(file.getAbsolutePath() + "删除成功");
            file.delete();
        }

        return true;
    }

}
