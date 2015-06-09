package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
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
		filenetDocumentRequest.setDocumentId("875686689658986589");
		filenetDocumentRequest.setAppGroup(appGroup);
		filenetDocumentRequest.getFilenetMetadata().putAll(getWBFilenetMetadata(filenetDocumentRequest));

		filenetDocumentRequest.setDocumentStream(new FileInputStream(fileLocation));
		return filenetDocumentRequest;
	}

	public static HashMap<String, String> getWBFilenetMetadata(final FilenetDocumentRequest filenetDocumentRequest) {
		final HashMap<String, String> propertiesValues = new HashMap<String, String>();
		final String dealId = "21324324354354353454";

		propertiesValues.put("DocumentTitle", filenetDocumentRequest.getDocumentName());
		propertiesValues.put("DocumentName", filenetDocumentRequest.getDocumentName());
		propertiesValues.put("DocumentType", FilenameUtils.getExtension(filenetDocumentRequest.getDocumentName()));
		propertiesValues.put("Country", filenetDocumentRequest.getDocumentLocation());
		propertiesValues.put("DocCategory", "Transaction Documents");
		propertiesValues.put("DocID", filenetDocumentRequest.getDocumentId());
		propertiesValues.put("ProductoftheDeal", dealId);
		propertiesValues.put("UserFullName", "Test");
		propertiesValues.put("DateTimeofUpload", System.currentTimeMillis() + "");
		propertiesValues.put("DealID", dealId);
		propertiesValues.put("ClientIDorProspectID", dealId);
		propertiesValues.put("SubProductoftheDeal", dealId);
		return propertiesValues;
	}

}