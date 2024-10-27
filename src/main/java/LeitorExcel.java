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

    public List<Emissao> extrarLivros(String nomeArquivo, InputStream arquivo) {
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

            List<Emissao> livrosExtraidos = new ArrayList<>();

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

                Emissao livro = new Emissao();
                livro.setGas(row.getCell(2).getStringCellValue());
                livro.setSetorEmissao(row.getCell(3).getStringCellValue());
                livro.setEstado(row.getCell(10).getStringCellValue());
                livro.setDoisMilDoze(row.getCell(54).getNumericCellValue());
                livro.setDoisMilTreze(row.getCell(55).getNumericCellValue());
                livro.setDoisMilQuatorze(row.getCell(56).getNumericCellValue());
                livro.setDoisMilQuinze(row.getCell(57).getNumericCellValue());
                livro.setDoisMilDezesseis(row.getCell(58).getNumericCellValue());
                livro.setDoisMilDezessete(row.getCell(59).getNumericCellValue());
                livro.setDoisMilDezoito(row.getCell(60).getNumericCellValue());
                livro.setDoisMilDezenove(row.getCell(61).getNumericCellValue());
                livro.setDoisMilVinte(row.getCell(62).getNumericCellValue());
                livro.setDoisMilVinteUm(row.getCell(63).getNumericCellValue());
                livro.setDoisMilVinteDois(row.getCell(64).getNumericCellValue());


                livrosExtraidos.add(livro);
            }

            // Fechando o workbook após a leitura
            workbook.close();

            System.out.println("\nLeitura do arquivo finalizada\n");

            return livrosExtraidos;
        } catch (IOException e) {
            // Caso ocorra algum erro durante a leitura do arquivo uma exceção será lançada
            throw new RuntimeException(e);
        }
    }

    private LocalDate converterDate(Date data) {
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
