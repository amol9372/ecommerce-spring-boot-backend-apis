package org.ecomm.ecommproduct.utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.exception.ErrorCodes;
import org.ecomm.ecommproduct.exception.ErrorResponse;
import org.ecomm.ecommproduct.exception.S3FileException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Component
public class S3Utility {

  private S3Utility() {}

  private static final String BUCKET_NAME = "ecomm-backend-images";

  private static S3Client s3Client = null;

  public static S3Client getClient() {
    if (Objects.isNull(s3Client)) {

      Region region = Region.CA_CENTRAL_1;
      s3Client =
          S3Client.builder()
              .credentialsProvider(DefaultCredentialsProvider.create())
              .region(region)
              .build();
    }

    return s3Client;
  }

  public String putObject(MultipartFile file) {

    PutObjectRequest putOb = getPutObjectRequest(file);
    try {
      getClient().putObject(putOb, RequestBody.fromBytes(file.getBytes()));
      var s3key = putOb.getValueForField("Key", String.class).orElseThrow();

      log.info(
          "[{}] File {} uploaded at path {}",
          MDC.get("requestId"),
          file.getOriginalFilename(),
          s3key);

      GetUrlRequest request = GetUrlRequest.builder().bucket(BUCKET_NAME).key(s3key).build();

      URL url = getClient().utilities().getUrl(request);
      return url.toString();
    } catch (IOException e) {
      var errorResponse = ErrorResponse.builder().code(ErrorCodes.S3_EXCEPTION).build();
      throw new S3FileException(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
    }
  }

  public List<String> putObjects(List<MultipartFile> files) {

    return files.stream().map(this::putObject).collect(Collectors.toList());
  }

  private PutObjectRequest getPutObjectRequest(MultipartFile file) {
    return PutObjectRequest.builder()
        .bucket(BUCKET_NAME)
        .key(Objects.requireNonNull(file.getOriginalFilename()))
        .build();
  }
}
