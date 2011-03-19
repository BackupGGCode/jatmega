package pl.mazon.jatmega.core.bus;

import gnu.io.CommDriver;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Sterownik magistrali RS232
 * @author radomir.mazon
 *
 */

public class RSBus extends BusAdapter implements IBus, SerialPortEventListener {

	private Log logger = LogFactory.getLog(RSBus.class);
	
	private SerialPort serialPort;
	
	private OutputStream outputStream;
	
	private InputStream inputStream;
	
	private String unfinischedBuffer;
	
	private RSBusConfig config;
	
	private boolean initResult;
	
	private CommDriver commDriver;
	
	public RSBus(RSBusConfig config) {
		this.config = config;
		unfinischedBuffer = "";
		
		commDriver = loadDriver(config.getDriverName());

		if (commDriver != null) {
			logger.info("Driver ok.");
			connect();
		} else {
			throw new IllegalStateException("Driver Fatal ERROR...");
		}
		
		logger.info("Serial list: ");
		for (String name : getSerialPortNames()) {
			logger.info(name);
		}
	}
	
	private List<String> getSerialPortNames()
	{
		CommPortIdentifier portId;
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		Vector listData = new Vector(8);

		// Walk through the list of port identifiers and, if it
		// is a serial port, add its name to the list.
		while (en.hasMoreElements())
		{
			portId = (CommPortIdentifier) en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				listData.addElement(portId.getName());
			}
		}

		return listData;
	}

	
	private CommDriver loadDriver(String driverName) {
		CommDriver result = null;
		try {
			logger.info("Driver name : " + driverName);
			result = (CommDriver) Class.forName(driverName).newInstance();
		} catch (Exception e) {
			logger.error(e.getMessage());
			onDriverLoadFailureEvent();
		} catch (Error ee) {
			logger.info("Serial driver load failure.");
			onDriverLoadFailureEvent();
		}
		return result;
	}
	
	private void connect() 
	{
		initResult = false;
		commDriver.initialize();
		String portName = config.getName();
				try {
                    serialPort = (SerialPort) commDriver.getCommPort(portName, CommPortIdentifier.PORT_SERIAL);
                    if (serialPort != null) {
	                 	outputStream = serialPort.getOutputStream();
	                 	inputStream = serialPort.getInputStream();
	                 	serialPort.addEventListener(this);
	                 	serialPort.notifyOnDataAvailable(true);
	                 	serialPort.notifyOnBreakInterrupt(true);
	                 	serialPort.notifyOnCTS(true);
	                 	serialPort.notifyOnOverrunError(true);
	                 	serialPort.setSerialPortParams(
	                    		 config.getBaudRate(),
	                    		 config.getDataBits(),
	                    		 config.getStopBits(),
	                         	 config.getPariti());
	                 	initResult = true;
	                    logger.info("RSBus is online...");
                    }
                 } catch (IOException e) {
                	 initResult = false;
                 } catch (UnsupportedCommOperationException e) {
                	 logger.error("Unsupported BUS operation.("+portName+")");
                	 initResult = false;
                 } catch (TooManyListenersException e) {
                	 logger.error("Too many listeners to receive.("+portName+")");
                	 initResult = false;
				}
             
		if (initResult) {
			logger.debug("Starting RS-BUS OK.("+portName+")");
			onConnectEvent();
		} else {
			logger.debug("RS-BUS is offline. ("+portName+")");
			onDisconnectEvent();
		}
	}
	
	private void disconnect() {
		initResult = false;
		try {
			serialPort.removeEventListener();
			serialPort.close();
		} catch(Exception e) {
			logger.info("Can not interrupt receiver thread ! FATAL ERROR");
			throw new RuntimeException("Can not interrupt receiver thread ! FATAL ERROR");
		}
		/*ThreadGroup rootGroup = Thread.currentThread( ).getThreadGroup( );
		Thread[] list = new Thread[rootGroup.activeCount()];
		rootGroup.enumerate(list);
		for (Thread t : list) {
			if (t.getName().contains("Win32SerialPort")) {
				t.interrupt();
			}
		}
		*/
		onDisconnectEvent();
	}

	synchronized public void serialEvent(SerialPortEvent event) {
	      switch (event.getEventType()) {
	      case SerialPortEvent.BI:
	    	  break;
	      case SerialPortEvent.OE:
	    	  break;
	      case SerialPortEvent.FE:
	    	  break;
	      case SerialPortEvent.PE:
	    	  break;
	      case SerialPortEvent.CD:
	    	  break;
	      case SerialPortEvent.CTS:
	    	  break;
	      case SerialPortEvent.DSR:
	    	  break;
	      case SerialPortEvent.RI:
	    	  break;
	      case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
	         break;
	      case SerialPortEvent.DATA_AVAILABLE:
	         // we get here if data has been received
	         byte[] readBuffer = new byte[1];
	         try {
	        	
	            // read data
	            while (inputStream.available() > 0) {
	               inputStream.read(readBuffer);
	               
	               if (readBuffer[0] == EOL && unfinischedBuffer != "") {
	            	   onReceive(unfinischedBuffer);
	            	   unfinischedBuffer = "";
	               } else {
	            	   unfinischedBuffer += new String(readBuffer);
	               }
	               
	               logger.info("Receive: " + new String(readBuffer));
	            }
	         } catch (IOException e) {}
	   
	         break;
	      }
	   } 

	@Override
	public void send(String message) {
		 try {
			 outputStream.write(message.getBytes());
       } catch (IOException e) {
    	   logger.info("RSBus: Can't write to port... Disconnect...");
    	   disconnect();
       } 
	}
	
	@Override
	public void sendLine(String message) {
		 try {
			 outputStream.write(message.getBytes());
			 outputStream.write(EOL);
       } catch (IOException e) {
    	   logger.info("RSBus: Can't write to port... Disconnect...");
    	   disconnect();
       } 
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	@Override
	public boolean isOnLine() {
		if (initResult == false) {
			connect();
		}
		return initResult;
	}
}