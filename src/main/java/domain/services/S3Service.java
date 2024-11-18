package domain.services;

import configs.S3Provider;
import domain.entities.Emissao;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class S3Service extends Servicos {
    S3Client s3Client = new S3Provider().getS3Client();

    @Override
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

    @Override
    public void logToCsv(List<Emissao> logList) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvName))) {

            pw.println("gas; setor emissao; estado; 2012; 2013; 2014; 2015; 2016; 2017; 2018; 2019; 2020; 2021; 2022");

            for (Emissao emissao : logList) {
                pw.println(
                        emissao.getGas() + ";" + emissao.getSetorEmissao() + ";" + emissao.getEstado() + ";"
                        + emissao.getDoisMilDoze() + ";" + emissao.getDoisMilTreze() + ";"
                        + emissao.getDoisMilQuinze() + ";" + emissao.getDoisMilDezesseis() + ";"
                        + emissao.getDoisMilDezessete() + ";" + emissao.getDoisMilDezoito() + ";"
                        + emissao.getDoisMilDezenove() + ";" + emissao.getDoisMilVinte() + ";"
                        + emissao.getDoisMilVinteUm() + ";" + emissao.getDoisMilVinteDois()
                );
            }
        }
    }

    @Override
    public void uploadCsv(){
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(csvName)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(new File(csvName)));
    }
}
