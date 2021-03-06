package io.choerodon.statemachine.api.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shinan.chen
 * @date 2018/10/9
 */
public class PropertyData {
    @ApiModelProperty(value = "服务名称")
    private String serviceName;
    @ApiModelProperty(value = "状态机配置列表")
    private List<ConfigCodeDTO> list = new ArrayList<>();

    public List<ConfigCodeDTO> getList() {
        return list;
    }

    public void setList(List<ConfigCodeDTO> list) {
        this.list = list;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "PropertyData{" +
                "serviceName='" + serviceName + '\'' +
                ", list=" + list +
                '}';
    }
}
