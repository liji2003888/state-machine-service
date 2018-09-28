package io.choerodon.statemachine.api.controller.v1;

import io.choerodon.core.base.BaseController;
import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.statemachine.api.dto.StateMachineDTO;
import io.choerodon.statemachine.api.service.StateMachineService;
import io.choerodon.statemachine.api.validator.StateMachineValidator;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author peng.jiang@hand-china.com
 */
@RestController
@RequestMapping(value = "/v1/organizations/{organization_id}/state_machines")
public class StateMachineController extends BaseController {

    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private StateMachineValidator stateMachineValidator;

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "分页查询状态机列表")
    @CustomPageRequest
    @GetMapping
    public ResponseEntity<Page<StateMachineDTO>> pagingQuery(@ApiIgnore
                                                             @SortDefault(value = "id", direction = Sort.Direction.DESC) PageRequest pageRequest,
                                                             @PathVariable("organization_id") Long organizationId,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String description,
                                                             @RequestParam(required = false) String[] param) {
        return new ResponseEntity<>(stateMachineService.pageQuery(pageRequest, new StateMachineDTO(name, description, organizationId),
                Arrays.stream(param).collect(Collectors.joining(","))), HttpStatus.OK);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "创建状态机")
    @PostMapping
    public ResponseEntity<StateMachineDTO> create(@PathVariable("organization_id") Long organizationId, @RequestBody StateMachineDTO stateMachineDTO) {
        stateMachineValidator.createValidate(stateMachineDTO);
        return new ResponseEntity<>(stateMachineService.create(organizationId, stateMachineDTO), HttpStatus.CREATED);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "更新状态机")
    @PutMapping(value = "/{state_machine_id}")
    public ResponseEntity<StateMachineDTO> update(@PathVariable("organization_id") Long organizationId,
                                                  @PathVariable("state_machine_id") Long stateMachineId,
                                                  @RequestBody StateMachineDTO stateMachineDTO) {
        stateMachineValidator.updateValidate(stateMachineDTO);
        return new ResponseEntity<>(stateMachineService.update(organizationId, stateMachineId, stateMachineDTO), HttpStatus.CREATED);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "删除状态机")
    @DeleteMapping(value = "/{state_machine_id}")
    public ResponseEntity<Boolean> delete(@PathVariable("organization_id") Long organizationId,
                                          @PathVariable("state_machine_id") Long stateMachineId) {
        return new ResponseEntity<>(stateMachineService.delete(organizationId, stateMachineId), HttpStatus.NO_CONTENT);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "发布状态机")
    @GetMapping(value = "/deploy/{state_machine_id}")
    public ResponseEntity<StateMachineDTO> deploy(@PathVariable("organization_id") Long organizationId,
                                                  @PathVariable("state_machine_id") Long stateMachineId) {
        return new ResponseEntity<>(stateMachineService.deploy(organizationId, stateMachineId), HttpStatus.OK);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "获取状态机及配置")
    @GetMapping(value = "/{state_machine_id}")
    public ResponseEntity<StateMachineDTO> queryStateMachineWithConfigById(@PathVariable("organization_id") Long organizationId,
                                                                           @PathVariable("state_machine_id") Long stateMachineId) {
        return new ResponseEntity<>(stateMachineService.queryStateMachineWithConfigById(organizationId, stateMachineId), HttpStatus.OK);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "获取状态机原件及配置")
    @GetMapping(value = "/original/{state_machine_id}")
    public ResponseEntity<StateMachineDTO> queryOriginalById(@PathVariable("organization_id") Long organizationId,
                                                             @PathVariable("state_machine_id") Long stateMachineId) {

        return new ResponseEntity<>(stateMachineService.queryOriginalById(organizationId, stateMachineId), HttpStatus.OK);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "删除草稿")
    @DeleteMapping(value = "/delete_draft/{state_machine_id}")
    public ResponseEntity<StateMachineDTO> deleteDraft(@PathVariable("organization_id") Long organizationId,
                                                       @PathVariable("state_machine_id") Long stateMachineId) {
        return new ResponseEntity<>(stateMachineService.deleteDraft(organizationId, stateMachineId), HttpStatus.NO_CONTENT);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "获取状态机")
    @GetMapping(value = "/get_stateMachine/{state_machine_id}")
    public ResponseEntity<StateMachineDTO> getStateMachineById(@PathVariable("organization_id") Long organizationId,
                                                               @PathVariable("state_machine_id") Long stateMachineId) {
        return new ResponseEntity<>(stateMachineService.queryStateMachineById(organizationId, stateMachineId), HttpStatus.OK);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "校验状态机名字是否未被使用")
    @GetMapping(value = "/check_name")
    public ResponseEntity<Boolean> checkName(@PathVariable("organization_id") Long organizationId,
                                             @RequestParam(value = "state_machine_id", required = false) Long stateMachineId,
                                             @RequestParam("name") String name) {
        return new ResponseEntity<>(stateMachineService.checkName(organizationId, stateMachineId, name), HttpStatus.OK);
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "获取组织下所有状态机")
    @GetMapping(value = "/query_all")
    public ResponseEntity<List<StateMachineDTO>> queryAll(@PathVariable("organization_id") Long organizationId) {
        return new ResponseEntity<>(stateMachineService.queryAll(organizationId), HttpStatus.OK);
    }
}