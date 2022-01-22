package client;

import model.Data;

import java.util.List;

public interface ServerConnectorListener {
    void onMessageReceived(Data data);
    void userListUpdate(List<String> userList);

}
