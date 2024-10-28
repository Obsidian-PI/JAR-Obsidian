import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TratarDados {
    public static void main(String[] args) throws IOException {
        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();
        JdbcTemplate connection = dbConnectionProvider.getConnection();

        String nomeArquivo = "SEEG1.xlsx";

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

