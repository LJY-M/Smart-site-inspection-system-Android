package com.graduation.smart_site_inspection_system.Bean.ProjectTree;


import com.baozi.treerecyclerview.annotation.TreeDataType;

import java.util.List;

@TreeDataType(iClass = ClientItem.class, bindField = "type")
public class ClientBean {
    public int clientId;
    public String clientName;
    public List<ProjectBean> projects;


    @TreeDataType(iClass = ProjectItem.class)
    public static class ProjectBean {
        public int projectId;
        public String projectName;
        public List<CheckSys1Bean> checkSystems;


        @TreeDataType(iClass = CheckSys1Item.class)
        public static class CheckSys1Bean {
            public int id;
            public String name;
            public List<CheckSys2Bean> subCheckSystems;

            @TreeDataType(iClass = CheckSys2Item.class)
            public static class CheckSys2Bean {
                public int id;
                public String name;
                public boolean examState;
                public boolean passState;
            }

        }
    }
}
