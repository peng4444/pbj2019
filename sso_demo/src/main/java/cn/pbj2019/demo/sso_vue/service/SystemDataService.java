package cn.pbj2019.demo.sso_vue.service;


import cn.pbj2019.demo.sso_vue.domain.CustomData;
import cn.pbj2019.demo.sso_vue.domain.ResultDetails;
import cn.pbj2019.demo.sso_vue.exception.CustomizeException;

import java.util.List;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2019-12-01 16:22
 */
public interface SystemDataService {
    List<CustomData> get();

    CustomData select(String id) throws CustomizeException;

    ResultDetails delete(String id, String authorities) throws CustomizeException;

    CustomData create(CustomData customData);
}
