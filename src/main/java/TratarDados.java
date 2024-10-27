import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TratarDados {
    public static void main(String[] args) throws IOException {
        String nomeArquivo = "SEEG1.xlsx";

        // Carregando o arquivo excel
        Path caminho = Path.of(nomeArquivo);
        InputStream arquivo = Files.newInputStream(caminho);

        // Extraindo os livros do arquivo
        LeitorExcel leitorExcel = new LeitorExcel();
        List<Emissao> livrosExtraidos = leitorExcel.extrarLivros(nomeArquivo, arquivo);

        // Fechando o arquivo após a extração
        arquivo.close();

        System.out.println("Dados extraídos:");
        for (Emissao livro : livrosExtraidos) {
            System.out.println(livro);
        }
    }
}

