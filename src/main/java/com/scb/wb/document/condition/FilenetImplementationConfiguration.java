package com.scb.wb.document.condition;

import static org.springframework.util.Assert.notNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.scb.wb.document.service.FilenetDocumentService;

/**
 * 
 * This is the {@link Configuration} class and it's a decider object. It injects <b>Mock</b> or <b>Real</b> instance
 * based on the {@link Condition}.
 *
 */
@Configuration
public class FilenetImplementationConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilenetImplementationConfiguration.class);

	@Autowired
	@Qualifier("mockFilenetDocumentServiceImpl")
	private FilenetDocumentService mockFilenetDocumentService;

	@Autowired
	@Qualifier("filenetDocumentServiceImpl")
	private FilenetDocumentService filenetDocumentService;

	@Bean(name = "filenetDocumentService")
	@Conditional(FilenetDocumentMockCondition.class)
	public FilenetDocumentService mockFilenetDocumentService() {
		notNull(mockFilenetDocumentService, "mockFilenetDocumentService should not be null");
		LOGGER.info("Called mockFilenetDocumentService ");
		return mockFilenetDocumentService;
	}

	@Bean(name = "filenetDocumentService")
	@Conditional(FilenetDocumentCondition.class)
	public FilenetDocumentService actualFilenetDocumentService() {
		notNull(filenetDocumentService, "filenetDocumentService should not be null");
		LOGGER.info("Called filenetDocumentService ");
		return filenetDocumentService;
	}
}
