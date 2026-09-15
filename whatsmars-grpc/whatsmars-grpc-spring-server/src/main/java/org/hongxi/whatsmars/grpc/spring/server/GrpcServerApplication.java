package org.hongxi.whatsmars.grpc.spring.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;

@SpringBootApplication
public class GrpcServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrpcServerApplication.class, args);
	}

	@Bean
	public GrpcExceptionHandler globalInterceptor() {
		return exception -> {
			if (exception instanceof IllegalArgumentException) {
				Metadata metadata = new Metadata();
				metadata.put(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER), "INVALID_ARGUMENT");
                return Status.INVALID_ARGUMENT.withDescription(exception.getMessage())
                    .asException(metadata);
			}
			return null;
		};
	}

}