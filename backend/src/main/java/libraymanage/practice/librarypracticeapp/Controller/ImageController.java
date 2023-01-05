package libraymanage.practice.librarypracticeapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import libraymanage.practice.librarypracticeapp.Entity.ImageEntity;
import libraymanage.practice.librarypracticeapp.Repository.ImageEntityRepos;
import libraymanage.practice.librarypracticeapp.Service.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping("/uploadimage")
    public ResponseEntity<String> uploadImage(@RequestParam(name = "file") MultipartFile file) {
        return new ResponseEntity<String>(imageService.uploadImage(file), HttpStatus.CREATED);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/{name}")
    public ResponseEntity<?> getImage(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imageService.getImage(name));
    }

    @PostMapping("/uploadimage2")
    public ResponseEntity<String> uploadImage2(@RequestParam(name = "file") MultipartFile file) {
        return new ResponseEntity<String>(imageService.uploadimage2(file), HttpStatus.CREATED);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/v2/{name}")
    public ResponseEntity<?> getimage2(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imageService.getImage2(name));
    }
    //test multipart file including file image and json file in form-data type 
    @PostMapping("/test")
    public ResponseEntity<HttpStatus> testRequestpart(@RequestPart("file") MultipartFile file, @RequestPart("image") ImageEntity imageEntity) {
        System.out.println(file.getOriginalFilename());
        System.out.println(imageEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

  
}
