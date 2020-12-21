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
        public List<CheckSys1Bean> checkSys1s;


        @TreeDataType(iClass = CheckSys1Item.class)
        public static class CheckSys1Bean {
            public int checkSys1Id;
            public String checkSys1Name;
            public List<CheckSys2Bean> checkSys2s;

            @TreeDataType(iClass = CheckSys2Item.class)
            public static class CheckSys2Bean {
                public int checkSys2Id;
                public String checkSys2Name;
            }

        }
    }
}
