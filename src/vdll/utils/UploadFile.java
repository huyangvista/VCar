package vdll.utils;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

/**
 * Created by Hocean on 2017/5/17.
 */
public class UploadFile {

    public static void save(HttpServletRequest request, String path, String name) {
        try {

            DiskFileUpload diskFileUpload = new DiskFileUpload();
            List<FileItem> list = diskFileUpload.parseRequest(request);

            File file1 = new File(request.getServletContext().getRealPath(path), name);
            file1.getParentFile().mkdirs();
            file1.createNewFile();
            InputStream ins = list.get(0).getInputStream();
            OutputStream ous = new FileOutputStream(file1);
            try {
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = ins.read(buffer)) > -1)
                    ous.write(buffer, 0, len);
            } finally {
                ous.close();
                ins.close();
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean find(HttpServletRequest request, String path, String name) {
        File file1 = new File(request.getServletContext().getRealPath(path), name);
        return file1.exists();

    }
}