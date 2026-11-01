package com.kikop.mycontroller;

//import jakarta.servlet.http.HttpServletResponse;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/flowable")
public class FlowableController {

    @Autowired
    private ProcessEngine processEngine;


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;


    /**
     * 1.发起请假
     * @param userId 用户Id
     * @param days   请假天数
     * @param desc   描述
     */
    @GetMapping(value = "/add")
    public String addExpense(String userId, Integer days, String desc) {
        // http://localhost:8080/myflowable/flowable/add?userId=123&days=3&desc=回家结婚
        //设置流程参数,用户名等等
        HashMap<String, Object> map = new HashMap<>();
        map.put("userTask", userId);
        map.put("days", days);
        map.put("desc", desc);
        //启动一个流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myleave", map); // leave_flow
        //生成一个任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        //第一步任务流程完成后，会按照流程图自动分配给下一个流程
        taskService.complete(task.getId());
        return "提交成功\n流程Id为：" + processInstance.getId() + "\n任务id: " + task.getId() + "\n请假人为：" + userId + "\n请假天数为：" + days + "\n描述为：" + desc;
    }


    /**
     * 2.查看生成流程图
     *
     * @param instanceId 流程ID comment by kikop
     */
    @GetMapping(value = "processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String instanceId) throws Exception {

        // http://localhost:8080/myflowable/flowable/processDiagram?instanceId=f6a96b44-7083-11f0-aeb4-68f728d2516f
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(InstanceId).list();

        //得到正在执行的flowable的Id
        List<String> flowableIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            flowableIds.addAll(ids);
        }
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", flowableIds, Collections.emptyList(),
                engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), null, 1.0, false);
        OutputStream out = null;
        byte[] buf = new byte[10240];
        int legth = 0;
        try {

            httpServletResponse.setContentType("image/png"); // 设置响应类型为图片 comment by kikop
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } catch (IOException e) {
            System.out.println("生成流程图失败");
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 3.查询请假流程
     *
     * @param userId 查询人
     */
    @GetMapping("findTask")
    public String findTask(String userId) {
        // http://localhost:8080/myflowable/flowable/findTask?userId=经理
        //从引擎中获取任务
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskAssignee(userId).singleResult();
        return userId + "的任务ID：" + task.getId();
    }

    /**
     * 4.获取审批管理列表
     *
     * @param userId 查询人
     */
    @GetMapping(value = "/list")
    public Map<String, String> list(String userId) {
        // http://localhost:8080/myflowable/flowable/list?userId=经理

        Map<String, String> result = new HashMap<>();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            result.put(task.getName(), task.getId());
        }
        return result;
    }

    /**
     * 4.批准
     *
     * @param taskId 任务ID
     */
    @GetMapping(value = "apply")
    public String apply(String taskId) {
        // http://localhost:8080/myflowable/flowable/apply?taskId=8673e5f3-708c-11f0-a1b3-68f728d2516f

        //获得任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", "同意");
        taskService.complete(taskId, map);
        return "任务ID：" + taskId + "---批准!";
    }

    /**
     * 5.驳回
     */
    @GetMapping(value = "reject")
    public String reject(String taskId) {
        // http://localhost:8080/myflowable/flowable/reject?taskId=9cb5009a-708e-11f0-a1b3-68f728d2516f
        //获得任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", "驳回");
        taskService.complete(taskId, map);
        return "任务ID：" + taskId + "---驳回!";
    }

}