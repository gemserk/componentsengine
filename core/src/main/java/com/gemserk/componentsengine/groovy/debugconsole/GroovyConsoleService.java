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

import groovy.ui.Console;

/**
 * 
 * @author Bruce Fancher
 * 
 */
public class GroovyConsoleService extends GroovyService {

    private Thread thread;

    public GroovyConsoleService() {
        super();
    }

    public void launch() {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    new Console(createBinding()).run();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.setDaemon(true);
        thread.start();
    }
}
