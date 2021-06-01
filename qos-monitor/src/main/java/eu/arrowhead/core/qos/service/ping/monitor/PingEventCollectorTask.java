package eu.arrowhead.core.qos.service.ping.monitor;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import eu.arrowhead.common.dto.shared.EventDTO;
import eu.arrowhead.common.dto.shared.QosMonitorEventType;
import eu.arrowhead.common.dto.shared.monitoringevents.FinishedMonitoringMeasurementEventDTO;
import eu.arrowhead.common.dto.shared.monitoringevents.InterruptedMonitoringMeasurementEventDTO;
import eu.arrowhead.common.dto.shared.monitoringevents.MeasurementMonitoringEvent;
import eu.arrowhead.common.dto.shared.monitoringevents.ReceivedMonitoringRequestEventDTO;
import eu.arrowhead.common.dto.shared.monitoringevents.StartedMonitoringMeasurementEventDTO;
import eu.arrowhead.common.exception.ArrowheadException;
import eu.arrowhead.common.exception.InvalidParameterException;
import eu.arrowhead.core.qos.QosMonitorConstants;
import eu.arrowhead.core.qos.dto.event.EventDTOConverter;

public class PingEventCollectorTask implements Runnable {

	//=================================================================================================
	// members

	private static final String NOT_SUPPORTED_EVENT_TYPE = " is not a supported event type. ";
	private static final String REPLACEING_MEASURMENT_EVENT = " - measurment , duplicate event. Overwriting : ";

	private final long clearingInterval = 1000 * 60 * 10;

	private boolean interrupted = false;
	private long lastBufferCleanAt;

	@Resource(name = QosMonitorConstants.EVENT_QUEUE)
	private LinkedBlockingQueue<EventDTO> eventQueue;

	@Resource(name = QosMonitorConstants.EVENT_BUFFER)
	private ConcurrentHashMap<UUID, PingEventBufferElement> eventBuffer;

	@Autowired
	private PingEventBufferCleaner bufferCleaner;

	private final Logger logger = LogManager.getLogger(PingEventCollectorTask.class);

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------	
	@Override
	public void run() {

		interrupted = Thread.currentThread().isInterrupted();
		clearBuffer();

		while (!interrupted) {
			logger.debug("PingEventCollectorTask run loop started...");
			try {
				putEventToBuffer(eventQueue.take());

				if (lastBufferCleanAt + clearingInterval < System.currentTimeMillis()) {

					lastBufferCleanAt = System.currentTimeMillis();
					clearBuffer();
				}

			} catch (final InterruptedException ex) {

				logger.debug("PingEventCollectorTask run intrrupted");
				interrupted = false;

			}catch (final Exception ex) {

				logger.debug(ex.getMessage());
			}
		}
	}

	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	private void clearBuffer() {
		logger.debug("clearBuffer started...");

		bufferCleaner.clearBuffer();
	}

	//-------------------------------------------------------------------------------------------------
	private void addEventToBufferElement (final PingEventBufferElement element, final int position, final MeasurementMonitoringEvent event) {
		logger.debug("addEventToBufferElement started...");

		if(element.getEventlist()[position] != null) {
			logger.warn(element.getId() + REPLACEING_MEASURMENT_EVENT + element.getEventlist()[position].toString());
		}

		element.addEvent(position, event);

	}
	//-------------------------------------------------------------------------------------------------
	private void putEventToBuffer(final EventDTO event) {
		logger.debug("putEventToBuffer started...");

		final UUID id = UUID.fromString(event.getMetaData().get(QosMonitorConstants.PROCESS_ID_KEY));

		PingEventBufferElement element = eventBuffer.get(id);
		if (element == null) {
			element = new PingEventBufferElement(id);
		}

		try {
			final QosMonitorEventType eventType = QosMonitorEventType.valueOf(event.getEventType());

			switch (eventType) {
			case RECEIVED_MONITORING_REQUEST:
				final ReceivedMonitoringRequestEventDTO validReceivedRequestEvent = EventDTOConverter.convertToReceivedMonitoringRequestEvent(event);

				addEventToBufferElement(element, QosMonitorConstants.RECEIVED_MONITORING_REQUEST_EVENT_POSITION, validReceivedRequestEvent);
				break;
			case STARTED_MONITORING_MEASUREMENT:
				final StartedMonitoringMeasurementEventDTO validStartedEvent = EventDTOConverter.convertToStartedMonitoringMeasurementEvent(event);

				addEventToBufferElement(element, QosMonitorConstants.STARTED_MONITORING_MEASUREMENT_EVENT_POSITION, validStartedEvent);
				break;
			case FINISHED_MONITORING_MEASUREMENT:
				final FinishedMonitoringMeasurementEventDTO validFinishEvent = EventDTOConverter.convertToFinishedMonitoringMeasurementEvent(event);

				addEventToBufferElement(element, QosMonitorConstants.FINISHED_MONITORING_MEASUREMENT_EVENT_POSITION, validFinishEvent);
				break;
			case INTERUPTED_MONITORING_MEASUREMENT:
				final InterruptedMonitoringMeasurementEventDTO validInteruptEvent = EventDTOConverter.convertToInteruptedMonitoringMeasurementEvent(event);

				addEventToBufferElement(element, QosMonitorConstants.INTERRUPTED_MONITORING_MEASUREMENT_EVENT_POSITION, validInteruptEvent);
				break;
			default:
				throw new InvalidParameterException(eventType + NOT_SUPPORTED_EVENT_TYPE);
			}

		} catch (final ArrowheadException ex) {
			logger.debug(ex.getMessage());
		}

		eventBuffer.put(id, element);

	}

}
