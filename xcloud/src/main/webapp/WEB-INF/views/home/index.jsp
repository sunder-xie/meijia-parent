<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
</head>
<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	<div class="am-cf admin-main">
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf"></div>
			</div>
			<!-- 需进一步确认
			<div class="am-g ">
				<div class="am-u-md-6">
					<div class="am-panel am-panel-secondary">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-company'}">
							HI, ${sessionScope.accountAuth.name}, 美好的一天从这里开始了.
							<span class="am-icon-chevron-down am-fr"></span>
						</div>
						<div id="collapse-index-company" class="am-panel-bd am-in">
							&nbsp;&nbsp;你的企业未认证，申请企业认证，获取更多的权益.
							<div class="am-btn-toolbar am-fr">
								<div class="am-btn-group am-btn-group-sm ">
									<button type="button" id="btn-company-idn" class="am-btn am-btn-warning am-radius">
										<span class="am-icon-star"></span>
										申请企业认证
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div> 
			-->

			<div class="am-g ">
				<div class="am-u-md-12">
					<div class="am-panel am-panel-secondary">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-company'}">
							常用功能
							<span class="am-icon-chevron-down am-fr"></span>

						</div>
						<div id="collapse-index-company" class="am-panel-bd am-in">
							<ul class="am-avg-sm-1 am-avg-md-8 am-margin am-padding am-text-center admin-content-list ">
								<li>
									<a href="/xcloud/schedule/card-form?card_type=2" class="am-text-secondary">
										<img src="http://img.bolohr.com/733d857f736cdb7cefe39379df5634ea?p=0" width="50%">
										<br />通知公告<br />
									</a>
								</li>
								<li>
									<a href="/xcloud/schedule/card-form?card_type=3" class="am-text-secondary">
										<img src="http://img.bolohr.com/8befcfd517342750758dceb3893e705c?p=0" width="50%">
										<br />事务提醒<br />
									</a>
								</li>
								<li>
									<a href="/xcloud/schedule/card-form?card_type=4" class="am-text-secondary">
										<img src="http://img.bolohr.com/b7573e98e89ce8a59a3107fa66be2591?p=0" width="50%">
										<br />面试邀约<br />
									</a>
								</li>
								
								<li>
									<a href="/xcloud/staff/list" class="am-text-secondary">
										<img src="http://img.bolohr.com/ded312297decfd7d0bbe07650894e8d0?p=0" width="50%">
										<br />通讯录<br />
									</a>
								</li>
								
								<li>
									<a href="/xcloud/xz/checkin/stat-list" class="am-text-secondary">
										<img src="http://img.bolohr.com/0467c7ef376f21477359124fa8f038e1?p=0" width="50%">
										<br />云考勤<br />
									</a>
								</li>
								
								<li>
									<a href="/xcloud/xz/assets/company_asset_list" class="am-text-secondary">
										<img src="http://img.bolohr.com/ff54c25acfe1c170e7c93e457483aa9a?p=0" width="50%">
										<br />资产管理<br />
									</a>
								</li>
								
								<li>
									<a href="/xcloud/atools/tools-tax" class="am-text-secondary">
										<img src="http://img.bolohr.com/15bd4e23a7882291d3ca9bd612011cd1?p=0" width="50%">
										<br />个税计算器<br />
									</a>
								</li>
								<li>
									<a href="/xcloud/atools/list" class="am-text-secondary">
										<img src="http://img.bolohr.com/437396cc0b49b04dc89a0552f7e90cae?p=0" width="50%">
										<br />更多功能<br />
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="am-g">
				<div class="am-u-md-6">
					<div class="am-panel am-panel-success">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-card'}">
							热点知识精选
							<span class="am-icon-arrow-right am-fr"><a href="http://bolohr.com" target="_blank"> 更多</a></span>
						</div>
						<div class="am-in" height="100%">
							<table id="news-index" class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
								<tbody>
									
									
								</tbody>
							</table>
						</div>
						
					</div>
				</div>
				<div class="am-u-md-6">
					<div class="am-panel am-panel-secondary">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-wp'}">
							薪资与福利
							<span class="am-icon-arrow-right am-fr"><a href="http://bolohr.com/tag/%E8%96%AA%E8%B5%84" target="_blank"> 更多</a></span>
						</div>
						<div class="am-in">
							<table id="news-64" class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
								<tbody>
									
								</tbody>
							</table>
						</div>
						
					</div>
				</div>
			</div>
			
			<div class="am-g">
				<div class="am-u-md-6">
					<div class="am-panel am-panel-danger">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-card'}">
							招聘与面试
							<span class="am-icon-arrow-right am-fr"><a href="http://bolohr.com/tag/%E6%8B%9B%E8%81%98" target="_blank"> 更多</a></span>
						</div>
						<div class="am-in" height="100%">
							<table id="news-62" class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
								<tbody>
									
									
								</tbody>
							</table>
						</div>
						
					</div>
				</div>
				<div class="am-u-md-6">
					<div class="am-panel am-panel-warning">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-wp'}">
							绩效与考核
							<span class="am-icon-arrow-right am-fr"><a href="http://bolohr.com/tag/%E7%BB%A9%E6%95%88" target="_blank"> 更多</a></span>
						</div>
						<div class="am-in">
							<table id="news-65" class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
								<tbody>
									
								</tbody>
							</table>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<!-- content end -->
	</div>
	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/home/index.js'/>"></script>
</body>
</html>
