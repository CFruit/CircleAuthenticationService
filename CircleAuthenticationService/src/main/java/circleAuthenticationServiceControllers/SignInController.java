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

import circleAuthenticationServiceDataModel.CircleSignInResponse;
import circleAuthenticationServiceDataModel.CircleStandardHttpResponse;
import circleAuthenticationServiceServices.AccountService;

@RestController
public class SignInController {
	@Autowired
    @Qualifier("AccountService")
	private AccountService accountService;
	
	@RequestMapping(value = "/sign-in",method=RequestMethod.POST)//  http:XXX /create-new-account?username=fafa&password=asfasf
    public @ResponseBody CircleSignInResponse setTest(@RequestParam("username") String username, @RequestParam("password") String password){
		CircleSignInResponse circleSignInResponse = new CircleSignInResponse();
		try {
			String circleAccessToken = accountService.signInVerification(username, password);
			circleSignInResponse.setCircleAccessToken(circleAccessToken);
			circleSignInResponse.setSucceed(true);
			circleSignInResponse.setCode(1);
			return circleSignInResponse;
		} catch (AmazonServiceException ase) {
			circleSignInResponse.setCircleAccessToken("");
			circleSignInResponse.setSucceed(false);
			circleSignInResponse.setCode(2);
			return circleSignInResponse;
		} catch (AmazonClientException ace) {
			circleSignInResponse.setCircleAccessToken("");
			circleSignInResponse.setSucceed(false);
			circleSignInResponse.setCode(3);
			return circleSignInResponse;
		} catch (Exception e) {
			circleSignInResponse.setCircleAccessToken("");
			circleSignInResponse.setSucceed(false);
			circleSignInResponse.setCode(4);
			return circleSignInResponse;
		}
	}
	
}
