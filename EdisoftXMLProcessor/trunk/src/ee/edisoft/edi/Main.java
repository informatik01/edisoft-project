package ee.edisoft.edi;

import org.apache.log4j.Logger;

import ee.edisoft.edi.xml.XMLProcessor;

/**
 * Main Class that runs the Application.
 * 
 * @author Levan Kekelidze
 * @version 0.3 Alpha
 */
public class Main {

	private static final Logger logger = Logger.getLogger(Main.class);

	private void addShutdownHook(final XMLProcessor xmlProcessor) {
		final Thread currentThread = Thread.currentThread();
		
		// adding ShutdownHook to properly finish application
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (xmlProcessor.isProcessingEvents()) {
					try {
						xmlProcessor.stopProcessingEvents();
						currentThread.join(5000);	// wait for 5 seconds; change time as needed
					} catch (InterruptedException e) {
						currentThread.interrupt();
					}
					/*
					 * If the thread is still alive, then interrupt.
					 * From the API docs: Interrupting a thread that is not alive
					 * need not have any effect.
					 */
					currentThread.interrupt();
				}
			}
		});
	}

	public static void main(String[] args) {
		logger.info("Starting XML Processor.");
		Main main = new Main();
		XMLProcessor xmlProcessor = new XMLProcessor();
		main.addShutdownHook(xmlProcessor);
		
		xmlProcessor.process();
		logger.info("===== XML Processor is shut down. =====");
	}
}
