package circleAuthenticationServiceServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import circleAuthenticationServiceDataModel.CircleStandardHttpResponse;
import circleAuthenticationServiceRepositories.AWSRepository;

@Service("AccountService")
public class AccountService {
    
	@Autowired
	@Qualifier("DynamoDBRepository")
	AWSRepository awsRepository;
	
	public AccountService(){
		//do initiation here;
	}
	
	public void createNewAccount(String username, String password) throws Exception {

		try {
			this.awsRepository.createNewAccount(username, password);
		} catch (AmazonServiceException ase) {
            throw ase;
        } catch (AmazonClientException ace) {
            throw ace;
        } catch (Exception e){
        	throw e;
        }
		
	    return;
	}
	
	public String signInVerification(String username, String password) throws Exception {
		
		return this.awsRepository.signInVerification(username, password);
	}
	
	public String verifyAccessToken(String accessToken) throws Exception {
		
		return this.awsRepository.verifyAccessToken(accessToken);
	}
	
}
