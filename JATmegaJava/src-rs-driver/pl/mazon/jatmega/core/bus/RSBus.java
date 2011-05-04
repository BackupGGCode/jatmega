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

import pl.mazon.jatmega.core.bus.config.RSBusConfig;
import pl.mazon.jatmega.logger.LogFactory;
import pl.mazon.jatmega.logger.Logger;

/**
 * Sterownik magistrali RS232
 * @author radomir.mazon
 *
 */

public class RSBus extends BusAdapter implements IBus, SerialPortEventListener {

	private Logger logger = LogFactory.getLog(RSBus.class);
	
	private SerialPort serialPort;
	
	private OutputStream outputStream;
	
	private InputStream inputStream;
	
	private String unfinischedBuffer;
	
	private RSBusConfig config;
	
	private CommDriver commDriver;
	
	public RSBus() {
	}
	
	public RSBus(RSBusConfig config) {
		init(config);
	}
	
	public void init(IBusConfig config) {
		this.config = (RSBusConfig) config;
		unfinischedBuffer = "";
		
		commDriver = loadDriver(this.config.getDriverName());

		if (commDriver != null) {
			logger.info("Driver ok.");
			//connect(); - wykomentowane, ponieważ połączenie następowało przed dodaniem lisynera ecentów.
		} else {
			throw new IllegalStateException("Driver Fatal ERROR...");
		}
		
		/*logger.info("Serial list: ");
		for (String name : getSerialPortNames()) {
			logger.info(name);
		}*/
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
	
	@Override
	protected boolean connectInternal() {
		boolean result = false;
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
             	result = true;
                logger.info("RSBus is online...");
            }
         } catch (IOException e) {
        	 result = false;
         } catch (UnsupportedCommOperationException e) {
        	 logger.error("Unsupported BUS operation.("+portName+")");
        	 result = false;
         } catch (TooManyListenersException e) {
        	 logger.error("Too many listeners to receive.("+portName+")");
        	 result = false;
		}
        return result;
	}
	
	@Override
	protected void disconnectInternal() {
		try {
			serialPort.removeEventListener();
			serialPort.close();
		} catch(Exception e) {
			logger.info("Can not interrupt receiver thread ! FATAL ERROR");
			throw new RuntimeException("Can not interrupt receiver thread ! FATAL ERROR");
		}
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
	            }
	         } catch (IOException e) {}
	   
	         break;
	      }
	   }
	
	@Override
	protected void sendLineInternal(String message) throws IOException {
		outputStream.write(message.getBytes());
		outputStream.write(EOL); 
	}
	
	@Override
	protected void sendInternal(String message) throws IOException {
		outputStream.write(message.getBytes()); 
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}
}