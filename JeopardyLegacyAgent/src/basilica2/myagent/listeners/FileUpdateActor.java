package basilica2.myagent.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*; 
import java.net.*; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset; 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import basilica2.agents.components.InputCoordinator;
import basilica2.agents.events.MessageEvent;
import basilica2.agents.events.FileEvent;
import basilica2.agents.events.priority.BlacklistSource;
import basilica2.agents.events.priority.PriorityEvent;
import basilica2.agents.events.priority.PriorityEvent.Callback;
import basilica2.agents.listeners.BasilicaAdapter;
import edu.cmu.cs.lti.basilica2.core.Agent;
import edu.cmu.cs.lti.basilica2.core.Event;
import edu.cmu.cs.lti.project911.utils.log.Logger;

public class FileUpdateActor extends BasilicaAdapter
{
	private static final String SOURCE_NAME = "FileUpdateActor";
	InputCoordinator source;
	private String status = "";
	String filePathIn; 
	String filePathOut; 
	String fileNameIn;
	String fileNameOut; 
	String fileSuffix;
	String pathIn; 
	String pathOut; 
	String fileContent; 
	Charset charSet = StandardCharsets.UTF_8;
	
	public FileUpdateActor(Agent a)
	{
		super(a, SOURCE_NAME);	
		System.err.println("FileUpdateActor: constructor started"); 
		if (properties != null)
		{
			try{filePathIn = getProperties().getProperty("filePathIn", "./html/");}
			catch(Exception e) {e.printStackTrace();}
			try{fileNameIn = getProperties().getProperty("fileNameIn", "jeopardy-init");}
			catch(Exception e) {e.printStackTrace();}	
			try{filePathOut = getProperties().getProperty("filePathOut", "./html/");}
			catch(Exception e) {e.printStackTrace();}
			try{fileNameOut = getProperties().getProperty("fileNameOut", "jeopardy-out");}
			catch(Exception e) {e.printStackTrace();}	
			try{fileSuffix = getProperties().getProperty("fileSuffix", ".html");}
			catch(Exception e) {e.printStackTrace();}		
		}
		pathIn = filePathIn + fileNameIn + fileSuffix; 
		Path pathOut = Paths.get(filePathOut + fileNameOut + fileSuffix); 
		try{fileContent = readFile (pathIn, charSet); }
		catch(IOException e) {e.printStackTrace();}
		
		try{Files.write(pathOut,fileContent.getBytes(charSet)); }
		catch(IOException e) {e.printStackTrace();}
		
		// URL url; 
		// try{URL url = new URL("http://bazaar.lti.cs.cmu.edu/jeopardy-out"); }
		// catch(Exception e) {e.printStackTrace();}
		// URLConnection connection = url.openConnection(); 
		// PrintStream outStream = new PrintStream(connection.getOutputStream()); 
		

		try {
		// URL url = new URL("http://misty.lti.cs.cmu.edu/bazaar/jeopardyout");
		URL url = new URL("http://bazaar.lti.cs.cmu.edu/jeopardyout");
		// URL url = new URL("https://jeopardylabs.com/play/wgu-welcome-jeopardy-3");
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true); 

        PrintStream outStream = new PrintStream(connection.getOutputStream());       
        // outStream.print(fileContent);    
        // outStream.print("test");     
        outStream.write(fileContent.getBytes(charSet));    


	    } catch (MalformedURLException me) {
	        System.err.println("MalformedURLException: " + me);
	    } catch (IOException ioe) {
	        System.err.println("IOException: " + ioe);
	    } catch (Exception e) {
	        System.err.println("Exception: " + e);
	    }
		finally {
			System.err.println("FileUpdateActor: write to URL completed");
		}
		
