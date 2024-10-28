import org.springframework.jdbc.core.JdbcTemplate;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ObsidianS3 {
    public static void main(String[] args) throws IOException {

        S3Client s3Client = new S3Provider().getS3Client();
        String bucketName = "s3obsidian";

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

        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();
        JdbcTemplate connection = dbConnectionProvider.getConnection();

        String nomeArquivo = "SEEG.xlsx";

        // Carregando o arquivo excel
        Path caminho = Path.of(nomeArquivo);
        InputStream arquivo = Files.newInputStream(caminho);

        // Extraindo os livros do arquivo
        LeitorExcel leitorExcel = new LeitorExcel();
        List<Emissao> emissoesExtraidas = leitorExcel.extrairEmissoes(nomeArquivo, arquivo);
        for (Emissao emissaoExtraida : emissoesExtraidas) {
            if (emissaoExtraida.getGas().contains("CO2e")){
                connection.update("INSERT INTO carbonFootprint (gas, setorEmissao, estado, doisMilDoze, doisMilTreze, doisMilQuatorze, doisMilQuinze, " +
                                "doisMilDezesseis, doisMilDezessete, doisMilDezoito, doisMilDezenove, doisMilVinte, doisMilVinteUm, doisMilVinteDois) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        emissaoExtraida.getGas(), emissaoExtraida.getSetorEmissao(), emissaoExtraida.getEstado(),
                        emissaoExtraida.getDoisMilDoze(), emissaoExtraida.getDoisMilTreze(), emissaoExtraida.getDoisMilQuatorze(),
                        emissaoExtraida.getDoisMilQuinze(), emissaoExtraida.getDoisMilDezesseis(), emissaoExtraida.getDoisMilDezessete(),
                        emissaoExtraida.getDoisMilDezoito(), emissaoExtraida.getDoisMilDezenove(), emissaoExtraida.getDoisMilVinte(),
                        emissaoExtraida.getDoisMilVinteUm(), emissaoExtraida.getDoisMilVinteDois());
            }
        }

        // Fechando o arquivo após a extração
        arquivo.close();

        System.out.println("Dados extraídos:");
        for (Emissao emissao : emissoesExtraidas) {
            System.out.println(emissao);
        }
    }
}

