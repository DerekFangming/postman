package com.fmning.postman.controller;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmning.postman.util.Util;

@RestController
public class HealthController {
	
	@GetMapping("/")
	public String getServerHealth() throws IOException, XmlPullParserException {
		String projVersion = getClass().getPackage().getImplementationVersion();
		if (projVersion == null) {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(new FileReader("pom.xml"));
			projVersion = model.getVersion();
		}
	
		String healthInfo = "Postman Service version " + projVersion + ". Server time: "
			+ new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	
		healthInfo += "<br><br>";
		healthInfo += "Send Grid remaining: " + (Util.sendGridThreshold1.intValue() + Util.sendGridThreshold2.intValue()) + " / 200";
		healthInfo += "<br>Gmail remaining: " + (Util.gmailThreshold1.intValue() + Util.gmailThreshold2.intValue()) + " / 200";
		healthInfo += "<br>Send In Blue remaining: " + Util.sendInBlueThreshold.intValue()  + " / 300";
		healthInfo += "<br>Mail Gun remaining: " + Util.mailGunThreshold.intValue()  + " / 300";
	
		return healthInfo;
	}

}
