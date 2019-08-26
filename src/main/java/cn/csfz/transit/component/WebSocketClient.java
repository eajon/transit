package cn.csfz.transit.component;

import cn.csfz.transit.util.ByteUtils;
import cn.csfz.transit.util.PropertyUtil;
import com.neovisionaries.ws.client.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author eajon
 */
@Component
public class WebSocketClient {

    private WebSocket webSocket;

    private WebSocketFactory webSocketFactory;

    public boolean isConnected() {
        if (webSocket == null) {
            return false;
        }
        return webSocket.isOpen();
    }


    public WebSocketClient() {
        webSocketFactory = new WebSocketFactory();
    }

    public void connect() throws IOException, WebSocketException {
        webSocket = webSocketFactory.createSocket(PropertyUtil.getProperty("transit.server") + "?id=" + PropertyUtil.getProperty("transit.id"), 5000);
        webSocket.addListener(new WebSocketAdapter() {
            @Override
            public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                super.onConnected(websocket, headers);
                System.out.println("连接成功");
            }

            @Override
            public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
                super.onBinaryMessage(websocket, binary);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String time = simpleDateFormat.format(new Date());
                String uuid = UUID.randomUUID().toString();
                String filename = time + "_" + uuid;
                ByteUtils.writeBytesToFileNio(binary, PropertyUtil.getProperty("transit.outbox") + filename + ".xml");

            }

            @Override
            public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
                super.onError(websocket, cause);
                System.out.println("onError");
            }

            @Override
            public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
                System.out.println("onDisconnected");
            }

            @Override
            public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {
                super.onUnexpectedError(websocket, cause);
                System.out.println("onError");
            }
        });
        webSocket.connect();
    }

    public void sendMessage(byte[] bytes) {
        webSocket.sendBinary(bytes);
    }
}
