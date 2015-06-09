package client;

import static org.springframework.util.Assert.notNull;

import java.io.FileNotFoundException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.scb.wb.document.exception.DocumentException;

@SpringBootApplication
@ComponentScan(basePackages = { "com.scb.wb.document", "client" })
public class Client {

	public static void main(final String[] args) throws DocumentException, FileNotFoundException {
		final ConfigurableApplicationContext context = new SpringApplicationBuilder().sources(Client.class)
				.profiles("app").run(args);
		final ExternalFilenetServiceSimulator bean = context.getBean(ExternalFilenetServiceSimulator.class);
		final String filenetReferenceId = bean.uploadDocument();
		notNull(filenetReferenceId, "filenetReferenceId should not be null");
		final String updatedFilenetReferenceId = bean.updateDocument(filenetReferenceId);
		notNull(updatedFilenetReferenceId, "updatedFilenetReferenceId should not be null");
		final String url = bean.downloadDocumentURL(updatedFilenetReferenceId);
		notNull(url, "url should not be null");
	}
}