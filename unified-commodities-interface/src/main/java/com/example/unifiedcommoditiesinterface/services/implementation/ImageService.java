package com.example.unifiedcommoditiesinterface.services.implementation;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    public String imageUpload(MultipartFile file, String username) throws IOException{
        
        String filePath = "http://localhost:8080/static/images/users/" + username + file.getOriginalFilename();

        if(file.getContentType().split("/")[0].equals("image")){

            file.transferTo(new File("D:\\varun\\college\\trustrace\\Unified Commodities Interface\\unified-commodities-interface\\src\\main\\resources\\static\\images\\users\\"+ username + file.getOriginalFilename()));
        }
        return filePath;
    }
}
