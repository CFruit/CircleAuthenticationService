package circleAuthenticationServiceRepositories;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;

@Repository("DynamoDBRepository")
public class AWSRepository {
	 private AmazonDynamoDBClient dynamoDB;

	 public AWSRepository() throws Exception {

	        AWSCredentials credentials = null;
	        try {
	            credentials = new ProfileCredentialsProvider("default").getCredentials();
	        } catch (Exception e) {
	            throw new AmazonClientException(
	                    "Cannot load the credentials from the credential profiles file. " +
	                    "Please make sure that your credentials file is at the correct " +
	                    e);
	        }
	        //DynamoDB Initiation
	        this.dynamoDB = new AmazonDynamoDBClient(credentials);
	        Region usEast1 = Region.getRegion(Regions.US_EAST_1);
	        this.dynamoDB.setRegion(usEast1);
	        
	    }

	 public void createNewAccount(String username, String password) throws Exception {
	  

	        try {
	            String tableName = "CircleUserAccounts";
	            if (!Tables.doesTableExist(this.dynamoDB, tableName)) {
	               throw new AmazonClientException("CircleUserAccounts doesn't exist.");
	            } 

	            

	            HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
	            Condition condition = new Condition()
	                .withComparisonOperator(ComparisonOperator.EQ.toString())
	                .withAttributeValueList(new AttributeValue(username));
	            scanFilter.put("username", condition);
	            ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
	            ScanResult scanResult = this.dynamoDB.scan(scanRequest);

	            if(!isValidEmailAddress(username)) {
	            	throw new Exception("Invalid Email address.");
	            }
	            if (scanResult.getCount()>=1) {
	            	throw new Exception("Email was used. Please use a new email.");
	            } else if (username.length()>=1 && username.length()<=50 && password.length()>=1 && password.length()<=50 ) {
	            	
                             String accessToken = "Circle-Auth-"+UUID.randomUUID().toString()+UUID.randomUUID().toString();
	                         Map<String, AttributeValue> account = newAccount(username, accessToken, password);
	                         PutItemRequest putItemRequest = new PutItemRequest(tableName, account);
	                         this.dynamoDB.putItem(putItemRequest);            
	            }

	        } catch (AmazonServiceException ase) {
	            throw ase;
	        } catch (AmazonClientException ace) {
	            throw ace;
	        } catch (Exception e){
	        	throw e;
	        }
	    }

	    private static Map<String, AttributeValue> newAccount(String username, String accessToken, String password) {
	        Map<String, AttributeValue> account = new HashMap<String, AttributeValue>();
	        account.put("username", new AttributeValue(username));
	        account.put("accessToken", new AttributeValue(accessToken));
	        account.put("password", new AttributeValue(password));
	        return account;
	    }
	    
	    public boolean isValidEmailAddress(String email) {
	           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	           java.util.regex.Matcher m = p.matcher(email);
	           return m.matches();
	    }

}