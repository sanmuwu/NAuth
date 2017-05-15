(function ($) {
	// 检验用户当前session是否已经通过认证
    $.fn.isAuthenticated = function () {
    	var _this = this;
    	_this.hide();
		$.ajax({	
			url : "auth/sys/authori/isAuthenticated",
			async : false,
			type : "GET",
			dataType : "json",
			success: function(result) {
				// 是否不具有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用认证方法出错，错误信息：" + message);
			}
		});
		return _this;
    };
    
    // 检验用户当前session是否未通过认证
    $.fn.isNotAuthenticated = function () {
    	var _this = this;
    	_this.hide();
		$.ajax({	
			url : "auth/sys/authori/isNotAuthenticated",
			async : false,
			type : "GET",
			dataType : "json",
			success: function(result) {
				// 是否不具有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用认证方法出错，错误信息：" + message);
			}
		});
		return _this;
		
    };
    
    // 检验用户当前session是否通过认证或者有认证身份标识对象
    $.fn.isUser = function () {
    	var _this = this;
    	_this.hide();
		$.ajax({	
			url : "auth/sys/authori/isUser",
			async : false,
			type : "GET",
			dataType : "json",
			success: function(result) {
				// 是否不具有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用认证方法出错，错误信息：" + message);
			}
		});
		return _this;
    };
    
    // 检验用户是否guest帐户(即当前session中并无认证身份标识)
    $.fn.isGuest = function () {
    	var _this = this;
    	_this.hide();
		$.ajax({	
			url : "auth/sys/authori/isGuest",
			async : false,
			type : "GET",
			dataType : "json",
			success: function(result) {
				// 是否不具有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用认证方法出错，错误信息：" + message);
			}
    	});
		return _this;
    };
    
	// 检验用户是否具有标识的权限
    $.fn.hasPermission = function (permissionName) {
    	var _this = this;
    	_this.hide();
    	$.ajax({	
			url : "auth/sys/authori/hasPermission",
			async : false,
			type : "GET",
			dataType : "json",
			data: {"permissionName" : permissionName},
			success: function(result) {
				// 是否有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用权限标识授权方法出错，错误信息：" + message);
			}
        });
    	return _this;
    };

    // 检验用户是否具有任一标识的权限(多个权限标识以,分隔)
    $.fn.hasAnyPermissions = function (permissionNames) {
    	var _this = this;
    	_this.hide();
		$.ajax({	
			url : "auth/sys/authori/hasAnyPermissions",
			async : false,
			type : "GET",
			dataType : "json",
			data: {"permissionNames" : permissionNames},
			success: function(result) {
				// 是否不具有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用权限标识授权方法出错，错误信息：" + message);
			}
    	});
		return _this;
    };
    
    // 检验用户是否不具有标识的权限
    $.fn.locksPermission = function (permissionName) {
    	var _this = this;
    	_this.hide();
		$.ajax({	
			url : "auth/sys/authori/locksPermission",
			async : false,
			type : "GET",
			dataType : "json",
			data: {"permissionName" : permissionName},
			success: function(result) {
				// 是否不具有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用权限标识授权方法出错，错误信息：" + message);
			}
    	});
		return _this;
    };

    // 对html页面中带有haspermisson的元素进行签权
    $.fn.initPermisson = function() {
    	
    }
    
	// 检验用户是否具有标识的角色
    $.fn.hasRole = function (roleName) {
    	var _this = this;
    	_this.hide();
    	$.ajax({	
			url : "auth/sys/authori/hasRole",
			async : false,
			type : "GET",
			dataType : "json",
			data: {"roleName" : roleName},
			success: function(result) {
				// 是否有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用角色授权方法出错，错误信息：" + message);
			}
        });
    	return _this;
    };

    // 检验用户是否具有任一标识的角色(多个角色标识以,分隔)
    $.fn.hasAnyRoles = function (roleNames) {
    	var _this = this;
    	_this.hide();
		$.ajax({	
			url : "auth/sys/authori/hasAnyRoles",
			async : false,
			type : "GET",
			dataType : "json",
			data: {"roleNames" : roleNames},
			success: function(result) {
				// 是否不具有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用角色授权方法出错，错误信息：" + message);
			}
    	});
		return _this;
    };
    
    // 检验用户是否不具有标识的角色
    $.fn.locksRole = function (roleName) {
    	var _this = this;
    	_this.hide();
		$.ajax({	
			url : "auth/sys/authori/lacksRole",
			async : false,
			type : "GET",
			dataType : "json",
			data: {"roleName" : roleName},
			success: function(result) {
				// 是否不具有标识的权限
				if(result) {
					_this.show();
				} else {
					_this.remove();
				}
			},
			error: function(xhr, message, error) {
				console.info("调用角色授权方法出错，错误信息：" + xhr.responseText);
			}
    	});
		return _this;
    };
})(jQuery);