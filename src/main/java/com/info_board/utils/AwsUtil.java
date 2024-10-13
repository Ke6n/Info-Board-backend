package com.info_board.utils;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.BlockingInputStreamAsyncRequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class AwsUtil {
    private static final String ACCESS_KEY=System.getenv("ACCESS_KEY");
    private static final String SECRET_KEY=System.getenv("SECRET_KEY");
    private static final String BUCKET_NAME = "info-board";

    /**
     *
     * @param fileName file name
     * @param inputStream inputStream of the file
     * @return URL of file
     */
    public static String uploadFile(String fileName, InputStream inputStream) {
        String url = "";
        String key = BUCKET_NAME+"/avatar/"+fileName;
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

        S3AsyncClient s3AsyncClient = S3AsyncClient.builder()
                .multipartEnabled(true)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.EU_NORTH_1)
                .build();
        try {
            BlockingInputStreamAsyncRequestBody body =
                    AsyncRequestBody.forBlockingInputStream(null);
            CompletableFuture<PutObjectResponse> responseFuture =
                    s3AsyncClient.putObject(r -> r.bucket(BUCKET_NAME).key(key), body);
            body.writeInputStream(inputStream);
            responseFuture.join();
            url = getFileUrl(s3AsyncClient, key);
        }catch (S3Exception e) {
            System.err.println("S3 Error Code: " + e.awsErrorDetails().errorCode());
            System.err.println("S3 Error Message: " + e.awsErrorDetails().errorMessage());
        }catch (Exception e) {
            System.err.println("Upload failed: " + e.getMessage());
        } finally {
            if (s3AsyncClient != null) {
                s3AsyncClient.close();
                System.out.println("S3 client closed.");
            }
        }
        return url;
    }

    private static String getFileUrl(S3AsyncClient s3, String keyName) {
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(BUCKET_NAME)
                .key(keyName)
                .build();

        return s3.utilities().getUrl(request).toString();
    }
}
