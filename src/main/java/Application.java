import com.volvo.impact.test.api.ImpactHost;
import com.volvo.impact.test.api.RestApi;
import com.volvo.impact.test.api.entities.OperationInfoHeader;
import com.volvo.impact.test.api.entities.StandardTimesResult;
import com.volvo.impact.test.api.requestBuilder.Request;
import com.volvo.impact.test.api.requestBuilder.RequestBuilder;
import data.StandardTimesInputData;
import data.StandardTimesOutputData;
import org.apache.http.auth.AuthenticationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Application {

    public static void main(String[] args) throws IOException, AuthenticationException {

        RestApi api = new RestApi(ImpactHost.TEST);
        api.authenticate("a222413", "dummy");

        CsvReaderWriter csvReaderWriter = new CsvReaderWriter();
        List<StandardTimesInputData> dataFromCsv = csvReaderWriter.openCsvFile();
        List<OperationInfoHeader> list = null;
        List<StandardTimesOutputData> outputDataList = new ArrayList<>();


        for (Object object : dataFromCsv) {
            StandardTimesOutputData outputData = new StandardTimesOutputData();

            StandardTimesInputData standardTimes = (StandardTimesInputData) object;
            Request request = RequestBuilder.operationsJson().search().setChassisSeries(standardTimes.getChassisSeries()).setChassisNumber(standardTimes.getChassisNumber());
            StandardTimesResult result = api.requestGet(request, StandardTimesResult.class);
            List<String> operationNo = new ArrayList<>();
            list = result.getHeaders();

            outputData.setChassisSeries(standardTimes.getChassisSeries());
            outputData.setChassisNumber(standardTimes.getChassisNumber());
            for (OperationInfoHeader aList : list) {
                operationNo.add(aList.getOperationNo());
                outputData.setOperationNo(operationNo);
            }
            outputDataList.add(outputData);
        }
        System.out.println(outputDataList);
        csvReaderWriter.writeCsvToBean(outputDataList);
    }
}
