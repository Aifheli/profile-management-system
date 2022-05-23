package ayo.profile.management.mock;

import ayo.profile.management.util.JsonPojoConverter;
import ayo.profile.management.exception.ProfileManagementException;
import ayo.profile.management.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@RestController
public class AyoMtnMockServerEndpoint {

    private AyoMtnMockServerService ayoMtnMockServerService;

    public AyoMtnMockServerEndpoint(AyoMtnMockServerService ayoMtnMockServerService) {
        this.ayoMtnMockServerService = ayoMtnMockServerService;
    }

    @ExecutionTime
    @RequestMapping(value = "/ayo/mtn/profile-service/v1", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<RegisterTransactionResponse> customerRegistration(@RequestBody RegisterTransactionRequest body, @RequestHeader(value="X-Correlation-ID", required=true) String xCorrelationID){
        ResponseEntity<RegisterTransactionResponse> responseEntity = null;
        try {
            ApplicationLoggerUtil.setxCorrelationID(xCorrelationID);
            ApplicationLoggerUtil.info(AyoMtnMockServerEndpoint.class,  EventLogUtil.LOGGER_EVENT.APPLICATION2.toString(), JsonPojoConverter.convertObjectToJson(body),
                    EventLogUtil.LOGGER_EVENT.REGISTER_EVENT.toString());

            if(body != null){
                ayoMtnMockServerService.performDbUpdate(body);
                responseEntity = Helpers.buildRegisterTransactionResponse(Integer.parseInt(ApplicationMessagesUtil.MSG.SUCCESS.getCode()),
                        ApplicationMessagesUtil.MSG.SUCCESS.getMessage(), "", HttpStatus.OK);
            }else {
                responseEntity = Helpers.buildRegisterTransactionResponse(Integer.parseInt(ApplicationMessagesUtil.MSG.EMPTY_REQUEST.getCode()),
                        ApplicationMessagesUtil.MSG.EMPTY_REQUEST.getMessage(), JsonPojoConverter.convertObjectToJson(body), HttpStatus.OK);
            }
        }catch (Exception e){
            throw new ProfileManagementException("Error: " + EventLogUtil.LOGGER_EVENT.REGISTER_EVENT.toString(), e);
        }
        return responseEntity;
    }


}
