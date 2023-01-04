package libraymanage.practice.librarypracticeapp.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import libraymanage.practice.librarypracticeapp.Entity.ImageEntity;
import libraymanage.practice.librarypracticeapp.Entity.ImageEntity2;
import libraymanage.practice.librarypracticeapp.Exception.EntityNotFoundException;
import libraymanage.practice.librarypracticeapp.Repository.ImageEntityRepos;
import libraymanage.practice.librarypracticeapp.Repository.ImageEntityRepos2;
import libraymanage.practice.librarypracticeapp.Utils.ImageUtil;

@Service
public class ImageService {
    @Autowired
    ImageEntityRepos imageEntityRepos;

    @Autowired
    ImageEntityRepos2 imageEntityRepos2;

    @Autowired
    ImageUtil imageUtil;

    private static final String path = "C:\\Users\\Quan Doan\\Desktop\\fullstack-SpringBoot-React\\practice-library-app\\backend\\src\\main\\resources\\images";
 

    public String uploadImage(MultipartFile file) {
        ImageEntity image = new ImageEntity(file.getOriginalFilename(), file.getContentType(), path + File.separator + file.getOriginalFilename());

        if(image == null) {
            return null;
        }
        String url = "/api/images/" + file.getOriginalFilename();
        
       try {
        file.transferTo(new File(path + File.separator + file.getOriginalFilename()));
        imageEntityRepos.save(image);
        return url;
       } catch (IOException ex) {
        throw new EntityNotFoundException(ex.getMessage());
       }
    }

    public byte[] getImage(String name) {
        Optional<ImageEntity> entity = imageEntityRepos.findByName(name);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the image with name " + name + " not found");
        }
        ImageEntity image = entity.get();
       try {
    
        byte[] imageByte = Files.readAllBytes(new File(image.getFilepath()).toPath());
        return imageByte;
       } catch (IOException ex) {
        throw new EntityNotFoundException(ex.getMessage());
       }


    }

    public String uploadimage2(MultipartFile file)  {
       try {
        System.out.println(file.getOriginalFilename());
        byte[] fileByte = imageUtil.compressImage(file.getBytes());
        System.out.println(fileByte.toString());
        ImageEntity2 imageEntity2 = new ImageEntity2(file.getOriginalFilename(), file.getContentType(), fileByte);
        if(imageEntity2 != null) {
            imageEntityRepos2.save(imageEntity2);
            String url = "/api/images/v2/" + file.getOriginalFilename();
            return url;
        } 
            return null;
        
        
       } catch (IOException ex) {
        throw new IllegalStateException(ex.getMessage());
       }
    }

    public byte[] getImage2(String name) {
        Optional<ImageEntity2> entity = imageEntityRepos2.findByName(name);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the image with name "+ name + " not found");
        }
        ImageEntity2 imageEntity2 = entity.get();
        if(imageEntity2 == null ) {
            return null;
        }
        byte[] imageByte = imageUtil.decompressImage(imageEntity2.getDatabyte());
        return imageByte;
    }
  

   
}
