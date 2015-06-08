package com.scb.wb.document.condition;

import static org.springframework.util.Assert.notNull;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.scb.wb.document.service.FilenetDocumentService;

/**
 * 
 * This is the {@link Configuration} class and it's a decider object which decides what type of instance need to be
 * injected. It injects <b>Mock</b> or <b>Real</b> instance based on the {@link Condition}.
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

	@Bean
	@ConfigurationProperties
	public FilenetProerties filenetProerties() {
		return new FilenetProerties();
	}

	/**
	 * 
	 * This class used to fetch CS properties from the database.
	 *
	 */
	public static class FilenetProerties {
		private final Map<String, Map<String, String>> filenetProerties = new HashMap<String, Map<String, String>>();

		public Map<String, Map<String, String>> getFilenetProerties() {
			// TODO Fix Me! Database fetch!
			filenetProerties.put("WB", getCSWBProperties());
			filenetProerties.put("WB", getCSCDDProperties());
			return filenetProerties;
		}

		private Map<String, String> getCSWBProperties() {
			final Map<String, String> map = new HashMap<String, String>();
			map.put("document.filenet.csURL", "http://10.128.76.162:9082/ContentServices");
			map.put("document.filenet.consumerKey", "oneWBCSUser");
			map.put("document.filenet.consumerSecret", "oneWBCSPass");
			map.put("document.filenet.bankId", "#1436621#");
			map.put("document.filenet.documentClass", "OneWBWorkBench");
			map.put("document.filenet.objectStore", "OneWB");
			map.put("document.filenet.folderPath", "");
			map.put("document.filenet.location", "HK");
			return map;
		}

		private Map<String, String> getCSCDDProperties() {
			final Map<String, String> map = new HashMap<String, String>();
			map.put("document.filenet.csURL", "http://10.128.55.138:9085/eCDDContentServices");
			map.put("document.filenet.consumerKey", "oneWBCSUser");
			map.put("document.filenet.consumerSecret", "oneWBCSPass");
			map.put("document.filenet.bankId", "#1436621#");
			map.put("document.filenet.documentClass", "OneWBWorkBench");
			map.put("document.filenet.objectStore", "OneWB");
			map.put("document.filenet.folderPath", "");
			map.put("document.filenet.location", "HK");
			return map;
		}
	}
}