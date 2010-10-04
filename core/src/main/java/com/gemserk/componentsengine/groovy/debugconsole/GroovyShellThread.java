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

package com.gemserk.componentsengine.groovy.debugconsole;

import groovy.lang.Binding;
import groovy.ui.InteractiveShell;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 
 * @author Bruce Fancher
 * 
 */
public class GroovyShellThread extends Thread {
    public static final String OUT_KEY = "out";

    private Socket socket;
    private Binding binding;

    public GroovyShellThread(Socket socket, Binding binding) {
        super();
        this.socket = socket;
        this.binding = binding;
    }

    @Override
    public void run() {
        try {
            final PrintStream out = new PrintStream(socket.getOutputStream());
            final InputStream in = socket.getInputStream();

            binding.setVariable(OUT_KEY, out);
            final InteractiveShell groovy = new InteractiveShell(binding, in, out, out);

            try {
                groovy.run();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            out.close();
            in.close();
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
