package com.humuson.test.client;

import com.humuson.test.common.exception.APIResponseException;
import com.humuson.test.config.AppProperties;
import com.humuson.test.config.StringConfig;
import com.humuson.test.order.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileClient {

    private final BufferedReader reader;

    FileClient(AppProperties appProperties) {
        String path = appProperties.externalFilePath;
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StringConfig.DEFAULT_FILE_ENCODING));
        } catch (FileNotFoundException ex) {
            throw new APIResponseException(HttpStatus.NOT_FOUND.value(), StringConfig.ERROR_MESSAGE_NOT_FOUND_FILE);
        } catch (UnsupportedEncodingException ex) {
            throw new APIResponseException(HttpStatus.BAD_REQUEST.value(), StringConfig.ERROR_MESSAGE_UNSUPPORTED_ENCODING);
        }
    }

    public List<Order> read() {
        List<Order> output = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (!line.equals(StringConfig.DEFAULT_FILE_HEADER)) {
                    String[] splLine = line.split(",");
                    Order order = new Order(
                            splLine[0],
                            splLine[1],
                            splLine[2],
                            splLine[3],
                            splLine[4],
                            Long.parseLong(splLine[5])
                    );
                    output.add(order);
                }
            }
        } catch (IOException ex) {
            throw new APIResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), StringConfig.ERROR_MESSAGE_FILE_READ_IO);
        }
        return output;
    }

    public void close() {
        try {
            this.reader.close();
        } catch (IOException ex) {
            throw new APIResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), StringConfig.ERROR_MESSAGE_FILE_CLOSE_IO);
        }
    }

}
