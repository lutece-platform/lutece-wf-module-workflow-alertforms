package fr.paris.lutece.plugins.workflow.modules.alertforms.business;

import java.sql.Timestamp;

import fr.paris.lutece.plugins.workflow.modules.alertforms.service.AlertPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.test.LuteceTestCase;

public class AlertBusinessTest extends LuteceTestCase
{
    private IAlertDAO _dao;
    
    @Override
    protected void setUp( ) throws Exception
    {
        super.setUp( );
        _dao = SpringContextService.getBean( AlertDAO.BEAN_NAME );
    }
    public void testCRUD( )
    {
        Alert alert = new Alert( );
        alert.setActive( true );
        alert.setExecuted( false );
        alert.setIdResourceHistory( 1 );
        alert.setIdTask( 2 );
        alert.setDateReference( new Timestamp( System.currentTimeMillis( ) ) );
        
        _dao.insert( alert, PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
        
        Alert loaded = _dao.load( 1, 2, PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
        assertEquals( alert.isActive( ),  loaded.isActive( ) );
        assertEquals( alert.isExecuted( ),  loaded.isExecuted( ) );
        assertEquals( alert.getDateReference( ),  loaded.getDateReference( ) );
        
        _dao.deleteByHistory( 1, 2, PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
        
        loaded = _dao.load( 1, 2, PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
        assertNull( loaded );
    }
}
