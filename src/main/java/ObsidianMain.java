import domain.entities.Emissao;
import domain.services.DbService;
import domain.services.LeitorExcel;
import domain.services.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ObsidianMain {
    private static final Logger log = LoggerFactory.getLogger(ObsidianMain.class);

    public static void main(String[] args) throws IOException {

        S3Service s3Service = new S3Service();
        s3Service.baixarArquivo();

        String nomeArquivo = "SEEG1.xlsx";

        Path caminho = Path.of(nomeArquivo);
        InputStream arquivo = Files.newInputStream(caminho);

        LeitorExcel leitorExcel = new LeitorExcel();
        List<Emissao> emissoesExtraidas = leitorExcel.extrairEmissoes(nomeArquivo, arquivo);

        arquivo.close();

        System.out.println("Dados extraídos:");
        for (Emissao emissao : emissoesExtraidas) {
            System.out.println(emissao);
        }

        DbService dbService = new DbService();
        s3Service.logToCsv(dbService.inserirDados(emissoesExtraidas));

        s3Service.uploadCsv();
    }
}

