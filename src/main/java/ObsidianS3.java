import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class ObsidianS3 {
    public static void main(String[] args) {

        S3Client s3Client = new S3Provider().getS3Client();
        String bucketName = "obsidian-dados";

        try {
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3Client.createBucket(createBucketRequest);
            System.out.println("Bucket criado com sucesso: " + bucketName);
        } catch (S3Exception e) {
            System.err.println("Erro ao criar o bucket: " + e.getMessage());
        }

        try {
            List<Bucket> buckets = s3Client.listBuckets().buckets();
            System.out.println("Lista de buckets:");
            for (Bucket bucket : buckets) {
                System.out.println("- " + bucket.name());
            }
        } catch (S3Exception e) {
            System.err.println("Erro ao listar buckets: " + e.getMessage());
        }

        try {
            ListObjectsRequest listObjects = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build();

            List<S3Object> objects = s3Client.listObjects(listObjects).contents();
            System.out.println("Objetos no bucket " + bucketName + ":");
            for (S3Object object : objects) {
                System.out.println("- " + object.key());
            }
        } catch (S3Exception e) {
            System.err.println("Erro ao listar objetos no bucket: " + e.getMessage());
        }

//        try {
//            String uniqueFileName = UUID.randomUUID().toString();
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(uniqueFileName)
//                    .build();
//
//            File file = new File("SEEG11.1-DADOS-NACIONAIS.txt");
//            s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
//
//            System.out.println("Arquivo '" + file.getName() + "' enviado com sucesso com o nome: " + uniqueFileName);
//        } catch (S3Exception e) {
//            System.err.println("Erro ao fazer upload do arquivo: " + e.getMessage());
//        }

        try {
            List<S3Object> objects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build()).contents();
            for (S3Object object : objects) {
                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(object.key())
                        .build();

                InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
                Files.copy(inputStream, new File(object.key()).toPath());
                System.out.println("Arquivo baixado: " + object.key());
            }
        } catch (IOException | S3Exception e) {
            System.err.println("Erro ao fazer download dos arquivos: " + e.getMessage());
        }

//        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                .bucket("nome-do-bucket")
//                .key("identificador-do-objeto")
//                .build();
//
//        s3Client.deleteObject(deleteObjectRequest);
//        System.out.println("Objeto deletado: " + "identificador-do-objeto");
    }
}

