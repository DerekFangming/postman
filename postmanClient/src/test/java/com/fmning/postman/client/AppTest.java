package com.fmning.postman.client;

import java.io.IOException;

import com.fmning.postman.client.model.Email.EmailContentType;
import com.fmning.postman.client.model.Email.EmailSenderType;
import com.fmning.postman.client.model.PostmanResponse;
import com.fmning.postman.client.service.EmailService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
	
    public AppTest( String testName ) {
        super( testName );
    }
    
    public static Test suite() {
        return new TestSuite( AppTest.class );
    }

    public void testApp() throws IOException
    {
    	EmailService service = new EmailService();
    	
    	PostmanResponse response = service.sendEmail("admin@fmning.com", "synfm@126.com", "test email from client111111111", "some conasd astent! <h1> haha</h1>",
    			EmailContentType.HTML, EmailSenderType.SEND_GRID);
    	System.out.println(response.getError());
    }
}
