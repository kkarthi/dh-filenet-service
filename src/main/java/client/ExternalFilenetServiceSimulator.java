package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scb.wb.document.exception.DocumentException;
import com.scb.wb.document.model.FilenetDocumentRequest;
import com.scb.wb.document.service.FilenetDocumentService;
import com.scb.wb.document.service.util.FilenetUtil;

@Service("externalFilenetServiceSimulator")
public class ExternalFilenetServiceSimulator {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExternalFilenetServiceSimulator.class);

	@Autowired
	private FilenetDocumentService filenetDocumentService;

	public String uploadDocument() throws DocumentException, FileNotFoundException {
		LOGGER.info("Client filenetDocumentService " + filenetDocumentService);
		final FilenetDocumentRequest filenetDocumentRequest = new FilenetDocumentRequest();
		filenetDocumentRequest.setDocumentName("Test.txt");
		filenetDocumentRequest.setAppGroup("WB");
		filenetDocumentRequest.getFilenetMetadata().putAll(FilenetUtil.getDocumentProperties(filenetDocumentRequest));

		filenetDocumentRequest.setDocumentStream(new FileInputStream("C:\\KARTHI\\test.txt"));
		return filenetDocumentService.uploadDocument(filenetDocumentRequest);
	}

}