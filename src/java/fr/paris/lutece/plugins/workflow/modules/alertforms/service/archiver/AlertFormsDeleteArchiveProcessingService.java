package fr.paris.lutece.plugins.workflow.modules.alertforms.service.archiver;

import java.util.List;

import javax.inject.Inject;

import fr.paris.lutece.plugins.workflow.modules.alertforms.service.IAlertService;
import fr.paris.lutece.plugins.workflow.modules.archive.service.AbstractArchiveProcessingService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceWorkflow;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;

public class AlertFormsDeleteArchiveProcessingService extends AbstractArchiveProcessingService
{

    public static final String BEAN_NAME = "workflow-alertforms.alertFormsDeleteArchiveProcessingService";
    
    private static final String TASK_TYPE_ALERT = "taskAlert";
    
    @Inject
    private IResourceHistoryService _resourceHistoryService;
    
    @Inject
    private IAlertService _alertService;
    
    @Override
    public void archiveResource( ResourceWorkflow resourceWorkflow )
    {
        List<ResourceHistory> historyList = _resourceHistoryService.getAllHistoryByResource( resourceWorkflow.getIdResource( ),
                resourceWorkflow.getResourceType( ), resourceWorkflow.getWorkflow( ).getId( ) );
        
        for ( ResourceHistory history : historyList )
        {
            List<ITask> taskList = findTasksByHistory( history, TASK_TYPE_ALERT );
            for ( ITask task : taskList )
            {
                _alertService.deleteByHistory( history.getId( ), task.getId( ) );
            }
        }
    }

}
