package com.yashpz.examination_system.examination_system.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            return "Error";
        }
    }

    public String deleteImageById(String publicId) throws IOException {
        Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return (String) deleteResult.get("result");
    }

    public String deleteImageByURL(String URL) {
        try {
            String publicId = extractPublicId(URL);
            if (publicId == null) return "Invalid URL";
            return deleteImageById(publicId);
        } catch (Exception e) {
            return "Error";
        }
    }

    private static String extractPublicId(String imageUrl) {
        if (imageUrl == null) return null;

        String regex = "http://res\\.cloudinary\\.com/[^/]+/image/upload(?:/v[0-9]+)?/([^/]+)\\.[a-zA-Z]{3,4}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(imageUrl);

        if (matcher.find())
            return matcher.group(1);
        else
            return "OK";
    }
}
