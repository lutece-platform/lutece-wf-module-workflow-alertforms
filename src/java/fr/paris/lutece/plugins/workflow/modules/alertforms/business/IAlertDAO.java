/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.alertforms.business;

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;

/**
 *
 * IAlertValueDAO
 *
 */
public interface IAlertDAO
{
    /**
     * Insert new formresponse
     * 
     * @param alert
     *            the Alert Object
     * @param plugin
     *            the plugin
     */
    void insert( Alert alert, Plugin plugin );

    /**
     * Load a formresponse by primary key
     * 
     * @param nIdResourceHistory
     *            the action history id
     * @param nIdTask
     *            the task id
     * @param plugin
     *            the plugin
     * @return Alert Object
     */
    Alert load( int nIdResourceHistory, int nIdTask, Plugin plugin );

    /**
     * Desactivate alert by history
     * 
     * @param nIdResourceHistory
     *            the History id
     * @param nIdTask
     *            the task id
     * @param plugin
     *            the plugin
     */
    void desactivateByHistory( int nIdResourceHistory, int nIdTask, boolean executed, Plugin plugin );

    /**
     * Desactivate alert by task
     * 
     * @param nIdTask
     *            the task id
     * @param plugin
     *            the plugin
     */
    void desactivateByTask( int nIdTask, Plugin plugin );

    /**
     * Find all active alerts
     * 
     * @param plugin
     *            the plugin
     * @return a list of active Alert
     */
    List<Alert> selectAllActive( Plugin plugin );
    
    /**
     * Delete alert by history
     * 
     * @param nIdResourceHistory
     *            the History id
     * @param nIdTask
     *            the task id
     * @param plugin
     *            the plugin
     */
    void deleteByHistory( int nIdResourceHistory, int nIdTask, Plugin plugin );
}
