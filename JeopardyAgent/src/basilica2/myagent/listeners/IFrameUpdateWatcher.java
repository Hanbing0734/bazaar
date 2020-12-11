package basilica2.myagent.listeners;

import edu.cmu.cs.lti.basilica2.core.Event;

import java.util.Map;

import basilica2.agents.listeners.BasilicaPreProcessor;
import basilica2.agents.components.InputCoordinator;
import basilica2.agents.events.LaunchEvent;
import basilica2.agents.events.MessageEvent;
import basilica2.agents.events.PresenceEvent;
import basilica2.agents.events.PromptEvent;
import basilica2.agents.events.TypingEvent;
import basilica2.agents.events.WhiteboardEvent;
import basilica2.agents.events.ReadyEvent;
import basilica2.agents.events.priority.BlacklistSource;
import basilica2.agents.events.priority.PriorityEvent;
import basilica2.agents.events.priority.PriorityEvent.Callback;
import basilica2.agents.events.IFrameEvent;
import basilica2.agents.listeners.PresenceWatcher;
import basilica2.agents.listeners.plan.StepHandler;
import basilica2.agents.listeners.BasilicaAdapter;
import edu.cmu.cs.lti.basilica2.core.Component;
import edu.cmu.cs.lti.basilica2.core.Event;
import edu.cmu.cs.lti.basilica2.core.Agent;
import edu.cmu.cs.lti.basilica2.core.Component;
import basilica2.agents.components.StateMemory;
import basilica2.agents.data.State;
import edu.cmu.cs.lti.project911.utils.log.Logger;
import edu.cmu.cs.lti.project911.utils.time.TimeoutReceiver;
import edu.cmu.cs.lti.project911.utils.time.Timer;

import java.util.Hashtable;
import java.util.Map;
import java.lang.Math; 
import java.io.*;


public class IFrameUpdateWatcher extends BasilicaAdapter
{ 
	private InputCoordinator source;
	private String status = "";
	String filePath; 
	String fileSuffix;
	String[] fileNames; 
	Boolean[] fileCompleted; 
	String roomName; 

	public IFrameUpdateWatcher(Agent a) 
	{
		super(a);
		roomName = a.getRoomName();
		System.err.println("IFrameUpdateWatcher: constructor finished");
	}

	public String getStatus()
	{
		return status;
	}

	@Override
	public void preProcessEvent(InputCoordinator source, Event e)
	{
		System.err.println("IFrameUpdateWatcher: preProcessEvent - enter");
		IFrameEvent.iframeEventType eventType = IFrameEvent.iframeEventType.valueOf("start"); 
		IFrameEvent iframeEvent = new IFrameEvent(source,"wgu_jeopardy",eventType,"","");
		source.pushEvent(iframeEvent);
		System.err.println("IFrameUpdateWatcher: preProcessEvent - exit");
	}
	
	/**
	 * @return the classes of events that this Preprocessor cares about
	 */
	@Override
	public Class[] getPreprocessorEventClasses()
	{
		return new Class[]{MessageEvent.class, ReadyEvent.class, PresenceEvent.class, WhiteboardEvent.class, TypingEvent.class};
	}


	@Override
	public void processEvent(InputCoordinator source, Event event) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Class[] getListenerEventClasses() {
		// TODO Auto-generated method stub
		return null;
	}

}
