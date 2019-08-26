package cn.csfz.transit.service;


import cn.csfz.transit.component.WebSocketClient;
import cn.csfz.transit.util.ByteUtils;
import cn.csfz.transit.util.FileUtils;
import com.neovisionaries.ws.client.WebSocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class ScheduledService {

    @Autowired
    WebSocketClient webSocketClient;

    @Value("${transit.inbox}")
    private String inBox;


    @Scheduled(cron = "*/5 * * * * ?")
    public void scheduledSendFile() {
        File[] fileList = new File(inBox).listFiles(pathname -> pathname.getName().contains(".xml"));
        if (fileList != null && fileList.length > 0) {
            for (File file : fileList) {
                if (file.length() > 0) {
                    byte[] bytes = ByteUtils.readBytesFromFileNio(file);
                    webSocketClient.sendMessage(bytes);
                    ByteUtils.writeBytesToFileNio(bytes, FileUtils.makeTimeDir(inBox) + "\\" + file.getName());
                    file.delete();
                    break;
                }
            }
        }
    }

    @Scheduled(cron = "*/2 * * * * ?")
    public void scheduledConnect() throws IOException, WebSocketException {
        if (!webSocketClient.isConnected()) {
            webSocketClient.connect();
        }

    }
}