		System.err.println("FileUpdateActor: constructor completed"); 
	}
	
	static String readFile(String path, Charset encoding)
			  throws IOException
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}

	@Override
	public void processEvent(InputCoordinator source, Event event)
	{
		System.err.println("FileUpdateActor: entering processEvent"); 
		this.source = source;
		if(event instanceof FileEvent)
		{
			System.err.println("FileUpdateActor: FileEvent received"); 
			fileEventResponse(source, (FileEvent)event);
		}
		
	}
	
	private void fileEventResponse(InputCoordinator source, FileEvent fileEvent)
	{
		String fileName = fileEvent.getFileName();		
		switch(fileName)
		{
		case "test-a":
			System.err.println("FileUpdateActor: test-a file received"); 
			respondTestA(source,fileEvent);
			break; 	
		case "test-b":
			System.err.println("FileUpdateActor: test-b file received"); 
			respondTestB(source,fileEvent);
			break; 	
		case "test-c":
			System.err.println("FileUpdateActor: test-c file received"); 
			respondTestC(source,fileEvent);
			break; 	
		case "test-d":
			System.err.println("FileUpdateActor: test-d file received"); 
			respondTestD(source,fileEvent);
			break; 	
		default:
			System.err.println("No response available for filename " + fileName);
		}		
	}
	
	private void respondTestA(InputCoordinator source, FileEvent event) {
		System.err.println("FileUpdateActor: entering respondTestA()"); 
		String prompt = "Navigator::You've passed a test case for the number of lines your program will output. Do you need to know the number of output lines?"; 
		
		MessageEvent newMe = new MessageEvent(source, this.getAgent().getUsername(), prompt);
		// newMe.setDestinationUser(identity1 + withinModeDelim + identity2);
		PriorityEvent blackout = PriorityEvent.makeBlackoutEvent(SOURCE_NAME, newMe, 1.0, 5, 5);
		blackout.addCallback(new Callback()
		{
			@Override
			public void accepted(PriorityEvent p) {}
			@Override
			public void rejected(PriorityEvent p) {}  // ignore our rejected proposals
		});
		source.pushProposal(blackout);
	}
	
	private void respondTestB(InputCoordinator source, FileEvent event) {
		System.err.println("FileUpdateActor: entering respondTestB()"); 
		String prompt = "It's time to switch the Driver and Navigator roles.|Navigator::Why don't you be the Driver, Joe?|Driver::Eric, you'll be the Navigator."; 
		
		MessageEvent newMe = new MessageEvent(source, this.getAgent().getUsername(), prompt);
		// newMe.setDestinationUser(identity1 + withinModeDelim + identity2);
		PriorityEvent blackout = PriorityEvent.makeBlackoutEvent(SOURCE_NAME, newMe, 1.0, 5, 5);
		blackout.addCallback(new Callback()
		{
			@Override
			public void accepted(PriorityEvent p) {}
			@Override
			public void rejected(PriorityEvent p) {}  // ignore our rejected proposals
		});
		source.pushProposal(blackout);
	}
	
	private void respondTestC(InputCoordinator source, FileEvent event) {
		System.err.println("FileUpdateActor: entering respondTestC()"); 
		String prompt = "It's time to switch the Driver and Navigator roles.|Navigator::Why don't you be the Driver, Joe?|Driver::Eric, you'll be the Navigator.";  
		
		MessageEvent newMe = new MessageEvent(source, this.getAgent().getUsername(), prompt);
		// newMe.setDestinationUser(identity1 + withinModeDelim + identity2);
		PriorityEvent blackout = PriorityEvent.makeBlackoutEvent(SOURCE_NAME, newMe, 1.0, 5, 5);
		blackout.addCallback(new Callback()
		{
			@Override
			public void accepted(PriorityEvent p) {}
			@Override
			public void rejected(PriorityEvent p) {}  // ignore our rejected proposals
		});
		source.pushProposal(blackout);
	}
	
	private void respondTestD(InputCoordinator source, FileEvent event) {
		System.err.println("FileUpdateActor: entering respondTestD()"); 
		String prompt = "It's time to switch the Driver and Navigator roles.|Navigator::Why don't you be the Driver, Joe?|Driver::Eric, you'll be the Navigator."; 
		
		MessageEvent newMe = new MessageEvent(source, this.getAgent().getUsername(), prompt);
		// newMe.setDestinationUser(identity1 + withinModeDelim + identity2);
		PriorityEvent blackout = PriorityEvent.makeBlackoutEvent(SOURCE_NAME, newMe, 1.0, 5, 5);
		blackout.addCallback(new Callback()
		{
			@Override
			public void accepted(PriorityEvent p) {}
			@Override
			public void rejected(PriorityEvent p) {}  // ignore our rejected proposals
		});
		source.pushProposal(blackout);
	}

	@Override
	public Class[] getListenerEventClasses()
	{
		return new Class[]{FileEvent.class};
	}
	
	@Override
	public void preProcessEvent(InputCoordinator source, Event event) {}

	
	@Override
	public Class[] getPreprocessorEventClasses()
	{ 
		return null;
	}
	
	public String getStatus()
	{
		return status;
	}
}
