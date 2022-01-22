package client;





import com.google.gson.Gson;
import model.Data;
import model.Type;


import java.io.*;
import java.net.Socket;

public class ServerConnector {
    private Socket socket;
    private final String host;
    private final String username;
    private final int port;
    private boolean isConnected = false;
    private ServerConnectorListener serverConnectorListener;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;
    private Thread readingThread;
    public ServerConnector(String host, int port,String username) {
        this.host = host;
        this.port = port;
        this.username = username;
    }

   public void connect(ConnectionListener connectionListener){

            Thread connectionThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        socket = new Socket(host,port);
                        dataInputStream =new DataInputStream(socket.getInputStream());
                        dataOutputStream =new DataOutputStream(socket.getOutputStream());
                        Data data = new Data(Type.JOIN_REQUEST,username);
                        dataOutputStream.writeUTF(data.getJsonString());

                        if(connectionListener!=null){
                            connectionListener.connected();
                        }

                    } catch (IOException e) {

                        if(connectionListener!=null){
                            connectionListener.connectionFailed(e.getMessage());
                        }
                        e.printStackTrace();
                    }

                    isConnected = true;
                }
            },"connection_thread");
            connectionThread.start();

    }
    public void sendMessage(Data data){

        try {
            dataOutputStream.writeUTF(data.getJsonString());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close(){
        try {
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void readingMessage(){
       readingThread = new Thread(new Runnable() {
                  @Override
                  public void run() {
                      try {
                          while (true) {
                              String message = dataInputStream.readUTF();
                              Gson gson = new Gson();
                              Data readData = gson.fromJson(message, Data.class);
                              if (serverConnectorListener != null) {

                                  if(readData.getType() == Type.USER_LIST_REQUEST ){
                                      serverConnectorListener.userListUpdate(readData.getClientList());
                                  }else {
                                      serverConnectorListener.onMessageReceived(readData);
                                  }


                              }
                          }
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                  }
              }, "reading_thread");
       readingThread.start();

    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getUsername() {
        return username;
    }

    public void setServerConnectorListener(ServerConnectorListener serverConnectorListener) {
        this.serverConnectorListener = serverConnectorListener;
    }
}
