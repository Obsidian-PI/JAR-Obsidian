package domain.services;

import configs.DBConnectionProvider;
import domain.entities.Emissao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class DbService {
    public List<Emissao> inserirDados(List<Emissao> emissoesExtraidas){
        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();
        JdbcTemplate connection = dbConnectionProvider.getConnection();

        List<Emissao> logList = new ArrayList<>();

        for (Emissao emissaoExtraida : emissoesExtraidas) {
            if (emissaoExtraida.getGas().contains("CO2e")){

                logList.add(emissaoExtraida);

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
        return logList;
    }
}
