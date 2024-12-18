import domain.services.SlackNotifier;
import exceptions.BadRequestException;
import exceptions.NotFoundException;
import domain.entities.Emissao;
import domain.services.DbService;
import domain.services.LeitorExcel;
import domain.services.S3Service;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ObsidianMain {
    public static void main(String[] args) throws IOException, BadRequestException, NotFoundException {

        S3Service s3Service = new S3Service();
        s3Service.baixarArquivo();

        String nomeArquivo = "SEEG.xlsx";

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

        SlackNotifier.sendNotification("A base de dados foi atualizada!");
    }
}

