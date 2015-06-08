package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scb.wb.document.exception.DocumentException;
import com.scb.wb.document.model.FilenetDocumentRequest;
import com.scb.wb.document.service.FilenetDocumentService;

@Service("externalFilenetServiceSimulator")
public class ExternalFilenetServiceSimulator {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExternalFilenetServiceSimulator.class);

	@Autowired
	private FilenetDocumentService filenetDocumentService;

	public void uploadDocument() throws DocumentException {
		LOGGER.info("Client filenetDocumentService " + filenetDocumentService);
		final FilenetDocumentRequest filenetDocumentRequest = new FilenetDocumentRequest();
		filenetDocumentRequest.getFilenetMetadata().put("Test", "Test");
		filenetDocumentRequest.setDocumentName("Test");
		filenetDocumentRequest.setAppGroup("WB");

		filenetDocumentService.uploadDocument(filenetDocumentRequest);
	}

}