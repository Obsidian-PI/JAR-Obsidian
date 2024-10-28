import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LeitorExcel {

    public List<Emissao> extrairEmissoes(String nomeArquivo, InputStream arquivo) {
        try {
            System.out.println("\nIniciando leitura do arquivo %s\n".formatted(nomeArquivo));

            // Criando um objeto Workbook a partir do arquivo recebido
            Workbook workbook;
            if (nomeArquivo.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(arquivo);
            } else {
                workbook = new HSSFWorkbook(arquivo);
            }

            Sheet sheet = workbook.getSheetAt(0);

            List<Emissao> emissoesExtraidas = new ArrayList<>();

            // Iterando sobre as linhas da planilha
            for (Row row : sheet) {

                if (row.getRowNum() == 0) {
                    System.out.println("\nLendo cabeçalho");

                    for (int i = 0; i < 4; i++) {
                        String coluna = row.getCell(i).getStringCellValue();
                        System.out.println("Coluna " + i + ": " + coluna);
                    }

                    System.out.println("--------------------");
                    continue;
                }

                // Extraindo valor das células e criando objeto Livro
                System.out.println("Lendo linha " + row.getRowNum());

                Emissao dados = new Emissao();
                dados.setGas(row.getCell(2).getStringCellValue());
                dados.setSetorEmissao(row.getCell(3).getStringCellValue());
                dados.setEstado(row.getCell(10).getStringCellValue());
                dados.setDoisMilDoze(row.getCell(54).getNumericCellValue());
                dados.setDoisMilTreze(row.getCell(55).getNumericCellValue());
                dados.setDoisMilQuatorze(row.getCell(56).getNumericCellValue());
                dados.setDoisMilQuinze(row.getCell(57).getNumericCellValue());
                dados.setDoisMilDezesseis(row.getCell(58).getNumericCellValue());
                dados.setDoisMilDezessete(row.getCell(59).getNumericCellValue());
                dados.setDoisMilDezoito(row.getCell(60).getNumericCellValue());
                dados.setDoisMilDezenove(row.getCell(61).getNumericCellValue());
                dados.setDoisMilVinte(row.getCell(62).getNumericCellValue());
                dados.setDoisMilVinteUm(row.getCell(63).getNumericCellValue());
                dados.setDoisMilVinteDois(row.getCell(64).getNumericCellValue());

                emissoesExtraidas.add(dados);
            }

            // Fechando o workbook após a leitura
            workbook.close();

            System.out.println("\nLeitura do arquivo finalizada\n");

            return emissoesExtraidas;
        } catch (IOException e) {
            // Caso ocorra algum erro durante a leitura do arquivo uma exceção será lançada
            throw new RuntimeException(e);
        }
    }
}
