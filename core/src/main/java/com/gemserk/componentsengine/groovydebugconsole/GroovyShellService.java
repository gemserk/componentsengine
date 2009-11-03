/*
 * Copyright 2007 Bruce Fancher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gemserk.componentsengine.groovydebugconsole;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Bruce Fancher
 * 
 */
public class GroovyShellService extends GroovyService {

    private ServerSocket serverSocket;
    private int socket;
    private Thread serverThread;
    private List<GroovyShellThread>threads = new ArrayList<GroovyShellThread>();

    public GroovyShellService() {
        super();
    }

    public GroovyShellService(int socket) {
        super();
        this.socket = socket;
    }

    public GroovyShellService(Map bindings, int socket) {
        super(bindings);
        this.socket = socket;
    }
    
    public void launch() {
        logger.info("GroovyShellService launch()");

        try {
            serverSocket = new ServerSocket(socket);
            logger.info("GroovyShellService launch() serverSocket: " + serverSocket);

            while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    logger.info("GroovyShellService launch() clientSocket: " + clientSocket);
                }
                catch (IOException e) {
                    logger.debug("e: " + e);
                    return;
                }

                GroovyShellThread clientThread = new GroovyShellThread(clientSocket, createBinding());
                threads.add(clientThread);
                clientThread.start();
            }
        }
        catch (IOException e) {
            logger.debug("e: " + e);
            return;
        }
        finally {
            try {
                serverSocket.close();
            }
            catch (IOException e) {
                logger.warn("e: " + e);
                return;
            }
            logger.info("GroovyShellService launch() closed connection");
        }
    }

    @Override
    public void destroy() {
        logger.info("closing serverSocket: " + serverSocket);
        try {
            serverSocket.close();
            for (GroovyShellThread nextThread : threads)  {
                logger.info("closing nextThread: " + nextThread);
                nextThread.getSocket().close();
            }
        }
        catch (IOException e) {
            logger.warn("e: " + e);
        }
    }

    public void setSocket(final int socket) {
        this.socket = socket;
    }
}
