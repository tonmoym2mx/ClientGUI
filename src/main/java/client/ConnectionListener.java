package client;

public interface ConnectionListener {
    void connected();
    void connectionFailed(String message);
}
