package basilica2.myagent.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import basilica2.agents.components.InputCoordinator;
import basilica2.agents.events.MessageEvent;
import basilica2.agents.events.IFrameEvent;
import basilica2.agents.events.priority.BlacklistSource;
import basilica2.agents.events.priority.PriorityEvent;
import basilica2.agents.events.priority.PriorityEvent.Callback;
import basilica2.agents.listeners.BasilicaAdapter;
import edu.cmu.cs.lti.basilica2.core.Agent;
import edu.cmu.cs.lti.basilica2.core.Event;
import edu.cmu.cs.lti.project911.utils.log.Logger;

public class IFrameActor extends BasilicaAdapter
{
	private static final String SOURCE_NAME = "IFrameActor";
	InputCoordinator source;
	private String status = "";
	
	public IFrameActor(Agent a)
	{
		super(a, SOURCE_NAME);
		log(Logger.LOG_WARNING, "IFrameActor created");
		System.err.println("IFrameActor created"); 
	}

	@Override
	public void processEvent(InputCoordinator source, Event event)
	{
		System.err.println("IFrameActor: entering processEvent"); 
		this.source = source;
		if(event instanceof IFrameEvent)
		{
			System.err.println("IFrameActor: IFrameEvent received"); 
			iframeEventResponse(source, (IFrameEvent)event);
		}
		
	}
	
	private void iframeEventResponse(InputCoordinator source, IFrameEvent iframeEvent)
	{
		System.err.println("IFrameActor: iframeEventResponse -- enter"); 
		
		// MessageEvent newMe = new MessageEvent(source, this.getAgent().getUsername(), prompt);
		// newMe.setDestinationUser(identity1 + withinModeDelim + identity2);
		PriorityEvent blackout = PriorityEvent.makeBlackoutEvent(SOURCE_NAME, iframeEvent, 1.0, 5, 5);
		blackout.addCallback(new Callback()
		{
			@Override
			public void accepted(PriorityEvent p) {}
			@Override
			public void rejected(PriorityEvent p) {}  // ignore our rejected proposals
		});
		source.pushProposal(blackout);
		System.err.println("IFrameActor: iframeEventResponse -- exit"); 
	}

	@Override
	public Class[] getListenerEventClasses()
	{
		return new Class[]{IFrameEvent.class};
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
