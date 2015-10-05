package circleAuthenticationServiceControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import circleAuthenticationServiceDataModel.CircleAccessTokenVerificationResponse;
import circleAuthenticationServiceDataModel.CircleStandardHttpResponse;
import circleAuthenticationServiceServices.AccountService;

@RestController
public class AccessTokenVerificationController {
	   @Autowired
	   @Qualifier("AccountService")
	   private AccountService accountService;
	   
	   @RequestMapping(value = "/accessToken-Verification",method=RequestMethod.POST)//  http:XXX /create-new-account?username=fafa&password=asfasf
	   public @ResponseBody CircleAccessTokenVerificationResponse setTest(@RequestParam("accessToken") String accessToken){
		 CircleAccessTokenVerificationResponse circleAccessTokenVerificationResponse = new CircleAccessTokenVerificationResponse();
	
		 try {
			circleAccessTokenVerificationResponse.setUsername(accountService.verifyAccessToken(accessToken));
			circleAccessTokenVerificationResponse.setCode(1);
			circleAccessTokenVerificationResponse.setSucceed(true);
		} catch (AmazonServiceException ase) {
			circleAccessTokenVerificationResponse.setCode(2);
			circleAccessTokenVerificationResponse.setSucceed(false);
		} catch (AmazonClientException ace) {
			circleAccessTokenVerificationResponse.setCode(3);
			circleAccessTokenVerificationResponse.setSucceed(false);
		} catch (Exception e) {
			circleAccessTokenVerificationResponse.setCode(4);
			circleAccessTokenVerificationResponse.setSucceed(false);
		}
		   
	     return circleAccessTokenVerificationResponse;
	   }
	   
}
