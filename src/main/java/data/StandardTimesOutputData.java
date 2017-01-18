package data;

import lombok.Data;

import java.util.List;

@Data
public class StandardTimesOutputData {

    private String chassisSeries;
    private String chassisNumber;
    private List<String> operationNo;
}
