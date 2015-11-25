<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>


<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link rel="stylesheet" href="<c:url value='/js/vendor/zTree/css/awesomeStyle/awesome.css'/>" type="text/css">
	<style type="text/css">
.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
	</style>

</head>

<body data-offset="250" data-target=".h5a-sidebar" data-spy="scroll">
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	</br>
	<div class="h5a-header" id="content">
		<div class="container h5a-container gray">
			<div class="row">

				<div class="col-sm-2">
					<div class="box hidden-print" style="height:500px;">
						 <div class="title">
								<h4 >
									<div style="margin-top:-10px;"><a href="#" >部门</a></div>
									
								</h4>
								<span class="pull-right boxes_right" style="margin-top:-35px;">
									<button class="btn btn-danger " type="button">修改</button>
								</span>
						
						 </div>
						 <ul id="treeDemo" class="ztree"></ul>
						
					</div>
				</div>
				
				<div class="col-sm-8">
					<div class="box hidden-print" style="height:500px;">
						 <div class="title">
								<h4 >
									<div style="margin-top:-13px;"><a href="#" >员工列表</a></div>
									
								</h4>
								<span class="pull-right boxes_right" style="margin-top:-30px;">
									<button class="btn btn-danger " type="button">添加员工</button>
								</span>
								
						 </div>

						<dl class="num_list clearfix">
							<dd>
								<a href="#">
									<p class="ind_num ind_color_green">
										7<em>人</em>
									</p>
									<p class="ind_name">在职员工</p>
								</a>
							</dd>
							<dd>
								<p class="ind_num ind_color_blue">
									4<em>人</em>
								</p>
								<p class="ind_name">全职员工</p>
							</dd>
							<dd>
								<p class="ind_num ind_color_red">
									3<em>人</em>
								</p>
								<p class="ind_name">外勤员工</p>
							</dd>
						</dl>
					</div>
				</div>
				
			</div>
			
			
		</div>
	</div>





	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script type="text/javascript" src="<c:url value='/js/vendor/zTree/js/jquery.ztree.core-3.5.js'/>"></script>
	
	<script type="text/javascript" src="<c:url value='/js/xcloud/staffs/staff-list.js'/>"></script>
</body>
</html>