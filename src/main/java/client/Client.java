package client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.scb.wb.document.model.FilenetDocumentRequest;
import com.scb.wb.document.service.FilenetDocumentService;

// TODO: Delete this class.
@SpringBootApplication
@ComponentScan(basePackages = "com.scb.wb.document")
public class Client {

	public static void main(final String[] args) {
		final ConfigurableApplicationContext context = new SpringApplicationBuilder().sources(Client.class)
				.profiles("app").run(args);
		final FilenetDocumentService bean = context.getBean(FilenetDocumentService.class);
		bean.uploadDocument(new FilenetDocumentRequest());
	}

}
