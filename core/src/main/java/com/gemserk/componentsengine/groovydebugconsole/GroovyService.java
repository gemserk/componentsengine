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

import groovy.lang.Binding;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * 
 * @author Bruce Fancher
 * 
 */
public abstract class GroovyService  {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    private Map<String, Object> bindings;
    private boolean launchAtStart;
    private Thread serverThread;

    public GroovyService() {
        super();
    }
    
    public GroovyService(Map<String, Object> bindings) {
        this();
        this.bindings = bindings;
    }

    public void launchInBackground() {
        serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    launch();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        serverThread.setDaemon(true);
        serverThread.start();
    }

    public abstract void launch();

    protected Binding createBinding() {
        Binding binding = new Binding();

        if (bindings != null)  {
            for (Map.Entry<String, Object> nextBinding : bindings.entrySet()) {
                binding.setVariable(nextBinding.getKey(), nextBinding.getValue());
            }
        }

        return binding;
    }
    
    public void initialize() {
        if (launchAtStart) {
            launchInBackground();
        }
    }
    
    public void destroy() {
    }

    public void setBindings(final Map<String, Object> bindings) {
        this.bindings = bindings;
    }

    public boolean isLaunchAtStart() {
        return launchAtStart;
    }

    public void setLaunchAtStart(boolean launchAtStart) {
        this.launchAtStart = launchAtStart;
    }
}
