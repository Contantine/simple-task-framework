package cn.wyq.task.core.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.wyq.task.core.enums.TaskStatusEnum;
import cn.wyq.task.core.mapper.TaskInstanceMapper;
import cn.wyq.task.core.model.TaskInstance;
import cn.wyq.task.core.service.TaskInstanceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskInstanceServiceImpl extends ServiceImpl<TaskInstanceMapper, TaskInstance>
        implements TaskInstanceService {
    @Override
    public TaskInstance generateTaskInstance(String definitionCode) {
        TaskInstance instance = new TaskInstance();
        instance.setDefinitionCode(definitionCode);
        instance.setStatus(TaskStatusEnum.IN_PROGRESS.getStatus());
        String instanceCode = RandomUtil.randomString(20);
        instance.setInstanceCode(instanceCode);
        baseMapper.insert(instance);
        return baseMapper.selectOne(new LambdaQueryWrapper<TaskInstance>().eq(TaskInstance::getInstanceCode, instanceCode));
    }

    @Override
    public TaskInstance getByInstanceCode(String instanceCode) {
        return baseMapper.selectOne(new LambdaQueryWrapper<TaskInstance>().eq(TaskInstance::getInstanceCode, instanceCode));
    }
}
