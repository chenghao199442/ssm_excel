package com.chenghao.poi.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public class FileUpload {

    private static final String UPLOAD_DIRECTORY = "upload";
    private static final Integer MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final Integer MAX_FILE_SIZE = 1024 * 1024 * 40;
    private static final Integer MAX_REQUEST_SIZE = 1024 * 1024 * 50;

    static String filePath;

    public static String upload(HttpServletRequest request) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        upload.setHeaderEncoding("UTF-8");
        String uploadPath = request.getSession().getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        item.write(storeFile);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public String deleteFile(String filePath) {
        File file = new File(filePath);
        String code = "fail";
        if (file.exists() && file.isFile()) {
            try {
                file.delete();
                code = "success";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return code;
    }
}
