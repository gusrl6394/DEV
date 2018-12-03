package com.itbank.helper;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class FileHelper {
	 public static String upload(String uploadPath, MultipartFile multipartFile, HttpServletRequest request) {
        String uploadedFileUrl = null;
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        System.out.println("rootPath:"+rootPath);
        String realUploadPath = rootPath + "/resources" + uploadPath;
        System.out.println("realUploadPath:"+realUploadPath);
        File dir = new File(realUploadPath);
        if (!dir.exists())
            dir.mkdirs();
        System.out.println("dir.getAbsolutePath():"+dir.getAbsolutePath());
        System.out.println("multipartFile.hashCode():"+multipartFile.hashCode()==null);
        System.out.println("mutipartFile.getOriginalFilename():"+multipartFile.getOriginalFilename());
        File file = new File(dir.getAbsolutePath() + File.separator + multipartFile.hashCode()
                + multipartFile.getOriginalFilename());
        System.out.println("file.getAbsolutePath():"+file.getAbsolutePath());
        try {
            multipartFile.transferTo(file);
            String contextPath = request.getContextPath();
            uploadedFileUrl = contextPath + uploadPath + File.separator + file.getName();
            System.out.println("uploadedFileUrl:"+uploadedFileUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedFileUrl;
    }
}
