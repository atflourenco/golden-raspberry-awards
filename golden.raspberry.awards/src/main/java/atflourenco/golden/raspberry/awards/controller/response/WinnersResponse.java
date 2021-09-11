package atflourenco.golden.raspberry.awards.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class WinnersResponse {

    private List<AwardProducerResponse> min;
    private List<AwardProducerResponse> max;

}
