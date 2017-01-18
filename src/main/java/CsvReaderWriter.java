import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import data.StandardTimesInputData;
import data.StandardTimesOutputData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvReaderWriter {
    private String csvFilePath = "C:\\repo\\standardTimes_data_search\\standardTimes_dataProcessor\\standatrdTimesData.csv";
    private String outputPath = "C:\\repo\\standardTimes_data_search\\standardTimes_dataProcessor\\";

    private final String[] MAPPING_STRATEGY = {"brand", "model", "chassisSeries", "chassisNumber", "functionGroup"};
    private final String[] MAPPING_STRATEGY_OUTPUT = {"chassisSeries","chassisNumber","operationNo"};

    public List<StandardTimesInputData> openCsvFile() {
        CsvToBean<StandardTimesInputData> csvToBean = new CsvToBean<>();
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(csvFilePath), ';');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return csvToBean.parse(setColumnMappingStrategy(), csvReader);
    }

    private ColumnPositionMappingStrategy<StandardTimesInputData> setColumnMappingStrategy() {
        ColumnPositionMappingStrategy<StandardTimesInputData> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(StandardTimesInputData.class);
        strategy.setColumnMapping(MAPPING_STRATEGY);
        return strategy;
    }
    private ColumnPositionMappingStrategy<StandardTimesOutputData> setColumnMappingStrategyOutput(){
        ColumnPositionMappingStrategy<StandardTimesOutputData> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(StandardTimesOutputData.class);
        strategy.setColumnMapping(MAPPING_STRATEGY_OUTPUT);
        return strategy;
    }

    public void writeCsvToBean(List<StandardTimesOutputData> standardTimesOutput){
        CSVWriter csvWriter = null;
        try{
            csvWriter = new CSVWriter(new FileWriter(outputPath+"operationNo.csv"),';');
            BeanToCsv<StandardTimesOutputData> beanToCsv = new BeanToCsv<>();
            beanToCsv.write(setColumnMappingStrategyOutput(),csvWriter, standardTimesOutput);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (csvWriter != null) {
                    csvWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

