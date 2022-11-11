package cn.wyq.task.core.service.impl;

import cn.wyq.task.core.mapper.TaskNodeVariableMapper;
import cn.wyq.task.core.model.TaskNodeVariable;
import cn.wyq.task.core.service.TaskNodeVariableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskNodeVariableServiceImpl extends ServiceImpl<TaskNodeVariableMapper, TaskNodeVariable> implements TaskNodeVariableService {
}
