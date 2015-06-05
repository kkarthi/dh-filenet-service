package com.scb.wb.document.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.scb.wb.document.service.FilenetDocumentService;
import com.scb.wb.document.service.impl.FilenetDocumentServiceImpl;

@Configuration
public class FilenetImplementationConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilenetImplementationConfiguration.class);

	@Autowired
	private ConfigurableApplicationContext context;

	@Bean(name = "filenetDocumentService")
	@Conditional(FilenetDocumentMockCondition.class)
	public FilenetDocumentService mockFilenetDocumentService() {
		LOGGER.info("Called mockFilenetDocumentService ");
		final BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
		beanFactory.removeBeanDefinition("filenetDocumentService");
		beanFactory.removeBeanDefinition("filenetDocumentServiceImpl");
		return (FilenetDocumentService) context.getBean("mockFilenetDocumentServiceImpl");
	}

	@Bean(name = "filenetDocumentService")
	@Conditional(FilenetDocumentCondition.class)
	public FilenetDocumentService actualFilenetDocumentService() {
		LOGGER.info("Called filenetDocumentService");
		final BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
		beanFactory.removeBeanDefinition("filenetDocumentServiceImpl");
		beanFactory.removeBeanDefinition("mockFilenetDocumentServiceImpl");
		return new FilenetDocumentServiceImpl();
	}
}
