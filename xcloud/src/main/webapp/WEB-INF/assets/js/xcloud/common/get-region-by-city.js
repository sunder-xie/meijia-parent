//根据城市动态加载 城市 下辖的 区县
$("#cityId").on('change',function(){
	
	var cityId = $(this).val();
	
	
	//simiOaRootUrl 定义在 common.js	
	
	$.ajax({
		type:"get",
		url: simiOaRootUrl + "/interface-dict/get-region-by-cityid.json",
		dataType:'json',
		data: {
			"cityId" : cityId
		},
		success: function(data){
			
			var regionList = data.data;
			
			$("#regionId").html("");
			
//				var optionHtml = "<option value=''>请选择区县</option>";
			
			var optionHtml = "";
			
			for(var i= 0, j= regionList.length ; i <j ; i++){
				
				optionHtml += "<option value="+regionList[i].region_id + ">" +regionList[i].name+"</option>";
			}
			
			$("#regionId").append(optionHtml);
		},
		error : function(){
			
			console.log('选择城市得到区县,请求发生error');
		}
	});
	
});


//页面加载事件
$(document).ready(function(){
	
	$("#cityId").trigger("change");
	
});

// 对于 点击重置 按钮后, 实现联动效果。 浏览器  默认的重置  （reset） 事件 发生在 点击事件之后
function getRegionByCity(cityId){
	
	if(cityId == ""){
		
		var  $optionList = "<option value=''>请选择区县</option>";
		$("#regionId").html($optionList);
		return false;
	}
	
	
	//simiOaRootUrl 定义在 common.js	
	
	$.ajax({
		type:"get",
		url: simiOaRootUrl + "/interface-dict/get-region-by-cityid.json",
		dataType:'json',
		data: {
			"cityId" : cityId
		},
		success: function(data){
			
			var regionList = data.data;
			
			$("#regionId").html("");
			
			var optionHtml = "";
			
			for(var i= 0, j= regionList.length ; i <j ; i++){
				
				optionHtml += "<option value="+regionList[i].region_id + ">" +regionList[i].name+"</option>";
			}
			
			$("#regionId").append(optionHtml);
		},
		error : function(){
			
			console.log('选择城市得到区县,请求发生error');
		}
	});
	
}

