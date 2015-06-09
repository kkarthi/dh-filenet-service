package client;

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
		context.getBean(ExternalFilenetServiceSimulator.class).uploadDocument();
	}
}