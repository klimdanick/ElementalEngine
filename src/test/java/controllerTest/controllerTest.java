package controllerTest;

import net.java.games.input.*;

public class controllerTest {

	public static void main(String[] args) {
		
		Event event = new Event();

		/* Get the available controllers */
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for (int i = 0; i < controllers.length; i++) {
		    /* Remember to poll each one */
		    controllers[i].poll();

		    /* Get the controllers event queue */
		    EventQueue queue = controllers[i].getEventQueue();

		    /* For each object in the queue */
		    while (queue.getNextEvent(event)) {
		        /* Get event component */
		        Component comp = event.getComponent();

		        System.out.println(comp.getPollData());
		        
		    }
		}
		
	}

}
