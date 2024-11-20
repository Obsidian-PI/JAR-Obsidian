package domain.services;

import domain.entities.Emissao;

import java.io.IOException;
import java.util.List;

public abstract class Servicos {
    String bucketName = "s3-obsidian";
    String csvName = "dadosEmissoes.csv";

    public abstract void baixarArquivo() throws IOException;

    public abstract void logToCsv(List<Emissao> logList) throws IOException;

    public abstract void uploadCsv();
}
