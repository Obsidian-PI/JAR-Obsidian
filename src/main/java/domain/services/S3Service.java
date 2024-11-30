package domain.services;

import exceptions.BadRequestException;
import exceptions.NotFoundException;
import configs.S3Provider;
import domain.entities.Emissao;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class S3Service {
    private final S3Client s3Client = new S3Provider().getS3Client();
    private final String bucketName = "s3-obsidian";
    private final String csvName = "dadosEmissoes.csv";

    public void baixarArquivo() throws NotFoundException {
        try {
            List<S3Object> objects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build()).contents();

            if (objects == null || objects.isEmpty()) {
                throw new NotFoundException("Nenhum arquivo encontrado no bucket: " + bucketName);
            }

            for (S3Object object : objects) {
                try {
                    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(object.key())
                            .build();

                    InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
                    Files.copy(inputStream, new File(object.key()).toPath());
                    System.out.println("Arquivo baixado: " + object.key());
                } catch (IOException e) {
                    System.err.println("Erro ao baixar o arquivo: " + object.key() + " - " + e.getMessage());
                }
            }

        } catch (S3Exception e) {
            throw new NotFoundException("Erro ao acessar o bucket S3: " + bucketName + e);
        }
    }

    public void logToCsv(List<Emissao> logList) throws IOException, BadRequestException {

        System.out.println("Transformando o log em csv...\n");

        if (logList == null || logList.isEmpty()) {
            throw new BadRequestException("A lista de emissões está nula ou vazia.");
        }

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

    public void uploadCsv(){
        System.out.println("Subindo o csv no bucket: " + bucketName);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(csvName)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(new File(csvName)));
    }
}
