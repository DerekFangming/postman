package com.fmning.postman;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fmning.postman.model.Notification;
import com.fmning.postman.util.Util;

@EnableScheduling
@Component
public class Scheduler {
	
	//@Scheduled(cron = "*/10 * * * * *") //Every 10 seconds, for testing only
	@Scheduled(cron = "0 0 5 * * ?")//Every 5 AM
	public void resetThreshold() {
		Util.gmailThreshold1.set(100);
		Util.gmailThreshold2.set(100);
		Util.gmailWarningSend = false;
		Util.gmailErrorSend = false;
		
		Util.sendGridThreshold1.set(100);
		Util.sendGridThreshold2.set(100);
		Util.sendGridWarningSend = false;
		Util.sendGridErrorSend = false;
		
		Util.mailGunThreshold.set(300);
		Util.mailGunWarningSend = false;
		Util.mailGunErrorSend = false;
	}
	
	@Scheduled(cron = "0 0 */2 * * *")//Every 2 hours
	public void checkForRemaining() {
		int gmailRemaining = Util.gmailThreshold1.intValue() + Util.gmailThreshold2.intValue();
		if (gmailRemaining < 20 && !Util.gmailErrorSend) {
			Util.gmailErrorSend = true;
			Util.notification(new Notification("Fatal warning. Gmail remaining balance is " + gmailRemaining));
		} else if (gmailRemaining < 100 && !Util.gmailWarningSend) {
			Util.gmailWarningSend = true;
			Util.notification(new Notification("Warning. Gmail remaining balance is " + gmailRemaining));
		}
		
		int sendGridRemaining = Util.sendGridThreshold1.intValue() + Util.sendGridThreshold2.intValue();
		if (sendGridRemaining < 20 && !Util.sendGridErrorSend) {
			Util.sendGridErrorSend = true;
			Util.notification(new Notification("Fatal warning. Send Grid remaining balance is " + sendGridRemaining));
		} else if (sendGridRemaining < 100 && !Util.sendGridWarningSend) {
			Util.sendGridWarningSend = true;
			Util.notification(new Notification("Warning. Send Grid remaining balance is " + sendGridRemaining));
		}
		
		if (Util.mailGunThreshold.intValue() < 0 && !Util.mailGunErrorSend) {
			Util.mailGunErrorSend = true;
			Util.notification(new Notification("Fatal warning. Mail Gun remaining balance is " + Util.mailGunThreshold.intValue()));
		} else if (Util.mailGunThreshold.intValue() < 150 && !Util.mailGunWarningSend) {
			Util.mailGunWarningSend = true;
			Util.notification(new Notification("Warning. Mail Gun remaining balance is " + Util.mailGunThreshold.intValue()));
		}
		
	}

}
