package cn.wyq.task.core.service.impl;

import cn.wyq.task.core.mapper.TaskDefinitionMapper;
import cn.wyq.task.core.model.TaskDefinition;
import cn.wyq.task.core.service.TaskDefinitionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskDefinitionServiceImpl extends ServiceImpl<TaskDefinitionMapper, TaskDefinition>
        implements TaskDefinitionService {
    @Override
    public TaskDefinition checkDefinitionCode(String definitionCode) {
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<TaskDefinition>()
                .eq(TaskDefinition::getDefinitionCode, definitionCode);

        TaskDefinition res = baseMapper.selectOne(queryWrapper);
        return res;
    }
}
