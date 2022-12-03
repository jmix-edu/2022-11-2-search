package com.company.jmixpm.screen.project;

import io.jmix.search.index.EntityIndexer;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {

    @Autowired
    private GroupTable<Project> projectsTable;

    @Autowired
    private EntityIndexer entityIndexer;

    @Subscribe("projectsTable.index")
    public void onProjectsTableIndex(Action.ActionPerformedEvent event) {
        Project selected = projectsTable.getSingleSelected();

        entityIndexer.index(selected);
    }
}