package com.qf.nauth.modules.auth.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.qf.nauth.common.web.ResponseData;
import com.qf.nauth.modules.auth.entity.MenuListEntity;
import com.qf.nauth.modules.auth.entity.RoleListEntity;
import com.qf.nauth.modules.auth.entity.UserEntity;
import com.qf.nauth.modules.auth.service.AuthorizingService;
import com.qf.nauth.modules.sys.security.exception.RestFulException;

/**
 * 授权信息查询接口
 * 
 * @ClassName: AuthorizationController
 * @author: [SenWu]
 * @CreateDate: [2017年5月7日 下午2:18:27]
 * @UpdateUser: [SenWu]
 * @UpdateDate: [2017年5月7日 下午2:18:27]
 * @UpdateRemark: []
 * @Description: []
 * @version: [V1.0]
 */
@RestController
@RequestMapping(value = "${adminPath}/service/v1")
public class AuthorizingController {

	private static Logger logger = LoggerFactory.getLogger(AuthorizingController.class);

	@Autowired
	private AuthorizingService authorizingService;

	/**
	 * 用户信息查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws RestFulException
	 */
	@RequestMapping(value = "/identity/users")
	public ResponseData getUserAndRoleInfo(ServletRequest request, ServletResponse response) {
		String userId = request.getParameter("userId");
		String systemId = request.getParameter("systemId");
		logger.info("用户信息查询接口 request input:userId({}),systemId({})", userId, systemId);
		if (StringUtils.isBlank(userId)) {
			logger.error("用户信息查询接口 request中用户名参数值不存在。 ");
			return ResponseData.badRequest().putDataValue(ResponseData.ERRORS_KEY, "userId参数值不存在");
		}

		ResponseData respData = ResponseData.ok();
		try {
			UserEntity userEntity = authorizingService.getByLoginName(userId);

			if (userEntity == null) {
				logger.error("用户信息查询接口  request对应的权限系统中无此用户信息。 ");
				return ResponseData.notFound().putDataValue(ResponseData.ERRORS_KEY, "权限系统中无此用户信息，请检查！！！");
			}

			if (userEntity.getId() == null || "".equals(userEntity.getId())) {
				logger.error("用户信息查询接口  request对应的权限系统中该用户ID为空，存在异常。 ");
				return ResponseData.serverInternalError().putDataValue(ResponseData.ERRORS_KEY,
						"权限系统中该用户ID为空，存在异常，请检查！！！");
			}

			respData.putDataValue("userInfo", userEntity);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", userEntity.getId());
			map.put("systemId", systemId);

			List<RoleListEntity> roleEntityList = authorizingService.getRoleListByUser(map);

			LinkedHashMap<String, Object> roleLists = new LinkedHashMap<String, Object>();
			for (RoleListEntity roleListEntity : roleEntityList) {

				roleLists.put(roleListEntity.getSystemId(), roleListEntity.getRoleList());
			}

			respData.putDataValue("roleList", roleLists);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("用户信息查询接口出错，异常信息如下:\n", e);
			}
			return ResponseData.serverInternalError().putDataValue(ResponseData.ERRORS_KEY, e.getMessage());

		}
		logger.info("用户信息查询接口 查询成功:返回报文如下:{}", JSON.toJSON(respData));

		return respData;

	}

	/**
	 * 用户授权查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/authorization")
	public ResponseData getMenuListInfo(ServletRequest request, ServletResponse response) {
		String userId = request.getParameter("userId");
		String systemId = request.getParameter("systemId");

		logger.info("用户授权查询接口 request input:userId({}),systemId({})", userId, systemId);

		if (StringUtils.isBlank(userId)) {

			logger.error("用户授权查询接口 request中用户名参数值不存在。 ");
			return ResponseData.badRequest().putDataValue(ResponseData.ERRORS_KEY, "userId参数值不存在");
		}

		ResponseData respData = ResponseData.ok();
		try {
			UserEntity userEntity = authorizingService.getByLoginName(userId);

			if (userEntity == null) {

				logger.error("用户授权查询接口  request对应的权限系统中无此用户信息。 ");
				return ResponseData.notFound().putDataValue(ResponseData.ERRORS_KEY, "权限系统中无此用户信息，请检查！！！");
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("systemId", systemId);

			List<MenuListEntity> menuList = authorizingService.getMenuList(map);

			if (menuList == null || menuList.isEmpty()) {

				logger.error("用户授权查询接口  request对应的权限系统无此用户对应systemId{}的信息权限菜信息。 ",
						((systemId == null || "".equals(systemId)) ? "为空" : systemId));
				/*return ResponseData.notFound().putDataValue(ResponseData.ERRORS_KEY, "权限系统无此用户对应systemId"
						+ ((systemId == null || "".equals(systemId)) ? "为空" : systemId) + "的信息权限菜信息，请检查！！！");*/

			}

			LinkedHashMap<String, Object> menuLists = new LinkedHashMap<String, Object>();
			for (MenuListEntity menuListEntity : menuList) {

				menuLists.put(menuListEntity.getSystemId(), menuListEntity.getMenuList());
			}

			respData.putDataValue("menuList", menuLists);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("用户授权查询接口出错，异常信息如下:\n", e);
			}
			return ResponseData.serverInternalError().putDataValue(ResponseData.ERRORS_KEY, e.getMessage());
		}
		logger.info("用户授权查询接口 查询成功:返回报文如下:{}", JSON.toJSON(respData));

		return respData;
	}
}
