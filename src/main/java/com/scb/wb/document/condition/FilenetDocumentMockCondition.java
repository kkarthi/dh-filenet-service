package com.scb.wb.document.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 
 * This class used for Conditional injection, it checks whether the file-net mock is enabled or not. It Injects the Mock
 * File-net service.
 *
 */
public class FilenetDocumentMockCondition implements Condition {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilenetDocumentMockCondition.class);

	@Override
	public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
		final String mockEnabled = context.getEnvironment().getProperty("filenet.mock.enabled");
		LOGGER.info("mockEnabled value " + mockEnabled);
		return new Boolean(mockEnabled);
	}

}
