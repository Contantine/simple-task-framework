package cn.wyq.task.core.action;

import cn.wyq.task.core.dto.TaskNodeDTO;
import cn.wyq.task.core.model.NodeUser;
import cn.wyq.task.core.model.TaskVariable;

import java.util.List;
import java.util.Map;

public interface Action {
    /**
     * 获取可以操作该任务节点的处理人
     * @param taskNodeDTO 任务节点信息
     * @return 处理人列表
     */
    List<NodeUser> assign(TaskNodeDTO taskNodeDTO);

    /**
     * 节点处理,返回需要存储的任务全局数据,其中,如果任务被终止,请放入terminate_flag 为true,表示任务被终止
     */
    default Map<String, Object> doProgress(String instanceCode, Map<String, Object> taskNodeVariableMap) {
        return null;
    };


}
