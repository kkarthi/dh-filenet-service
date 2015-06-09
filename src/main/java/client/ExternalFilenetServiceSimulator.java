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
		LOGGER.info("Filenet Upload Document");
		final FilenetDocumentRequest filenetDocumentRequest = createFilenetDocumentRequest("Test.txt", "WB",
				"C:\\KARTHI\\test.txt");
		return filenetDocumentService.uploadDocument(filenetDocumentRequest);
	}

	public String updateDocument(final String filenetReferenceId) throws DocumentException, FileNotFoundException {
		final FilenetDocumentRequest filenetDocumentRequest = createFilenetDocumentRequest("Test.txt", "WB",
				"C:\\KARTHI\\testOther.txt");
		filenetDocumentRequest.setFilenetReferenceId(filenetReferenceId);
		return filenetDocumentService.overwriteDocument(filenetDocumentRequest);
	}

	public String downloadDocumentURL(final String filenetReferenceId) throws DocumentException, FileNotFoundException {
		final FilenetDocumentRequest filenetDocumentRequest = createFilenetDocumentRequest("Test.txt", "WB",
				"C:\\KARTHI\\testOther.txt");
		filenetDocumentRequest.setFilenetReferenceId(filenetReferenceId);
		return filenetDocumentService.getDocumentDownloadUrl(filenetDocumentRequest);
	}

	private FilenetDocumentRequest createFilenetDocumentRequest(final String documentName, final String appGroup,
			final String fileLocation) throws FileNotFoundException {
		final FilenetDocumentRequest filenetDocumentRequest = new FilenetDocumentRequest();
		filenetDocumentRequest.setDocumentName(documentName);
		// Document ID, Deal Id DocCategory and Full name need to be send it from the calling application.
		filenetDocumentRequest.setDocumentId("875686689658986589");
		filenetDocumentRequest.setDealId("21324324354354353454");
		filenetDocumentRequest.setUserFullName("Test");
		filenetDocumentRequest.setDocCategory("Transaction Documents");

		filenetDocumentRequest.setAppGroup(appGroup);
		filenetDocumentRequest.getFilenetMetadata().putAll(FilenetUtil.getDocumentProperties(filenetDocumentRequest));

		filenetDocumentRequest.setDocumentStream(new FileInputStream(fileLocation));
		return filenetDocumentRequest;
	}
}