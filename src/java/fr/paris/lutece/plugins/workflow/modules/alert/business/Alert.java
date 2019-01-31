/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.workflow.modules.alert.business;

import java.sql.Timestamp;

/**
 *
 * Alert
 *
 */
public class Alert
{
    private int _nIdResourceHistory;
    private int _nIdTask;
    private Timestamp _dateReference;
    private boolean _bIsActive;

    /**
     * Set the id resource history
     * 
     * @param nIdResourceHistory
     *            the id resource history
     */
    public void setIdResourceHistory( int nIdResourceHistory )
    {
        _nIdResourceHistory = nIdResourceHistory;
    }

    /**
     * Get the id resource history
     * 
     * @return the id resource history
     */
    public int getIdResourceHistory( )
    {
        return _nIdResourceHistory;
    }

    /**
     * Set the id task
     * 
     * @param nIdTask
     *            the id task
     */
    public void setIdTask( int nIdTask )
    {
        _nIdTask = nIdTask;
    }

    /**
     * Get the id task
     * 
     * @return the id task
     */
    public int getIdTask( )
    {
        return _nIdTask;
    }

    /**
     * Set the reference date
     * 
     * @param dateReference
     *            the dateReference to set
     */
    public void setDateReference( Timestamp dateReference )
    {
        _dateReference = dateReference;
    }

    /**
     * Get the reference date
     * 
     * @return the dateReference
     */
    public Timestamp getDateReference( )
    {
        return _dateReference;
    }

    /**
     * Get the is active boolean
     * 
     * @return the is active boolean
     */
    public boolean isActive( )
    {
        return _bIsActive;
    }

    /**
     * Set the is active boolean
     * 
     * @param bIsActive
     *            the is active boolean
     */
    public void setActive( boolean bIsActive )
    {
        _bIsActive = bIsActive;
    }

}
