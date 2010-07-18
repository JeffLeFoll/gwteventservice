/*
 * GWTEventService
 * Copyright (c) 2010, GWTEventService Committers
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.novanic.eventservice.client.event.command;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.novanic.eventservice.client.connection.strategy.connector.RemoteEventConnector;
import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;

/**
 * The {@link de.novanic.eventservice.client.event.command.EventExecutionCommand} sends an event to a domain or to the
 * sending / calling user (user-specific event, when no domain is provided).
 *
 * @author sstrohschein
 *         <br>Date: 04.07.2010
 *         <br>Time: 13:45:18
 */
public class EventExecutionCommand extends ServerCallCommand<Void>
{
    private Domain myDomain;
    private Event myEvent;

    /**
     * Creates an EventExecutionCommand to send / register events to the server side.
     *
     * @param aRemoteEventConnector {@link de.novanic.eventservice.client.connection.strategy.connector.RemoteEventConnector}
     * @param aDomain domain
     * @param anEvent event
     * @param aCallback callback of the command
     */
    public EventExecutionCommand(RemoteEventConnector aRemoteEventConnector, Domain aDomain, Event anEvent, AsyncCallback<Void> aCallback) {
        super(aRemoteEventConnector, aCallback);
        myDomain = aDomain;
        myEvent = anEvent;
    }

    /**
     * Creates an EventExecutionCommand to send / register events to the server side. When no domain is provided it will be
     * registered as a user-specific event.
     *
     * @param aRemoteEventConnector {@link de.novanic.eventservice.client.connection.strategy.connector.RemoteEventConnector}
     * @param anEvent event
     * @param aCallback callback of the command
     */
    public EventExecutionCommand(RemoteEventConnector aRemoteEventConnector, Event anEvent, AsyncCallback<Void> aCallback) {
        this(aRemoteEventConnector, null, anEvent, aCallback);
    }

    /**
     * Sends / registers the event to the server side (domain- or user-specific events)
     */
    public void execute() {
        if(myDomain != null) {
            getRemoteEventConnector().sendEvent(myDomain, myEvent, getCommandCallback());
        } else {
            getRemoteEventConnector().sendEventUserSpecific(myEvent, getCommandCallback());
        }
    }
}