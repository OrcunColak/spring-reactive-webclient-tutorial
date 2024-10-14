package com.colak.springtutorial.quote.config.logginwebclient;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.Request;
import org.eclipse.jetty.http.HttpField;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class LoggerService {

    /**
     * @param inboundRequest of type jetty client Request Object
     * @return return same request again after preparing logging
     */
    public Request logRequestResponse(Request inboundRequest) {
        // Created a string builder object to append logs belongs to specific request
        StringBuilder logBuilder = new StringBuilder();

        // Request Logging

        /*
            This listener will be invoked when the request is being processed in order to be sent.
            When this is invoked we are appending uri and method to string builder with line break.

         */
        inboundRequest.onRequestBegin(request -> logBuilder.append("Request: \n")
                .append("URI: ")
                .append(request.getURI())
                .append("\n")
                .append("Method: ")
                .append(request.getMethod()));

        /*
            This listener will be invoked when the request headers are ready to be sent.
            When this is invoked we are appending Headers to string builder with proper format.
            make sure debugging is enabled if not remove log.isDebugEnabled condition.
         */
        inboundRequest.onRequestHeaders(request -> {
            if (log.isDebugEnabled()) {
                logBuilder.append("\nHeaders:\n");
                for (HttpField header : request.getHeaders()) {
                    logBuilder.append("\t\t").append(header.getName()).append(" : ").append(header.getValue()).append("\n");
                }
            }
        });

        /*
            This listener will be invoked when request content has been sent successfully.
            When this is invoked we are converting bytes to String and appending to StringBuilder .
            make sure debugging is enabled if not remove log.isDebugEnabled condition.
         */

        inboundRequest.onRequestContent((request, content) -> {
            if (log.isDebugEnabled()) {
                var bufferAsString = StandardCharsets.UTF_8.decode(content).toString();
                logBuilder.append("Body: \n\t")
                        .append(bufferAsString);
            }
        });


        // Response Logging

        /*
            This listener will be invoked  when the response containing HTTP version, HTTP status code and reason has been received and parsed.
            When this is invoked we are appending response status to string builder with line break for formatting.
         */
        inboundRequest.onResponseBegin(response -> logBuilder.append("\nResponse:\n")
                .append("Status: ")
                .append(response.getStatus())
                .append("\n"));

         /*
            This listener will be invoked when the response headers are received and parsed.
            When this is invoked we are appending Headers to string builder with proper format.
            make sure debugging is enabled if not remove log.isDebugEnabled condition.
         */
        inboundRequest.onResponseHeaders(response -> {
            logBuilder.append("Headers:\n");
            for (HttpField header : response.getHeaders()) {
                logBuilder.append("\t\t").append(header.getName()).append(" : ").append(header.getValue()).append("\n");
            }
        });

        /*
            This listener will be invoked when response content has been received and parsed.
            When this is invoked we are converting bytes to String and appending to StringBuilder .
            make sure debugging is enabled if not remove log.isDebugEnabled condition.
         */
        inboundRequest.onResponseContent(((response, content) -> {
            if (log.isDebugEnabled()) {
                var bufferAsString = StandardCharsets.UTF_8.decode(content).toString();
                logBuilder.append("Response Body:\n").append(bufferAsString);
            }
        }));


        inboundRequest.onResponseSuccess(response -> log.info(logBuilder.toString()));

        inboundRequest.onRequestFailure((request, throwable) -> log.error(logBuilder.toString(), throwable));
        inboundRequest.onResponseFailure((response, throwable) -> log.error(logBuilder.toString(), throwable));

        return inboundRequest;
    }
}
