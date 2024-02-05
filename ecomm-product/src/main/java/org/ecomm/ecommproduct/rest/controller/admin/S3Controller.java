package org.ecomm.ecommproduct.rest.controller.admin;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.ecomm.ecommproduct.utils.S3Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("admin/image")
public class S3Controller {

  @Autowired
  S3Utility s3Utility;

  @Autowired
  HttpServletRequest request;

  @PostMapping("upload")
  public ResponseEntity<?> uploadImages(
      @RequestParam("files") List<MultipartFile> files) {

    var response = s3Utility.putObjects(files);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
