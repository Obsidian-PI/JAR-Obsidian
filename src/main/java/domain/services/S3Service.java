package domain.services;

import configs.S3Provider;
import domain.entities.Emissao;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class S3Service {
    S3Client s3Client = new S3Provider().getS3Client();
    String bucketName = "s3obsidian";
    public void baixarArquivo() throws IOException {
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
    }

    public void logToCsv(List<Emissao> logList) throws IOException {
        FileWriter writer = new FileWriter("dadosEmissoes.csv");

        String collect = logList.stream()
                .map(emissao -> emissao.toString())
                .collect(Collectors.joining("\n"));

        System.out.println(collect);

        writer.write(collect);
        writer.close();
    }

    public void uploadCsv(){
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("s3obsidian")
                .key(UUID.randomUUID().toString())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(new File("dadosEmissoes.csv")));
    }
}
