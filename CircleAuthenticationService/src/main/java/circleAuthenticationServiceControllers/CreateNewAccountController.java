package circleAuthenticationServiceControllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import org.springframework.web.bind.annotation.RequestMethod;

import circleAuthenticationServiceDataModel.CircleStandardHttpResponse;
import circleAuthenticationServiceServices.AccountService;
import circleAuthenticationServiceServices.EmailNotificationService;;

@RestController

public class CreateNewAccountController {
	
    @Autowired
    @Qualifier("AccountService")
	private AccountService accountService;
    
    @Autowired
    @Qualifier("EmailNotificationService")
	private EmailNotificationService emailNotificationService;
	
    @RequestMapping(value = "/create-new-account",method=RequestMethod.POST)//  http:XXX /create-new-account?username=fafa&password=asfasf
    public @ResponseBody CircleStandardHttpResponse setTest(@RequestParam("username") String username, @RequestParam("password") String password){
    	CircleStandardHttpResponse circleStandardHttpResponse = new CircleStandardHttpResponse();
    	try {
			accountService.createNewAccount(username, password);
			circleStandardHttpResponse.setSucceed(true);
			circleStandardHttpResponse.setInformation("Account has already been created successfully.");
			try {
			emailNotificationService.sendAccountCreatedEmailToClient(username);
			} catch (Exception e) {
				;
			}
		} catch (AmazonServiceException ase) {
			circleStandardHttpResponse.setSucceed(false);
			circleStandardHttpResponse.setInformation("Server has faced an problem, please create an account later.");
        } catch (AmazonClientException ace) {
        	circleStandardHttpResponse.setSucceed(false);
			circleStandardHttpResponse.setInformation("Server has faced an problem, please create an account later.");
        } catch (Exception e){
        	circleStandardHttpResponse.setSucceed(false);
			circleStandardHttpResponse.setInformation(e.getMessage());
        }
		
    	
		return circleStandardHttpResponse;
	}

    /*@RequestMapping(value = "/create-new-account/username={username}",method=RequestMethod.POST)// http:XXX /create-new-account/username=fafa
    public @ResponseBody CreateNewAccountRequest setTest1(@PathVariable("username") String username){
		CreateNewAccountRequest createNewAccountRequest = new CreateNewAccountRequest();
		createNewAccountRequest.setUsername(username);
		return createNewAccountRequest;
	}*/
}