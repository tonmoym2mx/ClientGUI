package client;

import model.Data;

public interface ServerConnectorListener {
    void onMessageReceived(Data data);

}
