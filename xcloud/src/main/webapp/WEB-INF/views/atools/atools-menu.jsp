<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">

	<ul class="am-list admin-sidebar-list">
		<li>
			<a href="/xcloud/atools/list">
				<span class="am-icon-cubes"></span>
				我的应用
			</a>
		</li>
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-atools')">
				<span class="am-icon-calculator"></span>
				常用计算器
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-atools">
				<li>
					<a href="/xcloud/atools/tools-insu" class="am-cf">
						<span class="am-icon-tag"></span>
						社保公积金计算器
					</a>
				</li>
				<li>
					<a href="/xcloud/atools/tools-tax" class="am-cf">
						<span class="am-icon-tag"></span>
						 个税计算器
					</a>
				</li>
				
				<li>
					<a href="/xcloud/atools/tools-year" class="am-cf">
						<span class="am-icon-tag"></span>
						 年终奖计算器
					</a>
				</li>
				
				<li>
					<a href="/xcloud/atools/tools-pay" class="am-cf">
						<span class="am-icon-tag"></span>
						 劳务报酬所得计算器
					</a>
				</li>
			</ul>
		</li>
		<li>
			<a href="/xcloud/atools/req" onclick="setMenuId('collapse-nav-atools-pay')">
				<span class="am-icon-magic" ></span>
				应用需求悬赏
			</a>
		</li>

	</ul>


</div>
<!-- sidebar end -->